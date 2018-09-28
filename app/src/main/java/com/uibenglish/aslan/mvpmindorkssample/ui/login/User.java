package com.uibenglish.aslan.mvpmindorkssample.ui.login;

public class User {

    private String barcode;
    private String password;
    private boolean saveBarcode;
    private boolean savePassword;

    public User(String barcode, String password, boolean saveBarcode, boolean savePassword) {

        this.barcode = barcode;
        this.password = password;
        this.saveBarcode = saveBarcode;
        this.savePassword = savePassword;
    }

    public User() {
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSaveBarcode() {
        return saveBarcode;
    }

    public void setSaveBarcode(boolean saveBarcode) {
        this.saveBarcode = saveBarcode;
    }

    public boolean isSavePassword() {
        return savePassword;
    }

    public void setSavePassword(boolean savePassword) {
        this.savePassword = savePassword;
    }
}
