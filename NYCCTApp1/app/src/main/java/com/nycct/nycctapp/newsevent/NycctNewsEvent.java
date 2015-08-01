package com.nycct.nycctapp.newsevent;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.nycct.nycctapp.R;
import com.nycct.nycctapp.model.NewsEventEntity;
import com.nycct.nycctapp.util.AppConstant;


public class NycctNewsEvent extends AsyncTask<Void, Void, Void>{

	private String newsEventUrl = AppConstant.NEWS_EVENT_URL;
	private ProgressDialog progressDialog;
	private ArrayList<NewsEventEntity> newsEventList=new ArrayList<NewsEventEntity>();
	private LinearLayout newsEventGallery;
	private Context context;
	private String url;
	private String title;
	
	public NycctNewsEvent(Context context,LinearLayout newsEventGallery){
		this.context=context;
		this.newsEventGallery=newsEventGallery;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle("NYCCT News and Event");
		progressDialog.setMessage("Loading...");
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(true);
		progressDialog.show();
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {

			Document doc = Jsoup.connect(newsEventUrl).get();
			Elements newsLinks = doc.select("div#newscol a"); // news
			Elements eventLinks = doc.select("div#eventscol ul li a"); // events
			
			// get event links
			for (Element link : eventLinks) {

				url=link.attr("abs:href");
				title=link.text();
				newsEventList.add(new NewsEventEntity(url, title));

				Log.d("event url : ", url);
				Log.d("event title : ", title);
			}
			
			// get news links
			for (Element link : newsLinks) {

				url=link.attr("abs:href");
				title=link.text();
				newsEventList.add(new NewsEventEntity(url, title));
				
				Log.d("news url : ", url);
				Log.d("news title : ", title);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		populateEventGallery();
		
		if(progressDialog.isShowing()){
			progressDialog.dismiss();
		}else{
			// do nothing
		}
		
	}
	
	public void populateEventGallery(){
		
		for(NewsEventEntity newsEventEntity:newsEventList){
			
			String newsTitle=newsEventEntity.getText();
			String newsUrl=newsEventEntity.getUrl();
			newsEventGallery.addView(getNewsEventView(newsTitle, newsUrl));
		}
	}
	
	@SuppressLint("NewApi")
	public View getNewsEventView(final String title, final String url) {

		LinearLayout.LayoutParams scrollViewLayoutParam = new LinearLayout.LayoutParams(200,LayoutParams.MATCH_PARENT);
		scrollViewLayoutParam.setMargins(5, 0, 5, 5);

		TextView textView = new TextView(context);
		textView.setLayoutParams(new LayoutParams(200, LayoutParams.MATCH_PARENT));
		textView.setText(title);
		textView.setTextColor(Color.WHITE);
		textView.setPadding(5, 0, 5, 0);

		ScrollView scroll = new ScrollView(context);
		scroll.setBackground(context.getResources().getDrawable(R.drawable.news_background));
		scroll.setLayoutParams(scrollViewLayoutParam);
		scroll.addView(textView);

		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent news_details_intent = new Intent(context, NewsEventDetails.class);
				news_details_intent.putExtra("url", url);
				context.startActivity(news_details_intent);
			}
		});
		
		return scroll;
	}

}
