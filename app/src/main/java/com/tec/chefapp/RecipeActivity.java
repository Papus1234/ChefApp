package com.tec.chefapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import datastructures.Lista;

public class RecipeActivity extends AppCompatActivity{
    LinearLayoutCompat content;
    EditText editText;
    Lista<EditText> editTextLista;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipe);
        FloatingActionButton addStep = (FloatingActionButton) findViewById(R.id.addStep);
        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTextField();
            }
        });
    }
    public void addTextField(){
        content = (LinearLayoutCompat) findViewById(R.id.steps_view);
        editText = new EditText(this);
        content.addView(editText);
    }
}
