package curso.and22;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import curso.and22.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final int ENABLE_BLUETOOTH = 1;
	protected static final String TAG = "BLUETOOTH";
	protected static final int DISCOVERY_REQUEST = 1;
	BluetoothAdapter bluetooth;
	private TextView txtView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
		this.bluetooth = bluetooth;
		
		initBluetooth();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void initBluetoothUI() {
		Log.d(TAG, "Init UI");
		txtView = (TextView)findViewById(R.id.txtView);
		startDiscovery();
		makeDiscoverable();
	}
	
	private void initBluetooth() {
		if (!bluetooth.isEnabled()) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(intent, ENABLE_BLUETOOTH);
		} else {
			initBluetoothUI();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ENABLE_BLUETOOTH)
			if (resultCode == RESULT_OK) {
				initBluetoothUI();
			}

		if (requestCode == DISCOVERY_REQUEST) {
			if (resultCode == RESULT_CANCELED) {
				Log.d(TAG, "Discovery cancelled by user");
			}
		}

	}

	private void makeDiscoverable() {
		startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE), DISCOVERY_REQUEST);
	}

	/*
	 * Discovering remote Bluetooth Devices
	 */
	private ArrayList<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();

	private void startDiscovery() {
		Log.d(TAG, "StartDiscovering");
		registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));

		if (bluetooth.isEnabled() && !bluetooth.isDiscovering())
			deviceList.clear();
		
		bluetooth.startDiscovery();
	}

	BroadcastReceiver discoveryResult = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String remoteDeviceName = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
			BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

			deviceList.add(remoteDevice);
			
			txtView.append("\n" + remoteDevice);
			Log.d(TAG, "Discovered " + remoteDeviceName);
		}
	};

	/*
	 *  Listening for Bluetooth Socket connection requests
	 */
	private BluetoothSocket transferSocket;

	private UUID startServerSocket(BluetoothAdapter bluetooth) {
		UUID uuid = UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666");
		String name = "bluetoothserver";

		try {
			final BluetoothServerSocket btserver = bluetooth.listenUsingRfcommWithServiceRecord(name, uuid);

			Thread acceptThread = new Thread(new Runnable() {
				public void run() {
					try {

						BluetoothSocket serverSocket = btserver.accept();
						StringBuilder incoming = new StringBuilder();
						listenForMessages(serverSocket, incoming);

						transferSocket = serverSocket;
					} catch (IOException e) {
						Log.e(TAG, "Server connection IO Exception", e);
					}
				}
			});
			acceptThread.start();
		} catch (IOException e) {
			Log.e(TAG, "Socket listener IO Exception", e);
		}
		return uuid;
	}

	/*
	 * Creating a Bluetooth client socket
	 */
	private void connectToServerSocket(BluetoothDevice device, UUID uuid) {
		try {
			BluetoothSocket clientSocket = device.createRfcommSocketToServiceRecord(uuid);
			clientSocket.connect();

			StringBuilder incoming = new StringBuilder();
			listenForMessages(clientSocket, incoming);

			transferSocket = clientSocket;
		} catch (IOException e) {
			Log.e(TAG, "Blueooth client I/O Exception", e);
		}
	}

	/*
	 * Sending and receiving strings using Bluetooth Sockets
	 */
	private void sendMessage(BluetoothSocket socket, String message) {
		OutputStream outStream;
		try {
			outStream = socket.getOutputStream();

			byte[] byteArray = (message + " ").getBytes();
			byteArray[byteArray.length - 1] = 0;

			outStream.write(byteArray);
		} catch (IOException e) {
			Log.e(TAG, "Message send failed.", e);
		}
	}

	private boolean listening = false;

	private void listenForMessages(BluetoothSocket socket, StringBuilder incoming) {
		listening = true;

		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];

		try {
			InputStream instream = socket.getInputStream();
			int bytesRead = -1;

			while (listening) {
				bytesRead = instream.read(buffer);
				if (bytesRead != -1) {
					String result = "";
					while ((bytesRead == bufferSize) && (buffer[bufferSize - 1] != 0)) {
						result = result + new String(buffer, 0, bytesRead - 1);
						bytesRead = instream.read(buffer);
					}
					result = result + new String(buffer, 0, bytesRead - 1);
					incoming.append(result);
				}
				socket.close();
			}
		} catch (IOException e) {
			Log.e(TAG, "Message received failed.", e);
		} finally {
		}
	}
}
