package rasyan_native_app.rasyan_ahmed_pset6.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Scanner;

import rasyan_native_app.rasyan_ahmed_pset6.R;
import rasyan_native_app.rasyan_ahmed_pset6.activity.Home;
import rasyan_native_app.rasyan_ahmed_pset6.fragment.FollowedFragment;

import static rasyan_native_app.rasyan_ahmed_pset6.activity.Home.CURRENT_TAG;

/**
 * Created by Rasyan on 23-9-2016.
 */

public class Apigetter extends AsyncTask<String,Integer,String> {
    private Context context;
    private JSONObject json;
    private String type;
    private Activity activity;

    public Apigetter(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        String f2f;
        // use the right link depending on the type passed to this class
        type = params[0];
        if (Objects.equals(type, "search")) {
            f2f = "http://food2fork.com/api/search?key=fca46fd05c5ca9df7b6d2aa2eb67d17b&q=";
        } else {
            f2f = "http://food2fork.com/api/get?key=fca46fd05c5ca9df7b6d2aa2eb67d17b&rId=";
        }

        try {
            //get the data from the web

            String utf = URLEncoder.encode(params[1],"UTF-8");
            System.out.println("testy1 = " + utf);
            URL url;
            url = new URL(f2f + utf);
            System.out.println("testy2 = " + url.toString());
            InputStream stream = url.openStream();

            Scanner s = new Scanner(stream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";

            json = new JSONObject(result);
            System.out.println("json = " + json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // switch case to differentiate the types
        switch (type) {
            case "search":
                try {
                    JSONArray recipes = json.getJSONArray("recipes");
                    System.out.println("recipe1 = " + recipes.get(0));



//                    // make a search class with the info gottent from the web
//                    // and produce an intent to show search results
//                    if (json.getString("Response").equals("True")) {
//                        SearchResult searchresult = new SearchResult(json.getJSONArray("Search")
//                                , Integer.parseInt(json.getString("totalResults")));
//                        Intent intent = new Intent(context, ListView.class);
//                        intent.putExtra("searched", searchresult);
//                        context.startActivity(intent);
//                    } else {
//                        // if the search failed, show error message and dont produce an intent
//                        Toast.makeText(context, json.getString("Error"),
//                                Toast.LENGTH_SHORT).show();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            case "id":
                try {
                    //String a = json.get("social_rank").toString();
                    json = json.getJSONObject("recipe");
                    JSONArray arrJson = json.getJSONArray("ingredients");
                    String[] ingredients = new String[arrJson.length()];
                    for(int i=0;i<arrJson.length();i++) {
                        ingredients[i] = arrJson.getString(i);
                    }
                    ((Home)activity).changeFragment(2,json.getString("title"),json.getString("image_url"),
                            json.getString("source_url"),json.get("social_rank").toString(),
                            ingredients);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }
    }
}
