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
 * Created by amitshekhar on 28/08/16.
 */
public class MergeExampleFragment extends Fragment {

  private static final String TAG = MergeExampleFragment.class.getSimpleName();
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
   * Using merge operator to combine Observable : merge does not maintain
   * the order of Observable.
   * It will emit all the 7 values may not be in order
   * Ex - "A1", "B1", "A2", "A3", "A4", "B2", "B3" - may be anything
   */
  private void doSomeWork() {
    final String[] aStrings = {"A1", "A2", "A3", "A4"};
    final String[] bStrings = {"B1", "B2", "B3"};

    final Observable<String> aObservable = Observable.fromArray(aStrings);
    final Observable<String> bObservable = Observable.fromArray(bStrings);

    Observable.merge(aObservable, bObservable)
            .subscribe(getObserver());
  }


  private Observer<String> getObserver() {
    return new Observer<String>() {

      @Override
      public void onSubscribe(Disposable d) {
        Log.d(TAG, " onSubscribe : " + d.isDisposed());
      }

      @Override
      public void onNext(String value) {
        textView.append(" onNext : value : " + value);
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG, " onNext : value : " + value);
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