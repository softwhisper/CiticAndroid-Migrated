package es.sw.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import es.sw.weather.model.Weather;
import es.sw.weather.model.MainData;

import java.sql.SQLException;


/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String TAG = "DatabaseHelper";

	// name of the database file for your application -- change to something
	// appropriate for your app
	private static final String DATABASE_NAME = "weather.db";
	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 8;

    /**
     * Constructor
     * @param context
     */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

    /**
	 * This is called when the database is first created. Usually you should call
	 * createTable statements here to create the tables that will store your data.
	 */

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			if (BuildConfig.DEBUG)
				Log.e(DatabaseHelper.class.getName(), "onCreate");

			TableUtils.createTable(connectionSource, Weather.class);
			TableUtils.createTable(connectionSource, MainData.class);
			
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}


	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the new
	 * version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			if (BuildConfig.DEBUG)
				Log.e(DatabaseHelper.class.getName(), "onUpgrade");

			TableUtils.dropTable(connectionSource, Weather.class, true);
			TableUtils.dropTable(connectionSource, MainData.class, true);

			
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
		    Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
}