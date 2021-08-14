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

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.AsyncSubject;

/**
 * Created by amitshekhar on 17/12/16.
 */

public class AsyncSubjectExampleFragment extends Fragment {

  private static final String TAG = AsyncSubjectExampleFragment.class.getSimpleName();
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

    btn.setOnClickListener(view1 -> doSomeWork());
  }

  /* An AsyncSubject emits the last value (and only the last value) emitted by the source
   * Observable, and only after that source Observable completes. (If the source Observable
   * does not emit any values, the AsyncSubject also completes without emitting any values.)
   */
  private void doSomeWork() {

    AsyncSubject<Integer> source = AsyncSubject.create();

    source.subscribe(getFirstObserver()); // it will emit only 4 and onComplete

    source.onNext(1);
    source.onNext(2);
    source.onNext(3);

    /*
     * it will emit 4 and onComplete for second observer also.
     */
    source.subscribe(getSecondObserver());

    source.onNext(4);
    source.onComplete();

  }


  private Observer<Integer> getFirstObserver() {
    return new Observer<Integer>() {

      @Override
      public void onSubscribe(Disposable d) {
        Log.d(TAG, " First onSubscribe : " + d.isDisposed());
      }

      @Override
      public void onNext(Integer value) {
        textView.append(" First onNext : value : " + value);
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " First onNext value : " + value);
      }

      @Override
      public void onError(Throwable e) {
        textView.append(" First onError : " + e.getMessage());
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " First onError : " + e.getMessage());
      }

      @Override
      public void onComplete() {
        textView.append(" First onComplete");
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " First onComplete");
      }
    };
  }

  private Observer<Integer> getSecondObserver() {
    return new Observer<Integer>() {

      @Override
      public void onSubscribe(Disposable d) {
        textView.append(" Second onSubscribe : isDisposed :" + d.isDisposed());
        Log.d(TAG, " Second onSubscribe : " + d.isDisposed());
        textView.append(AppConstant.LINE_SEPARATOR);
      }

      @Override
      public void onNext(Integer value) {
        textView.append(" Second onNext : value : " + value);
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " Second onNext value : " + value);
      }

      @Override
      public void onError(Throwable e) {
        textView.append(" Second onError : " + e.getMessage());
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " Second onError : " + e.getMessage());
      }

      @Override
      public void onComplete() {
        textView.append(" Second onComplete");
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " Second onComplete");
      }
    };
  }


}