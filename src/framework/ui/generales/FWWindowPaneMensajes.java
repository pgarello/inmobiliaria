package framework.ui.generales;

import ccecho2.base.CCColumn;
import ccecho2.base.CCRow;
import ccecho2.complex.WindowPaneExitable;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;

import ccecho2.base.CCLabel;
import framework.ui.principal.FWContentPanePrincipal;

/**
 * Presentación de la ventana de Mensajes del sistema
 */
public class FWWindowPaneMensajes extends WindowPaneExitable {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
	private CCRow rArriba;
    private CCColumn cDerecha;
    private CCColumn cIzquierda;
   
    private CCLabel lTitulo;
    private CCLabel lMensaje;
    
    ImageReference Error;


    /**
     * Constructor
     */
    public FWWindowPaneMensajes() {
        super();
        this.crearObjetos();
        renderObjetos();
        
        lTitulo.setText("Titulo: ");
        lMensaje.setText("Mensaje: ");
    }

        
    /**
     * Constructor
     *
     * @param sTitulo Título de la ventana
     * @param sMensaje Mensaje a mostrar
     * @param nTipo Tipo de mensaje
     */
    public FWWindowPaneMensajes(String sTitulo, String sMensaje, short nTipo) {
        super();
        this.crearObjetos();
        renderObjetos();

        this.lTitulo.setText("Titulo: "+sTitulo);
        this.lMensaje.setText("Mensaje: "+sMensaje);
    }
    
    /**
     * Constructor
     *
     * @param ex Excepción a informar
     */
    public FWWindowPaneMensajes(Exception ex) {
        super();
        this.crearObjetos();
        renderObjetos();

        this.lTitulo.setText("Titulo: "+ex.toString());
        this.lMensaje.setText("Mensaje: "+ex.getMessage());
    }
    
    public FWWindowPaneMensajes(String mensaje, String titulo) {
        super();
        this.crearObjetos();
        renderObjetos();

        this.lTitulo.setText("Titulo: "+titulo);
        this.lMensaje.setText("Mensaje: "+mensaje);
        
        ((FWContentPanePrincipal) ApplicationInstance
                .getActive().getDefaultWindow().getContent())
                .abrirVentanaMensaje(this);
        
    }
    

    private void crearObjetos() {
        rArriba = new CCRow();
        cDerecha = new CCColumn();
        cIzquierda = new CCColumn();

        lTitulo = new CCLabel();
        lMensaje = new CCLabel();

        this.setModal(true);
        this.cIzquierda.setInsets(new Insets(10));
        this.cDerecha.setInsets(new Insets(3));
    }

    private void renderObjetos() {
        this.cpPrincipal.add(cIzquierda);
        this.cpPrincipal.add(cDerecha);

        this.cIzquierda.add(new CCLabel(new ResourceImageReference("/resources/crystalsvg22x22/actions/stop.png")));
        this.cDerecha.add(lTitulo);
        this.cDerecha.add(lMensaje);

    }
}


