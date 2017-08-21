package com.example.a1.tmap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by a1 on 2017. 8. 22..
 */

public class Intro extends AppCompatActivity {
    Button btn;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        btn = (Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intro.this, MainActivity.class);
                startActivity(intent);
            }
        });
        checkPermission1();

        //checkPermission2();

    }

    public void checkPermission1() {

        int pCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (pCheck1 == PackageManager.PERMISSION_DENIED) {
            //권한 추가
            ActivityCompat.requestPermissions(Intro.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);

        }

        int pCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(pCheck2 == PackageManager.PERMISSION_DENIED){
            //권한 추가
            ActivityCompat.requestPermissions(Intro.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        }



    }
    public void checkPermission2() {
        int pCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (pCheck == PackageManager.PERMISSION_DENIED) {
            //권한 추가
            ActivityCompat.requestPermissions(Intro.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            Log.d("ssss","success");

                        } else {
                            Log.d("ssss","fail");
                        }
                    }
                }
                break;

            case REQUEST_ACCESS_FINE_LOCATION:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            Log.d("ssss","success2");
                        } else {
                            Log.d("ssss","fail2");
                        }
                    }
                }
                break;
        }
    }

}