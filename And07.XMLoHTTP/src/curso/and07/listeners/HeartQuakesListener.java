package curso.and07.listeners;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import curso.and07.Quake;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HeartQuakesListener extends CustomListener {
	private final static String TAG = "HearthQuakeListner";
	private ArrayList<Quake> resultList = new ArrayList<Quake>();
	
	public HeartQuakesListener(Context _context, Handler _handler) {
		super(_context, _handler);
	}

	@Override
	public void processIncomingData(byte[] data, int status) {
		Log.d(TAG, "processingData");
		try {
			if (data == null) {
				data = "data is null".getBytes();
			}
			
			Log.v(TAG, new String(data));
			
			parseQuakes(data);
			
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putParcelableArrayList("quakes", resultList);
			
			msg.setData(bundle);
			msg.what = 1;
			handler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
			handler.sendEmptyMessage(2);
		}
	}
	
	private void parseQuakes(byte[] data) throws Exception {
		Log.d(TAG, "parseQaukes");
		
		try {
			InputStream xmlInputStream = new ByteArrayInputStream(data);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			// Parse the earthquake feed.
			Document dom = db.parse(xmlInputStream);
			Element docEle = dom.getDocumentElement();

			// Get a list of each earthquake entry.
			NodeList nl = docEle.getElementsByTagName("entry");
			if (nl != null && nl.getLength() > 0) {
				for (int i = 0 ; i < nl.getLength(); i++) {
					Element entry = (Element)nl.item(i);
					Element title = (Element)entry.getElementsByTagName("title").item(0);
					Element g = (Element)entry.getElementsByTagName("georss:point").item(0);
					Element when = (Element)entry.getElementsByTagName("updated").item(0);
					Element link = (Element)entry.getElementsByTagName("link").item(0);

					String details = title.getFirstChild().getNodeValue();
					String hostname = "http://earthquake.usgs.gov";
					String linkString = hostname + link.getAttribute("href");

					String point = g.getFirstChild().getNodeValue();
					String dt = when.getFirstChild().getNodeValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
					Date qdate = new GregorianCalendar(0,0,0).getTime();
			
					try {
						qdate = sdf.parse(dt);
					} catch (ParseException e) {
						Log.d(TAG, "Date parsing exception.", e);
					}

					String[] location = point.split(" ");
					Location l = new Location("dummyGPS");
					l.setLatitude(Double.parseDouble(location[0]));
					l.setLongitude(Double.parseDouble(location[1]));

					String magnitudeString = details.split(" ")[1];
					int end = magnitudeString.length()-1;
					double magnitude = Double.parseDouble(magnitudeString.substring(0, end));

					details = details.split(",")[1].trim();

					Quake quake = new Quake(qdate, details, l, magnitude, linkString);
					resultList.add(quake);			
				
					Log.d(TAG, "Parsed: " + quake.toString());
				}
			}
		} catch (Exception e) {
			Log.d(TAG, "Parsing Error");
			e.printStackTrace();
		}
	}

}
