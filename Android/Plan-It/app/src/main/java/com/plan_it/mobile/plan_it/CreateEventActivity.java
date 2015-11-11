package com.plan_it.mobile.plan_it;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CreateEventActivity extends AppCompatActivity {

    String base64Image;
    byte[] imageByte;

    String e_name;
    String e_reason;
    String e_loc;
    String e_fromdate;
    String e_todate;
    String e_fromTime;
    String e_toTime;
    EditText create_fromdate;
    EditText create_todate;
    Calendar myCalendar = Calendar.getInstance();

    ImageView viewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        e_fromTime = "12:00 PM";
        e_toTime = "5:00 PM";

        ImageButton submit = (ImageButton) findViewById(R.id.add_event);
        ImageButton clear = (ImageButton) findViewById(R.id.clear_event);
        final EditText event_name = (EditText) findViewById((R.id.event_name));
        final EditText event_reason = (EditText) findViewById((R.id.event_reason));
        final EditText event_location = (EditText) findViewById((R.id.event_location));
        create_fromdate = (EditText) findViewById(R.id.event_createfromdate);
        create_todate = (EditText) findViewById(R.id.event_createtodate);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e_name = event_name.getText().toString();
                e_reason = event_reason.getText().toString();
                e_loc = event_location.getText().toString();
                e_fromdate = create_fromdate.getText().toString();
                e_todate = create_todate.getText().toString();

                if (e_name.equals("") || e_reason.equals("") || e_loc.equals("") || e_fromdate.equals("") || e_todate.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please fill in all fields!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    try {
                        createEvent();
                    }
                    catch (JSONException ex){
                        ex.printStackTrace();
                    }
                    Intent myIntent = new Intent(CreateEventActivity.this, EventsListActivity.class);
                    startActivity(myIntent);
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event_name.setText("");
                event_reason.setText("");
                event_location.setText("");
                create_fromdate.setText("");
                create_todate.setText("");
            }
        });

        create_fromdate.setInputType(InputType.TYPE_NULL);
        create_fromdate.setOnTouchListener(listener);

        create_todate.setInputType(InputType.TYPE_NULL);
        create_todate.setOnTouchListener(listener);
    }

    OnTouchListener listener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                new DatePickerDialog(CreateEventActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
            return false;
        }
    };
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            if (create_fromdate.isFocused()) {
                updateLabel(0);
            } else if (create_todate.isFocused()) {
                updateLabel(1);
            }
        }
    };

    private void updateLabel(int id) {
        String myFormat = "MMM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);
        switch (id) {
            case 0:
                create_fromdate.setText(sdf.format(myCalendar.getTime()));
                break;
            case 1:
                create_todate.setText(sdf.format(myCalendar.getTime()));
                break;
        }
    }

    public void imageOptions(View c) {
        viewImage = (ImageView) findViewById(R.id.create_eventphoto);
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            1);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 10, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageByte = bytes.toByteArray();
                base64Image = Base64.encodeToString(imageByte, Base64.NO_WRAP);
                viewImage.setImageBitmap(thumbnail);
            } else if (requestCode == 1) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 10,bytes);
                imageByte = bytes.toByteArray();
                base64Image = Base64.encodeToString(imageByte, Base64.NO_WRAP);
                viewImage.setImageBitmap(bm);


            }
        }
    }

    public void createEvent() throws JSONException {
        RequestParams jdata = new RequestParams();
        jdata.put("what",e_name);
        jdata.put("why", e_reason);
        jdata.put("where", e_loc);
        jdata.put("when",e_fromdate);
        jdata.put("endDate", e_todate);
        jdata.put("fromTime",e_fromTime);
        jdata.put("toTime", e_toTime);
        jdata.put("picture", base64Image);
        RestClient.post("events",jdata, new JsonHttpResponseHandler() {
            public void onSuccess(String response) {
                JSONObject res;
                try {
                    res = new JSONObject(response);
                    Log.d("debug", res.getString("some_key")); // this is how you get a value out
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
