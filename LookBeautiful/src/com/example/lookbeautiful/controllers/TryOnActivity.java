package com.example.lookbeautiful.controllers;

import java.io.File;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lookbeautiful.R;

public class TryOnActivity extends Activity implements OnTouchListener {
	// private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private Uri fileUri;
	private static final int SELECT_PICTURE = 1;
	protected static final String imagePath = null;
	private String selectedImagePath;
	// private ImageView imageView;
	private static int RESULT_LOAD_IMAGE = 1;
	TextView myTouchEvent;
	int bmpWidth, bmpHeight;
	private float mPrevX;
	private float mPrevY;
	Bitmap bitmap;
	Bitmap bitmap1;
	// Touch event related variables
	int touchState;
	final int IDLE = 0;
	final int TOUCH = 1;
	final int PINCH = 2;
	float dist0, distCurrent;
	ImageView imageView;
	ImageView imageView1;
	ImageView imageView2;
	// ImageButton image1;
	private int _xDelta;
	private int _yDelta;
	int state = 0;
	Uri data1;
	int check;
	Bitmap bitmap2;
	Bitmap resizedBitmap;
	Bitmap resizedBitmap1;
	Bitmap resizedImage1;
	Bitmap resizedImage2;
	Bitmap result;
	Uri selectedImageUri;
	ImageView imageViewCompare1;
	ImageView imageViewCompare2;
	Bitmap bitmapCompare1;
	Bitmap bitmapCompare2;
	private File cacheDir;
	int i = 0;

