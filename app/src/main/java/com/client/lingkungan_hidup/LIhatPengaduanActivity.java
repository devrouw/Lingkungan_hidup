package com.client.lingkungan_hidup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LIhatPengaduanActivity extends AppCompatActivity {

    ArrayList<Pengaduan> pengaduans = new ArrayList<>();
    ArrayList<String> wordList = new ArrayList<>();
    ListView listviewPengaduan;
    ImageView imgbtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_ihat_pengaduan);
        imgbtnLogout = (ImageView)findViewById(R.id.imgBtnKeluar_pengaduan);
        listviewPengaduan = (ListView)findViewById(R.id.listViewPengaduan);
        Add();

    }

    private void UpdateList(){
        listviewPengaduan.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, wordList));
        listviewPengaduan.setSelected(true);

        listviewPengaduan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final Pengaduan data = pengaduans.get(arg2);
                AlertDialog.Builder builder = new AlertDialog.Builder(LIhatPengaduanActivity.this);
                builder.setTitle("Informasi");
                builder.setMessage("Tanggal: " + data.tanggal + "\n\n"
                        + "Lokasi: " + data.lokasi_satwa + ". \n\n"
                        + "Detail: " + data.alasan + ". \n\n"
                        + "Status: " + data.status);
                builder.create().show();
            }
        });

        imgbtnLogout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });



        AlertDialog.Builder builder = new AlertDialog.Builder(LIhatPengaduanActivity.this);
    }

    private void Add() {
        String url = MainActivity.basic_url+"getPengaduan.php";
        Log.d("deb URL",url);
        pengaduans.clear();
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("deb respon", "ok");
                try {
                    JSONObject data = new JSONObject(response);
                    if (data.getString("result").equalsIgnoreCase("ok"))
                    {
                        Log.d("deb URL", "result ok");
                        JSONArray array = data.getJSONArray("data");
                        for (int j=0; j<array.length(); j++){
                            JSONObject def = array.getJSONObject(j);
                            pengaduans.add(new Pengaduan(def.getString("id"),
                                    def.getString("gambar"),
                                    def.getString("alasan"),
                                    def.getString("lokasi_satwa"),
                                    def.getString("telepon"),
                                    def.getString("tanggal"),
                                    def.getString("status")));
                            wordList.add(def.getString("alasan"));
                        }
                    }
                    UpdateList();
                    Log.d("debug", pengaduans.toString());
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
        Log.d("deb sats size", pengaduans.size()+"");
    }

}