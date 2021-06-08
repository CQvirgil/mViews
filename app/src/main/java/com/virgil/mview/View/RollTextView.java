package com.virgil.mview.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class RollTextView extends TextView {
    private final String TAG = "RollTextView";
    private int width;
    private int height;
    private int offset;
    private int x;
    private int textWidth;
    private int textHeight;
    private int speed;
    private boolean isRoll = true;
    private String text;
    private Paint paint;

    public RollTextView(Context context) {
        super(context);
        init();
    }

    public RollTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    public RollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    private void init() {
        width = 0;
        height = 0;
        offset = 0;
        textWidth = 0;
        speed = 16;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(getTextColors().getDefaultColor());
        paint.setTextSize(getTextSize());
    }

    public void startRoll(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRoll) {
                    try {
                        if (x < -textWidth - 20 + width) {
                            Thread.sleep(2000);
                            offset = 0;
                        } else {
                            offset++;
                        }
                        postInvalidate();
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        //获取文本长度
        text = getText().toString();
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        textWidth = rect.width();
        textHeight = rect.height();
        if (textWidth > width){     //判断是否开启滚动
            isRoll = true;
            startRoll();
        } else {
            isRoll = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        if (isRoll) {
            x = -offset + 20;
        } else {
            x = (width / 2 - textWidth / 2) - offset;
        }
        canvas.drawText(text, x, height / 2 + textHeight / 2, paint);
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int px2dip(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
