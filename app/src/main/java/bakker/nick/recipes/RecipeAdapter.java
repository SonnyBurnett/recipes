package bakker.nick.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    private Context context;
    private List<Recipe> values;

    public RecipeAdapter(Context context, List<Recipe> values) {
        super(context, R.layout.list_item_pagination, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_pagination, parent, false);
        }

        TextView textView = (TextView) row.findViewById(R.id.list_item_pagination_text);
        ImageView imageView = (ImageView) row.findViewById(R.id.recipe_imageView);

        Recipe item = values.get(position);
        String message = item.getTitle();
        String recUrl = item.getImageUrl();

        textView.setText(message);
        Glide.with(row).load(item.getImageUrl()).into(imageView);

        return row;
    }
}
