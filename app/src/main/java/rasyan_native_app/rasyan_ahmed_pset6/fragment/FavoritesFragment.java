package rasyan_native_app.rasyan_ahmed_pset6.fragment;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.w3c.dom.Text;

import java.util.ArrayList;

import rasyan_native_app.rasyan_ahmed_pset6.R;
import rasyan_native_app.rasyan_ahmed_pset6.activity.Home;
import rasyan_native_app.rasyan_ahmed_pset6.other.Recipe;
import rasyan_native_app.rasyan_ahmed_pset6.other.ImageAdapter;

import static rasyan_native_app.rasyan_ahmed_pset6.R.id.fab;

/***
 * This fragment shows the list of faforites that the user has added.
 */
public class FavoritesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private RecyclerView mRecyclerView;
    public static ImageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();



    public FavoritesFragment() {

    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // after the view is created you can start fetching the recipes from firebase
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText("Your Favorites:");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        getFavorites();
    }

    // creates the view , uses the same view as the Search fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    // fetches the recipes from the firebase database
    private void getFavorites() {
        // connects to firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        // selects the users recipes depository in the database
        DatabaseReference myRef = db.getReference();
        myRef = myRef.child("users").child(user.getUid()).child("recipes");

        // everything below this listener is done once.
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for each child:
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    // add its values to the arraylists
                    Recipe recipe = postSnapshot.getValue(Recipe.class);
                    titles.add(recipe.getTitle());
                    images.add(recipe.getImage());
                    ids.add(recipe.getId());

                    // use those values to setup the recyclerView
                    setUpRecyclerView();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setUpRecyclerView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.list);

        // this line below adds a divider between the recyclerView items,
        // requires a dependency.
        // source : https://github.com/yqritc/RecyclerView-FlexibleDivider
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(Home.context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ImageAdapter(titles,images,ids,getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

}
