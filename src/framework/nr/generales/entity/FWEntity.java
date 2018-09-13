/*
 * FWEntity.java
 *
 * Created on 5 de junio de 2007, 12:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package framework.nr.generales.entity;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Interface a implementar por los EntityBean que podrán ser representados como
 *      Hashtables
 */
public interface FWEntity {
    /** Obtiene la representación Hash del Entity */
    public Hashtable getEntityHash(ArrayList<String> detailKeys);
}
