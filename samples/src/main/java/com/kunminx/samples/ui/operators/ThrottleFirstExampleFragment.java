package com.kunminx.samples.ui.operators;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kunminx.samples.R;
import com.kunminx.samples.utils.AppConstant;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * Created by threshold on 2017/1/11.
 */

public class ThrottleFirstExampleFragment extends Fragment {

  private static final String TAG = ThrottleFirstExampleFragment.class.getSimpleName();
  Button btn;
  TextView textView;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_example, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    btn = view.findViewById(R.id.btn);
    textView = view.findViewById(R.id.textView);

    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        doSomeWork();
      }
    });
  }

  /*
   * Using throttleFirst() -> if the source Observable has emitted no items since
   * the last time it was sampled, the Observable that results from this operator
   * will emit no item for that sampling period.
   */
  private void doSomeWork() {
    getObservable()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            // Run on a background thread
            .subscribeOn(Schedulers.io())
            // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver());
  }

  private Observable<Integer> getObservable() {
    return Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        // send events with simulated time wait
        Thread.sleep(0);
        emitter.onNext(1); // deliver
        emitter.onNext(2); // skip
        Thread.sleep(505);
        emitter.onNext(3); // deliver
        Thread.sleep(99);
        emitter.onNext(4); // skip
        Thread.sleep(100);
        emitter.onNext(5); // skip
        emitter.onNext(6); // skip
        Thread.sleep(305);
        emitter.onNext(7); // deliver
        Thread.sleep(510);
        emitter.onComplete();
      }
    });
  }

  private Observer<Integer> getObserver() {
    return new Observer<Integer>() {

      @Override
      public void onSubscribe(Disposable d) {
        Log.d(TAG, " onSubscribe : " + d.isDisposed());
      }

      @Override
      public void onNext(Integer value) {
        textView.append(" onNext : ");
        textView.append(AppConstant.LINE_SEPARATOR);
        textView.append(" value : " + value);
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " onNext ");
        Log.d(TAG, " value : " + value);
      }

      @Override
      public void onError(Throwable e) {
        textView.append(" onError : " + e.getMessage());
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " onError : " + e.getMessage());
      }

      @Override
      public void onComplete() {
        textView.append(" onComplete");
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " onComplete");
      }
    };
  }

}