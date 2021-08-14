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
import com.kunminx.samples.model.User;
import com.kunminx.samples.utils.AppConstant;
import com.kunminx.samples.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by amitshekhar on 27/08/16.
 */
public class ZipExampleFragment extends Fragment {

  private static final String TAG = ZipExampleFragment.class.getSimpleName();
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
   * Here we are getting two user list
   * One, the list of cricket fans
   * Another one, the list of football fans
   * Then we are finding the list of users who loves both
   */
  private void doSomeWork() {
    Observable.zip(getCricketFansObservable(), getFootballFansObservable(),
            new BiFunction<List<User>, List<User>, List<User>>() {
              @Override
              public List<User> apply(List<User> cricketFans, List<User> footballFans) {
                return Utils.filterUserWhoLovesBoth(cricketFans, footballFans);
              }
            })
            // Run on a background thread
            .subscribeOn(Schedulers.io())
            // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver());
  }

  private Observable<List<User>> getCricketFansObservable() {
    return Observable.create((ObservableOnSubscribe<List<User>>) e -> {
      if (!e.isDisposed()) {
        e.onNext(Utils.getUserListWhoLovesCricket());
        e.onComplete();
      }
    }).subscribeOn(Schedulers.io());
  }

  private Observable<List<User>> getFootballFansObservable() {
    return Observable.create(new ObservableOnSubscribe<List<User>>() {
      @Override
      public void subscribe(ObservableEmitter<List<User>> e) {
        if (!e.isDisposed()) {
          e.onNext(Utils.getUserListWhoLovesFootball());
          e.onComplete();
        }
      }
    }).subscribeOn(Schedulers.io());
  }

  private Observer<List<User>> getObserver() {
    return new Observer<List<User>>() {

      @Override
      public void onSubscribe(Disposable d) {
        Log.d(TAG, " onSubscribe : " + d.isDisposed());
      }

      @Override
      public void onNext(List<User> userList) {
        textView.append(" onNext");
        textView.append(AppConstant.LINE_SEPARATOR);
        for (User user : userList) {
          textView.append(" firstname : " + user.firstname);
          textView.append(AppConstant.LINE_SEPARATOR);
        }
        Log.d(TAG, " onNext : " + userList.size());
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