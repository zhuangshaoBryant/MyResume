package com.yqy.myresume.activity.project;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yqy.myresume.R;
import com.yqy.myresume.activity.CommonActivity;
import com.yqy.myresume.activity.education.Education2Activity;
import com.yqy.myresume.bean.Function;
import com.yqy.myresume.bean.ProjectExperience;
import com.yqy.myresume.utils.Contacts;
import com.yqy.myresume.utils.JsonUtil;
import com.yqy.myresume.utils.Utils;
import com.yqy.myresume.view.swipemenulistview.SwipeMenu;
import com.yqy.myresume.view.swipemenulistview.SwipeMenuCreator;
import com.yqy.myresume.view.swipemenulistview.SwipeMenuItem;
import com.yqy.myresume.view.swipemenulistview.SwipeMenuListView;
import com.yqy.myresume.view.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.yqy.myresume.view.swipemenulistview.SwipeMenuListView.OnSwipeListener;

public class ProjectLvActivity extends CommonActivity {

	private SwipeMenuListView mListView;
	private List<ProjectExperience> mList;
	private ProjectAdapter mAdapter;
	
	private int addCode = 1;
	private int modifyCode = 2;

	@Override
	protected void showNextPage() {

	}

	@Override
	protected void showPreviousPage() {
		startActivity(new Intent(this, Education2Activity.class));
		finish();

		//两个界面切换的动画
		overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
	}

	@Override
	public void initView() {
		setTitleAndContentLayoutId("项目经验", R.layout.activity_project_lv);
		showTextLeftBtn("返回");
		showTextRightBtn("添加");

		mListView = (SwipeMenuListView) findViewById(R.id.lv);
		initData();
		mAdapter = new ProjectAdapter();
		mListView.setAdapter(mAdapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent mIntent = new Intent(context, ProjectModifyActivity.class);
				mIntent.putExtra("bean",mList.get(arg2));
				mIntent.putExtra("resultCode", modifyCode);
				startActivityForResult(mIntent, 1);
			}
		});

		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				// set item width
				openItem.setWidth(Utils.dip2px(context,90));
				// set item title
				openItem.setTitle("Open");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(Utils.dip2px(context,90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		mListView.setMenuCreator(creator);

		// step 2. listener item click event
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
//				ProjectExperience item = mList.get(position);
				switch (index) {
				case 0:
					// open
//					open(item);
					Intent mIntent = new Intent(context, ProjectModifyActivity.class);
					mIntent.putExtra("bean",mList.get(position));
					mIntent.putExtra("resultCode", modifyCode);
					startActivityForResult(mIntent, 1);
					break;
				case 1:
					// delete
					// delete(item);
//					mList.remove(position);
//					mAdapter.notifyDataSetChanged();
					mList.remove(position);
					saveData();
					mAdapter.notifyDataSetChanged();
					break;
				}
			}
		});

		// set SwipeListener
		mListView.setOnSwipeListener(new OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});

		// other setting
		// listView.setCloseInterpolator(new BounceInterpolator());
		if(mList.size() == 0){
			//项目经验条目为0，则自动跳转到新增项目经验
		}
	}
	
	private void initData() {
		mList = new ArrayList<ProjectExperience>();
		String data = sp.read(Contacts.PROJECTEXPERIENCE, "");
		if(!Utils.isEmpty(data)){
			try {
				JSONArray ja = new JSONArray(data);
				for (int i = 0; i < ja.length(); i++) {
					ProjectExperience bean = JsonUtil.jsonToBean(ja.getJSONObject(i).toString(), ProjectExperience.class);
					mList.add(bean);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			Intent mIntent = new Intent(context, ProjectModifyActivity.class);
			mIntent.putExtra("resultCode", addCode);
			mIntent.putExtra("id", (mList.size()+1)+"");
			startActivityForResult(mIntent, 1);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1 && data != null){
			ProjectExperience bean= (ProjectExperience) data.getSerializableExtra("bean");
			if(resultCode == addCode){
				mList.add(bean);
				saveData();
			}
			if(resultCode == modifyCode){
				saveData(bean);
			}
			mAdapter.notifyDataSetChanged();
		}
	}
	
	private void saveData(ProjectExperience bean) {
		for (int i = 0; i < mList.size(); i++) {
			if(mList.get(i).getId().equals(bean.getId())){
				mList.set(i, bean);
				break;
			}
		}
		saveData();
	}
	
	private void saveData() {
		sp.write(Contacts.PROJECTEXPERIENCE, JsonUtil.objectToJson(mList));
	}

	@Override
	public void addLisener() {
		findViewById(R.id.rightTv).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(context, ProjectModifyActivity.class);
				mIntent.putExtra("resultCode", addCode);
				mIntent.putExtra("id", (mList.size()+1)+"");
				startActivityForResult(mIntent, 1);
			}
		});
	}

	@Override
	public OnClickListener getBackClickListener() {
		return null;
	}

	class ProjectAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder mHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_project, null);
				mHolder = new ViewHolder();
				mHolder.nameTv = (TextView) convertView
						.findViewById(R.id.nameTv);
				mHolder.timeTv = (TextView) convertView
						.findViewById(R.id.timeTv);
				convertView.setTag(mHolder);
			}else{
				mHolder = (ViewHolder) convertView.getTag();
			}
			ProjectExperience bean = mList.get(position);
			mHolder.nameTv.setText(bean.getName());
			mHolder.timeTv.setText(bean.getStart().replace("-", ".") + "-" +bean.getEnd().replace("-", "."));
			return convertView;
		}

		class ViewHolder {
			TextView nameTv;
			TextView timeTv;
		}

	}

}
