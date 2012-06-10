package com.nerdinand.anstimm.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.nerdinand.anstimm.entities.Song;
import com.nerdinand.anstimm.entities.SongDBConverter;

public class SongDB extends SQLiteOpenHelper {

	private static SongDB instance = null;

	private final String createSongTableSQL = "CREATE TABLE "
			+ SongTable.TABLE
			+ " (_id INTEGER PRIMARY KEY, created_at NUMERIC, title TEXT, composer TEXT, arrangement TEXT, status TEXT, soprano1 TEXT, soprano2 TEXT, alto1 TEXT, alto2 TEXT, tenor1 TEXT, tenor2 TEXT, bass1 TEXT, bass2 TEXT, solo1 TEXT, solo2 TEXT, key TEXT, genre TEXT, editor TEXT, comment TEXT);";

	public final static int DB_VERSION = 1;

	private static final String SONG_DB_FILENAME = "anstimm.sqlite";

	public static class SongTable {
		public final static String TABLE = "songs";

		public final static String COLUMN_ID = "_id";
		public final static String COLUMN_CREATED_AT = "created_at";

		public final static String COLUMN_TITLE = "title";
		public final static String COLUMN_COMPOSER = "composer";
		public final static String COLUMN_ARRANGEMENT = "arrangement";
		public final static String COLUMN_STATUS = "status";

		public final static String COLUMN_SOPRANO1 = "soprano1";
		public final static String COLUMN_SOPRANO2 = "soprano2";
		public final static String COLUMN_ALTO1 = "alto1";
		public final static String COLUMN_ALTO2 = "alto2";
		public final static String COLUMN_TENOR1 = "tenor1";
		public final static String COLUMN_TENOR2 = "tenor2";
		public final static String COLUMN_BASS1 = "bass1";
		public final static String COLUMN_BASS2 = "bass2";
		public final static String COLUMN_SOLO1 = "solo1";
		public final static String COLUMN_SOLO2 = "solo2";

		public final static String COLUMN_KEY = "key";
		public final static String COLUMN_GENRE = "genre";
		public final static String COLUMN_EDITOR = "editor";
		public final static String COLUMN_COMMENT = "comment";

		public final static String ORDER_COLUMN = COLUMN_TITLE;

		public final static String[] COLUMNS = new String[] { COLUMN_ID,
				COLUMN_CREATED_AT,

				COLUMN_TITLE, COLUMN_COMPOSER, COLUMN_ARRANGEMENT,
				COLUMN_STATUS,

				COLUMN_SOPRANO1, COLUMN_SOPRANO2, COLUMN_ALTO1, COLUMN_ALTO2,
				COLUMN_TENOR1, COLUMN_TENOR2, COLUMN_BASS1, COLUMN_BASS2,
				COLUMN_SOLO1, COLUMN_SOLO2,

				COLUMN_KEY, COLUMN_GENRE, COLUMN_EDITOR, COLUMN_COMMENT };
		
	}

	public static SongDB getInstance(Context context) {
		if (instance == null) {
			instance = new SongDB(context, SONG_DB_FILENAME, null);
		}

		return instance;
	}

	public SongDB(Context context, String name, CursorFactory factory) {
		super(context, name, factory, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createSongTableSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Do something intelligent here...

		// throw new RuntimeException("No strategy to migrate from " +
		// oldVersion + " to " + newVersion);
	}

	public Cursor getSongs() {
		return this.getReadableDatabase().query(SongTable.TABLE,
				SongTable.COLUMNS, null, null, null, null,
				SongTable.ORDER_COLUMN);
	}

	public int deleteSongs() {
		return this.getWritableDatabase().delete(SongTable.TABLE, null, null);
	}

	public void insertSong(Song song) {
		this.getWritableDatabase().insertOrThrow(SongTable.TABLE, null,
				SongDBConverter.getContentValues(song));
	}

	public Cursor getSong(long id) {
		return this.getReadableDatabase().query(SongTable.TABLE,
				SongTable.COLUMNS, SongTable.COLUMN_ID+" = ?", new String[]{""+id}, null, null, null);
	}
}
