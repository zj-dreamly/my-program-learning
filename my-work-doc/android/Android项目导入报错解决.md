在build.gradle文件中追加：

```xml
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.5.3'
        //注意：更换成自己的AS的版本
    }
}
```

