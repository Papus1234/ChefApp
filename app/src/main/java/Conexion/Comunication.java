package Conexion;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tec.chefapp.RecipeActivity;

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
import java.io.UnsupportedEncodingException;

public class Comunication
{
    public Context context;
    String URL;
    public HttpClient httpclient = new DefaultHttpClient();

    public Comunication(){
    }
    public void conect(String ip, String port,String path) {
        URL = "http://"+ip+":"+port+"/ProyectoServer/webapi/Cheff/"+path;
        StrictMode.ThreadPolicy p = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(p);
        StrictMode.enableDefaults();
//      HttpPost httppost=new HttpPost(URL);
//      httppost.setHeader("Content-Type","application/json");
//      JSONArray cheffs = new JSONArray(result)
//      HttpEntity entityy=new StringEntity(cheff.toString());
//      httppost.setEntity(entityy);
    }
    public void setContext(Context context){
        this.context=context;
    }
    public void postPlatillo(Platillo platillo) throws JSONException, IOException {
        JSONObject JO = new JSONObject();
        JO.put("nombre",platillo.getNombre());
        JO.put("informacion_nutricional",platillo.getInformacion_nutricional());
//      JO.put("receta",platillo.getReceta().getPasos());
//      JO.put("ingredientes",platillo.getIngredientes());
        post(JO);
    }
    public void post(JSONObject JSONobject) throws JSONException, IOException
    {
//      HttpEntity entityy=new StringEntity(cheff.toString());
//      httppost.setEntity(entityy);
        HttpPost request = new HttpPost(URL);
        request.addHeader("content-type", "application/json");
        StringEntity params = new StringEntity(JSONobject.toString());
        request.setEntity(params);
        httpclient.execute(request);

        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();

        String responseText = EntityUtils.toString(entity);
        CharSequence text1 = responseText;
        Toast toast = Toast.makeText(context, text1, Toast.LENGTH_LONG);
        toast.show();
    }
    public void get(){

    }
}
