package com.deity.driftbottle.bmob.bean;

import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.BmobObject;

/**
 * 漂流瓶信息
 * Created by fengwenhua on 2017/7/29.
 */

public class DriftBottle extends BmobObject {
    /**发送该漂流瓶的用户ID,对应bmob的objectId*/
    private BmobIMUserInfo chatFromUser;

    /**接收该漂流瓶的用户ID*/
    private String chatToUserId;
    /**漂流瓶信息*/
    private String message;
    /**被捞起次数,超过10次后,或者chatToUserId不为空,丢弃该记录*/
    private int count;

    public BmobIMUserInfo getChatFromUser() {
        return chatFromUser;
    }

    public void setChatFromUser(BmobIMUserInfo chatFromUser) {
        this.chatFromUser = chatFromUser;
    }

    public String getChatToUserId() {
        return chatToUserId;
    }

    public void setChatToUserId(String chatToUserId) {
        this.chatToUserId = chatToUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
