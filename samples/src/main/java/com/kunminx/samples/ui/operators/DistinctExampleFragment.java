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

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by techteam on 13/09/16.
 */
public class DistinctExampleFragment extends Fragment {

  private static final String TAG = DistinctExampleFragment.class.getSimpleName();
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
   * distinct() suppresses duplicate items emitted by the source Observable.
   */
  private void doSomeWork() {

    getObservable()
            .distinct()
            .subscribe(getObserver());
  }

  private Observable<Integer> getObservable() {
    return Observable.just(1, 2, 1, 1, 2, 3, 4, 6, 4);
  }


  private Observer<Integer> getObserver() {
    return new Observer<Integer>() {

      @Override
      public void onSubscribe(Disposable d) {
        Log.d(TAG, " onSubscribe : " + d.isDisposed());
      }

      @Override
      public void onNext(Integer value) {
        textView.append(" onNext : value : " + value);
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " onNext value : " + value);
      }

      @Override
      public void onError(Throwable e) {
        Log.d(TAG, " onError : " + e.getMessage());
      }

      @Override
      public void onComplete() {
        Log.d(TAG, " onComplete");
      }
    };
  }
}