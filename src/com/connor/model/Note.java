package com.connor.model;

public class Note
{
	private String NoteTitle;
	private int NoteDate;
	private String NoteContration;
	private int NoteImage;

	public Note(String noteTitle, int noteDate, String noteContration,
			int noteImage)
	{
		super();
		NoteTitle = noteTitle;
		NoteDate = noteDate;
		NoteContration = noteContration;
		NoteImage = noteImage;
	}

	public String getNoteTitle()
	{
		return NoteTitle;
	}

	public void setNoteTitle(String noteTitle)
	{
		NoteTitle = noteTitle;
	}

	public int getNoteDate()
	{
		return NoteDate;
	}

	public void setNoteDate(int noteDate)
	{
		NoteDate = noteDate;
	}

	public String getNoteContration()
	{
		return NoteContration;
	}

	public void setNoteContration(String noteContration)
	{
		NoteContration = noteContration;
	}

	public int getNoteImage()
	{
		return NoteImage;
	}

	public void setNoteImage(int noteImage)
	{
		NoteImage = noteImage;
	}

}
