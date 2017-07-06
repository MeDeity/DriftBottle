package com.deity.driftbottle;

import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.util.EasyUtils;

import butterknife.ButterKnife;

/**
 * 聊天页面
 * Created by Deity on 2017/7/4.
 */

public class ConversationListActivity extends EaseBaseActivity {

    private ConversationListFragment conversationListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        conversationListFragment = new ConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_chat,conversationListFragment).commit();
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
    public void onBackPressed() {
        super.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

//    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                                     @NonNull int[] grantResults) {
//        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
//    }
}
