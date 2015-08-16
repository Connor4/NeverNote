package com.connor.adapter;

import java.util.List;

import com.connor.model.Book;
import com.connor.nevernote.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.provider.Browser.BookmarkColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookAdapter extends ArrayAdapter<Book>
{
	private int ResourceId;
	private Context context;

	public BookAdapter(Context context, int resource, List<Book> books)
	{
		super(context, resource, books);
		this.ResourceId = resource;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Book book = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null)
		{
			view = LayoutInflater.from(context).inflate(ResourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.BookName = (TextView) view.findViewById(R.id.Book_Name);
			viewHolder.BookCount = (TextView) view
					.findViewById(R.id.Book_Count);
			view.setTag(viewHolder);
		} else
		{
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
		}
		viewHolder.BookName.setText(book.getBookname());
		viewHolder.BookCount.setText(book.getBookcount() + " 条笔记");
		return view;
	}

	class ViewHolder
	{
		TextView BookName;
		TextView BookCount;
	}

}
