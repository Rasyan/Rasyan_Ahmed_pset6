package rasyan_native_app.rasyan_ahmed_pset6.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import rasyan_native_app.rasyan_ahmed_pset6.R;
import rasyan_native_app.rasyan_ahmed_pset6.activity.Home;
import rasyan_native_app.rasyan_ahmed_pset6.other.IngredientsAdapter;
import rasyan_native_app.rasyan_ahmed_pset6.other.Recipe;

import static java.lang.Thread.sleep;

/**
 * This fragment shows the user the details of the recipy he has chosen.
 * it also has a floating action bar that can either delete or add an intem to the faforite list,
 * depending on wheter it is already in it or not.
 */
public class RecipeFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "image";
    private static final String ARG_PARAM3 = "link";
    private static final String ARG_PARAM4 = "score";
    private static final String ARG_PARAM5 = "ingredients";
    private static final String ARG_PARAM6 = "id";

    private String title;
    private String image;
    private String link;
    private String score;
    private ArrayList<String> ingredients;
    private String id;
    private RecyclerView mRecyclerView;
    public IngredientsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab2;
    private boolean alreadyInList = false;
    private String deleteId;
    private DatabaseReference myRef;

    public RecipeFragment() {
        // Required empty public constructor
    }

    // passes along the arguments to onCreate
    public static RecipeFragment newInstance(String param1, String param2, String param3, String param4, ArrayList<String> param5, String param6) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putStringArrayList(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        fragment.setArguments(args);
        return fragment;
    }

    // saves all the arguments for use later on
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            image = getArguments().getString(ARG_PARAM2);
            link = getArguments().getString(ARG_PARAM3);
            score = getArguments().getString(ARG_PARAM4);
            ingredients = getArguments().getStringArrayList(ARG_PARAM5);
            id = getArguments().getString(ARG_PARAM6);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        setUpViews();

        // connects to firebase and finds the users personal recipes
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        myRef = ref.child("users").child(user.getUid()).child("recipes");

        // check if you already have this item in your faforites, if you know that already then skip this.
        if (!alreadyInList) {
            checkIfInList();
        }
        // set the floating action button source image
        setButtonImage();

        // the listener for the floating action button
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // create a recipe class to store the recipe in, this class then gets stored in firebase db
                Recipe recipe = new Recipe(title, image, score, link, ingredients, id);

                // if you dont have this recipe in your favorites yet:
                if (!alreadyInList) {

                    // add it to the database
                    myRef.push().setValue(recipe);

                    // now you know that you have it in your database, change the view accordingly
                    alreadyInList = true;
                    checkIfInList(); // to find the new id, in case you want to delete it again right away
                    setButtonImage();
                    Toast.makeText(getActivity(), "Recipe added to favorites", Toast.LENGTH_SHORT).show();

                // if you already have this recipe in your favorites
                } else {

                    // remove it from the database
                    // deleteId is found in checkIfInList()
                    myRef.child(deleteId).removeValue();

                    // you know you dont have it anymore. change the view accordingly
                    alreadyInList = false;
                    setButtonImage();
                    Toast.makeText(getActivity(), "Recipe deleted from favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // sets up the text and image views.
    private void setUpViews() {
        View view = getView();
        ImageView imageView = (ImageView) view.findViewById(R.id.poster);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(image, imageView);

        TextView textView = (TextView) view.findViewById(R.id.name);
        textView.setText(title);
        TextView scoreView = (TextView) view.findViewById(R.id.score);
        scoreView.setText("Score: " + score);

        // this text view becomes a clickable link
        TextView linkView = (TextView) view.findViewById(R.id.link);
        linkView.setText(Html.fromHtml("<a href=\"" + link + "\">Link to full recipe</a>"));
        linkView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    // sets the right source image for the button depending on if you already have it in your list or not
    private void setButtonImage() {
        fab2 = (FloatingActionButton) getView().findViewById(R.id.fab2);
        if (alreadyInList) {
            fab2.setImageResource(R.drawable.ic_menu_close_clear_cancel);
        } else {
            fab2.setImageResource(R.drawable.ic_menu_add);
        }
    }

    // see whether the item is already in your faforites or not
    // if it is, get its database id so you know what to delete
    private void checkIfInList() {

        // everything in this listener gets repeated once
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Recipe> fav = new ArrayList<>();
                ArrayList<String> favID = new ArrayList<>();

                // add each child to fav and its key to favID
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Recipe post = postSnapshot.getValue(Recipe.class);
                    String key = postSnapshot.getKey();
                    fav.add(post);
                    favID.add(key);
                }

                // find the child that has the same recipe id as the one we are looking for, (if its in the list)
                // if this is the case, make its key (stored in favID) the deleteId
                for (int i = 0; i < fav.size(); i++) {
                    Recipe recipe = fav.get(i);
                    if (id.equals(recipe.getId())) {
                        alreadyInList = true;
                        setButtonImage();
                        deleteId = favID.get(i);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }

        });
    }

    private void setUpRecyclerView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.list);

        // this line below adds a divider between the recyclerView items,
        // requires a dependency.
        // source : https://github.com/yqritc/RecyclerView-FlexibleDivider
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).color(Color.BLACK).build());



        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(Home.context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // use the adapter
        mAdapter = new IngredientsAdapter(ingredients);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }
}
