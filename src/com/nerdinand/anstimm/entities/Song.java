package com.nerdinand.anstimm.entities;

import java.util.ArrayList;
import java.util.HashMap;

import com.nerdinand.anstimm.Util;

public class Song {
	public enum Voice{
		S1, S2, A1, A2, T1, T2, B1, B2, So1, So2
	}
	
	private String timestamp;
	private String title;
	private String composer;
	private String arrangement;

	HashMap<Voice, String> voiceToneMap=new HashMap<Voice, String>();

	static Voice[] intonationOrder=new Voice[]{
		Voice.B2, 
		Voice.B1, 
		
		Voice.T2, 
		Voice.T1, 
		
		Voice.A2, 
		Voice.A1, 
		
		Voice.S2, 
		Voice.S1, 
		
		Voice.So2, 
		Voice.So1
	};
	
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
			voiceToneMap.put(Voice.S1, value);
		} else if (key.equals("Sopran 2")) {
			voiceToneMap.put(Voice.S2, value);
		} else if (key.equals("Alt 1")) {
			voiceToneMap.put(Voice.A1, value);
		} else if (key.equals("Alt 2")) {
			voiceToneMap.put(Voice.A2, value);
		} else if (key.equals("Tenor 1")) {
			voiceToneMap.put(Voice.T1, value);
		} else if (key.equals("Tenor 2")) {
			voiceToneMap.put(Voice.T2, value);
		} else if (key.equals("Bass 1")) {
			voiceToneMap.put(Voice.B1, value);
		} else if (key.equals("Bass 2")) {
			voiceToneMap.put(Voice.B2, value);
		} else if (key.equals("Solo 1")) {
			voiceToneMap.put(Voice.So1, value);
		} else if (key.equals("Solo 2")) {
			voiceToneMap.put(Voice.So2, value);
		} else if (key.equals("Tonart")) {
			setKey(value);
		} else if (key.equals("Genre")) {
			setGenre(value);
		}
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

	public String getSoprano1() {
		return voiceToneMap.get(Voice.S1);
	}

	public String getSoprano2() {
		return voiceToneMap.get(Voice.S2);
	}

	public String getAlto1() {
		return voiceToneMap.get(Voice.A1);
	}

	public String getAlto2() {
		return voiceToneMap.get(Voice.A2);
	}

	public String getTenor1() {
		return voiceToneMap.get(Voice.T1);
	}

	public String getTenor2() {
		return voiceToneMap.get(Voice.T2);
	}

	public String getBass1() {
		return voiceToneMap.get(Voice.B1);
	}

	public String getBass2() {
		return voiceToneMap.get(Voice.B2);
	}

	public String getSolo1() {
		return voiceToneMap.get(Voice.So1);
	}

	public String getSolo2() {
		return voiceToneMap.get(Voice.So2);
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

	public String getVoicesString() {
		return Util.arrayToString(getVoices(), "");
	}

	public boolean hasVoice(Voice voice){
		return voiceToneMap.containsKey(voice);
	}
	
	public String[] getVoices() {
		ArrayList<String> voices = new ArrayList<String>();

		if (getSoprano1() != null) {
			voices.add("S");
		}
		if (getSoprano2() != null) {
			voices.add("1S2");
		}
		if (getAlto1() != null) {
			voices.add("A");
		}
		if (getAlto2() != null) {
			voices.add("1A2");
		}
		if (getTenor1() != null) {
			voices.add("T");
		}
		if (getTenor2() != null) {
			voices.add("1T2");
		}
		if (getBass1() != null) {
			voices.add("B");
		}
		if (getBass2() != null) {
			voices.add("1B2");
		}
		if (getSolo1() != null) {
			voices.add("Solo");
		}
		if (getSolo2() != null) {
			voices.add(" 1, 2");
		}

		return voices.toArray(new String[] {});
	}
}
