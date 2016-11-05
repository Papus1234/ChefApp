package org.rest.ProyectoServer.models;

public class Ingrediente {
	String nombre;
	

	int cantidad;
	
	public Ingrediente(String n){
		cantidad=1;
		nombre=n;
	}

	public Ingrediente() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}

