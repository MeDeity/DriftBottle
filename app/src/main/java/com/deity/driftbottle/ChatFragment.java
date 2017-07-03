package com.deity.driftbottle;

import android.view.View;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

/**
 * 聊天页面Fragment
 * Created by Deity on 2017/7/4.
 */

public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {
    private final static String TAG = ChatFragment.class.getSimpleName();
    /**
     * set message attribute（如果是聊天机器人，请添加属性区分）
     * @param message 消息
     */
    @Override
    public void onSetMessageAttributes(EMMessage message) {
        throw new UnsupportedOperationException(TAG+"Method onSetMessageAttributes() Not yet realized");
    }

    /**
     * enter to chat detail(群聊或聊天室跳转到详情页面)
     */
    @Override
    public void onEnterToChatDetails() {
        throw new UnsupportedOperationException(TAG+"Method onEnterToChatDetails() Not yet realized");
    }

    /**
     * on avatar clicked
     *
     * @param username
     */
    @Override
    public void onAvatarClick(String username) {
        throw new UnsupportedOperationException(TAG+"Method onAvatarClick() Not yet realized");
        //handling when user click avatar
//        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
//        intent.putExtra("username", username);
//        startActivity(intent);
    }

    /**
     * on avatar long pressed
     *
     * @param username
     */
    @Override
    public void onAvatarLongClick(String username) {
        throw new UnsupportedOperationException(TAG+"Method onAvatarLongClick() Not yet realized");
    }

    /**
     * on message bubble clicked
     *
     * @param message
     */
    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        throw new UnsupportedOperationException(TAG+"Method onMessageBubbleClick() Not yet realized");
//        return false;
    }

    /**
     * on message bubble long pressed
     * 信息泡泡长点击
     * @param message
     */
    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        throw new UnsupportedOperationException(TAG+"Method onMessageBubbleLongClick() Not yet realized");
    }

    /**
     * on extend menu item clicked, return true if you want to override
     *
     * @param itemId
     * @param view
     * @return
     */
    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        throw new UnsupportedOperationException(TAG+"Method onExtendMenuItemClick() Not yet realized");
//        return false;
    }

    /**
     * on set custom chat row provider
     *
     * @return
     */
    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        throw new UnsupportedOperationException(TAG+"Method onExtendMenuItemClick() Not yet realized");
//        return null;
    }
}
