package com.example.esthertang.imagerecognitionapp;

/**
 * Created by esthertang on 2017/3/19.
 */

//
// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license.
//
// Microsoft Cognitive Services (formerly Project Oxford): https://www.microsoft.com/cognitive-services
//
// Microsoft Cognitive Services (formerly Project Oxford) GitHub:
// https://github.com/Microsoft/Cognitive-Vision-Android
//
// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License:
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED ""AS IS"", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.esthertang.imagerecognitionapp.helper.SelectImageActivity;
import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Caption;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Category;
import com.microsoft.projectoxford.vision.contract.Face;
import com.microsoft.projectoxford.vision.contract.Tag;
import com.microsoft.projectoxford.vision.contract.Caption;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;
import com.example.esthertang.imagerecognitionapp.helper.ImageHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

public class DescribeActivity extends ActionBarActivity {

    // Flag to indicate which task is to be performed.
    private static final int REQUEST_SELECT_IMAGE = 0;

    // The button to select an image
    private Button mButtonSelectImage;

    // The URI of the image selected to detect.
    private Uri mImageUri;

    // The image selected to detect.
    private Bitmap mBitmap;

    // The edit to show status and result.
//    private EditText mEditText;

    private VisionServiceClient client;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    private Field field1;
    private Field field2;
    private Field field3;
    private Field field4;

    String itemName;
    String chineseName;

    SoundPool soundPool;
    //定义一个HashMap用于存放音频流的ID
    HashMap<Integer, Integer> musicId=new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        itemName = intent.getStringExtra("itemName");
        if(itemName.equals("bag")){
            chineseName = "背包";
        }else if(itemName.equals("shoes")){
            chineseName = "鞋子";
        }else if(itemName.equals("bottle")){
            chineseName = "瓶子";
        }
        setContentView(R.layout.activity_describe);

        if (client==null){
            client = new VisionServiceRestClient("56656b58c06c4060bbfa60c178d446a4");
        }

        mButtonSelectImage = (Button)findViewById(R.id.buttonSelectImage);
        imageView1 = (ImageView) findViewById(R.id.ImageView1);
        imageView2 = (ImageView) findViewById(R.id.ImageView2);
        imageView3 = (ImageView) findViewById(R.id.ImageView3);
        imageView4 = (ImageView) findViewById(R.id.ImageView4);

        Class drawable = R.drawable.class;

        try {
            field1 = drawable.getField(itemName+"imageview1");
            field2 = drawable.getField(itemName+"imageview2");
            field3 = drawable.getField(itemName+"imageview3");
            field4 = drawable.getField(itemName+"imageview4");
            int res_ID1 = field1.getInt(field1.getName());
            int res_ID2 = field2.getInt(field2.getName());
            int res_ID3 = field3.getInt(field3.getName());
            int res_ID4 = field4.getInt(field4.getName());
            imageView1.setBackgroundResource(res_ID1);
            imageView2.setBackgroundResource(res_ID2);
            imageView3.setBackgroundResource(res_ID3);
            imageView4.setBackgroundResource(res_ID4);
        } catch (Exception e) {}
        soundPool=new SoundPool(12, 0,5);
        //通过load方法加载指定音频流，并将返回的音频ID放入musicId中
        musicId.put(1, soundPool.load(this, R.raw.tip, 1));
        musicId.put(2, soundPool.load(this, R.raw.good, 1));
        musicId.put(3, soundPool.load(this, R.raw.tryagain, 1));
        soundPool.play(musicId.get(1),1,1, 0, 0, 1);


//        if(itemName.equals("bag")) {
//            imageView1.setBackgroundResource(R.drawable.bagimageview1);
//            imageView2.setBackgroundResource(R.drawable.bagimageview2);
//            imageView3.setBackgroundResource(R.drawable.bagimageview3);
//            imageView4.setBackgroundResource(R.drawable.bagimageview4);
//        }


//        mEditText = (EditText)findViewById(R.id.editTextResult);
//        mEditText.setText(itemName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_describe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doDescribe() {
        mButtonSelectImage.setEnabled(false);
        mButtonSelectImage.setText("正在分析");
        //mEditText.setText("Describing...");

