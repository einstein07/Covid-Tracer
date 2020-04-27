package com.example.covidtracer;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class NewJourneyActivity extends AppCompatActivity {

    int PERMISSION_ID = 44;
    int AUTOCOMPLETE_REQUEST_CODE = 1;

    FusedLocationProviderClient mFusedLocationClient;

    private EditText date, time, destination, txt_start;
    private  String entered_time, entered_date, entered_dest;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_journey);

        txt_start = findViewById(R.id.start);
        date = (EditText) findViewById(R.id.date);
        destination = (EditText) findViewById(R.id.destination);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewJourneyActivity.this,R.style.Theme_AppCompat_DayNight_Dialog_MinWidth,onDateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                entered_date = date.getText().toString();
            }
        };


        // Get Current Time
        time = (EditText) findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMin = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewJourneyActivity.this,onTimeSetListener,mHour,mMin, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                timePickerDialog.show();
            }
        });

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.setText(hourOfDay +":"+ minute);
                entered_time = time.getText().toString();
            }

        };

        getLastLocation();
    }


    public void setDestination(View view) {
        String apiKey = getString(R.string.api_key);
        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), apiKey);
        }
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == AUTOCOMPLETE_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Place place = Autocomplete.getPlaceFromIntent(data);
                destination.setText(place.getName() +", "+ place.getId());
            }
            else if (resultCode == AutocompleteActivity.RESULT_ERROR){
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("Place Auto Complete", status.getStatusMessage());
                Toast.makeText(this, "An unexpected error occurred: "+status.getStatusMessage(), Toast.LENGTH_SHORT).show();

            }
            else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Action calncelled by user.", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    public void save(View view) {
        Toast.makeText(this, "New journey created successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Granted. Start getting the location information
            }
        }
    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    txt_start.setText(location.getLatitude()+"\n"+location.getLongitude()+"");
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }


    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            txt_start.setText("Lat: "+mLastLocation.getLatitude()+"\nLong: "+mLastLocation.getLongitude()+"");
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }

}





