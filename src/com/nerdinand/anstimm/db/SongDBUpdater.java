package com.nerdinand.anstimm.db;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;

import com.nerdinand.anstimm.entities.Song;
import com.nerdinand.anstimm.worker.SongDownloader;
import com.nerdinand.anstimm.worker.SongFileParser;

public class SongDBUpdater {
	private SongFileParser songFileParser;
	private SongDownloader songDownloader;
	private File songFile;
	private Context context;

	public SongDBUpdater(Context context) {
		this.context = context;

		songFile = context.getFileStreamPath(SongDownloader.CSV_FILE_NAME);

		songFileParser = new SongFileParser(songFile);
		songDownloader = new SongDownloader(context);
	}

	public void update() throws SongDBUpdaterException {
		SongDB songDB = SongDB.getInstance(context);

		try {
			songDownloader.downloadSong();
			ArrayList<Song> songList = songFileParser.parse();

			songDB.deleteSongs();
			
			for (Song song : songList) {
				songDB.insertSong(song);
			}
			
		} catch (Exception e) {
			throw new SongDBUpdaterException(e);

		} finally {
			songFile.delete();
			songDB.close();
		}
	}
}