        try {
            new doRequest().execute();
        } catch (Exception e)
        {
            //mEditText.setText("Error encountered. Exception is: " + e.toString());
        }
    }

    // Called when the "Select Image" button is clicked.
    public void selectImage(View view) {
        //mEditText.setText("");

        Intent intent;
        intent = new Intent(DescribeActivity.this, SelectImageActivity.class);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }


    // Called when image selection is done.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("DescribeActivity", "onActivityResult");
        switch (requestCode) {
            case REQUEST_SELECT_IMAGE:
                if(resultCode == RESULT_OK) {
                    // If image is selected successfully, set the image URI and bitmap.
                    mImageUri = data.getData();

                    mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                            mImageUri, getContentResolver());
                    if (mBitmap != null) {
                        // Show the image on screen.
//                        ImageView imageView = (ImageView) findViewById(R.id.selectedImage);
//                        imageView.setImageBitmap(mBitmap);
//
//                        // Add detection log.
//                        Log.d("DescribeActivity", "Image: " + mImageUri + " resized to " + mBitmap.getWidth()
//                                + "x" + mBitmap.getHeight());

                        doDescribe();
                    }
                }
                break;
            default:
                break;
        }
    }


    private String process() throws VisionServiceException, IOException {
        Gson gson = new Gson();

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        AnalysisResult v = this.client.describe(inputStream, 1);

        String result = gson.toJson(v);
        Log.d("result", result);

        return result;
    }

    private class doRequest extends AsyncTask<String, String, String> {
        // Store error message
        private Exception e = null;

        public doRequest() {
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                return process();
            } catch (Exception e) {
                this.e = e;    // Store error
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            // Display based on error existence

            //mEditText.setText("");
            if (e != null) {
                //mEditText.setText("Error: " + e.getMessage());
                this.e = null;
            } else {
                Gson gson = new Gson();
                AnalysisResult result = gson.fromJson(data, AnalysisResult.class);

//                mEditText.append("Image format: " + result.metadata.format + "\n");
//                mEditText.append("Image width: " + result.metadata.width + ", height:" + result.metadata.height + "\n");
//                mEditText.append("\n");
//
//                for (Caption caption: result.description.captions) {
//                    mEditText.append("Caption: " + caption.text + ", confidence: " + caption.confidence + "\n");
//                }
//                mEditText.append("\n");
//
                int count = 0;
                for (String tag: result.description.tags) {
                    if(tag.equals(itemName)){
                        count++;
                        soundPool.play(musicId.get(2),1,1, 0, 0, 1);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(DescribeActivity.this);
//              dialog.setIcon(R.drawable.ic_launcher);//窗口头图标
                        dialog.setTitle("恭喜！");//窗口名
                        dialog.setMessage("非常好! 这张图里有"+chineseName+"!");
                        dialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Intent intent;
                                intent = new Intent(DescribeActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        dialog.show();

                    }
                }

                if(count==0){
                    soundPool.play(musicId.get(3),1,1, 0, 0, 1);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(DescribeActivity.this);
//              dialog.setIcon(R.drawable.ic_launcher);//窗口头图标
                    dialog.setTitle("抱歉");//窗口名
                    dialog.setMessage("这张图里没有"+chineseName+", 请再试一次");
                    dialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
                    dialog.show();
                }


//                mEditText.append("\n");
//
//                mEditText.append("\n--- Raw Data ---\n\n");
//                mEditText.append(data);

                //mEditText.setSelection(0);
            }
            mButtonSelectImage.setEnabled(true);
            mButtonSelectImage.setText("请选择图片");

        }
    }
}
