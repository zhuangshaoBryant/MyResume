package com.yqy.myresume.activity;

import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.yqy.myresume.R;

public class MainActivity extends CommonActivity implements OnClickListener{

	@Override
	protected void showNextPage() {

	}

	@Override
	protected void showPreviousPage() {

	}

	@Override
	public void initView() {
		setTitleAndContentLayoutId("首页", R.layout.activity_main);
		hideTitleBar();

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}

	}

	@Override
	public void addLisener() {
	}

	@Override
	public OnClickListener getBackClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		};
	}

	@Override
	public void onClick(View view) {

	}

}
