package com.example.pinmycar;

import android.widget.TextView;

import androidx.room.Entity;

import java.util.Date;

@Entity
public class Pin {
    TextView label;
    Date date;
    Double latitude;
    Double longitude;
    public TextView getLabel() {
        return label;
    }
    public void setLabel(TextView label) {
        this.label = label;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}