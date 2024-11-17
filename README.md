这是我在写项目时的一部分经历，有部分知识点与问题，还有很多问题与修复过程忘记写了。

[‍‌‍‍‍‌‌‍⁠‍‌‌‬﻿⁠﻿‍‌‍‌‌⁠‌‬‌﻿‌﻿⁠﻿qq音乐 - 飞书云文档](https://baqrz3fz2zy.feishu.cn/docx/BRdpdoJlpoE7GBxkh9KcdmoEnZe)



# 欢迎页面：

![image-20241117013851745](https://gitee.com/wind-know/mypic/raw/master/img/image-20241117013851745.png)

这里直接截图截的qq音乐做背景，

布局使用了ConstraintLayout布局，配上了Guideline来控制位置，同时获取了一些需要使用的权限，设置了页面的沉浸式，这里的fakeqq音乐四个字会慢慢出现

# 首页+社区界面：

![image-20241117014259847](https://gitee.com/wind-know/mypic/raw/master/img/image-20241117014259847.png)



## 布局：

首页是一个activity中的一个fragment，这个fragment又嵌套了viewpager2与tablayout的联动

## 技术：

但是因为qq音乐原版很男泵的重复页面，所以我这里的fragment直接复用的之后的页面

1. 这个页面的顶部是AppBarLayout+CollapsingToolbarLayout，在上滑时会消失，下滑会出现，但是不太灵敏
2. 剩下的几个是activity，尝试解决了横向的滑动冲突，使用了一个布局来包裹，并设置了事件拦截，使这片区域的操作全部被拦截，不会触发viewpager2的滑动。
3. 底部的播放器是window窗口

# 直播:

![image-20241117015620073](https://gitee.com/wind-know/mypic/raw/master/img/image-20241117015620073.png)zhe

这个界面没有什么东西，因为对于原版来说，也就是recycleview的网格布局（甚至不是瀑布流），不过能动的封面倒是有点意思，虽然没写，但是之后可以学习一下。

## 布局：

没什么东西，viewpager2嵌套viewpager2嵌套fragment滚动视图

## 技术：

没有什么，还是一个解决了一下滑动冲突的问题

# 我的：

![image-20241117020046038](https://gitee.com/wind-know/mypic/raw/master/img/image-20241117020046038.png)



## 布局：

最上方个人栏是卡片布局，内部是约束布局，放了一些图片图标，使用guide来约束位置，底下是一个横向recycleview，再底下是一个tablayout联动viewpager2，但是禁用了滑动操作，增加了一个点击展开点击隐藏的功能。

## 技术：

最上方recycleview的播放图标，使用 Palette 提取主色调，并设置到上方，会随着图标变化

设置了点击出现和点击隐藏的功能，传入的list先隐藏部分，点击之后显现出来，再点击设置隐藏

![image-20241117020714926](https://gitee.com/wind-know/mypic/raw/master/img/image-20241117020714926.png)

![image-20241117020723524](https://gitee.com/wind-know/mypic/raw/master/img/image-20241117020723524.png)

# 播放：

![image-20241117160550774](https://gitee.com/wind-know/mypic/raw/master/img/image-20241117160550774.png)

这个界面是播放的界面

## 布局：

这个的布局比较复杂，整体是一个自定义的BackgourndAnimationRelativeLayout。唱盘区域包括唱盘、唱针、底盘、以及实现切换的ViewPager等控件。时长显示区域使用RelativeLayout作为根布局，进度条使用SeekBar实现。

## 技术：

1. 一些动画效果如转盘转动，唱针的动画
2. viewpager与服务的联动效果
3. 加载网络图片与唱盘合成一张图片
4. 背景白色蒙版使图标清晰，随歌曲切换背景图



# 播放效果以及其他实现：

![image-20241117021830569](https://gitee.com/wind-know/mypic/raw/master/img/image-20241117021830569.png)



这是歌单列表，点击之后会启动对应的歌曲界面





![image-20241117021840091](https://gitee.com/wind-know/mypic/raw/master/img/image-20241117021840091.png)

底部的window会跟随当前播放的歌曲来变化，变化颜色，图片等，颜色是提取的主色调



![image-20241117160830304](https://gitee.com/wind-know/mypic/raw/master/img/image-20241117160830304.png)

点击歌单（横向的大图）

![image-20241117160841282](https://gitee.com/wind-know/mypic/raw/master/img/image-20241117160841282.png)

进入歌曲列表白色蒙版覆盖在图片上面，使得内容比较清楚

随着上滑内容会渐变隐藏，标题栏会逐渐出现

![image-20241117161117757](https://gitee.com/wind-know/mypic/raw/master/img/image-20241117161117757.png)

唱盘界面的动画，随着音乐的播放与暂停，唱针会进行动画接近唱盘或远离