package com.test.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.test.GetImage;
import com.test.R;
import com.test.model.Post;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private Context context;
    private RecyclerView recyclerView;
    List<Post> posts;

    public RecyclerViewAdapter(Context context, RecyclerView recyclerView, List<Post> posts) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.posts = posts;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View items = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        return new RecyclerViewAdapter.RecyclerViewHolder(items);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        try {
            JSONObject object = new JSONObject(posts.get(position).getAuthor());
            holder.author.setText(object.getString("name"));
            object = new JSONObject(posts.get(position).getLink());
            new GetImage(holder.image, object.getString("small")).execute();

            if(posts.get(position).getDescription()!="null"){
                holder.description.setVisibility(View.VISIBLE);
                holder.description.setText(posts.get(position).getDescription());
            }
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    LayoutInflater inflater = LayoutInflater.from(context);
                    View promptsView = inflater.inflate(R.layout.post_item_dialog, null, true);
                    ImageView imageView = promptsView.findViewById(R.id.post_image_dialog);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setView(promptsView);

                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    imageView.setImageDrawable(holder.image.getDrawable());
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.cancel();
                        }
                    });
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {return posts.size();}


    public static final class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView author, description;
        ImageView image;
        CardView item;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            author = itemView.findViewById(R.id.post_author);
            description = itemView.findViewById(R.id.post_description);
            image = itemView.findViewById(R.id.post_image);
        }
    }

}
