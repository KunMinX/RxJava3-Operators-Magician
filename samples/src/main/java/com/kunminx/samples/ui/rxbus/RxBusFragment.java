package com.kunminx.samples.ui.rxbus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kunminx.samples.MyApplication;
import com.kunminx.samples.R;
import com.kunminx.samples.model.Events;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amitshekhar on 06/02/17.
 */

public class RxBusFragment extends Fragment {

    public static final String TAG = RxBusFragment.class.getSimpleName();
    TextView textView;
    Button button;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear(); // do not send event after activity has been destroyed
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_example, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.btn);
        textView = view.findViewById(R.id.textView);

        disposables.add(((MyApplication) getActivity().getApplication())
                .bus()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) {
                        if (object instanceof Events.AutoEvent) {
                            textView.setText("Auto Event Received");
                        } else if (object instanceof Events.TapEvent) {
                            textView.setText("Tap Event Received");
                        }
                    }
                }));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication) getActivity().getApplication())
                        .bus()
                        .send(new Events.TapEvent());
            }
        });
    }

}
