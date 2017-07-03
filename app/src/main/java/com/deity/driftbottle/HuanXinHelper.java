package com.deity.driftbottle;

import com.hyphenate.chat.EMClient;

/**
 * 环信工具类
 * Created by Deity on 2017/7/4.
 */

@SuppressWarnings("unused")
public class HuanXinHelper {
    private static HuanXinHelper instance = null;

    private HuanXinHelper() {
    }

    public synchronized static HuanXinHelper getInstance() {
        if (instance == null) {
            instance = new HuanXinHelper();
        }
        return instance;
    }

    /**
     * if ever logged in
     * @return true if logged
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }
}
