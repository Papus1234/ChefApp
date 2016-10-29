/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructures;

public class Nodo<T> {

    private T dato;
    private Nodo<T> siguiente;

    public Nodo(){
        siguiente=null;
    }

    public Nodo(T p){
        siguiente=null;
        dato = p;
    }

    public Nodo(T t, Nodo<T> siguiente){
        this.siguiente=siguiente;
        dato = t;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }

}
