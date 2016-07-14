package com.yqy.myresume.activity.share;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;


import com.yqy.myresume.R;
import com.yqy.myresume.activity.CommonActivity;
import com.yqy.myresume.activity.info.InfoModifyActivity;
import com.yqy.myresume.interfacepackage.JsCallJavaInterface;
import com.yqy.myresume.utils.Contacts;
import com.yqy.myresume.utils.SPUtil;

public class ShareWebviewActivity extends CommonActivity {

	private WebView mWebView;
	 private JsCallJavaInterface jsCallJavaMethod;
	private String info;//个人信息
	private String education;
	private String education2;
	private String project;
	private TextView rightTv;

	@Override
	public void initView() {
		setTitleAndContentLayoutId("简历预览", R.layout.activity_share_webview);
		showTextRightBtn("预览");
		showTextLeftBtn("返回");

		mWebView = (WebView) findViewById(R.id.webview);
		rightTv = (TextView) findViewById(R.id.rightTv);
		setWebView();
		initData();

		rightTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				//mWebView.loadUrl("javascript:jscallbynative()");// 无参数调用js方法
				mWebView.loadUrl("javascript:info('" + info + "'+" + "''" + ")");
				mWebView.loadUrl("javascript:education('" + education + "'+" + "''" + ")");
				mWebView.loadUrl("javascript:education1('" + education2 + "'+" + "''" + ")");
				mWebView.loadUrl("javascript:project('" + project + "'+" + "''" + ")");
				rightTv.setVisibility(View.GONE);
			}
		});

	}

	private void initData() {
		info = sp.read(Contacts.USERINFO, "");
		education = sp.read(Contacts.EDUCATION, "");
		education2 = sp.read(Contacts.EDUCATION2, "");
		project = sp.read(Contacts.PROJECTEXPERIENCE, "");
		Log.d("壮少",education);
		Log.d("壮少",info);
		Log.d("壮少",education2);
		Log.d("壮少",project);
	}

	@SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
	private void setWebView() {

		// JavaScript使能（如果加载的页面中有JS代码，则必须使能JS）
		WebSettings webSettings = mWebView.getSettings();
		// 启用javascript
		webSettings.setJavaScriptEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setBuiltInZoomControls(true);

		// 在WebView中打开连接（默认行为是使用浏览器，设置此项后都用WebView打开）
		mWebView.setWebViewClient(new WebViewClient());

		// 从assets目录下面的加载html
		mWebView.loadUrl("file:///android_asset/aa/index.html");

		//在JS里调用接口的某个方法，从而可以调用android的方法
		jsCallJavaMethod = new JsCallJavaInterface() {

			@Override
			@JavascriptInterface
			public void javaFunction() {// js 调用（无参）Android原生接口中方法
				runOnUiThread(new Runnable() {

					@Override
					public void run() {

					}
				});

			}

			@JavascriptInterface
			public void javaFunction(final String str) {// js 调用（有参）Android原生接口中方法
				runOnUiThread(new Runnable() {

					@Override
					public void run() {

					}
				});
			}

		};
		mWebView.addJavascriptInterface(jsCallJavaMethod, "InterfaceTest");//InterfaceTest可以在js'里调用安卓里的jsCallJavaMethod对象的方法

	}


	@Override
	protected void showNextPage() {

	}

	@Override
	protected void showPreviousPage() {

	}



	@Override
	public void addLisener() {

	}

	@Override
	public View.OnClickListener getBackClickListener() {
		return null;
	}
}
