package gsd.multazam.cataloguemovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import gsd.multazam.cataloguemovie.R;
import gsd.multazam.cataloguemovie.model.Movie;

/**
 * Created by rehan on 21/02/18.
 */

public class MovieAdapter extends BaseAdapter {
    private ImageView poster;
    private ArrayList<Movie> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Movie> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(final Movie item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    @Override
    public Movie getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.movie_row, null);
            holder.tvTitle = convertView.findViewById(R.id.textViewName);
            holder.ivPoster = convertView.findViewById(R.id.imageView);
            holder.tvDesc = convertView.findViewById(R.id.textViewDesc);
            holder.tvDate = convertView.findViewById(R.id.textViewDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load("https://image.tmdb.org/t/p/w185" + mData.get(position).getPoster())
                .into(holder.ivPoster);
        holder.tvTitle.setText(mData.get(position).getTitle());
        holder.tvDesc.setText(mData.get(position).getOverview());
        holder.tvDate.setText(mData.get(position).getRelease_date());
        return convertView;
    }

    public static class ViewHolder {
        TextView tvTitle;
        ImageView ivPoster;
        TextView tvDesc;
        TextView tvDate;
    }
}
