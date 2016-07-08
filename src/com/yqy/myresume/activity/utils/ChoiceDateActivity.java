package com.yqy.myresume.activity.utils;

import java.util.Calendar;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yqy.myresume.R;
import com.yqy.myresume.R.id;
import com.yqy.myresume.R.layout;
import com.yqy.myresume.activity.CommonActivity;
import com.yqy.myresume.fragment.DatePickerFragment;
import com.yqy.myresume.utils.Utils;

/**
 * 选择日期<br/>
 * @params title 标题
 * @params resultCode 结果码<br/>
 */
public class ChoiceDateActivity extends CommonActivity{

	private TextView dateTv;
	
	private int mYear = 1993;
	private int mMonth = 0;
	private int mDay = 1;
	
	private String dateStr;//YY-MM-DD
	
	private int resultCode;

	@Override
	protected void showNextPage() {

	}

	@Override
	protected void showPreviousPage() {

	}

	@Override
	public void initView() {
		String title = getIntent().getStringExtra("title");
		resultCode = getIntent().getIntExtra("resultCode", 0);
		setTitleAndContentLayoutId(title,
				R.layout.activity_choice_date);
		showTextLeftBtn("返回");
		hideRight();
		
		Calendar mCalendar = Calendar.getInstance();
		mYear = mCalendar.get(Calendar.YEAR);
		mMonth = mCalendar.get(Calendar.MONTH);
		
		dateTv = (TextView) findViewById(R.id.dateTv);
		dateTv.setText(mYear+"-"+mMonth+"-"+mDay);
		DatePickerFragment.OnChooiceDateFragmentListener mListener = new DatePickerFragment.OnChooiceDateFragmentListener() {

			@Override
			public void onChooiceDate(String date) {
				dateStr = date;
				dateTv.setText(dateStr);
			}
		};
		DatePickerFragment mFragment = new DatePickerFragment(mListener, mYear,
				mMonth, mDay);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.dateLl, mFragment).commit();
	}

	@Override
	public void addLisener() {

	}
	
	OnClickListener backClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(!Utils.isEmpty(dateStr)){
				Intent intent = new Intent();
				intent.putExtra("date", dateStr);
				setResult(resultCode, intent);
				finish();
			}else{
				finish();
			}
		}
	};

	@Override
	public OnClickListener getBackClickListener() {
		return backClickListener;
	}


}
