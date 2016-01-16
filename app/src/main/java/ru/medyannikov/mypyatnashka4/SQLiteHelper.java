package ru.medyannikov.mypyatnashka4;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_RECORDS = "pyatnashka_records";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_MOVEMENT = "movement_count";
	public static final String COLUMN_NAME = "name_user";
	public static final String DATABASE_NAME = "pyatnashaka.db";
	public static final int DATABASE_VERSION = 5;
	private static final String DATABASE_CREATE = "create table "
		      + TABLE_RECORDS + "(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN_TIME
		      + " text not null, "+COLUMN_MOVEMENT+" int not null, "+COLUMN_NAME+" text not null);";
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(DATABASE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
	    onCreate(db);
	}

}
