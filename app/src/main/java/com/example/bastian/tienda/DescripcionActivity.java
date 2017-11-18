package com.example.bastian.tienda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DescripcionActivity extends BaseActivity implements View.OnClickListener{


    Integer[] imageId = {
            R.drawable.polera1,
            R.drawable.polera2,
            R.drawable.polera3,
            R.drawable.polera4,
            R.drawable.polera5
    };

    Button verCarro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        ImageView img = (ImageView)findViewById(R.id.img);
        TextView titulo = (TextView)findViewById(R.id.titulo);
        TextView descripcion = (TextView)findViewById(R.id.descripcion);
        TextView precio = (TextView)findViewById(R.id.precio);

        String textTitle = getIntent().getExtras().getString("titulo");
        String textDescripcion = getIntent().getExtras().getString("descripcion");
        String textPrecio = getIntent().getExtras().getString("precio");

        titulo.setText(textTitle);
        descripcion.setText(textDescripcion);
        precio.setText("$"+textPrecio);
        img.setImageResource(imageId[getIntent().getExtras().getInt("image")]);

        verCarro = (Button)findViewById(R.id.agregar2);
        verCarro.setOnClickListener(this);

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.back_left, R.anim.back_right);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(verCarro)){
            Intent intent = new Intent(this, CarroActivity.class);
            startActivity(intent);
        }
    }
}
