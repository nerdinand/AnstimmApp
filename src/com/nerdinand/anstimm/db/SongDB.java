package com.nerdinand.anstimm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SongDB extends SQLiteOpenHelper {

	private final String createSongTableSQL = "CREATE TABLE songs (id INTEGER PRIMARY KEY, created_at NUMERIC, title TEXT, composer TEXT, arrangement TEXT, status TEXT, soprano1 TEXT, soprano2 TEXT, alto1 TEXT, alto2 TEXT, tenor1 TEXT, tenor2 TEXT, bass1 TEXT, bass2 TEXT, solo1 TEXT, solo2 TEXT, key TEXT, genre TEXT, editor TEXT, comment TEXT);";

	public SongDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createSongTableSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		throw new RuntimeException("No strategy to migrate from " + oldVersion + " to " + newVersion);
	}

}
