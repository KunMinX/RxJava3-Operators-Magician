package com.kunminx.samples.ui.cache.source;

import com.kunminx.samples.ui.cache.model.Data;

import io.reactivex.rxjava3.core.Observable;


/**
 * Class to simulate Network DataSource
 */
public class NetworkDataSource {

  public Observable<Data> getData() {
    return Observable.create(emitter -> {
      Data data = new Data();
      data.source = "network";
      emitter.onNext(data);
      emitter.onComplete();
    });
  }

}
