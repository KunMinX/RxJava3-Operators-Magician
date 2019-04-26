package com.kunminx.samples.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.kunminx.samples.R;
import com.kunminx.samples.ui.operators.AsyncSubjectExampleFragment;
import com.kunminx.samples.ui.operators.BehaviorSubjectExampleFragment;
import com.kunminx.samples.ui.operators.BufferExampleFragment;
import com.kunminx.samples.ui.operators.CompletableObserverExampleFragment;
import com.kunminx.samples.ui.operators.ConcatExampleFragment;
import com.kunminx.samples.ui.operators.DebounceExampleFragment;
import com.kunminx.samples.ui.operators.DeferExampleFragment;
import com.kunminx.samples.ui.operators.DelayExampleFragment;
import com.kunminx.samples.ui.operators.DisposableExampleFragment;
import com.kunminx.samples.ui.operators.DistinctExampleFragment;
import com.kunminx.samples.ui.operators.FilterExampleFragment;
import com.kunminx.samples.ui.operators.FlowableExampleFragment;
import com.kunminx.samples.ui.operators.IntervalExampleFragment;
import com.kunminx.samples.ui.operators.LastOperatorExampleFragment;
import com.kunminx.samples.ui.operators.MapExampleFragment;
import com.kunminx.samples.ui.operators.MergeExampleFragment;
import com.kunminx.samples.ui.operators.PublishSubjectExampleFragment;
import com.kunminx.samples.ui.operators.ReduceExampleFragment;
import com.kunminx.samples.ui.operators.ReplayExampleFragment;
import com.kunminx.samples.ui.operators.ReplaySubjectExampleFragment;
import com.kunminx.samples.ui.operators.ScanExampleFragment;
import com.kunminx.samples.ui.operators.SimpleExampleFragment;
import com.kunminx.samples.ui.operators.SingleObserverExampleFragment;
import com.kunminx.samples.ui.operators.SkipExampleFragment;
import com.kunminx.samples.ui.operators.SwitchMapExampleFragment;
import com.kunminx.samples.ui.operators.TakeExampleFragment;
import com.kunminx.samples.ui.operators.TakeUntilExampleFragment;
import com.kunminx.samples.ui.operators.TakeWhileExampleFragment;
import com.kunminx.samples.ui.operators.ThrottleFirstExampleFragment;
import com.kunminx.samples.ui.operators.ThrottleLastExampleFragment;
import com.kunminx.samples.ui.operators.TimerExampleFragment;
import com.kunminx.samples.ui.operators.WindowExampleFragment;
import com.kunminx.samples.ui.operators.ZipExampleFragment;

import androidx.appcompat.app.AppCompatActivity;

public class OperatorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators);
    }

    public void startSimpleActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, SimpleExampleFragment.class));
    }

    public void startMapActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, MapExampleFragment.class));
    }

    public void startZipActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, ZipExampleFragment.class));
    }

    public void startDisposableActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, DisposableExampleFragment.class));
    }

    public void startTakeActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, TakeExampleFragment.class));
    }

    public void startTimerActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, TimerExampleFragment.class));
    }

    public void startIntervalActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, IntervalExampleFragment.class));
    }

    public void startSingleObserverActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, SingleObserverExampleFragment.class));
    }

    public void startCompletableObserverActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, CompletableObserverExampleFragment.class));
    }

    public void startFlowableActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, FlowableExampleFragment.class));
    }

    public void startReduceActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, ReduceExampleFragment.class));
    }

    public void startBufferActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, BufferExampleFragment.class));
    }

    public void startFilterActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, FilterExampleFragment.class));
    }

    public void startSkipActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, SkipExampleFragment.class));
    }

    public void startScanActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, ScanExampleFragment.class));
    }

    public void startReplayActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, ReplayExampleFragment.class));
    }

    public void startConcatActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, ConcatExampleFragment.class));
    }

    public void startMergeActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, MergeExampleFragment.class));
    }

    public void startDeferActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, DeferExampleFragment.class));
    }

    public void startDistinctActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, DistinctExampleFragment.class));
    }

    public void startLastOperatorActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, LastOperatorExampleFragment.class));
    }

    public void startReplaySubjectActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, ReplaySubjectExampleFragment.class));
    }

    public void startPublishSubjectActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, PublishSubjectExampleFragment.class));
    }

    public void startBehaviorSubjectActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, BehaviorSubjectExampleFragment.class));
    }

    public void startAsyncSubjectActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, AsyncSubjectExampleFragment.class));
    }

    public void startThrottleFirstActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, ThrottleFirstExampleFragment.class));
    }

    public void startThrottleLastActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, ThrottleLastExampleFragment.class));
    }

    public void startDebounceActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, DebounceExampleFragment.class));
    }

    public void startWindowActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, WindowExampleFragment.class));
    }

    public void startDelayActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, DelayExampleFragment.class));
    }

    public void startSwitchMapActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, SwitchMapExampleFragment.class));
    }

    public void startTakeWhileActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, TakeWhileExampleFragment.class));
    }

    public void startTakeUntilActivity(View view) {
        startActivity(new Intent(OperatorsActivity.this, TakeUntilExampleFragment.class));
    }
}
