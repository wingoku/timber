package com.example.timber;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import timber.log.Timber;

import static timber.log.Timber.DebugTree;

public class ExampleApp extends Application {
  Timber.FileLoggingTree mTree;


  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new DebugTree(""));
      Timber.plant(new DebugTree("specialDebugTree"));
      ArrayList<Integer> list= new ArrayList<>();
      list.add(Log.ERROR);
      list.add(Log.DEBUG);
      mTree = new Timber.FileLoggingTree("fileTree", "timberFile", "/sdcard/picavi", ""/*, (ArrayList<Integer>) Arrays.asList(Log.ERROR, Log.DEBUG)*/);
      Timber.plant(mTree);
    } else {
      Timber.plant(new CrashReportingTree(""));
    }

    Timber.i("specialDebugTree", "This message should go in specialDebugTree");
    Timber.i("fileTree", "This message should go in File Logging Tree");
  }

  /** A tree which logs important information for crash reporting. */
  private static class CrashReportingTree extends Timber.Tree {
    public CrashReportingTree(String tName) {
      super(tName);
    }

    @Override protected void log(int priority, String tag, @NonNull String message, Throwable t) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return;
      }

      FakeCrashLibrary.log(priority, tag, message);

      if (t != null) {
        if (priority == Log.ERROR) {
          FakeCrashLibrary.logError(t);
        } else if (priority == Log.WARN) {
          FakeCrashLibrary.logWarning(t);
        }
      }
    }
  }
}
