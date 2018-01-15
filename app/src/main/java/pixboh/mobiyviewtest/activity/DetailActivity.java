package pixboh.mobiyviewtest.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pixboh.mobiyviewtest.R;
import pixboh.mobiyviewtest.realm.DataItemRealm;

/**
 * Created by pix on 1/12/18.
 */

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.img_header)
    ImageView mImageHeader;
    @BindView(R.id.toolbar_detail)
    android.support.v7.widget.Toolbar mToolbar;
    @BindView(R.id.tv_description)
    TextView mDescription;
    @BindView(R.id.detail_title)
    TextView mTitle;
    @BindView(R.id.attraction_detail_categorie)
    TextView mCatgeory;
    private DataItemRealm dataItem;
    @BindView(R.id.attraction_price_view)
    LinearLayout mAttractionPriceView;
    @BindView(R.id.location_area_detail)
    TextView mLocationArea;
    @BindView(R.id.address_detail)
    TextView mAddress;
    @BindView(R.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    private boolean isOffilne = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //ca fait ressortir la photo lol!
//        doLikeWhatsappCollapse();
        //Change back arrow color even API<21
        Drawable drawable = mToolbar.getNavigationIcon();
        drawable.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        dataItem = getIntent().getParcelableExtra(MainActivity.CLICKED_TAG_INFO);

        collapsingToolbarLayout.setTitle(dataItem.getTitle());
        loadImageHeader();
        bindData();
        initMap();

    }


    public void bindData() {
        Object dat = null;
        setTitle(dataItem.getTitle());
        String description = getIntent().getStringExtra(MainActivity.CLICKED_TAG_DESCRIPTION);
        if (description != null)
            mDescription.setText(description);
        mTitle.setText(dataItem.getTitle());
        String locationArea = getIntent().getStringExtra(MainActivity.CLICKED_TAG_LOCATION);
        mLocationArea.setText(locationArea);
        mAddress.setText(dataItem.getAddress());
        //Difference in ui
        if (dataItem.getType().contains("restaurant")) {
            mAttractionPriceView.setVisibility(View.INVISIBLE);
            mCatgeory.setText("");
        } else {
            mAttractionPriceView.setVisibility(View.VISIBLE);
            String attractionCategory = getIntent().getStringExtra(MainActivity.CLICKED_TAG_ATTR_CATEGORES);
            mCatgeory.setText(attractionCategory);
        }


    }

    public void loadImageHeader() {
        String uri_image = getIntent().getStringExtra(MainActivity.CLICKED_TAG_FILE_URI);
        if (uri_image == null) {
            if (dataItem.getType().contains("restaurant")) {
                Log.e("Null", "NullRestau");
                Picasso.with(this).load("null")
                        .placeholder(R.drawable.placeholder_restaurant)
                        .into(mImageHeader);
            } else {
                Log.e("Null", "NullAttrac");
                Picasso.with(this).load("null")
                        .placeholder(R.drawable.placeholder_attraction)
                        .into(mImageHeader);
            }

        } else {
            Picasso.with(this).load(uri_image)
                    .into(mImageHeader);
        }


    }

    public void initMap() {

        String latlong = getIntent().getStringExtra(MainActivity.CLICKED_TAG_GEO);
        LatLng place = new LatLng(Double.valueOf(latlong.split(",")[0]), Double.valueOf(latlong.split(",")[1]));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
//                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + place.latitude + "," + place.longitude + "&mode=d");
//                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                        mapIntent.setPackage("com.google.android.apps.maps");
//                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                            startActivity(mapIntent);
//                        }
                        return false;

                    }

                });
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Uri gmmIntentUri = Uri.parse("geo:" + place.latitude + "," + place.longitude + "?z=15");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                    }
                });
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                googleMap.addMarker(new MarkerOptions().title(dataItem.getTitle()).position(place));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
