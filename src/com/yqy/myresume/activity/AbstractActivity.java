package com.yqy.myresume.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.yqy.myresume.R;
import com.yqy.myresume.bean.Function;
import com.yqy.myresume.utils.Contacts;
import com.yqy.myresume.utils.JsonUtil;
import com.yqy.myresume.utils.SPUtil;
import com.yqy.myresume.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractActivity extends FragmentActivity {

	public int[] icons = { R.drawable.icon_info, R.drawable.icon_study, R.drawable.icon_project,
			R.drawable.icon_power, R.drawable.icon_evaluation,
			R.drawable.icon_job,
			R.drawable.icon_work,  R.drawable.icon_train,
			 R.drawable.icon_other };// 图片资源
	public String[] titles = { "我的信息", "教育经历", "项目经验", "专业技能","自我评价", "求职意向",
			"实习经历", "工作经历", "其他" };// 功能标题
	public List<Function> functionList;
	/**
	 * 这里添加所有公共的对象 单例
	 */
	protected SPUtil sp;
	public Context context;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(createView());

//		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//		}

		initSingleton();
		initFunctionListData();
		init();
	}

	/**
	 * 初始化所有单例对象
	 */
	private void initSingleton() {
		sp = SPUtil.getInstance();
		sp.init(this);
		context = this;
	}
	
	/**
	 * 初始化功能列表数据
	 */
	public void initFunctionListData(){
		functionList = new ArrayList<Function>();
		
		String data = sp.read(Contacts.FUNCTIONLIST, "");
		if(Utils.isEmpty(data)){
			for (int i = 0; i < 9; i++) {
				Function function = new Function((i+1)+"","" + icons[i], titles[i], null,
						"true");
				functionList.add(function);
			}
			String str = JsonUtil.objectToJson(functionList);//将对象转化为json格式
			sp.write(Contacts.FUNCTIONLIST, str);
		}else{
			try {
				JSONArray ja = new JSONArray(data);
				for (int i = 0; i < ja.length(); i++) {
					Function function = JsonUtil.jsonToBean(ja.getJSONObject(i).toString(), Function.class);
					functionList.add(function);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void hideSoftInput(){
		if(this.getCurrentFocus() != null)
			((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public abstract Integer createView();

	/**
	 * 所有初始化在此方法完成
	 */
	public abstract void init();

	public void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}

	public void showShortToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
