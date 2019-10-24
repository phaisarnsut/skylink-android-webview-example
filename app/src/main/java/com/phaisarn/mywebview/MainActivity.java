package com.phaisarn.mywebview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    WebView myWebView;

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String[] PERM_CAMERA = {Manifest.permission.CAMERA};

    private static final int REQUEST_AUDIO_PERMISSION = 1;
    private static final String[] PERM_AUDIO = {Manifest.permission.RECORD_AUDIO};

    private static final String URL = "https://codepen.io/temasys/pen/GogabE";

    static String TAG = "Jackwebview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        myWebView = new WebView(this);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        myWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        myWebView.setWebViewClient(new WebViewClient());

        myWebView.loadUrl(URL);

        myWebView.setWebChromeClient(new WebChromeClient() {
            // Grant permissions for camera
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d(TAG, "onPermissionRequest");

//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//                    @Override
//                    public void run() {
//                        if (request.getOrigin().toString().equals("*")) { // https://YOUR_ORIGIN_GOES_HERE
//                            request.grant(request.getResources());
//                        } else {
//                            request.deny();
//                        }
//                    }
//                });
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    request.grant(request.getResources());
//                }

                request.grant(request.getResources());
            }

            @Override
            public void onPermissionRequestCanceled(PermissionRequest request) {
                super.onPermissionRequestCanceled(request);
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        });

//        if (EasyPermissions.hasPermissions(MainActivity.this, PERM_AUDIO) == false) {
//            EasyPermissions.requestPermissions(
//                    this,
//                    "This app needs access to your audio.",
//                    REQUEST_AUDIO_PERMISSION,
//                    PERM_AUDIO);
//        }

//        if (EasyPermissions.hasPermissions(MainActivity.this, PERM_CAMERA)) {
//            //myWebView.loadUrl("Your URL");
//            myWebView.loadUrl(URL);
//
//            setContentView(myWebView);
//        } else {
//            EasyPermissions.requestPermissions(
//                    this,
//                    "This app needs access to your camera so you can take pictures.",
//                    REQUEST_CAMERA_PERMISSION,
//                    PERM_CAMERA);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myWebView.loadUrl(URL);

        EasyPermissions.requestPermissions(
                this,
                "This app needs access to your audio.",
                REQUEST_AUDIO_PERMISSION,
                PERM_AUDIO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted");

        if (EasyPermissions.hasPermissions(MainActivity.this, PERM_CAMERA)) {
            //myWebView.loadUrl("Your URL");
            myWebView.loadUrl(URL);

            setContentView(myWebView);
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs access to your camera so you can take pictures.",
                    REQUEST_CAMERA_PERMISSION,
                    PERM_CAMERA);
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied");
    }
}

