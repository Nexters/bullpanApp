package com.bullpan.bullpanapp.activity;

import android.app.Application;

import com.bullpan.bullpanapp.R;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by daehyun on 16. 2. 16..
 */
public class BaseApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/NotoSansKR-Regular-subset.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
