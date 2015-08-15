package com.connor.adapter;

import java.util.List;

import com.connor.model.Note;
import com.connor.nevernote.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteAdapter extends ArrayAdapter<Note>
{
	private int ResourceId;
	private Context context;

	public NoteAdapter(Context context, int resource, List<Note> note)
	{
		super(context, resource, note);
		this.ResourceId = resource;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Note note = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null)
		{
			view = LayoutInflater.from(context).inflate(ResourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.mTitle = (TextView) view.findViewById(R.id.Note_Title);
			viewHolder.mDate = (TextView) view.findViewById(R.id.Note_Date);
			viewHolder.mContent = (TextView) view
					.findViewById(R.id.Note_Contration);
			view.setTag(viewHolder);
		} else
		{
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
		}
		viewHolder.mTitle.setText(note.getNoteTitle());
		viewHolder.mContent.setText(note.getNoteContent());
		viewHolder.mDate.setText(note.getNoteDate());
		return view;
	}

	class ViewHolder
	{
		TextView mTitle;
		TextView mContent;
		TextView mDate;
	}

}
