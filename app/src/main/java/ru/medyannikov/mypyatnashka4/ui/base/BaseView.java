package ru.medyannikov.mypyatnashka4.ui.base;

import android.content.Context;

public interface BaseView {
  void showLoading();
  void hideLoading();
  void showError(Throwable throwable);
  boolean isReady();
  Context context();
}
