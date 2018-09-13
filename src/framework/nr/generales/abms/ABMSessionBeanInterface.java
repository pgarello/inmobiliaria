/*
 * ABMSessionBean.java
 *
 * Created on 3 de mayo de 2007, 13:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package framework.nr.generales.abms;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
//import oracle.toplink.essentials.expressions.Expression;
//import oracle.toplink.essentials.queryframework.ReadAllQuery;

/**
 *
 * @author luigi
 */
public interface ABMSessionBeanInterface {
    /** Obtiene un vector de Hashtables */
    public Vector<Hashtable> findByPage(int page, int pageSize, String order,
            ArrayList<String> datailKeys);
    /** Obtiene un vector de Hashtables según filtro (argumentos)*/
    public Vector<Hashtable> findByPage(int page, int pageSize, String order,
            ArrayList<String> datailKeys, String[] argumentos);
    /** Obtiene la cantidad de páginas de acuerdo al total de filas y
    *      cantidad de filas por pagina */
    public int getPageCount(int pageSize);
    /** Obtiene la cantidad de páginas de acuerdo al filtro, al total de filas y
    *      cantidad de filas por pagina */
    public int getPageCount(int pageSize, String[] argumentos);
    /** Persiste un Entity desde el Hashtable que lo representa */
    public void create(Hashtable hashEntity);
    /** Modifica un Entity desde el Hashtable que lo representa */
    public void edit(Hashtable hashEntity);
    /** Elimina un Entity desde el Hashtable que lo representa */
    public void destroy(Hashtable hashEntity);
    /** Obtiene un hashtable vacio con la extructura del entity  */
    public Hashtable getEmpty();

    //public Expression getFilterCriteria(ReadAllQuery query, String[] argumentos);
}
