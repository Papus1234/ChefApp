package org.rest.ProyectoServer.models;

import java.util.ArrayList;
import java.util.List;

public class Receta {
	List <String> Pasos;
	
	public Receta(){
		Pasos=new ArrayList<>();
		
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
 