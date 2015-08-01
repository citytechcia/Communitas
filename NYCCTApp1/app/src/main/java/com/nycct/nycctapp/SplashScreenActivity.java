package com.nycct.nycctapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen_layout);
		
		showSplashScreen();
	}

	public void showSplashScreen() {

		new Handler().postDelayed(new Runnable() {

			// Using handler with postDelayed called runnable run method

			@Override
			public void run() {
				Intent i = new Intent(SplashScreenActivity.this, MainPageActivity.class);
				startActivity(i);

				// close this activity
				finish();

				// Apply splash exit (fade out) and main entry (fade in)
				// animation transitions.
				overridePendingTransition(R.anim.mainfadein, R.anim.splashfadeout);
			}
		}, 2000); // wait for 3 seconds
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
