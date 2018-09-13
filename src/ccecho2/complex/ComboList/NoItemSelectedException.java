/*
 * NoItemSelectedException.java
 *
 * Created on June 26, 2007, 9:51 AM
 *
 */

package ccecho2.complex.ComboList;

/**
 *
 * @author gferreyra
 */
public class NoItemSelectedException extends Exception {
    
    /** Creates a new instance of NoItemSelectedException */
    public NoItemSelectedException() {
        super("No se ha seleccionado item alguno de la lista");
    }

}
