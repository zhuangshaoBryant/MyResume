package com.yqy.myresume.activity.project;

import com.yqy.myresume.R;
import com.yqy.myresume.R.layout;
import com.yqy.myresume.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ProjectAddActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_add);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.project_add, menu);
		return true;
	}

}
