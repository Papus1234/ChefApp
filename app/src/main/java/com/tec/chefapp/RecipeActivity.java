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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.io.EOFException;
import java.io.IOException;

import Conexion.Comunication;
import datastructures.Lista;

public class RecipeActivity extends AppCompatActivity{

    public Platillo platillo = new Platillo();
    EditText editText;
    Spinner spinner;
    Button button;
    Comunication comunication;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        comunication=new Comunication();
        comunication.conect("172.26.109.73","8080","GuardarPlatillo");
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

            }
        });

        FloatingActionButton addIngredientes = (FloatingActionButton) findViewById(R.id.addIngredientes);
        addIngredientes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

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

                try{
                    comunication.postPlatillo(platillo);
                }catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
                    e.printStackTrace();
                }

                LISessionManager.getInstance(getApplicationContext()).clearSession();
                Intent intent = new Intent(RecipeActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}

