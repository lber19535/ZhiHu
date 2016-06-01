# ZhiHu   [![Build Status](https://travis-ci.org/lber19535/ZhiHu.svg?branch=master)](https://travis-ci.org/lber19535/ZhiHu)
一个简单的知乎app， 支持Android 4.0以上，这个项目算是日常工作学习的积累。

最开始的几个版本是模拟浏览器行为做的，但是由于网页经常变动，所以使用 jsoup 解析的时候没办法很好的解析出想要的内容，现在直接使用官方的接口。
整个设计尽量使用 MD 的设计规范，由于我没什么设计细胞，只能将界面做到不难看。

## 截图
虚拟机录得 gif，比较卡

 <img src="http://7xisp0.com1.z0.glb.clouddn.com/zhihu_start.gif" width="351.4" height="589.4">  <img src="http://7xisp0.com1.z0.glb.clouddn.com/zhihu_home.gif" width="351.4" height="589.4">  <img src="http://7xisp0.com1.z0.glb.clouddn.com/zhihu_answer.gif" width="351.4" height="589.4">



## API
api 部分做成了单独的 module，貌似这个有版权问题，这里就不写分析了，源码部分比较简单，想用的可以直接拿这个 module 去用，需要注意下混淆和一些组件的初始化问题
