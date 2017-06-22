package com.test.myapplication;

//Source : http://www.mkyong.com/android/how-to-turn-onoff-camera-ledflashlight-in-android/ (https://goo.gl/dbxSW3)

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

@SuppressWarnings( "deprecation" )   //Camera and Parameters are deprecated
public class MainActivity extends Activity {

    //flag to detect flash is on or off
    private boolean isLightOn = false;

    Camera camera;
    ImageButton button;

    @Override
    protected void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (ImageButton) findViewById(R.id.buttonFlashlight);
        //final RelativeLayout relative = (RelativeLayout) findViewById(R.id.rellay);

        Context context = this;
        PackageManager pm = context.getPackageManager();

        // if device support camera?
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.d("err", "Device has no camera!");
            return;
        }

        camera = Camera.open();
        final Parameters p = camera.getParameters();

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (isLightOn) {

                    Log.d("info", "torch is turned off!");

                    p.setFlashMode(Parameters.FLASH_MODE_OFF);
                    camera.setParameters(p);
                    camera.stopPreview();
                    isLightOn = false;
                    //relative.setBackgroundColor(Color.BLACK);
                    //this does not work here

                } else {

                    Log.d("info", "torch is turned on!");

                    p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                    //relative.setBackgroundColor(Color.WHITE);
                    camera.setParameters(p);
                    camera.startPreview();
                    isLightOn = true;

                }

            }
        });

    }
}
