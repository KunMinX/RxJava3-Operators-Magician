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

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by techteam on 13/09/16.
 */
public class LastOperatorExampleFragment extends Fragment {

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
     * last() emits only the last item emitted by the Observable.
     */
    private void doSomeWork() {
        getObservable().last("A1") // the default item ("A1") to emit if the source ObservableSource is empty
                .subscribe(getObserver());
    }

    private Observable<String> getObservable() {
        return Observable.just("A1", "A2", "A3", "A4", "A5", "A6");
    }

    private SingleObserver<String> getObserver() {
        return new SingleObserver<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(String value) {
                textView.append(" onNext : value : " + value);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onNext value : " + value);
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
