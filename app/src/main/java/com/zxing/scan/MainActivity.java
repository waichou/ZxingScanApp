package com.zxing.scan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.zxing.scan.vertical.ScanActivity;

/**
 * zxing封装的demo https://github.com/journeyapps/zxing-android-embedded
 * 参看博客说明：https://blog.csdn.net/yaomli/article/details/69384094?locationNum=9&fps=1
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new IntentIntegrator(this).initiateScan();

        Button btnScan=(Button) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //默认横屏调用
                //new IntentIntegrator(MainActivity.this).initiateScan(); //初始化扫描

                //竖屏调用
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setCaptureActivity(ScanActivity.class);
                integrator.setPrompt("请扫描"); //底部的提示文字，设为""可以置空
                integrator.setCameraId(0); //前置或者后置摄像头
                integrator.setBeepEnabled(false); //扫描成功的「哔哔」声，默认开启
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });
    }

    /**
     *回调获取扫描得到的条码值
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "扫码取消！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
