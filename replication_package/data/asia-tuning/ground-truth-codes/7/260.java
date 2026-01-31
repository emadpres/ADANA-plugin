package ru.maslo.android.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ru.maslo.android.view.ReorderRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ReorderRecyclerView recyclerView;
    private List<String> cheeseList;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (ReorderRecyclerView) findViewById(R.id.recyclerView);

        cheeseList = new ArrayList<>();
        Collections.addAll(cheeseList, Cheeses.sCheeseStrings);

        mLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHandlerId(R.id.handler);

        CustomAdapter adapter= new CustomAdapter(this,cheeseList);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);


    }
}