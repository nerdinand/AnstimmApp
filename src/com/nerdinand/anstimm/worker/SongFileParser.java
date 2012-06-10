package com.nerdinand.anstimm.worker;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.nerdinand.anstimm.entities.Song;

import android.util.Log;
import au.com.bytecode.opencsv.CSVReader;

public class SongFileParser {

	private static final String TAG = "SongFileParser";
	private File songFile;

	public SongFileParser(File songFile) {
		this.songFile = songFile;
	}

	public ArrayList<Song> parse() throws IOException {
		ArrayList<Song> songList = new ArrayList<Song>();

		CSVReader csvReader;
		FileReader reader = new FileReader(songFile);
		csvReader = new CSVReader(reader);

		String[] keys = csvReader.readNext();
		Log.i(TAG, "csv keys: "+Arrays.toString(keys));

		String[] line;
		while ((line = csvReader.readNext()) != null) {
			Song song = new Song();
			
			for (int i=0; i<line.length; i++) {
				if (! line[i].isEmpty()) {
					song.setAttribute(keys[i], line[i]);
				}
			}
			
			Log.i(TAG, "adding song: "+song);
			
			songList.add(song);
		}

		Log.i(TAG, songList.size() + " songs parsed.");
		
		return songList;
	}
}
