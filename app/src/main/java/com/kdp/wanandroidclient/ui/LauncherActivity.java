package com.kdp.wanandroidclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.UserBean;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.ui.base.BasePresenterActivity;
import com.kdp.wanandroidclient.ui.logon.LogonContract;
import com.kdp.wanandroidclient.ui.logon.LogonPresenter;
import com.kdp.wanandroidclient.ui.main.MainActivity;

import java.lang.ref.WeakReference;


/**
 * 启动页
 * Created by 康栋普 on 2018/1/31.
 */

public class LauncherActivity extends BasePresenterActivity<LogonPresenter, LogonContract.ILoginRegisterView> implements LogonContract.ILoginRegisterView {
    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new DelayRunnable(this), 2000);
    }

    //自动登录
    private void autoLogin() {
        if (UserInfoManager.isLogin()) {
            //自动登录
            userBean = UserInfoManager.getUserInfo();
            if (userBean != null)
                mPresenter.login();
        }
        startToActivity();
    }


    private static class DelayRunnable implements Runnable {
        private WeakReference<LauncherActivity> mWeakReference;

        DelayRunnable(LauncherActivity instance) {
            mWeakReference = new WeakReference<>(instance);
        }

        @Override
        public void run() {
            LauncherActivity instance = mWeakReference.get();
            if (instance == null) return;
            instance.autoLogin();
        }
    }

    //进入首页
    private void startToActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected LogonPresenter createPresenter() {
        return new LogonPresenter();
    }

    @Override
    public String getUserName() {
        return userBean.getUsername();
    }

    @Override
    public String getPassWord() {
        return userBean.getPassword();
    }


    @Override
    public void showResult(String msg) {
    }
}
