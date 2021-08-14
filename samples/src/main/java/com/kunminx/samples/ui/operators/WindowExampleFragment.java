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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WindowExampleFragment extends Fragment {

  private static final String TAG = WindowExampleFragment.class.getSimpleName();
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
   * Example using window operator -> It periodically
   * subdivide items from an Observable into
   * Observable windows and emit these windows rather than
   * emitting the items one at a time
   */
  protected void doSomeWork() {

    Observable.interval(1, TimeUnit.SECONDS).take(12)
            .window(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getConsumer());
  }

  public Consumer<Observable<Long>> getConsumer() {
    return observable -> {
      Log.d(TAG, "Sub Divide begin....");
      textView.append("Sub Divide begin ....");
      textView.append(AppConstant.LINE_SEPARATOR);
      observable
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(value -> {
                Log.d(TAG, "Next:" + value);
                textView.append("Next:" + value);
                textView.append(AppConstant.LINE_SEPARATOR);
              });
    };
  }
}
