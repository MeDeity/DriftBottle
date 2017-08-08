package com.deity.driftbottle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.deity.driftbottle.adapter.ConversationAdapter;
import com.deity.driftbottle.adapter.IMutlipleItem;
import com.deity.driftbottle.adapter.OnRecyclerViewListener;
import com.deity.driftbottle.bmob.bean.Conversation;
import com.deity.driftbottle.bmob.bean.DriftBottle;
import com.deity.driftbottle.bmob.bean.PrivateConversation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.rc_view)
    RecyclerView rc_view;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    ConversationAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        initViews();
        query();
//        testData();
//        testQuery();
    }

    public void initViews(){
        //单一布局
        IMutlipleItem<Conversation> mutlipleItem = new IMutlipleItem<Conversation>() {

            @Override
            public int getItemViewType(int postion, Conversation c) {
                return 0;
            }

            @Override
            public int getItemLayoutId(int viewtype) {
                return R.layout.item_conversation;
            }

            @Override
            public int getItemCount(List<Conversation> list) {
                return list.size();
            }
        };
        adapter = new ConversationAdapter(ConversationActivity.this,mutlipleItem,null);
        rc_view.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(ConversationActivity.this);
        rc_view.setLayoutManager(layoutManager);
        sw_refresh.setEnabled(true);
        setListener();
    }


    /**
     * 获取会话列表的数据：增加新朋友会话
     * @return
     */
    private List<Conversation> getConversations(){
        //添加会话
        List<Conversation> conversationList = new ArrayList<>();
        conversationList.clear();
        List<BmobIMConversation> list =BmobIM.getInstance().loadAllConversation();
        if(list!=null && list.size()>0){
            for (BmobIMConversation item:list){
                switch (item.getConversationType()){
                    case 1://私聊
                        conversationList.add(new PrivateConversation(item));
                        break;
                    default:
                        break;
                }
            }
        }
//        //添加新朋友会话-获取好友请求表中最新一条记录
//        List<NewFriend> friends = NewFriendManager.getInstance(getActivity()).getAllNewFriend();
//        if(friends!=null && friends.size()>0){
//            conversationList.add(new NewFriendConversation(friends.get(0)));
//        }
        //重新排序
        Collections.sort(conversationList);
        return conversationList;
    }

    /**
     查询本地会话
     */
    public void query(){
//        adapter.bindDatas(BmobIM.getInstance().loadAllConversation());
        adapter.bindDatas(getConversations());
        adapter.notifyDataSetChanged();
        sw_refresh.setRefreshing(false);
    }

    private void setListener(){
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                adapter.getItem(position).onClick(ConversationActivity.this);
            }

            @Override
            public boolean onItemLongClick(int position) {
                adapter.getItem(position).onLongClick(ConversationActivity.this);
                adapter.remove(position);
                return true;
            }
        });
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
                Toast.makeText(ConversationActivity.this,"瓶子查询成功"+list.size(),Toast.LENGTH_SHORT).show();
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
