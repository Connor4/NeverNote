package com.connor.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

public class PhotoWithCamUtil
{
	/**
	 * 存放照片的目录
	 */
	private static final String filePath = Environment
			.getExternalStorageDirectory().getPath() + "/NeverNote/";
	/**
	 * 照片名
	 */
	private String imgFileName = "";
	/**
	 * 照片存放全路径，用于找的时候
	 */
	private String imgFilePath = "";
	private Context context;
	private Bitmap bitmap;

	public PhotoWithCamUtil(Context context)
	{
		this.context = context;
	}

	public Bitmap PhotoWithCam()
	{
		String status = Environment.getExternalStorageState();
		Bitmap smallbitmap = null;
		if (status.equals(Environment.MEDIA_MOUNTED))
		{ // 将照片保存到
			bitmap = BitmapFactory.decodeFile(Environment
					.getExternalStorageDirectory() + "/image.jpg");
			imgFileName = createPhotoFileName();
			
			File path = new File(filePath);
			if (!path.exists())
			{
				path.mkdirs();
			}

			File f = new File(filePath, imgFileName);
			savePic(context, f, bitmap);

			if (bitmap != null)
			{
				smallbitmap = zoomBitmap(bitmap, bitmap.getWidth() / 5,
						bitmap.getHeight() / 5);
				// 回收bitmap
				bitmap.recycle();
			}
		}
		return smallbitmap;
	}

	/**
	 * 创建图片不同的文件名
	 */
	private String createPhotoFileName()
	{
		String imgName = "";
		Date date = new Date(System.currentTimeMillis()); // 系统当前时间
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		imgName = dateFormat.format(date) + ".jpg";
		imgFilePath = filePath + imgName;
		return imgName;
	}

	/**
	 * 保存图片到本应用目录
	 */
	private void savePic(Context context, File f, Bitmap bitmap)
	{
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(f);
			// 存入照片到指定文件夹
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} finally
		{
			if (fos != null)
			{
				try
				{
					fos.close();
					fos = null;
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 缩放图片
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height)
	{
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * 获取图片名字
	 */
	public String getPhotoFilePath()
	{
		return imgFilePath;
	}
}
