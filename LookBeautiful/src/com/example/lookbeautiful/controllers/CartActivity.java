package com.example.lookbeautiful.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.lookbeautiful.R;

public class CartActivity extends Activity {
	int i = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_page);

		Log.d("here", "here");
		for (i = 1; i <= 3; i++)
			new PullImages().execute(
					"http://54.174.223.185/lookbeautiful/images/" + i + ".png",
					i + "s");
		String[] projection = new String[] { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
				MediaStore.Images.Media.DATE_TAKEN };

		// Get the base URI for the People table in the Contacts content
		// provider.
		Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		// Make the query.
		Cursor cur = managedQuery(images, projection, // Which columns to return
				null, // Which rows to return (all rows)
				null, // Selection arguments (none)
				null // Ordering
		);

		Log.d("ListingImages", " query count=" + cur.getCount());

		if (cur.moveToFirst()) {
			String bucket;
			String date;
			int bucketColumn = cur
					.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

			int dateColumn = cur
					.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);

			do {
				// Get the field values
				bucket = cur.getString(bucketColumn);
				date = cur.getString(dateColumn);

				// Do something with the values.
				Log.d("ListingImages", " bucket=" + bucket + "  date_taken="
						+ date);
			} while (cur.moveToNext());

		}
		((ImageButton) findViewById(R.id.imageButton1))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {

						BitmapFactory.Options o = new BitmapFactory.Options();
						o.inSampleSize = 2;
						Bitmap bmp = BitmapFactory.decodeResource(
								getResources(), R.drawable.dress1);
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
						byte[] byteArray = stream.toByteArray();

						Intent intent = new Intent(getApplicationContext(),
								TryOnActivity.class);
						intent.putExtra("picture", byteArray);
						startActivityForResult(intent, 2);
					}
				});
	}

	public class PullImages extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			Log.d("checking..", "checking..");
			// TODO Auto-generated method stub
			File root = Environment.getExternalStorageDirectory();

			File dir = new File(root.getAbsolutePath()
					+ "/DCIM/LookBeautifulServer/");
			Log.d("bgtask", dir.getAbsolutePath());
			if (dir.exists() == false) {

				dir.mkdirs();
			}
			String fName = params[1] + ".png";
			Log.d("bgtask", dir.getAbsolutePath() + fName);
			try {
				URL url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.connect();
				InputStream in = con.getInputStream();
				OutputStream out = new FileOutputStream(dir.getAbsolutePath()
						+ "/" + fName);
				byte[] data = new byte[1024];
				int count;
				while ((count = in.read(data)) != -1) {

					out.write(data, 0, count);
				}
				out.flush();
				out.close();
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}
}