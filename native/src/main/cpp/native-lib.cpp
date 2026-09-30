#include <jni.h>

// M1 stub: version string only. Full native bridge lands with M3/M5.
extern "C" JNIEXPORT jstring JNICALL
Java_com_opencode_nativelib_NativeBridge_version(JNIEnv* env, jobject /* thiz */) {
  return env->NewStringUTF("m1-stub");
}
