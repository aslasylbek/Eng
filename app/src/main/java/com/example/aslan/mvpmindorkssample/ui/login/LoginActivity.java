package com.example.aslan.mvpmindorkssample.ui.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aslan.mvpmindorkssample.general.LoadingDialog;
import com.example.aslan.mvpmindorkssample.general.LoadingView;
import com.example.aslan.mvpmindorkssample.ui.main.Main2Activity;
import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginMvpView {

    private LoginPresenter loginPresenter;
    //private LoadingView mLoadingDialog;
    @BindView(R.id.email) EditText editTextEmail;
    @BindView(R.id.password) EditText editTextPassword;
    @BindView(R.id.email_sign_in_button) Button button;
    @BindView(R.id.savePassword) CheckBox savePass;
    @BindView(R.id.saveBarcode) CheckBox saveBarcode;

    public static Intent getStartIntent(Context context){
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void init(@Nullable Bundle state) {
        //mLoadingDialog = LoadingDialog.view(getSupportFragmentManager());
        DataManager dataManager = ((MvpApp)getApplication()).getDataManager();
        loginPresenter = new LoginPresenter(dataManager);
        loginPresenter.attachView(LoginActivity.this);

    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_login;
    }

    @Override
    public void openMainActivity() {
        Intent intent = Main2Activity.getStartIntent(this);
        startActivity(intent);
        finish();

    }

    @OnClick(R.id.email_sign_in_button)
    public void onLoginButtonClick() {
        loginPresenter.startLogin();

    }

    @Override
    public User getUser() {
        String barcodeId = editTextEmail.getText().toString();
        String passwordId = editTextPassword.getText().toString();
        return new User(barcodeId, passwordId, saveBarcode.isChecked(), savePass.isChecked());
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void wrongLoginOrPassword() {
        editTextEmail.setError("Incorrect barcode");
        editTextPassword.setError("Or Incorrect password");
    }

    @Override
    protected void onDestroy() {
        loginPresenter.detachView();
        super.onDestroy();
    }


}

