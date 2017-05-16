package net.agusharyanto.aplikasidatamahasiswa;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by agus on 5/16/17.
 */

public class MahasiswaArrayAdapter extends ArrayAdapter<Mahasiswa>{

    private Context context;

    public MahasiswaArrayAdapter(Context context, int resourceId,
                                 List<Mahasiswa> mahasiswas) {
        super(context, resourceId, mahasiswas);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {

        TextView txtNIM;
        TextView txtNama;
        TextView txtJurusan;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Mahasiswa mahasiswa = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_mahasiswa, null);
            holder = new ViewHolder();
            holder.txtNIM = (TextView) convertView.findViewById(R.id.textViewRowNim);
            holder.txtNama = (TextView) convertView.findViewById(R.id.textViewRowNama);
            holder.txtJurusan = (TextView) convertView.findViewById(R.id.textViewRowJurusan);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtNIM.setText(mahasiswa.getNim());
        holder.txtNama.setText(mahasiswa.getNama());
        holder.txtJurusan.setText(mahasiswa.getJurusan());

        return convertView;
    }



}
