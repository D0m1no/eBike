package com.ebike.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 2018/1/31.
 */

public class Weather {

    public String status;
    public Basic basic;
    public Update update;
    public Now now;
    public List<Lifestyle> lifestyles;
    public class Lifestyle {
        public String brf;
        public String txt;
        public String getBrf(){
            return brf;
        }
        public String getTxt(){
            return txt;
        }
    }
}
