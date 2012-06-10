package com.nerdinand.anstimm.activities;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.nerdinand.anstimm.entities.Song.Voice;

public class VoiceButton extends Button {
	private Voice voice;
	
	public VoiceButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public VoiceButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VoiceButton(Context context) {
		super(context);
	}
	
	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	public Voice getVoice() {
		return voice;
	}
}
