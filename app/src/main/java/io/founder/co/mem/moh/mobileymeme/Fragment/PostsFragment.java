package io.founder.co.mem.moh.mobileymeme.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

import io.founder.co.mem.moh.mobileymeme.Activity.DetailsActivity;
import io.founder.co.mem.moh.mobileymeme.Adapter.RecyclerViewAdapter;
import io.founder.co.mem.moh.mobileymeme.Class.Mobile;
import io.founder.co.mem.moh.mobileymeme.Interface.OnItemClickRecyclerListener;
import io.founder.co.mem.moh.mobileymeme.R;


public class PostsFragment extends Fragment implements OnItemClickRecyclerListener {
    private static final String TAG = "meme";


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private String currentUserID;

    private ArrayList<Mobile> pMobiles = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    private Typeface myFont;
    private Typeface myFontText;

    private TextView tvPost;
    private TextView tvMain;


    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_posts, container, false);

        progressBar = v.findViewById(R.id.loading);

        //Ad
//        pAdView = v.findViewById(R.id.adViewPost);
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();
//        pAdView.loadAd(adRequest);
        //_-_-_-_-//


        tvPost = v.findViewById(R.id.tv_post_p);
        tvMain = v.findViewById(R.id.tv_main);

        myFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Comfortaa.ttf");
        myFontText = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nabila.ttf");

        tvPost.setTypeface(myFont);
        tvMain.setTypeface(myFontText);


        recyclerView = v.findViewById(R.id.recycler_view);
        adapter = new RecyclerViewAdapter(getContext(), pMobiles, this);


        mAuth = FirebaseAuth.getInstance();
//        currentUserID = mAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

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
                                    Mobile mobile = new Mobile();
                                    Map<String, Object> mobilesF = document.getData();

                                    mobile.setID(mobilesF.get("currentUserID").toString());
                                    mobile.setImage(mobilesF.get("imageOne").toString());
                                    mobile.setImageTow(mobilesF.get("imageTow").toString());
                                    mobile.setImageThree(mobilesF.get("imageThree").toString());
                                    mobile.setBrand(mobilesF.get("brand").toString());
                                    mobile.setModel(mobilesF.get("model").toString());
                                    mobile.setCondition(mobilesF.get("condition").toString());
                                    mobile.setPrice(mobilesF.get("price").toString() + " EGP");
                                    mobile.setPhone(mobilesF.get("phone").toString());
                                    mobile.setCurrentDate(mobilesF.get("currentDate").toString());
                                    mobile.setStorageCapacity(mobilesF.get("storageCapacity").toString());
                                    mobile.setLocation(mobilesF.get("location").toString());
                                    mobile.setDetail(mobilesF.get("detail").toString());
                                    mobile.setColor(mobilesF.get("colors").toString());
                                    mobile.setName(mobilesF.get("name").toString());
                                    Log.d("gggggggggggggggg", "name: " + mobile.getName());
                                    Log.d("gggggggggggggggg", "phone: " + mobile.getPhone());
                                    onNewItemAdded(mobile);
                                }
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                                Log.d("gggggggggggggggg", "Error: " + task.getException());
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

            initImageBitmaps();
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            Log.d("gggggggggggggggg", "interner: " + "No Internet");
            progressBar.setVisibility(View.GONE);
        }
        return v;
    }

    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        Mobile mobile = new Mobile();
        mobile.setBrand("Samsung");
        mobile.setModel("5s");
        mobile.setPhone("01206026705");
        mobile.setColor("white");
        mobile.setPrice("150 EGP");
        mobile.setImage("https://www.t-mobile.com/content/dam/t-mobile/en-p/cell-phones/apple/apple-iphone-7-plus/gold/Apple-iPhone7-Plus-Gold-1-3x.jpg");
        mobile.setCondition("Used");
        mobile.setDetail("Good Condition");
        mobile.setLocation("Cairo");
        mobile.setName("karim");
        onNewItemAdded(mobile);//to handel it adapter
        intiRecyclerView();
    }


    private void intiRecyclerView() {
        Log.d(TAG, "intiRecyclerView: init RecyclerView .");

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    void onNewItemAdded(Mobile m) {
        adapter.onNewMobileAdded(m);
    }


    @Override
    public void onClick(Mobile mobile) {

        Intent intent = new Intent(getContext(), DetailsActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("mobile", mobile);
        intent.putExtras(data);
        startActivity(intent);

    }

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
}
