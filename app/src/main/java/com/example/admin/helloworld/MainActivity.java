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
        String weatherId="CN101020300";
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=19a70794d47b4a9b81a7bf57a21922ee";
        HttpUtil.sendOkHttpRequest(weatherUrl,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call,Response response) throws IOException{
                final String responseText = response.body().string();
                textView.setText(responseText);
            }
        });
    }
}
