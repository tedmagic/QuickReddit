package umwat.quickreddit;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created on 12/31/14.
 */
public class JsonFeedTask extends AsyncTask<Void, Void, JSONObject> {
    String jsonFeedUrl;


    public JsonFeedTask(String url) {
        jsonFeedUrl = url;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        try {
            JSONObject jsonObject;
            jsonObject = new JSONObject(getJson());
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getJson() {
        InputStream in = null;
        String rssFeed = "";
        try {
            URL url = new URL(jsonFeedUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            in = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int count; (count = in.read(buffer)) != -1; ) {
                out.write(buffer, 0, count);
            }
            byte[] response = out.toByteArray();
            rssFeed = new String(response, "UTF-8");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return rssFeed;
    }

    @Override
    protected void onPostExecute(JSONObject heyson) {
        try {


            JSONArray jsonArray = heyson.getJSONObject("data").getJSONArray("children");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject data = jsonArray.getJSONObject(i).getJSONObject("data");

                Submission submission = new Submission();
                submission.setScore(data.getInt("score"));
                submission.setTitle(data.getString("title"));
                submission.setUrl(data.getString("url"));
                submission.setSubreddit(data.getString("subreddit"));
                submission.setCommentsUrl("http://www.reddit.com" + data.getString("permalink"));
                submission.setThumbnailUrl(data.getString("thumbnail"));
                submission.setSelfText(data.getString("selftext_html"));
                submission.setSelf(data.getBoolean("is_self"));
                submission.setDomain(data.getString("domain"));
                submission.setAuthor(data.getString("author"));
                submission.setNumComments(data.getInt("num_comments"));
                submission.setStickied(data.getBoolean("stickied"));
                submission.setOver18(data.getBoolean("over_18"));
                submission.setCreated(data.getLong("created_utc"));

                MainFragment.submisions.add(submission);


            }



        } catch (JSONException e) {
            e.printStackTrace();
        }



        MainFragment.swipeView.setRefreshing(false);
        MainActivity.adapter.notifyDataSetChanged();
    }

}




