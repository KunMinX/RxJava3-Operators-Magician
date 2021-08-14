package com.kunminx.rxmagic.log;
/*
 * Copyright (c) 2018-2019. KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * Create by KunMinX at 19/4/30
 */
public class CrashHandler {

  private static final String TAG = CrashHandler.class.getSimpleName();

  public static void init(final Context context) {

    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread thread, Throwable th) {
        String sb = ("\nModel:" + Build.MODEL) +
                "\nSystemVersion:" + Build.VERSION.RELEASE +
                "\nCPU arch:" + Build.CPU_ABI +
                "\nVersionCode:" + getVersionCode(context) +
                "\nVersionName:" + getVersionName(context) + "\n";
        Logger.t(TAG).e(sb + Log.getStackTraceString(th));
        System.exit(1);
        throw new RuntimeException(th);
      }
    });
  }

  private static int getVersionCode(Context context) {
    try {
      PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      return pi.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
    }
    return -1;
  }

  private static String getVersionName(Context context) {
    try {
      PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      return pi.versionName;
    } catch (PackageManager.NameNotFoundException e) {
    }
    return null;
  }
}
