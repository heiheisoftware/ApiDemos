/*
 * demo.c
 *
 *  Created on: 2014-11-4
 *      Author: linqing
 */
/**
 * 签名校验
 */
jboolean isVaild(JNIEnv* env, jobject thizz, jobject thiz) {
    //LOGD("enter isVaild");
    jclass native_clazz = (*env)->GetObjectClass(env, thiz);
    jmethodID methodID_func = (*env)->GetMethodID(env, native_clazz,"getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject package_manager = (*env)->CallObjectMethod(env, thiz, methodID_func);
    jclass pm_clazz = (*env)->GetObjectClass(env, package_manager);
    jmethodID methodID_pm = (*env)->GetMethodID(env, pm_clazz, "getPackageInfo", "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jmethodID methodID_packagename = (*env)->GetMethodID(env, native_clazz,"getPackageName", "()Ljava/lang/String;");
    jstring name_str = ((*env)->CallObjectMethod(env, thiz, methodID_packagename));
    jobject package_info = (*env)->CallObjectMethod(env, package_manager, methodID_pm, name_str, 64);
    jclass pi_clazz = (*env)->GetObjectClass(env, package_info);
    jfieldID fieldID_signatures = (*env)->GetFieldID(env, pi_clazz, "signatures", "[Landroid/content/pm/Signature;");
    jobjectArray signatures = (*env)->GetObjectField(env, package_info, fieldID_signatures);
    jobject signature = (*env)->GetObjectArrayElement(env, signatures, 0);
    jclass s_clazz = (*env)->GetObjectClass(env, signature);
    jmethodID methodID_hc = (*env)->GetMethodID(env, s_clazz, "toByteArray", "()[B");
    jbyteArray sign = (*env)->CallObjectMethod(env, signature, methodID_hc);
    char* b = (*env)->GetByteArrayElements(env, sign, NULL);
    jstring sigStr = byteArrayToHEXJString(env, b, strlen(b));
    (*env)->ReleaseByteArrayElements(env, sign, b, 0);
    const char *nativeString = (*env)->GetStringUTFChars(env, sigStr, NULL);
    //LOG_DEBUG(c_TAG, nativeString);
    //LOG_DEBUG(c_TAG, key4);
    int length1 = strlen(key4);
    int length2 = strlen(nativeString);
    int i = 0;
    for (; i < length1 && i < length2; i ++)
        if (nativeString[i] != key4[i])
        {
            //LOGD("leave isVaild");
            return 0;
        }
    (*env)->ReleaseStringUTFChars(env, sigStr, nativeString);
    //LOGD("leave isVaild");
    return 1;
}
