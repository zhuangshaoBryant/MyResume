package com.yqy.myresume.fragment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yqy.myresume.R;
import com.yqy.myresume.view.datepicker.wheelview.NumericWheelAdapter;
import com.yqy.myresume.view.datepicker.wheelview.OnWheelScrollListener;
import com.yqy.myresume.view.datepicker.wheelview.WheelView;

public class DatePickerFragment extends Fragment implements OnWheelScrollListener{

	private OnChooiceDateFragmentListener mListener;
	private int mYear;
	private int mMonth;
	private int mDay;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	
	private int startYear = 1900;
	
	public DatePickerFragment(OnChooiceDateFragmentListener mListener,
			int mYear, int mMonth, int mDay) {
		super();
		this.mListener = mListener;
		this.mYear = mYear;
		this.mMonth = mMonth;
		this.mDay = mDay;
	}

	public void setListener(OnChooiceDateFragmentListener mListener){
		this.mListener = mListener;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.wheel_date_picker, container, false);
	}
	
	/**
	 * 初始化布局
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initDatePick(view);
	}
	
	/**
	 * 初始化日期选择器
	 */
	private void initDatePick(View view) {
		Calendar c = Calendar.getInstance();
		int norYear = c.get(Calendar.YEAR);
		
		int curYear = mYear;
		int curMonth =mMonth+1;
		int curDate = mDay;
		
		year = (WheelView) view.findViewById(R.id.year);
		year.setAdapter(new NumericWheelAdapter(startYear, 2050));//设置起始年和终止年
		year.setLabel("年");
		year.setCyclic(true);
		year.addScrollingListener(this);
		
		month = (WheelView) view.findViewById(R.id.month);
		month.setAdapter(new NumericWheelAdapter(1, 12, "%02d"));
		month.setLabel("月");
		month.setCyclic(true);
		month.addScrollingListener(this);
		
		day = (WheelView) view.findViewById(R.id.day);
		initDay(curYear,curMonth);
		day.setLabel("日");
		day.setCyclic(true);

		year.setCurrentItem(curYear - startYear);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);

	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;//是否是闰年
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	/**
	 */
	private void initDay(int arg1, int arg2) {
		day.setAdapter(new NumericWheelAdapter(1, getDay(arg1, arg2), "%02d"));
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
	}
	
	@Override
	public void onScrollingStarted(WheelView wheel) {
		
	}

	@Override
	public void onScrollingFinished(WheelView wheel) {
		int n_year = year.getCurrentItem() + startYear;//
		int n_month = month.getCurrentItem() + 1;//
		initDay(n_year,n_month);
		String birthday=new StringBuilder().append((year.getCurrentItem()+startYear)).append("-").append((month.getCurrentItem() + 1) < 10 ? "0" + (month.getCurrentItem() + 1) : (month.getCurrentItem() + 1)).append("-").append(((day.getCurrentItem()+1) < 10) ? "0" + (day.getCurrentItem()+1) : (day.getCurrentItem()+1)).toString();
		onChooiceDate(birthday);
	}
	
	public interface OnChooiceDateFragmentListener {
		public void onChooiceDate(String date);
	}

	public void onChooiceDate(String date) {
		mListener.onChooiceDate(date);
	}

	
}
