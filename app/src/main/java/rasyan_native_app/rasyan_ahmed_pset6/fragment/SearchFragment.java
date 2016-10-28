package rasyan_native_app.rasyan_ahmed_pset6.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.w3c.dom.Text;

import java.util.ArrayList;

import rasyan_native_app.rasyan_ahmed_pset6.R;
import rasyan_native_app.rasyan_ahmed_pset6.other.Apigetter;
import rasyan_native_app.rasyan_ahmed_pset6.other.ImageAdapter;

import static rasyan_native_app.rasyan_ahmed_pset6.activity.Home.context;

/**
 * a fragment that shows a list of recipes.
 * by default it shows a list of the currently trending items,
 * when the floating action button is pressed a dialog shows up where the user can input its search query,
 * ApiGetter processes that query and the list shown here gets refreshed with the new results.
 */
public class SearchFragment extends Fragment {

    private RecyclerView mRecyclerView;
    public static ImageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab;
    private static ArrayList<String> titles;
    private static ArrayList<String> images;
    private static ArrayList<String> ids;
    public static boolean searching;
    private static String search;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();

        // if you are showing a search request:
        if (searching) {

            // set the title
            TextView title = (TextView) view.findViewById(R.id.text);
            title.setText("Searching for: " + search);

            // and show the search results
            mAdapter.swap(titles, images, ids);
        } else {

            // set the title
            TextView title = (TextView) view.findViewById(R.id.text);
            title.setText("Currently Trending recipes");

            // get the trending list.
            Apigetter getter = new Apigetter(getActivity());
            getter.execute("trending");
        }

        // show the dialog fragment where the user can input its search query
        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.DialogFragment newFragment = PopupFragment.newInstance();
                newFragment.show(getActivity().getFragmentManager(), "searchDialog");
            }
        });
    }

    // create the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    // set up the recycler view, it starts out empty
    private void setUpRecyclerView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.list);

        // this line below adds a divider between the recyclerView items,
        // requires a dependency.
        // source : https://github.com/yqritc/RecyclerView-FlexibleDivider
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).color(Color.BLACK).build());


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // create an empty adapter.
        mAdapter = new ImageAdapter(null,null,null,getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    // this method is accessed from the apigetter, it sets data in this class and tells the adapter to refresh the list.
    public static void refresh(ArrayList<String> names, ArrayList<String> imageURLs, ArrayList<String> id, String query) {
        titles = names;
        images = imageURLs;
        ids = id;
        search = query;
        mAdapter.swap(names,imageURLs,id);
    }
}
