package com.nerdinand.anstimm.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nerdinand.anstimm.R;

public class InformationActivity extends Activity implements OnItemClickListener {
	private ListView changeLogListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.information_layout);

		changeLogListView = (ListView) findViewById(R.id.changeLogListView);
		changeLogListView.setOnItemClickListener(this);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.versions, android.R.layout.simple_list_item_1);

		changeLogListView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		String[] changes = getResources().getStringArray(R.array.changes);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(changes[position]);
		builder.setTitle(((TextView)view).getText());
		builder.setPositiveButton(getString(R.string.ok), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.show();
		
	}
}
