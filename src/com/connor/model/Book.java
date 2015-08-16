package com.connor.model;

public class Book
{
	private int id;
	private String bookname;
	private int bookcount;

	public Book()
	{
		super();
	}

	public Book(int id, String bookname, int bookcount)
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

	public int getBookcount()
	{
		return bookcount;
	}

	public void setBookcount(int bookcount)
	{
		this.bookcount = bookcount;
	}

}
