package gsd.multazam.favoritemovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import gsd.multazam.favoritemovie.R;
import gsd.multazam.favoritemovie.model.Favorite;

/**
 * Created by rehan on 25/02/18.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w500";
    Cursor list;
    Context context;
    IFavoAdapter mIFavoAdapter;

    public FavoriteAdapter(Context context, Cursor list, IFavoAdapter iFavo) {
        this.list = list;
        this.context = context;
        this.mIFavoAdapter = iFavo;
    }

    public void setListNotes(Cursor list) {
        this.list = list;
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
        final Favorite fav = getItem(position);
        holder.tvName.setText(fav.getTitle());
        holder.tvDesc.setText(fav.getOverview());
        holder.tvDate.setText(fav.getRelease_date());
        Glide.with(context)
                .load(IMAGE_URL_BASE_PATH + fav.getPoster())
                .into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.getCount();
    }

    public Favorite getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favorite(list);
    }

    public interface IFavoAdapter {
        void doClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvName;
        TextView tvDesc, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textViewName);
            tvDesc = itemView.findViewById(R.id.textViewDesc);
            tvDate = itemView.findViewById(R.id.textViewDate);
            ivPoster = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIFavoAdapter.doClick(getAdapterPosition());
                }
            });
        }
    }
}
