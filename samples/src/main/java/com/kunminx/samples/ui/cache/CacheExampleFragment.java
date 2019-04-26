package com.kunminx.samples.ui.cache;

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
import com.kunminx.samples.ui.cache.model.Data;
import com.kunminx.samples.ui.cache.source.DataSource;
import com.kunminx.samples.ui.cache.source.DiskDataSource;
import com.kunminx.samples.ui.cache.source.MemoryDataSource;
import com.kunminx.samples.ui.cache.source.NetworkDataSource;
import com.kunminx.samples.utils.AppConstant;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CacheExampleFragment extends Fragment {

    private static final String TAG = CacheExampleFragment.class.getSimpleName();
    Button btn;
    TextView textView;
    DataSource dataSource;

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

        dataSource = new DataSource(new MemoryDataSource(), new DiskDataSource(), new NetworkDataSource());
    }

    private void doSomeWork() {

        Observable<Data> memory = dataSource.getDataFromMemory();
        Observable<Data> disk = dataSource.getDataFromDisk();
        Observable<Data> network = dataSource.getDataFromNetwork();

        Observable.concat(memory, disk, network)
                .firstElement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .subscribe(getObserver());
    }

    private Observer<Data> getObserver() {
        return new Observer<Data>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Data data) {
                textView.append(" onNext : " + data.source);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onNext : " + data.source);
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