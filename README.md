# AndroidUtils
个人Android开发汇集工具类



# gradle 配置案例

### moudle的build.gradle
```
apply plugin: 'com.android.application'


android {

    //渠道名称 TODO 3.0以后改版了
//    productFlavors {
//        develop {}
//        xiaomi {}
//        huawei {}
//        anzhi {}
//    }
    //修改生成的apk名称
    android.applicationVariants.all { variant ->
        variant.outputs.all { output ->

            def outputFile = output.outputFile
            if (outputFile != null && outputFile.name.endsWith('.apk')) {
                def fileName = outputFile.name.replace(".apk", "-${defaultConfig.versionName}.apk")
                outputFileName = fileName
            }
        }
    }
    //新建 config.gradle 文件方式:
    compileSdkVersion rootProject.ext.android.compileSdkVersion

    defaultConfig {
        //配置 moudle 的 build.gradle 方式,直接定义变量名声明
        final Version_Code = '1'
        final Version_Name = '1.0'
        versionCode Version_Code as int
        versionName Version_Name
        //配置 gradle.properties 方式
        applicationId ANDROID_APPLICATION_ID
        minSdkVersion ANDROID_MIN_SDK_VERSION as int
        targetSdkVersion ANDROID_TARGET_SDK_VERSION as int
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}




dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    androidTestImplementation('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    implementation rootProject.ext.dependencies["support-v7"]
    testImplementation 'junit:junit:4.12'
    //配置 app 的 build.gradle 方式
    implementation "com.android.support:cardview-v7:$rootProject.supportLibraryVersion"
    implementation "com.android.support:appcompat-v7:$rootProject.supportLibraryVersion"
    implementation "com.android.support:design:$rootProject.supportLibraryVersion"
}


```

### app的build.gradle

```
// Top-level build file where you can add configuration options common to all sub-projects/modules.
apply from: "config.gradle"

buildscript {
    repositories {
        jcenter()

    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.2.1'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()
        maven { url 'https://maven.google.com' }
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

// Define versions in a single place
ext {

    // SDK And Tools
    minSdkVersion = 14
    targetSdkVersion = 23
    compileSdkVersion = 23
    buildToolsVersion = '24.0.2'

    //Dependencies
    supportLibraryVersion = '25.3.1'
}

```

### 自定义config.gradle

```
ext {
    android = [compileSdkVersion: 26,
               buildToolsVersion: "26.0.2",
               minSdkVersion    : 15,
               targetSdkVersion : 25,
               versionCode      : 1,
               versionName      : "1.0"]


    dependencies = ["support-v7"      : 'com.android.support:appcompat-v7:25.3.1',
                    "design"          : 'com.android.support:design:25.3.1',
                    "rx_android"      : 'io.reactivex:rxandroid:1.0.1',
                    "rx_binding"      : 'com.jakewharton.rxbinding:rxbinding:0.2.0',
                    "retrofit"        : 'com.squareup.retrofit2:retrofit:2.0.0-beta3',
                    "retrofit_adapter": 'com.squareup.retrofit2:adapter-rxjava:2.0.0-beta3']
}
```