package com.uibenglish.aslan.mvpmindorkssample.ui.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.general.LoadingDialog;
import com.uibenglish.aslan.mvpmindorkssample.general.LoadingView;
import com.uibenglish.aslan.mvpmindorkssample.utils.NetworkUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements MvpView {

    private BaseActivity mActivity;
    private Unbinder mUnbinder;
    private LoadingView loadingView;
    private View view;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity){
            BaseActivity activity = (BaseActivity)context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null)
            view = inflater.inflate(getContentResource(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        init(savedInstanceState);
        return view;
    }

    protected abstract void init(@Nullable Bundle bundle);


    @LayoutRes
    protected abstract int getContentResource();

    @Override
    public boolean isNetworkConnected() {
        if (mActivity!=null)
            return mActivity.isNetworkConnected();
        return false;
    }

    @Override
    public void noInternetConnection() {
        new AlertDialog.Builder(mActivity)
                .setTitle("Failed to connect")
                .setMessage("Check internet connection and try again")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    @Override
    public void showToastMessage(int resId) {
        Toast.makeText(getContext(),
                getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(mActivity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(
                mActivity, R.color.whiteText));
        snackbar.show();
    }

    @Override
    public void showLoading() {
        hideLoading();
        if (loadingView==null){
            loadingView = LoadingDialog.view(mActivity.getSupportFragmentManager());
        }
        loadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        if (loadingView!=null)
            loadingView.hideLoading();
    }

    @Override
    public void onDetach() {
        mActivity=null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    @Override
    public void onDestroy() {
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
