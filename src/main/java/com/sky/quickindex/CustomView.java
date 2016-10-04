package com.sky.quickindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.IllegalFormatFlagsException;

/**
 * 作者：SKY
 * 创建时间：2016-9-11 9:48
 * 描述：自定义的view 用于绘制右侧快速索引条
 */
public class CustomView extends View {

    private Paint paint;
    private float cellWidth;
    private float cellHeight;
    private int currentIndex = -1;
    private int preIndex = -1;

    public CustomView (Context context) {
        this(context, null);
    }

    public CustomView (Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init () {
        // 创建画笔
        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setTextSize(25);
        paint.setTypeface(Typeface.DEFAULT_BOLD); // 加粗
        paint.setAntiAlias(true);   // 抗锯齿

    }

    @Override
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < Cheeses.LETTERS.length; i++) {
            String letter = Cheeses.LETTERS[i];

            Rect rect = new Rect();

            /**
             * 这个方法将文本的宽高进行测量，将其宽高设置到 rect 中
             * 参数2,3对应 字母的开始和结束
             */
            paint.getTextBounds(letter, 0, 1, rect);
            int rectWidth = rect.width();
            int rectHeight = rect.height();

            // 注意：绘制文本的基准线在左下角
            float x = cellWidth / 2 - rectWidth / 2;
            float y = rectHeight / 2 + cellHeight / 2 + i * cellHeight;
            // 选中的索引进行高亮显示
            paint.setColor(i == currentIndex ? Color.BLUE : Color.GRAY );
            paint.setTextSize(i == currentIndex ? 28 : 25);
            canvas.drawText(letter, x, y, paint);

        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (listener != null) {
                    listener.indexGone();
                }
                currentIndex = -1;
                invalidate();
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (listener != null) {
                    preIndex = currentIndex;
                    currentIndex = (int) (event.getY() / cellHeight);
                    // 处理上标和下标的越界问题
                    currentIndex = currentIndex <= 0 ? 0 : currentIndex;
                    currentIndex = currentIndex >= Cheeses.LETTERS.length ? Cheeses.LETTERS.length : currentIndex;

                    String letter = Cheeses.LETTERS[currentIndex];
                    if (preIndex != currentIndex) {
                        listener.indexVisible(letter);
                    }
                    invalidate();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 每个放置字母的空间占据的宽
        cellWidth = this.getMeasuredWidth();
        // 每个放置字母的空间占据的高，用测量的总高 / 字母的个数
        cellHeight = this.getMeasuredHeight() * 1.0f / Cheeses.LETTERS.length;
    }

    private OnIndexChangedListener listener;

    public interface OnIndexChangedListener {
        void indexVisible (String letter);

        void indexGone ();
    }

    public void setOnIndexChangedListener (OnIndexChangedListener listener) {
        this.listener = listener;
    }

}
