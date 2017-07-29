package com.deity.driftbottle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.deity.driftbottle.bmob.bean.DriftBottle;

import java.util.List;
import java.util.Random;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 会话界面(漂流瓶界面)
 * Created by fengwenhua on 2017/7/29.
 */

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testData();
        testQuery();
    }

    /**启动指定Activity
     * @param target
     * @param bundle
     */
    public void startActivity(Class<? extends Activity> target, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(ConversationActivity.this, target);
        if (bundle != null) {
            intent.putExtra(ConversationActivity.this.getPackageName(), bundle);
        }
        ConversationActivity.this.startActivity(intent);
    }

    public void testQuery(){
        BmobQuery<DriftBottle> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(ConversationActivity.this, new FindListener<DriftBottle>() {
            @Override
            public void onSuccess(List<DriftBottle> list) {
                Toast.makeText(ConversationActivity.this,"瓶子查询成功"+list.toArray(),Toast.LENGTH_SHORT).show();
                try {
                    DriftBottle x = list.get(0);
                    BmobIMUserInfo info = new BmobIMUserInfo(x.getChatFromUser().getUserId(),x.getChatFromUser().getName(),x.getChatFromUser().getAvatar());
                    //启动一个会话，实际上就是在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
                    BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, null);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("c", c);

                    startActivity(ChatActivity.class, bundle);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(ConversationActivity.this,"瓶子查询失败,ErrorCode:"+i+" ErrorDesc:"+s,Toast.LENGTH_SHORT).show();
            }
        });
//        bmobQuery.findObjects(ConversationActivity.this, new FindCallback() {
//            @Override
//            public void onSuccess(JSONArray jsonArray) {
//                Toast.makeText(ConversationActivity.this,"瓶子查询成功"+jsonArray,Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Toast.makeText(ConversationActivity.this,"瓶子查询失败,ErrorCode:"+i+" ErrorDesc:"+s,Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void testData(){
        Random random = new Random();
        DriftBottle bottle = new DriftBottle();
        bottle.setChatFromUser(BmobIM.getInstance().getUserInfo(BmobIM.getInstance().getCurrentUid()));
        bottle.setMessage("这是第"+random.nextInt(100)+"瓶子");
        bottle.save(ConversationActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(ConversationActivity.this,"瓶子成功保存",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(ConversationActivity.this,"瓶子保存失败,ErrorCode:"+i+" ErrorDesc:"+s,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
