#  ClockView
一个自定义的动态时钟View，可根据自己的需求完成各种颜色的搭配，大小设置。

#  效果图

###  GIF演示图
<img src="https://github.com/zhijieeeeee/ClockView/blob/master/screenshot/preview.gif" width = "292" height = "480" alt="效果图" />

###  样式效果演示图
<div>
<img src="https://github.com/zhijieeeeee/ClockView/blob/master/screenshot/pre1.png" width = "270" height = "480" alt="效果图" />
<img src="https://github.com/zhijieeeeee/ClockView/blob/master/screenshot/pre2.png" width = "270" height = "480" alt="效果图" />
<img src="https://github.com/zhijieeeeee/ClockView/blob/master/screenshot/pre3.png" width = "270" height = "480" alt="效果图" />
</div>
#  基本使用

###  1.在gradle中添加依赖
	
	compile 'com.don:clockviewlibrary:1.0.1'

###  2.布局中使用

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
        don:second_color="@android:color/holo_green_light" 
		don:draw_center_circle="true"/>


* circle\_color：时钟的背景颜色（圆的颜色）

* hour\_color：时针的颜色

* minute\_color：分针的颜色

* second\_color：秒针的颜色

* minute\_mark\_color：分钟刻度线的颜色

* quarter\_mark\_color：一刻钟刻度线的颜色

* draw\_center\_circle：是否绘制3个指针的圆心



###  3.代码中可设置时间的监听

	ClockView clockView = (ClockView) findViewById(R.id.clockView);
    clockView.setOnCurrentTimeListener(new ClockView.OnCurrentTimeListener() {
            @Override
            public void currentTime(String time) {
                Log.i("MyLog", time);
            }
        });

#  实现解析

关于项目的实现方式，大家可以参考我的博客[Android自定义控件：时钟](http://blog.csdn.net/u013155862/article/details/53740211)，里面有详细的绘制分析

#  更新日志
###  V1.0.1
* 添加是否绘制中心圆的属性draw\_center\_circle

###  V1.0.0
* 动态时钟View

#  License
        Copyright 2016 zhijieeeeee

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
