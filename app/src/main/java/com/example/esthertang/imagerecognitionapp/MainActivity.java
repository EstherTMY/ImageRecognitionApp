package com.example.esthertang.imagerecognitionapp;

import android.content.Intent;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ImageButton bagButton;
    ImageButton shoesButton;
    ImageButton bottleButton;

    SoundPool soundPool;
    //定义一个HashMap用于存放音频流的ID
    HashMap<Integer, Integer> musicId=new HashMap<Integer, Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bagButton = (ImageButton) findViewById(R.id.bagButton);
        shoesButton = (ImageButton) findViewById(R.id.shoesButton);
        bottleButton = (ImageButton) findViewById(R.id.bottleButton);
        soundPool=new SoundPool(12, 0,5);
        musicId.put(1, soundPool.load(this, R.raw.bag, 2));
        musicId.put(2, soundPool.load(this, R.raw.shoes, 2));
        musicId.put(3, soundPool.load(this, R.raw.bottle, 2));
        bagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                soundPool.play(musicId.get(1),1,1, 0, 0, 1);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DescribeActivity.class);
                intent.putExtra("itemName","bag");
                startActivity(intent);

            }
        });
        shoesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(musicId.get(2),1,1, 0, 0, 1);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DescribeActivity.class);
                intent.putExtra("itemName","shoes");
                startActivity(intent);
            }
        });

        bottleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(musicId.get(3),1,1, 0, 0, 1);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DescribeActivity.class);
                intent.putExtra("itemName","bottle");
                startActivity(intent);
            }
        });
    }
}
