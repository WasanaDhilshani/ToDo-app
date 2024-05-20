package com.example.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TaskActivity extends AppCompatActivity {
    LinearLayout layout;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        dbHelper = new DatabaseHelper(this);
        layout = findViewById(R.id.container);

        loadTasks();
    }

    private void loadTasks() {
        Cursor cursor = dbHelper.getAllTasks();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            addCard(name);
        }
    }

    private void addCard(String name) {
        final View view = getLayoutInflater().inflate(R.layout.card, null);

        TextView nameView = view.findViewById(R.id.name);
        Button delete = view.findViewById(R.id.delete);
        nameView.setText(name);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper.deleteTask(name) > 0) {
                    layout.removeView(view);
                }
            }
        });
        layout.addView(view);
    }
}


