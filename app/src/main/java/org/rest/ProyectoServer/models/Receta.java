package org.rest.ProyectoServer.models;

import java.util.ArrayList;
import java.util.List;

public class Receta {
	List <String> Pasos;
	String recet;
	public Receta(){
		Pasos=new ArrayList<>();
		
	}

	public Receta(String recet) {
		this.recet=recet;
	}

	public void adherir_pasos(String p){
		Pasos.add(p);
	}

	public List<String> getPasos() {
		return Pasos;
	}

	public void setPasos(List<String> pasos) {
		Pasos = pasos;
	}
	
}
 