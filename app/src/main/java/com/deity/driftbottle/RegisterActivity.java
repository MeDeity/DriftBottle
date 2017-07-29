package com.deity.driftbottle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.deity.driftbottle.bmob.model.BaseModel;
import com.deity.driftbottle.bmob.model.UserModel;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 注册页面
 * Created by Deity on 2017/7/4.
 */

public class RegisterActivity extends EaseBaseActivity {
    @BindView(R.id.username)
    public EditText username;

    @BindView(R.id.password)
    public EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_register)
    public void onRegisterEvent(View view){
        final String userName = username.getText().toString();
        final String passwordStr = password.getText().toString();
        UserModel.getInstance().register(userName,passwordStr,passwordStr,new LogInListener() {
            @Override
            public void done(Object o, BmobException e) {
                if(e==null){
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                }else{
                    if(e.getErrorCode()== BaseModel.CODE_NOT_EQUAL){

                    }
                    Toast.makeText(RegisterActivity.this,e.getMessage()+"("+e.getErrorCode()+")",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_login)
    public void toLogin(View view){
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
}
