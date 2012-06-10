package com.nerdinand.anstimm.lib;

import java.io.*;
import java.util.HashMap;

import android.util.Log;

// Dit programma genereert een MIDI-file met de muziek
// die in de methode score wordt gespecificeerd.

public class MidiMaker {
	// In de methode score specificeer je de melodie, door
	// aanroepen van de methode play.
	// Die methode heeft twee parameters:
	// * een int die de hoogte van de toon aangeeft (of 0 voor stilte)
	// * een int die de lengte van de toon aangeeft

	// er zijn constanten beschikbaar voor enkele veel-gebruikte toonhoogtes

	private static final String TAG = "MidiMaker";
	private static HashMap<String, Integer> toneValues = new HashMap<String, Integer>();

	// met deze methoden kun je een noot een kruis (sharp) of een mol (flat)
	// geven
	// of een octaaf verhogen of verlagen

	static int sharp(int x) {
		return x + 1;
	}

	static int flat(int x) {
		return x - 1;
	}

	static int high(int x) {
		return x + 12;
	}

	static int low(int x) {
		return x - 12;
	}

	// ======================================================================
	// de rest van het programma dient niet veranderd te worden

	final byte NoteOn = (byte) 144;
	final byte NoteOff = (byte) 128;
	final byte defaultVolume = (byte) 100;
	final int defaultTPQ = 480;

	int timeSinceLastNote;
	int tpq = defaultTPQ;

	ByteArrayOutputStream track;

	public MidiMaker() {
		track = new ByteArrayOutputStream();

		toneValues.put("C", 36);
		toneValues.put("C#", 37);
		toneValues.put("D", 38);
		toneValues.put("D#", 39);
		toneValues.put("E", 40);
		toneValues.put("F", 41);
		toneValues.put("F#", 42);
		toneValues.put("G", 43);
		toneValues.put("G#", 44);
		toneValues.put("A", 45);
		toneValues.put("A#", 46);
		toneValues.put("H", 47);

		toneValues.put("c", 48);
		toneValues.put("c#", 49);
		toneValues.put("d", 50);
		toneValues.put("d#", 51);
		toneValues.put("e", 52);
		toneValues.put("f", 53);
		toneValues.put("f#", 54);
		toneValues.put("g", 55);
		toneValues.put("g#", 56);
		toneValues.put("a", 57);
		toneValues.put("a#", 58);
		toneValues.put("h", 59);

		toneValues.put("c'", 60);
		toneValues.put("c'#", 61);
		toneValues.put("d'", 62);
		toneValues.put("d'#", 63);
		toneValues.put("e'", 64);
		toneValues.put("f'", 65);
		toneValues.put("f'#", 66);
		toneValues.put("g'", 67);
		toneValues.put("g'#", 68);
		toneValues.put("a'", 69);
		toneValues.put("a'#", 70);
		toneValues.put("h'", 71);
		toneValues.put("c''", 72);

		toneValues.put("c''#", 73);
		toneValues.put("d''", 74);
		toneValues.put("d''#", 75);
		toneValues.put("e''", 76);
		toneValues.put("f''", 77);
		toneValues.put("f''#", 78);
		toneValues.put("g''", 79);
		toneValues.put("g''#", 80);
		toneValues.put("a''", 81);
		toneValues.put("a''#", 82);
		toneValues.put("h''", 83);
		toneValues.put("c'''", 84);
	}

	public File generateFile() {
		try {
			File tempFile;
			tempFile = File.createTempFile("anstimm", ".midi");
			String filePath = tempFile.getCanonicalPath();

			try {
				DataOutputStream data;
				data = new DataOutputStream(new FileOutputStream(tempFile));

				data.writeBytes("MThd");
				data.writeInt(6);
				data.writeInt(1);
				data.writeShort((short) defaultTPQ);
				data.writeBytes("MTrk");
				data.writeInt(track.size() + 4);
				data.write(track.toByteArray());
				data.writeInt(16723712);
				data.close();

				Log.i(TAG, "file written to " + filePath);

			} catch (Exception e) {
				Log.e(TAG, "error writing file" + filePath);
			}

			return tempFile;
		} catch (IOException e1) {
			Log.e(TAG, "could not create temporary file");
		}

		return null;
	}

	public void play(int pitch, double duration) {
		int durat;
		durat = (int) (duration * tpq / 4);

		if (pitch == 0)
			timeSinceLastNote += durat;
		else {
			sendLength(timeSinceLastNote);
			sendByte(NoteOn);
			sendByte((byte) pitch);
			sendByte(defaultVolume);

			sendLength(durat);
			sendByte(NoteOff);
			sendByte((byte) pitch);
			sendByte(defaultVolume);
			timeSinceLastNote = 0;
		}
	}

	void sendLength(int x) {
		if (x >= 2097152) {
			sendByte((byte) (128 + x / 2097152));
			x %= 2097152;
		}
		if (x >= 16384) {
			sendByte((byte) (128 + x / 16384));
			x %= 16384;
		}
		if (x >= 128) {
			sendByte((byte) (128 + x / 128));
			x %= 128;
		}
		sendByte((byte) x);
	}

	void sendByte(byte b) {
		track.write(b);
	}

	public static int getTone(String tone) {
		return toneValues.get(tone);
	}
}
