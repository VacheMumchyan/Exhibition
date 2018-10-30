package com.vache.exhibition;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.vache.exhibition.MainActivity.TAG;

public class FileExhibitsLoader implements Exhibit.ExhibitsLoader {

    private Context context;

    public FileExhibitsLoader(Context context) {
        this.context = context;
    }

    @Override
    public List<Exhibit> getExhibitList() {
        List<Exhibit> exhibitList = new ArrayList<>();
        try {
            FileInputStream is = new FileInputStream(context.getFilesDir().getAbsolutePath() + "/exhibits.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONArray list = jsonObject.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject exhibit = list.getJSONObject(i);
                String title = exhibit.getString("title");
                List<String> images = new ArrayList<>();
                JSONArray imageArray = exhibit.getJSONArray("images");
                for (int j = 0; j < imageArray.length(); j++) {
                    images.add(imageArray.getString(j));
                }
                exhibitList.add(new Exhibit(title, images));
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return exhibitList;
    }
}
