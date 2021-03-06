package net.agusharyanto.aplikasidatamahasiswa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivityServer extends AppCompatActivity {

    private ListView listViewMahasiswa;
    private Button buttonTambahData;


    private ArrayList<Mahasiswa> mahasiswaArrayList = new ArrayList<Mahasiswa>();
    private MahasiswaArrayAdapter mahasiswaArrayAdapter;

    private static  final int REQUEST_CODE_ADD =1;
    private static  final int REQUEST_CODE_EDIT =2;

    private ProgressDialog pDialog;



    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = MainActivityServer.this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        pDialog = new ProgressDialog(MainActivityServer.this);

        listViewMahasiswa = (ListView) findViewById(R.id.listViewMahasiswa);
        buttonTambahData = (Button) findViewById(R.id.buttonTambah);


        loadDataServerVolley();
        gambarDatakeListView();

        buttonTambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFormMahasiswa();
            }
        });

    }

    private void loadDataServerVolley(){

        String url = GlobalVar.IP_SERVER+"/mahasiswa/listdata.php";
        pDialog.setMessage("Retieve Data Mahasiswa...");
        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("tag","response:"+response);
                        hideDialog();
                        processResponse(response);
                       gambarDatakeListView();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                //params.put("nim","1002");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void processResponse(String response){

        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONArray jsonArray = jsonObj.getJSONArray("data");
            Log.d("TAG", "data length: " + jsonArray.length());
            Mahasiswa objectmahasiswa = null;
            mahasiswaArrayList.clear();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                objectmahasiswa= new Mahasiswa();
                objectmahasiswa.setId(obj.getString("id"));

                objectmahasiswa.setNama(obj.getString("nama"));
                objectmahasiswa.setNim(obj.getString("nim"));
                objectmahasiswa.setJurusan(obj.getString("jurusan"));


                mahasiswaArrayList.add(objectmahasiswa);
            }

        } catch (JSONException e) {
            Log.d("MainActivity", "errorJSON");
        }

    }
    private  void gambarDatakeListView(){
        mahasiswaArrayAdapter = new MahasiswaArrayAdapter(context, R.layout.row_mahasiswa, mahasiswaArrayList);
        listViewMahasiswa.setAdapter(mahasiswaArrayAdapter);


        listViewMahasiswa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Mahasiswa mahasiswa = (Mahasiswa) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivityServer.this, MahasiswaActivity.class);
                intent.putExtra("mahasiswa",mahasiswa);
                startActivityForResult(intent,REQUEST_CODE_EDIT);

            }
        });
    }
    private void openFormMahasiswa(){
        Intent intent = new Intent(MainActivityServer.this, MahasiswaActivity.class);
        startActivityForResult(intent,REQUEST_CODE_ADD);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mahasiswa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD: {
                if (resultCode == RESULT_OK && null != data) {
                    if (data.getStringExtra("refreshflag").equals("1")) {
                       loadDataServerVolley();
                        gambarDatakeListView();
                    }
                }
                break;
            }
            case REQUEST_CODE_EDIT: {
                if (resultCode == RESULT_OK && null != data) {
                    if (data.getStringExtra("refreshflag").equals("1")) {
                        loadDataServerVolley();
                        gambarDatakeListView();
                    }
                }
                break;
            }
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
