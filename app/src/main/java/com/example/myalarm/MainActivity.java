package com.example.myalarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myalarm.Model.Alarm;
import com.example.myalarm.database.room.AlarmEntity;
import com.example.myalarm.database.dbaccess.RoomAlarmSave;
import com.google.gson.Gson;

import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;


public class MainActivity extends AppCompatActivity {

    Button button;
    Integer myInt = 1;
    RoomAlarmSave roomOperations;
    FlutterEngine flutterEngine;
    public static final String CHANNEL = "com.example.arshan";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        roomOperations = new RoomAlarmSave(getApplicationContext());
        flutterEngine = MyApplication.flutterEngine;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alarm alarm = new Alarm();
                Gson gson = new Gson();
                String json = gson.toJson(alarm);
                Alarm alarm2 = gson.fromJson(json,Alarm.class);
                System.out.println(alarm2);

//                roomOperations.insert(new AlarmEntity(myInt++,"arshan"));
//                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
            }
        });

        startActivity(
                FlutterActivity
                        .withCachedEngine("my_engine_id")
                        .build(this)
        );




            new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                    .setMethodCallHandler(
                            (call, result) -> {
                                if(call.method.equals("arshan")) {
                                    Map<String ,Object> alarmData = call.arguments();
                                    Alarm alarm = new Alarm(alarmData);
                                    result.success(Alarm.toMap(alarm));
                                }

                            }
                    );



    }
}