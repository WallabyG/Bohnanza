package com.tobitint.bohnanza;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Stack;

/**
 *
 * 정보 공유 애플리케이션
 *
 * @author YJH
 * @version 1.0
 *
 */
public class InfoApplication extends Application {

    /**
     * 각 콩의 이미지를 저장하는 맵
     */
    private static final HashMap<Integer, Integer> beanImageIdMap = new HashMap<Integer, Integer>() {{
        put(6, R.drawable.gardenbean_card);
        put(8, R.drawable.redbean_card);
        put(10, R.drawable.blackeyedbean_card);
        put(12, R.drawable.soybean_card);
        put(14, R.drawable.greenbean_card);
        put(16, R.drawable.stinkbean_card);
        put(18, R.drawable.chilibean_card);
        put(20, R.drawable.bluebean_card);
        put(0, R.drawable.empty_card);
        put(-1, R.drawable.card_back);
    }};

    /**
     * 액티비티 스택
     */
    private Stack<Activity> activityStack = new Stack<>();

    /**
     * 플레이어 이름
     */
    private String playerName = null;

    /**
     * 서수
     */
    private String[] ordinalNumber = {"1st", "2nd", "3rd", "4th", "5th"};

    DisplayMetrics dm;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int getBeanImageId(int beanNumber) {
        return beanImageIdMap.get(beanNumber);
    }

    public void pushCurrentActivity(Activity currentActivity) {
        activityStack.push(currentActivity);
    }

    public Activity popCurrentActivity() {
        return activityStack.pop();
    }

    public Activity getCurrentActivity() {
        return activityStack.peek();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getOrdinalNumber(int order) {
        return ordinalNumber[order];
    }

    public void initDm() {
        dm = getResources().getDisplayMetrics();
    }

    public int dp2px(int dp) {
        return Math.round(dp * dm.density);
    }

}
