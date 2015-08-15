package com.connor.utils;

import java.io.File;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.connor.application.NeverNoteApplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class GetPhoto
{
	private NeverNoteApplication mApplication;
	private PhotoWithCamUtil mPhotoWithCamUtil = new PhotoWithCamUtil(
			mApplication.getContext());

	public List<Bitmap> getPhoto(String path)
	{
		List<Bitmap> list = new ArrayList<Bitmap>();
		Bitmap bitmap;
		Bitmap smallbitmap;
		String newpath = path.replaceAll("[\\[|\\]]", "");
		int times = newpath.length() / 53;
		for (int i = 0; i < times; i++)
		{
			bitmap = getDiskBitmap(newpath.substring(i * 55, 53 + i * 55));
			smallbitmap = mPhotoWithCamUtil.zoomBitmap(bitmap,
					bitmap.getWidth() / 5, bitmap.getHeight() / 5);
			bitmap.recycle();
			list.add(smallbitmap);
		}
		return list;
	}

	private Bitmap getDiskBitmap(String pathString)
	{
		Bitmap bitmap = null;
		try
		{
			File file = new File(pathString);
			if (file.exists())
			{
				bitmap = BitmapFactory.decodeFile(pathString);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return bitmap;
	}

}
