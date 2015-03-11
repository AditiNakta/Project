package com.example.lookbeautiful.controllers;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lookbeautiful.R;

public class DisplayActivity extends Activity {
	private static int RESULT_LOAD_IMAGE = 1;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private Uri fileUri;
	private static final int SELECT_PICTURE = 1;
	private String selectedImagePath;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_image);

	}

	public void picture(View view) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to
															// save the image
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
															// name
		// setResult(Activity.RESULT_OK,intent);
		// start the image capture Intent
		startActivityForResult(intent, 1);
	}

	private Uri getOutputMediaFileUri(int mediaTypeImage) {
		return null;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				Log.d("Printing buid sdk ",
						String.valueOf(Build.VERSION.SDK_INT));
				Log.d("printing uri", String.valueOf(selectedImageUri));
				selectedImagePath = getPath(selectedImageUri);
				Log.d("print", String.valueOf(selectedImagePath));
				Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
				ImageView imageView = (ImageView) findViewById(R.id.imageView2);
				imageView.setImageBitmap(bitmap);

			}
		}
	}

	/**
	 * helper to retrieve the path of an image URI
	 */
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
