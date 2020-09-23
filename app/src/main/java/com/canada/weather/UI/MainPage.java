package com.canada.weather.UI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.canada.weather.R;
import com.canada.weather.Service.ApiClient;
import com.canada.weather.Service.ApiService;
import com.canada.weather.Utils.Constants;
import com.canada.weather.model.currentweather.CurrentWeatherResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class MainPage extends Fragment implements OnMapReadyCallback {

    private Location currentLocation;
    private String current_cityname;
    private TextView current_temp, current_date, current_status;
    EditText search_text;
    private ImageView current_status_image;
    String cur_lat, cur_lon;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 200;
    private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main_page, container, false);

        current_temp = root.findViewById(R.id.temperature);
        current_date = root.findViewById(R.id.current_date);
        current_status = root.findViewById(R.id.weather_text);
        search_text = root.findViewById(R.id.et_search_text);
        apiService = ApiClient.getClient().create(ApiService.class);

        // Get Current City name
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fetchLastLocation();

        return root;
    }

    private void init(String lat, String lon) {
        disposable.add(
                apiService.getCurrentWeather(
                        lat, lon, Constants.UNITS_C, Constants.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CurrentWeatherResponse>() {
                            @Override
                            public void onSuccess(CurrentWeatherResponse currentWeatherResponse) {
                                current_temp.setText(String.valueOf((int) Math.floor(currentWeatherResponse.getMain().getTemp())) + "°");

                                SimpleDateFormat sdf = new SimpleDateFormat("dd. MM. yyyy");
                                Date date = new Date(System.currentTimeMillis());
                                current_date.setText( sdf.format(date));
                                current_status.setText(String.valueOf(currentWeatherResponse.getWeather().get(0).getMain()));
                            }

                            @Override
                            public void onError(Throwable e) {

                                try {
                                    HttpException error = (HttpException) e;
                                    handleErrorCode(error);
                                } catch (Exception exception) {
                                    e.printStackTrace();
                                }
                            }
                        })

        );
    }

    private void handleErrorCode(HttpException error) {
        if (error.code() == 404) {
            Toast.makeText(getActivity(), getString(R.string.no_city_found_message), Toast.LENGTH_SHORT).show();
        } else if (error.code() == 401) {
            Toast.makeText(getActivity(), getString(R.string.invalid_api_key_message), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), getString(R.string.network_exception_message), Toast.LENGTH_SHORT).show();
        }
    }

    // Get the current location
    private void fetchLastLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
            return;
        }
        Task task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object location) {
                if(location != null)
                    currentLocation = (Location) location;
                    int cur_latitude = (int) currentLocation.getLatitude();
                    int cur_longtitude = (int) currentLocation.getLongitude();
                    cur_lat = String.valueOf(cur_latitude);
                    cur_lon = String.valueOf(cur_longtitude);
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                    current_cityname = addresses.get(0).getLocality();
                    search_text.setHint(current_cityname);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Get the weather of your current location
                init(cur_lat, cur_lon);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }
    }
}