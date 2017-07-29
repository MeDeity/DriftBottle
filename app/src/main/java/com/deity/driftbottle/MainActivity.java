package com.deity.driftbottle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.deity.driftbottle.bmob.bean.User;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.beach_shui)
    public ImageView beach_shui;

    @BindView(R.id.beach_lang)
    public ImageView beach_lang;

    @BindView(R.id.bottle_getting)
    public ImageView bottle_getting;

    @BindView(R.id.bottle_sending)
    public ImageView bottle_sending;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initBmob();
        beach_shui.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_shake));
        beach_lang.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_shake));
    }

    private void initBmob(){
        //connect server
        User user = BmobUser.getCurrentUser(this,User.class);
        BmobIM.connect(user.getObjectId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                    Logger.i("connect success");
                    //服务器连接成功就发送一个更新事件，同步更新会话及主页的小红点
//                    EventBus.getDefault().post(new RefreshEvent());//TODO
                } else {
                    Logger.e(e.getErrorCode() + "/" + e.getMessage());
                }
            }
        });
        //监听连接状态，也可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
                Toast.makeText(MainActivity.this,"" + status.getMsg(),Toast.LENGTH_SHORT).show();
            }
        });
        //解决leancanary提示InputMethodManager内存泄露的问题
//        IMMLeaks.fixFocusedViewLeak(getApplication());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.send_nav_image)
    public void sendBottleMsg(View view){
//        new AlertDialog.Builder(MainActivity.this).setView(R.layout.dialog_bottle_send)
//                .show();
        bottle_sending.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_move_up));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.get_nav_image)
    public void getBottleMsg(View view){
//        new AlertDialog.Builder(MainActivity.this).setView(R.layout.dialog_bottle_receiver)
//                .show();
        bottle_getting.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_move_up));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.item_bottle_msg)
    public void getBottle(View view){
        Intent intent = new Intent(MainActivity.this,ConversationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //显示小红点
        checkRedPoint();
        //进入应用后，通知栏应取消
        BmobNotificationManager.getInstance(this).cancelNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清理导致内存泄露的资源
        BmobIM.getInstance().clear();
    }

    private void checkRedPoint(){
        int count = (int)BmobIM.getInstance().getAllUnReadCount();
//        if(count>0){
//            iv_conversation_tips.setVisibility(View.VISIBLE);
//        }else{
//            iv_conversation_tips.setVisibility(View.GONE);
//        }
//        //是否有好友添加的请求
//        if(NewFriendManager.getInstance(this).hasNewFriendInvitation()){
//            iv_contact_tips.setVisibility(View.VISIBLE);
//        }else{
//            iv_contact_tips.setVisibility(View.GONE);
//        }
    }
}
