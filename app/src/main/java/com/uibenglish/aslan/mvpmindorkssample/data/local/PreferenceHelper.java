package com.uibenglish.aslan.mvpmindorkssample.data.local;

public interface PreferenceHelper {

    String MY_PREFS = "MY_PREF";
    String BARCODE = "BARCODE";
    String PASSWORD = "PASSWORD";
    String TOKEN = "TOKEN";
    String USER_ID = "USER_ID";
    String COURSE_ID = "COURSE_ID";
    String NAME = "FIO";
    String GROUP = "GROUP";
    String PROGRAM = "PROGRAM";

    void clear();

    void putName(String name);
    String getName();

    void putGroup(String group);
    String getGroup();

    void putProgram(String program);
    String getProgram();

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
