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
import com.kunminx.samples.model.ApiUser;
import com.kunminx.samples.model.User;
import com.kunminx.samples.utils.AppConstant;
import com.kunminx.samples.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by amitshekhar on 27/08/16.
 */
public class MapExampleFragment extends Fragment {

  private static final String TAG = MapExampleFragment.class.getSimpleName();
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
   * Here we are getting ApiUser Object from api server
   * then we are converting it into User Object because
   * may be our database support User Not ApiUser Object
   * Here we are using Map Operator to do that
   */
  private void doSomeWork() {
    getObservable()
            // Run on a background thread
            .subscribeOn(Schedulers.io())
            // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .map(Utils::convertApiUserListToUserList)
            .subscribe(getObserver());
  }

  private Observable<List<ApiUser>> getObservable() {
    return Observable.create(e -> {
      if (!e.isDisposed()) {
        e.onNext(Utils.getApiUserList());
        e.onComplete();
      }
    });
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