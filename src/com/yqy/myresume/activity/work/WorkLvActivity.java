package com.yqy.myresume.activity.work;

import android.view.View;
import android.view.View.OnClickListener;

import com.yqy.myresume.R;
import com.yqy.myresume.activity.CommonActivity;

public class WorkLvActivity extends CommonActivity {


	@Override
	protected void showNextPage() {

	}

	@Override
	protected void showPreviousPage() {

	}

	@Override
	public void initView() {
		setTitleAndContentLayoutId("工作历程", R.layout.activity_work_lv);
		showTextRightBtn("编辑");
		showTextLeftBtn("返回");

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
}
