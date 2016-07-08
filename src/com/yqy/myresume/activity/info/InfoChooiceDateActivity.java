package com.yqy.myresume.activity.info;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yqy.myresume.R;
import com.yqy.myresume.activity.CommonActivity;
import com.yqy.myresume.fragment.DatePickerFragment;
import com.yqy.myresume.utils.Utils;

public class InfoChooiceDateActivity extends CommonActivity implements
		OnClickListener {

	private TextView ageTv;
	private TextView starTv;
	
	private int mYear = 1993;
	private int mMonth = 0;
	private int mDay = 1;
	
	private String birth;//YY-MM-DD
	private String age;
	private String star;

	@Override
	protected void showNextPage() {

	}

	@Override
	protected void showPreviousPage() {

	}

	@Override
	public void initView() {
		setTitleAndContentLayoutId("选择出生日期",
				R.layout.activity_info_choice_date);
		showTextLeftBtn("返回");
		hideRight();
		
		ageTv = (TextView) findViewById(R.id.ageTv);
		starTv = (TextView) findViewById(R.id.starTv);
		
		DatePickerFragment.OnChooiceDateFragmentListener mListener = new DatePickerFragment.OnChooiceDateFragmentListener() {

			@Override
			public void onChooiceDate(String date) {
				birth = date;
				age = Utils.getAgeByBirthday(date);
				star = Utils.getStarByBirth(date);
				ageTv.setText(age);
				starTv.setText(star);
				Log.e("mLisenter", date+"-"+age+"-"+star);
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
			if(!Utils.isEmpty(birth)){
				Intent intent = new Intent();
				intent.putExtra("birth", birth);
				intent.putExtra("age", age);
				intent.putExtra("star", star);
				setResult(1, intent);
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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
}
