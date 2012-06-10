package com.nerdinand.anstimm.entities;

import android.content.ContentValues;
import android.database.Cursor;

import com.nerdinand.anstimm.db.SongDB;
import com.nerdinand.anstimm.entities.Song.Voice;

public class SongDBConverter {
	public static ContentValues getContentValues(Song song) {
		ContentValues contentValues = new ContentValues();

		contentValues.put(SongDB.SongTable.COLUMN_CREATED_AT,
				song.getTimestamp());
		contentValues.put(SongDB.SongTable.COLUMN_TITLE, song.getTitle());
		contentValues.put(SongDB.SongTable.COLUMN_COMPOSER, song.getComposer());
		contentValues.put(SongDB.SongTable.COLUMN_ARRANGEMENT,
				song.getArrangement());
		contentValues.put(SongDB.SongTable.COLUMN_STATUS, song.getStatus());

		contentValues.put(SongDB.SongTable.COLUMN_SOPRANO1, song.getVoice(Voice.S1));
		contentValues.put(SongDB.SongTable.COLUMN_SOPRANO2, song.getVoice(Voice.S2));
		contentValues.put(SongDB.SongTable.COLUMN_ALTO1, song.getVoice(Voice.A1));
		contentValues.put(SongDB.SongTable.COLUMN_ALTO2, song.getVoice(Voice.A2));
		contentValues.put(SongDB.SongTable.COLUMN_TENOR1, song.getVoice(Voice.T1));
		contentValues.put(SongDB.SongTable.COLUMN_TENOR2, song.getVoice(Voice.T2));
		contentValues.put(SongDB.SongTable.COLUMN_BASS1, song.getVoice(Voice.B1));
		contentValues.put(SongDB.SongTable.COLUMN_BASS2, song.getVoice(Voice.B2));
		contentValues.put(SongDB.SongTable.COLUMN_SOLO1, song.getVoice(Voice.So1));
		contentValues.put(SongDB.SongTable.COLUMN_SOLO2, song.getVoice(Voice.So2));

		contentValues.put(SongDB.SongTable.COLUMN_KEY, song.getKey());
		contentValues.put(SongDB.SongTable.COLUMN_GENRE, song.getGenre());
		contentValues.put(SongDB.SongTable.COLUMN_EDITOR, song.getEditor());
		contentValues.put(SongDB.SongTable.COLUMN_COMMENT, song.getComment());

		return contentValues;
	}

	public static Song getSongFromCursor(Cursor cursor) {
		cursor.moveToFirst();

		Song song = new Song();

		song.setTimestamp(cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_CREATED_AT)));
		song.setTitle(cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_TITLE)));
		song.setComposer(cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_COMPOSER)));
		song.setArrangement(cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_ARRANGEMENT)));
		song.setStatus(cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_STATUS)));

		song.setVoice(Voice.S1, cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_SOPRANO1)));
		song.setVoice(Voice.S2, cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_SOPRANO2)));
		song.setVoice(Voice.A1, cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_ALTO1)));
		song.setVoice(Voice.A2, cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_ALTO2)));
		song.setVoice(Voice.T1, cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_TENOR1)));
		song.setVoice(Voice.T2, cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_TENOR2)));
		song.setVoice(Voice.B1, cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_BASS1)));
		song.setVoice(Voice.B2, cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_BASS2)));
		song.setVoice(Voice.So1, cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_SOLO1)));
		song.setVoice(Voice.So2, cursor.getString(cursor
				.getColumnIndex(SongDB.SongTable.COLUMN_SOLO2)));

		cursor.close();
		
		return song;
	}
}
