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
public class ABMListadoPrintView extends WindowPaneExitable {
    
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
    private ImageReference PRINT = new ResourceImageReference("/resources/crystalsvg22x22/devices/printer1.png");
    
    private ImageReference EXEL = new ResourceImageReference("/resources/crystalsvg22x22/mimetypes/spreadsheet.png");
    private ImageReference PDF = new ResourceImageReference("/resources/crystalsvg22x22/mimetypes/pdf.png");
    
    private CCButton btnFirst;
    private CCButton btnPrevious;
    private CCButton btnNext;
    private CCButton btnLast;
    private CCButton btnDelete;
    private CCButton btnEdit;
    private CCButton btnNew;
    
    private CCButton btnExel, btnPdf;
    
    // Va a ser la clase que muestra el alta 
    protected Class printView;
    
    public CCTable oTable;
    private CCLabel lPagina;

    
    public ABMListadoPrintView(Class printView) {
    	
    	this.printView = printView;
    	
    	this.setModal(true);
    	
        this.crearObjetos();
        this.renderObjetos();
        
    }
    
    /** Creación de objetos */
    private void crearObjetos() {
        //this.browser = new Browser();
    	
    	this.oTable = new CCTable();
    	this.oTable.setHeaderBackground(Color.LIGHTGRAY);
    	this.oTable.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(11)));
    	
    	//this.oTable = new PageableSortableTable();
        
    	// Información de la página que se esta mostrando
    	this.lPagina = new CCLabel();
    	//this.lPagina
    	
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
        this.btnNew = new CCButton(PRINT);
        this.btnNew.setActionCommand("print");
        this.btnNew.setToolTipText("Imprime el listado");
        
        this.btnExel = new CCButton(EXEL);
        this.btnExel.setActionCommand("printExel");
        this.btnExel.setToolTipText("Imprime el listado en formato EXEL");
        
        // Agregar los eventos a los botones
        this.btnFirst.addActionListener(this);
        this.btnPrevious.addActionListener(this);
        this.btnNext.addActionListener(this);
        this.btnLast.addActionListener(this);
        this.btnDelete.addActionListener(this);
        this.btnEdit.addActionListener(this);
        this.btnNew.addActionListener(this);
        
        this.btnExel.addActionListener(this);
    }
    
    /** Armado de interfaz */
    private void renderObjetos() {
        // Agrego los botones a la barra
        this.rBotones.add(this.btnFirst);
        this.rBotones.add(this.btnPrevious);
        this.rBotones.add(this.btnNext);
        this.rBotones.add(this.btnLast);
        this.rBotones.add(new Separator());
        this.rBotones.add(this.btnNew);
        this.rBotones.add(this.btnEdit);
        this.rBotones.add(this.btnDelete);
        
        //this.cPrincipal.add(this.browser.getTable());
        this.cpPrincipal.add(this.oTable);
        this.cpPrincipal.add(this.lPagina);
    }

    public void limpiarBotoneraEdicion() {
    	this.rBotones.removeAll();
    }
    
    public void agregarBotonImprimir() {
    	this.rBotones.add(this.btnNew);
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
            
        } else if (e.getActionCommand().equals("print")){
            
        	// Impresión
        	this.doPrint();
        	/*
        	try {
				printView.newInstance();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			*/
			
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

    
    public void update(TableModel oModel) {
    	           
        // Refresco la lista
        this.oTable.setModel(oModel);        
        
    }

    public void update(TableModel oModel, Integer pagina, int last) {
        
        // Refresco la lista
        this.oTable.setModel(oModel);
        
        // Refresco la página
        this.lPagina.setText("Página " + pagina + " de " + last);
        
    }
    
    public void agregarBotonExcel() {
    	this.rBotones.add(this.btnExel);
    }
    
    public void sacarBotonEditar() {
    	this.rBotones.remove(this.btnEdit);
    }
    
    public void sacarBotonBorrar() {
    	this.rBotones.remove(this.btnDelete);
    }
    
    
    public CCTable getTable() {
        return this.oTable;
    }


    public void doDelete() {
        // indico al controlador que elimine el registro actual
        // int currentRow = this.getBrowser().getTable().getSelectionModel().getMinSelectedIndex();
        // this.controller.delete(currentRow);
    }
    
    public void doPrint() {}

}
