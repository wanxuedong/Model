组件模块

lib_utils:基本工具类

lib_internet：网络请求模块

lib_logger：日志模块

lib_widget：自定义view模块

lib_common,lib_wheelpicker：年月日选择模块

app：app壳模块

module_login：登录模块

module_main：首页模块

module_itext：pdf生成查看模块


要点：
一：
通过更改gradle.properties下的isModule来更改开发模式为集成式开发还是组件化开发
集成式开发：整体模块一起打包运行，并通过app壳进行访问
组件式开发：所有应用组件可以单独打包运行开发

二：
通讯模块使用的ARouter
gradle中添加
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName()]
            }
        }
并在Application中初始化
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
需要注意：每个需要使用Arouter功能Module都需要重新在build.gradle的dependencies重新配置一下依赖，不然会找不到，原因未知


年月日弹框选择模块
示例：

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