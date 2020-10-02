package com.example.zooapp.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.zooapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class ZooLocaliza extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoo_localiza);

        com.example.epicapi.activities.Models.Permissoes.validarPermissoes(permissoes, this, 1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                final LatLng zoomaps = new LatLng(-23.650989,-46.6200225);
                final LatLng vilamariana = new LatLng(-23.585141, -46.633509);


                // Add a marker in Sydney and move the camera
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                mMap.clear();
                LatLng localusuario = new LatLng(latitude, longitude);

                mMap.addMarker(new MarkerOptions().position(localusuario).title("Seu Local")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.peoplejune)));
                mMap.addMarker(new MarkerOptions().position(zoomaps).title("Centro Espacial John F. Kennedy")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.z00)));
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(localusuario);
                polylineOptions.add(zoomaps);
                polylineOptions.color(Color.GREEN);
                polylineOptions.width(3);
                mMap.addPolyline(polylineOptions);
                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(localusuario);
                circleOptions.radius(12000000);//meters
                circleOptions.strokeWidth(5);
                circleOptions.strokeColor(Color.CYAN);
                circleOptions.fillColor(Color.argb(115,153, 153, 255));//0 a 255 opacidade!
                mMap.addCircle(circleOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vilamariana, 12));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000,
                    0,
                    locationListener
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int permissaoResultado : grantResults) {
            if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                //alerta
                alertaValidacaoPermissao();
            } else if (permissaoResultado == PackageManager.PERMISSION_GRANTED) {
                //RECUPERAR LOCALIZAÇÃO DO USUARIO
                //1- Provedor de localização
                //2 - Tempo mínimo entre atualizações de localização (milesegundos)
                //3 - Distancia mínima entre atualizações de localização (metros)
                //4 - Location Listener (para recebermos as atualizações)
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0,
                            0,
                            locationListener
                    );
                }
            }
        }
    }
    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}