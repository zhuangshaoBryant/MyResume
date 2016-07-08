package com.yqy.myresume.activity;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yqy.myresume.R;
import com.yqy.myresume.bean.Function;
import com.yqy.myresume.utils.Contacts;
import com.yqy.myresume.utils.JsonUtil;
import com.yqy.myresume.utils.Utils;
import com.yqy.myresume.view.switchbutton.SwitchButton;

public class SettingActivity extends CommonActivity {

	private ListView functionLv;
	private FunctionSetAdapter adapter;

	@Override
	protected void showNextPage() {

	}

	@Override
	protected void showPreviousPage() {

	}

	@Override
	public void initView() {
		setTitleAndContentLayoutId("设置", R.layout.activity_setting);
		showTextRightBtn("完成");
		functionLv = (ListView) findViewById(R.id.functionLv);
		adapter = new FunctionSetAdapter();
		functionLv.setAdapter(adapter);
		
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			@SuppressWarnings("unchecked")
			Map<String,String> map = (Map<String, String>) msg.obj;
			updateFunctionById(map.get("id"), map.get("flag"));
		};
	};
	
	private void updateFunctionById(String id, String flag) {
		for (int i = 0; i < functionList.size(); i++) {
			Function function = functionList.get(i);
			if(function.getId().equals(id)){
				function.setFlag(flag);
				functionList.set(i, function);
				break;
			}
		}
	}

	@Override
	public void addLisener() {
		findViewById(R.id.rightTv).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				saveFunctionListData();
				finish();
			}
		});
	}
	
	private void saveFunctionListData() {
		String data = JsonUtil.objectToJson(functionList);
		sp.write(Contacts.FUNCTIONLIST, data);
	}

	@Override
	public OnClickListener getBackClickListener() {
		return null;
	}
	
	class FunctionSetAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return functionList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return functionList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			ViewHolder holder = null;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = inflaterGetView(context, R.layout.item_setting);
				holder.iconIv = (ImageView) convertView.findViewById(R.id.iconIv);
				holder.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
				holder.mySwitchBtn = (SwitchButton) convertView.findViewById(R.id.mySwitchBtn);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			final Function function = functionList.get(position);
			holder.iconIv.setImageResource(Integer.parseInt(function.getIconresource()));
			holder.nameTv.setText(function.getTitle());
			holder.mySwitchBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", function.getId());
					map.put("flag", arg1 + "");
					mHandler.sendMessage(mHandler.obtainMessage(1, map));
				}
			});
			if (Utils.isEmpty(function.getFlag())) {
				holder.mySwitchBtn.setChecked(true);
			}else{
				holder.mySwitchBtn.setChecked(Boolean.parseBoolean(function.getFlag()));
			}
			return convertView;
		}
		
		class ViewHolder{
			private ImageView iconIv;
			private TextView nameTv;
			private SwitchButton mySwitchBtn;
		}
		
	}


}
