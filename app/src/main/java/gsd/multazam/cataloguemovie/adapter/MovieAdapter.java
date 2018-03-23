package gsd.multazam.cataloguemovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import gsd.multazam.cataloguemovie.R;
import gsd.multazam.cataloguemovie.model.Movie;

/**
 * Created by rehan on 25/02/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w500";
    ArrayList<Movie> list;
    Context context;
    IMovieAdapter iMovieAdapter;

    public MovieAdapter(Context context, ArrayList<Movie> list, IMovieAdapter iMovieAdapter) {
        this.list = list;
        this.context = context;
        this.iMovieAdapter = iMovieAdapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Movie mov = list.get(position);
        holder.tvName.setText(mov.getTitle());
        holder.tvDesc.setText(mov.getOverview());
        holder.tvDate.setText(mov.getRelease_date());
        Glide.with(context)
                .load(IMAGE_URL_BASE_PATH + mov.getPoster())
                .into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public interface IMovieAdapter {
        void doClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvName;
        TextView tvDesc, tvDate;
        ImageView ivFav;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textViewName);
            tvDesc = itemView.findViewById(R.id.textViewDesc);
            tvDate = itemView.findViewById(R.id.textViewDate);
            ivPoster = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iMovieAdapter.doClick(getAdapterPosition());
                }
            });
        }
    }
}
