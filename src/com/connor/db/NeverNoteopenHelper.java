package com.connor.db;

import android.content.ContentValues;
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
			+ "book_count integer default (0))";

	public static final String CREATE_DEFAULTBOOK = "create table defaultbook("//
			+ "id integer primary key autoincrement,"//
			+ "title text,"//
			+ "content text,"//
			+ "date text,"//
			+ "path text,"//
			+ "attach text,"//
			+ "reminder text)";

	public NeverNoteopenHelper(Context context, String name,
			CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_ALLBOOK);
		db.execSQL(CREATE_DEFAULTBOOK);
		// 默认添加"默认笔记本"
		ContentValues values = new ContentValues();
		values.put("book_name", "defaultbook");
		db.insert("allbook", null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{

	}

}
