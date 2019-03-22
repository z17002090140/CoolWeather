package com.example.admin.helloworld;

import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textView=(TextView)findViewById(R.id.abc);
        String weatherId="CN101190401";
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=2a4ad245ad794c8b9279c9192737bc56";
        HttpUtil.sendOkHttpRequest(weatherUrl,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call,Response response) throws IOException{
                final String responseText = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(responseText);
                    }
                });
            }
        });
    }
}
