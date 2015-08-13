package com.connor.adapter;

import java.util.List;

import com.connor.nevernote.R;

import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteAdapter extends ArrayAdapter<Note>
{
	private int ResourceId;

	public NoteAdapter(Context context, int resource, List<Note> objects)
	{
		super(context, resource, objects);
		this.ResourceId = resource;
	}

	@Override
	public int getCount()
	{
		return super.getCount();
	}

	@Override
	public Note getItem(int position)
	{
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position)
	{
		return super.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Note note = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null)
		{
			view = LayoutInflater.from(getContext()).inflate(ResourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.mTitle = (TextView) view.findViewById(R.id.Note_Title);
			viewHolder.mDate = (TextView) view.findViewById(R.id.Note_Date);
			viewHolder.mContration = (TextView) view
					.findViewById(R.id.Note_Contration);
			viewHolder.mImage = (ImageView) view.findViewById(R.id.Note_Image);
			view.setTag(viewHolder);
		} else
		{
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
		}
		return view;
	}

	class ViewHolder
	{
		TextView mTitle;
		TextView mDate;
		TextView mContration;
		ImageView mImage;
	}

}
