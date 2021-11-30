package com.example.basicdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import us.zoom.androidlib.utils.ZmMimeTypeUtils;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.ZoomSDK;

public class WaitActivity extends AppCompatActivity implements MeetingServiceListener, View.OnClickListener {
    TextView MeetingTopic, MeetingDate, MeetingTime,MeetingId;
    String topic,date,time;
    long meetingId;
    Button LeaveMeeting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        Intent intent = getIntent();
        topic = intent.getStringExtra(ZmMimeTypeUtils.EXTRA_TOPIC);
        date = intent.getStringExtra(ZmMimeTypeUtils.EXTRA_DATE);
        time = intent.getStringExtra(ZmMimeTypeUtils.EXTRA_TIME);
        meetingId = intent.getLongExtra(ZmMimeTypeUtils.EXTRA_MEETING_ID,0);
        MeetingTopic = findViewById(R.id.tvMTP);
        MeetingDate =findViewById(R.id.tvMD);
        MeetingTime = findViewById(R.id.tvMT);
        MeetingId = findViewById(R.id.tvMID);
        LeaveMeeting = findViewById(R.id.btnLM);
        if(topic != null){
            MeetingTopic.setText("Meeting Topic: "+topic);
        }
        if(date != null){
            MeetingDate.setText("Meeting Topic: "+date);
        }
        if(time != null){
            MeetingTime.setText("Meeting Topic: "+topic);
        }
        if(meetingId >0){
            MeetingId.setText("Meeting Topic: "+topic);
        }
        LeaveMeeting.setOnClickListener(this);
        ZoomSDK zoomSDk = ZoomSDK.getInstance();
        MeetingService meetingService = zoomSDk.getMeetingService();
        if(meetingService !=null){
            meetingService.addListener(this);
        }


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLM){
            OnClick();
        }
    }

    @Override
    public void onMeetingStatusChanged(MeetingStatus meetingStatus, int i, int i1) {
        if(meetingStatus != MeetingStatus.MEETING_STATUS_WAITINGFORHOST){
            finish();

        }

    }
    protected  void onDestroy(){
        ZoomSDK zoomSDK = ZoomSDK.getInstance();

        if (zoomSDK.isInitialized()){
            MeetingService meetingService =  zoomSDK.getMeetingService();
            meetingService.removeListener(this);
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        OnClick();
    }

    private void OnClick(){
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        MeetingService meetingService = zoomSDK.getMeetingService();
        if(meetingService != null){
            meetingService.leaveCurrentMeeting(false);

        }
        finish();
    }
}