package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    
    private Nodo raiz;
    private int cardinal;

    private class Nodo {
        private T valor;
        private Nodo padre;
        private Nodo izq;
        private Nodo der;

        public Nodo(T val){
            valor = val;
            padre = null;
            izq = null;
            der = null;
        }

        public Nodo(T val,Nodo p){
            valor = val;
            padre = p;
            izq = null;
            der = null;
        }
    }

    public ABB() {
        raiz = null;
        cardinal = 0;
    }

    public int cardinal() {
        return cardinal;
    }

    public T minimo(){
        T res = null;
        Nodo actual = raiz;
        if(cardinal == 0){
            res = null;
        }
        else{
            while(actual.izq != null){
                actual = actual.izq;
            }
            res = actual.valor;
        }
        return res;
    }

    public T maximo(){
        T res = null;
        Nodo actual = raiz;
        if(cardinal == 0){
            res = null;
        }
        else{
            while(actual.der != null){
                actual = actual.der;
            }
            res = actual.valor;
        }
        return res;
    }
    public void insertar(T elem){;
        Nodo padre_actual = null;
        Nodo actual = raiz;
        boolean noesta = true;
        if(cardinal == 0){
            raiz = new Nodo (elem);
            cardinal ++;
        }
        else{
            while(actual != null && noesta){
                padre_actual = actual;
                if (elem.compareTo(actual.valor)==0){
                    noesta = false;
                }
                else if(elem.compareTo(actual.valor)>0){
                    actual = actual.der;
                }
                else{
                    actual = actual.izq;
                }
            }
            if(noesta){
                if(elem.compareTo(padre_actual.valor)>0){
                    padre_actual.der = new Nodo (elem,padre_actual);
                }
                else{
                    padre_actual.izq = new Nodo (elem,padre_actual);
                }
                cardinal ++;
            }
        }
    }

    public boolean pertenece(T elem){
        boolean res = false;
        Nodo actual = raiz;
        boolean fusible = true;
            while(actual != null && fusible){
                if (elem.compareTo(actual.valor)==0){
                    fusible = false;
                    res = true;
                }
                else if(elem.compareTo(actual.valor)>0){
                    actual = actual.der;
                }
                else{
                    actual = actual.izq;
                }
            }
        return res;
    }

    public void eliminar(T elem) {
        Nodo padre_actual = null;
        Nodo actual = raiz;
        boolean esta = false;
        while(actual != null && !esta){
            if(elem.compareTo(actual.valor) == 0){
                esta = true;
            }else if(elem.compareTo(actual.valor) > 0){
                padre_actual = actual;
                actual = actual.der;
            } 
            else{
                padre_actual = actual;
                actual = actual.izq;
            }
        }
        if(esta){
            if(actual.der == null && actual.izq == null){
                if(padre_actual == null){
                    raiz = null;
                }
                else if(actual.valor.compareTo(padre_actual.valor)>0){
                    padre_actual.der = null;
                }
                else{
                    padre_actual.izq = null;
                }
            }
            else if(actual.der == null && actual.izq != null){
                if (padre_actual == null){
                    raiz = raiz.izq;
                    raiz.padre = null;
                }
                else if(actual.valor.compareTo(padre_actual.valor)>0){
                    padre_actual.der = actual.izq;
                }
                else{
                    padre_actual.izq = actual.izq;
                }
                actual.izq.padre = padre_actual;
            }
            else if(actual.der != null && actual.izq == null){
                if(padre_actual == null){
                    raiz = raiz.der;
                    raiz.padre = null;
                }
                else if(actual.valor.compareTo(padre_actual.valor)>0){
                    padre_actual.der = actual.der;
                }
                else{
                    padre_actual.izq = actual.der;
                }
                actual.der.padre = padre_actual;
            }
            else {
                Nodo sucesor = actual.der;
                Nodo padre_sucesor = actual;
                while(sucesor.izq != null){
                    padre_sucesor = sucesor;
                    sucesor = sucesor.izq;
                }
                actual.valor = sucesor.valor;
                if(sucesor.valor.compareTo(padre_sucesor.izq.valor)==0){
                    padre_sucesor.izq = sucesor.der;
                }
                else{
                    padre_sucesor.der = sucesor.der;
                }
                if(sucesor.der != null){
                    sucesor.der.padre = padre_sucesor;
                }
            }
            cardinal--;
        }
    }

    public String toString(){
        String res = "{";
        int i = 0;
        Iterador<T> actual = this.iterador();
        while(i<cardinal){
            res += actual.siguiente() ;
            if(i < cardinal-1){
                res += ",";
            }
            i ++;
        }
        res += "}";
        return res;
    }

    public T sucesor(T elem){
        T res = maximo();
        Nodo actual = raiz;
        boolean fusible = true;
            while(actual != null && fusible){
                if(elem.compareTo(actual.valor)==0){
                    if(actual.der != null){
                        actual = actual.der;
                        while(actual.izq != null){
                            actual = actual.izq;
                        }
                        res = actual.valor;
                    }
                    fusible = false;
                }
                else if(elem.compareTo(actual.valor)>0){
                    actual = actual.der;
                }
                else{
                    if(actual.valor.compareTo(res)<0){
                        res = actual.valor;
                    }
                    actual = actual.izq;
                }
            }
        return res;  
    }

    private class ABB_Iterador implements Iterador<T> {
        private T actual = minimo();
        private T ultimo = maximo();

        public boolean haySiguiente() { 
            return actual != ultimo;
        }
    
        public T siguiente() {
            T res = actual;
            actual = sucesor(actual);
            return res;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }
}