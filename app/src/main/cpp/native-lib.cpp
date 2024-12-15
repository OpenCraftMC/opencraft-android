#include <jni.h>
#include <string>
#include <GLES2/gl2.h>
#include <android/log.h>

#define LOG_TAG "OpenCraft"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

extern "C" {
    JNIEXPORT void JNICALL Java_com_chyves_opencraft_MainActivity_handleTouchEventInCpp(JNIEnv *env, jobject, jfloat x, jfloat y, jint action) {
        LOGD("Touch event: x=%.2f, y=%.2f, action=%d", x, y, action);
    }

    JNIEXPORT void JNICALL Java_com_chyves_opencraft_MainActivity_handleKeyEventInCpp(JNIEnv *env, jobject, jint keyCode, jstring keyChar) {
        const char *keyCharCStr = env->GetStringUTFChars(keyChar, nullptr);

        LOGD("Key event: keyCode=%d, keyChar=%s", keyCode, keyCharCStr);
        env->ReleaseStringUTFChars(keyChar, keyCharCStr);

        // eventually both input handlers will route into main c++ file through event system
    }

    JNIEXPORT void JNICALL Java_com_chyves_opencraft_MainActivity_nativeInitOpenGL(JNIEnv *env, jobject obj) {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    JNIEXPORT void JNICALL Java_com_chyves_opencraft_MainActivity_nativeRender(JNIEnv *env, jobject obj) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // both renderers will eventually will also route into main c++
    }

    JNIEXPORT void JNICALL Java_com_chyves_opencraft_MainActivity_nativeCleanUp(JNIEnv *env, jobject obj) {
        // empty for now, free mem
    }
}