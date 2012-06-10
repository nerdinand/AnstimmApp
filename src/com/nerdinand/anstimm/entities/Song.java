package com.nerdinand.anstimm.entities;

import java.util.ArrayList;
import java.util.HashMap;

public class Song {
	public enum Voice {
		S1, S2, A1, A2, T1, T2, B1, B2, So1, So2
	}

	private String timestamp;
	private String title;
	private String composer;
	private String arrangement;

	private String status;
	private String editor;
	private String comment;

	HashMap<Voice, String> voiceToneMap = new HashMap<Voice, String>();

	static Voice[] intonationOrder = new Voice[] { Voice.B2, Voice.B1,

	Voice.T2, Voice.T1,

	Voice.A2, Voice.A1,

	Voice.S2, Voice.S1,

	Voice.So2, Voice.So1 };

	private String key;

	private String genre;

	@Override
	public String toString() {
		return "Song [timestamp=" + timestamp + ", title=" + title
				+ ", composer=" + composer + ", arrangement=" + arrangement
				+ ", voiceToneMap=" + voiceToneMap + ", key=" + key
				+ ", genre=" + genre + "]";
	}

	public void setAttribute(String key, String value) {
		if (key.equals("Zeitstempel")) {
			setTimestamp(value);
		} else if (key.equals("Liedtitel")) {
			setTitle(value);
		} else if (key.equals("Komponist")) {
			setComposer(value);
		} else if (key.equals("Arrangement")) {
			setArrangement(value);
		} else if (key.equals("Sopran 1")) {
			setVoice(Voice.S1, value);
		} else if (key.equals("Sopran 2")) {
			setVoice(Voice.S2, value);
		} else if (key.equals("Alt 1")) {
			setVoice(Voice.A1, value);
		} else if (key.equals("Alt 2")) {
			setVoice(Voice.A2, value);
		} else if (key.equals("Tenor 1")) {
			setVoice(Voice.T1, value);
		} else if (key.equals("Tenor 2")) {
			setVoice(Voice.T2, value);
		} else if (key.equals("Bass 1")) {
			setVoice(Voice.B1, value);
		} else if (key.equals("Bass 2")) {
			setVoice(Voice.B2, value);
		} else if (key.equals("Solo 1")) {
			setVoice(Voice.So1, value);
		} else if (key.equals("Solo 2")) {
			setVoice(Voice.So2, value);
		} else if (key.equals("Tonart")) {
			setKey(value);
		} else if (key.equals("Genre")) {
			setGenre(value);
		}
	}

	public void setVoice(Voice voice, String value) {
		voiceToneMap.put(voice, value);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public String getArrangement() {
		return arrangement;
	}

	public void setArrangement(String arrangement) {
		this.arrangement = arrangement;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String[] getIntonation() {
		ArrayList<String> intonation = new ArrayList<String>();

		for (Voice voice : intonationOrder) {
			intonation.add(getTone(voice));
		}

		return intonation.toArray(new String[0]);
	}

	public String getTone(Voice voice) {
		return clean(voiceToneMap.get(voice));
	}

	private String clean(String tone) {
		if (tone == null) {
			return "";
		}

		return tone.split("/")[0].trim();
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public boolean hasVoice(Voice voice) {
		return voiceToneMap.containsKey(voice)
				&& voiceToneMap.get(voice) != null
				&& !voiceToneMap.get(voice).isEmpty();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getVoice(Voice voice) {
		return voiceToneMap.get(voice);
	}

}
