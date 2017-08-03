package com.deity.driftbottle.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deity.driftbottle.R;
import com.deity.driftbottle.bmob.bean.Conversation;
import com.deity.driftbottle.utils.TimeUtil;

import java.util.Collection;

/**
 * Created by Deity on 2017/8/3.
 */

public class ConversationAdapter extends BaseRecyclerAdapter<Conversation>  {
    private Context context;

    public ConversationAdapter(Context context, IMutlipleItem<Conversation> items, Collection<Conversation> datas) {
        super(context,items,datas);
        this.context = context;
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, Conversation conversation, int position) {
        holder.setText(R.id.tv_recent_msg,conversation.getLastMessageContent());
        holder.setText(R.id.tv_recent_time, TimeUtil.getChatTime(false,conversation.getLastMessageTime()));
        //会话图标
        Object obj = conversation.getAvatar();
        if(obj instanceof String){
            String avatar=(String)obj;
            Glide.with(context).load(avatar).error(R.mipmap.head).into((ImageView) holder.getView(R.id.iv_recent_avatar));
        }else{
            int defaultRes = (int)obj;
            Glide.with(context).load(R.mipmap.head).error(R.mipmap.head).into((ImageView) holder.getView(R.id.iv_recent_avatar));
        }
        //会话标题
        holder.setText(R.id.tv_recent_name, conversation.getcName());
        //查询指定未读消息数
        long unread = conversation.getUnReadCount();
        if(unread>0){
            holder.setVisible(R.id.tv_recent_unread, View.VISIBLE);
            holder.setText(R.id.tv_recent_unread, String.valueOf(unread));
        }else{
            holder.setVisible(R.id.tv_recent_unread, View.GONE);
        }
    }
}
