package com.plan_it.mobile.plan_it;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ViewEventActvity extends Activity{
    Bitmap bmp;

    String eTitle;
    String eDesc;
    String eLocation;
    String eFromDate;
    String eToDate;

    EditText addInvitee;
    Button addMore;
    EditText etTitle;
    EditText etDesc;
    EditText etLocation;
    EditText etFromDate;
    EditText etToDate;
    TextView tvWhoIsComing;
    Button itemList;
    Button messageBoard;
    Button btnGoing;
    Button btnNotGoing;
    Button btnLoadImg;
    ImageView eventImage;

    Calendar myCalendar = Calendar.getInstance();

    ListView attendeeList;
    String[] attendeeName = {
            "Kristian",
            "Joanne",
            "Kevin",
            "Mo",
            "Amina",
            "Luke",
            "Kamran"
    };
    Integer[] imgid = {
            R.drawable.mickey_mouse_icon,
            R.drawable.riot_fest_325,
            R.drawable.victoria_snowboard_mount_washington_small,
            R.drawable.cottage_26_waterside_248,
            R.drawable.no_image,
            R.drawable.victoria_snowboard_mount_washington_small,
            R.drawable.cottage_26_waterside_248,
    };

    Integer[] imgStatus = {
            R.drawable.ic_thumb_up_green_24dp,
            R.drawable.ic_thumb_up_green_24dp,
            R.drawable.ic_thumb_up_green_24dp,
            R.drawable.ic_thumb_up_green_24dp,
            R.drawable.ic_thumb_up_green_24dp,
            R.drawable.ic_thumb_up_green_24dp,
            R.drawable.ic_thumb_up_green_24dp
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_actvity);



        addInvitee = (EditText)findViewById(R.id.edit_addInvitee);
        addMore = (Button)findViewById(R.id.btnInviteMore);
        tvWhoIsComing = (TextView)findViewById(R.id.tvWhoIsComing);
        etTitle = (EditText)findViewById(R.id.etViewEventTitle);
        etDesc = (EditText)findViewById(R.id.etViewEventDescription);
        etLocation = (EditText)findViewById(R.id.etViewEventLocation);
        etFromDate = (EditText)findViewById(R.id.etViewEventFromDate);
        etToDate = (EditText)findViewById(R.id.etViewEventEndDate);
        itemList = (Button)findViewById(R.id.btnViewItemList);
        messageBoard = (Button)findViewById(R.id.btnViewMsgBoard);
        btnGoing = (Button)findViewById(R.id.btnAccept);
        btnNotGoing = (Button)findViewById(R.id.btnDecline);

        AttendeeListAdapter adapter = new AttendeeListAdapter(this, attendeeName, imgid, imgStatus);
        attendeeList = (ListView)findViewById(android.R.id.list);
        attendeeList.setAdapter(adapter);

        btnLoadImg = (Button)findViewById(R.id.btnChngPic);
        eventImage = (ImageView)findViewById(R.id.ivViewEventImage);

        btnLoadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });
        //isAttending();
        isOwner();
        //isInvited();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent intent)
    {
        super.onActivityResult(requestCode, resultcode, intent);

        if (requestCode == 1)
        {
            if (intent != null && resultcode == RESULT_OK)
            {

                Uri selectedImage = intent.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                if(bmp != null && !bmp.isRecycled())
                {
                    bmp = null;
                }

                bmp = BitmapFactory.decodeFile(filePath);
                eventImage.setBackgroundResource(0);
                eventImage.setImageBitmap(bmp);
            }
            else
            {
                Log.d("Status:", "Photopicker canceled");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_event_actvity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void isAttending(){
        etTitle.setEnabled(false);
        etTitle.setFocusable(false);
        etDesc.setEnabled(false);
        etDesc.setFocusable(false);
        etLocation.setEnabled(false);
        etLocation.setFocusable(false);
        etFromDate.setEnabled(false);
        etFromDate.setFocusable(false);
        etToDate.setEnabled(false);
        etToDate.setFocusable(false);

        btnLoadImg.setVisibility(View.INVISIBLE);
    }
    public void isInvited(){

        etTitle.setEnabled(false);
        etTitle.setFocusable(false);
        etDesc.setEnabled(false);
        etDesc.setFocusable(false);
        etLocation.setEnabled(false);
        etLocation.setFocusable(false);
        etFromDate.setEnabled(false);
        etFromDate.setFocusable(false);
        etToDate.setEnabled(false);
        etToDate.setFocusable(false);


        itemList.setVisibility(View.INVISIBLE);
        messageBoard.setVisibility(View.GONE);
        btnLoadImg.setVisibility(View.GONE);

        btnGoing.setVisibility(View.VISIBLE);
        btnNotGoing.setVisibility(View.VISIBLE);

    }

    public void isOwner(){
        tvWhoIsComing.setText("Invite or Uninvite People");

        addInvitee.setVisibility(View.VISIBLE);
        addMore.setVisibility(View.VISIBLE);

        eTitle = etTitle.getText().toString();
        eDesc = etDesc.getText().toString();
        eLocation = etLocation.getText().toString();
        eFromDate = etFromDate.getText().toString();
        eToDate = etToDate.getText().toString();

        etFromDate.setInputType(InputType.TYPE_NULL);
        etFromDate.setOnTouchListener(listener);

        etToDate.setInputType(InputType.TYPE_NULL);
        etToDate.setOnTouchListener(listener);


    }
    View.OnTouchListener listener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                new DatePickerDialog(ViewEventActvity.this, date, myCalendar
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

            if(etFromDate.isFocused()){
                updateLabel(0);
            }
            else if(etToDate.isFocused()){
                updateLabel(1);
            }
        }
    };

    private void updateLabel(int id) {
        String myFormat = "MMM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA );
        switch(id) {
            case 0:
                etFromDate.setText(sdf.format(myCalendar.getTime()));
                break;
            case 1:
                etToDate.setText(sdf.format(myCalendar.getTime()));
                break;
        }
    }




}
