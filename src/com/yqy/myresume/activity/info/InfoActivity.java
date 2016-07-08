package com.yqy.myresume.activity.info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.GestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.yqy.myresume.R;
import com.yqy.myresume.activity.CommonActivity;
import com.yqy.myresume.activity.education.Education2Activity;
import com.yqy.myresume.activity.education.EducationActivity;
import com.yqy.myresume.bean.UserInfo;
import com.yqy.myresume.utils.Contacts;
import com.yqy.myresume.utils.JsonUtil;
import com.yqy.myresume.utils.Utils;
import com.yqy.myresume.view.NotifyingScrollView;
import com.yqy.myresume.view.NotifyingScrollView.OnScrollChangedListener;

import java.io.File;
import java.io.FileInputStream;

/**
 * 我的信息
 */
public class InfoActivity extends CommonActivity {


	private ImageView faceIv;//头像
	private NotifyingScrollView myScrollView;//滑动父控件
	private LinearLayout contentLl;//所有行的集合
	private RelativeLayout mainInfoRl;//壮少23岁男
	private View sizeView;//背景图
	private View bgView;

	private AlphaAnimation showAnim, hideAnim;
	private long time = 100;
	private boolean flag = false;
	String facePath = Utils.getSDPath()+"/"+Contacts.FACEFILENAME;//头像路径
	
	private TextView nameTv,ageTv,sexTv,birthTv,starTv,workingageTv,telTv,emailTv,hometownTv;
	private UserInfo bean;


	@Override
	protected void showNextPage() {
		startActivity(new Intent(this, EducationActivity.class));
		finish();

		//两个界面切换的动画
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//进入动画和退出动画
	}

	@Override
	protected void showPreviousPage() {

	}

	@SuppressLint("NewApi")
	@Override
	public void initView() {
		setTitleAndContentLayoutId("", R.layout.activity_info);
		showTextRightBtn("编辑");
		showTextLeftBtn("返回");

		myScrollView = (NotifyingScrollView) findViewById(R.id.myScrollView);
		sizeView = findViewById(R.id.sizeView);
		mainInfoRl = (RelativeLayout) findViewById(R.id.mainInfoRl);
		bgView = findViewById(R.id.bgView);
		bgView.setVisibility(View.GONE);



		showAnim = new AlphaAnimation(0f, 1f);
		showAnim.setDuration(time);

		hideAnim = new AlphaAnimation(1f, 0f);
		hideAnim.setDuration(time);

		nameTv = (TextView) findViewById(R.id.nameTv);
		ageTv = (TextView) findViewById(R.id.ageTv);
		sexTv = (TextView) findViewById(R.id.sexTv);
		birthTv = (TextView) findViewById(R.id.birthTv);
		starTv = (TextView) findViewById(R.id.starTv);
		workingageTv = (TextView) findViewById(R.id.workingageTv);
		telTv = (TextView) findViewById(R.id.telTv);
		emailTv = (TextView) findViewById(R.id.emailTv);
		hometownTv = (TextView) findViewById(R.id.hometownTv);
		faceIv = (ImageView) findViewById(R.id.faceIv);

	}



	private void initData() {
		String data = sp.read(Contacts.USERINFO, "");
		if(!Utils.isEmpty(data)){
			try {
				bean = JsonUtil.jsonToBean(data, UserInfo.class);
				nameTv.setText(bean.getName());
				ageTv.setText(Utils.getAgeByBirthday(bean.getBirth()));
				sexTv.setText(bean.getSex());
				birthTv.setText(bean.getBirth());
				workingageTv.setText(bean.getWorkingage());
				telTv.setText(bean.getTel());
				emailTv.setText(bean.getEmail());
				hometownTv.setText(bean.getHometown());
				starTv.setText(Utils.getStarByBirth(bean.getBirth()));
				//从本地加载图片为头像

				FileInputStream fis = new FileInputStream(facePath);
				Bitmap face = BitmapFactory.decodeStream(fis);
				faceIv.setImageBitmap(face);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}

	@Override
	public void addLisener() {
		
		findViewById(R.id.rightTv).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(context,InfoModifyActivity.class));
			}
		});
		
		ViewTreeObserver vto = sizeView.getViewTreeObserver();

		//在开始绘画之前，将壮少23男放在最下方的位置
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {

				if (!flag){
//					height = rl.getMeasuredHeight();
					mainInfoRl.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,sizeView.getMeasuredHeight()));
					mainInfoRl.setVisibility(View.VISIBLE);
					flag = true;
				}
				return true;
			}
		});
		
		
		showAnim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				bgView.setVisibility(View.VISIBLE);
			}
		});
		hideAnim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				bgView.setVisibility(View.GONE);

			}
		});
		myScrollView.setOnScrollChangedListener(new OnScrollChangedListener() {

			@SuppressLint("NewApi")
			@Override
			public void onScrollChanged(ScrollView who, int l, int t, int oldl,
					int oldt) {
				int height = Utils.dip2px(context, 130);

				if (oldt < t && height <= t && height >= oldt) {
					bgView.startAnimation(showAnim);
					showTextCenterBtn("我的信息");
				} else if (oldt > t && height >= t && height <= oldt) {
					showTextCenterBtn("");
					bgView.startAnimation(hideAnim);
				}

			}
		});
	}

	@Override
	public OnClickListener getBackClickListener() {
		return null;
	}

}
