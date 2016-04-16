package jukebox.android.com.socialjukebox;

/**
 * Created by f1vyer1 on 4/16/16.
 */


        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.View;
        import android.widget.ProgressBar;
        import android.widget.Toast;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;

public class SongList extends AppCompatActivity {
    private static final String TAG = "RecyclerViewExample";
    private List<FeedItem> feedsList;
    private RecyclerView mRecyclerView;
    private SongListAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mRefreshTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        progressBar.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setRefreshing(false);

        // Downloading data from below url
        final String url = "http://javatechig.com/?json=get_recent_posts&count=45";
        new AsyncHttpTask().execute(url);
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new SongListAdapter(SongList.this, feedsList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(SongList.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            feedsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setSongName(post.optString("title"));
                item.setUserName(post.optString("thumbnail"));
                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}