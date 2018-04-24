package com.example.tandung_pc.monngonduongpho.View;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tandung_pc.monngonduongpho.R;
import com.example.tandung_pc.monngonduongpho.until.Server;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

public class Activity_Add_Next extends AppCompatActivity implements View.OnClickListener {
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //
    private static final int SELECT_PICTURE = 100;
    ImageView imageView;
    EditText edtNameFood, edtPrice, edtAddress, edtInformation;
    Button btnThem, btnThoat;
    Spinner spinner;
    ProgressDialog progressDialog;
    String user_id = "";
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;
    //Bitmap to get image from gallery
    private Bitmap bitmap;
    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add__next);
        requestStoragePermission();
        initView();
        initSpinner();
    }

    private void initView() {
        imageView = findViewById(R.id.img_bg);
        edtAddress = findViewById(R.id.Address);
        edtNameFood = findViewById(R.id.namFood);
        edtPrice = findViewById(R.id.price);
        btnThem = findViewById(R.id.btnThem);
        btnThoat = findViewById(R.id.btnThoat);
        edtInformation = findViewById(R.id.Thongtin);
        //
        btnThem.setOnClickListener(this);
        btnThoat.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    private void initSpinner() {
        spinner = findViewById(R.id.spinner);
        String[] arr = {
                "Bún,Phở",
                "Món chè",
                "Nước uống",
                "Đồ chiên,nướng",
                "Bánh mì",
                "Các món khác"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_Add_Next.this,
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }


    public void uploadMultipart() {
        String name = edtNameFood.getText().toString();
        String address = edtAddress.getText().toString();
        String price = edtPrice.getText().toString();
        String information = edtInformation.getText().toString();
        String temp = spinner.getSelectedItem().toString();
        String idFoodtype = "";

        if (temp.equals("Bún,Phở")) {
            idFoodtype = "1";
        } else if (temp.equals("Món chè")) {
            idFoodtype = "2";
        } else if (temp.equals("Nước uống")) {
            idFoodtype = "4";
        } else if (temp.equals("Đồ chiên,nướng")) {
            idFoodtype = "3";
        } else if (temp.equals("Bánh mì")) {
            idFoodtype = "5";
        } else if (temp.equals("Các món khác")) {
            idFoodtype = "6";
        }
        progressDialog = new ProgressDialog(Activity_Add_Next.this);
        progressDialog.setTitle("Đang tải lên");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();


        if (name.equals("") || address.equals("") || price.equals("") || temp.equals("")
                || information.equals("")) {
            progressDialog.dismiss();
            Toast.makeText(Activity_Add_Next.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {

            //getting the actual path of the image
            String path = getPath(filePath);
            Log.d("PATH", path);

            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();
                //Creating a multi part request
                new MultipartUploadRequest(Activity_Add_Next.this, uploadId, Server.DuongdanUploadImage)
                        .addFileToUpload(path, "image") //Adding file
                        .addParameter("name", name)
                        .addParameter("price", price)
                        .addParameter("address", address)
                        .addParameter("description", information)
                        .addParameter("idcuahang", "1")
                        .addParameter("typefood_id", idFoodtype)
                        .addParameter("user_id", "1")
                        .addParameter("comment_id", "1")
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(this, "Tải lên thành công !", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();

            } catch (Exception exc) {
                Toast.makeText(this, "Có lỗi, Vui lòng thử lại !", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

    }

    //method to show file chooser
    private void showFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        Intent in = new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);

        //Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        String pickTitle = "Lựa chọn hình"; // Or get from strings.xml
        Intent chooserIntent = Intent.createChooser(in, pickTitle);
        chooserIntent.putExtra
                (
                        Intent.EXTRA_INITIAL_INTENTS,
                        new Intent[]{pickPhoto}
                );
        startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
    }


    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_bg:
                showFileChooser();
                break;
            case R.id.btnThem:
                uploadMultipart();
                finish();
                break;
            case R.id.btnThoat:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
