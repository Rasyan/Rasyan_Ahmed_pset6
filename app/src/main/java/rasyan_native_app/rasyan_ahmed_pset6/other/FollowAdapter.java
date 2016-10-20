package rasyan_native_app.rasyan_ahmed_pset6.other;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import rasyan_native_app.rasyan_ahmed_pset6.R;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {

    private String[] ingredients;

    public FollowAdapter(String[] ingredients){
        this.ingredients = ingredients;
        //System.out.println("adapterTEST ");
    }

    // make the viewholder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView myText;

        public ViewHolder(View itemView) {
            super(itemView);
            myText = (TextView) itemView.findViewById(R.id.text);

        }
    }
    // choose single_view as viewholder
    @Override
    public FollowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_text, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(FollowAdapter.ViewHolder vh, int i) {
        vh.myText.setText(ingredients[i]);
    }

    @Override
    public int getItemCount() {
        if (ingredients != null) {
            return ingredients.length;
        } else {
            return 0;
        }
    }
}