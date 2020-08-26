package com.example.pinmycar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private int locationRequestCode = 1000;
    private double latitude = 0.0, longitude = 0.0;

    private TextView txtLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtLocation = (TextView) findViewById(R.id.txtLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);
        } else {
            // already permission granted
        }


        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            txtLocation.setText(String.format(Locale.FRANCE, "%s , %s", latitude, longitude));
                        } else {
                            txtLocation.setText(String.format("fail"));
                        }
                    }
                });




    };




}



@Database(entities = {Pin.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {
    public abstract PinDao pinDao();

    @Entity
    public class Pin {
        @PrimaryKey
        public int pinid;

        @ColumnInfo(name = "label")
        public String label;

        @ColumnInfo(name = "date")
        public Date date;

        @ColumnInfo(name = "latitude")
        public Double latitude;

        @ColumnInfo(name = "longitude")
        public Double longitude;

    };

    @Dao
    public interface PinDao {
        @Query("SELECT * FROM pin")
        List<Pin> getAll();

        @Insert
        void insertAll(Pin... pins);

        @Update
        void update(Pin pin);

        @Delete
        void delete(Pin pin);
    };

    protected abstract Context getApplicationContext();
    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
            AppDatabase.class, "database-name").build();



};



