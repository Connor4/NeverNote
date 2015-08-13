package com.connor.frament;

import java.util.ArrayList;
import java.util.List;

import com.connor.activity.AddNoteActivity;
import com.connor.nevernote.R;
import com.connor.view.ArcMenu;
import com.connor.view.ArcMenu.OnArcMenuItemClickListener;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class AllNoteFragment extends Fragment
{
	private View allnoteview;
	private ArcMenu mArcMenu;

	private ListView mListView;
	private List<String> mDatas;

	public static AllNoteFragment getInstance()
	{
		AllNoteFragment mAllNoteFragment = new AllNoteFragment();
		return mAllNoteFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		allnoteview = inflater.inflate(R.layout.allnote_fragment, container,
				false);

		initData();
		initView();
		mListView.setAdapter(new ArrayAdapter<String>(getActivity()
				.getApplicationContext(), android.R.layout.simple_list_item_1,
				mDatas));

		initEvent();
		return allnoteview;
	}

	private void initEvent()
	{
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
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}).start();

					break;
				case 2:

					break;
				case 3:

					break;
				case 4:

					break;

				default:
					break;
				}
			}
		});
	}

	private void initData()
	{
		mDatas = new ArrayList<String>();

		for (int i = 'A'; i < 'Z'; i++)
		{
			mDatas.add((char) i + "");
		}

	}

	private void initView()
	{
		mListView = (ListView) allnoteview.findViewById(R.id.id_listview);
		mArcMenu = (ArcMenu) allnoteview.findViewById(R.id.arcmenu);
	}

}
