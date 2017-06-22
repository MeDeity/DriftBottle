package com.deity.driftbottle;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.send_nav_image)
    public ImageView send_nav_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
