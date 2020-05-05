package com.example.medtonulfem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

class CustomAdapter extends BaseAdapter
{
    Context context;
    ArrayList<User> users;

    public CustomAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.scoreboard_single_row, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView pointsTextView = convertView.findViewById(R.id.pointsTextView);
        ImageView photourlImageView = convertView.findViewById(R.id.scoreboardImage);

        final User s = (User) this.getItem(position);

        nameTextView.setText(s.getName());
        pointsTextView.setText(s.getPoints().toString());
        //photourlTextView.setText(s.getPhotourl());
        String url = s.getPhotourl();

        Picasso.get().load(url).transform(new CropCircleTransformation()).into(photourlImageView);

 /*
  String photoUrl = user.getPhotoUrl().toString();

        photoUrl = photoUrl + "?height=500";

        Picasso.get().load(photoUrl)
                .transform(new CropCircleTransformation()).into(profilePicture)


 //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, s.getName(), Toast.LENGTH_SHORT).show();
            }
        });*/
        return convertView;
    }
}
