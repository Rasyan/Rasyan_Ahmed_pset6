package rasyan_native_app.rasyan_ahmed_pset6.other;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import rasyan_native_app.rasyan_ahmed_pset6.R;

/***
 * An adapter class that fills the recyclerlists in search and favorites fragments.
 * It shows an image and a text view.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> images;
    private ArrayList<String> names;
    private ArrayList<String> ids;
    private View.OnClickListener listener;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Activity activity;

    // constructor
    public ImageAdapter(ArrayList<String> titles, ArrayList<String> imageURLs, ArrayList<String> id, Activity act){
        images = imageURLs;
        names = titles;
        ids = id;
        activity = act;

        // if an item in the list is clicked, go to its info page
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view){
                int position = ((ViewGroup) view.getParent()).indexOfChild(view);
                Apigetter getter = new Apigetter(activity);
                getter.execute("id",ids.get(position));
            }
        };
    }
    // make the viewholder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView myName;
        public ImageView myPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            myName = (TextView) itemView.findViewById(R.id.name);
            myPhoto = (ImageView) itemView.findViewById(R.id.photo);

        }
    }
    // choose single_view as viewholder
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
        ViewHolder vh = new ViewHolder(view);
        context = view.getContext();
        return vh;
    }

    // set the text and listener for this view, if an image is provided, update that image
    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder vh, int i) {
        vh.myName.setText(names.get(i));
        vh.itemView.setOnClickListener(listener);
        if (!images.get(i).equals("N/A")){
            imageLoader.displayImage(images.get(i), vh.myPhoto);
        }
    }


    @Override
    public int getItemCount() {
        if (names != null) {
            return names.size();
        } else {
            return 0;
        }
    }

    // this method is called when the recyclerview needs to be updated with new data. this is only done in the search fragment
    public void swap(ArrayList<String> names, ArrayList<String> imageURLs, ArrayList<String> id) {
        this.images = imageURLs;
        this.names = names;
        this.ids = id;
        notifyDataSetChanged();
    }
}