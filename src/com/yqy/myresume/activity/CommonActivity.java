package com.yqy.myresume.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yqy.myresume.R;
import com.yqy.myresume.bean.DialogBean;
import com.yqy.myresume.utils.Utils;

public abstract class CommonActivity extends AbstractActivity {
	private GestureDetector mGestureDetector;
	private RelativeLayout topLayout;
	private TextView leftTv, centerTv, rightTv;
	private ImageView leftIv, rightIv;
	private LinearLayout contentLayout;

	@Override
	public void init() {
		topLayout = (RelativeLayout) findViewById(R.id.topLayout);
		leftTv = (TextView) findViewById(R.id.leftTv);
		centerTv = (TextView) findViewById(R.id.centerTv);
		rightTv = (TextView) findViewById(R.id.rightTv);
		leftIv = (ImageView) findViewById(R.id.leftIv);
		rightIv = (ImageView) findViewById(R.id.rightIv);

		contentLayout = (LinearLayout) findViewById(R.id.contentLayout);
		OnClickListener mListener = getBackClickListener();
		if (mListener == null) {
			leftTv.setOnClickListener(new OnBackClickLisener());
			leftIv.setOnClickListener(new OnBackClickLisener());
		} else {
			leftTv.setOnClickListener(mListener);
			leftIv.setOnClickListener(mListener);
		}

		initView();
		addLisener();

		mGestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {

					/**
					 * 监听手势滑动事件 e1表示滑动的起点,e2表示滑动终点 velocityX表示水平速度
					 * velocityY表示垂直速度
					 */
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
					                       float velocityX, float velocityY) {

						// 判断纵向滑动幅度是否过大, 过大的话不允许切换界面
						if (Math.abs(e1.getRawY() - e2.getRawY()) > 100) {
							return false;
						}

						// 判断滑动是否过慢
						if (Math.abs(velocityX) < 50) {
							//showShortToast("滑的太慢了");
							return true;
						}

						//向左滑
						if (e1.getRawX() - e2.getRawX() > 100) {

							showNextPage();
							return true;
						}

						//向右滑
						if (e2.getRawX() - e1.getRawX() > 100) {
							showPreviousPage();
							return true;
						}

						return super.onFling(e1, e2, velocityX, velocityY);
					}
				});
	}

	protected abstract void showNextPage();
	protected abstract void showPreviousPage();

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev){
		mGestureDetector.onTouchEvent(ev); //让GestureDetector响应触碰事件
		return super.dispatchTouchEvent(ev); //让Activity响应触碰事件
	}

	public View inflaterGetView(Context context, int layoutId) {
		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	public void hideTitleBar(){
		topLayout.setVisibility(View.GONE);
		RelativeLayout.LayoutParams mLayoutParams = (RelativeLayout.LayoutParams) contentLayout.getLayoutParams();
		mLayoutParams.setMargins(0,0,0,0);
		contentLayout.setLayoutParams(mLayoutParams);
	}

	/**
	 * 默认次设置
	 */
	public void showImageLeftBtn() {
		leftTv.setVisibility(View.GONE);
		leftIv.setVisibility(View.VISIBLE);
	}

	public void hideCenter() {
		centerTv.setVisibility(View.GONE);
	}

	public void hideLeft() {
		leftTv.setVisibility(View.GONE);
		leftIv.setVisibility(View.GONE);
	}

	public void hideRight() {
		rightTv.setVisibility(View.GONE);
		rightIv.setVisibility(View.GONE);
	}

	public void showTextCenterBtn(String title) {
		if (Utils.isEmpty(title))
			centerTv.setVisibility(View.GONE);
		else {
			centerTv.setVisibility(View.VISIBLE);
			centerTv.setText(title);
		}
	}

	/**
	 * 左侧按钮文字
	 * @param str
	 */
	public void showTextLeftBtn(String str) {
		leftTv.setVisibility(View.VISIBLE);
		leftIv.setVisibility(View.GONE);
		if (Utils.isEmpty(str))
			leftTv.setText("返回");
		else
			leftTv.setText(str);
	}

	/**
	 * 右侧按钮文字
	 *
	 * @param str
	 */
	public void showTextRightBtn(String str) {
		rightTv.setVisibility(View.VISIBLE);
		rightIv.setVisibility(View.GONE);
		if (Utils.isEmpty(str))
			rightTv.setText("完成");
		else
			rightTv.setText(str);
	}

	public void setTitleAndContentLayoutId(String title, int layoutId) {
		getLayoutInflater().inflate(layoutId, (ViewGroup) contentLayout);
		if (Utils.isEmpty(title))
			centerTv.setVisibility(View.GONE);
		else
			centerTv.setText(title);
	}

	public abstract void initView();

	public abstract void addLisener();

	public abstract OnClickListener getBackClickListener();

	class OnBackClickLisener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			finish();
		}
	}

	@Override
	public Integer createView() {
		return R.layout.common_title_bar;
	}

	/********************** dialog开始 ***************************/

	private LinearLayout titleLl, contentLl, btnLl;
	private TextView titleTv, contentTv;
	private Button leftBtn, centerBtn, rightBtn;

	private Dialog mDialog;

	public void showDialog(DialogBean mDialogBean) {
		if (mDialog == null) {
			mDialog = new Dialog(context, R.style.dialog);
			mDialog.setContentView(R.layout.common_dialog);
			mDialog.setCancelable(mDialogBean.isCancelable());
			mDialog.setCanceledOnTouchOutside(mDialogBean
					.isCanceledOnTouchOutside());
			titleLl = (LinearLayout) mDialog.findViewById(R.id.titleLl);
			contentLl = (LinearLayout) mDialog.findViewById(R.id.contentLl);
			btnLl = (LinearLayout) mDialog.findViewById(R.id.btnLl);
			titleTv = (TextView) mDialog.findViewById(R.id.titleTv);
			contentTv = (TextView) mDialog.findViewById(R.id.contentTv);
			leftBtn = (Button) mDialog.findViewById(R.id.leftBtn);
			centerBtn = (Button) mDialog.findViewById(R.id.centerBtn);
			rightBtn = (Button) mDialog.findViewById(R.id.rightBtn);
			if (!Utils.isEmpty(mDialogBean.getTitle())) {
				titleLl.setVisibility(View.VISIBLE);
				titleTv.setText(mDialogBean.getTitle());
			}
			if (!Utils.isEmpty(mDialogBean.getContent())) {
				contentLl.setVisibility(View.VISIBLE);
				contentTv.setText(mDialogBean.getContent());
			}
			if (!Utils.isEmpty(mDialogBean.getLeft())
					&& mDialogBean.getLeftListenr() != null) {
				if (btnLl.getVisibility() == View.GONE)
					btnLl.setVisibility(View.VISIBLE);
				leftBtn.setVisibility(View.VISIBLE);
				leftBtn.setText(mDialogBean.getLeft());
				leftBtn.setOnClickListener(mDialogBean.getLeftListenr());
			}
			if (!Utils.isEmpty(mDialogBean.getCenter())
					&& mDialogBean.getCenterListenr() != null) {
				if (btnLl.getVisibility() == View.GONE)
					btnLl.setVisibility(View.VISIBLE);
				centerBtn.setVisibility(View.VISIBLE);
				centerBtn.setText(mDialogBean.getCenter());
				centerBtn.setOnClickListener(mDialogBean.getCenterListenr());
			}
			if (!Utils.isEmpty(mDialogBean.getRight())
					&& mDialogBean.getRightListenr() != null) {
				if (btnLl.getVisibility() == View.GONE)
					btnLl.setVisibility(View.VISIBLE);
				rightBtn.setVisibility(View.VISIBLE);
				rightBtn.setText(mDialogBean.getRight());
				rightBtn.setOnClickListener(mDialogBean.getRightListenr());
			}
		}
		mDialog.show();
	}

	public void hideDialog() {
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
	}

	/********************** dialog结束 ***************************/

	/** 
     * 菜单、返回键响应 
     */  
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键  
        	if(leftTv.getVisibility() == View.VISIBLE){
        		leftTv.performClick();
        	}else if(leftIv.getVisibility() == View.VISIBLE){
        		leftIv.performClick();
        	}
            return true; 
        }
//        else if (keyCode == KeyEvent.KEYCODE_MENU) {// MENU键  
//            return true;  
//        }  
        return super.onKeyDown(keyCode, event);  
    }  
	
}
