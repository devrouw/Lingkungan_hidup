package com.client.lingkungan_hidup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ManipulateSatwaActivity extends AppCompatActivity {
    EditText editTextNama, editTextSpesies, editTextAsal, editTextDesk, editTextGambar;
    TextView txtJudul;
    Button btnProses;
    String strId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate_satwa);

        editTextNama = (EditText)findViewById(R.id.txtNamaSatwaManipulate);
        editTextSpesies = (EditText)findViewById(R.id.txtSpesiesManipulate);
        editTextDesk = (EditText)findViewById(R.id.txtDescSatwaManipulate);
        editTextGambar = (EditText)findViewById(R.id.txtURLGambarSatwaManipulate);
        editTextAsal = (EditText)findViewById(R.id.txtAsalmanipulate);
        txtJudul = (TextView)findViewById(R.id.txtManipulateJudul);
        btnProses = (Button)findViewById(R.id.btnSelesaiManipulate);

        final String strCommand = getIntent().getStringExtra("command");

        if (strCommand.equalsIgnoreCase("edit")){

            strId = getIntent().getStringExtra("id");
            String strNama = getIntent().getStringExtra("nama");
            String strSpesies = getIntent().getStringExtra("spesies");
            String strAsal = getIntent().getStringExtra("asal");
            String strDesk = getIntent().getStringExtra("desk");
            String strGambar = getIntent().getStringExtra("gambar");

            txtJudul.setText("Edit Satwa");
            editTextNama.setText(strNama);
            editTextAsal.setText(strAsal);
            editTextSpesies.setText(strSpesies);
            editTextDesk.setText(strDesk);
            editTextGambar.setText(strGambar);

        }
        else if (strCommand.equalsIgnoreCase("add")){
            txtJudul.setText("Tambah Satwa");
        }

        btnProses.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (strCommand.equalsIgnoreCase("edit")){ //edit

                    String url = MainActivity.basic_url+"editSatwa.php?"+
                            "nama="+editTextNama.getText().toString() +
                            "&spesies=" + editTextSpesies.getText().toString() +
                            "&asal=" + editTextAsal.getText().toString() +
                            "&deskripsi=" + editTextDesk.getText().toString() +
                            "&gambar=" + editTextGambar.getText().toString() +
                            "&id=" + strId;
                    Log.d("deb edit satwa URL",url);

                    StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("deb respon", "ok");
                            try {
                                JSONObject data = new JSONObject(response);
                                if (data.getString("result").equalsIgnoreCase("ok"))
                                {
                                    Log.d("deb URL", "result ok");
                                    Toast.makeText(getBaseContext(),"Data satwa diedit!",Toast.LENGTH_LONG).show();
                                    finish();
                                    Intent i = new Intent(getBaseContext(), ListSatwaAdminActivity.class);
                                    startActivity(i);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getBaseContext(),"err"+error.toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                    );
                    RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                    requestQueue.add(stringRequest);
                }
                else{ //add
                    String url = MainActivity.basic_url+"inputSatwa.php?"+
                            "nama="+editTextNama.getText().toString() +
                            "&spesies=" + editTextSpesies.getText().toString() +
                            "&asal=" + editTextAsal.getText().toString() +
                            "&deskripsi=" + editTextDesk.getText().toString() +
                            "&gambar=" + editTextGambar.getText().toString();
                    Log.d("deb adu URL",url);

                    StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("deb respon", "ok");
                            try {
                                JSONObject data = new JSONObject(response);
                                if (data.getString("result").equalsIgnoreCase("ok"))
                                {
                                    Log.d("deb URL", "result ok");
                                    Toast.makeText(getBaseContext(),"Data satwa ditambah!",Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(getBaseContext(),"err"+error.toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                    );
                    RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                    requestQueue.add(stringRequest);
                }
            }
        });

    }
}