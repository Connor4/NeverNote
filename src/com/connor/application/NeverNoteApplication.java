package com.connor.application;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

public class NeverNoteApplication extends Application
{
	private static Context context;

	@Override
	public void onCreate()
	{
		super.onCreate();
		this.context = getApplicationContext();
	}

	public Context getContext()
	{
		return context;
	}
}
