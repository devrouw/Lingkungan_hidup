package com.client.lingkungan_hidup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

public class ListSatwaAdminActivity extends AppCompatActivity {

    public static ArrayList<Satwa> satwas = new ArrayList<>();
    private ArrayList<String> wordList = new ArrayList<>();
    Button btnLihatPengaduan;
    ImageView imgbtnLogout;
    ListView listviewSatwa;
    FloatingActionButton fabAddSatwa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_satwa_admin);

        btnLihatPengaduan = (Button)findViewById(R.id.btnAdminLIhatPengaduan);
        imgbtnLogout = (ImageView)findViewById(R.id.imgBtnLogout);
        listviewSatwa = (ListView)findViewById(R.id.listviewAdminSatwa);
        fabAddSatwa = (FloatingActionButton)findViewById(R.id.fabAddSatwa);

        Add();

        fabAddSatwa.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(), ManipulateSatwaActivity.class);
                i.putExtra("command", "add");
                startActivity(i);
            }
        });

        btnLihatPengaduan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(), LIhatPengaduanActivity.class);
                startActivity(i);
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
    }
    private void UpdateList(){
        listviewSatwa.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, wordList));
        listviewSatwa.setSelected(true);
        listviewSatwa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final Satwa data = satwas.get(arg2);
                final CharSequence[] dialogitem = {"Edit Data", "Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ListSatwaAdminActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                Intent i = new Intent(getBaseContext(), ManipulateSatwaActivity.class);
                                i.putExtra("command", "edit");
                                i.putExtra("id", data.id);
                                i.putExtra("nama", data.nama);
                                i.putExtra("spesies",  data.spesies);
                                i.putExtra("asal", data.asal);
                                i.putExtra("desk", data.deskripsi);
                                i.putExtra("gambar", data.gambar);
                                startActivity(i);
                                break;
                            }
                            case 1: {
                                String url = MainActivity.basic_url+"hapusSatwa.php?&id=" + data.id;
                                Log.d("deb hapus satwa URL",url);

                                StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("deb respon", "ok");
                                        try {
                                            JSONObject data = new JSONObject(response);
                                            if (data.getString("result").equalsIgnoreCase("ok"))
                                            {
                                                Log.d("deb URL", "result ok");
                                                Toast.makeText(getBaseContext(),"Data satwa berhasil dihapus!",Toast.LENGTH_LONG).show();
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
                        }
                    }
                });
                builder.create().show();

            }
        });
    }

    private void Add() {
        String url = MainActivity.basic_url+"getSatwa.php";
        Log.d("deb URL",url);
        satwas.clear();
        wordList.clear();
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
                            satwas.add(new Satwa(def.getString("idSatwa"),
                                    def.getString("nama"),
                                    def.getString("spesies"),
                                    def.getString("asal"),
                                    def.getString("deskripsi"),
                                    def.getString("gambar")));
                            wordList.add(def.getString("nama"));
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