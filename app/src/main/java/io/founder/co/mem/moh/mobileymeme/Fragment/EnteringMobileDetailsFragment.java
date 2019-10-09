package io.founder.co.mem.moh.mobileymeme.Fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;


import io.founder.co.mem.moh.mobileymeme.Activity.RegisterActivity;
import io.founder.co.mem.moh.mobileymeme.Class.MyLocationProvider;
import io.founder.co.mem.moh.mobileymeme.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class EnteringMobileDetailsFragment extends Fragment implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 12;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 112;
    //EditText Declaration

    private TextView tvLocation;
    private EditText etName;
    private Spinner spinnerBrand;
    private EditText etModel;
    private Spinner spinnerCondition;
    private Spinner spinnerCapacity;
    private EditText etPrice;
    private Spinner spinnerColor;
    private EditText etPhone;
    private EditText etDetail;


    private String conditionSpinner;
    private String brandSpinner;
    private String capacitySpinner;
    private String colorSpinner;

    private String currentDate, currentTime;
    ;


    //Button Declaration
    private Button btnSave;
    private Button btnUploadImage;
    private Button btnCurrentLocation;
    private Button btnLocation;


    private TextView tvEntering;
    private Typeface myFont;


    private ImageView ivProduct;
    private ImageView ivProductTow;
    private ImageView ivProductThree;


    private ProgressBar progressBar;
    //private AdView mAdView;
    MyLocationProvider locationProvider;


    private Uri uriFile;
    private Uri imageUriOne;
    private Uri imageUriTow;
    private Uri imageUriThree;

    private Uri selectedUri;

    private Uri FileOne;
    private Uri FileTow;
    private Uri FileThree;

    private Activity activity;
    private FirebaseAuth mAuth;
    private String CurrentUserID;
    private FirebaseFirestore db;
    private DatabaseReference RootRef;
    private StorageReference eStorageReference;
    private FirebaseStorage eStorage;


    public static final int GET_FROM_GALLERY = 14;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 11;
    private Map<String, String> mobile;
    public static final String TAG = "meme";

    private static String imageCondition = "imageOne";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_entering_mobile_fragmet, container, false);


        //Init
        //Edit Text
        //firebase
        try {
            mAuth = FirebaseAuth.getInstance();
            CurrentUserID = mAuth.getCurrentUser().getUid().toString();
            eStorage = FirebaseStorage.getInstance();
            RootRef = FirebaseDatabase.getInstance().getReference();
            eStorageReference = eStorage.getReference();
            db = FirebaseFirestore.getInstance();
        } catch (Exception e) {

        }

        spinnerBrand = v.findViewById(R.id.spinner_brand_e);
        etModel = v.findViewById(R.id.et_model_e);
        spinnerColor = v.findViewById(R.id.spinner_color_e);
        spinnerCapacity = v.findViewById(R.id.spinner_storage_capacity_e);
        etPrice = v.findViewById(R.id.et_price_e);
        btnLocation = v.findViewById(R.id.btn_location);
        tvLocation = v.findViewById(R.id.tv_location);
        etPhone = v.findViewById(R.id.et_phone_e);
        etName = v.findViewById(R.id.et_name_e);
        etDetail = v.findViewById(R.id.et_detail_e);
        spinnerCondition = v.findViewById(R.id.spinner_condition_e);

        spinnerBrand.setPrompt("Brands");
        spinnerCapacity.setPrompt("Storage Capacity");
        spinnerColor.setPrompt("Color");
        spinnerCondition.setPrompt("Condition");


        progressBar = v.findViewById(R.id.loading);
        progressBar.setVisibility(View.GONE);

        ivProduct = v.findViewById(R.id.iv_product_e);
        ivProductTow = v.findViewById(R.id.iv_product_e_tow);
        ivProductThree = v.findViewById(R.id.iv_product_e_three);

        //Buttons
        btnSave = v.findViewById(R.id.btn_save);
        btnUploadImage = v.findViewById(R.id.btn_save_image);
        btnCurrentLocation = v.findViewById(R.id.btn_current_location);


        tvEntering = v.findViewById(R.id.tv_Entering);
        myFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Comfortaa.ttf");
        tvEntering.setTypeface(myFont);
        etModel.setTypeface(myFont);
        etPrice.setTypeface(myFont);
        etName.setTypeface(myFont);
        etPhone.setTypeface(myFont);
        etDetail.setTypeface(myFont);
        btnSave.setTypeface(myFont);
        btnUploadImage.setTypeface(myFont);


