package com.tec.chefapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.LISessionManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.rest.ProyectoServer.models.Platillo;

import java.io.IOException;

import Conexion.Comunication;

public class RecipeActivity extends AppCompatActivity{

    public Platillo platillo = new Platillo();
    EditText editText, editText1;
    String categoria;
    String[] categorias={"Fruta","Vegetal","Carne","Lacteo","Grano"};
    Spinner spinner;
    Button button;
    Comunication comunication;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        comunication=new Comunication();
        comunication.conect(JoinActivity.ip_number,JoinActivity.puerto_number,"GuardarPlatillo");
        comunication.setContext(getApplicationContext());

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipe);

        FloatingActionButton addStep = (FloatingActionButton) findViewById(R.id.addStep);
        addStep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                editText = (EditText) findViewById(R.id.pasos_editText);
                platillo.getReceta().getPasos().add(editText.getText().toString());
                Toast toast = Toast.makeText(getApplicationContext(), "El paso "+platillo.getReceta().getPasos().size()+" ha sido añadido", Toast.LENGTH_LONG);
                toast.show();
                editText.setText("");
            }
        });

        FloatingActionButton addIngredientes = (FloatingActionButton) findViewById(R.id.addIngredientes);
        addIngredientes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                editText = (EditText) findViewById(R.id.ingredientes_editText);
                editText1 = (EditText) findViewById(R.id.cantidad_ingrediente_editText);
                platillo.getIngredientes().Registrar(editText.getText().toString(),categoria,Integer.parseInt(editText1.getText().toString()));
                Toast toast = Toast.makeText(getApplicationContext(),"Ingrediente: "+editText.getText().toString()+"\nCategoría: "+categoria+"\nCantidad: "+Integer.parseInt(editText1.getText().toString())+"\nSe ha añadido el ingrediente", Toast.LENGTH_LONG);
                toast.show();
                editText.setText("");
                editText1.setText("");
            }
        });

        button = (Button) findViewById(R.id.send_recipe_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText= (EditText) findViewById(R.id.nombre_receta_editText);
                platillo.setNombre(editText.getText().toString());
                editText= (EditText) findViewById(R.id.informacion_nutricional_editText);
                platillo.setInformacion_nutricional(editText.getText().toString());
                editText= (EditText) findViewById(R.id.precio_editText);
                platillo.setPrecio(Integer.parseInt(editText.getText().toString()));
                try{
                    comunication.postPlatillo(platillo);
                }catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(RecipeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        spinner = (Spinner) findViewById(R.id.categoria_ingredientes);;
        SpinnerAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                categoria=(String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }
}

