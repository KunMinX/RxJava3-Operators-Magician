package com.kunminx.samples.ui.rxbus;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * Created by amitshekhar on 06/02/17.
 */

public class RxBus {

  public RxBus() {
  }

  private PublishSubject<Object> bus = PublishSubject.create();

  public void send(Object o) {
    bus.onNext(o);
  }

  public Observable<Object> toObservable() {
    return bus;
  }

  public boolean hasObservers() {
    return bus.hasObservers();
  }
}
