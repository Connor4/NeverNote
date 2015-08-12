package com.connor.adapter;

import java.util.List;

import com.connor.model.DrawerListItem;
import com.connor.nevernote.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerItemAdapter extends ArrayAdapter<DrawerListItem>
{
	private int mResourceId;
	private ImageView mImageView;

	public DrawerItemAdapter(Context context, int resource,
			List<DrawerListItem> objects)
	{
		super(context, resource, objects);
		this.mResourceId = resource;
	}

	@Override
	public int getCount()
	{
		return super.getCount();
	}

	@Override
	public DrawerListItem getItem(int position)
	{
		return super.getItem(position);
	}

	@Override
	public int getPosition(DrawerListItem item)
	{
		return super.getPosition(item);
	}

	@Override
	public long getItemId(int position)
	{
		return super.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		DrawerListItem item = getItem(position);
		View view = LayoutInflater.from(getContext())
				.inflate(mResourceId, null);
		mImageView = (ImageView) view.findViewById(R.id.list_item_iv);
		TextView mTextView = (TextView) view.findViewById(R.id.list_item_name);
		mImageView.setImageResource(item.getmImageViewId());
		mTextView.setText(item.getmText());
		return view;
	}
}
