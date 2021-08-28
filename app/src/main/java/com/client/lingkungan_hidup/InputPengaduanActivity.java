package com.client.lingkungan_hidup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputPengaduanActivity extends AppCompatActivity {

    TextView txtgambar, txtAlasan, txtLokasiSatwa, txtTelp;
    Button btnSend;
    ImageButton btnUpload;
    private Bitmap bitmap;
    private String imageName;

    private static final int GALLERY_1 = 11;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pengaduan);

        txtgambar = (TextView) findViewById(R.id.txtUrlLapor);
        txtAlasan = (TextView) findViewById(R.id.txtDeksripsiLapor);
        txtLokasiSatwa = (TextView) findViewById(R.id.txtLokasiSatwaLapor);
        txtTelp = (TextView) findViewById(R.id.txtTelpPelapor);
        btnSend = (Button) findViewById(R.id.btnInputPengaduan);
        btnUpload = findViewById(R.id.bt_upload);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    //main logic or main code
                    openGallery();
                    // . write your main code to execute, It will execute if the permission is already given.
                } else {
                    requestPermission();
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent i=new Intent(getBaseContext(), LoginAdminActivity.class);
//                startActivity(i);

                if (txtgambar.getText().toString().isEmpty() ||
                        txtAlasan.getText().toString().isEmpty() ||
                        txtLokasiSatwa.getText().toString().isEmpty() ||
                        txtTelp.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), "silahkan lengkapi data", Toast.LENGTH_LONG).show();
                } else {
                    String url = MainActivity.basic_url + "tambah-pengaduan";
                    Log.d("deb adu URL", url);

                    VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                            new Response.Listener<NetworkResponse>() {
                                @Override
                                public void onResponse(NetworkResponse response) {
                                    String resultResponse = new String(response.data);
                                    try {
                                        JSONObject data = new JSONObject(resultResponse);
                                        if (data.getBoolean("status")) {
                                            Toast.makeText(getApplicationContext(), "Data berhasil dikirim", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                    Log.e("GotError", "" + error.getMessage());
                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("lokasi_satwa", txtLokasiSatwa.getText().toString());
                            params.put("telepon", txtTelp.getText().toString());
                            params.put("alasan", txtAlasan.getText().toString());
                            return params;
                        }

                        @Override
                        protected Map<String, DataPart> getByteData() {
                            Map<String, DataPart> params = new HashMap<>();
                            params.put("image_upload", new DataPart(imageName, getFileDataFromDrawable(bitmap)));
                            return params;
                        }
                    };

                    //adding the request to volley
                    Volley.newRequestQueue(InputPengaduanActivity.this).add(volleyMultipartRequest);

//                    StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.d("deb respon", "ok");
//                            try {
//                                JSONObject data = new JSONObject(response);
//                                if (data.getString("result").equalsIgnoreCase("ok"))
//                                {
//                                    Log.d("deb URL", "result ok");
//                                    Toast.makeText(getBaseContext(),"terima kasih, data sudah direkam!",Toast.LENGTH_LONG).show();
//                                    finish();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                            new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    Toast.makeText(getBaseContext(),"err"+error.toString(),Toast.LENGTH_LONG).show();
//                                }
//                            }
//                    );
//                    RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
//                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            String filePath = getPath(picUri);
            File file = new File(getPath(picUri));
            if (filePath != null) {
                try {
                    imageName = file.getName();
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                    txtgambar.setText(file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Gambar tidak ditemukan",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

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

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}