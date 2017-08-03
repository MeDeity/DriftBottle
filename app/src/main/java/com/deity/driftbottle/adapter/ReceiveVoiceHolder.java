package com.deity.driftbottle.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deity.driftbottle.R;
import com.deity.driftbottle.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import cn.bmob.newim.bean.BmobIMAudioMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobDownloadManager;
import cn.bmob.newim.listener.FileDownloadListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * 接收到的文本类型
 */
public class ReceiveVoiceHolder extends BaseViewHolder {

  @BindView(R.id.iv_avatar)
  protected ImageView iv_avatar;

  @BindView(R.id.tv_time)
  protected TextView tv_time;

  @BindView(R.id.tv_voice_length)
  protected TextView tv_voice_length;
  @BindView(R.id.iv_voice)
  protected ImageView iv_voice;

  @BindView(R.id.progress_load)
  protected ProgressBar progress_load;

  private String currentUid="";

  public ReceiveVoiceHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
    super(context, root, R.layout.item_chat_received_voice,onRecyclerViewListener);
    try {
      currentUid = BmobUser.getCurrentUser(context).getObjectId();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void bindData(Object o) {
    BmobIMMessage msg = (BmobIMMessage)o;
    //用户信息的获取必须在buildFromDB之前，否则会报错'Entity is detached from DAO context'
    final BmobIMUserInfo info = msg.getBmobIMUserInfo();
      Glide.with(context).load(info.getAvatar()).error(R.mipmap.head).into(iv_avatar);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    String time = dateFormat.format(msg.getCreateTime());
    tv_time.setText(time);
    iv_avatar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        toast("点击" + info.getName() + "的头像");
      }
    });
    //显示特有属性
    final BmobIMAudioMessage message = BmobIMAudioMessage.buildFromDB(false, msg);
    boolean isExists = BmobDownloadManager.isAudioExist(currentUid, message);
    if(!isExists){//若指定格式的录音文件不存在，则需要下载，因为其文件比较小，故放在此下载
        BmobDownloadManager downloadTask = new BmobDownloadManager(getContext(),msg,new FileDownloadListener() {

          @Override
          public void onStart() {
              progress_load.setVisibility(View.VISIBLE);
              tv_voice_length.setVisibility(View.GONE);
              iv_voice.setVisibility(View.INVISIBLE);//只有下载完成才显示播放的按钮
          }

          @Override
          public void done(BmobException e) {
            if(e==null){
                progress_load.setVisibility(View.GONE);
                tv_voice_length.setVisibility(View.VISIBLE);
                tv_voice_length.setText(message.getDuration()+"\''");
                iv_voice.setVisibility(View.VISIBLE);
            }else{
                progress_load.setVisibility(View.GONE);
                tv_voice_length.setVisibility(View.GONE);
                iv_voice.setVisibility(View.INVISIBLE);
            }
          }
        });
        downloadTask.execute(message.getContent());
    }else{
        tv_voice_length.setVisibility(View.VISIBLE);
        tv_voice_length.setText(message.getDuration() + "\''");
    }
    iv_voice.setOnClickListener(new NewRecordPlayClickListener(getContext(), message, iv_voice));

    iv_voice.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (onRecyclerViewListener != null) {
                 onRecyclerViewListener.onItemLongClick(getAdapterPosition());
            }
            return true;
        }
    });

  }

  public void showTime(boolean isShow) {
    tv_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
  }
}