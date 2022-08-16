package com.test;

import android.content.Context;
import android.os.AsyncTask;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.test.adapter.RecyclerViewAdapter;
import com.test.model.Post;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class GetPosts extends AsyncTask<String, Void, String> {


    private Context context;
    private RecyclerView recyclerView;
    private String client_id;

    List<Post> posts = new ArrayList<Post>();

    public GetPosts(Context context, RecyclerView recyclerView, String client_id){
        this.context = context;
        this.recyclerView = recyclerView;
        this.client_id = client_id;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            String link = "https://api.unsplash.com/photos/?client_id="+client_id;

            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoInput(true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while(line != null){
                sb.append(line);
                break;
            }
            return sb.toString();

        }catch (Exception e){
            return new String("Exeption" + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {

        result = "{\"posts\":"+result+"}";
        try {
            JSONObject object = new JSONObject(result);
            JSONArray array = object.getJSONArray("posts");
            for(int i = 0; i < array.length(); i++){
                JSONObject temp = array.getJSONObject(i);
                System.out.println(temp);
                posts.add(new Post(temp.getString("id"), temp.getString("user"),temp.getString("description"),temp.getString("urls")));
            }
            setMainRecycler();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setMainRecycler() {
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(context, recyclerView, posts);
        recyclerView.setAdapter(adapter);
    }

}
