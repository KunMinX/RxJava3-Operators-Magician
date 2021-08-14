package com.kunminx.samples.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kunminx.samples.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by amitshekhar on 15/10/17.
 */

public class SearchFragment extends Fragment {

  public static final String TAG = SearchFragment.class.getSimpleName();
  private SearchView searchView;
  private TextView textViewResult;

  @Nullable
  @Override
  public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_search, container, false);
  }

  @Override
  public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    searchView = view.findViewById(R.id.searchView);
    textViewResult = view.findViewById(R.id.textViewResult);

    setUpSearchObservable();
  }

  private void setUpSearchObservable() {
    RxSearchObservable.fromView(searchView)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter(new Predicate<String>() {
              @Override
              public boolean test(String text) {
                if (text.isEmpty()) {
//                            textViewResult.setText("");
                  return false;
                } else {
                  return true;
                }
              }
            })
            .distinctUntilChanged()
            .switchMap((Function<String, ObservableSource<String>>) this::dataFromNetwork)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(result -> textViewResult.setText(result));
  }

  /**
   * Simulation of network data
   */
  private Observable<String> dataFromNetwork(final String query) {
    return Observable.just(true)
            .delay(2, TimeUnit.SECONDS)
            .map(value -> query);
  }

}
