package com.nerdinand.anstimm.entities;

import java.util.Comparator;

public class SongComparator implements Comparator<Song> {
	@Override
	public int compare(Song lhs, Song rhs) {
		return lhs.getTitle().compareTo(rhs.getTitle());
	}

}
