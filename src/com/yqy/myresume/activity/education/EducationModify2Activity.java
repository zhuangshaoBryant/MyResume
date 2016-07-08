package com.yqy.myresume.activity.education;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yqy.myresume.R;
import com.yqy.myresume.activity.CommonActivity;
import com.yqy.myresume.activity.utils.ChoiceDateActivity;
import com.yqy.myresume.activity.utils.RadioActivity;
import com.yqy.myresume.bean.DialogBean;
import com.yqy.myresume.bean.EducationExperience;
import com.yqy.myresume.utils.Contacts;
import com.yqy.myresume.utils.JsonUtil;
import com.yqy.myresume.utils.Utils;
import com.yqy.myresume.view.datepicker.wheelview.ArrayWheelAdapter;
import com.yqy.myresume.view.datepicker.wheelview.OnWheelScrollListener;
import com.yqy.myresume.view.datepicker.wheelview.WheelView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description:编辑教育信息
 * @param <T>
 */
public class EducationModify2Activity<T> extends CommonActivity implements OnClickListener,TextWatcher {
	/* 请求识别码 */
	private static final int CODE_GALLERY_REQUEST = 0xc0;
	private static final int CODE_RESULT_REQUEST = 0xa2;

	// 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
	private static int output_X = 128;
	private static int output_Y = 128;

	private int starCode = 9;
	private int endCode = 10;
	private int eduCode = 8;
	private int engCode = 7;
	private TextView rightTv;

	private EditText nameEt, orderEt,  schEt, courseEt, proEt;
	private TextView eduTv, startTv,  endTv,engTv;

	private EducationExperience bean;
	private boolean isModified = false;//是否修改，用于是否展示是否放弃编辑弹出框

	private String nameStr, eduStr, startStr, proStr, endTvStr,
			orderStr, engStr, schStr, courseStr;

	//显示性别选择器
	private RelativeLayout rl;
	private LinearLayout sexLl;//性别选择器父控件
	private View whiteView;//点击空白关闭性别选择器
	private TextView okTv;//完成
	private LayoutInflater inflater = null;
	private WheelView sex;
	private String[] sexStrs = new String[]{"男","女"};

	private DialogBean mDialogBean;
	private ImageView faceIv;//头像

	@Override
	protected void showNextPage() {

	}

	@Override
	protected void showPreviousPage() {

	}

