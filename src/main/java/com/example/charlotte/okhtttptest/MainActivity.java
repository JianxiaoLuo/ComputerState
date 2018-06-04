package com.example.charlotte.okhtttptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 此类参考《android第一行代码》okhttp部分
 * 实现了获取JSON数据和用intent传参
 * @author Charlotte
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView responseText;
    StateBean stateBean;

    final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Button sendRequest = (Button) findViewById(R.id.send_request);
       Button testPie = (Button) findViewById(R.id.test_pie);
       responseText = (TextView) findViewById(R.id.response_text);
       sendRequest.setOnClickListener(this);
       testPie.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send_request){
            sendRequestWithOKHttp();
        }
    }

    //new一个线程，进行通信
    private void sendRequestWithOKHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8080/f/state/Test") //模拟器使用此地址，下到手机上需要在同一个局域网，ipconfig获取电脑IP地址
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);

                    //从Json转换成Bean类
                    stateBean = new StateBean();
                    Gson gson = new Gson();
                    stateBean = gson.fromJson(responseData,StateBean.class);

                    //获取数据，并通过intent和bundle传到PieChartActivity
                    if(null != stateBean) {
                        double usedMemPre = stateBean.getUsedMemPre();
                        double cpuRatio = stateBean.getCpuRatio();
                        double usedFilePre = stateBean.getUsedFilePre();

                        Logger.i("usedMemPre=" + usedMemPre + "   cpuRatio" + cpuRatio + "   usedFilePre" + usedFilePre);

                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putDouble("usedMemPre", usedMemPre);
                        bundle.putDouble("cpuRatio", cpuRatio);
                        bundle.putDouble("usedFilePre", usedFilePre);
                        intent.putExtras(bundle);
                        intent.setClass(MainActivity.this, PieChartActivity.class);
                        startActivity(intent);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示在界面上
                responseText.setText(response);
            }
        });
    }
}
