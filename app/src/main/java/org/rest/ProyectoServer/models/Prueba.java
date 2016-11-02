package org.rest.ProyectoServer.models;

public class Prueba {

	public static void main(String[] args) {
		Ingredientes c=new Ingredientes();
		c.Registrar("papaya", "frutas");
		c.Registrar("manzana", "frutas");
		c.Registrar("manzana", "frutas");
		
		for (int i=0;i<c.frutas.size();i++){
			
			System.out.println(c.frutas.get(i).getNombre());
			
		}
		// TODO Auto-generated method stub

	}

}
