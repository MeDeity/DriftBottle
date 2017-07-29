package com.deity.driftbottle.handler;

import android.content.Context;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * 注册消息接收器
 * Created by fengwenhua on 2017/7/29.
 */

public class DriftBottleMessageHandler extends BmobIMMessageHandler {

    private Context context;

    public DriftBottleMessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onMessageReceive(MessageEvent messageEvent) {
        //当接收到服务器发来的消息时，此方法被调用
    }

    @Override
    public void onOfflineReceive(OfflineMessageEvent offlineMessageEvent) {
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
    }

}
