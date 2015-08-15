package com.connor.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.connor.model.Note;
import com.connor.nevernote.R;
import com.connor.utils.GetPhoto;
import com.connor.utils.PhotoWithCamUtil;
import com.connor.view.ArcMenu;
import com.connor.view.ArcMenu.OnArcMenuItemClickListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ViewNote extends Activity
{
	private ArcMenu mArcMenu;
	private Note note;
	private EditText mTitle;
	private EditText mContent;
	private ImageView mBack;
	private ImageView mShare;
	private ImageView mMore;
	private ImageView mReminder;
	private List<Bitmap> bitmapList = new ArrayList<Bitmap>();

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.viewnote);

		initView();
		initData();
		initeEvent();

	}

	private void initData()
	{
		GetPhoto mGetPhoto = new GetPhoto();
		note = getIntent().getParcelableExtra("object");

		mTitle.setText(note.getNoteTitle());

		String path = note.getNotePic();
		bitmapList = mGetPhoto.getPhoto(path);
		mContent.setText(note.getNoteContent().replaceAll("#", ""));
		addPic(note.getNoteContent());

		// 设置不可编辑
		mTitle.setFocusable(false);
		mContent.setFocusable(false);
	}

	private void addPic(String content)
	{
		for (Iterator<Bitmap> it = bitmapList.iterator(); it.hasNext();)
		{
			// 根据Bitmap对象创建ImageSpan对象
			ImageSpan imageSpan = new ImageSpan(ViewNote.this, it.next());
			// 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
			SpannableString spannableString = new SpannableString("#");
			// 用ImageSpan对象替换face
			spannableString.setSpan(imageSpan, 0, 1,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// 将选择的图片追加到EditText中光标所在位置
			// 获取#所在位置
			int index = content.indexOf("#");
			content.replace('#', ' ');
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

	private void initeEvent()
	{
		mArcMenu.setOnArcMenuItemClickListener(new OnArcMenuItemClickListener()
		{
			@Override
			public void onClick(View view, int position)
			{

			}

			@Override
			public void MainClick(View view)
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						try
						{
							Thread.sleep(300);
							Bundle b = new Bundle();
							b.putParcelable("edit_object", note);
							Intent intent = new Intent(getApplicationContext(),
									EditNote.class);
							intent.putExtras(b);
							startActivity(intent);
							finish();
						} catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();

			}
		});

		mBack.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

		mReminder.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

			}
		});

		mShare.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
			}
		});

		mMore.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

			}
		});
	}

	private void initView()
	{
		mTitle = (EditText) findViewById(R.id.viewnote_et_title);
		mContent = (EditText) findViewById(R.id.viewnote_et_content);
		mBack = (ImageView) findViewById(R.id.viewnote_back);
		mShare = (ImageView) findViewById(R.id.viewnote_share);
		mMore = (ImageView) findViewById(R.id.viewnote_more);
		mReminder = (ImageView) findViewById(R.id.viewnote_reminder_iv);
		mArcMenu = (ArcMenu) findViewById(R.id.viewnote_arc);
	}

}
