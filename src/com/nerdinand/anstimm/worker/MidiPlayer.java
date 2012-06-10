package com.nerdinand.anstimm.worker;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.nerdinand.anstimm.R;
import com.nerdinand.anstimm.lib.MidiMaker;

public class MidiPlayer implements OnCompletionListener {

	private static final String TAG = "MidiPlayer";
	private MediaPlayer mediaPlayer;
	private Context context;
	private File midiFile;
	private OnCompletionListener onCompletionListener;

	public MidiPlayer(Context context) {
		this.context = context;
	}

	public void playMidi(String... tones) {
		playMidi(4, tones);
	}
	
	public void playMidi(int length, String... tones) {
		Log.i(TAG, Arrays.toString(tones));

		MidiMaker midiMaker = new MidiMaker();

		ArrayList<String> playedTones = new ArrayList<String>();

		for (String tone : tones) {
			if (tone != null && !tone.isEmpty()) {
				midiMaker.play(MidiMaker.getTone(tone), length);
				playedTones.add(tone);
			}
		}

		midiFile = midiMaker.generateFile();

		if (midiFile != null) {
			mediaPlayer = MediaPlayer.create(context, Uri.fromFile(midiFile));
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.start();

			Toast.makeText(context,
					context.getString(R.string.playing) + " " + playedTones,
					Toast.LENGTH_SHORT).show();
		}
	}

	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		if (onCompletionListener!=null){
			onCompletionListener.onCompletion(mp);
		}
		
		midiFile.delete();
		mediaPlayer.release();
		mediaPlayer = null;
	}

	public void setOnCompletionListener(
			OnCompletionListener onCompletionListener) {
		this.onCompletionListener = onCompletionListener;
	}

}