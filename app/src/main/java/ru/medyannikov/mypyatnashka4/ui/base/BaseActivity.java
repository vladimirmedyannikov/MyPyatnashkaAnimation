package ru.medyannikov.mypyatnashka4.ui.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

  protected P presenter;
  @Override public void showLoading() {

  }

  @Override public void hideLoading() {

  }

  @Override public void showError(Throwable throwable) {

  }

  @Override public boolean isReady() {
    return true;
  }

  @Override public Context context() {
    return this;
  }
}
