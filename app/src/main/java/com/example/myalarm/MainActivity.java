package com.example.myalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myalarm.database.room.AlarmEntity;
import com.example.myalarm.database.dbaccess.RoomAlarmSave;



public class MainActivity extends AppCompatActivity {

    Button button;
    Integer myInt = 1;
    RoomAlarmSave roomOperations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        roomOperations = new RoomAlarmSave(getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomOperations.insert(new AlarmEntity(myInt++,"arshan"));
                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
            }
        });



    }
}