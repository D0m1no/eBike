package com.ebike.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 2018/1/31.
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond_txt")
    public String cond_txt;

}
