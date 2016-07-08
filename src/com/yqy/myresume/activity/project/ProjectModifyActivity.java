package com.yqy.myresume.activity.project;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.yqy.myresume.R;
import com.yqy.myresume.activity.CommonActivity;
import com.yqy.myresume.activity.utils.ChoiceDateActivity;
import com.yqy.myresume.activity.utils.RadioActivity;
import com.yqy.myresume.bean.DialogBean;
import com.yqy.myresume.bean.ProjectExperience;
import com.yqy.myresume.utils.Utils;

/**
 * 项目经验的 添加 修改 Description:
 */
public class ProjectModifyActivity extends CommonActivity implements
		OnClickListener, TextWatcher {

	private TextView rightTv;

	private EditText nameEt, briefEt, descEt, lightsEt;
	private TextView startTv, endTv, isonlineTv, toolTv, typeTv, systemTv;

	private boolean isModified = false;// 是否修改，用于是否展示是否放弃编辑弹出框

	private DialogBean mDialogBean;

	private int starCode = 2;
	private int endCode = 3;
	private int isonlineCode = 4;
	private int toolCode = 5;
	private int typeCode = 6;
	private int systemCode = 7;

	private int resultCode;
	private ProjectExperience bean;
	private String id = "1";

	@Override
	protected void showNextPage() {

	}

	@Override
	protected void showPreviousPage() {

	}

	@Override
	public void initView() {
		setTitleAndContentLayoutId("编辑项目经验", R.layout.activity_project_modify);
		showTextRightBtn("保存");
		showTextLeftBtn("取消");

		rightTv = (TextView) findViewById(R.id.rightTv);

		startTv = (TextView) findViewById(R.id.startTv);
		endTv = (TextView) findViewById(R.id.endTv);
		isonlineTv = (TextView) findViewById(R.id.isonlineTv);
		toolTv = (TextView) findViewById(R.id.toolTv);
		typeTv = (TextView) findViewById(R.id.typeTv);
		systemTv = (TextView) findViewById(R.id.systemTv);

		nameEt = (EditText) findViewById(R.id.nameEt);
		briefEt = (EditText) findViewById(R.id.briefEt);
		descEt = (EditText) findViewById(R.id.descEt);
		lightsEt = (EditText) findViewById(R.id.lightsEt);

		initDialogBean();

		resultCode = getIntent().getIntExtra("resultCode", 0);
		bean = (ProjectExperience) getIntent().getSerializableExtra("bean");
		if(bean == null){
			setTitle("新增项目经验");
			id = getIntent().getStringExtra("id");
		}
		intData();

		hideSoftInput();
	}

	/**
	 * 初始化显示的数据
	 */
	private void intData() {
		if (bean != null) {
			startTv.setText(bean.getStart());
			endTv.setText(bean.getEnd());
			isonlineTv.setText(bean.getIsonline());
			toolTv.setText(bean.getTool());
			typeTv.setText(bean.getType());
			systemTv.setText(bean.getSystem());
			nameEt.setText(bean.getName());
			briefEt.setText(bean.getBrief());
			descEt.setText(bean.getDesc());
			lightsEt.setText(bean.getLights());
		}
	}

	@Override
	public void addLisener() {
		rightTv.setOnClickListener(this);
		startTv.setOnClickListener(this);
		endTv.setOnClickListener(this);
		isonlineTv.setOnClickListener(this);
		toolTv.setOnClickListener(this);
		typeTv.setOnClickListener(this);
		systemTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent mIntent = null;
		switch (v.getId()) {
		case R.id.rightTv:
			bean = new ProjectExperience(id,nameEt.getText().toString(), 
					startTv.getText().toString(), endTv.getText().toString(),
					isonlineTv.getText().toString(),toolTv.getText().toString(),
					typeTv.getText().toString(),systemTv.getText().toString(),
					briefEt.getText().toString(),descEt.getText().toString(),
					lightsEt.getText().toString());
			String tip = checkValue();
			if (Utils.isEmpty(tip)) {
				mIntent = new Intent();
				mIntent.putExtra("id", "");
				mIntent.putExtra("bean", bean);
				setResult(resultCode,mIntent);
				finish();
			} else {
				showToast(tip);
			}
			break;
		case R.id.startTv:
			mIntent = new Intent(context, ChoiceDateActivity.class);
			mIntent.putExtra("title", "选择开始时间");
			mIntent.putExtra("resultCode", starCode);
			startActivityForResult(mIntent, 1);
			break;
		case R.id.endTv:
			mIntent = new Intent(context, ChoiceDateActivity.class);
			mIntent.putExtra("title", "选择结束时间");
			mIntent.putExtra("resultCode", endCode);
			startActivityForResult(mIntent, 1);
			break;
		case R.id.isonlineTv:
			mIntent = new Intent(context, RadioActivity.class);
			mIntent.putExtra("items", getResources().getStringArray(R.array.project_isonline));
			mIntent.putExtra("title", "选择是否上线");
			mIntent.putExtra("resultCode", isonlineCode);
			startActivityForResult(mIntent, 1);
			break;
		case R.id.toolTv:
			mIntent = new Intent(context, RadioActivity.class);
			mIntent.putExtra("items", getResources().getStringArray(R.array.project_tool));
			mIntent.putExtra("title", "选择开发工具");
			mIntent.putExtra("resultCode", toolCode);
			startActivityForResult(mIntent, 1);
			break;
		case R.id.typeTv:
			mIntent = new Intent(context, RadioActivity.class);
			mIntent.putExtra("items", getResources().getStringArray(R.array.project_type));
			mIntent.putExtra("title", "选择项目类型");
			mIntent.putExtra("resultCode", typeCode);
			startActivityForResult(mIntent, 1);
			break;
		case R.id.systemTv:
			mIntent = new Intent(context, RadioActivity.class);
			mIntent.putExtra("items", getResources().getStringArray(R.array.project_system));
			mIntent.putExtra("title", "选择操作系统");
			mIntent.putExtra("resultCode", systemCode);
			startActivityForResult(mIntent, 1);
			break;

		default:
			break;
		}

	}

	private String checkValue() {
		String tip = "";
		if (Utils.isEmpty(bean.getName())) {
			tip = "请填写项目名称";
		} else if (Utils.isEmpty(bean.getStart())) {
			tip = "请选择开始时间";
		} else if (Utils.isEmpty(bean.getEnd())) {
			tip = "请选择结束时间";
		} else if (Utils.isEmpty(bean.getIsonline())) {
			tip = "请选择是否上线";
		} else if (Utils.isEmpty(bean.getTool())) {
			tip = "请选择开发工具";
		} else if (Utils.isEmpty(bean.getType())) {
			tip = "请选择项目类型";
		} else if (Utils.isEmpty(bean.getSystem())) {
			tip = "请选择操作系统";
		} else if (Utils.isEmpty(bean.getBrief())) {
			tip = "请填写项目简介";
		} else if (Utils.isEmpty(bean.getDesc())) {
			tip = "请填写项目描述";
		} else if (Utils.isEmpty(bean.getLights())) {
			tip = "请填写项目亮点";
		}
		return tip;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			updateModifyStatus();
			if (resultCode == starCode) {
				startTv.setText(data.getStringExtra("date"));
			}
			if (resultCode == endCode) {
				endTv.setText(data.getStringExtra("date"));
			}
			if (resultCode == isonlineCode) {
				isonlineTv.setText(data.getStringExtra("text"));
			}
			if(resultCode == typeCode){
				typeTv.setText(data.getStringExtra("text"));
			}
			if(resultCode == toolCode){
				toolTv.setText(data.getStringExtra("text"));
			}
			if(resultCode == systemCode){
				systemTv.setText(data.getStringExtra("text"));
			}
		}
	}

	private void initDialogBean() {
		mDialogBean = new DialogBean();
		mDialogBean.setContent("是否放弃编辑");
		mDialogBean.setLeft("是");
		mDialogBean.setRight("否");
		mDialogBean.setLeftListenr(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				hideDialog();
				finish();
			}
		});
		mDialogBean.setRightListenr(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				hideDialog();
			}
		});
	}

	@Override
	public OnClickListener getBackClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (isModified) {
					// 弹出是否放弃编辑弹出框
					showDialog(mDialogBean);
				} else {
					finish();
				}
			}
		};
	}

	/**
	 * 更新是否修改 状态
	 */
	private void updateModifyStatus() {
		if (!isModified) {
			isModified = true;
			rightTv.setEnabled(true);
			rightTv.setTextColor(Color.parseColor("#ffffff"));
		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		updateModifyStatus();

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

}
