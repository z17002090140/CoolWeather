package com.example.admin.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private TextView textView;
    private  String[] weatherId={"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
//    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Intent intent = getIntent();
        final int cid = intent.getIntExtra("cid",0);
        final int pid = intent.getIntExtra("pid",0);
        final int Cid = intent.getIntExtra("Cid",0);
        this.textView = (TextView)findViewById(R.id.textView);
//        this.button=(Button)findViewById(R.id.button);
//        this.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(WeatherActivity.this,CityActivity.class));
//            }
//        });
//        String weatherId =
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=19a70794d47b4a9b81a7bf57a21922ee";
//        String weatherUrl = "http://guolin.tech/api/china/"+pid+"/"+cid;
        HttpUtil.sendOkHttpRequest(weatherUrl,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException{
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
    private void parseJSONWithJSONObject(String responseText) {
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONArray(responseText);
            String[] result=new String[jsonArray.length()];
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                this.weatherId[i]=jsonObject.getString("weather_id");
            }
//            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        return null;
    }
}
