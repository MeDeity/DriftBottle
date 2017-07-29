package com.deity.driftbottle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.deity.driftbottle.bmob.bean.User;
import com.deity.driftbottle.bmob.model.UserModel;
import com.hyphenate.easeui.ui.EaseBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    /**
     * login
     *
     * @param view
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_login)
    public void login(View view) {
        UserModel.getInstance().login(username.getText().toString(), password.getText().toString(), new LogInListener() {

            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    User user =(User)o;
                    BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar()));
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this,e.getMessage() + "(" + e.getErrorCode() + ")",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @SuppressWarnings("unused")
    @OnClick(R.id.btn_register)
    public void onRegisterEvent(View view){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

}
