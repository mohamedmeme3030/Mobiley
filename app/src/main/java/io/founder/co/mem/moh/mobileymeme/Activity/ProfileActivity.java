package io.founder.co.mem.moh.mobileymeme.Activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.founder.co.mem.moh.mobileymeme.Adapter.RecyclerViewAdapterProfile;
import io.founder.co.mem.moh.mobileymeme.Class.Mobile;
import io.founder.co.mem.moh.mobileymeme.Interface.OnItemClickRecyclerListener;
import io.founder.co.mem.moh.mobileymeme.R;


public class ProfileActivity extends AppCompatActivity implements OnItemClickRecyclerListener {


    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageRef;


    private ArrayList<Mobile> pMobiles = new ArrayList<>();
    private RecyclerViewAdapterProfile adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CircleImageView circleImageView;
    private TextView detailProfile,yourProfile,mainProfile;
    private Typeface typefaceOne;
    private Typeface typefaceTow;


    private String userName;
    private String userImage;
    private String ID;

    private CollectionReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressBar = findViewById(R.id.loading);
        circleImageView = findViewById(R.id.iv_profile_image);
        detailProfile = findViewById(R.id.tv_profile_bio);
        recyclerView = findViewById(R.id.rv_profile_posts);
        yourProfile=findViewById(R.id.tv_your_profile);
        mainProfile=findViewById(R.id.tv_main_profile);

        typefaceOne=Typeface.createFromAsset(this.getAssets(), "fonts/Comfortaa.ttf");
        typefaceTow=Typeface.createFromAsset(this.getAssets(), "fonts/Nabila.ttf");
        yourProfile.setTypeface(typefaceOne);
        mainProfile.setTypeface(typefaceTow);



        adapter = new RecyclerViewAdapterProfile(ProfileActivity.this, pMobiles, this);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        userName = getIntent().getSerializableExtra("userName").toString();
        ID = getIntent().getSerializableExtra("ID").toString();

        if (isConnectedToInternet()) {
            db.collection("Users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Map<String, Object> userF = document.getData();

                                    if (ID.equals(userF.get("userID").toString())) {


                                        userImage = userF.get("image").toString();
                                        Glide.with(ProfileActivity.this).load(userImage).into(circleImageView);
                                        userName = userF.get("name").toString();
                                        detailProfile.setText(userName);




                                    } else {

                                        Toast.makeText(ProfileActivity.this, "Nothing in profile", Toast.LENGTH_SHORT).show();


                                    }
                                }


                            }

                        }


                    }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }


        //Read from firebase
        if (isConnectedToInternet()) {
            progressBar.setVisibility(View.VISIBLE);
            db.collection("mobiles")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Map<String, Object> mobilesF = document.getData();

                                    if (ID.equals(mobilesF.get("currentUserID").toString())) {

                                        Mobile mobile = new Mobile();
                                        mobile.setImage(mobilesF.get("imageOne").toString());
                                        mobile.setImage(mobilesF.get("imageTow").toString());
                                        mobile.setImage(mobilesF.get("imageThree").toString());
                                        mobile.setBrand(mobilesF.get("brand").toString());
                                        mobile.setModel(mobilesF.get("model").toString());
                                        mobile.setCondition(mobilesF.get("condition").toString());
                                        mobile.setPrice(mobilesF.get("price").toString() + " EGP");
                                        mobile.setPhone(mobilesF.get("phone").toString());
                                        mobile.setLocation(mobilesF.get("location").toString());
                                        mobile.setDetail(mobilesF.get("detail").toString());
                                        mobile.setColor(mobilesF.get("colors").toString());
                                        mobile.setName(mobilesF.get("name").toString());
                                        onNewItemAdded(mobile);


                                    } else {

                                        Toast.makeText(ProfileActivity.this, "Nothing in profile", Toast.LENGTH_SHORT).show();


                                    }
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
            initImageBitmaps();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }

    private void initImageBitmaps() {

        intiRecyclerView();
    }

    private void intiRecyclerView() {
        Log.d("sss", "intiRecyclerView: init RecyclerView .");
        adapter = new RecyclerViewAdapterProfile(ProfileActivity.this, pMobiles, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void onNewItemAdded(Mobile m) {
        adapter.onNewMobileAdded(m);
    }


    @Override
    public void onClick(Mobile mobile) {
        Intent intent = new Intent(this, DetailsActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("mobile", mobile);
        intent.putExtras(data);
        startActivity(intent);

    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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


}

