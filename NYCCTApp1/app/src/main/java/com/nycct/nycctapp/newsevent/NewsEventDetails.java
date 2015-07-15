package com.nycct.nycctapp.newsevent;

import com.nycct.nycctapp.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class NewsEventDetails extends Activity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_event_details_layout);

		webView = (WebView) findViewById(R.id.newsEvnentWebView);
		String url = getIntent().getStringExtra("url");
		setWebViewConfiguration(url);
		
	}
	
	public void setWebViewConfiguration(String url){
		
		webView.loadUrl(url);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.setScrollbarFadingEnabled(false);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.setWebViewClient(new CustomWebView());
	}

	private class CustomWebView extends WebViewClient {

		ProgressDialog progressDialog;

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onLoadResource(WebView view, String url) {
			super.onLoadResource(view, url);
			
			if (progressDialog == null) {
				progressDialog = new ProgressDialog(NewsEventDetails.this);
				progressDialog.setMessage("Loading...");
				progressDialog.show();
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			
			try {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
					progressDialog = null;
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

		@Override
		public void onFormResubmission(WebView view, Message dontResend, Message resend) {
			super.onFormResubmission(view, dontResend, resend);
			resend.sendToTarget();
		}

	}

	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			super.onBackPressed();
		}
	}

}
