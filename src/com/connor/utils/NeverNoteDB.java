package com.connor.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.connor.db.NeverNoteopenHelper;
import com.connor.model.Book;
import com.connor.model.Note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NeverNoteDB
{
	public static final String DB_NAME = "never_note";
	public static final int VERSION = 1;
	private static NeverNoteDB mNeverNoteDB;
	private SQLiteDatabase db;

	/**
	 * 用于新建笔记本
	 */
	public static final String CREATE_NEWBOOK = " ("//
			+ "id integer primary key autoincrement,"//
			+ "title text,"//
			+ "content text,"//
			+ "reminder text,"//
			+ "fid integer)";

	private NeverNoteDB(Context context)
	{
		NeverNoteopenHelper dbHelper = new NeverNoteopenHelper(context,
				DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();

	}

	public synchronized static NeverNoteDB getInstance(Context context)
	{
		if (mNeverNoteDB == null)
		{
			mNeverNoteDB = new NeverNoteDB(context);
		}
		return mNeverNoteDB;
	}

	/**
	 * 用于添加新的笔记本组
	 * 
	 * @param title
	 *            笔记本名
	 */
	public void CreateNewBook(String title)
	{
		String newTable = "create table " + title + CREATE_NEWBOOK;
		db.execSQL(newTable);
	}

	/**
	 * 往笔记本添加一条新记录
	 * 
	 * @param note
	 */
	public void AddNewNote(Note note, String table)
	{
		ContentValues values = new ContentValues();
		values.put("title", note.getNoteTitle());
		values.put("content", note.getNoteContent());
		values.put("date", note.getNoteDate());
		values.put("path", note.getNotePic());
		values.put("attach", note.getNoteAttach());
		values.put("reminder", note.getNoteReminder());
		if(table.equals("")){
			table = "defaultbook";
		}
		db.insert(table, null, values);
		int count = getCount(table)+1;
		updateCount(table, count);
	}


	/**
	 * 获取所有笔记
	 */
	public List<Note> loadNotes()
	{
		List<Note> notes = new ArrayList<Note>();
		List<String> books = new ArrayList<String>();
		books = this.queryAllBook();

		for (Iterator<String> it = books.iterator(); it.hasNext();)
		{
			Cursor notescursor = db.query(it.next(), null, null, null, null,
					null, null);
			if (notescursor.moveToFirst())
			{
				do
				{
					Note note = new Note();
					note.setNoteTitle(notescursor.getString(notescursor
							.getColumnIndex("title")));
					note.setNoteContent(notescursor.getString(notescursor
							.getColumnIndex("content")));
					note.setNoteDate(notescursor.getString(notescursor
							.getColumnIndex("date")));
					note.setNotePic(notescursor.getString(notescursor
							.getColumnIndex("path")));
					note.setNoteReminder(notescursor.getString(notescursor
							.getColumnIndex("reminder")));
					notes.add(note);
				} while (notescursor.moveToNext());
			}
		}
		return notes;
	}

	/**
	 * 添加一条修改过的笔记，需要删除旧的那条
	 * 
	 * @param oldNote
	 * @param newNote
	 */
	public void AddEditedNote(Note oldNote, Note newNote,String t)
	{
		List<String> books = new ArrayList<String>();
		String time = oldNote.getNoteDate();
		books = queryAllBook();

		for (Iterator<String> it = books.iterator(); it.hasNext();)
		{
			String table = it.next();
			Cursor notescursor = db.query(table, null, null, null, null, null,
					null);
			if (notescursor.moveToFirst())
			{
				do
				{
					String temp = notescursor.getString(notescursor
							.getColumnIndex("date"));

					if (time.equals(temp))
					{
						db.delete(table, "date = ?", new String[] { time });
						break;
					}

				} while (notescursor.moveToNext());
			}
		}
		this.AddNewNote(newNote, t);
	}

	/**
	 * 获取全部笔记本，以Book对象的List返回
	 */
	public List<Book> getAllBook()
	{
		List<Book> getallbook = new ArrayList<Book>();
		Cursor cursor = db.query("allbook", null, null, null, null, null, null);
		if (cursor.moveToFirst())
		{
			do
			{
				Book book = new Book();
				book.setId(cursor.getInt(cursor.getColumnIndex("id")));
				book.setBookname(cursor.getString(cursor
						.getColumnIndex("book_name")));
				book.setBookcount(cursor.getInt(cursor
						.getColumnIndex("book_count")));
				getallbook.add(book);
			} while (cursor.moveToNext());
		}
		return getallbook;
	}

	public List<Note> queryThisBook(String table)
	{
		List<Note> list = new ArrayList<Note>();
		Cursor cursor = db.query(table, null, null, null, null, null, null);
		if (cursor.moveToFirst())
		{
			do
			{
				Note note = new Note();
				note.setNoteTitle(cursor.getString(cursor
						.getColumnIndex("title")));
				note.setNoteContent(cursor.getString(cursor
						.getColumnIndex("content")));
				note.setNoteDate(cursor.getString(cursor.getColumnIndex("date")));
				note.setNotePic(cursor.getString(cursor.getColumnIndex("path")));
				note.setNoteReminder(cursor.getString(cursor
						.getColumnIndex("reminder")));
				list.add(note);
			} while (cursor.moveToNext());
		}
		return list;
	}

	public List<String> queryAllBook()
	{
		List<String> books = new ArrayList<String>();
		Cursor bookcursor = db.query("allbook", null, null, null, null, null,
				null);
		if (bookcursor.moveToFirst())
		{
			do
			{
				books.add(bookcursor.getString(bookcursor
						.getColumnIndex("book_name")));
			} while (bookcursor.moveToNext());
		}
		return books;
	}

	public int getCount(String table)
	{
		int count = 0;
		Cursor cursor = db.query("allbook", new String[] { "book_count" },
				"book_name = ?", new String[] { table }, null, null, null);
		if(cursor.moveToFirst())
		{
			do
			{
				count = cursor.getInt(cursor.getColumnIndex("book_count"));
			} while (cursor.moveToNext());
		}
		return count;
	}
	

	private void updateCount(String table, int i)
	{
		ContentValues values = new ContentValues();
		values.put("book_count", i);
		db.update("allbook", values, "book_name = ?", new String[]{table});
	}

}
