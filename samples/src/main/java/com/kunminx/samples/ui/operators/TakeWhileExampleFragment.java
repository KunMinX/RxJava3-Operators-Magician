package com.kunminx.samples.ui.operators;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Predicate;

public class TakeWhileExampleFragment extends TakeOperatorBaseFragment {

  private static final String TAG = TakeWhileExampleFragment.class.getSimpleName();

  @Override
  protected void doSomeWork() {
    getStringObservable()
            //Delay item emission by one second
            .zipWith(Observable.interval(0, 1, TimeUnit.SECONDS), new BiFunction<String, Long, String>() {
              @Override
              public String apply(String s, Long aLong) throws Exception {
                return s;
              }
            })
            //Take the items until the condition is met.
            .takeWhile(new Predicate<String>() {
              @Override
              public boolean test(String s) throws Exception {
                return !s.toLowerCase().contains("honey");
              }
            })
            //We need to observe on MainThread because delay works on background thread to avoid UI blocking.
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver());
  }
}
