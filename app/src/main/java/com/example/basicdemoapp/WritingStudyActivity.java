package com.example.basicdemoapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

import us.zoom.sdk.InMeetingService;
import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingError;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.StartMeetingOptions;
import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKAuthenticationListener;
import us.zoom.sdk.ZoomSDKInitParams;
import us.zoom.sdk.ZoomSDKInitializeListener;

public class WritingStudyActivity  extends AppCompatActivity  {
    private ZoomSDKAuthenticationListener authListener = new ZoomSDKAuthenticationListener() {
        /**
         * This callback is invoked when a result from the SDK's request to the auth server is
         * received.
         */

        @Override
        public void onZoomSDKLoginResult(long result) {
            if (result == ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS) {
                // Once we verify that the request was successful, we may start the meeting
                startMeeting(WritingStudyActivity.this);
            }

        }

        @Override
        public void onZoomSDKLogoutResult(long l) { }
        @Override
        public void onZoomIdentityExpired() { }
        @Override
        public void onZoomAuthIdentityExpired() { }
    };
    private FirebaseAuth mFirebaseAuth;       //파이어베이스 인증
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabase;
    String strZoomPwd;
    String strZoomId;
    String getTime;
    String strTitle;
    String meetingID;
    String meetingPwd;
    String strContent;
    // 사용할 컴포넌트 선언
    EditText title_et, content_et, zoomid_et,zoompwd_et;
    Button reg_button;
    ImageView mimage;
    // 유저아이디 변수
    String userid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_post);
        initializeSdk(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("GodGong");
//        ZoomSDK sdk = ZoomSDK.getInstance();
//
//        JoinMeetingOptions options = new JoinMeetingOptions();
////
//        MeetingService meetingService = sdk.getMeetingService();
//        StartMeetingOptions startMeetingOptions = new StartMeetingOptions();
//
//        meetingService.startInstantMeeting(this, startMeetingOptions);
//
//

        FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

// 컴포넌트 초기화

        mimage = findViewById(R.id.imageView);
        title_et = findViewById(R.id.title_et);
        content_et = findViewById(R.id.content_et);
        zoomid_et = findViewById(R.id.zoomid_et);
        zoompwd_et = findViewById(R.id.zoompwd_et);
        reg_button = findViewById(R.id.reg_button);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("images");
        mimage.setImageResource(R.drawable.write);
        if(pathReference == null){
            Toast.makeText(WritingStudyActivity.this, "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();}
        else{
            Toast.makeText(WritingStudyActivity.this, "저장소에 사진이 있습니다.", Toast.LENGTH_SHORT).show();
            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

            StorageReference submitProfile = storageReference.child("images/"+firebaseUser.getEmail());


            Glide.with(this /* context */)
                    .load(submitProfile)
                    .into(mimage);
        }


        reg_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

//               ImageView image = mimage.getDrawable(R.drawable.ic_launcher_background);


//                Intent intent = new Intent( WritingStudyActivity.this , DetailActivity.class);
//                startActivity(intent);

                if (ZoomSDK.getInstance().isLoggedIn()) {
                    startMeeting(WritingStudyActivity.this);
                } else {

                    if ( zoomid_et.getText() != null && zoompwd_et.getText() != null) {
                        String email = zoomid_et.getText().toString();
                        String password = zoompwd_et.getText().toString();
                        if (email.trim().length() > 0 && password.trim().length() > 0) {
                            login(email, password);
                            startMeeting(WritingStudyActivity.this);
                        }
                    }
                    else{
                        Toast.makeText(WritingStudyActivity.this, "아이디와 비밀번호를 입력하시오.", Toast.LENGTH_SHORT).show();
                    }
                }


                // Firebase Auth 진행
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                getTime = sdf.format(date);
                strTitle = title_et.getText().toString();
                strZoomId = zoomid_et.getText().toString();
                strZoomPwd = zoompwd_et.getText().toString();


                strContent = content_et.getText().toString();

                Toast.makeText(WritingStudyActivity.this, "글이 등록되었습니다 방을 생성합니다.", Toast.LENGTH_SHORT).show();

            }

        });





    }

    public void startMeeting(Context context) {

        ZoomSDK sdk = ZoomSDK.getInstance();
        MeetingServiceListener meetingServiceListener = new MeetingServiceListener(){

            @Override
            public void onMeetingStatusChanged(MeetingStatus meetingStatus, int i, int i1) {
                if(meetingStatus == meetingStatus.MEETING_STATUS_INMEETING) {
                    meetingID = String.valueOf(sdk.getInMeetingService().getCurrentMeetingNumber());
                    meetingPwd = sdk.getInMeetingService().getMeetingPassword();
                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                    PostZoom post = new PostZoom();

                    post.setEmailId(firebaseUser.getEmail());
                    post.setTitle_et(strTitle);
                    post.setContent_et(strContent);
                    post.setZoomId(strZoomId);
                    post.setZoomPwd(strZoomPwd);
                    post.setDate(getTime);
                    post.setWriterId(firebaseUser.getUid());
                    post.setRoomNum(meetingID);
                    post.setRoomPwd(meetingPwd);
                    String key = mDatabaseRef.child("studyposts").push().getKey();
                    post.setToken(key);
                    Register regi = new Register();
                    mDatabaseRef.child("studyposts").child(key).setValue(post);
                    mDatabaseRef.child("registerzoom").child(key).push().setValue(regi);

                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("postzoom").push().setValue(key);
                }
            }
        };
        if (sdk.isLoggedIn()) {
            MeetingService meetingService = sdk.getMeetingService();
            StartMeetingOptions options = new StartMeetingOptions();


            int result = meetingService.startInstantMeeting(context, options);
            if(result == MeetingError.MEETING_ERROR_SUCCESS)
            ZoomSDK.getInstance().getMeetingService().addListener(meetingServiceListener);

        }
    }
//    private void startMeetingDialog(){
//        ZoomSDK sdk = ZoomSDK.getInstance();
//        MeetingService meetingService = sdk.getMeetingService();
//        StartMeetingOptions options = new StartMeetingOptions();
//
//        meetingService.startInstantMeeting(this,options);
//    }

    public void login(String username, String password) {
        int result = ZoomSDK.getInstance().loginWithZoom(username, password);
        if (result == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {
            // Request executed, listen for result to start meeting
            ZoomSDK.getInstance().addAuthenticationListener(authListener);
        }
    }

    public void initializeSdk(Context context) {
        ZoomSDK sdk = ZoomSDK.getInstance();
        // TODO: Do not use hard-coded values for your key/secret in your app in production!
        ZoomSDKInitParams params = new ZoomSDKInitParams();
        params.appKey = "8ldKjhRAXw7nkMcOW6UIJtU7UbIFBfBPOKfb"; // TODO: Retrieve your SDK key and enter it here
        params.appSecret = "nLu6cbw9EeJWNtH2g2RU2ftziQXVQ4W4jOet"; // TODO: Retrieve your SDK secret and enter it here
        params.domain = "zoom.us";
        params.enableLog = true;
        // TODO: Add functionality to this listener (e.g. logs for debugging)
        ZoomSDKInitializeListener listener = new ZoomSDKInitializeListener() {
            /**
             * @param errorCode {@link us.zoom.sdk.ZoomError#ZOOM_ERROR_SUCCESS} if the SDK has been initialized successfully.
             */
            @Override
            public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) { }

            @Override
            public void onZoomAuthIdentityExpired() { }
        };
        sdk.initialize(context, listener, params);

    }



}

