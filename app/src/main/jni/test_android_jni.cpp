//
// Created by lishui.lin on 21-2-23.
//

#include <jni.h>
#include <stdio.h>

#ifdef __cplusplus
extern "C" {
#endif

jstring Java_lishui_example_app_test_JniTest_getNativeStr
(JNIEnv *env, jobject thiz)
{
    printf("invoke getNativeStr in C++\n");
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

#ifdef __cplusplus
}
#endif