package com.example.minchul.gaama.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.minchul.gaama.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.device_list));

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: {
                Intent intent = new Intent(this, GamePadActivity.class);
                startActivity(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(this, KeyboardActivity.class);
                startActivity(intent);
            }
            break;
            case 2: {
                Intent intent = new Intent(this, MouseActivity.class);
                startActivity(intent);
            }
            break;
            default:
                break;
        }

    }
}
