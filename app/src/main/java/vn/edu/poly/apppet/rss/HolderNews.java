package vn.edu.poly.apppet.rss;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.edu.poly.apppet.R;

public class HolderNews extends RecyclerView.ViewHolder {

    public final ImageView imgThumbs;
    public final TextView tvTitle;
    public final TextView tvDes;
    public final TextView tvDate;

    public HolderNews(View itemView) {
        super (itemView);
        imgThumbs = itemView.findViewById (R.id.imgThumbs);
        tvTitle = itemView.findViewById (R.id.tvTitle);
        tvDes = itemView.findViewById (R.id.tvDes);
        tvDate = itemView.findViewById (R.id.tvDate);
    }
}
