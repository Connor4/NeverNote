package com.connor.activity;

import java.util.ArrayList;
import java.util.List;

import com.connor.adapter.DrawerItemAdapter;
import com.connor.application.NeverNoteApplication;
import com.connor.model.DrawerListItem;
import com.connor.nevernote.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private NeverNoteApplication mApplication;
	private DrawerLayout mDrawerLayout;
	private LinearLayout mLinearDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private DrawerItemAdapter adapter;
	private List<DrawerListItem> items;

	private int currentSelectedPosition = 0;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] itemText;
	private int[] itemIv = { R.drawable.drawer_allnote_pressed,
			R.drawable.drawer_book_default, R.drawable.drawer_setting };
	private ImageView mDrawerUserImage;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getActionBar().setIcon(R.drawable.actionbar_icon);
		mTitle = mDrawerTitle = getTitle();
		itemText = getResources().getStringArray(R.array.drawer_list);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		mLinearDrawerLayout = (LinearLayout) findViewById(R.id.linearDrawer);
		mDrawerUserImage = (ImageView) findViewById(R.id.drawerUserImage);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		items = getListItem();
		adapter = new DrawerItemAdapter(this, R.layout.drawer_item, items);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new OnListItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close)
		{
			@Override
			public void onDrawerClosed(View drawerView)
			{
				super.onDrawerClosed(drawerView);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView)
			{
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerUserImage.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(mApplication.getContext(), "click user image",
						Toast.LENGTH_SHORT).show();
			}
		});

		selectItem(currentSelectedPosition);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private List<DrawerListItem> getListItem()
	{
		ArrayList<DrawerListItem> list = new ArrayList<DrawerListItem>();
		for (int i = 0; i < 3; i++)
		{
			list.add(new DrawerListItem(itemText[i], itemIv[i]));
		}
		return list;
	}

	private class OnListItemClickListener implements
			android.widget.AdapterView.OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			if (mDrawerLayout.isDrawerOpen(mLinearDrawerLayout))
			{
				mDrawerLayout.closeDrawer(mLinearDrawerLayout);
				onNavigationDrawerItemSelected(position);
				selectItem(position);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (mDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		} else if (item.getItemId() == R.id.action_allnote_localsearch)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void selectItem(int position)
	{
		if (mDrawerList != null)
		{
			mDrawerList.setItemChecked(position, true);
			items.get(currentSelectedPosition).setSelected(false);
			items.get(position).setSelected(true);

			currentSelectedPosition = position;
			getActionBar().setTitle(items.get(position).getmText());
		}

		if (mLinearDrawerLayout != null)
		{
			mDrawerLayout.closeDrawer(mLinearDrawerLayout);
		}
	}

	private void onNavigationDrawerItemSelected(int position)
	{
		switch (position)
		{
		case 0:
			// NoteFrament fragment = new NoteFrament();
			// getFragmentManager().beginTransaction()
			// .replace(R.id.contentFrame, fragment).commit();
			Toast.makeText(getApplicationContext(), "case 0",
					Toast.LENGTH_SHORT).show();
			break;
		case 1:
			Toast.makeText(getApplicationContext(), "case 1",
					Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(getApplicationContext(), "case 2",
					Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(getApplicationContext(), "case 3",
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;

		}
	}

}
