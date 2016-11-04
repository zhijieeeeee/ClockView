# ClockView
一个自定义的动态时钟View，可根据自己的需求完成各种颜色的搭配，支持大小设置和padding。
秒针每秒动一次，分针和时针会根据时间进行缓慢的转动。

#效果图

<img src="https://github.com/zhijieeeeee/ClockView/blob/master/screenshot/preview.png" width = "270" height = "480" alt="效果图" />

#基本使用

###1.在gradle中添加依赖
	
	compile 'com.don:clockviewlibrary:1.0.0'

###2.布局中使用

使用默认的样式，其中宽高可根据自己的需求设置，支持wrap\_content，match\_parent，固定尺寸

	<com.don.clockviewlibrary.ClockView
        android:id="@+id/clockView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

自定义样式，根据自己的需求设置各种颜色
	
	<com.don.clockviewlibrary.ClockView
        android:layout_width="150dp"
        android:layout_height="150dp"
        don:circle_color="@android:color/holo_blue_light"
        don:hour_color="#ff00ff"
        don:minute_color="@android:color/holo_red_light"
        don:minute_mark_color="@android:color/white"
        don:quarter_mark_color="@android:color/black"
        don:second_color="@android:color/holo_green_light" />


* circle\_color：时钟的背景颜色（圆的颜色）

* hour\_color：时针的颜色

* minute\_color：分针的颜色

* second\_color：秒针的颜色

* minute\_mark\_color：分钟刻度线的颜色

* quarter\_mark\_color：一刻钟刻度线的颜色



###3.代码中可设置时间的监听

	ClockView clockView = (ClockView) findViewById(R.id.clockView);
    clockView.setOnCurrentTimeListener(new ClockView.OnCurrentTimeListener() {
            @Override
            public void currentTime(String time) {
                Log.i("MyLog", time);
            }
        });

