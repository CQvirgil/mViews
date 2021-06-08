package com.virgil.mview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.virgil.mview.View.CircularProgress;
import com.virgil.mview.View.PassWordEditorText.PassWordEditorText;
import com.virgil.mview.View.PassWordEditorText.PassWordListener;
import com.virgil.mview.View.RollTextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private RollTextView mRollTextView;
    private CircularProgress mCircularProgress;
    private PassWordEditorText vPSWEditor;
    private TextView value;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRollTextView = findViewById(R.id.main_roll_text_view);
        value = findViewById(R.id.main_progress_value);
        vPSWEditor = findViewById(R.id.main_pass_word_editor);
        vPSWEditor.setOnPasswordListener(new PassWordListener() {
            @Override
            public void onFinish(String text) {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
        mCircularProgress = findViewById(R.id.main_circular_progress);
        i = 0;
        final Handler handler = new Handler();
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                i++;
                mCircularProgress.setPercentage(i);
                if (i > 100) {
                    i = 0;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        value.setText(i + "%");
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 100);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
