package com.deity.driftbottle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.ButterKnife;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.newim.listener.ObseverListener;

/**
 * 聊天页面
 * Created by Deity on 2017/7/4.
 */

public class ChatActivity extends AppCompatActivity implements ObseverListener,MessageListHandler {


    BmobIMConversation bmobIMConversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        bmobIMConversation= BmobIMConversation.obtain(BmobIMClient.getInstance(), (BmobIMConversation) getBundle().getSerializable("c"));
    }

    public Bundle getBundle() {
        if (getIntent() != null && getIntent().hasExtra(getPackageName()))
            return getIntent().getBundleExtra(getPackageName());
        else
            return null;
    }


    //当页面在栈顶时，仍接收到Intent，会执行该方法
    @Override
    protected void onNewIntent(Intent intent) {
//        String username = intent.getStringExtra("userId");
//        if (toChatUsername.equals(username))
//            super.onNewIntent(intent);
//        else {
//            finish();
//            startActivity(intent);
//        }

        super.onNewIntent(intent);
    }


    @Override
    public void onMessageReceive(List<MessageEvent> list) {
        Logger.i("聊天页面接收到消息：" + list.size());
        //当注册页面消息监听时候，有消息（包含离线消息）到来时会回调该方法
        for (int i=0;i<list.size();i++){
            addMessage2Chat(list.get(i));
        }
    }


    /**添加消息到聊天界面中
     * @param event
     */
    private void addMessage2Chat(MessageEvent event){
        BmobIMMessage msg =event.getMessage();
//        if(bmobIMConversation !=null && event!=null && bmobIMConversation.getConversationId().equals(event.getConversation().getConversationId()) //如果是当前会话的消息
//                && !msg.isTransient()){//并且不为暂态消息
//            if(adapter.findPosition(msg)<0){//如果未添加到界面中
//                adapter.addMessage(msg);
//                //更新该会话下面的已读状态
//                bmobIMConversation.updateReceiveStatus(msg);
//            }
//            scrollToBottom();
//        }else{
//            Logger.i("不是与当前聊天对象的消息");
//        }
    }
}
