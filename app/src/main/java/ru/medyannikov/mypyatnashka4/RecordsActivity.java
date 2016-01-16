package ru.medyannikov.mypyatnashka4;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import ru.medyannikov.mypyatnashka4.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class RecordsActivity extends Activity {
	private RecordsDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_records);
		ListView list = (ListView) findViewById(R.id.list_view);
		datasource = new RecordsDataSource(this);

		datasource.open();
		//datasource.createRecord("1:43", 155);
		//datasource.createRecord("1:43", 154);

		List<Record> values = datasource.getAllRecords();

		Collections.sort(values);

		// use the SimpleCursorAdapter to show the
		// elements in a ListView
		final ArrayAdapter<Record> adapter = new ArrayAdapter<Record>(this,
				android.R.layout.simple_list_item_1, values);
		list.setAdapter(adapter);

		list.setOnItemLongClickListener (new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(final AdapterView adapter1, final View view,
										   final int arg2, final long arg3) {
				// TODO Auto-generated method stub

				AlertDialog.Builder exitDialog;
				exitDialog = new AlertDialog.Builder(RecordsActivity.this);
				exitDialog.setTitle("Удаление");
				exitDialog.setMessage("Вы действительно хотите удалить запись ?");
				exitDialog.setPositiveButton("Да",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								Record rec = (Record)adapter1.getItemAtPosition(arg2);
								datasource.deleteRecord(rec);
								//Toast.makeText(getApplicationContext(), "text"+arg2 +" "+arg3+" "+rec.getId(), Toast.LENGTH_SHORT).show();
								adapter.remove(rec);
							}
						});

				exitDialog.setNegativeButton("Нет",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						});
				exitDialog.setCancelable(false);
				exitDialog.show();

				return false;
			}
		});
		//setListAdapter(adapter);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.records, menu);
		return true;
	}

}
