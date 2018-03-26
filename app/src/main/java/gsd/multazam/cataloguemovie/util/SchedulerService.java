package gsd.multazam.cataloguemovie.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import gsd.multazam.cataloguemovie.DetailActivity;
import gsd.multazam.cataloguemovie.R;
import gsd.multazam.cataloguemovie.SearchActivity;
import gsd.multazam.cataloguemovie.model.Movie;

/**
 * Created by Rehan on 25/03/2018.
 */

public class SchedulerService extends GcmTaskService {

    public static String TAG_TASK_UPCOMING = "upcoming movies";

    private ArrayList<Movie> mList = new ArrayList<>();

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_UPCOMING)) {
            loadData();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }

    private void loadData() {
        mList.clear();
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + SearchActivity.API_KEY + "&language=en-US";
        JsonObjectRequest reqData = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley", "Response : " + response);
                        try {
                            JSONArray result = response.getJSONArray("results");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject data = result.getJSONObject(i);
                                Movie mo = new Movie();
                                mo.setId(data.getInt("id"));
                                mo.setVoteavg(data.getString("vote_average"));
                                mo.setLanguage(data.getString("original_language"));
                                mo.setPopularity(data.getString("popularity"));
                                mo.setTitle(data.getString("title"));
                                mo.setOverview(data.getString("overview"));
                                mo.setRelease_date(data.getString("release_date"));
                                mo.setPoster(data.getString("poster_path"));
                                mList.add(mo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        int index = new Random().nextInt(mList.size());
                        Movie item = mList.get(index);
                        String title = mList.get(index).getTitle();
                        String message = mList.get(index).getOverview();
                        int notifId = 200;

                        showNotification(getApplicationContext(), title, message, notifId, item);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(reqData);
    }

    private void showNotification(Context context, String title, String message, int notifId, Movie item) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE, mList.get(item.getId()));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}

