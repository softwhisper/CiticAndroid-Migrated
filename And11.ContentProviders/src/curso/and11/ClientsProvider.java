package curso.and11;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

public class ClientsProvider extends ContentProvider {
	
	// Content URI
	private static final String uri = "content://curso.and/clients";
	public static final Uri CONTENT_URI = Uri.parse(uri);
	
	// Matcher 
	private static final UriMatcher uriMatcher;
	private static final int CLIENTS = 1;
	private static final int CLIENTS_ID = 2;
	
	//Inicializamos el UriMatcher
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("curso.and", "clientes", CLIENTS);
		uriMatcher.addURI("curso.and", "clientes/#", CLIENTS_ID);
	}	
	
	// Constant for the columns
	public static final class Clients implements BaseColumns {
		private Clients() {}
		
		public static final String C_NAME = "name";
		public static final String C_PHONE = "phone_number";
		public static final String C_EMAIL = "email";
	}
	
	private ClientsDBHelper db;
	private static final String DB_NAME = "CiticClients";
	private static final int DB_VERSION = 1;
	private static final String CLIENTS_TABLE = "clients";
	
	
	@Override
	public boolean onCreate() {
		db = new ClientsDBHelper(getContext(), DB_NAME, null, DB_VERSION);
		return false;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		String where = selection;
		
		if (uriMatcher.match(uri) == CLIENTS_ID) 
            where = "_id=" + uri.getLastPathSegment();
        

		SQLiteDatabase database = db.getWritableDatabase();
		Cursor c = database.query(CLIENTS_TABLE, projection, where, selectionArgs, null, null, sortOrder);
		
		return c;	
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long regId = 1; 
		
		SQLiteDatabase database = db.getWritableDatabase();
		regId = database.insert(CLIENTS_TABLE, null, values);
		Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
		
		return newUri;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		
		String where = selection;
		
		if (uriMatcher.match(uri) == CLIENTS_ID)
            where = "_id=" + uri.getLastPathSegment();
        
		SQLiteDatabase database = db.getWritableDatabase();
		
		return database.update(CLIENTS_TABLE, values, where, selectionArgs);
	}

	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String where = selection;
		if(uriMatcher.match(uri) == CLIENTS_ID){
            where = "_id=" + uri.getLastPathSegment();
        }
		
		SQLiteDatabase database = db.getWritableDatabase();
		
		return database.delete(CLIENTS_TABLE, where, selectionArgs);
	}

	@Override
	public String getType(Uri uri) {
		int match = uriMatcher.match(uri);
		
		switch (match)  {
			case CLIENTS: 
				return "vnd.android.cursor.dir/vnd.curso.client";
			case CLIENTS_ID: 
				return "vnd.android.cursor.item/vnd.curso.client";
			default: 
				return null;
		}
	}
}
