//
// Created by lishui.lin on 21-2-23.
//

#include <jni.h>
#include <stdio.h>

#ifdef __cplusplus
extern "C" {
#endif

void callJavaMethod(JNIEnv *,jobject);

jstring Java_lishui_example_app_test_JniTest_getNativeStr
(JNIEnv *env, jobject thiz)
{
    printf("invoke getNativeStr in C++\n");
    callJavaMethod(env,thiz);
    return env->NewStringUTF("Hello from JNI with C++");
}

void Java_lishui_example_app_test_JniTest_setNativeStr
(JNIEnv *env, jobject thiz, jstring string)
{
    printf("invoke setNativeStr from C++\n");
    char* str = (char*)env->GetStringUTFChars(string, NULL);
    printf("%s\n", str);
    env->ReleaseStringUTFChars(string, str);
}

void callJavaMethod(JNIEnv *env,jobject thiz)
{
    jclass clazz = env->FindClass("lishui/example/app/test/JniTest");
    if(clazz == NULL){
        printf("find class JniTest error!");
        return;
    }
    jmethodID id = env->GetStaticMethodID(clazz,"invokeByJNI","(Ljava/lang/String;)V");
    if(id == NULL){
       printf("find method invokeByJNI error!");
       return;
    }
    jstring msg = env->NewStringUTF("msg send by callJavaMethod in test_android_jni.cpp");
    env->CallStaticVoidMethod(clazz, id, msg);
}

#ifdef __cplusplus
}
#endif