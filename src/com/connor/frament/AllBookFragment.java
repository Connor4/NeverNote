package com.connor.frament;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.connor.activity.BooksNotes;
import com.connor.adapter.BookAdapter;
import com.connor.adapter.NoteAdapter;
import com.connor.application.NeverNoteApplication;
import com.connor.model.Book;
import com.connor.model.Note;
import com.connor.nevernote.R;
import com.connor.utils.NeverNoteDB;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class AllBookFragment extends Fragment
{
	private NeverNoteApplication mApplication;
	private NeverNoteDB mNeverNoteDB;
	private BookAdapter adapter;
	private View allbookview;
	private ListView mListView;
	private List<Book> books = new ArrayList<Book>();
	private List<Book> temp = new ArrayList<Book>();
	private ImageView more;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		allbookview = inflater.inflate(R.layout.allbook_fragment, container,
				false);

		initView();
		initData();
		initEvent();
		return allbookview;
	}

	private void initEvent()
	{
		mListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				String table = temp.get(position).getBookname();
				Intent intent = new Intent(mApplication.getContext(), BooksNotes.class);
				intent.putExtra("table", table);
				startActivity(intent);
			}
		});
	}

	private void initData()
	{
		temp = mNeverNoteDB.getAllBook();
		if (temp.size() <= 0)
		{
			Toast.makeText(getActivity().getApplicationContext(),
					"还没有笔记本，快去创建吧", Toast.LENGTH_SHORT).show();
		}else
		{
			for(Iterator<Book> it = temp.iterator();it.hasNext();)
			{
				books.add(it.next());
			}
		}
	}

	private void initView()
	{
		mListView = (ListView) allbookview.findViewById(R.id.id_listview_book);
		mNeverNoteDB = NeverNoteDB.getInstance(mApplication.getContext());
		adapter = new BookAdapter(mApplication.getContext(),
				R.layout.book_item, books);
		mListView.setAdapter(adapter);
	}
}
