package rasyan_native_app.rasyan_ahmed_pset6.other;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rasyan_native_app.rasyan_ahmed_pset6.R;

/***
 * An adapter class that fills the recyclerlists in the Recipe fragment.
 * it shows a list of ingredient names
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private ArrayList<String> ingredients;

    // constructor
    public IngredientsAdapter(ArrayList<String> ingredients){
        this.ingredients = ingredients;
    }

    // make the viewholder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView myText;

        public ViewHolder(View itemView) {
            super(itemView);
            myText = (TextView) itemView.findViewById(R.id.text);

        }
    }
    // choose single_text as viewholder
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_text, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // set the text
    @Override
    public void onBindViewHolder(IngredientsAdapter.ViewHolder vh, int i) {
        vh.myText.setText(ingredients.get(i));
    }

    @Override
    public int getItemCount() {
        if (ingredients != null) {
            return ingredients.size();
        } else {
            return 0;
        }
    }
}