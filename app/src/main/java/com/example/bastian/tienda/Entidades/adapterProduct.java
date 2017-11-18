package com.example.bastian.tienda.Entidades;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bastian.tienda.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by osplaza on 18-11-17.
 */

public class adapterProduct extends ArrayAdapter<producto> {
    public adapterProduct(Context context, ArrayList<producto> contactList) {
        super(context,0,contactList);
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent)
    {
        Log.d("TAG", "getView: ");
        producto user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
        }
        String urlImage = "https://bastianvastian.000webhostapp.com/img/"+user.getImagen();
        ImageView Img = (ImageView) convertView.findViewById(R.id.img);
        TextView tvnombre = (TextView) convertView.findViewById(R.id.txtnombre);
        TextView tvPresio = (TextView) convertView.findViewById(R.id.txtpresio);
        Picasso.with(getContext()).load(urlImage).into(Img);

        tvnombre.setText(user.nombre_producto);
        tvPresio.setText(user.precio);
        return convertView;

    }
}
