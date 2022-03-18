package com.idiots.klip.klip;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.idiots.klip.R;
import com.idiots.klip.account.account;
import com.idiots.klip.discover.discover;
import com.idiots.klip.home.dashboard;
import com.idiots.klip.inbox.inbox;

import java.util.HashMap;

public class klip extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    VideoView view;
    Button b1, b2;
    String e1, e2;
    EditText editText1;
    EditText editText2;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Uri vediouri;
    String vediourl;
    String vediomainurl;
    long maxid;
    long maxid1;
    ProgressBar progressBar;
    String cuser;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klip);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.Klip);
        view = findViewById(R.id.vv1);
        b1 = findViewById(R.id.selectbutton);
        b2 = findViewById(R.id.upload_btn);
        editText1 = findViewById(R.id.vtitle);
        editText2 = findViewById(R.id.descrip);
        progressBar = findViewById(R.id.progressBar);
        cuser = user.getPhoneNumber();
        progressBar.setVisibility(View.INVISIBLE);
        reference.child(cuser).child("vediourl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxid = (dataSnapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("unidata").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxid1 = (dataSnapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gaintent = new Intent();
                gaintent.setAction(Intent.ACTION_GET_CONTENT);
                gaintent.setType("Video/*");
                startActivityForResult(gaintent, 2);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vediouri != null) {
                    uploadfirebase(vediouri);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(klip.this, "please select vedio", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), dashboard.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.discover:
                        startActivity(new Intent(getApplicationContext(), discover.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.Klip:
//                        startActivity(new Intent(getApplicationContext(),klip.class));
//                        overridePendingTransition(0,0);
                        return true;
                    case R.id.inbox:
                        startActivity(new Intent(getApplicationContext(), inbox.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), account.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });
    }


    private void uploadfirebase(Uri uri) {
        String e1 = editText1.getText().toString();
        String e2 = editText2.getText().toString();

        StorageReference fileref = storageReference.child(System.currentTimeMillis() + "." + getfileExtensiom(uri));
        fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        progressBar.setVisibility(View.VISIBLE);
                        vediourl = uri.toString();
                        vediomainurl = uri.toString();
                        udata udata = new udata(e1, e2, vediomainurl);


                        HashMap urldata = new HashMap();
                        urldata.put("vediourl" + maxid, vediourl);
                        reference.child("unidata").push().setValue(udata);
                        reference.child(cuser).child("vediourl").updateChildren(urldata).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(klip.this, "upload sucess", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(klip.this, "something went wrong", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    }
                });


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(klip.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getfileExtensiom(Uri muri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(muri));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            vediouri = data.getData();
          
            view.setVideoURI(vediouri);

        }
    }
}