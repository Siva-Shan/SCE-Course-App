package com.example.sce.screen.ui.userProfile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.sce.R;
import com.example.sce.common.CommonConstant;
import com.example.sce.databinding.FragmentNotificationsBinding;
import com.example.sce.db.user.UserDao;
import com.example.sce.helper.PreferenceManager;
import com.example.sce.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserProfileFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private ImageView profileImageView;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhoneNumber;
    private Button changeProfileImageButton;
    private Button saveProfileButton;
    private String currentPhotoPath;
    private PreferenceManager preferenceManager;
    private UserDao userDao;
    private User user;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        preferenceManager = new PreferenceManager(getContext());
        String userID = preferenceManager.getUserId();

        userDao = new UserDao(getContext());
        user = userDao.getUser(Long.parseLong(userID));


        profileImageView = root.findViewById(R.id.profileImageView);
        editTextName = root.findViewById(R.id.editTextName);
        editTextEmail = root.findViewById(R.id.editTextEmail);
        editTextPhoneNumber = root.findViewById(R.id.editTextPhoneNumber);
        changeProfileImageButton = root.findViewById(R.id.changeProfileImageButton);
        saveProfileButton = root.findViewById(R.id.saveProfileButton);

        if(user.getProfilePicturePath()!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(user.getProfilePicturePath());
            profileImageView.setImageBitmap(bitmap);
        }

        editTextEmail.setText(user.getEmailAddress());
        editTextName.setText(user.getName());
        editTextPhoneNumber.setText(user.getMobilePhoneNumber());

        changeProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageSource();
            }
        });

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        return root;
    }

    private void selectImageSource() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE_REQUEST);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "Error occurred while creating the File", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.example.sce.provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private static final int PERMISSION_REQUEST_CODE = 1001;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            if (checkPermission()) {
                handleImageSelection(data.getData());
            } else {
                requestPermission();
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            if (checkPermission()) {
                handleCameraImage();
            } else {
                requestPermission();
            }
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean readExternalStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean writeExternalStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (readExternalStorageAccepted && writeExternalStorageAccepted) {
                    // Handle the image selection again after permissions are granted
                    // This logic should be the same as in onActivityResult
                } else {
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void handleImageSelection(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
            profileImageView.setImageBitmap(bitmap);

            String sourcePath = getPathFromUri(imageUri);
            File sourceFile = new File(sourcePath);

            File newDPDir = new File(getActivity().getFilesDir(), "newDP");
            if (!newDPDir.exists()) {
                newDPDir.mkdirs();
            }
            File newFile = new File(newDPDir, sourceFile.getName());

            try (FileInputStream in = new FileInputStream(sourceFile);
                 FileOutputStream out = new FileOutputStream(newFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            currentPhotoPath = newFile.getAbsolutePath();
            user.setProfilePicturePath(currentPhotoPath);
            userDao.updateUser(user);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCameraImage() {
        user.setProfilePicturePath(currentPhotoPath);
        userDao.updateUser(user);
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        profileImageView.setImageBitmap(bitmap);
    }

    // Helper method to get the file path from Uri
    private String getPathFromUri(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }


    private void saveProfile() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            user.setName(name);
            user.setEmailAddress(email);
            user.setMobilePhoneNumber(phoneNumber);
            userDao.updateUser(user);
            Toast.makeText(getActivity(), "Profile saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
