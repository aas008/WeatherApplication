package com.example.weatherapplication_ny;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.weatherapplication_ny.databinding.ActivityMainBinding;
import com.example.weatherapplication_ny.model.MyWeather;

public class MainActivity extends AppCompatActivity implements MyWeatherTaskListener
{
    private ActivityMainBinding binding;

    //Web URL of the JSON file
    private String mApiKey = "3786b2385721e59b7bb961335fa2f5a2";
    private String mCity = "New York";
    private String mCountry = "United States";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //http://api.openweathermap.org/data/2.5/weather?q=city,country&
        // APPID={your api key};
        String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q=" + mCity + "," + mCountry + "&appid=" + mApiKey;
        //http://api.openweathermap.org/data/2.5/weather?q=New York,United States&appid=34144c3cbc32dd09ea134d41ac886c9b
        new MyWeatherTask(this).execute(weatherURL);
    }

    @Override
    public void onMyWeatherTaskPreExecute()
    {
        binding.loadinglayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMyWeatherTaskPostExecute(MyWeather myWeather)
    {
        if (myWeather != null)
        {
            binding.cityTextView.setText(mCity);
            binding.cityTextView2.setText(mCountry);

            binding.ConditionTextViewer.setText(myWeather.getWeatherCondition());
            binding.conditionDescriptionTextViewer.setText(myWeather.getWeatherDescription());

            int temp = Math.round(myWeather.getTemperature() - 273.15f);
            String tempStr = String.valueOf(temp);
            binding.temperatureTextView.setText(tempStr);

            String imgUrl = "http://openweathermap.org/img/wn/" + myWeather.getWeatherIconStr() + "@2x.png";

            Glide.with(MainActivity.this)
                    .asBitmap()
                    .load(imgUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(binding.weatherIconImage);
        }
        binding.loadinglayout.setVisibility(View.GONE);
    }
}