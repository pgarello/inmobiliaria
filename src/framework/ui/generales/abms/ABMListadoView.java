/*
 * NavWindow.java
 *
 * Created on 9 de febrero de 2007, 15:05
 *
 */

package framework.ui.generales.abms;

import ccecho2.base.CCButton;
import ccecho2.base.CCContentPane;
import ccecho2.base.CCLabel;
import ccecho2.base.CCTable;

import ccecho2.complex.WindowPaneExitable;
import echopointng.ExtentEx;
import echopointng.Separator;


//import framework.abstractMVC.controllers.AbstractController;
//import framework.abstractMVC.interfaces.MVCViewInterface;

import java.util.Observable;


import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.ResourceImageReference;

import nextapp.echo2.app.event.ActionEvent;

import nextapp.echo2.app.table.TableModel;

import nextapp.echo2.app.Color;

/**
 *
 * @author SShadow
 */
public class ABMListadoView extends WindowPaneExitable {
    
	private static final long serialVersionUID = 1L;

	/** Imagen del botón inicial */
    private ImageReference FIRST = new ResourceImageReference("/resources/crystalsvg22x22/actions/player_start.png");
    
    /** Imagen del botón anterior */
    private ImageReference PREVIOUS = new ResourceImageReference("/resources/crystalsvg22x22/actions/player_rew.png");
    
    /** Imagen del botón siquiente */
    private ImageReference NEXT = new ResourceImageReference("/resources/crystalsvg22x22/actions/player_fwd.png");
    
    /** Imagen del botón último */
    private ImageReference LAST = new ResourceImageReference("/resources/crystalsvg22x22/actions/player_end.png");
    
    /** Imagen del botón borrar */
    private ImageReference DELETE = new ResourceImageReference("/resources/crystalsvg22x22/actions/editdelete.png");
    
    /** Imagen del botón editar */
    private ImageReference EDIT = new ResourceImageReference("/resources/crystalsvg22x22/actions/edit.png");
    
    /** Imagen del botón editar */
    private ImageReference NEW = new ResourceImageReference("/resources/crystalsvg22x22/actions/add.png");
    
    private CCButton btnFirst;
    private CCButton btnPrevious;
    private CCButton btnNext;
    private CCButton btnLast;
    private CCButton btnDelete;
    private CCButton btnEdit;
    private CCButton btnNew;
    private Separator separacion; 
    
    
    // Va a ser la clase que muestra el alta 
    protected Class addView;
    
    public CCTable oTable;
    protected CCLabel lPagina;
    
    public ABMListadoView(Class addView) {
    	
    	this.addView = addView;
    	
    	this.setModal(true);
    	
        this.crearObjetos(false);
        this.renderObjetos();
        
        
        //((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).setLayoutCPColCuerpo();
        //((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(this);
    }
    
