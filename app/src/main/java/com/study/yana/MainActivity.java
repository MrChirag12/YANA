package com.study.yana;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int PERMISSION_REQUEST_CAMERA = 3;

    private ImageView imageViewCapture;
    private ImageView imageViewPlaceholder;
    private TextInputEditText editTextDescription;
    private TextInputEditText editTextIssueType;
    private MaterialButton buttonUpload;
    private MaterialButton buttonCamera;
    private MaterialButton buttonSubmit;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewCapture = findViewById(R.id.imageViewCapture);
        imageViewPlaceholder = findViewById(R.id.imageViewPlaceholder);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextIssueType = findViewById(R.id.editTextIssueType);
        buttonUpload = findViewById(R.id.buttonUpload);
        buttonCamera = findViewById(R.id.buttonCamera);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    captureImage();
                } else {
                    requestCameraPermission();
                }
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReport();
            }
        });
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
    }

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageViewCapture.setImageBitmap(imageBitmap);
                imageViewPlaceholder.setVisibility(View.GONE);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    imageViewCapture.setImageBitmap(bitmap);
                    imageViewPlaceholder.setVisibility(View.GONE);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            } else {
                Toast.makeText(this, "Camera permission is required to capture images", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void submitReport() {
        String description = editTextDescription.getText().toString().trim();
        String issueType = editTextIssueType.getText().toString().trim();

        if (imageUri == null && imageViewCapture.getDrawable() == null) {
            Toast.makeText(this, "Please select or capture an image", Toast.LENGTH_SHORT).show();
            return;
        }

        if (description.isEmpty() || issueType.isEmpty()) {
            Toast.makeText(this, "Please enter a description and issue type", Toast.LENGTH_SHORT).show();
            return;
        }

        // Here you would typically send the report to a backend service
        // For now, we'll just show a success message
        Toast.makeText(this, "Report submitted successfully", Toast.LENGTH_SHORT).show();
        resetForm();
    }

    private void resetForm() {
        imageViewCapture.setImageDrawable(null);
        imageViewPlaceholder.setVisibility(View.VISIBLE);
        editTextDescription.setText("");
        editTextIssueType.setText("");
        imageUri = null;
    }
}

