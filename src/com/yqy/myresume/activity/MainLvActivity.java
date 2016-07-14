package com.yqy.myresume.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yqy.myresume.R;
import com.yqy.myresume.activity.education.EducationActivity;
import com.yqy.myresume.activity.info.InfoActivity;
import com.yqy.myresume.activity.info.InfoModifyActivity;
import com.yqy.myresume.activity.project.ProjectLvActivity;
import com.yqy.myresume.activity.share.ShareWebviewActivity;
import com.yqy.myresume.adapter.FunctionAdapter;

public class MainLvActivity extends CommonActivity implements OnClickListener,
		OnItemClickListener {

	private ImageView userfaceIv;
	private FunctionAdapter adapter;

	private RelativeLayout rl;// 设置图片背景
	private ListView lv;// 功能列表

	private int height;// 每个item的高度
	private boolean flag = false;
	private boolean isScroll = false;

	@Override
	protected void showNextPage() {

	}

	@Override
	protected void showPreviousPage() {

	}

	@Override
	public void initView() {
		setTitleAndContentLayoutId("", R.layout.activity_main_lv);
		userfaceIv = (ImageView) findViewById(R.id.userfaceIv);
		rl = (RelativeLayout) findViewById(R.id.functionRl);
		lv = (ListView) findViewById(R.id.functionLv);

		ViewTreeObserver vto = rl.getViewTreeObserver();

		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {

				if (!flag) {
					height = rl.getMeasuredHeight();
					// 获取到宽度和高度后，可用于计算
					flag = true;
					if (height % 4 > 0) {
						rl.setPadding(0, height % 4, 0, 0);
					}
					initFunctionListData();
					adapter = new FunctionAdapter(context, functionList, height - height % 4);
					lv.setAdapter(adapter);
				}
				return true;
			}
		});

	}

	//主界面进行选项的地方
	private Intent toActivityById(String id) {
		Intent intent = null;
		switch (Integer.parseInt(id)) {
			case 1:
				intent = new Intent(context, InfoActivity.class);
				break;
			case 2:
				intent = new Intent(context, EducationActivity.class);
				break;
			case 3:
				intent = new Intent(context, ProjectLvActivity.class);
				break;
			case 9:
				intent = new Intent(context, ShareWebviewActivity.class);
			default:
				break;
		}
		return intent;
	}

	@Override
	public void addLisener() {
		findViewById(R.id.rightIv).setOnClickListener(this);

		lv.setOnItemClickListener(this);
		/*lv.setOnScrollListener(new OnScrollListener() {// 滑动监听

			@SuppressLint("NewApi")
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {// 滑动状态改变
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && !isScroll) {
					if(view.getChildAt(view.getFirstVisiblePosition()) == null)
						return;
					int y = view.getChildAt(view.getFirstVisiblePosition()).getTop();
					final int result = ((height/4)*view.getFirstVisiblePosition()) - y;
					isScroll = true;
					lv.post(new Runnable() {
						
						@Override
						public void run() {
							lv.scrollTo(0, -result);
						}
					});
				}else{
					isScroll = false;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {// 滑动过程中
//				Log.e("firstTop","-"+ view.getChildAt(firstVisibleItem).getTop());
			}
		});*/
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (adapter != null) {
			initFunctionListData();
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public OnClickListener getBackClickListener() {
		return null;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.rightIv:
				startActivity(new Intent(context, SettingActivity.class));
				break;
			default:
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if ("true".equals(functionList.get(arg2).getFlag())) {
			Intent intent = toActivityById(functionList.get(arg2).getId());
			if (intent != null)
				startActivity(intent);
			else
				showToast("此功能正在开发中...");
		} else {
			showShortToast("此功能暂时关闭");
		}
		adapter.notifyDataSetChanged();
	}

}
