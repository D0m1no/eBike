package com.ebike.android;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebike.android.gson.Weather;
import com.ebike.android.util.HttpUtil;
import com.ebike.android.util.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RideInfoActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private TextView title_city;
    private TextView title_update_time;
    private TextView degree_text;
    private TextView weather_info_text;
    private LinearLayout damn_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ride_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ride_info);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_ride_info);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.home);
        }
        damn_layout = (LinearLayout) findViewById(R.id.suggestions);
        damn_layout.setVisibility(View.INVISIBLE);
        requestWeather();
        damn_layout.setVisibility(View.VISIBLE);
    }

    public void requestWeather() {
        String url = "https://free-api.heweather.com/s6/weather/now?location=南京&key=783dec58ce06408fba92163be7b7d623";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RideInfoActivity.this, "获取天气信息失败", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RideInfoActivity.this, "获取天气信息成功", Toast.LENGTH_LONG).show();
                        showWeather(weather);
                    }
                });
            }
        });
    }

    private void showWeather(Weather weather) {
        String info = weather.basic.cityName;
        title_city = (TextView) findViewById(R.id.title_city);
        title_city.setText(info);

        info = weather.update.updateTime.split(" ")[1];
        title_update_time = (TextView) findViewById(R.id.title_update_time);
        title_update_time.setText(info);

        info = weather.now.temperature + "℃";
        degree_text = (TextView) findViewById(R.id.degree_text);
        degree_text.setText(info);

        info = weather.now.cond_txt;
        weather_info_text = (TextView) findViewById(R.id.weather_info_text);
        weather_info_text.setText(info);
        //Toast.makeText(RideInfoActivity.this, info, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }
}