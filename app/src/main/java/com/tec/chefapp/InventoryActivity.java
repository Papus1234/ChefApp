package com.tec.chefapp;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Comunication;

public class InventoryActivity extends AppCompatActivity {

    public ArrayList<String> MenuOptions=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        try {
            populateList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ClickCallback();
    }
    public List<Ingrediente> FixIt (String pls) throws JSONException,IOException {
        JSONArray jsonarray = new JSONArray(pls);
        List<Ingrediente> ingrediente = new ArrayList<>();
        ArrayList<String> MenuO = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); i++) {
            Ingrediente aux=new Ingrediente();
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            aux.setCantidad(Integer.parseInt(jsonobject.getString("cantidad")));
            aux.setNombre(jsonobject.getString("nombre"));
            ingrediente.add(i,aux);
        }
        return  ingrediente;
    }
    public void populateList() throws IOException, JSONException {
        Comunication c= new Comunication();
        c.conect(JoinActivity.ip_number,JoinActivity.puerto_number,"");
        c.setContext(getApplicationContext());
        List<Ingrediente> ingredientes=FixIt(c.get("Ingredientes"));
        for(int i=0;i<ingredientes.size();i++){
            MenuOptions.add("Nombre: "+ingredientes.get(i).getNombre()+"\nCantidad: "+ingredientes.get(i).getCantidad());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.order_template, MenuOptions);
        //configurar el ListView para utilizar los items creados con el contructor
        ListView list = (ListView) findViewById(R.id.inventario_view);
        list.setAdapter(adapter);
    }
    public void ClickCallback(){
        ListView list = (ListView) findViewById(R.id.inventario_view);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(InventoryActivity.this,"Item "+position+" selected", Toast.LENGTH_LONG).show();
            }
        });
    }
}
