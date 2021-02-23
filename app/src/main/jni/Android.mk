LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
# 生成so的文件名称
LOCAL_MODULE := jni-test
LOCAL_SRC_FILES := test_jni.cpp
include $(BUILD_SHARED_LIBRARY)