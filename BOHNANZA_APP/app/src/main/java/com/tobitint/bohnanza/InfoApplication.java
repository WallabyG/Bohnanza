package com.tobitint.bohnanza;

import android.app.Activity;
import android.app.Application;

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
     * 각 콩의 이미지 파일을 저장하는 맵
     */
    private static final HashMap<Integer, Integer> beanImageMap = new HashMap<Integer, Integer>() {{
        put(1, 1);
    }};

    /**
     * 액티비티 스택
     */
    private Stack<Activity> activityStack = new Stack<>();

    /**
     * 플레이어 이름
     */
    private String playerName = null;

    @Override
    public void onCreate() {
        super.onCreate();
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

}
