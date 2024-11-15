package com.example.fakeqqmusic.base.adapter;

import android.content.Context;

public class DisplayUtil {
    public static final float ROTATION_INIT_NEEDLE = -30;//角度

    private static final float BASE_WIDTH = (float) 1080.0;
    private static final float BASE_HEIGHT = (float) 1920.0;

    public static final float SCALE_NEEDLE_WIDTH = (float) (276.0 / BASE_WIDTH);
    public static final float SCALE_NEEDLE_MARGIN_LEFT = (float) (500.0 / BASE_WIDTH);
    public static final float SCALE_NEEDLE_PIVOT_X = (float) (43.0 / BASE_WIDTH);
    public static final float SCALE_NEEDLE_PIVOT_Y = (float) (43.0 / BASE_WIDTH);
    public static final float SCALE_NEEDLE_HEIGHT = (float) (413.0 / BASE_HEIGHT);
    public static final float SCALE_NEEDLE_MARGIN_TOP = (float) (43.0 / BASE_HEIGHT);


    public static final float SCALE_DISC_SIZE = (float) (813.0 / BASE_WIDTH);
    public static final float SCALE_DISC_MARGIN_TOP = (float) (190 / BASE_HEIGHT);


    public static final float SCALE_MUSIC_PIC_SIZE = (float) (533.0 / BASE_WIDTH);

    public static int getWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}