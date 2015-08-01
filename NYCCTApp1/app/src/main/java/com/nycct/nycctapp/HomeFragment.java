package com.nycct.nycctapp;

import com.nycct.nycctapp.R;
import com.nycct.nycctapp.campusservice.CampusService;
import com.nycct.nycctapp.classcancel.ClassCancel;
import com.nycct.nycctapp.clubinformation.ClubInformation;
import com.nycct.nycctapp.groupsnloops.GroupsnLoops;
import com.nycct.nycctapp.newsevent.NycctNewsEvent;
import com.nycct.nycctapp.officehours.OfficeHours;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class HomeFragment extends Fragment implements OnClickListener{

	private Context homeFragmentContext;
	private LinearLayout newsEventGallery;
	private ImageView campusService;
	private ImageView classCancel;
	private ImageView groupsnLoops;
	private ImageView clubInformation;
	private ImageView officeHours;
	
	public HomeFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		homeFragmentContext=container.getContext();
		
		View rootView = inflater.inflate(R.layout.home_fragment_layout,	container, false);
		
		newsEventGallery = (LinearLayout) rootView.findViewById(R.id.newsEventGallery);
		campusService=(ImageView) rootView.findViewById(R.id.campusServiceImageView);
		classCancel=(ImageView) rootView.findViewById(R.id.classCancelImageView);
		groupsnLoops=(ImageView) rootView.findViewById(R.id.groupsnLoppsImageView);
		clubInformation=(ImageView) rootView.findViewById(R.id.clubInfoImageView);
		officeHours=(ImageView) rootView.findViewById(R.id.officeHoursImageView);
		
		campusService.setOnClickListener(this);
		classCancel.setOnClickListener(this);
		groupsnLoops.setOnClickListener(this);
		clubInformation.setOnClickListener(this);
		officeHours.setOnClickListener(this);
		
		//load news event gallery
		NycctNewsEvent nycctNewsEvent=new NycctNewsEvent(homeFragmentContext, newsEventGallery);
		nycctNewsEvent.execute();
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.campusServiceImageView:
			Intent campus_service_intent = new Intent(homeFragmentContext, CampusService.class);
			startActivity(campus_service_intent);
			break;
		case R.id.classCancelImageView:
			Intent class_cancel_intent = new Intent(homeFragmentContext, ClassCancel.class);
			startActivity(class_cancel_intent);
			break;
		case R.id.groupsnLoppsImageView:
			Intent groups_loops_intent = new Intent(homeFragmentContext, GroupsnLoops.class);
			startActivity(groups_loops_intent);
			break;
		case R.id.clubInfoImageView:
			Intent club_info_intent = new Intent(homeFragmentContext, ClubInformation.class);
			startActivity(club_info_intent);
			break;
		case R.id.officeHoursImageView:
			Intent office_hours_intent = new Intent(homeFragmentContext, OfficeHours.class);
			startActivity(office_hours_intent);
			break;
		default:
			break;
		}
		
	}


}
