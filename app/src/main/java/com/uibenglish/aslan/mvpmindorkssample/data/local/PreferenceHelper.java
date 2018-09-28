package com.uibenglish.aslan.mvpmindorkssample.data.local;

public interface PreferenceHelper {

    String MY_PREFS = "MY_PREF";
    String BARCODE = "BARCODE";
    String PASSWORD = "PASSWORD";
    String TOKEN = "TOKEN";
    String USER_ID = "USER_ID";
    String COURSE_ID = "COURSE_ID";

    void clear();

    void putPassword(String password);
    String getPrefPassword();


    void putToken(String token);
    String getPrefToken();


    void putUserId(String user_id);
    String getPrefUserid();


    void putBarcode(String barcode);
    String getBarcode();


    boolean getLoggedMode();
    void setLoggedMode(boolean loggedIn);


    void putCourseId(String course_id);
    String getCourseId();
}
