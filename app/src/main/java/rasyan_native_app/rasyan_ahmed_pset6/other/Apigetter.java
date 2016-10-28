package rasyan_native_app.rasyan_ahmed_pset6.other;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import rasyan_native_app.rasyan_ahmed_pset6.R;
import rasyan_native_app.rasyan_ahmed_pset6.activity.Home;
import rasyan_native_app.rasyan_ahmed_pset6.fragment.SearchFragment;

/**
 * Created by Rasyan on 23-9-2016.
 * This class handles all the api calls,
 * it searches for recipes based on a query (or whats trending)
 * and it can find the details of a recipe based on an id.
 */

public class Apigetter extends AsyncTask<String,Integer,String> {
    private Context context;
    private JSONObject json;
    private String type;
    private Activity activity;
    private String query;

    // the constructor
    public Apigetter(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        // use the right link depending on the type passed to this class
        type = params[0];
        URL url = null;

        // the trending url is always the same
        if (Objects.equals(type, "trending")) {
            try {
                url = new URL("http://food2fork.com/api/search?key=fca46fd05c5ca9df7b6d2aa2eb67d17b&sort=t");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {

            // get the base url
            String f2f;
            if (Objects.equals(type, "search")) {
                f2f = "http://food2fork.com/api/search?key=fca46fd05c5ca9df7b6d2aa2eb67d17b&q=";
            } else {
                f2f = "http://food2fork.com/api/get?key=fca46fd05c5ca9df7b6d2aa2eb67d17b&rId=";
            }

            // add the parameters to the base url
            try {
                //get the data from the web
                query = params[1];
                String utf = URLEncoder.encode(query, "UTF-8");
                url = new URL(f2f + utf);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // read the info from the api by means of an inputstream
        InputStream stream = null;
        try {
            stream = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner s = new Scanner(stream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        // save result as an json
        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // when doInBackground is done:
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        // if statement to differentiate the types
        if (type.equals("id")) {
            // if the type is id, then dissect the json and ask home to change to the recipe fragment
            // with the resulting data
            try {

                // get the ingredients list
                json = json.getJSONObject("recipe");
                JSONArray arrJson = json.getJSONArray("ingredients");
                ArrayList<String> ingredients = new ArrayList<>();
                for(int i=0;i<arrJson.length();i++) {
                    ingredients.add(arrJson.getString(i));
                }
                // ask home to show the data using the recipe fragment
                ((Home)activity).changeFragment(2,json.getString("title"),json.getString("image_url"),
                        json.getString("source_url"),json.get("social_rank").toString(),
                        ingredients,json.getString("recipe_id"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // if trending or searching then refresh the search fragment with the new data
            try {
                int count =  json.getInt("count");
                if (count == 0) {
                    Toast.makeText(context, "No search results", Toast.LENGTH_SHORT).show();
                } else {

                    // add each childs data in the json to the corresponding arraylist
                    JSONArray recipes = json.getJSONArray("recipes");
                    ArrayList<String> titles = new ArrayList<>();
                    ArrayList<String> images = new ArrayList<>();
                    ArrayList<String> ids = new ArrayList<>();
                    for (int i=0; i < count; i++ ) {
                        JSONObject recipe = recipes.getJSONObject(i);
                        titles.add(recipe.getString("title"));
                        images.add(recipe.getString("image_url"));
                        ids.add(recipe.getString("recipe_id"));
                    }
                    // refresh the recyclerList in the search fragment
                    SearchFragment.refresh(titles,images,ids,query);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
