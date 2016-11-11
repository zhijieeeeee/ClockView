package com.don.clockviewlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * <p>
 * Description：动态时钟
 * </p>
 *
 * @author tangzhijie
 */
public class ClockView extends View {

    //使用wrap_content时默认的尺寸
    private final static int DEFAULT_SIZE = 400;

    //刻度线宽度
    private final static int MARK_WIDTH = 8;

    //刻度线长度
    private final static int MARK_LENGTH = 20;

    //刻度线与圆的距离
    private final static int MARK_GAP = 12;

    //时针宽度
    private final static int HOUR_LINE_WIDTH = 10;

    //分针宽度
    private final static int MINUTE_LINE_WIDTH = 6;

    //秒针宽度
    private final static int SECOND_LINE_WIDTH = 4;

    //圆心坐标
    private int centerX;
    private int centerY;

    //圆半径
    private int radius;

    //圆的画笔
    private Paint circlePaint;

    //刻度线画笔
    private Paint markPaint;

    //时针画笔
    private Paint hourPaint;

    //分针画笔
    private Paint minutePaint;

    //秒针画笔
    private Paint secondPaint;

    //时针长度
    private int hourLineLength;

    //分针长度
    private int minuteLineLength;

    //秒针长度
    private int secondLineLength;

    private Bitmap hourBitmap;
    private Bitmap minuteBitmap;
    private Bitmap secondBitmap;

    private Canvas hourCanvas;
    private Canvas minuteCanvas;
    private Canvas secondCanvas;

    //圆的颜色
    private int mCircleColor = Color.WHITE;
    //时针的颜色
    private int mHourColor = Color.BLACK;
    //分针的颜色
    private int mMinuteColor = Color.BLACK;
    //秒针的颜色
    private int mSecondColor = Color.RED;
    //一刻钟刻度线的颜色
    private int mQuarterMarkColor = Color.parseColor("#B5B5B5");
    //分钟刻度线的颜色
    private int mMinuteMarkColor = Color.parseColor("#EBEBEB");
    //是否绘制3个指针的圆心
    private boolean isDrawCenterCircle = false;

    //获取时间监听
    private OnCurrentTimeListener onCurrentTimeListener;

