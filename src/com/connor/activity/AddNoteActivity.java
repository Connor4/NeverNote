package com.connor.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.connor.model.Note;
import com.connor.nevernote.R;
import com.connor.utils.NeverNoteDB;
import com.connor.utils.PhotoWithCamUtil;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddNoteActivity extends Activity
{
	private static final int PHOTO_WITH_GARLLEY = 1;
	private static final int PHOTO_WITH_CAMERA = 2;
	/**
	 * 用于存放相片的路径
	 */
	private List<String> photoPath;

	private ImageView mFinishView;
	private ImageView mFormatView;
	private ImageView mAttachVeiw;
	private ImageView mCamView;
	private ImageView mMoreView;
	private ImageView mReminderView;
	private EditText mTitle;
	private EditText mContent;
	private TextView mGroup;

	private NeverNoteDB mNeverNoteDB;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newnote);

		initContent();
		initListener();
	}

	private void initContent()
	{
		mFinishView = (ImageView) findViewById(R.id.newnote_finish);
		mFormatView = (ImageView) findViewById(R.id.newnote_format);
		mAttachVeiw = (ImageView) findViewById(R.id.newnote_attach_iv);
		mCamView = (ImageView) findViewById(R.id.newnote_photo);
		mMoreView = (ImageView) findViewById(R.id.newnote_more);
		mTitle = (EditText) findViewById(R.id.newnote_et_title);
		mContent = (EditText) findViewById(R.id.newnote_et_content);
		mGroup = (TextView) findViewById(R.id.newnote_group);
		mNeverNoteDB = NeverNoteDB.getInstance(this);
		photoPath = new ArrayList<String>();
	}

	private void initListener()
	{
		mCamView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				/**
				 * 这是调用相机
				 */
				// Intent getImageByCamera = new Intent(
				// "android.media.action.IMAGE_CAPTURE");
				// startActivityForResult(getImageByCamera, CAMERA_SUCCESS);

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 调用系统相机
				Uri imageUri = Uri.fromFile(new File(Environment
						.getExternalStorageDirectory(), "image.jpg"));
				// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				// 直接使用，没有缩小
				startActivityForResult(intent, PHOTO_WITH_CAMERA); // 用户点击了从相机获取
				/**
				 * 这是调用相册的，给附件用
				 */
				// Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
				// getImage.addCategory(Intent.CATEGORY_OPENABLE);
				// getImage.setType("image/*");
				// startActivityForResult(getImage, PHOTO_SUCCESS);

			}
		});

		mFinishView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				if (TextUtils.isEmpty(mTitle.getText().toString())
						&& TextUtils.isEmpty(mContent.getText().toString()))
				{
					Toast.makeText(getApplicationContext(), "不能保存一条空笔记",
							Toast.LENGTH_SHORT).show();
					finish();
				} else
				{
					Date date = new Date(System.currentTimeMillis());
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyyMMdd_HHmmss");
					Note note = new Note(mTitle.getText().toString(), mContent
							.getText().toString(), photoPath.toString(),
							dateFormat.format(date), "", "");
					mNeverNoteDB.AddNewNote(note);
					finish();
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent)
	{
		ContentResolver resolver = getContentResolver();
		if (resultCode == RESULT_OK)
		{
			switch (requestCode)
			{
			/*
			 * case PHOTO_SUCCESS: // 获得图片的uri Uri originalUri =
			 * intent.getData(); Bitmap bitmap = null; try { Bitmap
			 * originalBitmap = BitmapFactory.decodeStream(resolver
			 * .openInputStream(originalUri)); bitmap =
			 * resizeImage(originalBitmap, 200, 200); } catch
			 * (FileNotFoundException e) { e.printStackTrace(); } if (bitmap !=
			 * null) { // 根据Bitmap对象创建ImageSpan对象 ImageSpan imageSpan = new
			 * ImageSpan(NewTopic.this, bitmap); //
			 * 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像 SpannableString
			 * spannableString = new SpannableString( "[local]" + 1 +
			 * "[/local]"); // 用ImageSpan对象替换face
			 * spannableString.setSpan(imageSpan, 0, "[local]1[local]".length()
			 * + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //
			 * 将选择的图片追加到EditText中光标所在位置 int index =
			 * mContent.getSelectionStart(); // 获取光标所在位置 Editable edit_text =
			 * mContent.getEditableText(); if (index < 0 || index >=
			 * edit_text.length()) { edit_text.append(spannableString); } else {
			 * edit_text.insert(index, spannableString); } } else {
			 * Toast.makeText(NewTopic.this, "获取图片失败", Toast.LENGTH_SHORT)
			 * .show(); } break;
			 */
			case PHOTO_WITH_CAMERA:
				PhotoWithCamUtil mPhotoWithCamUtil = new PhotoWithCamUtil(
						getApplicationContext());
				Bitmap bitmap = mPhotoWithCamUtil.PhotoWithCam();
				if (bitmap != null)
				{
					photoPath.add(mPhotoWithCamUtil.getPhotoFilePath());
					// 根据Bitmap对象创建ImageSpan对象
					ImageSpan imageSpan = new ImageSpan(AddNoteActivity.this,
							bitmap);
					// 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
					SpannableString spannableString = new SpannableString("#");
					// 用ImageSpan对象替换face
					spannableString.setSpan(imageSpan, 0, 1,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					// 将选择的图片追加到EditText中光标所在位置
					int index = mContent.getSelectionStart(); // 获取光标所在位置
					Editable edit_text = mContent.getEditableText();
					if (index < 0 || index >= edit_text.length())
					{
						edit_text.append(spannableString);
					} else
					{
						edit_text.insert(index, spannableString);
					}
				} else
				{
					Toast.makeText(AddNoteActivity.this, "获取图片失败",
							Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
			}
		}
	}

}