    public ABMListadoView(Class addView, boolean tablaSeleccionMultiple) {
    	
    	this.addView = addView;
    	
    	this.setModal(true);
    	
        this.crearObjetos(tablaSeleccionMultiple);
        this.renderObjetos();
        
        
        //((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).setLayoutCPColCuerpo();
        //((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(this);
    }
    
    
    
    
    /** Creación de objetos */
    private void crearObjetos(boolean tablaSeleccionMultiple) {
        //this.browser = new Browser();
    	
    	if (tablaSeleccionMultiple) {
    		
    		this.oTable = new CCTable("");
        	this.oTable.setHeaderBackground(Color.LIGHTGRAY);
        	this.oTable.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(10)));
    		
    	} else {
	    	this.oTable = new CCTable();
	    	this.oTable.setHeaderBackground(Color.LIGHTGRAY);
	    	this.oTable.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(11)));
	    	
	    	//this.oTable = new PageableSortableTable();
    	}
    	
    	// Información de la página que se esta mostrando
    	this.lPagina = new CCLabel();	
        
        // Seteos de la ventana
        this.setWidth(new ExtentEx(600, ExtentEx.PX));
        
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
        this.btnDelete = new CCButton(DELETE);
        this.btnDelete.setActionCommand("delete");
        this.btnDelete.setToolTipText("Borrar Registro Actual");
        this.btnEdit = new CCButton(EDIT);
        this.btnEdit.setActionCommand("edit");
        this.btnEdit.setToolTipText("Editar Registro Actual");
        this.btnNew = new CCButton(NEW);
        this.btnNew.setActionCommand("new");
        this.btnNew.setToolTipText("Agrega un Nuevo Registro");
        
        this.separacion = new Separator();
        
        // Agregar los eventos a los botones
        this.btnFirst.addActionListener(this);
        this.btnPrevious.addActionListener(this);
        this.btnNext.addActionListener(this);
        this.btnLast.addActionListener(this);
        this.btnDelete.addActionListener(this);
        this.btnEdit.addActionListener(this);
        this.btnNew.addActionListener(this);        
        
    }
    
    /** Armado de interfaz */
    private void renderObjetos() {
        // Agrego los botones a la barra
        this.rBotones.add(this.btnFirst);
        this.rBotones.add(this.btnPrevious);
        this.rBotones.add(this.btnNext);
        this.rBotones.add(this.btnLast);
        this.rBotones.add(this.separacion);
        this.rBotones.add(this.btnNew);
        this.rBotones.add(this.btnEdit);
        this.rBotones.add(this.btnDelete);
        
        //this.cPrincipal.add(this.browser.getTable());
        this.cpPrincipal.add(this.oTable);
        this.cpPrincipal.add(this.lPagina);
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
            
        } else if (e.getActionCommand().equals("new")){
            
        	// Inserción
        	try {
				addView.newInstance();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            //this.controller.addRow();
            
        } else if (e.getActionCommand().equals("edit")){
            // Edición de la fila actual
            // int currentRow = this.getBrowser().getTable().getSelectionModel().getMinSelectedIndex();
            // this.controller.editRow(currentRow);
            
        } else if (e.getActionCommand().equals("delete")){
            // Borrar la fila actual
            //MessageWindowPane mwp = new MessageWindowPane("¿Está seguro de eliminar permanentemente el Registro?", this, "doDelete");
        
        } //else if (e.getActionCommand().equals("exit")){
            // Salir
            //this.exit();
        //}
        
        super.actionPerformed(e);
        
    }
    
    /** Quita la ventana del contenedor */
    public void exit(){
        ((CCContentPane) getParent()).remove(this);
    }
    
    public void update(Observable o, Object arg) {
    	/*
    	ABMListadoModel model = (ABMListadoModel) o;
        // Obtiene la lista
        this.lista = model.getDataPage();
        // setea el browser con la lista a mostrar
        // this.browser.getModel().setLista(this.lista);
        
        // fila actual
        int selectedRow = this.getBrowser().getTable().getSelectionModel().getMinSelectedIndex();
        // cantidad de filas
        int rowCount = this.lista.size() - 1;
        
        if (rowCount >= 0) {
            // Hay filas
            if (selectedRow == -1) {
                // si no hay filas seleccionadas, selecciono la primera
                this.getBrowser().getTable().getSelectionModel().setSelectedIndex(0, true);
            } else if (selectedRow > rowCount) {
                /*
                 * si la fila seleccionada es mayor a la Ãºltima disponible,
                 *  selecciono la Ãºltima
                 /
                this.getBrowser().getTable().getSelectionModel().setSelectedIndex(rowCount, true);
            }
        }
        
        this.btnDelete.setVisible(rowCount >= 0);
        this.btnEdit.setVisible(rowCount >= 0);
           
        
        // Refresco la lista
        this.browser.update();
        */
    }

    
    public void update(TableModel oModel, Integer pagina, int last) {
    	           
        // Refresco la lista
        this.oTable.setModel(oModel);
        
        // Refresco la página
        if (pagina > 0 && last > 0) {        
        	this.lPagina.setText("Página " + pagina + " de " + last);
        }
    }

    
    public CCTable getTable() {
        return this.oTable;
    }


    public void doDelete() {
        // indico al controlador que elimine el registro actual
        // int currentRow = this.getBrowser().getTable().getSelectionModel().getMinSelectedIndex();
        // this.controller.delete(currentRow);
    }
    
    public void sacarBotonesNavegacion() {
    	this.rBotones.remove(this.btnFirst);
    	this.rBotones.remove(this.btnLast);
    	this.rBotones.remove(this.btnNext);
    	this.rBotones.remove(this.btnPrevious);
    	this.rBotones.remove(this.separacion);    	
    }
    
    public void sacarBotonesABM() {
    	this.rBotones.remove(this.btnDelete);
    	this.rBotones.remove(this.btnEdit);
    	this.rBotones.remove(this.btnNew);
    }
    
    public void soloEdicion() {
    	this.rBotones.remove(this.btnDelete);
    	this.rBotones.remove(this.btnNew);
    }

}