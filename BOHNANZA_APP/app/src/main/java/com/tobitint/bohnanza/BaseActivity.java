package com.tobitint.bohnanza;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

/**
 * 기본 액티비티
 */
public class BaseActivity extends AppCompatActivity {

    protected InfoApplication infoApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        infoApplication = (InfoApplication) this.getApplicationContext();
    }

    @Override
    protected void onPause() {
        super.onPause();

        infoApplication.popCurrentActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();

        infoApplication.pushCurrentActivity(this);
    }

    /**
     *
     * 기본 입력 필터<br>
     * - 영문자, 숫자만 허용
     *
     * @author YJH
     * @version 1.0
     *
     */
    public class BaseInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");

            if (source.equals("") || pattern.matcher(source).matches()) {
                return source;
            }

            return "";
        }
    }

}
