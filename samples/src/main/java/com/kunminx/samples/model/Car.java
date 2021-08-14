package com.kunminx.samples.model;


import io.reactivex.rxjava3.core.Observable;

/**
 * Created by amitshekhar on 30/08/16.
 */
public class Car {

  private String brand;

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public Observable<String> brandDeferObservable() {
    return Observable.defer(() -> Observable.just(brand));
  }

}
