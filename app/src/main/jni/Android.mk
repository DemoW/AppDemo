LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := jni-test

LOCAL_SRC_FILES := test_android_jni.cpp

include $(BUILD_SHARED_LIBRARY)