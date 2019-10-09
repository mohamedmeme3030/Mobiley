package io.founder.co.mem.moh.mobileymeme.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import io.founder.co.mem.moh.mobileymeme.Base.MyBaseActivity;
import io.founder.co.mem.moh.mobileymeme.R;

import static io.founder.co.mem.moh.mobileymeme.Fragment.EnteringMobileDetailsFragment.GET_FROM_GALLERY;


public class RegisterActivity extends MyBaseActivity {


    private EditText etEmail, etPhone, etName;
    private EditText etPassword;
    private CircleImageView ivUserImage;

    private LinearLayout rootLayout;


    private Button btnSignUp;
    private TextView tvLogin;
    private ProgressBar progressBar;


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference rStorageReference;
    private FirebaseStorage rStorage;


    private Map<String, String> user;
    private Typeface myFont;
    private TextView tvRegister;
    private Uri selectedUri;
    private Uri imageUriOne;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        rStorage = FirebaseStorage.getInstance();
        rStorageReference = rStorage.getReference();


        etEmail = findViewById(R.id.et_email_r);
        etName = findViewById(R.id.et_name_r);
        etPhone = findViewById(R.id.et_phone_r);
        etPassword = findViewById(R.id.et_password_r);
        btnSignUp = findViewById(R.id.btn_register_r);
        tvLogin = findViewById(R.id.tv_login_r);
        tvRegister = findViewById(R.id.tv_register);
        ivUserImage = findViewById(R.id.iv_user_image);
        progressBar = findViewById(R.id.loading);


        rootLayout = findViewById(R.id.rootLayoutRegister);


        user = new HashMap<>();


        myFont = Typeface.createFromAsset(RegisterActivity.this.getAssets(), "fonts/Comfortaa.ttf");

        tvRegister.setTypeface(myFont);
        tvLogin.setTypeface(myFont);
        btnSignUp.setTypeface(myFont);
        etEmail.setTypeface(myFont);
        etName.setTypeface(myFont);
        etPhone.setTypeface(myFont);
        etPassword.setTypeface(myFont);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
            }
        });

        ivUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedToInternet()) {
                    if (imageUriOne != null) {


                        String name = etName.getText().toString();
                        String email = etEmail.getText().toString();
                        String phone = etPhone.getText().toString();
                        String password = etPassword.getText().toString();

//                                String imageUri = uriFile.toString();


                        if (TextUtils.isEmpty(name)) {
                            Toast.makeText(RegisterActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(email)) {
                            Snackbar.make(rootLayout, "Please enter email address", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(password)) {
                            Snackbar.make(rootLayout, "Please enter password", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        if (password.length() < 6) {
                            Snackbar.make(rootLayout, "password too short !!!", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(phone)) {
                            Snackbar.make(rootLayout, "Please enter phone", Snackbar.LENGTH_SHORT).show();
                            return;
                        }


                        user.put("email", email);
                        user.put("name", name);
                        user.put("phone", phone);
                        user.put("image", imageUriOne.toString());


                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        String CurrentUserID = mAuth.getCurrentUser().getUid();
                                        user.put("userID", CurrentUserID);


                                        CollectionReference referenceUser = db.collection("Users");
                                        referenceUser.add(user).
                                                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {


                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(RegisterActivity.this, "User added", Toast.LENGTH_SHORT).show();
                                                        Intent intentMainFragment = new Intent(RegisterActivity.this, MainFragmentActivity.class);
                                                        startActivity(intentMainFragment);
                                                        finish();

                                                    }

                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Log.d("dddddddddd", "onFailure: " + e.getMessage());
                                                Toast.makeText(RegisterActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {


                                Log.d("ffffffffff", "onFailure: " + e.getMessage());
                                Toast.makeText(RegisterActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else{


                    }
                } else {


                    Toast.makeText(RegisterActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    private void openGallery() {
        Intent galleryImageThree = new Intent(Intent.ACTION_GET_CONTENT);
        galleryImageThree.setType("image/*");
        startActivityForResult(galleryImageThree, GET_FROM_GALLERY);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentMainFragmentActivity = new Intent(RegisterActivity.this, MainFragmentActivity.class);
        startActivity(intentMainFragmentActivity);
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            selectedUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(RegisterActivity.this.getContentResolver(), selectedUri);
                ivUserImage.setImageBitmap(bitmap);


                if (isConnectedToInternet()) {


                    //get a reference to store file

                    if (selectedUri != null) {
                        final StorageReference ref = rStorageReference.child("imageUser/" + UUID.randomUUID().toString());

                        Log.e("image", "saveImage: " + ref);


                        ref.putFile(selectedUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {


                                                imageUriOne = uri;
                                                HideProgressBar();

                                                Log.i("imageUriOne", "onSuccess: " + imageUriOne);

                                            }
                                        });
                                    }


                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Please Enter the Image Of Mobile", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
