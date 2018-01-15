package pixboh.mobiyviewtest.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.exceptions.RealmMigrationNeededException;
import pixboh.mobiyviewtest.R;
import pixboh.mobiyviewtest.adapter.RVAdapterRealm;
import pixboh.mobiyviewtest.adapter.RvItemClickListener;
import pixboh.mobiyviewtest.model.DataItemModel;
import pixboh.mobiyviewtest.realm.DataItemRealm;
import pixboh.mobiyviewtest.realm.RealmQueries;
import pixboh.mobiyviewtest.realm.RealmUtil;
import pixboh.mobiyviewtest.rest.RestService;
import pixboh.mobiyviewtest.rest.ServiceGenerator;
import pixboh.mobiyviewtest.utils.NetworkUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final long LOCATION_REFRESH_TIME = 0;
    private static final float LOCATION_REFRESH_DISTANCE = 1f;
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.pb_loading)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_error)
    TextView mTvError;


    public static final String CLICKED_TAG_INFO = "com.pix.mobyviewtest.clickedItem";
    public static final String CLICKED_TAG_FILE_URI = "com.pix.mobyviewtest.clickedItem.fileui";
    public static final String CLICKED_TAG_LOCATION = "com.pix.mobyviewtest.clickedItem.location";
    public static final String CLICKED_TAG_DESCRIPTION = "com.pix.mobyviewtest.clickedItem.description";
    public static final String CLICKED_TAG_GEO = "com.pix.mobyviewtest.clickedItem.geo";
    public static final String CLICKED_TAG_ATTR_CATEGORES = "com.pix.mobyviewtest.clickedItem.categories";
    private final String TAG = "MainActivity";

    /**
     * Requirements
     * Download more while scrolling with fludity.
     * Load all data from api without database realm
     */
    private int limitPage = 0;
    private boolean isLoading = false;
    private boolean dataIsOver = false;
    private boolean clicked = false;

    /***
     * Location
     *
     */
    GoogleApiClient mGoogleApiClient;
    private final int REQUEST_CHECK_SETTINGS = 123;
    private final int PLAY_SERVICES_REQUEST = 123;

    private Location lastLocation;
    private RecyclerView.LayoutManager mLinearLayoutManager;

    /**
     * Requirement for realm
     */
    Realm realm;
    private RVAdapterRealm rvAdapterRealm;
    private ArrayList<DataItemRealm> dataItemRealms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        realm = getRealmAPI();
        if (checkPlayServices()) {
            buildGoogleApiClient();
            getLocation();
        }
        setUpRecyclerViewForRealm();
        if (NetworkUtil.getConnectivityStatus(this) == NetworkUtil.TYPE_NOT_CONNECTED) {
            loadDataFromRealm();
        } else {
            if (getRealmAPI().where(DataItemRealm.class).count() == 0) {
                getDataFromServer(limitPage);
            } else {
                loadDataFromRealm();
            }

        }
        setTitle("Around you");
    }

    public void getDataFromServer(int limitPage) {
        mTvError.setVisibility(View.INVISIBLE);
        if (isLoading || NetworkUtil.getConnectivityStatus(this) == NetworkUtil.TYPE_NOT_CONNECTED) {
            return;
        }
        isLoading = true;
        //Load data from api
        final boolean[] stopRequest = {false};
        if (!dataIsOver)
            ServiceGenerator.createService(RestService.class)
                    .listData("restaurant", limitPage, limitPage == 0 ? 200 : 3, "title").enqueue(new Callback<List<DataItemModel>>() {
                @Override
                public void onResponse(Call<List<DataItemModel>> call, Response<List<DataItemModel>> response) {
                    final List<DataItemModel> listRestaurant = response.body();
                    stopRequest[0] = listRestaurant.size() == 0;
                    ServiceGenerator.createService(RestService.class)
                            .listData("attraction", limitPage, limitPage == 0 ? 200 : 3, "title")
                            .enqueue(new Callback<List<DataItemModel>>() {
                                @Override
                                public void onResponse(Call<List<DataItemModel>> call, Response<List<DataItemModel>> response) {
                                    //Update UI
                                    isLoading = false;
                                    mProgressBar.setVisibility(View.INVISIBLE);

                                    List<DataItemModel> listAttraction = response.body();
                                    if (listAttraction.size() == 0) {
                                        if (stopRequest[0] == true) {
                                            dataIsOver = true;
                                        }
                                    }
                                    ArrayList<DataItemModel> newList = new ArrayList<>();
                                    newList.addAll(listAttraction);
                                    newList.addAll(listRestaurant);
                                    //Update realm and show data
                                    RealmQueries.addDataOnRealmFromServer(realm, newList);
                                    if (dataItemRealms.size() == 0) {
                                        Log.e("New size", 0 + "");
                                        loadDataFromRealm();
                                    } else {
                                        updateRealmData(RealmUtil.castToRealmdata(newList));
                                        Log.e("New size", "something");
                                    }
                                    Toast.makeText(MainActivity.this, "Data up to date!", Toast.LENGTH_SHORT).show();
//                                    mRecyclerView.getAdapter().notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<List<DataItemModel>> call, Throwable t) {
                                    isLoading = false;
                                    mTvError.setVisibility(View.VISIBLE);
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                }

                @Override
                public void onFailure(Call<List<DataItemModel>> call, Throwable t) {
                    Log.e("Error request", t.toString());
                    mTvError.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    isLoading = false;
                }
            });

    }

    public void loadDataFromRealm() {
        mProgressBar.setVisibility(View.INVISIBLE);
        RealmResults<DataItemRealm> results = getRealmAPI().where(DataItemRealm.class).findAll()
                .sort("title", Sort.ASCENDING);
        if (results.size() == 0) {
            mTvError.setVisibility(View.VISIBLE);
            mTvError.setText("Activer votre connexion internet");
        } else {
            /**
             * if it's the
             * first load
             * fetch all
             * */
            dataItemRealms.addAll(results);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.getAdapter().notifyDataSetChanged();
//        setUpRecyclerViewForRealm();
        }

    }

    public void updateRealmData(List<DataItemRealm> newData) {
        RealmQueries.addDataOnRealm(getRealmAPI(), newData);
        dataItemRealms.addAll(newData);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }


    public void getLocation() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("NO GRANTED", "LOCATION");

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {
            Log.e("GRANTED", "LOCATION");
            lastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
            if (lastLocation == null) {
                Log.e("Location", "null");


            } else {
                rvAdapterRealm.addCurrentLocation(lastLocation);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                Log.e("Latitude", lastLocation.getLatitude() + "");
                Log.e("Longitude", lastLocation.getLongitude() + "");
            }
        }
    }

    public void setUpRecyclerViewForRealm() {
        rvAdapterRealm = new RVAdapterRealm(dataItemRealms, MainActivity.this, lastLocation);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        RecyclerView.OnItemTouchListener clickListner = new RvItemClickListener(MainActivity.this, new RvItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                /**
                 * PARCELABLE CLASS WITH INNER OBJECT IS NOT A GOOD PRACTICE.
                 * SO I PREFER SEND DATA WITH DIFFERENT TAG
                 * */
                intent.putExtra(CLICKED_TAG_INFO, dataItemRealms.get(position));
                intent.putExtra(CLICKED_TAG_LOCATION, dataItemRealms.get(position).getLocationArea());
                intent.putExtra(CLICKED_TAG_GEO, dataItemRealms.get(position).getLatlong());
                if (dataItemRealms.get(position).getType().contains("attraction"))
                    intent.putExtra(CLICKED_TAG_ATTR_CATEGORES, dataItemRealms.get(position).getAttractionCategory());
                if (dataItemRealms.get(position).getDescription() != null) {
                    intent.putExtra(CLICKED_TAG_DESCRIPTION, dataItemRealms.get(position).getDescription());
                }
                if (dataItemRealms.get(position).getImage() != null) {
                    intent.putExtra(CLICKED_TAG_FILE_URI, dataItemRealms.get(position).getImage());
                }

                /**END*/
                if (!clicked) {
                    startActivity(intent);
                    clicked = true;
                }
                Log.e("Clicked : ", dataItemRealms.get(position).getTitle());
            }
        });
//        Avoid adding listener twice
        try {
            mRecyclerView.removeOnItemTouchListener(clickListner);
        } catch (Exception err) {

        } finally {
            mRecyclerView.addOnItemTouchListener(clickListner);

        }
        rvAdapterRealm.setOnBottomReachedListner(new RVAdapterRealm.OnBottomReachedInterface() {
            @Override
            public void onBottomReached(int position) {
                if (isLoading == false) {
                    /**
                     * Pour contourner une erreur sur la pagination des données reçu
                     * je ne peux recuperer que 6 nouvelles entrées pour etre sur de ne pas en sauter.
                     * Ca gache un petit peu le UX!
                     * */
                    limitPage += limitPage == 0 ? 200 : 3;
                    getDataFromServer(limitPage);
                }
            }
        });
        mRecyclerView.setAdapter(rvAdapterRealm);
    }


    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.e("Location", "Success");
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                //Update data from server
                if (NetworkUtil.getConnectivityStatus(this) != NetworkUtil.TYPE_NOT_CONNECTED
                        && !isLoading) {
                    mRecyclerView.setVisibility(View.GONE);
                    RealmQueries.destroyAll(getRealmAPI());
                    dataItemRealms = new ArrayList<>();
                    mRecyclerView.removeAllViews();
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                    mProgressBar.setVisibility(View.VISIBLE);
                    setUpRecyclerViewForRealm();
                    getDataFromServer(0);
                } else {
                    if (isLoading) {
                        Toast.makeText(this, "Please wait!", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    Toast.makeText(this, "No connection!", Toast.LENGTH_SHORT).show();

                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Method to verify google play services on the device
     */

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        clicked = false;
//        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Avoid detail activity lauching twice
        clicked = false;
//        checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.e(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
        Log.e(TAG, "Coonected yo google api ");
        // Once connected with google api, get the location
        getLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    private void closeRealmAPI() {
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    private void initRealmAPI(Context context) {
        try {
            realm = Realm.getInstance(RealmQueries.config);
        } catch (RealmMigrationNeededException ex) {
            ex.printStackTrace();

            // TODO migration
            Realm.deleteRealm(RealmQueries.config);

            realm = Realm.getInstance(RealmQueries.config);
        }
    }

    private Realm getRealmAPI() {
        if (realm == null) {
            initRealmAPI(this);
        }
        return realm;
    }


}
