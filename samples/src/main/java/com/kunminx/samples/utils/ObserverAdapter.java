package com.kunminx.samples.utils;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Adapter class for Observer to get the method only needed by the user. Instead of overriding each and every method.
 *
 * @param <T>
 */
public abstract class ObserverAdapter<T> implements Observer<T> {
  @Override
  public void onSubscribe(Disposable d) {

  }

  @Override
  public void onNext(T s) {

  }

  @Override
  public void onError(Throwable e) {

  }

  @Override
  public void onComplete() {

  }
}
