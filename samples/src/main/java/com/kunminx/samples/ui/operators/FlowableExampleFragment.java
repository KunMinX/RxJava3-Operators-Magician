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

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;

/**
 * Created by amitshekhar on 27/08/16.
 */
public class FlowableExampleFragment extends Fragment {

  private static final String TAG = FlowableExampleFragment.class.getSimpleName();
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

  /*
   * simple example using Flowable
   */
  private void doSomeWork() {

    Flowable<Integer> observable = Flowable.just(1, 2, 3, 4);

    observable.reduce(50, (t1, t2) -> t1 + t2).subscribe(getObserver());

  }

  private SingleObserver<Integer> getObserver() {

    return new SingleObserver<Integer>() {
      @Override
      public void onSubscribe(Disposable d) {
        Log.d(TAG, " onSubscribe : " + d.isDisposed());
      }

      @Override
      public void onSuccess(Integer value) {
        textView.append(" onSuccess : value : " + value);
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " onSuccess : value : " + value);
      }

      @Override
      public void onError(Throwable e) {
        textView.append(" onError : " + e.getMessage());
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " onError : " + e.getMessage());
      }
    };
  }
}