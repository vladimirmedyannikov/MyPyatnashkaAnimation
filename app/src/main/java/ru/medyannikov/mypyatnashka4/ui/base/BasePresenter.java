package ru.medyannikov.mypyatnashka4.ui.base;

public class BasePresenter<V extends BaseView> {
  protected V view;

  public void attachView(V view) {
    this.view = view;
  }

  public void detachView() {
    if (view != null) {
      view.hideLoading();
    }
    view = null;
  }

  protected void showError(Throwable throwable) {
    if (view != null && view.isReady()) {
      view.hideLoading();
      view.showError(throwable);
    }
  }

  protected void showProgress() {
    if (view != null && view.isReady()) {
      view.showLoading();
    }
  }

  protected void hideProgress() {
    if (view != null && view.isReady()) {
      view.hideLoading();
    }
  }
}
