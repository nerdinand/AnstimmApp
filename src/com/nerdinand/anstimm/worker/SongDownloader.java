package com.nerdinand.anstimm.worker;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.nerdinand.anstimm.R;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class SongDownloader {
	public static final String URL_STRING = "https://docs.google.com/spreadsheet/pub?key=0Atsn6KPUPAjsdDhmZ2FSeGhwV0RlMkNPTkw1cXk0Mnc&output=csv";
	public static final String CSV_FILE_NAME = "songs.csv";

	private Context context;

	public SongDownloader(Context context) {
		this.context = context;
	}

	public void downloadSong() throws FileNotFoundException, InterruptedException, ExecutionException, TimeoutException {
		
		Toast.makeText(context, context.getString(R.string.downloading),
				Toast.LENGTH_LONG);

		FileOutputStream openFileOutput = context.openFileOutput(CSV_FILE_NAME,
				Context.MODE_PRIVATE);

		DownloadFile downloadFile = new DownloadFile();
		downloadFile.execute(openFileOutput);
		downloadFile.get(20, TimeUnit.SECONDS);
	}
}

class DownloadFile extends AsyncTask<FileOutputStream, Integer, Void> {
	@Override
	protected Void doInBackground(FileOutputStream... fos) {
		try {
			URL url = new URL(SongDownloader.URL_STRING);
			URLConnection connection = url.openConnection();
			connection.connect();
			// this will be useful so that you can show a typical 0-100%
			// progress bar
			int fileLength = connection.getContentLength();

			// download the file
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = fos[0];

			byte data[] = new byte[1024];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				publishProgress((int) (total * 100 / fileLength));
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();

		} catch (Exception e) {
		}
		return null;
	}
}
