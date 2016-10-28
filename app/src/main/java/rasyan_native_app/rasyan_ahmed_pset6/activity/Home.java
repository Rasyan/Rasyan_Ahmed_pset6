package rasyan_native_app.rasyan_ahmed_pset6.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import rasyan_native_app.rasyan_ahmed_pset6.R;
import rasyan_native_app.rasyan_ahmed_pset6.fragment.SearchFragment;
import rasyan_native_app.rasyan_ahmed_pset6.fragment.FavoritesFragment;
import rasyan_native_app.rasyan_ahmed_pset6.fragment.RecipeFragment;
import rasyan_native_app.rasyan_ahmed_pset6.other.CircleTransform;
/*
 * This is the Main activity of this app, this activity always has a full screen fragment on top of it
 * and a navigation drawer that is accessible by swiping from the left side of the screen to the right or
 * by the button in the top left.
 * most of the code inside this activity is there to provide functionality to the navigation drawer,
 * which regulates the interaction between the fragments.
 *
 * please note that this code has been made by following the tutorial on:
 * http://www.androidhive.info/2013/11/android-sliding-menu-using-navigation-drawer/
 * most of the code and comments here are from that tutorial but they are heavily edited to fit my own needs.
 */

public class Home extends AppCompatActivity {

    public static Context context;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;

    // Header image for the navigation bar used in the tutorial.
    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_SEARCH = "search";
    private static final String TAG_FAVO = "my_favorites";
    private static final String TAG_RECIPE = "recipe";
    public static String CURRENT_TAG = TAG_SEARCH; // selects the starting fragment

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_SEARCH;
            loadHomeFragment();
        }

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
    }

    // Load navigation menu header information
    private void loadNavHeader() {
        Bundle extras = getIntent().getExtras();
        txtName.setText(extras.getString("user"));

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(extras.getString("photo"))
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                Fragment searchFragment = new SearchFragment();
                return searchFragment;

            case 1:
                FavoritesFragment favoritesFragment = FavoritesFragment.newInstance();
                return favoritesFragment;

            default:
                return new SearchFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        // Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                // Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    // Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_search:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_SEARCH;
                        break;

                    case R.id.nav_my_favorites:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_FAVO;
                        break;

                    case R.id.nav_logout:
                        // launch new intent instead of loading fragment
                        Intent intent = new Intent(Home.this, Login.class);
                        intent.putExtra("logout", true);
                        startActivity(intent);
                        return true;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }


    // this method regulates all back press interactions
    @Override
    public void onBackPressed() {

        // if the drawer is open, a back press closes it.
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }


        if (shouldLoadHomeFragOnBackPress) {
            // This code loads home fragment when back key is pressed
            // when user is in other fragment than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_SEARCH;
                loadHomeFragment();
                return;
            } else {
                // this is an addition i made myself:
                if (SearchFragment.searching)  {
                    // if the user is in home and has search results up
                    // then tell it to display the default results
                    SearchFragment.searching = false;
                    loadHomeFragment();
                } else {
                    // if it is already showing the default result then the user is back where he started at first,
                    // so the correct behaviour is to close the app, unfortunately my implementation causes a loop.
                    // so the code below hardcodes the closure of the app.
                    // code from: http://stackoverflow.com/questions/2354336/android-pressing-back-button-should-exit-the-app
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }

        super.onBackPressed();
    }

    // This method allows other classes to change the activate fragment.
    // in my implementation this is only used to go to the Recipe fragment from the search fragment
    // (by the apigetter class)
    public void changeFragment(int nav, String title, String image, String link,
                               String score, ArrayList<String> ingredients, String id) {
        Fragment fragment = null;
        // nav is used to select the fragment to go to.
        switch (nav) {
            case 0:
                navItemIndex = 0;
                CURRENT_TAG = TAG_SEARCH;
                break;
            case 1:
                navItemIndex = 1;
                CURRENT_TAG = TAG_FAVO;
                break;
            case 2:
                navItemIndex = 2;
                CURRENT_TAG = TAG_RECIPE;
                fragment = RecipeFragment.newInstance(title, image, link, score, ingredients,id);
                break;
            default:
                navItemIndex = 0;
        }
        // go to the fragment chosen above, use the same effects as usual.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
