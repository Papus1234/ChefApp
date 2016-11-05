package com.tec.chefapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.rest.ProyectoServer.models.Platillo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Comunication;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String topCardUrl = "https://api.linkedin.com/v1/people/~:(email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";
    ProgressDialog progress;
    ImageView profile_pic;
    TextView user_name, user_email;
    NavigationView navigation_view;
    Button logout;
    static String email="";
    public ArrayList<String> Options=new ArrayList<String>();
    //Método principal en donde se define todo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        logout = (Button) findViewById(R.id.logout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ordenes");
        progress = new ProgressDialog(this);
        progress.setMessage("Retrieve data...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        getUserData(); //Método definido por mi

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setNavigationHeader(); //Método definido por mi
        navigation_view.setNavigationItemSelectedListener(this);

        //maneja la acción del botón logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LISessionManager.getInstance(getApplicationContext()).clearSession();
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                finish();
                startActivity(intent);
            }
        });
        populateList();
        ClickCallback();
    }
    public void populateList(){
        Options.add("1");
        Options.add("2");
        Options.add("3");
        Options.add("4");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.order_template, Options);
        //configurar el ListView para utilizar los items creados con el contructor
        ListView list = (ListView) findViewById(R.id.ordenes_view);
        list.setAdapter(adapter);
    }
    public void ClickCallback(){
        ListView list = (ListView) findViewById(R.id.ordenes_view);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Item "+position+" selected", Toast.LENGTH_LONG).show();
            }
        });
    }
    //La información del ususario es obtenida mediante paquetes JSON enviados por el servidor de LinkedIn
    public void getUserData()
    {
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(MainActivity.this, topCardUrl, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                try {
                    setUserProfile(apiResponse.getResponseDataAsJson());
                    progress.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onApiError(LIApiError LIApiError) {

            }
        });
    }
    //Añade a la barra de acción, el nombre, email y foto del usuario de LinkedIn
    public void  setNavigationHeader(){
        navigation_view = (NavigationView) findViewById(R.id.nav_view);

        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main,null);
        navigation_view.addHeaderView(header);

        user_name = (TextView) header.findViewById(R.id.username);
        user_email = (TextView) header.findViewById(R.id.email);
        profile_pic = (ImageView) header.findViewById(R.id.profile_pic);
    }
    //Asigna los datos a mostrar, oteniéndolos de las respuestas del servidor de LinkedIn, mediante un JSON
    public void setUserProfile(JSONObject response){
        try{
            user_email.setText(response.get("emailAddress").toString());
            email=response.get("emailAddress").toString();
            user_name.setText(response.get("formattedName").toString());
            Picasso.with(this).load(response.getString("pictureUrl")).into(profile_pic);
        } catch (Exception e){

            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //Añade items a la barra de acción
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //Maneja la acción del botón que muestra la barra de acción, además la esconde si se presiona la tecla back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
    //Maneja las acciones presentes en la barra de acción
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_chat){
            Intent intent = new Intent(MainActivity.this, Chat.class);
            startActivity(intent);
        }else if (id == R.id.nav_recipe){
            Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_inventory) {
            Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
