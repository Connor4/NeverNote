package com.connor.frament;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.connor.activity.AddNoteActivity;
import com.connor.activity.ViewNote;
import com.connor.adapter.NoteAdapter;
import com.connor.application.NeverNoteApplication;
import com.connor.model.Note;
import com.connor.nevernote.R;
import com.connor.utils.NeverNoteDB;
import com.connor.view.ArcMenu;
import com.connor.view.ArcMenu.OnArcMenuItemClickListener;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;

public class AllNoteFragment extends Fragment implements
		SwipeRefreshLayout.OnRefreshListener, OnItemClickListener
{
	private View allnoteview;
	private ArcMenu mArcMenu;
	private NeverNoteDB mNeverNoteDB;
	private NeverNoteApplication mApplication;
	private NoteAdapter adapter;
	private List<Note> dataList = new ArrayList<Note>();
	private List<Note> temp = new ArrayList<Note>();
	private ListView mListView;
	private static final int REFRESH_COMPLETE = 0X110;
	private SwipeRefreshLayout mSwipeLayout;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case REFRESH_COMPLETE:
				// 需要判断有没有新的数据
				// initData();
				adapter.notifyDataSetChanged();
				mSwipeLayout.setRefreshing(false);
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		allnoteview = inflater.inflate(R.layout.allnote_fragment, container,
				false);

		initView();
		initData();
		initEvent();
		return allnoteview;
	}

	private void initEvent()
	{
		mListView.setOnItemClickListener(this);
		mListView.setOnScrollListener(new OnScrollListener()
		{

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount)
			{
				if (mArcMenu.isOpen())
					mArcMenu.ToggleMenu(300);
			}
		});

		mArcMenu.setOnArcMenuItemClickListener(new OnArcMenuItemClickListener()
		{
			@Override
			public void onClick(View view, int pos)
			{
				switch (pos)
				{
				case 1:
					new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								Thread.sleep(250);
								Intent intent = new Intent(getActivity(),
										AddNoteActivity.class);
								startActivity(intent);
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					}).start();

					break;
				case 4:
					new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								Thread.sleep(250);
								Intent intent = new Intent(getActivity(),
										AddNoteActivity.class);
								intent.putExtra("CallForCam", true);
								startActivity(intent);
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					}).start();

					break;
				default:
					break;
				}
			}

			@Override
			public void MainClick(View view)
			{

			}
		});
	}

	private void initData()
	{
		temp = mNeverNoteDB.loadNotes();
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
			Toast.makeText(mApplication.getContext(), "还没有笔记，快去创建一条吧",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void initView()
	{
		mListView = (ListView) allnoteview.findViewById(R.id.id_listview);
		mArcMenu = (ArcMenu) allnoteview.findViewById(R.id.arcmenu);
		mNeverNoteDB = NeverNoteDB.getInstance(getActivity()
				.getApplicationContext());
		mSwipeLayout = (SwipeRefreshLayout) allnoteview
				.findViewById(R.id.id_swipe_ly);

		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_green_dark,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		adapter = new NoteAdapter(mApplication.getContext(),
				R.layout.note_item, dataList);
		mListView.setAdapter(adapter);
	}

	public void onRefresh()
	{
		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		Note note = new Note();
		note.setNoteTitle(temp.get(position).getNoteTitle());
		note.setNoteContent(temp.get(position).getNoteContent());
		note.setNoteDate(temp.get(position).getNoteDate());
		note.setNotePic(temp.get(position).getNotePic());
		note.setNoteReminder(temp.get(position).getNoteReminder());

		Bundle arg = new Bundle();
		arg.putParcelable("object", note);
		Intent intent = new Intent(mApplication.getContext(), ViewNote.class);
		intent.putExtras(arg);
		startActivity(intent);
	}

}
