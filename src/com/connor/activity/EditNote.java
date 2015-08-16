package com.connor.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.connor.model.Note;
import com.connor.nevernote.R;
import com.connor.utils.GetPhoto;
import com.connor.utils.NeverNoteDB;
import com.connor.utils.PhotoWithCamUtil;

import android.app.Activity;
import android.content.ContentResolver;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditNote extends Activity
{
	private static final int PHOTO_WITH_GARLLEY = 1;
	private static final int PHOTO_WITH_CAMERA = 2;
	private String photoPath;
	private List<Bitmap> bitmapList = new ArrayList<Bitmap>();
	private Note parasenote;

	private ImageView mFinishView;
	private ImageView mFormatView;
	private ImageView mAttachVeiw;
	private ImageView mCamView;
	private ImageView mMoreView;
	private ImageView mReminderView;
	private EditText mTitle;
	private EditText mContent;
	private TextView mGroup;
	private String table="";

	private NeverNoteDB mNeverNoteDB;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newnote);

		initView();
		initData();
		initeEvent();

	}

	public void handlePath(String path)
	{
		if (photoPath.length() > 3)
		{
			int length = photoPath.length();
			photoPath = photoPath.substring(0, length - 1) + ", " + path + "]";
		} else
		{
			photoPath = "[" + path + "]";
		}
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

			case PHOTO_WITH_CAMERA:
				PhotoWithCamUtil mPhotoWithCamUtil = new PhotoWithCamUtil(
						getApplicationContext());
				Bitmap bitmap = mPhotoWithCamUtil.PhotoWithCam();
				if (bitmap != null)
				{
					handlePath(mPhotoWithCamUtil.getPhotoFilePath());
					// 根据Bitmap对象创建ImageSpan对象
					ImageSpan imageSpan = new ImageSpan(EditNote.this, bitmap);
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
					Toast.makeText(EditNote.this, "获取图片失败", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			default:
				break;
			}
		}
	}

	private void initeEvent()
	{
		mCamView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 这是调用相机
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 调用系统相机
				Uri imageUri = Uri.fromFile(new File(Environment
						.getExternalStorageDirectory(), "image.jpg"));
				// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				// 直接使用，没有缩小
				startActivityForResult(intent, PHOTO_WITH_CAMERA); // 用户点击了从相机获取
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
					// 太耗时，必须开新线程
					new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							Date date = new Date(System.currentTimeMillis());
							SimpleDateFormat dateFormat = new SimpleDateFormat(
									"yyyyMMdd_HHmmss");
							Note note = new Note(mTitle.getText().toString(),
									mContent.getText().toString(), photoPath,
									dateFormat.format(date), "", "");
							mNeverNoteDB.AddEditedNote(parasenote, note,table);
							finish();
						}
					}).start();
				}
			}
		});
	}

	private void initData()
	{
		GetPhoto mGetPhoto = new GetPhoto();
		parasenote = getIntent().getParcelableExtra("edit_object");
		table = getIntent().getStringExtra("table");
		
		mGroup.setText(table);
		mTitle.setText(parasenote.getNoteTitle());

		String path = photoPath = parasenote.getNotePic();
		bitmapList = mGetPhoto.getPhoto(path);
		mContent.setText(parasenote.getNoteContent().replaceAll("#", ""));
		addPic(parasenote.getNoteContent());
	}

	private void addPic(String content)
	{
		for (Iterator<Bitmap> it = bitmapList.iterator(); it.hasNext();)
		{
			ImageSpan imageSpan = new ImageSpan(EditNote.this, it.next());
			SpannableString spannableString = new SpannableString("#");
			spannableString.setSpan(imageSpan, 0, 1,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			int index = content.indexOf("#");
			Editable edit_text = mContent.getEditableText();
			
			if (index < 0 || index >= edit_text.length())
			{
				edit_text.append(spannableString);
			} else
			{
				edit_text.insert(index, spannableString);
			}
		}
	}

	private void initView()
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
	}
}
