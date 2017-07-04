package com.deity.driftbottle;

import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseBaseActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Deity on 2017/7/4.
 */

public class SplashActivity extends EaseBaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkLogin();
    }

    /**
     * 检查当前设备是否登录
     */
    private void checkLogin(){
        Observable<Boolean> observable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            /**
             * Called for each Observer that subscribes.
             *
             * @param emitter the safe emitter instance, never null
             * @throws Exception on error
             */
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(HuanXinHelper.getInstance().isLoggedIn());
            }
        });

        Consumer<Boolean> consumer = new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                if (aBoolean){//当前已经登录
                    EMClient.getInstance().chatManager().loadAllConversations();
                    EMClient.getInstance().groupManager().loadAllGroups();
                    //直接进入首页
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }
        };
        //主题订阅处理
        observable.subscribeOn(Schedulers.io()).subscribe(consumer);
    }
}
