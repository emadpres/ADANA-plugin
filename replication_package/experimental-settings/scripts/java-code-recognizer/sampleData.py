def load_sample_data():
    sample_java_code = """final View activityRootView = findViewById(R.id.activityRoot);
                                activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                                        if (heightDiff > dpToPx(getApplicationContext(), 200)) { // if more than 200 dp, it's probably a keyboard...
                                            // ... do something here
                                        }
                                    }
                                });"""
    sample_cpp_code = """#include <stdio.h>
                        JNIEXPORT jstring JNICALL
                        Java_com_example_hellojni_HelloJni_stringFromJNI( JNIEnv* env, jobject thiz )
                        {
                            Py_Initialize();
                            PyObject *pModule = PyImport_AddModule("__main__");
                            PyRun_SimpleString("import sys");
                            PyRun_SimpleString("import time");
                            PyRun_SimpleString("sys.path.append(\"/sdcard/Download\")"); //self-written python module put here.
                            PyRun_SimpleString("1+1");
                            PyRun_SimpleString("time.sleep(60)");
                            Py_Finalize();

                            return (*env)->NewStringUTF(env, "Hello from JNI !  Compiled with ABI " ABI ".");
                        }"""

    sample_xml = """<?xml version="1.0" encoding="utf-8"?>
                    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                        xmlns:app="http://schemas.android.com/apk/res-auto"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent">

                        <ScrollView
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content">

                            <LinearLayout
                                android:layout_width="match_parent"
                                android:layout_height="wrap_content"
                                android:orientation="vertical">

                              ............... my views 

                            </LinearLayout>
                        </ScrollView>
                    </RelativeLayout>"""
    sample_mk = """LOCAL_PATH := $(call my-dir)
                    include $(CLEAR_VARS)
                    APP_ABI := armeabi
                    NDK_TOOLCHAIN_VERSION := clang3.4-obfuscator
                    include $(BUILD_EXECUTABLE)"""

    return (sample_java_code, sample_cpp_code, sample_xml, sample_mk)
