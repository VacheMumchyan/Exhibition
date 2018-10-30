package com.vache.exhibition;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "exhibit_tag";
    private static final String BASE_URL = "https://goo.gl/t1qKMS";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.MULTIPLY);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        final RecyclerAdapter adapter = new  RecyclerAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressBar.setVisibility(View.GONE);
                if (writeFile(response.toString())) {
                    FileExhibitsLoader fileExhibitsLoader = new FileExhibitsLoader(MainActivity.this);
                    List<Exhibit> list = sortByTitle(fileExhibitsLoader.getExhibitList());
                    recyclerView.addItemDecoration(new ItemDecoration(20, list.size(), false));
                    adapter.putExhibits(list);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, throwable.getMessage());
                Toast.makeText(MainActivity.this, "Error loading", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean writeFile(String json) {
        try{
            File f = new File(getFilesDir(), "exhibits.json");
            FileWriter writer = new FileWriter(f);
            writer.append(json);
            writer.flush();
            writer.close();
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    private List<Exhibit> sortByTitle(List<Exhibit> list) {
        List<Exhibit> exhibitList = new ArrayList<>();
        Map<String, Set<String>> map = new TreeMap<>();
        for (Exhibit e : list) {
            Set<String> set = new TreeSet<>();
            set.addAll(e.getImages());
            map.put(e.getTitle(), set);
        }
        for (Map.Entry<String, Set<String>> e : map.entrySet()) {
            List<String> images = new ArrayList<>();
            images.addAll(e.getValue());
            exhibitList.add(new Exhibit(e.getKey(), images));
        }
        return exhibitList;
    }
}
