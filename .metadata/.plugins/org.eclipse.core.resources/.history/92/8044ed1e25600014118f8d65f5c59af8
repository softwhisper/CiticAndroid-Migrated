package curso.and10;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author pablo
 *
 */
public class DataBaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "PersonalContacts";
	private static final String TABLE_CONTACTS = "contacts";

	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PH_NO = "phone_number";	
	
	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_PH_NO + " TEXT" + ")";
		
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	/**
	 * Hacemos un drop y un create de nuevo.
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO
	}


	/*
	 * CRUD sobre Contact
	 */
	void addContact(Contact contact) {
		// TODO
	}

	Contact getContact(int id) {
		// TODO
	}
	
	public List<Contact> getAllContacts() {
		//TODO
		
		return null;
	}

	public int updateContact(Contact contact) {
		// TODO
		return 0;
	}

	public void deleteContact(Contact contact) {
		// TODO
	}

	public int getContactsCount() {
		// TODO
	}	
}
