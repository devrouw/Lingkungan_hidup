package com.client.lingkungan_hidup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailLokasiActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Double longitude, latitude;
    private GoogleMap mMap;
    private TextView txtNama, txtAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lokasi);

        txtNama = (TextView)findViewById(R.id.txtNamaLokasi);
        txtAlamat = (TextView)findViewById(R.id.txtAlamatLokasi);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapLokasi);
        mapFragment.getMapAsync(this);
        String strNama = getIntent().getStringExtra("nama_lokasi");
        String strAlamat = getIntent().getStringExtra("alamat");
        String strLatitude = getIntent().getStringExtra("latitude");
        String strLongitude = getIntent().getStringExtra("longitude");

        latitude = Double.parseDouble(strLatitude);
        longitude = Double.parseDouble(strLongitude);

        txtAlamat.setText(strAlamat);
        txtNama.setText(strNama);

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(location).title("Your Loc"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

        //Create CameraPosition
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)
                .tilt(0)
                .zoom(12)
                .bearing(0)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}