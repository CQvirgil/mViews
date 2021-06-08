package com.virgil.passwordeditortext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

@SuppressLint("AppCompatCustomView")
public class PassWordEditorText extends EditText {
    private final String TAG = "PassWordEditorText";
    private float width;
    private float height;
    private int text_lenth;
    private String rect_color = "#dddddd";
    private String point_color = "#000000";
    private PassWordListener passwordListener = null;

    public void setOnPasswordListener(PassWordListener passwordListener) {
        this.passwordListener = passwordListener;
    }

    public String getRect_color() {
        return rect_color;
    }

    public void setRect_color(String rect_color) {
        this.rect_color = rect_color;
    }

    public String getPoint_color() {
        return point_color;
    }

    public void setPoint_color(String point_color) {
        this.point_color = point_color;
    }

    public PassWordEditorText(Context context) {
        super(context);
        set();
    }

    public PassWordEditorText(Context context, AttributeSet attrs) {
        super(context, attrs);
        set();
    }

    public PassWordEditorText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        set();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PassWordEditorText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        set();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = setDip(MeasureSpec.getSize(widthMeasureSpec));
        height = setDip(MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        drawRect(canvas);

        drawCircle(canvas);

    }

    private void drawCircle(Canvas canvas) {
        Paint c_paint = new Paint();
        c_paint.setAntiAlias(true);
        c_paint.setDither(true);
        c_paint.setColor(Color.parseColor(point_color));
        int circle_x = 26;
        for (int i = 0; i < text_lenth; i++) {
            canvas.drawCircle(setDip(circle_x), setDip(25), setDip(6), c_paint);
            circle_x += 50;
        }
    }

    private void drawRect(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(rect_color));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(setDip(1));
        paint.setStyle(Paint.Style.STROKE);
        int rectf_left = 6; //(1,5)
        int rectf_top = 5;
        int rectf_rigth = 47;//(1,45)
        int rectf_bottom = 45;
        for (int i = 0; i < 6; i++) {
            RectF rect = new RectF(setDip(rectf_left), setDip(rectf_top), setDip(rectf_rigth), setDip(rectf_bottom));
            canvas.drawRoundRect(rect, setDip(3), setDip(3), paint);
            rectf_left += 50;
            rectf_rigth += 50;
        }
    }

    private void set() {
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setCursorVisible(false);
        this.setMaxLines(1);
        this.setMaxEms(6);
        this.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        NullMenuEditText();
        setMaxLines(6);
    }

    private int setDip(int dp) {
        int px = dip2px(dp);
        return px;
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int px2dip(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        text_lenth = text.length();
        postInvalidate();
        Log.i(TAG, "onTextChanged: " + text);
        if (text_lenth == 6 && passwordListener != null) {
            passwordListener.onFinish(text.toString());
        }
    }

    public void NullMenuEditText() {
        setLongClickable(false);
        setTextIsSelectable(false);
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        return true;
    }
}