    public void setOnCurrentTimeListener(OnCurrentTimeListener onCurrentTimeListener) {
        this.onCurrentTimeListener = onCurrentTimeListener;
    }

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClockView);
        mCircleColor = a.getColor(R.styleable.ClockView_circle_color, Color.WHITE);
        mHourColor = a.getColor(R.styleable.ClockView_hour_color, Color.BLACK);
        mMinuteColor = a.getColor(R.styleable.ClockView_minute_color, Color.BLACK);
        mSecondColor = a.getColor(R.styleable.ClockView_second_color, Color.RED);
        mQuarterMarkColor = a.getColor(R.styleable.ClockView_quarter_mark_color, Color.parseColor("#B5B5B5"));
        mMinuteMarkColor = a.getColor(R.styleable.ClockView_minute_mark_color, Color.parseColor("#EBEBEB"));
        isDrawCenterCircle = a.getBoolean(R.styleable.ClockView_draw_center_circle, false);
        a.recycle();
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        reMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
//        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
//        centerX = width / 2 + getPaddingLeft();
//        centerY = height / 2 + getPaddingTop();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        centerX = width / 2 ;
        centerY = height / 2;
        radius = Math.min(width, height) / 2;

        hourLineLength = radius / 2;
        minuteLineLength = radius * 3 / 4;
        secondLineLength = radius * 3 / 4;

        //时针
        hourBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        hourCanvas = new Canvas(hourBitmap);

        //分针
        minuteBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        minuteCanvas = new Canvas(minuteBitmap);

        //秒针
        secondBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        secondCanvas = new Canvas(secondBitmap);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆
        canvas.drawCircle(centerX, centerY, radius, circlePaint);
        //绘制刻度线
        for (int i = 0; i < 12; i++) {
            if (i % 3 == 0) {//一刻钟
                markPaint.setColor(mQuarterMarkColor);
            } else {
                markPaint.setColor(mMinuteMarkColor);
            }
            canvas.drawLine(
                    centerX,
                    centerY - radius + MARK_GAP,
                    centerX,
                    centerY - radius + MARK_GAP + MARK_LENGTH,
                    markPaint);
            canvas.rotate(30, centerX, centerY);
        }
        canvas.save();

        Calendar calendar = Calendar.getInstance();
        int hour12 = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        //(方案一)每过一小时(3600秒)时针添加30度，所以每秒时针添加（1/120）度
        //(方案二)每过一小时(60分钟)时针添加30度，所以每分钟时针添加（1/2）度
        hourCanvas.save();
        //清空画布
        hourCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        hourCanvas.rotate(hour12 * 30 + (minute * 0.5f), centerX, centerY);
        hourCanvas.drawLine(centerX, centerY,
                centerX, centerY - hourLineLength, hourPaint);
        if (isDrawCenterCircle)//根据指针的颜色绘制圆心
            hourCanvas.drawCircle(centerX, centerY, 2 * HOUR_LINE_WIDTH, hourPaint);
        hourCanvas.restore();

        //每过一分钟（60秒）分针添加6度，所以每秒分针添加（1/10）度；当minute加1时，正好second是0
        minuteCanvas.save();
        //清空画布
        minuteCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        minuteCanvas.rotate(minute * 6 + (second * 0.1f), centerX, centerY);
        minuteCanvas.drawLine(centerX, centerY,
                centerX, centerY - minuteLineLength, minutePaint);
        if (isDrawCenterCircle)//根据指针的颜色绘制圆心
            minuteCanvas.drawCircle(centerX, centerY, 2 * MINUTE_LINE_WIDTH, minutePaint);
        minuteCanvas.restore();

        //每过一秒旋转6度
        secondCanvas.save();
        //清空画布
        secondCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        secondCanvas.rotate(second * 6, centerX, centerY);
        secondCanvas.drawLine(centerX, centerY,
                centerX, centerY - secondLineLength, secondPaint);
        if (isDrawCenterCircle)//根据指针的颜色绘制圆心
            secondCanvas.drawCircle(centerX, centerY, 2 * SECOND_LINE_WIDTH, secondPaint);
        secondCanvas.restore();

        canvas.drawBitmap(hourBitmap, 0, 0, null);
        canvas.drawBitmap(minuteBitmap, 0, 0, null);
        canvas.drawBitmap(secondBitmap, 0, 0, null);

        //每隔1s重新绘制
        postInvalidateDelayed(1000);

        if (onCurrentTimeListener != null) {
            //小时采用24小时制返回
            int h = calendar.get(Calendar.HOUR_OF_DAY);
            String currentTime = intAdd0(h) + ":" + intAdd0(minute) + ":" + intAdd0(second);
            onCurrentTimeListener.currentTime(currentTime);
        }
    }

    /**
     * 初始化
     */
    private void init() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(mCircleColor);

        markPaint = new Paint();
        circlePaint.setAntiAlias(true);
        markPaint.setStyle(Paint.Style.FILL);
        markPaint.setStrokeCap(Paint.Cap.ROUND);
        markPaint.setStrokeWidth(MARK_WIDTH);

        hourPaint = new Paint();
        hourPaint.setAntiAlias(true);
        hourPaint.setColor(mHourColor);
        hourPaint.setStyle(Paint.Style.FILL);
        hourPaint.setStrokeCap(Paint.Cap.ROUND);
        hourPaint.setStrokeWidth(HOUR_LINE_WIDTH);

        minutePaint = new Paint();
        minutePaint.setAntiAlias(true);
        minutePaint.setColor(mMinuteColor);
        minutePaint.setStyle(Paint.Style.FILL);
        minutePaint.setStrokeCap(Paint.Cap.ROUND);
        minutePaint.setStrokeWidth(MINUTE_LINE_WIDTH);

        secondPaint = new Paint();
        secondPaint.setAntiAlias(true);
        secondPaint.setColor(mSecondColor);
        secondPaint.setStyle(Paint.Style.FILL);
        secondPaint.setStrokeCap(Paint.Cap.ROUND);
        secondPaint.setStrokeWidth(SECOND_LINE_WIDTH);

    }

    /**
     * 重新设置view尺寸
     */
    private void reMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (measureWidthMode == MeasureSpec.AT_MOST
                && measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_SIZE, DEFAULT_SIZE);
        } else if (measureWidthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_SIZE, measureHeight);
        } else if (measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(measureWidth, DEFAULT_SIZE);
        }
    }

    public interface OnCurrentTimeListener {
        void currentTime(String time);
    }

    /**
     * int小于10的添加0
     *
     * @param i
     * @return
     */
    private String intAdd0(int i) {
        DecimalFormat df = new DecimalFormat("00");
        if (i < 10) {
            return df.format(i);
        } else {
            return i + "";
        }
    }
}