//condition spinner


        mobile = new HashMap<>();


        initialSpinner();


        //Events
        btnSave.setOnClickListener(this);
        btnUploadImage.setOnClickListener(this);
        btnCurrentLocation.setOnClickListener(this);
        btnLocation.setOnClickListener(this);


        ivProduct.setOnClickListener(this);
        ivProductTow.setOnClickListener(this);
        ivProductThree.setOnClickListener(this);

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            selectedUri = data.getData();


            if (imageCondition.equals("imageOne")) {
                try {
                    //bitmap to take image and display it to image view
                    FileOne = selectedUri;
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), FileOne);
                    ivProduct.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (imageCondition.equals("imageTow")) {
                try {
                    //bitmap to take image and display it to image view
                    FileTow = selectedUri;
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), FileTow);
                    ivProductTow.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (imageCondition.equals("imageThree")) {
                try {
                    FileThree = selectedUri;

                    //bitmap to take image and display it to image view
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), FileThree);
                    ivProductThree.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i("bbbbbbbbbbbbbb", "0");

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {

            Log.i("bbbbbbbbbbbbbb", "1");
            if (resultCode == RESULT_OK) {
                Log.i("bbbbbbbbbbbbbb", "2");
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                Log.i("location", "Place: enter");
                Log.i("location", "Place: " + place.getName());
                tvLocation.setText(place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Log.i("bbbbbbbbbbbbbb", "3");
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
                // TODO: Handle the error.
                Log.i("mememe", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.i("bbbbbbbbbbbbbb", "4");
            } else
                Log.i("bbbbbbbbbbbbbb", "6");
            Log.i("bbbbbbbbbbbbbb", "7");


        }

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btn_save_image:

                if (isConnectedToInternet()) {

                    saveImage(FileOne, 1);


//                    imageUriOne = uriFile;

                    saveImage(FileTow, 2);

//                    imageUriTow = uriFile;

                    saveImage(FileThree, 3);

//                    imageUriThree = uriFile;

                } else {
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.btn_save:

                if (isConnectedToInternet()) {

                    saveOnFireBase();
                } else {
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.iv_product_e:
                imageCondition = "imageOne";
                openGallery();
                break;


            case R.id.iv_product_e_tow:
                imageCondition = "imageTow";
                openGallery();
                break;


            case R.id.iv_product_e_three:
                imageCondition = "imageThree";
                openGallery();
                break;

            case R.id.btn_location:
//outo complete place
                // Set the fields to specify which types of place data to
// return after the user has made a selection.
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                    Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                    Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();

                }


            case R.id.btn_current_location:
                //current location
                if (checkLocationPermission()) {

                    Log.d("mmmmmmmm", "onClick: " + checkLocationPermission());
                    //permission granted
                    locationAllowed();

                    Log.d("location", "locationAllowed: ");


                } else {
                    //permission request
                    Log.d("requestLocation", "onClick: " + checkLocationPermission());
                    requestLocationPermission();

                }

        }
    }

    private void openGallery() {
        Intent galleryImageThree = new Intent(Intent.ACTION_GET_CONTENT);
        galleryImageThree.setType("image/*");
        startActivityForResult(galleryImageThree, GET_FROM_GALLERY);
    }

    //Save image in fire storage
    private void saveImage(Uri uri, final int flag) {

        progressBar.setVisibility(View.VISIBLE);

        //get a reference to store file

        if (uri != null) {
            final StorageReference ref = eStorageReference.child("images/" + UUID.randomUUID().toString());
            Log.e("image", "saveImage: " + ref);
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    uriFile = uri;
                                    switch (flag) {
                                        case 1:
                                            imageUriOne = uri;
                                            progressBar.setVisibility(View.VISIBLE);
                                            Log.i("imageUriOne", "onSuccess: " + imageUriOne);
                                        case 2:
                                            imageUriTow = uri;
                                            Log.i("imageUriOne", "onSuccess: " + imageUriTow);
                                            progressBar.setVisibility(View.VISIBLE);
                                        case 3:
                                            imageUriThree = uri;
                                            Log.i("imageUriOne", "onSuccess: " + imageUriThree);
                                            progressBar.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                            progressBar.setVisibility(View.GONE);
                        }


                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Please Enter the Image Of Mobile", Toast.LENGTH_SHORT).show();
        }

    }


    // Save on fire base every things
    private void saveOnFireBase() {
        if (isConnectedToInternet()) {
            if (imageUriOne != null && imageUriTow != null && imageUriThree != null) {
                progressBar.setVisibility(View.GONE);


                Calendar calForTime = Calendar.getInstance();
                SimpleDateFormat currentTimeFormat = new SimpleDateFormat("h:mm a");
                currentTime = currentTimeFormat.format(calForTime.getTime());

                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currentDateFormat = new SimpleDateFormat("ddd");
                currentDate = currentDateFormat.format(calForDate.getTime());


                String brand = brandSpinner;
                String condition = conditionSpinner;
                String model = etModel.getText().toString().trim();
                String colors = colorSpinner;
                String capacity = capacitySpinner;
                String price = etPrice.getText().toString().trim();
                String location = tvLocation.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String detail = etDetail.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String imageOne = imageUriOne.toString().trim();
                Log.i("imageUriOne", "onSuccess: " + imageUriOne);
                String imageTow = imageUriTow.toString().trim();
                Log.i("imageUriOne", "onSuccess: " + imageUriTow);
                String imageThree = imageUriThree.toString().trim();
                Log.i("imageUriOne", "onSuccess: " + imageUriThree);

                if (TextUtils.isEmpty(brand)) {
                    Toast.makeText(getContext(), "Enter Brand", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(model)) {
                    Toast.makeText(getContext(), "Enter Model", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(colors)) {
                    Toast.makeText(getContext(), "Enter Color", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(price)) {
                    Toast.makeText(getContext(), "Enter Price", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getContext(), "Enter Phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(detail)) {
                    Toast.makeText(getContext(), "Enter Brand", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(condition)) {
                    Toast.makeText(getContext(), "Enter Condition", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getContext(), "Enter Your Name", Toast.LENGTH_SHORT).show();
                    return;

                } else {


                    mobile.put("brand", brand);
                    mobile.put("model", model);
                    mobile.put("colors", colors);
                    mobile.put("price", price);
                    mobile.put("phone", phone);
                    mobile.put("detail", detail);
                    mobile.put("condition", condition);
                    mobile.put("location", location);
                    mobile.put("storageCapacity", capacity);
                    mobile.put("name", name);
                    mobile.put("imageOne", imageOne);
                    mobile.put("imageTow", imageTow);
                    mobile.put("imageThree", imageThree);
                    mobile.put("currentUserID", CurrentUserID);
                    mobile.put("currentDate", currentDate + " " + currentTime);
                    db.collection("mobiles")
                            .add(mobile)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("nnnnnnnnnnnn", "DocumentSnapshot added with ID: " + documentReference.getId());

                                    Toast.makeText(getContext(), "done", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    Log.w("5555555555", "Error adding document", e);
                                    Toast.makeText(getContext()
                                            , "Error", Toast.LENGTH_LONG).show();
                                }
                            });
                    //try & catch because beginTransaction may produce 'java NullPointerException'
                    try {
                        android.support.v4.app.Fragment fragmentPost = new PostsFragment();
                        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_frame, fragmentPost);
                        fragmentTransaction.addToBackStack(fragmentPost.toString());
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        fragmentTransaction.commit();
                    } catch (NullPointerException e) {
                        Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                    etModel.setText("");
                    etPrice.setText("");
                    etPhone.setText("");
                    etDetail.setText("");
                    tvLocation.setText("");
                    etName.setText("");
                    ivProduct.setImageDrawable(null);
                    ivProductTow.setImageDrawable(null);
                    ivProductThree.setImageDrawable(null);

                }
            } else {
                Toast.makeText(getContext(), "please Upload image ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "no internet connection ", Toast.LENGTH_SHORT).show();
        }

    }


    //Check internet
    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }


    //method to request location permission
    public void requestLocationPermission() {

        // Here, thisActivity is the current activity
        if (!checkLocationPermission()) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ShowConfirmationDialog("Warning", "allow app to use location to determine your location ", "allow", "cancel", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION);
                    }
                }, null);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            locationAllowed();
        }

    }

    //method to check permission
    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    //method Location allowed
    public void locationAllowed() {

        locationProvider = new MyLocationProvider(getContext());


        if (locationProvider.isCanGetLocation()) {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());


            try {
                Log.d("aaaaaa", "Lat " + locationProvider.getLng());
                Log.d("nnnnnn", "Lng " + locationProvider.getLng());
                List<Address> addresses = geocoder.getFromLocation(locationProvider.getLat(), locationProvider.getLng(), 1);
                String cityName = addresses.get(0).getAddressLine(0);
                tvLocation.setText(cityName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public MaterialDialog ShowConfirmationDialog(String title, String content, String pos, String neg, MaterialDialog.SingleButtonCallback posCallback, MaterialDialog.SingleButtonCallback negCallback) {

        return new MaterialDialog.Builder(getContext())
                .content(content)
                .title(title)
                .positiveText(pos)
                .onPositive(posCallback)
                .negativeText(neg)
                .onNegative(negCallback)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do
                    locationAllowed();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(activity, "user denied location request", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }

    }


    private void initialSpinner() {
        ArrayAdapter<CharSequence> adapterCondition = ArrayAdapter.createFromResource(getContext(), R.array.condition, R.layout.support_simple_spinner_dropdown_item);
        adapterCondition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondition.setAdapter(adapterCondition);
        spinnerCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Condition")) {
                } else {
                    conditionSpinner = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Please Choose the Condition", Toast.LENGTH_SHORT).show();

            }

        });


//brands spinner
        ArrayAdapter<CharSequence> adapterBrand = ArrayAdapter.createFromResource(getContext(), R.array.brands, R.layout.support_simple_spinner_dropdown_item);
        adapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBrand.setAdapter(adapterBrand);

        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brandSpinner = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Please Choose the Brand", Toast.LENGTH_SHORT).show();

            }
        });


        //storage capacity spinner
        ArrayAdapter<CharSequence> adapterStorageCapacity = ArrayAdapter.createFromResource(getContext(), R.array.StorageCapacity, R.layout.support_simple_spinner_dropdown_item);
        adapterStorageCapacity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCapacity.setAdapter(adapterStorageCapacity);

        spinnerCapacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                capacitySpinner = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Please Choose the Storage Capacity", Toast.LENGTH_SHORT).show();

            }
        });

        //Color spinner
        ArrayAdapter<CharSequence> adapterColor = ArrayAdapter.createFromResource(getContext(), R.array.Color, R.layout.support_simple_spinner_dropdown_item);
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(adapterColor);

        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                colorSpinner = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Please Choose the Color", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        if (CurrentUserID == null) {
            sendToRegisterActivity();
        }


    }

    private void sendToRegisterActivity() {
        Intent intentRegister = new Intent(getContext(), RegisterActivity.class);
        startActivity(intentRegister);
    }
}