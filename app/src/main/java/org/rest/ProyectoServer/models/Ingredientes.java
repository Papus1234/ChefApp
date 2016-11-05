package org.rest.ProyectoServer.models;

import java.util.ArrayList;
import java.util.List;

public class Ingredientes {
	public List <Ingrediente> frutas,vegetales,granos,lacteos,carnes;
	List<Ingrediente>todos;
	
	
	public Ingredientes(){
		frutas=new ArrayList<>();
		granos=new ArrayList<>();
		vegetales=new ArrayList<>();
		lacteos=new ArrayList<>();
		carnes=new ArrayList<>();
		todos=new ArrayList<>();
	}
	
	public void Registrar(String ingrediente, String categoria, int cantidad){
		if (categoria=="frutas"){
			Ingrediente n= new Ingrediente(ingrediente);
			n.cantidad=cantidad;
			frutas.add(n);
			todos.add(n);
		}
		else if(categoria=="granos"){
			Ingrediente n= new Ingrediente(ingrediente);
			n.cantidad=cantidad;
			granos.add(n);
			todos.add(n);
		}
		else if(categoria=="vegetales"){
			Ingrediente n= new Ingrediente(ingrediente);
			n.cantidad=cantidad;
			vegetales.add(n);
	    }
		else if(categoria=="lacteos"){
			Ingrediente n= new Ingrediente(ingrediente);
			n.cantidad=cantidad;
			lacteos.add(n);
			todos.add(n);
	    }
		else if(categoria=="carnes"){
			Ingrediente n= new Ingrediente(ingrediente);
			n.cantidad=cantidad;
			carnes.add(n);
			todos.add(n);
	    }
		else{
			System.err.print("Categoria no valida");
		}
	}	
}
