package com.example.admin.permitme;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Const
    private static final String TAG = "MainActivity:";
    private static final int PERMISSION_REQUEST_CODE = 1234;


    //Widget
    private Button cameraButton;

    //Var
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraButton = findViewById(R.id.cameraClick);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "LogTag: cameraButton.onClick: Clicked!");
                checkPermissions();
            }
        });
    }

    private void checkPermissions() {
        Log.d(TAG, "LogTag: checkPermissions: Asking for user permissions!");
        final String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permissions[0]) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), permissions[1]) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), permissions[2]) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "LogTag: checkPermissions: Asking for All Permissions!");
            if (shouldShowRequestPermissionRationale(permissions[0]) && shouldShowRequestPermissionRationale(permissions[1])) {
                Log.d(TAG, "LogTag: checkPermissions: Asking for All Permissions: 2");
                ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST_CODE);
                flag = true;
            } else if (flag) {
                Log.d(TAG, "LogTag: checkPermissions: Asking for All Permissions: 3");
                onDialog("Camera & Storage");
            } else {
                Log.d(TAG, "LogTag: checkPermissions: Asking for All Permissions: 1");
                ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST_CODE);
            }


        } else if (ContextCompat.checkSelfPermission(getApplicationContext(), permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "LogTag: checkPermissions: Asking for Camera Permission!");
            if (shouldShowRequestPermissionRationale(permissions[0])) {
                Log.d(TAG, "LogTag: checkPermissions: Asking for Camera Permission: 2");
                ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST_CODE);
                flag = true;
            } else if (flag) {
                Log.d(TAG, "LogTag: checkPermissions: Asking for Camera Permission: 3");
                onDialog("Camera");
            } else {
                Log.d(TAG, "LogTag: checkPermissions: Asking for Camera Permission: 2");
                ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST_CODE);
            }


        } else if (ContextCompat.checkSelfPermission(getApplicationContext(), permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "LogTag: checkPermissions: Asking for Storage Permission!");
            if (shouldShowRequestPermissionRationale(permissions[1])) {
                Log.d(TAG, "LogTag: checkPermissions: Asking for Storage Permission: 2");
                ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST_CODE);
                flag = true;
            } else if (flag) {
                Log.d(TAG, "LogTag: checkPermissions: Asking for Storage Permission: 3");
                onDialog("Storage");
            } else {
                Log.d(TAG, "LogTag: checkPermissions: Asking for Storage Permission: 2");
                ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST_CODE);
            }
        } else {
            Log.d(TAG, "LogTag: checkPermissions: All Granted!");
            proceed();
        }
    }


    public void proceed() {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "LogTag: onRequestPermissionsResult: Checking Permission Result!");
        checkPermissions();
    }

    public void onDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("We Need:")
                .setMessage("Your " + message + "!")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Please Enable The permission Manually", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        MainActivity.this.startActivity(intent);
                    }
                })
                .setNegativeButton("nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }
}
