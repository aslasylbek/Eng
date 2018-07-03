package com.example.aslan.mvpmindorkssample.data.local;

public interface PreferenceHelper {

    String MY_PREFS = "MY_PREF";
    String BARCODE = "BARCODE";
    String PASSWORD = "PASSWORD";
    String TOKEN = "TOKEN";
    String USER_ID = "USER_ID";

    void clear();

    void putPassword(String password);

    String getPrefPassword();

    void putToken(String token);

    String getPrefToken();

    void putUserId(String password);

    String getPrefUserid();

    void putBarcode(String barcode);

    String getBarcode();

    boolean getLoggedMode();

    void setLoggedMode(boolean loggedIn);
}
