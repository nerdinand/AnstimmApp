package com.nerdinand.anstimm.activities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nerdinand.anstimm.R;
import com.nerdinand.anstimm.entities.Song;
import com.nerdinand.anstimm.entities.SongComparator;
import com.nerdinand.anstimm.worker.SongDownloader;
import com.nerdinand.anstimm.worker.SongFileParser;

public class AnstimmAppActivity extends Activity implements OnItemClickListener {
	private static Song selectedSong;
	ArrayList<Song> songList = new ArrayList<Song>();
	File songFile;

	private SongFileParser songFileParser;

	private SongDownloader songDownloader;
	private ListView songListView;
	private SongAdapter songAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		songFile = this.getFileStreamPath(SongDownloader.CSV_FILE_NAME);

		songFileParser = new SongFileParser(songFile);
		songDownloader = new SongDownloader(this);

		songListView = (ListView) this.findViewById(R.id.songListView);
		songListView.setFastScrollEnabled(true);
		songListView.setOnItemClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (!songFile.exists()) {
			updateSongs();
		}

		reloadSongList();
	}

	private void reloadSongList() {
		try {
			songList = songFileParser.parse();
			songAdapter = new SongAdapter(this, R.layout.song_row, songList);
			songAdapter.sort(new SongComparator());
			songListView.setAdapter(songAdapter);

		} catch (IOException e) {
			Toast.makeText(this, this.getString(R.string.parsing_error),
					Toast.LENGTH_LONG);
		}
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
			songDownloader.downloadSong();
		} catch (Exception e) {
			Toast.makeText(this, this.getString(R.string.downloading_error),
					Toast.LENGTH_LONG);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent == songListView) {
			Song song = songList.get(position);
			startSongActivity(song);
		}
	}

	private void startSongActivity(Song song) {
		AnstimmAppActivity.setSelectedSong(song);
		
		Intent intent = new Intent(this, SongActivity.class);
		this.startActivity(intent);
	}

	private static void setSelectedSong(Song song) {
		AnstimmAppActivity.selectedSong=song;
	}
	
	public static Song getSelectedSong() {
		return selectedSong;
	}

	private class SongAdapter extends ArrayAdapter<Song> {

		private ArrayList<Song> items;

		public SongAdapter(Context context, int textViewResourceId,
				ArrayList<Song> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.song_row, null);
			}

			Song song = items.get(position);
			if (song != null) {
				TextView titleView = (TextView) convertView
						.findViewById(R.id.title);
				TextView composerView = (TextView) convertView
						.findViewById(R.id.composer);
				TextView voicesView = (TextView) convertView
						.findViewById(R.id.voices);

				titleView.setText(song.getTitle());
				composerView.setText(song.getComposer());
				voicesView.setText(song.getVoicesString());
			}

			return convertView;
		}
	}
}
