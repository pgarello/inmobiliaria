package framework.ui.principal;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.ResourceImageReference;

import nextapp.echo2.app.Font;
import nextapp.echo2.app.layout.SplitPaneLayoutData;

import ccecho2.base.CCContentPane;
import ccecho2.base.CCLabel;
import ccecho2.base.CCRow;
import ccecho2.base.CCToolBar;
import ccecho2.base.CCWindowPane;
import framework.grales.seguridad.FWUsuario;
import framework.ui.generales.FWWindowPaneMensajes;

/**
 *Panel principal de aplicación.
 *<u>Este panel tiene dos secciones
 *  <i>una superior para el menú
 *  <i>una inferior para el despliegue de ventanas
 */
public class FWContentPanePrincipal extends CCContentPane {
    
	private static final long serialVersionUID = 1L;
    
	private FWMenuBarPrincipal menuPrincipal;
    
    private CCToolBar toolBar = new CCToolBar();
    private CCRow rowMenu = new CCRow();
    private CCContentPane colCuerpo = new CCContentPane();
    private CCContentPane colMenu = new CCContentPane();
    
    public static final Color oColorMenu = new Color(0x2e84b1); 

    public FWContentPanePrincipal() {
        super();
        //this.toolBar.add(rowMenu);
        colMenu.add(rowMenu);
        this.toolBar.add(colMenu);
        this.toolBar.add(colCuerpo);
        this.add(toolBar);

        //FWUsuario oFWUsuario = ((FWApplicationInstancePrincipal) this.getApplicationInstance().getActive()).getUsuario();
        FWUsuario oFWUsuario = ((FWApplicationInstancePrincipal) ApplicationInstance.getActive()).getUsuario();
        menuPrincipal = new FWMenuBarPrincipal(oFWUsuario);
        this.rowMenu.add(menuPrincipal);
        
        
        CCLabel oLabel = new CCLabel("  - Usuario:  " + oFWUsuario.getUsuario());
        oLabel.setFont(new Font(Font.ARIAL, Font.ITALIC, new Extent(14)));
        oLabel.setForeground(Color.YELLOW);
        this.rowMenu.add(oLabel);
        
        
        //ResourceImageReference imageReference1 = new ResourceImageReference("/resources/image/ShadowBackgroundLightBlue.png");
        //colMenu.setBackgroundImage(new FillImage(imageReference1));
        colMenu.setBackground(oColorMenu);        
        rowMenu.setBackground(oColorMenu);
        menuPrincipal.setBackground(oColorMenu);

        ImageReference OKa = new ResourceImageReference("/resources/image/Logo.jpg");
        FillImage oImage = new FillImage(OKa, new Extent(50, Extent.PX), new Extent(50, Extent.PX), FillImage.NO_REPEAT);
        
       
        colCuerpo.setBackgroundImage(oImage);
/*    
-- Quiero cargar por defecto una VENTANA que no sea MODAL, que solo sirva para informar. Tiene que mostrar los CONTRATOS que vencen
-- en el mes en curso y en el mes siguiente.
  */      
        this.abrirVentana("app.abms.contrato.ContratoListadoVencidosView");
        
        nextPosition = 0;
        
    }

    /**
     *@param ventana 
     */
    public void abrirVentana(CCWindowPane ventana) {
    	
    	/* 
    	 * Valido la cantidad de Ventanas existentes en pantalla 
    	 * 11-01-2011
    	 * 
    	 * Necesito saber la cantidad de ventanas ¿?
    	 */ 
    	
    	//System.out.println("Cantidad de VENTANAS " + (this.colCuerpo.getVisibleComponentCount()+1));
    	
    	positionWindowPane(ventana);
        this.colCuerpo.add(ventana);
    }
    
    public void cerrarVentana(CCWindowPane ventana) {
    	//System.out.println("CONTROL cerrarVentana ************************ " + nextPosition + " - " + ventana.isVisible());
   		this.colCuerpo.remove(ventana);
   		if (nextPosition > 20) nextPosition -= 20;
    }
    

    public void abrirVentanaMensaje(CCWindowPane ventana) {
        this.colCuerpo.add(ventana);
    }

    
    
    /**
     *Abre una ventana en la sección inferior del panel
     *
     *@param comando es el nombre de la ventana a abrir
     */
    public void abrirVentana(String comando){
        Object objeto = null;
        try {
            objeto = Class.forName(comando).newInstance();
            if (objeto instanceof CCWindowPane) {
                abrirVentana((CCWindowPane) objeto);
            }
       } catch (InstantiationException ex) {
            ex.printStackTrace();
            abrirVentanaMensaje(new FWWindowPaneMensajes(ex));
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            abrirVentanaMensaje(new FWWindowPaneMensajes(ex));
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            abrirVentanaMensaje(new FWWindowPaneMensajes(ex));
        }
    }
    
    public void setLayoutCPColCuerpo() {
    	SplitPaneLayoutData cpPrincipalLayoutData = new SplitPaneLayoutData();
        cpPrincipalLayoutData.setAlignment(new Alignment(Alignment.LEFT, Alignment.TOP));        
        colCuerpo.setLayoutData(cpPrincipalLayoutData);
    }
    
    //private int ventanas_abiertas = 0;
    private int nextPosition = 0;
    private void positionWindowPane(CCWindowPane windowPane) {    	   	
    	//System.out.println("CONTROL positionWindowPane ************************ " + nextPosition + " - ");
        //nextPosition = ventanas_abiertas * 20;
    	Extent positionExtent = new Extent(nextPosition, Extent.PX);
        windowPane.setPositionX(positionExtent);
        windowPane.setPositionY(positionExtent);
        nextPosition += 20;
        //ventanas_abiertas++;
        if (nextPosition > 200) {
            nextPosition = 0;
        }
        
    }
    
    public void inicializarPosition() {
    	nextPosition = 0;
    }
    
    public void cerrarVentana() {
    	if (nextPosition > 20) nextPosition -= 20;
    }
    
    public void cerrarTodasLasVentana() {
    	// Cuendo ejecuto una ventana de nivel 0 (desde el menú) cierro antes todas las ventanas existentes
    	this.inicializarPosition();
    	this.colCuerpo.removeAll();
    }
    
}