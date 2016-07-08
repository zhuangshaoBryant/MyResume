package com.yqy.myresume.activity.education;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yqy.myresume.R;
import com.yqy.myresume.activity.CommonActivity;
import com.yqy.myresume.activity.info.InfoModifyActivity;
import com.yqy.myresume.activity.project.ProjectLvActivity;
import com.yqy.myresume.bean.EducationExperience;
import com.yqy.myresume.utils.Contacts;
import com.yqy.myresume.utils.JsonUtil;
import com.yqy.myresume.utils.Utils;
import com.yqy.myresume.view.NotifyingScrollView;
import com.yqy.myresume.view.NotifyingScrollView.OnScrollChangedListener;

import java.io.FileInputStream;

/**
 * 教育信息
 */
public class Education2Activity extends CommonActivity {
	private ImageView faceIv;//头像
	private NotifyingScrollView myScrollView;//滑动父控件
	private LinearLayout contentLl;//所有行的集合
	private RelativeLayout mainInfoRl;//壮少23岁男
	private View sizeView;//背景图
	private View bgView;
	private AlphaAnimation showAnim, hideAnim;
	private long time = 100;
	private boolean flag = false;
	String facePath = Utils.getSDPath()+"/"+Contacts.SCHOOLFILENAME2;//头像路径

	private TextView nameTv, proTv, eduTv, startTv, endTv, orderTv, engTv, schTv, courseTv;
	private EducationExperience bean;

	@SuppressLint("NewApi")
	@Override
	public void initView() {
		setTitleAndContentLayoutId("", R.layout.activity_education2);
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
		proTv = (TextView) findViewById(R.id.proTv);
		eduTv = (TextView) findViewById(R.id.eduTv);
		startTv = (TextView) findViewById(R.id.startTv);
		endTv = (TextView) findViewById(R.id.endTv);
		orderTv = (TextView) findViewById(R.id.orderTv);
		engTv = (TextView) findViewById(R.id.engTv);
		schTv = (TextView) findViewById(R.id.schTv);
		courseTv = (TextView) findViewById(R.id.courseTv);
		faceIv = (ImageView) findViewById(R.id.faceIv);

	}

	protected void showNextPage() {
		startActivity(new Intent(this, ProjectLvActivity.class));
		finish();

		//两个界面切换的动画
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);//进入动画和退出动画
	}

	private void initData() {
		String data = sp.read(Contacts.EDUCATION2, "");
		if(!Utils.isEmpty(data)){
			try {
				bean = JsonUtil.jsonToBean(data, EducationExperience.class);
				nameTv.setText(bean.getName());
				proTv.setText(bean.getProfessional());
				eduTv.setText(bean.getEducation());
				startTv.setText(bean.getStart());
				orderTv.setText(bean.getOrder());
				engTv.setText(bean.getEnglish());
				schTv.setText(bean.getScholarship());
				courseTv.setText(bean.getCourse());
				endTv.setText(bean.getEnd());
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

	public void showPreviousPage() {
		startActivity(new Intent(this, EducationActivity.class));
		finish();
		overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
	}

	@Override
	public void addLisener() {

		findViewById(R.id.rightTv).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(context,EducationModify2Activity.class));
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
					showTextCenterBtn("教育经历");
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
