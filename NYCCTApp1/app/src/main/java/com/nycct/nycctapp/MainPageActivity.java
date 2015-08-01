package com.nycct.nycctapp;

import java.util.ArrayList;
import com.nycct.nycctapp.adapter.NavDrawerListAdapter;
import com.nycct.nycctapp.campusservice.CampusService;
import com.nycct.nycctapp.clubinformation.ClubInformation;
import com.nycct.nycctapp.model.NavDrawerItem;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

@SuppressWarnings("unused")
@SuppressLint("NewApi")
public class MainPageActivity extends ActionBarActivity {

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	private CharSequence drawerTitle;
	private CharSequence mTitle; // used to store app title
	private String[] navMenuTitles; // slide menu items
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	public FragmentManager fragmentManager = getSupportFragmentManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		mTitle = drawerTitle = getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1))); // Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1))); // news and event
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1))); // Office hours
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1))); // Club information
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1))); // Campus Service
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1))); // Class cancel
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1))); // Groups on loops

		
		navMenuIcons.recycle(); // Recycle the typed array
		drawerList.setOnItemClickListener(new SlideMenuClickListener());
		
		adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems); // setting the nav drawer list adapter
		drawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.drawable.ic_drawer, R.string.app_name){
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // calling onPrepareOptionsMenu() to show action bar icons
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(drawerTitle);
				invalidateOptionsMenu(); // calling onPrepareOptionsMenu() to hide action bar icons
			}
		};
		
		drawerLayout.setDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			displayView(0); // on first time display view for first nav item
		}
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class SlideMenuClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			displayView(position); // display view for selected nav drawer item
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu); // Inflate the menu;
		return true;
	}

	private void displayView(int position) {

		Fragment fragment = null; // update the main content by replacing fragments
		
		switch (position) {
		case 0:
			fragment = new HomeFragment();

			if (fragment != null) {

				fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

				// update selected item and title, then close the drawer
				drawerList.setItemChecked(position, true);
				drawerList.setSelection(position);
				setTitle(navMenuTitles[position]);
				drawerLayout.closeDrawer(drawerList);
			} else {
				Log.e("MainActivityPage", "Error in creating fragment");
			}

			break;
		case 1:
			closeDrawerList(position);
			// create new intent

			break;

		case 2:
			closeDrawerList(position);
			// create new intent
			break;
		case 3:
			closeDrawerList(position);
			// create new intent
			Intent club_information_intent = new Intent(this, ClubInformation.class);
			startActivity(club_information_intent);
			break;
		case 4:
			closeDrawerList(position);
			// create new intent
			Intent campus_service_intent = new Intent(this, CampusService.class);
			startActivity(campus_service_intent);
			break;
		case 5:
			closeDrawerList(position);
			// create new intent

			break;

		case 6:
			closeDrawerList(position);
			// create new intent
			break;

		default:
			break;
		}

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		drawerToggle.syncState(); // Sync the toggle state after onRestoreInstanceState has occurred.
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		drawerToggle.onConfigurationChanged(newConfig); // Pass any configuration change to the drawer toggles
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();

//		drawerLayout.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				drawerLayout.openDrawer(Gravity.START);
//
//			}
//		}, 1000);
	}

	public void closeDrawerList(int position) {

		// update selected item and title, then close the drawer
		drawerList.setItemChecked(position, true);
		drawerList.setSelection(position);
		setTitle(navMenuTitles[position]);
		drawerLayout.closeDrawer(drawerList);
	}
}
