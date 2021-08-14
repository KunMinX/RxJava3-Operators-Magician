package com.kunminx.samples.ui.compose;

import org.reactivestreams.Publisher;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by werockstar on 5/19/2017.
 */

public class RxSchedulers {

  public <T> ObservableTransformer<T, T> applyObservableAsync() {
    return new ObservableTransformer<T, T>() {
      @Override
      public ObservableSource<T> apply(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  public <T> ObservableTransformer<T, T> applyObservableCompute() {
    return new ObservableTransformer<T, T>() {
      @Override
      public ObservableSource<T> apply(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  public <T> ObservableTransformer<T, T> applyObservableMainThread() {
    return new ObservableTransformer<T, T>() {
      @Override
      public ObservableSource<T> apply(Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  public <T> FlowableTransformer<T, T> applyFlowableMainThread() {
    return new FlowableTransformer<T, T>() {
      @Override
      public Publisher<T> apply(Flowable<T> flowable) {
        return flowable.observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  public <T> FlowableTransformer<T, T> applyFlowableAsysnc() {
    return new FlowableTransformer<T, T>() {
      @Override
      public Publisher<T> apply(Flowable<T> flowable) {
        return flowable.observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  public <T> FlowableTransformer<T, T> applyFlowableCompute() {
    return new FlowableTransformer<T, T>() {
      @Override
      public Publisher<T> apply(Flowable<T> flowable) {
        return flowable.observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

}
