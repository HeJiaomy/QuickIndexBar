package com.example.hj.quickindexbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class QuickIndexBar extends View {

    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};

    private Paint paint;
    private int width;
    private float cellHeight;

    public QuickIndexBar(Context context) {
        super(context);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(24);
        paint.setTextAlign(Paint.Align.CENTER);//设置文本的起点是文字底边的中心
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        //得到一个格子的高度
        cellHeight = getMeasuredHeight() / indexArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < indexArr.length; i++) {
            int x = width / 2;
            float y = cellHeight / 2 + getTextHeight(indexArr[i])/2 + i * cellHeight;
            paint.setColor(lastIndex==i?Color.BLACK:Color.WHITE);
            canvas.drawText(indexArr[i], x, y, paint);
        }
    }

    private float getTextHeight(String str) {
        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);
        return bounds.height();
    }

    private int lastIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                //得到触摸点的索引
                int index = (int) (y / cellHeight);
                if (index >= 0 && index <= 25) {
                    if (listener != null)
                        listener.onTouchLetter(indexArr[index]);
                }
                lastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                lastIndex = -1;
                break;
        }
        //引起重绘
        invalidate();
        return true;
    }

    private OnTouchLetterListener listener;

    public void setOnTouchLetterListener(OnTouchLetterListener listener) {
        this.listener = listener;
    }

    public interface OnTouchLetterListener {
        void onTouchLetter(String letter);
    }
}
