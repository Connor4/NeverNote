package com.connor.activity;

import java.util.ArrayList;
import java.util.List;

import com.connor.adapter.NoteAdapter;
import com.connor.model.Note;
import com.connor.nevernote.R;
import com.connor.utils.NeverNoteDB;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class BooksNotes extends Activity
{
	private NeverNoteDB mNeverNoteDB;
	private ImageView back;
	private ImageView more;
	private ListView mListView;
	private NoteAdapter adapter;
	private List<Note> dataList = new ArrayList<Note>();
	private List<Note> temp = new ArrayList<Note>();
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.book2note);
		
		initView();
		initData();
		initEvent();
	}
	
	private void initEvent()
	{
		back.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				
				Bundle b = new Bundle();
				b.putString("table", getIntent().getStringExtra("table"));
				b.putParcelable("edit_object", temp.get(position));
				Intent intent = new Intent(BooksNotes.this, EditNote.class);
				intent.putExtras(b);
				startActivity(intent);
			}
		});
	}

	private void initData()
	{
		String s = getIntent().getStringExtra("table");
		temp = mNeverNoteDB.queryThisBook(s);
		if (temp.size() > 0)
		{
			for (int i = 0; i < temp.size(); i++)
			{
				Note note = new Note();
				String content = temp.get(i).getNoteContent();
				String contentresult = content.replaceAll("[\r\n]|#", "");
				String date = temp.get(i).getNoteDate();
				String dateresult = date.substring(0, 8);

				note.setNoteTitle(temp.get(i).getNoteTitle());
				note.setNoteContent(contentresult);
				note.setNoteDate(dateresult);

				dataList.add(note);
			}
		} else
		{
			Toast.makeText(getApplicationContext(), "还没有笔记，快去创建一条吧",
					Toast.LENGTH_SHORT).show();
		}
	}


	private void initView()
	{
		mListView = (ListView) findViewById(R.id.book2note_list);
		back = (ImageView) findViewById(R.id.book2note_back);
		more = (ImageView) findViewById(R.id.book2note_more);
		adapter = new NoteAdapter(getApplicationContext(), R.layout.note_item, dataList);
		mListView.setAdapter(adapter);
		mNeverNoteDB = NeverNoteDB.getInstance(getApplicationContext());
	}

}
