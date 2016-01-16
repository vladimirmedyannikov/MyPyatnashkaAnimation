package ru.medyannikov.mypyatnashka4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RecordsDataSource {
	// Database fields
	  private SQLiteDatabase database;
	  private SQLiteHelper dbHelper;
	  private String[] allColumns = { SQLiteHelper.COLUMN_ID,
			  SQLiteHelper.COLUMN_TIME, SQLiteHelper.COLUMN_MOVEMENT,SQLiteHelper.COLUMN_NAME};
	public RecordsDataSource(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper = new SQLiteHelper(context);
	}
	
	public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }
	
	public void close() {
	    dbHelper.close();
	  }
	
	public Record createRecord(String time, int mov, String name) {
	    ContentValues values = new ContentValues();
	    
	    values.put(SQLiteHelper.COLUMN_TIME, time);
	    values.put(SQLiteHelper.COLUMN_MOVEMENT, mov);
	    values.put(SQLiteHelper.COLUMN_NAME, name);
	    long insertId = database.insert(SQLiteHelper.TABLE_RECORDS, null,
	        values);
	    Cursor cursor = database.query(SQLiteHelper.TABLE_RECORDS,
	        allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Record newRecord = cursorToRecord(cursor);
	    cursor.close();
	    return newRecord;
	}
	
	public void deleteRecord(Record record) {
	    long id = record.getId();
	    //System.out.println("Comment deleted with id: " + id);
	    database.delete(SQLiteHelper.TABLE_RECORDS, SQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<Record> getAllRecords() {
	    List<Record> records = new ArrayList<Record>();

	    Cursor cursor = database.query(SQLiteHelper.TABLE_RECORDS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Record record = cursorToRecord(cursor);
	      records.add(record);
	      cursor.moveToNext();
	    }
	    Collections.sort(records);
	    // make sure to close the cursor
	    cursor.close();
	    return records;
	  }
	
	private Record cursorToRecord(Cursor cursor) {
	    Record record = new Record();
	    record.setId(cursor.getLong(0));
	    record.setTime(cursor.getString(1));
	    record.setMovement(cursor.getInt(2));
	    record.setName(cursor.getString(3));
	    return record;
	  }
}
