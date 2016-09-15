package com.dam.kevoundfreunde.workfromhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Juxtar on 08/09/2016.
 */
public class RowAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Trabajo[] listTrabajos;

    RowAdapter(Trabajo[] trabajos, Context context){
        super();
        inflater = LayoutInflater.from(context);
        listTrabajos = trabajos;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.row_layout, parent, false);

        TextView puesto, corporation, details, deadline;
        ImageView flag;
        CheckBox english;

        puesto = (TextView) row.findViewById(R.id.textPuesto);
        corporation = (TextView) row.findViewById(R.id.textCorporation);
        details = (TextView) row.findViewById(R.id.textDetails);
        deadline = (TextView) row.findViewById(R.id.textDeadline);
        flag = (ImageView) row.findViewById(R.id.imgFlag);
        english = (CheckBox) row.findViewById(R.id.checkEnglish);


        puesto.setText(listTrabajos[position].getCategoria().getDescripcion());
        details.setText("ARREGLAR HORAS Y PAGO TODO");
        /* corporation.setText(listTrabajos[position].getDescripcion();
        deadline.setText(SimpleDateFormat("AAAA-MM-DD",listTrabajos[position].getFechaEntrega()));
        details.setText(listTrabajos[position].getCategoria().getDescripcion());
        flag.setImageIcon(listTrabajos[position].getCategoria().getDescripcion());
        english.setText(listTrabajos[position].getCategoria().getDescripcion());
*/ // TODO: 08/09/2016 ARREGLAR HORAS Y PAGO


        return row;
    }
}
