package com.example.pcdas.moviee.adapters;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pcdas.moviee.R;
import com.example.pcdas.moviee.activities.Main2Activity;
import com.example.pcdas.moviee.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends  RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context context;
    private List<Item> itemsList ;


    public MoviesAdapter(Context context , List<Item> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }


    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.itemm , parent , false);

        final MoviesViewHolder viewHolder = new MoviesViewHolder(view);
        viewHolder.pack.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent in= new Intent(context , Main2Activity.class);
                in.putExtra("original_title",itemsList.get(viewHolder.getAdapterPosition()).getName());
                in.putExtra("id",itemsList.get(viewHolder.getAdapterPosition()).getId());
                in.putExtra("overview",itemsList.get(viewHolder.getAdapterPosition()).getDescription());
                in.putExtra("release_date",itemsList.get(viewHolder.getAdapterPosition()).getrdate());
                in.putExtra("vote_average",itemsList.get(viewHolder.getAdapterPosition()).getRating());
                in.putExtra("poster_path",itemsList.get(viewHolder.getAdapterPosition()).getImage());


                context.startActivity(in);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        Picasso.with(context).load(context.getResources().getString(R.string.image_url)+itemsList.get(position).getImage()).into((holder.movieImage));

        holder.movieName.setText(itemsList.get(position).getName());
        holder.re_date.setText(itemsList.get(position).getrdate());
        holder.movieRate.setText(itemsList.get(position).getRating());
    }
    public void updateList(List<Item> newList){
        this.itemsList.clear();
        this.itemsList.addAll(newList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class MoviesViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView movieImage;
        public TextView movieName;
        public TextView re_date;
        public TextView movieRate;
        public LinearLayout pack;
        public MoviesViewHolder (View itemView)
        {
            super(itemView);
            movieImage = itemView.findViewById(R.id.iv_poster);
            movieName = itemView.findViewById(R.id.tv_title);
            movieRate = itemView.findViewById(R.id.tv_rating);
            re_date = itemView.findViewById(R.id.tv_r_date);
            pack = itemView.findViewById(R.id.packa);
        }
    }


}
