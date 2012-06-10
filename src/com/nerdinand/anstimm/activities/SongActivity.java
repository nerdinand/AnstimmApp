package com.nerdinand.anstimm.activities;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.nerdinand.anstimm.R;
import com.nerdinand.anstimm.entities.Song;
import com.nerdinand.anstimm.entities.Song.Voice;
import com.nerdinand.anstimm.worker.MidiPlayer;

public class SongActivity extends Activity implements OnClickListener,
		OnCompletionListener {

	private MidiPlayer midiPlayer;
	private TextView titleText;
	private TextView composerText;
	private TextView arrangementText;

	private TextView[] textViews;

	private VoiceButton soprano1Button;
	private VoiceButton soprano2Button;
	private VoiceButton alto1Button;
	private VoiceButton alto2Button;

	private VoiceButton tenor1Button;
	private VoiceButton tenor2Button;
	private VoiceButton bass1Button;
	private VoiceButton bass2Button;

	private VoiceButton solo1Button;
	private VoiceButton solo2Button;

	private VoiceButton[] voiceButtons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		midiPlayer = new MidiPlayer(this);

		setContentView(R.layout.song_layout);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		titleText = (TextView) findViewById(R.id.titleText);
		composerText = (TextView) findViewById(R.id.composerText);
		arrangementText = (TextView) findViewById(R.id.arrangementText);

		textViews = new TextView[] { titleText, composerText, arrangementText };

		soprano1Button = (VoiceButton) findViewById(R.id.soprano1Button);
		soprano1Button.setVoice(Voice.S1);
		soprano2Button = (VoiceButton) findViewById(R.id.soprano2Button);
		soprano2Button.setVoice(Voice.S2);
		alto1Button = (VoiceButton) findViewById(R.id.alto1Button);
		alto1Button.setVoice(Voice.A1);
		alto2Button = (VoiceButton) findViewById(R.id.alto2Button);
		alto2Button.setVoice(Voice.A2);

		tenor1Button = (VoiceButton) findViewById(R.id.tenor1Button);
		tenor1Button.setVoice(Voice.T1);
		tenor2Button = (VoiceButton) findViewById(R.id.tenor2Button);
		tenor2Button.setVoice(Voice.T2);
		bass1Button = (VoiceButton) findViewById(R.id.bass1Button);
		bass1Button.setVoice(Voice.B1);
		bass2Button = (VoiceButton) findViewById(R.id.bass2Button);
		bass2Button.setVoice(Voice.B2);

		solo1Button = (VoiceButton) findViewById(R.id.solo1Button);
		solo1Button.setVoice(Voice.So1);
		solo2Button = (VoiceButton) findViewById(R.id.solo2Button);
		solo2Button.setVoice(Voice.So2);

		voiceButtons = new VoiceButton[] { soprano1Button, soprano2Button,
				alto1Button, alto2Button, tenor1Button, tenor2Button,
				bass1Button, bass2Button, solo1Button, solo2Button };

		for (VoiceButton voiceButton : voiceButtons) {
			voiceButton.setOnClickListener(this);
		}

		midiPlayer.setOnCompletionListener(this);

		clear();
	}

	private void clear() {
		for (TextView textView : textViews) {
			textView.setText("");
		}

		for (Button voiceButton : voiceButtons) {
			voiceButton.setVisibility(Button.INVISIBLE);
		}
	}

	private void displaySong(Song selectedSong) {
		titleText.setText(selectedSong.getTitle());
		composerText.setText(selectedSong.getComposer());
		arrangementText.setText(selectedSong.getArrangement());

		for (VoiceButton voiceButton : voiceButtons) {
			if (selectedSong.hasVoice(voiceButton.getVoice())) {
				voiceButton.setVisibility(Button.VISIBLE);
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		Song selectedSong = AnstimmAppActivity.getSelectedSong();

		displaySong(selectedSong);
		play(selectedSong);
	}

	private void play(Song song) {
		midiPlayer.playMidi(song.getIntonation());
	}

	@Override
	protected void onStop() {
		super.onStop();

		midiPlayer.stop();
	}

	@Override
	public void onClick(View view) {
		if (view instanceof VoiceButton) {
			setVoiceButtonsEnabled(false);
			
			VoiceButton voiceButton = (VoiceButton) view;

			Voice voice = voiceButton.getVoice();
			Song selectedSong = AnstimmAppActivity.getSelectedSong();
			String tone = selectedSong.getTone(voice);
			midiPlayer.playMidi(8, new String[] { tone });
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		setVoiceButtonsEnabled(true);
	}
	
	private void setVoiceButtonsEnabled(boolean enabled){
		for (VoiceButton voiceButton : voiceButtons) {
			voiceButton.setEnabled(enabled);
		}
	}
}
