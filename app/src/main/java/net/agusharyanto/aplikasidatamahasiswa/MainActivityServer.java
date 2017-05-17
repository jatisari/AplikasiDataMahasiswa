package net.agusharyanto.aplikasidatamahasiswa;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivityServer extends AppCompatActivity {

    private ListView listViewMahasiswa;
    private Button buttonTambahData;


    private ArrayList<Mahasiswa> mahasiswaArrayList = new ArrayList<Mahasiswa>();
    private MahasiswaArrayAdapter mahasiswaArrayAdapter;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private static  final int REQUEST_CODE_ADD =1;
    private static  final int REQUEST_CODE_EDIT =2;


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

        listViewMahasiswa = (ListView) findViewById(R.id.listViewMahasiswa);
        buttonTambahData = (Button) findViewById(R.id.buttonTambah);

        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
        mahasiswaArrayList = databaseHelper.getDataMahasiswa(db);
        gambarDatakeListView();

        buttonTambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFormMahasiswa();
            }
        });

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
                        mahasiswaArrayList = databaseHelper.getDataMahasiswa(db);
                        gambarDatakeListView();
                    }
                }
                break;
            }
            case REQUEST_CODE_EDIT: {
                if (resultCode == RESULT_OK && null != data) {
                    if (data.getStringExtra("refreshflag").equals("1")) {
                        mahasiswaArrayList = databaseHelper.getDataMahasiswa(db);
                        gambarDatakeListView();
                    }
                }
                break;
            }
        }
    }
}
