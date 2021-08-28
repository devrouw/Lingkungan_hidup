package com.client.lingkungan_hidup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListSatwaActivity extends AppCompatActivity {

    public static ArrayList<Satwa> satwas = new ArrayList<>();
    RecyclerView recviewSatwa;
    Button  btnBuatPengaduan, btnLihatLokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_satwa);
        recviewSatwa = (RecyclerView)findViewById(R.id.recViewSatwa);
        btnBuatPengaduan = (Button)findViewById(R.id.btnBuatPengaduan);
        btnLihatLokasi = (Button)findViewById(R.id.btnLihatLokasi);

        Add(); //load list


        btnBuatPengaduan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(), InputPengaduanActivity.class);
                startActivity(i);
            }
        });
        btnLihatLokasi.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(), LokasiWisataActivity.class);
                startActivity(i);
            }
        });
    }

    private void UpdateList(){
        LinearLayoutManager lm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recviewSatwa.setLayoutManager(lm);
        ListSatwaAdapter adp = new ListSatwaAdapter();
        recviewSatwa.setAdapter(adp);
    }

    private void Add() {
        String url = MainActivity.basic_url+"list-satwa";
        Log.d("deb URL",url);
        satwas.clear();
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("deb respon", "ok");
                try {
                    JSONObject data = new JSONObject(response);
                    if (data.getBoolean("status"))
                    {
                        Log.d("deb URL", "result ok");
                        JSONArray array = data.getJSONArray("data");
                        for (int j=0; j<array.length(); j++){
                            JSONObject def = array.getJSONObject(j);
                            satwas.add(new Satwa(def.getString("idSatwa"),
                                    def.getString("nama"),
                                    def.getString("spesies"),
                                    def.getString("asal"),
                                    def.getString("deskripsi"),
                                    def.getString("gambar")));
                        }
                    }
                    UpdateList();
                    Log.d("debug", satwas.toString());
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
        Log.d("deb sats size", satwas.size()+"");
    }
}