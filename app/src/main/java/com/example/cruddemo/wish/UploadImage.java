package com.example.cruddemo.wish;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.cruddemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class UploadImage extends Fragment {
    private String displayImg = null;
    private ImageView itemPic;
    private FloatingActionButton addImage;
    //why public
    private Uri imageUri;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    public UploadImage() {
        // Required empty public constructor
    }

    public UploadImage(String displayImg) {
        this.displayImg = displayImg;
    }

    public static UploadImage newInstance(String param1, String param2) {
        UploadImage fragment = new UploadImage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_upload_image, container, false);

        addImage = view.findViewById(R.id.additemimage);
        itemPic = view.findViewById(R.id.new_selected_image);

        if(displayImg != null){
            Glide.with(getContext()).load(displayImg).into(itemPic);
        }


        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void choosePicture() {

        Intent intent  = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            itemPic.setImageURI(imageUri);
            Log.i("new image URI",imageUri.toString());
            uploadPicture();
        }
    }

    private void uploadPicture() {

        final ProgressDialog progressUpload = new ProgressDialog(getView().getContext());

        progressUpload.setTitle("Your image is being uploaded");
        progressUpload.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference newImageRef = storageReference.child("images/" + randomKey);


        newImageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressUpload.dismiss();

                        final Task<Uri> imageTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        imageTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(imageTask.isSuccessful()){
                                    //getting download URL
                                    Uri downloadUri = imageTask.getResult();
                                    if(downloadUri != null){
                                        String uploadedImageURL = downloadUri.toString();
                                        Toast.makeText(getView().getContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                                        if(displayImg!=null){
                                            final EditWishActivity editWishActiv = (EditWishActivity) getActivity();
                                            editWishActiv.sendUploadUrl(uploadedImageURL);
                                        }else{
                                            final AddNewWish newWishActv = (AddNewWish) getActivity();
                                            newWishActv.sendUploadUrl(uploadedImageURL);
                                        }

                                    }
                                } else {
                                    Log.i("Download image URL", "Unable to get the download url");
                                }
                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressUpload.dismiss();
                        Toast.makeText(getView().getContext(), "Sorry, something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}