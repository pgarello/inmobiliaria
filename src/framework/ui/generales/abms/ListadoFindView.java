package framework.ui.generales.abms;

import ccecho2.base.CCButton;
import ccecho2.base.CCContentPane;
import ccecho2.base.CCLabel;
import ccecho2.base.CCTable;

import ccecho2.complex.WindowPaneExitable;
import echopointng.ExtentEx;
import echopointng.Separator;

import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.table.TableModel;


public class ListadoFindView extends WindowPaneExitable {
    
	private static final long serialVersionUID = 1L;

	/** Imagen del botón inicial */
    private ImageReference FIRST = new ResourceImageReference("/resources/crystalsvg22x22/actions/player_start.png");
    
    /** Imagen del botón anterior */
    private ImageReference PREVIOUS = new ResourceImageReference("/resources/crystalsvg22x22/actions/player_rew.png");
    
    /** Imagen del botón siquiente */
    private ImageReference NEXT = new ResourceImageReference("/resources/crystalsvg22x22/actions/player_fwd.png");
    
    /** Imagen del botón último */
    private ImageReference LAST = new ResourceImageReference("/resources/crystalsvg22x22/actions/player_end.png");
    
    /** Imagen del boton asociar */
    private ImageReference ASOCIAR = new ResourceImageReference("/resources/crystalsvg22x22/actions/attach.png");
    
    private CCButton btnFirst;
    private CCButton btnPrevious;
    private CCButton btnNext;
    private CCButton btnLast;
    private CCButton btnAsociar;
    
    
    // Va a ser la clase que muestra el alta 
    protected Class addView;
    
    public CCTable oTable;
    private CCLabel lPagina;
    
    public ListadoFindView(Class addView) {
    	
    	this.addView = addView;
    	
    	this.setModal(true);
    	
        this.crearObjetos();
        this.renderObjetos();
                
    }
    
    /** Creación de objetos */
    private void crearObjetos() {
        //this.browser = new Browser();

    	//this.oTable = new PageableSortableTable();
    	
    	//this.oTable = new CCTable();
    	//this.oTable.setHeaderBackground(Color.LIGHTGRAY);
    	//this.oTable.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(11)));

        
        // Seteos de la ventana
        this.setWidth(new ExtentEx(600, ExtentEx.PX));
        
        // Información de la página que se esta mostrando
        this.lPagina = new CCLabel();
        
        // crea los botones
        this.btnFirst = new CCButton(FIRST);
        this.btnFirst.setActionCommand("first");
        this.btnFirst.setToolTipText("Primer Página");
        this.btnPrevious = new CCButton(PREVIOUS);
        this.btnPrevious.setActionCommand("previous");
        this.btnPrevious.setToolTipText("Página Anterior");
        this.btnNext = new CCButton(NEXT);
        this.btnNext.setActionCommand("next");
        this.btnNext.setToolTipText("Página Siguiente");
        this.btnLast = new CCButton(LAST);
        this.btnLast.setActionCommand("last");
        this.btnLast.setToolTipText("Ultima Página");
        this.btnAsociar = new CCButton(ASOCIAR);
        this.btnAsociar.setActionCommand("asociar");
        this.btnAsociar.setToolTipText("Asocia la entidad seleccionada");

        
        // Agregar los eventos a los botones
        this.btnFirst.addActionListener(this);
        this.btnPrevious.addActionListener(this);
        this.btnNext.addActionListener(this);
        this.btnLast.addActionListener(this);
        this.btnAsociar.addActionListener(this);

    }
    
    /** Armado de interfaz */
    private void renderObjetos() {
        // Agrego los botones a la barra
        this.rBotones.add(this.btnFirst);
        this.rBotones.add(this.btnPrevious);
        this.rBotones.add(this.btnNext);
        this.rBotones.add(this.btnLast);
        this.rBotones.add(new Separator());
        this.rBotones.add(this.btnAsociar);
        
        //this.cPrincipal.add(this.browser.getTable());
        //this.cPrincipal.add(this.oTable);
        
        //this.cpPrincipal.add(this.lPagina);
        
    }

    
    /** Acciones a capturar */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("first")){
            // Página inicial
            // this.controller.first();
            
        } else if (e.getActionCommand().equals("previous")){
            // Página anterior
            // this.controller.previous();
            
        } else if (e.getActionCommand().equals("next")){
            // Página siguiente
            // this.controller.next();
            
        } else if (e.getActionCommand().equals("last")){
            // Ultima página
            // this.controller.last();
            
        } else if (e.getActionCommand().equals("asociar")){
        	
        	// Aca asocio y salgo !!!
        	
        }
        
        super.actionPerformed(e);
        
    }
    
    /** Quita la ventana del contenedor */
    public void exit(){
        ((CCContentPane) getParent()).remove(this);
    }
    
    public void update(TableModel oModel, Integer pagina, int last) {
    	           
        // Refresco la lista
        this.oTable.setModel(oModel);
        
        // Refresco la página
        this.lPagina.setText("Página " + pagina + " de " + last);
        
    }

    
    public CCTable getTable() {
        return this.oTable;
    }


}