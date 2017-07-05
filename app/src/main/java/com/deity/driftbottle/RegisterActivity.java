package com.deity.driftbottle;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
        Observable<String[]> observable = Observable.create(new ObservableOnSubscribe<String[]>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String[]> e) throws Exception {
                e.onNext(new String[]{userName,passwordStr});
            }
        });
        Consumer<String[]> consumer = new Consumer<String[]>() {
            @Override
            public void accept(@NonNull String[] strings) throws Exception {
                EMClient.getInstance().createAccount(strings[0],strings[1]);
            }
        };
        observable.subscribeOn(Schedulers.io()).subscribe(consumer);
    }
}
