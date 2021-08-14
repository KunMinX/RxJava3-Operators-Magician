package com.kunminx.samples.ui.pagination;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.samples.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by amitshekhar on 15/03/17.
 */

public class PaginationFragment extends Fragment {

  public static final String TAG = PaginationFragment.class.getSimpleName();
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private PublishProcessor<Integer> paginator = PublishProcessor.create();
  private PaginationAdapter paginationAdapter;
  private RecyclerView recyclerView;
  private ProgressBar progressBar;
  private boolean loading = false;
  private int pageNumber = 1;
  private final int VISIBLE_THRESHOLD = 1;
  private int lastVisibleItem, totalItemCount;
  private LinearLayoutManager layoutManager;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_pagination, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerView = view.findViewById(R.id.recyclerView);
    progressBar = view.findViewById(R.id.progressBar);
    layoutManager = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(RecyclerView.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);
    paginationAdapter = new PaginationAdapter();
    recyclerView.setAdapter(paginationAdapter);
    setUpLoadMoreListener();
    subscribeForData();
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
    compositeDisposable.clear();
  }

  /**
   * setting listener to get callback for load more
   */
  private void setUpLoadMoreListener() {
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView,
                             int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        totalItemCount = layoutManager.getItemCount();
        lastVisibleItem = layoutManager
                .findLastVisibleItemPosition();
        if (!loading
                && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
          pageNumber++;
          paginator.onNext(pageNumber);
          loading = true;
        }
      }
    });
  }

  /**
   * subscribing for data
   */
  private void subscribeForData() {

    Disposable disposable = paginator
            .onBackpressureDrop()
            .doOnNext(page -> {
              loading = true;
              progressBar.setVisibility(View.VISIBLE);
            })
            .concatMapSingle(page -> dataFromNetwork(page)
                    .subscribeOn(Schedulers.io())
                    .doOnError(throwable -> {
                      // handle error
                    }))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(items -> {
              paginationAdapter.addItems(items);
              paginationAdapter.notifyDataSetChanged();
              loading = false;
              progressBar.setVisibility(View.INVISIBLE);
            });

    compositeDisposable.add(disposable);

    paginator.onNext(pageNumber);

  }

  /**
   * Simulation of network data
   */
  private Single<List<String>> dataFromNetwork(final int page) {
    return Single.just(true)
            .delay(2, TimeUnit.SECONDS)
            .map(value -> {
              List<String> items = new ArrayList<>();
              for (int i = 1; i <= 10; i++) {
                items.add("Item " + (page * 10 + i));
              }
              return items;
            });
  }
}
