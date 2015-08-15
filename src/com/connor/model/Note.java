package com.connor.model;

import java.util.Date;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable
{
	private String NoteTitle;
	private String NoteContent;
	private String NotePic;
	private String NoteDate;
	private String NoteAttach;
	private String NoteReminder;

	public Note()
	{
		super();
	}

	public Note(String noteTitle, String noteContent, String notePic,
			String noteDate,  String noteAttach, String noteReminder)
	{
		super();
		NoteTitle = noteTitle;
		NoteContent = noteContent;
		NotePic = notePic;
		NoteDate = noteDate;
		NoteAttach = noteAttach;
		NoteReminder = noteReminder;
	}
	

	public String getNoteAttach()
	{
		return NoteAttach;
	}

	public void setNoteAttach(String noteAttach)
	{
		NoteAttach = noteAttach;
	}

	public String getNoteTitle()
	{
		return NoteTitle;
	}

	public void setNoteTitle(String noteTitle)
	{
		NoteTitle = noteTitle;
	}

	public String getNoteContent()
	{
		return NoteContent;
	}

	public void setNoteContent(String noteContent)
	{
		NoteContent = noteContent;
	}

	public String getNotePic()
	{
		return NotePic;
	}

	public void setNotePic(String notePic)
	{
		NotePic = notePic;
	}

	public String getNoteDate()
	{
		return NoteDate;
	}

	public void setNoteDate(String noteDate)
	{
		NoteDate = noteDate;
	}

	public String getNoteReminder()
	{
		return NoteReminder;
	}

	public void setNoteReminder(String noteReminder)
	{
		NoteReminder = noteReminder;
	}

	public static final Parcelable.Creator<Note> CREATOR = new Creator<Note>()
	{
		public Note createFromParcel(Parcel source)
		{
			Note note = new Note();
			note.NoteTitle = source.readString();
			note.NoteContent = source.readString();
			note.NoteDate = source.readString();
			note.NotePic = source.readString();
			note.NoteReminder = source.readString();
			note.NoteAttach = source.readString();
			return note;
		}

		public Note[] newArray(int size)
		{
			return new Note[size];
		}
	};

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(NoteTitle);
		dest.writeString(NoteContent);
		dest.writeString(NoteDate);
		dest.writeString(NotePic);
		dest.writeString(NoteAttach);
		dest.writeString(NoteReminder);
	}

	@Override
	public String toString()
	{
		return "Note [NoteTitle=" + NoteTitle + ", NoteContent=" + NoteContent
				+ ", NotePic=" + NotePic + ", NoteDate=" + NoteDate
				+ ", NoteAttach=" + NoteAttach + ", NoteReminder="
				+ NoteReminder + "]";
	}


}
