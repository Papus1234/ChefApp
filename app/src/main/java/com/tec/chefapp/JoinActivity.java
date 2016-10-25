package com.tec.chefapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.Image;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

public class JoinActivity extends AppCompatActivity {

    ImageButton join;
    //Esto es un método que me permite pasar a la siguiente activity si ingreso con mi cuenta de LinkedIn
    public void nextActivity(ImageButton button, final Activity previous, final Class next)
    {
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                login_linkedin();
            }
        });
    }
    //Método principal de la actividad, aquí se carga todo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_join);
        join= (ImageButton) findViewById(R.id.linkedin_button);
        nextActivity(join,this,MainActivity.class);
    }
    // Método que me permite validar si la autentificación de la cuenta de LinkedIn fue exitosa, de lo contrario muestra el código del error
    public void login_linkedin(){
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {

            }

            @Override
            public void onAuthError(LIAuthError error) {
                Toast.makeText(getApplicationContext(), "Failed :C" + error.toString(), Toast.LENGTH_LONG).show();
            }
        },true);
    }
    //Este método me permite transferir los datos obtenidos de LinkedIn para ser usados en otra actividad, cuando nota la acción del método login_linkedin, ella cambia la actividad actual por la que se estipuló
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    //Me permite obtener información del perfil del usuario, sustrayéndola de su cuenta de LinkedIn
    private static Scope buildScope(){
        return Scope.build(Scope.R_BASICPROFILE,Scope.R_EMAILADDRESS);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
