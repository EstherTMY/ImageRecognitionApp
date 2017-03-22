package com.example.esthertang.imagerecognitionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton bagButton;
    ImageButton shoesButton;
    ImageButton bottleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bagButton = (ImageButton) findViewById(R.id.bagButton);
        shoesButton = (ImageButton) findViewById(R.id.shoesButton);
        bottleButton = (ImageButton) findViewById(R.id.bottleButton);
        bagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DescribeActivity.class);
                intent.putExtra("itemName","bag");
                startActivity(intent);
            }
        });
        shoesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DescribeActivity.class);
                intent.putExtra("itemName","shoes");
                startActivity(intent);
            }
        });
        shoesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DescribeActivity.class);
                intent.putExtra("itemName","shoes");
                startActivity(intent);
            }
        });
        bottleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DescribeActivity.class);
                intent.putExtra("itemName","bottle");
                startActivity(intent);
            }
        });
    }
}
