package datastructures;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Lista<T>{
    private Nodo<T> primero;

    public Lista(){
        vaciarLista();
    }
    /**
     * Vacia la lista
     */
    private void vaciarLista(){
        primero = null;
    }
    /**
     * Indica si la lista esta vacia o no
     * @return True = esta vacia
     */
    public boolean estaVacia(){
        return primero == null;
    }
    /**
     * Inserta un objeto al principio de la lista
     * @param t Dato insertado
     */
    public void insertarPrimero(T t){
        Nodo<T> nuevo = new Nodo<T>(t);

        if (!estaVacia()){
            //Sino esta vacia, el primero actual pasa a ser
            // el siguiente de nuestro nuevo nodo
            nuevo.setSiguiente(primero);
        }

        //el primero apunta al nodo nuevo
        primero=nuevo;

    }
    /**
     * Inserta al final de la lista un objeto
     * @param t Dato insertado
     */
    public void insertarUltimo(T t){

        Nodo<T> aux = new Nodo<T>(t);
        Nodo<T> rec_aux;

        if (estaVacia()) {
            insertarPrimero(t);
        }else {
            rec_aux = primero;

            //Buscamos el ultimo nodo
            while(rec_aux.getSiguiente() != null){
                rec_aux=rec_aux.getSiguiente();
            }

            //Actualizamos el siguiente del ultimo
            rec_aux.setSiguiente(aux);
        }
    }
    /**
     * Devuelve el número de elementos de la lista
     * @return Número de elementos
     */
    public int cuantosElementos(){
        Nodo<T> aux;
        int numElementos=0;
        aux = primero;

        //Recorremos
        while(aux != null){
            numElementos++;
            aux = aux.getSiguiente();
        }
        return numElementos;

    }

    /**
     * Devuelve el dato del nodo en la posicion pos
     * @param pos
     * @return dato del nodo en la posicion indicada
     */
    public T devolverDato(int pos){
        Nodo<T> aux=primero;
        int cont=0;
        T dato=null;

        if(pos<0 || pos>=cuantosElementos()){
            System.out.println("La posicion insertada no es correcta");
        }else{
            //recorremos
            while(aux!=null){
                if (pos == cont){
                    //Cogemos el dato
                    dato=aux.getDato();
                }

                aux=aux.getSiguiente();
                cont++;
            }
        }
        return dato;
    }
    /**
     * Inserta un nuevo nodo en la posicion indicada con el su dato
     * @param pos
     * @param dato
     */
    public void introducirDato(int pos, T dato){
        Nodo<T> aux=primero;
        Nodo<T> auxDato=null; //Debemos crear un nodo para insetar el dato
        Nodo<T> anterior=primero; //Debemos crear un nodo para insetar el dato

        int contador=0;

        if(pos<0 || pos>cuantosElementos()){
            System.out.println("La posicion insertada no es correcta");
        }else{

            if(pos==0){
                insertarPrimero(dato);
            }else if(pos==cuantosElementos()){
                insertarUltimo(dato);
            }else{
                //Recorremos
                while(aux!=null){
                    if (pos == contador){
                        //Creo el nodo
                        auxDato=new Nodo<>(dato, aux);
                        //El siguiente del anterior a aux es auxDato
                        anterior.setSiguiente(auxDato);
                    }

                    //Actualizo anterior
                    anterior=aux;

                    contador++;
                    aux=aux.getSiguiente(); //Actualizo siguiente
                }
            }
        }
    }

    public void intercambiar(int pre_data, int post_data){
        T temp=devolverDato(pre_data);
        modificarDato(pre_data,devolverDato(post_data));
        modificarDato(post_data,temp);
    }
    /**
     * Modifica el dato indicado en el nodo de la posicion indicada
     * @param pos
     * @param dato
     */
    public void modificarDato(int pos, T dato){
        Nodo<T> aux=primero;
        int cont=0;

        if(pos<0 || pos>=cuantosElementos()){
            System.out.println("La posicion insertada no es correcta");
        }else{
            //Recorremos
            while(aux!=null){
                if (pos == cont){
                    //Modificamos el dato directamente
                    aux.setDato(dato);
                }
                cont++;
                aux=aux.getSiguiente(); //Actualizamos
            }
        }
    }
    /**
     * Borra un elemento de la lista
     * @param pos Posición de la lista que queremos borrar
     */
    public void borraPosicion(int pos){

        Nodo<T> aux=primero;
        Nodo<T> anterior=null;
        int contador=0;

        if(pos<0 || pos>=cuantosElementos()){
            System.out.println("La posicion insertada no es correcta");
        }else{
            while(aux!=null){
                if (pos == contador){
                    if (anterior==null){
                        primero = primero.getSiguiente();
                    }else {
                        //Actualizamos el anterior
                        anterior.setSiguiente(aux.getSiguiente());
                    }
                    aux=null;
                }else{
                    anterior=aux;
                    aux=aux.getSiguiente();
                    contador++;
                }
            }
        }
    }
}
