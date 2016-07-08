package com.yqy.myresume.activity.info;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

import com.yqy.myresume.R;
import com.yqy.myresume.activity.CommonActivity;
import com.yqy.myresume.activity.utils.RadioActivity;
import com.yqy.myresume.bean.DialogBean;
import com.yqy.myresume.bean.UserInfo;
import com.yqy.myresume.utils.Contacts;
import com.yqy.myresume.utils.JsonUtil;
import com.yqy.myresume.utils.ModelUtils;
import com.yqy.myresume.utils.Utils;
import com.yqy.myresume.view.datepicker.wheelview.ArrayWheelAdapter;
import com.yqy.myresume.view.datepicker.wheelview.OnWheelScrollListener;
import com.yqy.myresume.view.datepicker.wheelview.WheelView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description:编辑个人信息
 * @param <T>
 */
public class InfoModifyActivity<T> extends CommonActivity implements OnClickListener,TextWatcher {
	/* 请求识别码 */
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	private static final int CODE_RESULT_REQUEST = 0xa2;

	// 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
	private static int output_X = 128;
	private static int output_Y = 128;


	private TextView rightTv;
	
	private EditText nameEt,workingageEt,telEt,emailEt,hometownEt;
	private TextView sexTv,birthTv,ageTv,starTv;

	private UserInfo bean;
	private boolean isModified = false;//是否修改，用于是否展示是否放弃编辑弹出框
	
	private String nameStr,sexStr,birthStr,ageStr,starStr,
					workingageStr,telStr,emailStr,hometownStr;

	private ImageView faceIv;//头像
	
	//显示性别选择器
	private RelativeLayout rl;
	private LinearLayout sexLl;//性别选择器父控件
	private View whiteView;//点击空白关闭性别选择器
	private TextView okTv;//完成
	private LayoutInflater inflater = null;
	private WheelView sex;
	private String[] sexStrs = new String[]{"男","女"};
	
	private DialogBean mDialogBean;

	@Override
	protected void showNextPage() {

	}

	@Override
	protected void showPreviousPage() {

	}

	@Override
	public void initView() {
		setTitleAndContentLayoutId("编辑资料", R.layout.activity_info_modify);
		showTextRightBtn("完成");
		showTextLeftBtn("取消");

		rightTv = (TextView) findViewById(R.id.rightTv);
		
		nameEt = (EditText) findViewById(R.id.nameEt);
		workingageEt = (EditText) findViewById(R.id.workingageEt);
		telEt = (EditText) findViewById(R.id.telEt);
		emailEt = (EditText) findViewById(R.id.emailEt);
		hometownEt = (EditText) findViewById(R.id.hometownEt);

		faceIv = (ImageView) findViewById(R.id.faceIv);
		faceIv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				choseHeadImageFromGallery();//从本地选择头像
			}
		});

		sexTv = (TextView) findViewById(R.id.sexTv);
		birthTv = (TextView) findViewById(R.id.birthTv);
		ageTv = (TextView) findViewById(R.id.ageTv);
		starTv = (TextView) findViewById(R.id.starTv);
		
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
			sexTv.setText(""+sexStrs[sex.getCurrentItem()]);
			updateModifyStatus();
		}
	};

	@Override
	public void addLisener() {
		rightTv.setOnClickListener(this);
		birthTv.setOnClickListener(this);
		ageTv.setOnClickListener(this);
		starTv.setOnClickListener(this);
		
		nameEt.addTextChangedListener(this);
		workingageEt.addTextChangedListener(this);
		telEt.addTextChangedListener(this);
		emailEt.addTextChangedListener(this);
		hometownEt.addTextChangedListener(this);
		
		sexTv.setOnClickListener(this);
		whiteView.setOnClickListener(this);
		okTv.setOnClickListener(this);
	}
	
	private void initData() {
		String data = sp.read(Contacts.USERINFO, "");
		if(!Utils.isEmpty(data)){
			try {
				bean = JsonUtil.jsonToBean(data, UserInfo.class);
				nameEt.setText(bean.getName());
				ageTv.setText(Utils.getAgeByBirthday(bean.getBirth()));
				sexTv.setText(bean.getSex());
				birthTv.setText(bean.getBirth());
				workingageEt.setText(bean.getWorkingage());
				telEt.setText(bean.getTel());
				emailEt.setText(bean.getEmail());
				hometownEt.setText(bean.getHometown());
				starTv.setText(Utils.getStarByBirth(bean.getBirth()));
				//从本地加载图片为头像
				FileInputStream fis = new FileInputStream(Utils.getSDPath()+"/"+Contacts.FACEFILENAME);
				Bitmap face = BitmapFactory.decodeStream(fis);
				faceIv.setImageBitmap(face);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void saveData(){
		UserInfo newBean = new UserInfo(nameStr,sexStr,birthStr,workingageStr,telStr,emailStr,hometownStr);
		sp.write(Contacts.USERINFO, JsonUtil.objectToJson(newBean));
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
		switch (view.getId()) {
		case R.id.rightTv:
			if(isModified){
				nameStr = nameEt.getText().toString().replace(" ", "");
				sexStr = sexTv.getText().toString();
				birthStr = birthTv.getText().toString();
				workingageStr = workingageEt.getText().toString();
				telStr = telEt.getText().toString();
				emailStr = emailEt.getText().toString();
				hometownStr = hometownEt.getText().toString();
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
		case R.id.sexTv:
//			rl.setVisibility(View.VISIBLE);
			Intent mIntent = new Intent(context, RadioActivity.class);
			mIntent.putExtra("items", getResources().getStringArray(R.array.info_sex));
			mIntent.putExtra("title", "选择性别");
			mIntent.putExtra("resultCode", 2);
			startActivityForResult(mIntent, 1);
			break;
		case R.id.birthTv:
		case R.id.ageTv:
		case R.id.starTv:
			//跳转到选择年龄界面
			startActivityForResult(new Intent(context, InfoChooiceDateActivity.class),1);
			break;
		case R.id.whiteView:
			rl.setVisibility(View.GONE);
			break;
		case R.id.okTv:
			sexTv.setText(""+sexStrs[sex.getCurrentItem()]);
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
			tip = "请填写姓名";
		}else if(Utils.isEmpty(sexStr)){
			tip = "请选择性别";
		}else if(Utils.isEmpty(birthStr)){
			tip = "请选择生日";
		}else if(Utils.isEmpty(workingageStr)){
			tip = "请填写工作年龄";
		}else if(Utils.isEmpty(telStr)){
			tip = "请填写手机号";
		}else if(!ModelUtils.isMobileNO(telStr)){
			tip = "请填写正确的手机号";
		}else if(Utils.isEmpty(emailStr)){
			tip = "请填写邮箱";
		}else if(!ModelUtils.isEmailValid(emailStr)){
			tip = "请填写正确的邮箱";
		}else if(Utils.isEmpty(hometownStr)){
			tip = "请填写故乡所在地";
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
				if(resultCode == 1){
					birthTv.setText(data.getStringExtra("birth"));
					ageTv.setText(data.getStringExtra("age"));
					starTv.setText(data.getStringExtra("star"));
				}
				if(resultCode == 2){
					sexTv.setText(data.getStringExtra("text"));
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
			File file = new File(dir, Contacts.FACEFILENAME);
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