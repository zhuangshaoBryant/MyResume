package com.yqy.myresume.bean;

import android.view.View.OnClickListener;

/**
 * dialog属性实体类
 */
public class DialogBean {
	private String title;// 标题
	private String content;// 内容
	private String left;// 左侧文字
	private String center;// 中部文字
	private String right;// 右侧文字
	private OnClickListener leftListenr;// 左侧按钮单击监听事件
	private OnClickListener centerListenr;// 中部按钮
	private OnClickListener rightListenr;// 右侧按钮

	private boolean cancelable = true;//点击返回键是否关闭dialog
	private boolean canceledOnTouchOutside = true;//点击边缘是否关闭dialog
	
	public String getTitle() {
		return title;
	}

	/**
	 * 标题
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	/**
	 * 内容
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public String getLeft() {
		return left;
	}

	/**
	 * 左侧按钮文字
	 * @param left
	 */
	public void setLeft(String left) {
		this.left = left;
	}

	public String getCenter() {
		return center;
	}

	/**
	 * 中部按钮文字
	 * @param center
	 */
	public void setCenter(String center) {
		this.center = center;
	}

	public String getRight() {
		return right;
	}

	/**
	 * 右侧按钮文字
	 * @param right
	 */
	public void setRight(String right) {
		this.right = right;
	}

	public OnClickListener getLeftListenr() {
		return leftListenr;
	}

	/**
	 * 左侧按钮单击监听事件
	 * @param leftListenr
	 */
	public void setLeftListenr(OnClickListener leftListenr) {
		this.leftListenr = leftListenr;
	}

	public OnClickListener getCenterListenr() {
		return centerListenr;
	}

	/**
	 * 中部按钮单击监听事件
	 * @param centerListenr
	 */
	public void setCenterListenr(OnClickListener centerListenr) {
		this.centerListenr = centerListenr;
	}

	public OnClickListener getRightListenr() {
		return rightListenr;
	}

	/**
	 * 右侧按钮单击监听事件
	 * @param rightListenr
	 */
	public void setRightListenr(OnClickListener rightListenr) {
		this.rightListenr = rightListenr;
	}

	public boolean isCancelable() {
		return cancelable;
	}

	/**
	 * 点击返回键是否可关闭
	 * @param cancelable
	 */
	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	public boolean isCanceledOnTouchOutside() {
		return canceledOnTouchOutside;
	}

	/**
	 * 点击边缘是否关闭dialog
	 * @param canceledOnTouchOutside
	 */
	public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
		this.canceledOnTouchOutside = canceledOnTouchOutside;
	}
}
