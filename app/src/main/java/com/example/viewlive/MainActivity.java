package com.example.viewlive;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    int count =0;
    private Object Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating a data object
        //to pass the data with workRequest
        //we can put as many variables needed
        Data data = new Data.Builder().putString(MyWorker.TASK_DESC, "The task data passed from MainActivity").build();
        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();
        final OneTimeWorkRequest workRequest1 = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();
        final OneTimeWorkRequest workRequest2 = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();
        final OneTimeWorkRequest workRequest3 = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();

        final PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 1, TimeUnit.SECONDS).setInputData(data).build();

//        Constraints constraints = new Constraints.Builder()
//                .setRequiresCharging(true)
//                .build();


        //WorkManager.getInstance().enqueue(periodicWorkRequest);

        findViewById(R.id.buttonEnqueue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // WorkManager.getInstance().enqueue(periodicWorkRequest);
//                WorkManager.getInstance().
//                        beginWith(workRequest)
//                        .then(workRequest1)
//                        .then(workRequest2)
//                        .then(workRequest3)
//                        .enqueue();
               WorkManager.getInstance().enqueueUniquePeriodicWork("send_reminder_periodic", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);
            }
        });


        textView = findViewById(R.id.textViewStatus);
        WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        textView.append(workInfo.getState().name() + "\n");
                    }
                });
    }

    public void letsee()

    {
        count++;
        textView.setText(count);
    }
}