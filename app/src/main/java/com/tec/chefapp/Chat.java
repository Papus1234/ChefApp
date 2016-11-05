package com.tec.chefapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class Chat extends AppCompatActivity{

    private Button enviar;
    TextView chat;
    HiloChat actualizarChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final String emailChef = MainActivity.email;
        final String direccionIp = JoinActivity.ip_number;
        final EditText mensaje = (EditText)findViewById(R.id.mensaje);
        final TextView chat = (TextView)findViewById(R.id.chat);
        actualizarChat = new HiloChat(direccionIp);
        actualizarChat.execute();
        StrictMode.ThreadPolicy p = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(p);
        StrictMode.enableDefaults();
        enviar = (Button)findViewById(R.id.enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try {
                        chat.setText(chat.getText().toString()+"\n"+emailChef+":"+mensaje.getText().toString());
                        JSONObject envioMensaje = new JSONObject();
                        envioMensaje.put("id",emailChef);
                        envioMensaje.put("message",mensaje.getText().toString());
                        StringEntity entity = new StringEntity(envioMensaje.toString());
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://"+JoinActivity.ip_number+":"+JoinActivity.puerto_number+"/ProyectoServer/webapi/Cheff/Chat");
                        httppost.setHeader("Content-Type", "application/json");
                        httppost.setEntity(entity);
                        HttpResponse resp = httpclient.execute(httppost);
                        mensaje.setText("");
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(),"Fallo al enviar mensaje",Toast.LENGTH_LONG).show();
                    }
            }
                });
    }
    public class HiloChat extends AsyncTask<Void, String, Void>
    {
        String chatInicial = "";
        String direccionIp;

        public HiloChat(String Ip) {
            direccionIp = Ip;
        }

        @Override

        protected void onPreExecute()
        {
            TextView chat = (TextView)findViewById(R.id.chat);
            chat.setText(chatInicial);
        }
        @Override
        protected void onProgressUpdate(String... actualizar)
        {
            TextView chat = (TextView)findViewById(R.id.chat);
            chat.setText(chatInicial);
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (!isCancelled())
            {
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet httpget = new HttpGet("http://"+JoinActivity.ip_number+":"+JoinActivity.puerto_number+"/ProyectoServer/webapi/Cheff/Chat");
                    httpget.addHeader("Content-Type", "application/json");
                    HttpResponse response = httpclient.execute(httpget);
                    HttpEntity ent = response.getEntity();
                    String comentarios = EntityUtils.toString(ent);
                    JSONObject respJSON = new JSONObject(comentarios);
                    if (respJSON.get("message") != null) {
                        chatInicial = respJSON.get("message").toString();
                    }
                } catch (Exception e) {

                }
                publishProgress(chatInicial);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }
}

