# 组件模块
```
lib_utils:基本工具类
lib_internet：网络请求模块
lib_logger：日志模块
lib_widget：自定义view模块
lib_common,lib_wheelpicker：年月日选择模块

app：app壳模块
module_login：登录模块
module_main：首页模块
module_itext：pdf生成查看模块
```


# 要点：
## 一 gradle新增和特殊的有以下几个文件：
1.gradle.properties   :位于项目根目录，通过更改gradle.properties下的isModule来更改开发模式为集成式开发还是组件化开发

                       isModule为false，即集成式开发，整体模块一起打包运行，并通过app壳进行访问
                       
                       isModule为true，组件式开发，所有module级别可以单独打包运行开发
                       
2.config.gradle       :位于项目根目录，项目中设计到全部官方库和三方库全部在此定义，并在其他gradle中引用，只在gradle之间使用
3.module.build.gradle :位于项目根目录，项目的module级别(即可独立运行的module)添加引用，定义了module特有的一些设置
4.lib-build.gradle    :位于项目根目录，所有lib级别(即不能独立运行，只能作为依赖)需要使用的引用在里面进行添加引用(感觉会引用一些用不到的，待优化)
需要注意的是ProjectA在dependencies中添加ProjectB的依赖，并不能使用ProjectB的Gradle中配置，必须单独在自己的Gradle文件中写，
或使用类似：apply from: "../module.build.gradle"这样，引用专门的gradle配置文件

## 二 通讯模块使用的ARouter
gradle中添加
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName()]
            }
        }
并在Application中初始化
```
    /**
     * 初始化路由
     * **/
    private void initRouter() {
        //这两行必须写在init之前，否则这些配置在init过程中无效
        if (BuildConfig.DEBUG) {//第三方工具判断是否是Debug 或者BuildConfig.DEBUG
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险）
            ARouter.openDebug();

        }
        ARouter.init(this);
    }
```
**需要注意：每个需要使用Arouter功能Module都需要重新在build.gradle的dependencies重新配置一下依赖，不然会找不到，原因未知**

# 模块介绍

## 年月日弹框选择模块
示例：
```

    public static DateTimePicker produce(Activity activity) {
        DateTimePicker dateTimePicker = new DatePicker(activity);
        //时间选择框开始结束范围
        dateTimePicker.setDateRangeStart(CalendarUtils.getCurentYear(), CalendarUtils.getCurentMonth(), 1);
        dateTimePicker.setDateRangeEnd(CalendarUtils.getCurentYear() + 50, 12, 31);
        dateTimePicker.setTopLineColor(Color.argb(255, 153, 153, 153));
        dateTimePicker.setLabelTextColor(Color.argb(255, 102, 102, 102));
        dateTimePicker.setDividerColor(Color.argb(255, 153, 153, 153));
        dateTimePicker.setTextColor(Color.argb(255, 102, 102, 102));
        dateTimePicker.setCancelTextColor(Color.argb(255, 102, 102, 102));
        dateTimePicker.setSubmitTextColor(Color.argb(255, 102, 102, 102));
        dateTimePicker.setSelectedItem(CalendarUtils.getCurentYear(), CalendarUtils.getCurentMonth(), CalendarUtils.getCurentDay(), 0, 0);
        dateTimePicker.setCancelText("取消");
        dateTimePicker.setSubmitText("确定");
        return dateTimePicker;
    }
```


# 踩坑
### 1.当项目中一直报错找不到Databinding中的BR文件时
检查Gradle中是否添加了以下配置
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-parcelize'
apply plugin: 'kotlin-kapt'

### 2.安装apk一直提示找不到SplashActivity
检查apk中的classes.dex等文件中发现Activity等文件(给module_login进行打包，任一文件都不在apk内)全部未打包进去
尝试方案：更换Gradle和Gradle插件版本
        清除项目依赖缓存，并重启
        删除idea和.gradle并重启
编译器能正常编译其他项目，说明编译器正常
猜测是否项目依赖关系混乱导致
尝试方案：
1.新建Java类型TestActivity并重新安装，可正常打开该类，检查apk内，发现整个module_login下只有该类打包进classes.dex等文件
2.新建Kotlin类型MainActivity并重新安装，开始提示原本就应该提示一些module中编译期间就应该提示的错误，如引用爆红等，
解决提示的问题，并重新安装，检查classes.dex等文件，发现module_login下的文件终于全部打包进去，至此问题解决，原因未知，特此记录。