	public void FileCache(Context context) {
		File cacheDir;
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"LookBeautiful");
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.try_on);

		((Button) findViewById(R.id.gallery))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						Intent i = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

						startActivityForResult(i, RESULT_LOAD_IMAGE);
					}
				});

	}

	public void add(View view) {
		((Button) findViewById(R.id.add))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						if (state == 0) {
							Intent i = new Intent(
									Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

							startActivityForResult(i, 2);
							state++;
						}

						else if (state == 1) {
							Intent i = new Intent(
									Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

							startActivityForResult(i, 3);
							state++;
						}

					}
				});
	}

	public void picture(View view) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		startActivityForResult(intent, 1);
	}

	public void delete(View view) {

		((Button) findViewById(R.id.delete))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (state == 2) {

							imageView2.setImageBitmap(null);
							state--;
						} else if (state == 1) {
							imageView1.setImageBitmap(null);
							state--;
						}
					}
				});
	}

	public Bitmap screenShot(View view) {
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		return bitmap;
	}

	public void saveImage(View view) {
		File filename;

		View v = findViewById(R.id.fr1);
		result = screenShot(v);
		try {

			String path = Environment.getExternalStorageDirectory().toString();
			i++;
			new File(path + "/LookBeautiful").mkdirs();
			filename = new File(path + "/LookBeautiful/image" + i + ".jpg");

			FileOutputStream out = new FileOutputStream(filename);

			result.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();

			MediaStore.Images.Media.insertImage(getContentResolver(),
					filename.getAbsolutePath(), filename.getName(),
					filename.getName());

			Toast.makeText(getApplicationContext(),
					"File is Saved in  " + filename, 1000).show();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
						String root1 = root + "/LookBeautiful/image" + i
								+ ".jpg";
						Uri myUri = Uri.parse(root1);
						// Log.d("string",root);
						shareIntent.putExtra(Intent.EXTRA_STREAM, myUri);

						shareIntent.setType("image/jpeg");
						startActivity(Intent
								.createChooser(shareIntent, "share"));
					}
				});
	}

	public void compare(View view) {

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

	private Uri getOutputMediaFileUri(int mediaTypeImage) {
		return null;
	}

	/**
	 * Create a File for saving an image or video
	 * 
	 * @return
	 */

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				setContentView(R.layout.display_image);
				Uri selectedImageUri = data.getData();
				Log.d("Printing buid sdk ",
						String.valueOf(Build.VERSION.SDK_INT));
				Log.d("printing uri", String.valueOf(selectedImageUri));
				selectedImagePath = getPath(selectedImageUri);
				Log.d("print", String.valueOf(selectedImagePath));
				bitmap2 = BitmapFactory.decodeFile(selectedImagePath);
				imageView = (ImageView) findViewById(R.id.imageView1);
				imageView.setImageBitmap(bitmap2);

			} else if (requestCode == 2) {

				selectedImageUri = data.getData();
				data1 = data.getData();
				Log.d("Printing buid sdk ",
						String.valueOf(Build.VERSION.SDK_INT));
				Log.d("printing uri", String.valueOf(selectedImageUri));
				selectedImagePath = getPath(selectedImageUri);
				Log.d("print", String.valueOf(selectedImagePath));
				bitmap = BitmapFactory.decodeFile(selectedImagePath);
				imageView1 = (ImageView) findViewById(R.id.imageView2);
				myTouchEvent = (TextView) findViewById(R.id.touchevent);
				imageView1.setImageBitmap(bitmap);
				bmpWidth = bitmap.getWidth();
				bmpHeight = bitmap.getHeight();

				distCurrent = 1; // Dummy default distance
				dist0 = 1; // Dummy default distance
				drawMatrix();
				// check = 0;
				imageView1.setOnTouchListener(MyOnTouchListener);

				// state++;
				touchState = IDLE;
			} else if (requestCode == 3) {

				Uri selectedImageUri = data.getData();
				Log.d("Printing buid sdk ",
						String.valueOf(Build.VERSION.SDK_INT));
				Log.d("printing uri", String.valueOf(selectedImageUri));
				selectedImagePath = getPath(selectedImageUri);
				Log.d("print", String.valueOf(selectedImagePath));
				bitmap1 = BitmapFactory.decodeFile(selectedImagePath);
				imageView2 = (ImageView) findViewById(R.id.imageView3);
				// myTouchEvent = (TextView) findViewById(R.id.touchevent);
				imageView2.setImageBitmap(bitmap1);
				bmpWidth = bitmap1.getWidth();
				bmpHeight = bitmap1.getHeight();

				distCurrent = 1; // Dummy default distance
				dist0 = 1; // Dummy default distance
				drawMatrix1();
				// check = 1;
				imageView2.setOnTouchListener(MyOnTouchListener);

				touchState = IDLE;
			} else if (requestCode == 4) {
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

	private void drawMatrix() {
		float curScale = distCurrent / dist0;
		if (curScale < 0.1) {
			curScale = 0.1f;
		}

		int newHeight = (int) (bmpHeight * curScale);
		int newWidth = (int) (bmpWidth * curScale);
		resizedBitmap1 = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight,
				false);

		imageView1.setImageBitmap(resizedBitmap1);
		resizedImage1 = ((BitmapDrawable) imageView1.getDrawable()).getBitmap();
	}

	private void drawMatrix1() {
		float curScale = distCurrent / dist0;
		if (curScale < 0.1) {
			curScale = 0.1f;
		}

		int newHeight = (int) (bmpHeight * curScale);
		int newWidth = (int) (bmpWidth * curScale);
		resizedBitmap = Bitmap.createScaledBitmap(bitmap1, newWidth, newHeight,
				false);
		imageView2.setImageBitmap(resizedBitmap);
		resizedImage2 = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();
	}

	OnTouchListener MyOnTouchListener = new OnTouchListener() {

		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// TODO Auto-generated method stub
			final int X = (int) event.getRawX();
			final int Y = (int) event.getRawY();
			float distx, disty;
			float currX, currY;
			int action = event.getAction();
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN: {

				if (state == 1) {
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view
							.getLayoutParams();
					_xDelta = (int) (X - imageView1.getTranslationX());
					_yDelta = (int) (Y - imageView1.getTranslationY());
					touchState = TOUCH;
				}
				if (state == 2) {
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view
							.getLayoutParams();

					_xDelta = (int) (X - imageView2.getTranslationX());
					_yDelta = (int) (Y - imageView2.getTranslationY());
					touchState = TOUCH;
				}
				break;
			}
			case MotionEvent.ACTION_POINTER_DOWN:

				touchState = PINCH;

				// Get the distance when the second pointer touch
				distx = event.getX(0) - event.getX(1);
				disty = event.getY(0) - event.getY(1);
				dist0 = FloatMath.sqrt(distx * distx + disty * disty);

				break;
			case MotionEvent.ACTION_MOVE:

				currX = event.getRawX();
				currY = event.getRawY();

				if (touchState == PINCH) {

					distx = event.getX(0) - event.getX(1);
					disty = event.getY(0) - event.getY(1);
					distCurrent = FloatMath.sqrt(distx * distx + disty * disty);
					if (state == 1) {
						drawMatrix();
					} else if (state == 2) {
						drawMatrix1();
					}
				} else {

					if (state == 1) {
						RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
								.getLayoutParams();
						imageView1.setTranslationX(X - _xDelta);
						imageView1.setTranslationY(Y - _yDelta);

					} else if (state == 2) {
						RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
								.getLayoutParams();
						imageView2.setTranslationX(X - _xDelta);
						imageView2.setTranslationY(Y - _yDelta);

					}
					break;
				}

				break;
			case MotionEvent.ACTION_UP:
				touchState = IDLE;
				break;
			case MotionEvent.ACTION_POINTER_UP:
				touchState = TOUCH;
				break;
			}

			return true;
		}

	};

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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}