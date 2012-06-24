package com.nerdinand.anstimm.activities;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.hlidskialf.android.hardware.ShakeListener;
import com.nerdinand.anstimm.R;
import com.nerdinand.anstimm.db.SongDB;
import com.nerdinand.anstimm.db.SongDBUpdater;
import com.nerdinand.anstimm.db.SongDBUpdaterException;

public class AnstimmAppActivity extends Activity implements
		OnItemClickListener, TextWatcher, OnClickListener {
	private ListView songListView;
	private SongDBUpdater songDBUpdater;
	private SimpleCursorAdapter cursorAdapter;
	private EditText filterEditText;
	private ShakeListener shakeListener;
	private Button clearButton;
	private Random random;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		random = new Random();

		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		shakeListener = new ShakeListener(this);
		shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {

			public void onShake() {
				int rand = random.nextInt(songListView.getCount());
				startSongActivity(cursorAdapter.getItemId(rand));
			}
		});

		songDBUpdater = new SongDBUpdater(this);

		songListView = (ListView) this.findViewById(R.id.songListView);
		songListView.setFastScrollEnabled(true);
		songListView.setOnItemClickListener(this);

		Cursor songCursor = getSongCursor();

		cursorAdapter = new SimpleCursorAdapter(getApplicationContext(),
				R.layout.song_row, songCursor, new String[] {
						SongDB.SongTable.COLUMN_TITLE,
						SongDB.SongTable.COLUMN_COMPOSER },// from
				new int[] { R.id.title, R.id.composer });// to)

		songListView.setAdapter(cursorAdapter);
		songListView.setFastScrollEnabled(true);

		filterEditText = (EditText) findViewById(R.id.filterEditText);
		filterEditText.addTextChangedListener(this);

		clearButton = (Button) findViewById(R.id.clearButton);
		clearButton.setOnClickListener(this);
	}

	private Cursor getSongCursor() {
		return SongDB.getInstance(this).getSongs();
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (cursorAdapter.getCount() == 0) {
			updateSongs();
		}

		reloadSongList();
		filterList(filterEditText.getText().toString());
	}

	private void reloadSongList() {
		cursorAdapter.changeCursor(getSongCursor());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.information:
			Intent intent = new Intent(this, InformationActivity.class);
			this.startActivity(intent);

			break;
		case R.id.update_songs:
			updateSongs();

			reloadSongList();

			break;

		default:
			break;
		}
		return true;
	}

	private void updateSongs() {
		try {
			songDBUpdater.update();
		} catch (SongDBUpdaterException e) {
			Toast.makeText(this, this.getString(R.string.downloading_error),
					Toast.LENGTH_LONG);
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent == songListView) {
			long songId = cursorAdapter.getItemId(position);
			startSongActivity(songId);
		}
	}

	private void startSongActivity(long songId) {
		Intent intent = new Intent(this, SongActivity.class);

		// to only have one SongActivity on the top of the stack, so pressing
		// back button will always open AnstimmAppActivity again
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		intent.putExtra(SongDB.SongTable.TABLE + "."
				+ SongDB.SongTable.COLUMN_ID, songId);
		this.startActivity(intent);
	}

	@Override
	public void afterTextChanged(Editable editable) {
		String filterString = editable.toString();

		filterList(filterString);
	}

	private void filterList(String filterString) {
		cursorAdapter.changeCursor(SongDB.getInstance(this).getFilteredSongs(
				filterString));
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void onClick(View view) {
		if (view == clearButton) {
			filterEditText.setText("");
		}
	}
}
