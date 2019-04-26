package com.kunminx.samples.ui.compose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kunminx.samples.R;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class ComposeOperatorExampleFragment extends Fragment {

    private RxSchedulers schedulers = new RxSchedulers();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose_operator_example, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         /*
            Compose for reusable code.
         */
        Observable.just(1, 2, 3, 4, 5)
                .compose(schedulers.<Integer>applyObservableAsync())
                .subscribe(/* */);

        Flowable.just(1, 2, 3, 4, 5)
                .compose(schedulers.<Integer>applyFlowableAsysnc())
                .subscribe(/* */);
    }

}
