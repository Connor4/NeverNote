package com.connor.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NeverNoteopenHelper extends SQLiteOpenHelper
{
	public static final String CREATE_ALLBOOK = "create table allbook("//
			+ "id integer primary key autoincrement,"//
			+ "book_name text,"//
			+ "note_count integer)";
	
	public static final String CREATE_BOOK = "create table book("//
			+"id integer primary key autoincrement,"//
			+"title text,"//
			+"content text,"//
			+"reminder text,"//
			+"fid integer,"//
			+"FOREIGN KEY(fid) REFERENCES allbook(id))";

	public NeverNoteopenHelper(Context context, String name,
			CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_ALLBOOK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{

	}

}
