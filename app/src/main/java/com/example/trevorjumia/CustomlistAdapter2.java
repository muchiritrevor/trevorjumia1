package com.example.trevorjumia;



import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

public class CustomlistAdapter2 extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Movie> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomlistAdapter2(Activity activity, List<Movie> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_raw1, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);
        TextView descripion=convertView.findViewById(R.id.descriptiontxt);

        // getting movie data for the row
        Movie m = movieItems.get(position);

        String urlimage="http://192.168.60.188/www/html/trevor/";
        String suburl="upload/images/932222365_1576826998.jpeg";
        String Combined=urlimage.concat(m.getThumbnailUrl());

        Picasso.get().load(Combined).error(R.mipmap.ic_launcher).into(thumbNail);


        // thumbnail image
        // thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
     //   title.setText("NAME: "+m.getTitle());

        // rating
        rating.setText("NAME: " + String.valueOf(m.getTitle()));

        // genre
       // genre.setText("PRICE PER PRODUCT: " +m.getPrice());
        descripion.setText("RETAIL PRICES: "+m.getdescription());

        // release year
      //  year.setText(String.valueOf("QUANTITY :"+m.getYear()));

        return convertView;
    }

}
