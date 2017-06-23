package com.deity.driftbottle;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        beach_shui.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_shake));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.send_nav_image)
    public void sendBottleMsg(View view){
        new AlertDialog.Builder(MainActivity.this).setView(R.layout.dialog_bottle_send)
                .show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.get_nav_image)
    public void getBottleMsg(View view){
        new AlertDialog.Builder(MainActivity.this).setView(R.layout.dialog_bottle_receiver)
                .show();
    }
}
