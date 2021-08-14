package com.kunminx.samples.ui.cache.source;

import com.kunminx.samples.ui.cache.model.Data;

import io.reactivex.rxjava3.core.Observable;

/**
 * Class to simulate Disk DataSource
 */
public class DiskDataSource {

  private Data data;

  public Observable<Data> getData() {
    return Observable.create(emitter -> {
      if (data != null) {
        emitter.onNext(data);
      }
      emitter.onComplete();
    });
  }

  public void saveToDisk(Data data) {
    this.data = data.clone();
    this.data.source = "disk";
  }

}
