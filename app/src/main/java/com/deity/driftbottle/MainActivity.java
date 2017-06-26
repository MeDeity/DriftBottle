package com.deity.driftbottle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.beach_shui)
    public ImageView beach_shui;

    @BindView(R.id.beach_lang)
    public ImageView beach_lang;

    @BindView(R.id.bottle_getting)
    public ImageView bottle_getting;

    @BindView(R.id.bottle_sending)
    public ImageView bottle_sending;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        beach_shui.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_shake));
        beach_lang.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_shake));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.send_nav_image)
    public void sendBottleMsg(View view){
//        new AlertDialog.Builder(MainActivity.this).setView(R.layout.dialog_bottle_send)
//                .show();
        bottle_sending.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_move_up));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.get_nav_image)
    public void getBottleMsg(View view){
//        new AlertDialog.Builder(MainActivity.this).setView(R.layout.dialog_bottle_receiver)
//                .show();
        bottle_getting.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_move_up));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.item_bottle_msg)
    public void getBottle(View view){
        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
        startActivity(intent);



    }
}
