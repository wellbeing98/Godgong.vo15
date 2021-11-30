package com.example.basicdemoapp;


import android.widget.ImageView;


class PostZoom {

    public String title_et;
    public String content_et;
    private String zoomid;
    private String zoompwd;
    public int starCount = 0;
    public ImageView image;
    private String emailId;   //이메일 아이디
    private String token;
    private String Date;
    private String writerId;
    private String roomNum;
    private String roomPwd;
    public String getRoomNum(){
        return roomNum;
    }
    public void setRoomNum(String roomnum){
        this.roomNum = roomnum;
    }
    public void setRoomPwd(String roomPwd){
        this.roomPwd = roomPwd;
    }
    public String getRoomPwd(){
        return roomPwd;
    }

    public String getWriterId() {
        return writerId;
    }
    public void setWriterId(String writerId){
        this.writerId = writerId;
    }

    public String getDate(){
        return Date;
    }
    public void setDate(String Date){
        this.Date = Date;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
    public PostZoom() {

    }
    public void setImage(ImageView image){
        this.image = image;
    }
    public ImageView getImage(){
        return image;
    }
    public void setTitle_et(String title_et) {
        this.title_et = title_et;
    }

    public void setContent_et(String content_et) { this.content_et = content_et;}
    public void setZoomId(String id) {this.zoomid = id;}
    public void setZoomPwd(String pwd) {this.zoompwd = pwd;}
    public String getTitle_et() {
        return title_et;
    }

    public String getContent_et() {
        return content_et;
    }

    public String getZoomId() {
        return this.zoomid;
    }

    public String getZoomPwd() {
        return this.zoompwd;
    }
    public String getEmailId(){return emailId;}
    public void setEmailId(String emailId){this.emailId = emailId;}
    private String idToken;   //파이어베이스 Uid (고유 토큰정보)

    private String password;  //비밀번호


    public void setIdToken(String uid) {
    }

    public PostZoom(String userid, String title_et, String content_et, String zoomid, String zoompwd) {

        this.title_et = title_et;
        this.content_et = content_et;
        this.zoomid = zoomid;
        this.zoompwd =zoompwd;
    }

//    public void setEmailId(String emailId) {
//        this.emailId = emailId;
//    }
}
