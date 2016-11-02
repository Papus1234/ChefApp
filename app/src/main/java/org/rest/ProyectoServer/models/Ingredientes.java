package org.rest.ProyectoServer.models;

import java.util.ArrayList;
import java.util.List;

public class Ingredientes {
	List <Ingrediente> frutas,vegetales,granos,lacteos,carnes;
	
	
	
	public Ingredientes(){
		frutas=new ArrayList<>();
		granos=new ArrayList<>();
		vegetales=new ArrayList<>();
		lacteos=new ArrayList<>();
		carnes=new ArrayList<>();
	}
	
	public void Registrar(String ingrediente, String categoria){
		if (categoria=="frutas"){
			Ingrediente n= new Ingrediente(ingrediente);
			frutas.add(n);
		}
		else if(categoria=="granos"){
			Ingrediente n= new Ingrediente(ingrediente);
			granos.add(n);
		}
		else if(categoria=="vegetales"){
			Ingrediente n= new Ingrediente(ingrediente);
			vegetales.add(n);
	    }
		else if(categoria=="lacteos"){
			Ingrediente n= new Ingrediente(ingrediente);
			lacteos.add(n);
	    }
		else if(categoria=="carnes"){
			Ingrediente n= new Ingrediente(ingrediente);
			carnes.add(n);
	    }
		else{
			System.err.print("Categoria no valida");
		}
	}	
}
