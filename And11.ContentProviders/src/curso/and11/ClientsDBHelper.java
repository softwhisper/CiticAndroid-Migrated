package curso.and11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ClientsDBHelper extends SQLiteOpenHelper {

	
    String sqlCreate = "CREATE TABLE clients " + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
	           " name TEXT, " + 
	           " phone_number TEXT, " +
	           " email TEXT )";
	
	
	public ClientsDBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
        
        for(int i = 1; i <= 15; i++) {
            String name = "Cliente " + i;
            String phone_number = "900-123-00" + i;
            String email = "email" + i + "@mail.com";

            db.execSQL("INSERT INTO clients (name, phone_number, email) " +
                       "VALUES ('" + name + "', '" + phone_number +"', '" + email + "')");
        }

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clients");      
        db.execSQL(sqlCreate);
	}

}