	@Override
	public void initView() {
		setTitleAndContentLayoutId("编辑资料", R.layout.activity_education_modify2);
		showTextRightBtn("完成");
		showTextLeftBtn("取消");

		rightTv = (TextView) findViewById(R.id.rightTv);

		nameEt = (EditText) findViewById(R.id.nameEt);
		orderEt = (EditText) findViewById(R.id.orderEt);
		engTv = (TextView) findViewById(R.id.engTv);
		schEt = (EditText) findViewById(R.id.schEt);
		courseEt = (EditText) findViewById(R.id.courseEt);

		eduTv = (TextView) findViewById(R.id.eduTv);
		startTv = (TextView) findViewById(R.id.startTv);
		proEt = (EditText) findViewById(R.id.proTv);
		endTv = (TextView) findViewById(R.id.endTv);

		faceIv = (ImageView) findViewById(R.id.faceIv);
		faceIv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				choseHeadImageFromGallery();//从本地选择头像
			}
		});

		rl = (RelativeLayout) findViewById(R.id.rl);
		sexLl = (LinearLayout) findViewById(R.id.sexLl);
		whiteView = findViewById(R.id.whiteView);
		okTv = (TextView) findViewById(R.id.okTv);

		inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		sexLl.addView(getSexPicker());

		rightTv.setEnabled(false);
		rightTv.setTextColor(Color.parseColor("#dddddd"));

		initData();
		initDialogBean();

		hideSoftInput();
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

	/**
	 * 更新是否修改 状态
	 */
	private void updateModifyStatus() {
		if(!isModified){
			isModified = true;
			rightTv.setEnabled(true);
			rightTv.setTextColor(Color.parseColor("#ffffff"));
		}
	}

	/**
	 * 初始化并获取性别选择器控件
	 * @return
	 */
	private View getSexPicker(){
		final View view = inflater.inflate(R.layout.wheel_sex_picker, null);
		sex = (WheelView) view.findViewById(R.id.sex);
		okTv = (TextView) view.findViewById(R.id.okTv);
		sex.setAdapter(new ArrayWheelAdapter<String>(sexStrs,2));
		sex.addScrollingListener(scrollListener);
		return view;
	}

	/**
	 * 性别选择器滚动事件
	 */
	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			eduTv.setText(""+sexStrs[sex.getCurrentItem()]);
			updateModifyStatus();
		}
	};

	@Override
	public void addLisener() {
		rightTv.setOnClickListener(this);
		startTv.setOnClickListener(this);

		endTv.setOnClickListener(this);
		engTv.setOnClickListener(this);

		nameEt.addTextChangedListener(this);
		orderEt.addTextChangedListener(this);
		proEt.addTextChangedListener(this);
		schEt.addTextChangedListener(this);
		courseEt.addTextChangedListener(this);

		eduTv.setOnClickListener(this);
		whiteView.setOnClickListener(this);
		okTv.setOnClickListener(this);
	}

	private void initData() {
		String data = sp.read(Contacts.EDUCATION2, "");
		if(!Utils.isEmpty(data)){
			try {
				bean = JsonUtil.jsonToBean(data, EducationExperience.class);
				nameEt.setText(bean.getName());
				proEt.setText(bean.getProfessional());
				eduTv.setText(bean.getEducation());
				startTv.setText(bean.getStart());
				orderEt.setText(bean.getOrder());
				engTv.setText(bean.getEnglish());
				schEt.setText(bean.getScholarship());
				courseEt.setText(bean.getCourse());
				endTv.setText(bean.getEnd());
				//从本地加载图片为头像
				FileInputStream fis = new FileInputStream(Utils.getSDPath()+"/"+Contacts.SCHOOLFILENAME2);
				Bitmap face = BitmapFactory.decodeStream(fis);
				faceIv.setImageBitmap(face);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}



	private void saveData(){
		EducationExperience newBean = new EducationExperience(nameStr,startStr, endTvStr, eduStr, proStr, orderStr, courseStr, engStr, schStr);
		sp.write(Contacts.EDUCATION2, JsonUtil.objectToJson(newBean));
	}

	@Override
	public OnClickListener getBackClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(isModified){
					//弹出是否放弃编辑弹出框
					showDialog(mDialogBean);
				}else{
					finish();
				}
			}
		};
	}

	@Override
	public void onClick(View view) {
		Intent mIntent = null;
		switch (view.getId()) {
			case R.id.rightTv:
				if(isModified){
					nameStr = nameEt.getText().toString().replace(" ", "");
					eduStr = eduTv.getText().toString();
					startStr = startTv.getText().toString();
					orderStr = orderEt.getText().toString();
					engStr = engTv.getText().toString();
					schStr = schEt.getText().toString();
					courseStr = courseEt.getText().toString();
					endTvStr = endTv.getText().toString();
					proStr = proEt.getText().toString();

					String tip = checkValue();
					if(Utils.isEmpty(tip)){
						saveData();
						finish();
					}else{
						showToast(tip);
					}
				}else{
					finish();
				}
				break;
			case R.id.eduTv:
//			rl.setVisibility(View.VISIBLE);
				mIntent = new Intent(context, RadioActivity.class);
				mIntent.putExtra("items", getResources().getStringArray(R.array.education_edu));
				mIntent.putExtra("title", "选择学历");
				mIntent.putExtra("resultCode", eduCode);
				startActivityForResult(mIntent, 1);
				break;
			case R.id.startTv:
				mIntent = new Intent(context, ChoiceDateActivity.class);
				mIntent.putExtra("title", "选择开始时间");
				mIntent.putExtra("resultCode", starCode);
				startActivityForResult(mIntent, 1);
				break;
			case R.id.engTv:
				mIntent = new Intent(context, RadioActivity.class);
				mIntent.putExtra("items", getResources().getStringArray(R.array.education_english));
				mIntent.putExtra("title", "选择等级");
				mIntent.putExtra("resultCode", engCode);
				startActivityForResult(mIntent, 1);
				break;
			case R.id.endTv:
				mIntent = new Intent(context, ChoiceDateActivity.class);
				mIntent.putExtra("title", "选择结束时间");
				mIntent.putExtra("resultCode", endCode);
				startActivityForResult(mIntent, 1);
				break;
			case R.id.whiteView:
				rl.setVisibility(View.GONE);
				break;
			case R.id.okTv:
				eduTv.setText(""+sexStrs[sex.getCurrentItem()]);
				rl.setVisibility(View.GONE);
				updateModifyStatus();
				break;
			default:
				break;
		}
	}

	private String checkValue(){
		String tip = "";
		if(Utils.isEmpty(nameStr)){
			tip = "请填写学校名字";
		}else if(Utils.isEmpty(eduStr)){
			tip = "请选择学历";
		}else if(Utils.isEmpty(startStr)){
			tip = "请选择开始时间";
		}else if(Utils.isEmpty(endTvStr)){
			tip = "请选择毕业时间";
		}else if(Utils.isEmpty(proStr)){
			tip = "请选择专业";
		}else if(Utils.isEmpty(orderStr)){
			tip = "请填写班级名次";
		}else if(Utils.isEmpty(engStr)){
			tip = "请选择英语水平";
		}else if(Utils.isEmpty(schStr)){
			tip = "请填写奖学金";
		}else if(Utils.isEmpty(courseStr)){
			tip = "请填写选修课程";
		}
		return tip;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 用户没有进行有效的设置操作，返回
		if (resultCode == RESULT_CANCELED) {
			return;
		}

		switch (requestCode) {
			case CODE_GALLERY_REQUEST:
				cropRawPhoto(data.getData());
				break;

			case CODE_RESULT_REQUEST:
				if (data != null) {
					setImageToHeadView(data);
					updateModifyStatus();
				}

				break;
			case 1:
				if (resultCode == starCode) {
					startTv.setText(data.getStringExtra("date"));
				}
				if (resultCode == endCode) {
					endTv.setText(data.getStringExtra("date"));
				}

				if(resultCode == eduCode){
					eduTv.setText(data.getStringExtra("text"));
				}
				if(resultCode == engCode){
					engTv.setText(data.getStringExtra("text"));
				}
				updateModifyStatus();
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	/**
	 * 从本地相册选取图片作为头像
	 */
	private void choseHeadImageFromGallery() {
		Intent intentFromGallery = new Intent();
		// 设置文件类型
		intentFromGallery.setType("image/*");
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
	}


	/**
	 * 裁剪原始的图片
	 */
	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		// 设置裁剪
		intent.putExtra("crop", "true");

		// aspectX , aspectY :宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : 裁剪图片宽高
		intent.putExtra("outputX", output_X);
		intent.putExtra("outputY", output_Y);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, CODE_RESULT_REQUEST);
	}

	/**
	 * 提取保存裁剪之后的图片数据，并设置头像部分的View
	 */
	private void setImageToHeadView(Intent intent) {

		Bundle extras = intent.getExtras();
		if (extras != null) {

			Bitmap photo = extras.getParcelable("data");

			//将Bitmap 转换成二进制，写入本地
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			/////////////////////

			////////////////////
			String path = Utils.getSDPath();
			File dir = new File(path);
			if (!dir.isFile()) {
				dir.mkdir();
			}
			File file = new File(dir, Contacts.SCHOOLFILENAME2);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				fos.write(byteArray, 0, byteArray.length);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					fos.close();
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			//saveData();
			faceIv.setImageBitmap(photo);
		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		updateModifyStatus();
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                              int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}

}
