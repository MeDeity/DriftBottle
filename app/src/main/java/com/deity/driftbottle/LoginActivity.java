package com.deity.driftbottle;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 第三方登录页面
 * Created by Deity on 2017/6/29.
 */

public class LoginActivity extends EaseBaseActivity {

    private ProgressDialog dialog;

    @BindView(R.id.username)
    public EditText username;

    @BindView(R.id.password)
    public EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (HuanXinHelper.getInstance().isLoggedIn()){//自动登录
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            return;
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        handleThirdPartLogin(savedInstanceState);
    }


    /**
     * login
     *
     * @param view
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_login)
    public void login(View view) {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        String currentUsername = username.getText().toString().trim();
        String currentPassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "EMClient.getInstance().onCancel");
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();

        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
//        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
//        DemoHelper.getInstance().setCurrentUserName(currentUsername);

        final long start = System.currentTimeMillis();
        // call login method
        Log.d(TAG, "EMClient.getInstance().login");
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "login: onSuccess");


                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
//                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
//                        DemoApplication.currentUserNick.trim());
//                if (!updatenick) {
//                    Log.e("LoginActivity", "update current user nick fail");
//                }

                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }
                // get user's info (this should be get from App's server or 3rd party service)
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
//                if (!progressShow) {
//                    return;
//                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void handleThirdPartLogin(Bundle savedInstanceState){
        dialog = new ProgressDialog(this);
        UMShareAPI.get(this).fetchAuthResultWithBundle(this, savedInstanceState, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                SocializeUtils.safeShowDialog(dialog);
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                Toast.makeText(getApplicationContext(), "onRestoreInstanceState Authorize succeed", Toast.LENGTH_SHORT).show();
                SocializeUtils.safeCloseDialog(dialog);
                //TODO 获取用户信息
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authGetInfoListener);
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                Toast.makeText(getApplicationContext(), "onRestoreInstanceState Authorize onError", Toast.LENGTH_SHORT).show();
                SocializeUtils.safeCloseDialog(dialog);
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                Toast.makeText(getApplicationContext(), "onRestoreInstanceState Authorize onCancel", Toast.LENGTH_SHORT).show();
                SocializeUtils.safeCloseDialog(dialog);
            }
        });
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_wx)
    public void loginWX(View view){
        boolean isauth = UMShareAPI.get(LoginActivity.this).isAuthorize(LoginActivity.this, SHARE_MEDIA.WEIXIN);
        if (isauth) {
            UMShareAPI.get(LoginActivity.this).deleteOauth(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
        } else {
            UMShareAPI.get(LoginActivity.this).doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
        }
    }

    //授权
    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            SocializeUtils.safeCloseDialog(dialog);
            TostMsg("成功了");
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            TostMsg("失败：" + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SocializeUtils.safeCloseDialog(dialog);
            TostMsg("取消了");
        }
    };


    //获取用户信息
    UMAuthListener authGetInfoListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String temp = "";
            for (String key : data.keySet()) {
                temp = temp + key + " : " + data.get(key) + "\n";
            }
            TostMsg(temp);

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            TostMsg("错误" + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {

        }
    };

    private void TostMsg(String mesage){
        Toast.makeText(LoginActivity.this,mesage,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }
}
