package com.connor.model;

public class DrawerListItem
{
	private int mImageViewId;
	private String mText;
	private boolean mainItem;
	private boolean selected;

	public DrawerListItem( String mText,int mImageViewId)
	{
		this.mImageViewId = mImageViewId;
		this.mText = mText;
	}

	public boolean isMainItem()
	{
		return mainItem;
	}

	public void setMainItem(boolean mainItem)
	{
		this.mainItem = mainItem;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public int getmImageViewId()
	{
		return mImageViewId;
	}

	public void setmImageViewId(int mImageViewId)
	{
		this.mImageViewId = mImageViewId;
	}

	public String getmText()
	{
		return mText;
	}

	public void setmText(String mText)
	{
		this.mText = mText;
	}

}
