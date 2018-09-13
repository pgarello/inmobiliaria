/*
 * FWPersona.java
 *
 * Created on 17 de abril de 2007, 08:23
 *
 */

package framework.grales;

/**
 * Representa en forma abstracta una persona física
 *
 * @author luigi
 */
public class FWPersona {
    /** IdentificaciÃ³n de la persona */
    private int idPersona;
    /** Nro de documento */
    private int documentoNro;
    /** Tipo de documento */
    private FWDocumentoTipo documentoTipo;
    /** Apellido */
    private String apellido;
    /** Nombres */
    private String nombres;
    
    /** Creates a new instance of FWPersona */
    public FWPersona() {
    }
    
}
