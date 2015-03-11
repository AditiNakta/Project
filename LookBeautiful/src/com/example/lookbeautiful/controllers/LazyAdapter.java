package com.example.lookbeautiful.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.example.lookbeautiful.R;

import com.example.lookbeautiful.controllers.FileCache;
import com.example.lookbeautiful.controllers.ImageLoader;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    public String[] data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    public ImageLoader file;
    public FileCache cachedir;
    
    public LazyAdapter(Activity a, String[] d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        
        if(convertView==null)
            vi = inflater.inflate(R.layout.saved_items, null);

        TextView text=(TextView)vi.findViewById(R.id.text);;
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        text.setText("item "+position);
        imageLoader.DisplayImage(data[position], image);
        text.setClickable(true);
        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	
            	
            	ImageLoader img = new ImageLoader(activity);
            	Bitmap b = null;
            	String root = Environment.getExternalStorageDirectory()
						.toString();
				String root1 = root + "/LookBeautiful/";
				Uri myUri = Uri.parse(root1);
				img.getBitmap(myUri.toString());
				
				
				Bundle extras = new Bundle();

				Intent intent = new Intent(v.getContext(),
						TryOnActivity.class);
				extras.putParcelable("image1", b);
			       intent.putExtras(extras);
				activity.startActivity(intent);

            }
        });
        return vi;
    }
}