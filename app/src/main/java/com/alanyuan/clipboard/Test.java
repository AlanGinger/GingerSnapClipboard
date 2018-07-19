package com.alanyuan.clipboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class Test extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String abc = Constants.NOTIFICATION_CHANNEL_NAME;
    }
}
