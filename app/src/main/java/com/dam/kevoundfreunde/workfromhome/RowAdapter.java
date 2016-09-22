package com.dam.kevoundfreunde.workfromhome;

import android.content.Context;
import android.graphics.drawable.Icon;
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
    Context contexto;

    RowAdapter(Trabajo[] trabajos, Context context){
        super();
        inflater = LayoutInflater.from(context);
        listTrabajos = trabajos;
        contexto = context;

    }

    @Override
    public int getCount() {
        return listTrabajos.length;
    }

    @Override
    public Object getItem(int position) {
        return listTrabajos[position];
    }

    @Override
    public long getItemId(int position) {
        return listTrabajos[position].getId();
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
        details.setText(String.format("Horas: %1$d Max $/Hora: %2$.2f",
                listTrabajos[position].getHorasPresupuestadas(),
                listTrabajos[position].getPrecioMaximoHora()));
        corporation.setText(listTrabajos[position].getDescripcion());
        deadline.setText(new SimpleDateFormat("yyyy-MM-dd")
                .format(listTrabajos[position].getFechaEntrega()));
        flag.setImageIcon(designarFlag(listTrabajos[position].getMonedaPago()));
        english.setChecked(listTrabajos[position].getRequiereIngles());


        return row;
    }

    private Icon designarFlag(Integer monedaPago) {
        int idFlag = 0;
        switch (monedaPago){
            case 1:
                idFlag = R.drawable.us;
                break;
            case 2:
                idFlag = R.drawable.eu;
                break;
            case 3:
                idFlag = R.drawable.ar;
                break;
            case 4:
                idFlag = R.drawable.uk;
                break;
            case 5:
                idFlag = R.drawable.br;
                break;
        }
        return Icon.createWithResource(contexto, idFlag);
    }
}
