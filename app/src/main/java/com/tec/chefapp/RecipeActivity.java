package com.tec.chefapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import datastructures.Lista;

public class RecipeActivity extends AppCompatActivity{
    LinearLayoutCompat content;
    EditText editText;
    TextView textView;
    Lista<EditText> editTextLista;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipe);
        FloatingActionButton addStep = (FloatingActionButton) findViewById(R.id.addStep);
        addStep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                conexión();
            }
        });
    }
    public void conexión()
    {
        String restURL="http://172.26.109.180:8080/ProyectoServer/webapi/Cheff";
        //new RestOperation().execute(restURL);
        String result = "";
        HttpClient httpclient = new DefaultHttpClient();
        //HttpResponse response = httpclient.execute(new HttpGet(URL));
        //HttpBackground a =new HttpBackground();

        StrictMode.ThreadPolicy p = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(p);
        StrictMode.enableDefaults();
        //a.doInBackground(URL);
        HttpPost httppost=new HttpPost(restURL);
        httppost.setHeader("Content-Type","application/json");
        //JSONArray cheffs = new JSONArray(result);

        HttpEntity entity=new StringEntity(cheff.toString());
        httppost.setEntity(entity);

        try{
            HttpResponse resp=httpclient.execute(httppost);
            HttpEntity ent=resp.getEntity();
            String text= EntityUtils.toString(ent);
            System.out.print(text);
        }catch (Exception e){
            e.printStackTrace();
        }
    }





    private class RestOperation extends AsyncTask<String,Void,Void> {
        String content,error;
        ProgressDialog progressDialog= new ProgressDialog(RecipeActivity.this);
        String data;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            editText = (EditText) findViewById(R.id.nombre_receta_editText);
            textView = (TextView) findViewById(R.id.nombre_receta_textView);
            progressDialog.setTitle("Porfavor espere...");
            progressDialog.show();

            try{
                data += "&"+ URLEncoder.encode("data","UTF-8")+"-"+ editText.getText();
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            BufferedReader br=null;
            URL url;
            try{
                url =new URL(params[0]);
                URLConnection connection=url.openConnection();
                connection.setDoOutput(true);

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
                outputStreamWriter.write(data);
                outputStreamWriter.flush();

                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line=br.readLine())!=null){
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                }

                content = sb.toString();
            }catch (MalformedURLException e){
                error= e.getMessage();
                e.printStackTrace();
            } catch (IOException e) {
                error=e.getMessage();
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if(error!=null){

            }else{
                String output="";
                JSONObject jsonResponse;
                try {
                    jsonResponse=new JSONObject(content);
                    JSONArray jsonArray = jsonResponse.optJSONArray("Android");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject child = jsonArray.getJSONObject(i);
                        String nombre =child.getString("nombre");
                        String edad=child.getString("edad");

                        output = "Nombre: "+nombre+System.getProperty("line.separator")+"Edad: "+edad+System.getProperty("line.separator");
                        output += System.getProperty("line.separator");
                    }
                    textView.setText(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
