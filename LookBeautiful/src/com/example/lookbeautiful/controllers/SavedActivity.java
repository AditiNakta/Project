package com.example.lookbeautiful.controllers;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.lookbeautiful.R;

public class SavedActivity extends Activity {
	Uri selectedImageUri;
	ImageView imageViewCompare1;
	ImageView imageViewCompare2;
	Bitmap bitmapCompare1;
	Bitmap bitmapCompare2;
	private String selectedImagePath;
	private String[] mFileStrings;
	private File[] listFile;
	ListView list;
	ArrayAdapter adapter;
	int i;
	Bitmap bitmap;
	ImageView image = null;

	// List<RowItem> rowItems;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saved_items);

//		String root = Environment.getExternalStorageDirectory().toString();
//		// i++;
//		String root1 = root + "/LookBeautiful/";
//		// Uri myUri = Uri.parse(root1);
//		File file = new File(root1);
//		Log.d("h", root1);
//		if (file.isDirectory()) {
//			listFile = file.listFiles();
//			mFileStrings = new String[listFile.length];
//
//			for (int i = 0; i < listFile.length; i++) {
//
//				mFileStrings[i] = listFile[i].getAbsolutePath();
//				Log.d("dsf", mFileStrings[i]);
//				// Uri myUri = Uri.parse(mFileStrings[i]);
//				bitmap = BitmapFactory.decodeFile(mFileStrings[i]);
//			}
//		}
//		LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout01);
//		for (int i = 0; i < listFile.length; i++) {
//			mFileStrings[i] = listFile[i].getAbsolutePath();
//			Log.d("dsf", mFileStrings[i]);
//			ImageView image = new ImageView(this);
//			// Uri myUri = Uri.parse(mFileStrings[i]);
//			
//			// btn.setLayoutParams(new
//			// android.view.ViewGroup.LayoutParams(80,60));
//			image.setMaxHeight(20);
//			image.setMaxWidth(20);
//			layout.addView(image);
//			bitmap = BitmapFactory.decodeFile(mFileStrings[i]);
//
//			image.setImageBitmap(bitmap);
//			bitmap.recycle();
//			
//		}
////		list = (ListView) findViewById(R.id.list);
////		ArrayAdapter<Bitmap> myAdapter = new ArrayAdapter<Bitmap>(this,
////				android.R.layout.simple_list_item_1);
////		ImageView imageView2 = (ImageView) findViewById(R.id.image);
////		imageView2.setImageBitmap(bitmap);
////		// adapter = new ArrayAdapter(this, mFileStrings);
////		list.setAdapter(myAdapter);
//		// list.setAdapter(adapter);

	}

	public void share(View view) {

		((Button) findViewById(R.id.share))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent shareIntent = new Intent();
						shareIntent.setAction(Intent.ACTION_SEND);
						String root = Environment.getExternalStorageDirectory()
								.toString();
						String root1 = root + "/folder/subfolder/image.jpg";
						Uri myUri = Uri.parse(root1);
						// Log.d("string",root);
						shareIntent.putExtra(Intent.EXTRA_STREAM, myUri);

						shareIntent.setType("image/jpeg");
						startActivity(Intent
								.createChooser(shareIntent, "share"));
					}
				});
	}

	public void compare1(View view) {

		setContentView(R.layout.compare_page);
		((Button) findViewById(R.id.selectImage1))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {

						Intent i = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(i, 4);
					}

				});
		((Button) findViewById(R.id.selectImage2))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {

						Intent i = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

						startActivityForResult(i, 5);
					}

				});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 4) {
				Uri selectedImageUri = data.getData();
				Log.d("Printing buid sdk ",
						String.valueOf(Build.VERSION.SDK_INT));
				Log.d("printing uri", String.valueOf(selectedImageUri));
				selectedImagePath = getPath(selectedImageUri);
				Log.d("print", String.valueOf(selectedImagePath));
				bitmapCompare1 = BitmapFactory.decodeFile(selectedImagePath);
				imageViewCompare1 = (ImageView) findViewById(R.id.imageViewCompare1);
				imageViewCompare1.setImageBitmap(bitmapCompare1);

			} else if (requestCode == 5) {
				Uri selectedImageUri = data.getData();
				Log.d("Printing buid sdk ",
						String.valueOf(Build.VERSION.SDK_INT));
				Log.d("printing uri", String.valueOf(selectedImageUri));
				selectedImagePath = getPath(selectedImageUri);
				Log.d("print", String.valueOf(selectedImagePath));
				bitmapCompare2 = BitmapFactory.decodeFile(selectedImagePath);
				imageViewCompare2 = (ImageView) findViewById(R.id.imageViewCompare2);
				imageViewCompare2.setImageBitmap(bitmapCompare2);

			}
		}
	}

	public String getPath(Uri uri) {
		if (uri == null) {
			return null;
		}
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(uri, projection, null, null,
				null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		return uri.getPath();
	}

}
