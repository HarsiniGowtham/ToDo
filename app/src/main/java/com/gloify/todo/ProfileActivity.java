package com.gloify.todo;


import com.github.clans.fab.FloatingActionButton;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn;
    private List<String> mDateList = new ArrayList<>();
    private List<String> mTimeList = new ArrayList<>();
    private List<String> mMessageList = new ArrayList<>();

    DatabaseManager databaseManager = new DatabaseManager(this);

    RecyclerView recyclerView;
    AdapterSectionRecycler adapterRecycler;
    List<SectionHeader> sectionHeaders = new ArrayList<>();
    FloatingActionButton mFAB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseManager.open();
        fetchData();

        //initialize RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mFAB = (FloatingActionButton) findViewById(R.id.fab);
        mFAB.setOnClickListener(this::onClick);

        //setLayout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapterRecycler = new AdapterSectionRecycler(this, sectionHeaders);
        recyclerView.setAdapter(adapterRecycler);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                ChooserDialogFragment dialogFragment = new ChooserDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "My  Fragment");
                break;
        }
    }

    public void insertData(String date, String time, String message) {
        databaseManager.insert(message, date, time);
        fetchData();
        adapterRecycler = new AdapterSectionRecycler(ProfileActivity.this, sectionHeaders);
        recyclerView.setAdapter(adapterRecycler);
    }


    @SuppressLint("Range")
    private void fetchData() {
        Cursor cursor = databaseManager.fetch();
        sectionHeaders = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATE));
                String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TIME));
                String message = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_MSG));

                mDateList = new ArrayList<>();
                mTimeList = new ArrayList<>();
                mMessageList = new ArrayList<>();

                mDateList.add(date);
                mTimeList.add(time);
                mMessageList.add(message);

                ListMultimap<String, String> multimap = ArrayListMultimap.create();
                for (int i = 0; i < mDateList.size(); i++) {
                    multimap.put(mDateList.get(i), mTimeList.get(i) + "," + mMessageList.get(i));
                }
                Map<String, Collection<String>> map = multimap.asMap();
                int i = 1;
                for (Map.Entry entry : map.entrySet()) {
                    String key = (String) entry.getKey();
                    List<String> value = (List<String>) entry.getValue();
                    //Create a List of Child DataModel
                    List<Child> childList = new ArrayList<>();
                    for (String str : value) {
                        childList.add(new Child(str));
                    }

                    //Create a List of SectionHeader DataModel implements SectionHeader
                    sectionHeaders.add(new SectionHeader(childList, key, i));
                }

            } while (cursor.moveToNext());
        }
    }


}

