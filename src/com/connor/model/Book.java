package com.connor.model;

public class Book
{
	private int id;
	private String bookname;
	private String bookcount;

	public Book(int id, String bookname, String bookcount)
	{
		super();
		this.id = id;
		this.bookname = bookname;
		this.bookcount = bookcount;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getBookname()
	{
		return bookname;
	}

	public void setBookname(String bookname)
	{
		this.bookname = bookname;
	}

	public String getBookcount()
	{
		return bookcount;
	}

	public void setBookcount(String bookcount)
	{
		this.bookcount = bookcount;
	}

}
