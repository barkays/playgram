package com.example.amirah.playgram2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.amirah.playgram2.utilities.FileUtility;

import java.text.DecimalFormat;
import java.util.Calendar;

public class AddSchedule extends Activity {

    public static final String CAPTION = "caption";
    public static final String POST_TIME = "post_time";
    public static final String IMAGE_PATH = "image_path";

    private Intent returnIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        returnIntent = new Intent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TabView.SELECTED_FILE_UPLOAD_CODE && resultCode == RESULT_OK) {
            Uri selectedFileUri = data.getData(); //The uri with the location of the file
            String filePath = FileUtility.getPathFromUri(this, selectedFileUri);
            try {
                returnIntent.putExtra(IMAGE_PATH, filePath);
                Bitmap myBitmap = BitmapFactory.decodeFile(filePath);
                ImageView imagePreview = findViewById(R.id.previewImageSchedule);
                imagePreview.setImageBitmap(myBitmap);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void chooseImage(View view) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
                // TODO
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, TabView.READ_EXTERNAL_STORAGE_REQUEST_PERMISSION);

        } else {
            openFileDialog();
        }
    }

    public void openFileDialog() {
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select a file"), TabView.SELECTED_FILE_UPLOAD_CODE);
    }

    public void saveSchedule(View view) {
        EditText textScheduleCaption = findViewById(R.id.textScheduleCaption);
        returnIntent.putExtra(CAPTION, textScheduleCaption.getText().toString());
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void addPostingTime(View view) {
        int mYear, mMonth, mDay;
        final DecimalFormat mFormat = new DecimalFormat("00");
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        timePicker(Integer.toString(year), mFormat.format(monthOfYear + 1), Integer.toString(dayOfMonth));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timePicker(final String year, final String month, final String day) {
        final DecimalFormat mFormat = new DecimalFormat("00");

        final TextView postTimeTextView = findViewById(R.id.postTimeText);
        int mHour, mMinute;
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @SuppressLint({"DefaultLocale", "SimpleDateFormat"})
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String dateView = String.format(
                                "%s/%s/%s %s:%s:00", day, month, year, mFormat.format(hourOfDay),
                                mFormat.format(minute));
                        String dateSave = String.format(
                                "%s-%s-%s %s:%s:00", year, month, day, mFormat.format(hourOfDay),
                                mFormat.format(minute)
                        );
                        postTimeTextView.setText(dateView);
                        returnIntent.putExtra(POST_TIME, dateSave);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
