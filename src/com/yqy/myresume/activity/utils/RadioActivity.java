package com.yqy.myresume.activity.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.yqy.myresume.R;
import com.yqy.myresume.activity.CommonActivity;

/**
 * 单选列表<br/>
 * 从xml中获取String数组的方法<br/>
 * String[] items = getResources().getStringArray(R.array.sex) Description:
 * @params title 标题
 * @params resultCode 接收结果
 * @params items String数组
 */
public class RadioActivity extends CommonActivity {

	private ListView mListView;
	private int resultCode;
	private String[] items;

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
		items = getIntent().getStringArrayExtra("items");
		setTitleAndContentLayoutId(title == null ? "" : title,
				R.layout.activity_radio);
		showTextLeftBtn("返回");
		hideRight();

		List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < items.length; i++) {
			Map<String, Object> listem = new HashMap<String, Object>();
			listem.put("text", items[i]);
			listems.add(listem);
		}
		SimpleAdapter mAdapter = new SimpleAdapter(context, listems,
				R.layout.item_radio, new String[] { "text" },
				new int[] { R.id.textTv });

		mListView = (ListView) findViewById(R.id.mListView);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				@SuppressWarnings("unchecked")
				HashMap<String, Object> item = (HashMap<String, Object>) arg0
						.getItemAtPosition(arg2);
				Intent mIntent = new Intent();
				mIntent.putExtra("text", item.get("text").toString());
				setResult(resultCode, mIntent);
				finish();
			}
		});
	}

	@Override
	public void addLisener() {
		
	}

	@Override
	public OnClickListener getBackClickListener() {
		// TODO Auto-generated method stub
		return null;
	}

}
