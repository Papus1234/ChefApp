package com.tec.chefapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rest.ProyectoServer.models.Ingrediente;
import org.rest.ProyectoServer.models.Ingredientes;
import org.rest.ProyectoServer.models.Platillo;
import org.rest.ProyectoServer.models.Receta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Comunication;

public class RecipesActivity extends AppCompatActivity {

    public ArrayList<String> MenuOptions = new ArrayList<>();
    public int item=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        try {
            populateList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ClickCallback();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipesActivity.this, RegisterRecipeActivity.class);
                startActivity(intent);
            }
        });
    }

    public List<Platillo> FixIt (String pls) throws JSONException,IOException{
        JSONArray jsonarray = new JSONArray(pls);
        List<Platillo>platillo=new ArrayList<>();
        ArrayList<String> MenuO = new ArrayList<>();

        for (int i = 0; i < jsonarray.length(); i++) {
            Platillo aux = new Platillo();
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            String nutri = jsonobject.getString("informacion_nutricional");

            System.err.println(nutri);

            aux.setInformacion_nutricional(nutri);
            String nombre = jsonobject.getString("nombre");

            //Ingredientes ingre = (Ingredientes) jsonobject.get("ingredientes");

            //aux.setIngredientes(ingre);
            System.err.println(nombre);
            MenuO.add(nombre);

            aux.setNombre(nombre);
            int precio = jsonobject.getInt("precio");

            System.err.println(precio);

            aux.setPrecio(precio);

            JSONObject receta = jsonobject.getJSONObject("receta");

            String recet = receta.getString("textoPasos");

            aux.setReceta(new Receta(recet));

            System.err.println(aux.getReceta().getPasos());

            int time = jsonobject.getInt("tiempo_de_preparacion");
            aux.setTiempo_de_preparacion(time);


            System.err.println(aux.getTiempo_de_preparacion());
            System.err.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");

            platillo.add(i, aux);

        }
        System.err.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return platillo;
    }
    private void populateList() throws IOException, JSONException {
        Comunication c= new Comunication();
        c.conect(JoinActivity.ip_number,JoinActivity.puerto_number,"");
        c.setContext(getApplicationContext());
        List<Platillo> platillos=FixIt(c.get("Platillos"));
        for(int i=0;i<platillos.size();i++){
            MenuOptions.add("Receta número "+Integer.toString(i+1)+":"+"\nNombre: "+platillos.get(i).getNombre()+"\nInformación nutricional: "+platillos.get(i).getInformacion_nutricional()+"\nIngredientes: "+platillos.get(i).getIngredientes()+"\nPasos: "+platillos.get(i).getReceta().getPasos()+"\nTiempo de preparación: "+platillos.get(i).getTiempo_de_preparacion()+"\nPrecio: "+platillos.get(i).getPrecio());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.recipe_template, MenuOptions);
        //configurar el ListView para utilizar los items creados con el contructor
        ListView list = (ListView) findViewById(R.id.recipe_view);
        list.setAdapter(adapter);
    }
    private void ClickCallback(){
        ListView list = (ListView) findViewById(R.id.recipe_view);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RecipesActivity.this,"Item "+position+" selected", Toast.LENGTH_LONG).show();
            }
        });
    }
}
