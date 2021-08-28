package com.client.lingkungan_hidup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LokasiWisataActivity extends AppCompatActivity {

    ArrayList<Lokasi> lokasiWisatas = new ArrayList<>();
    private ArrayList<String> wordList = new ArrayList<>();

    Button btnBack, btnBuatPengaduan;
    ListView listLokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_wisata);

        btnBack = (Button)findViewById(R.id.btnBackLokWisata);
        btnBuatPengaduan = (Button)findViewById(R.id.btnBuatPengaduanLokWisata);
        listLokasi = (ListView)findViewById(R.id.listViewLokasi);


        btnBuatPengaduan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(), InputPengaduanActivity.class);
                startActivity(i);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Add();


    }

    private void UpdateList(){
        listLokasi.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, wordList));
        listLokasi.setSelected(true);
        listLokasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                Lokasi data = lokasiWisatas.get(arg2);
                Intent i = new Intent(getBaseContext(), DetailLokasiActivity.class);
                i.putExtra("nama_lokasi", data.nama_lokasi);
                i.putExtra("alamat",  data.alamat);
                i.putExtra("longitude", data.longitude);
                i.putExtra("latitude", data.latitude);
                startActivity(i);
            }
        });
    }

    private void Add() {
        String url = MainActivity.basic_url+"list-lokasi";
        Log.d("deb URL",url);
        lokasiWisatas.clear();
        wordList.clear();
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
                            lokasiWisatas.add(new Lokasi(def.getString("id"),
                                    def.getString("nama_lokasi"),
                                    def.getString("alamat"),
                                    def.getString("longitude"),
                                    def.getString("latitude")));
                            wordList.add(def.getString("nama_lokasi"));
                        }
                    }
                    UpdateList();
                    Log.d("debug", lokasiWisatas.toString());
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
        Log.d("deb sats size", lokasiWisatas.size()+"");
    }
}