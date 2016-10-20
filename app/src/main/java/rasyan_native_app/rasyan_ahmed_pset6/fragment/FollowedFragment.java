package rasyan_native_app.rasyan_ahmed_pset6.fragment;

import android.content.Context;
import android.net.Uri;
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

import rasyan_native_app.rasyan_ahmed_pset6.R;
import rasyan_native_app.rasyan_ahmed_pset6.activity.Home;
import rasyan_native_app.rasyan_ahmed_pset6.other.Apigetter;
import rasyan_native_app.rasyan_ahmed_pset6.other.FollowAdapter;
import rasyan_native_app.rasyan_ahmed_pset6.other.SearchAdapter;

import static rasyan_native_app.rasyan_ahmed_pset6.R.id.fab;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link FollowedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FollowedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    // TODO: Rename and change types of parameters
    private String title;
    private String image;
    private String link;
    private String score;
    private String[] ingredients;
    private RecyclerView mRecyclerView;
    public FollowAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab;

//    private OnFragmentInteractionListener mListener;

    public FollowedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FollowedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FollowedFragment newInstance(String param1, String param2, String param3, String param4, String[] param5) {
        FollowedFragment fragment = new FollowedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putStringArray(ARG_PARAM5, param5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            image = getArguments().getString(ARG_PARAM2);
            link = getArguments().getString(ARG_PARAM3);
            score = getArguments().getString(ARG_PARAM4);
            ingredients = getArguments().getStringArray(ARG_PARAM5);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        TextView textView = (TextView) view.findViewById(R.id.name);
        textView.setText(title);
        TextView scoreView = (TextView) view.findViewById(R.id.score);
        scoreView.setText("Score: " + score);
        TextView linkView = (TextView) view.findViewById(R.id.link);
        linkView.setText(link);
        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get dialog fragment to ask you whatsup son.to what list do you wanna add this?
            }
        });
    }

    private void setUpRecyclerView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.list);

        // this line below adds a divider between the recyclerView items,
        // requires a dependency.
        // source : https://github.com/yqritc/RecyclerView-FlexibleDivider
        //mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(Home.context).build());


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(Home.context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // use the adapter
        mAdapter = new FollowAdapter(ingredients);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followed, container, false);
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
