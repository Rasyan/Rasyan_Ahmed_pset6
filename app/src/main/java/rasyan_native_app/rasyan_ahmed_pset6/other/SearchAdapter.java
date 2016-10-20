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

import rasyan_native_app.rasyan_ahmed_pset6.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private String[] images;
    private String[] names;
    private View.OnClickListener listener;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Activity activity;

    public SearchAdapter(String[] names, String[] imageURLs, final String[] ids, final Activity activity){
        this.images = imageURLs;
        this.names = names;
        this.activity = activity;
        System.out.println("adapterTEST ");

        // if an item in the list is clicked, go to its info page
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view){
                int position = ((ViewGroup) view.getParent()).indexOfChild(view);
                Apigetter getter = new Apigetter(activity);
                getter.execute("id",ids[position]);
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
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
        ViewHolder vh = new ViewHolder(view);
        context = view.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder vh, int i) {
        vh.myName.setText(names[i]);
        vh.itemView.setOnClickListener(listener);
        if (!images[i].equals("N/A")){
            imageLoader.displayImage(images[i], vh.myPhoto);
        }
    }

    @Override
    public int getItemCount() {
        if (names != null) {
            return names.length;
        } else {
            return 0;
        }
    }
}