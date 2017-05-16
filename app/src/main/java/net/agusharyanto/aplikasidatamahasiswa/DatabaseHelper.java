package net.agusharyanto.aplikasidatamahasiswa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by agus on 5/16/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private final static String DATABASE_NAME = "dbmahasiswa";
    private final static int DATABASE_VERSION = 1;
    private final static String MAHASISWA_TABLE = "tbl_mahasiswa";
    private final static String FIELD_ID = "_id";
    private final static String FIELD_NIM = "nim";
    private final static String FIELD_NAMA = "nama";
    private final static String FIELD_JURUSAN = "jurusan";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creat table dan init data
        String SQL_CREATE_TABLE = "create table " + MAHASISWA_TABLE + " (" + FIELD_ID +
                " integer primary key autoincrement, "
                + FIELD_NIM + " text not null, " + FIELD_NAMA + " text not null,"
                + FIELD_JURUSAN + " text not null);";

        db.execSQL(SQL_CREATE_TABLE);
        initData(db);

    }

    private void initData(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_NIM, "201510001");
        contentValues.put(FIELD_NAMA, "Mika");
        contentValues.put(FIELD_JURUSAN, "TI");
        db.insert(MAHASISWA_TABLE, null, contentValues);
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(FIELD_NIM, "201510002");
        contentValues1.put(FIELD_NAMA, "Fatin");
        contentValues1.put(FIELD_JURUSAN, "SI");
        db.insert(MAHASISWA_TABLE, null, contentValues1);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(FIELD_NIM, "201510003");
        contentValues2.put(FIELD_NAMA, "Muhid");
        contentValues2.put(FIELD_JURUSAN, "TI");
        db.insert(MAHASISWA_TABLE, null, contentValues2);

    }

    public ArrayList<Mahasiswa> getDataMahasiswa(SQLiteDatabase db) {
        ArrayList<Mahasiswa> mahasiswaArrayList = new ArrayList<Mahasiswa>();
        String[] allColumns = {FIELD_ID, FIELD_NIM, FIELD_NAMA, FIELD_JURUSAN};

        Cursor cursor = db.query(MAHASISWA_TABLE, allColumns, null, null, null, null, null);
        // String sql ="select "+FIELD_ID+","+ FIELD_NIM+","+ FIELD_NAMA+","+ FIELD_JURUSAN+" " +
        //       " from "+MAHASISWA_TABLE+ " where _id=2";
        //Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Mahasiswa mahasiswa = cursorToMahasiswa(cursor);
            mahasiswaArrayList.add(mahasiswa);
            cursor.moveToNext();
        }

        return mahasiswaArrayList;

    }

    private Mahasiswa cursorToMahasiswa(Cursor cursor) {

        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setId(cursor.getString(0));
        mahasiswa.setNim(cursor.getString(1));
        mahasiswa.setNama(cursor.getString(2));
        mahasiswa.setJurusan(cursor.getString(3));
        //   Log.d("TAG", mahasiswa.toString());
        return mahasiswa;
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
