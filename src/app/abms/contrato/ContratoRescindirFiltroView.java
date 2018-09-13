package app.abms.contrato;

import java.util.List;
import java.util.Vector;

import app.abms.contrato.ContratoListadoModel;
import app.busquedas.inmueble.InmuebleFindListadoView;
import app.busquedas.persona.PersonaFindListadoView;
import ccecho2.base.CCColumn;

import ccecho2.base.CCLabel;

import ccecho2.base.CCButton;
import ccecho2.base.CCRow;
import ccecho2.base.CCTable;
import ccecho2.base.CCTextField;
import datos.Page;
import datos.contrato.Contrato;
import datos.contrato.ContratoProcesos;
import datos.inmueble.Inmueble;
import datos.persona.Persona;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.list.DefaultListSelectionModel;
import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.table.TableCellRenderer;


import framework.nr.generales.busquedas.FWBusquedas;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoFilterView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class ContratoRescindirFiltroView extends ABMListadoFilterView implements FWBusquedas {
	    
    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rInquilino, rInmueble, rContratos;
	
	private CCLabel lMensaje;
	private CCLabel lInmueble, lPropietario, lInquilino, lListado;
	private CCTextField tInmueble, tPropietario, tInquilino;
	
	private ImageReference iInmueble = new ResourceImageReference("/resources/crystalsvg22x22/actions/gohome.png");
    private CCButton btnInmueble;
    private Inmueble oInmueble;
    
    private ImageReference iInquilino = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnInquilino;
    private Persona oInquilino;
	
    private FWContentPanePrincipal CPPrincipal;
    
    private List<Contrato> dataList = new Vector<Contrato>();
	private Page dataListPage;
	ContratoListadoModel oModel = new ContratoListadoModel();
	
	// Tabla de objetos contrato
    public CCTable oTable;
    
	
	/*
	 * Filtros q tiene la pantalla
	 * 1 - Inmueble (me muestra todos los contratos de un inmueble)
	 * 2 - Propietario (me muestra todos los contratos de propiedades del propietario)
	 * 3 - Inquilino (me muestra todos los contratos en los que fue y es inquilino la persona)
	 */	

	
    public ContratoRescindirFiltroView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Filtro para la Rescinción de CONTRATOS");
		this.setHeight(new Extent(400, Extent.PX));
		this.setWidth(new Extent(700, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
    }
    
    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();
        rInmueble = new CCRow(22);
        rInquilino = new CCRow(22);
        rContratos = new CCRow();
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10, Extent.PX));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10, Extent.PX));
        cTexts.setInsets(new Insets(10));
        
        
        /* Configuro el boton del Inmueble */
        btnInmueble = new CCButton(iInmueble);
        this.btnInmueble.setActionCommand("inmueble");
        this.btnInmueble.setToolTipText("Asignar Inmueble");
        
        this.btnInmueble.setStyleName(null);
        this.btnInmueble.setInsets(new Insets(10, 0));        
        this.btnInmueble.addActionListener(this);
        
        /* Configuro el boton del Inquilino */
        btnInquilino = new CCButton(iInquilino);
        this.btnInquilino.setActionCommand("inquilino");
        this.btnInquilino.setToolTipText("Asignar Inquilino");
        
        this.btnInquilino.setStyleName(null);
        this.btnInquilino.setInsets(new Insets(10, 0));        
        this.btnInquilino.addActionListener(this);        
        
        /*******************************************************************/       
        lInmueble = new CCLabel("Inmueble:",22);
        tInmueble = new CCTextField(300, false);
        tInmueble.setEnabled(false);
                
        lPropietario = new CCLabel("Propietario:",22);
        tPropietario = new CCTextField(300,22,20,false);
        tPropietario.setEnabled(false);
        
        lInquilino = new CCLabel("Inquilino:",22);
        tInquilino = new CCTextField(300,false);
        tInquilino.setEnabled(false);
        
        lListado = new CCLabel("Listado de Contratos. Seleccione uno para continuar:",22);        
        
        /* Tabla con el resultado de la búsqueda de Contratos */
    	this.oTable = new CCTable();
    	this.oTable.setHeaderBackground(Color.LIGHTGRAY);
    	this.oTable.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(11)));
    	this.oTable.setWidth(new Extent(580));
        
    	ColumnLayoutData cTableLD = new ColumnLayoutData();
    	cTableLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.BOTTOM));
    	this.oTable.setLayoutData(cTableLD);
        
    	/** Cargo los datos en la grilla - Inicialmente esta vacia */
        oModel.setDataList(dataList);
        this.update(oModel);
        
        
        /*******************************************************************/
        rBotones = new CCRow();
        rBotones.setInsets(new Insets(10));
        rBotones.setAlignment(Alignment.ALIGN_CENTER);

        
        /*******************************************************************/
        rMensaje = new CCRow();
        rMensaje.setAlignment(Alignment.ALIGN_CENTER);

        lMensaje = new CCLabel();
        lMensaje.setForeground(Color.RED);

    }
    
    
    private void renderObjetos() {
        
    	// Agrego la columna al ContentPane Principal
    	cpPrincipal.add(_cPrincipal);

        _cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        
        //rPrincipal.setBorder(new Border(1, Color.BLUE,Border.STYLE_DASHED));
        
        _cPrincipal.add(rMensaje);
        _cPrincipal.add(rBotones);        
        
        rInmueble.add(tInmueble);
        rInmueble.add(btnInmueble);

        rInquilino.add(tInquilino);
        rInquilino.add(btnInquilino);
        
        CCColumn c1 = new CCColumn();
        
        c1.add(lListado);
        c1.add(this.oTable);
        
        rContratos.add(c1);
        cpPrincipal.add(rContratos);
        
        
        cLabels.add(lInmueble);
        cLabels.add(lPropietario);
        cLabels.add(lInquilino);
        
        cTexts.add(rInmueble);
        cTexts.add(tPropietario);
        cTexts.add(rInquilino);
                
        rMensaje.add(lMensaje);

        ApplicationInstance.getActive().setFocusedComponent(tInmueble);
        
    }
	
	
    public void find() {

    	/*

    	 */
    	
    	/** Levanto el filtro */
    	int contrato_ptr = this.oTable.getSelectionModel().getMinSelectedIndex();
    	if (contrato_ptr >= 0) {
			
			Contrato oContrato = dataList.get(contrato_ptr);			
			
			/** Llamo a ContratoListadoView */    	
	    	ContratoRescindirView oPantallaListado = new ContratoRescindirView(oContrato);
	    	((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(oPantallaListado);    	
	    	
			
		} else {
			
			/** saldo por el error */
        	((FWContentPanePrincipal) ApplicationInstance
        			.getActive().getDefaultWindow().getContent())
                    .abrirVentana(new FWWindowPaneMensajes("Error Rescinción de CONTRATO","Debe seleccionar una contrato para continuar.",(short)1));
        }
    	
    	
    }
    
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getActionCommand().equals("inquilino")){
    		
            CPPrincipal.abrirVentanaMensaje(new PersonaFindListadoView("Inquilino", this, (short)0));
    		
    	} else if (ae.getActionCommand().equals("inmueble")){
    		
    		CPPrincipal.abrirVentanaMensaje(new InmuebleFindListadoView(this, false));
    		
    	}
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
    	
    }

	@SuppressWarnings("unchecked")
	public void setResultado(Object object) {
		
		if (object instanceof Persona) {
			
			tInquilino.setText(((Persona)object).getDescripcion()); 
			oInquilino = (Persona) object;
			int id_inquilino = oInquilino.getIdPersona();
			
			// busco los Contratos que tienen por inquilino a la persona seleccionada
			ContratoListadoModel oModel = new ContratoListadoModel();
	        dataListPage = ContratoProcesos.findByFilter(true,0,id_inquilino,0,0,9,null,null,false,false);
	        dataList = dataListPage.getList();
	        
//	        try {
//				dataList = ContratoProcesos.completar(dataList);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	        
//	        oModel.setDataList(dataList);
//	        this.update(oModel);			
			
			
		} else if (object instanceof Inmueble) {
			
			oInmueble = (Inmueble)object;
			
			// Evaluo si el inmueble seleccionado tiene propietario
			try {
				if (oInmueble.getPropietario().getDescripcion().equals("")) {}
			} catch(NullPointerException npe) {
				
				// salgo
				new FWWindowPaneMensajes("El inmueble seleccionado debe tener asignado un PROPIETARIO", "ERROR");
				return;
			}
			
			tInmueble.setText(oInmueble.getDireccion_completa());
			tPropietario.setText(oInmueble.getPropietario().getDescripcion());
			
			// Busco los contratos sobre el Inmueble Seleccionado
			
				int id_inmueble = oInmueble.getIdInmueble();
			
				/** 1º busco los datos y los cargo en la grilla */
		        ContratoListadoModel oModel = new ContratoListadoModel();
		        dataListPage = ContratoProcesos.findByFilter(true,id_inmueble,0,0,0,9, null,null,false,false);
		        dataList = dataListPage.getList();
		        
//		        try {
//					dataList = ContratoProcesos.completar(dataList);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
		        
		        //oModel.setDataList(dataList);
		        //this.update(oModel);			
			
		}
				
		try {
			dataList = ContratoProcesos.completar(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Controlo que la consulta no de vacia
        if (dataListPage.getList().size() > 0) {
                	
        	//dataList = dataListPage.getList();
        
        	oModel.setDataList(dataList);
        	this.update(oModel);
        	this.fila_color = -1;
        	
        	ListSelectionModel selectionModel = this.oTable.getSelectionModel();
        	selectionModel.clearSelection();
        	this.oTable.setWidth(null);
        	this.oTable.setDefaultRenderer(Object.class, randomizingCellRenderer);
        	
        }		
		
	}
	
	private int fila_color = -1;
    private TableCellRenderer randomizingCellRenderer = new TableCellRenderer() {
    	  
        public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
        	//System.out.println("haber " + value.getClass().getName() + " - " + column + " - " + row);     	
        	Label label = null;        	
        	if (column == 1) {   
        		//label = new Label("");
        		if ((Boolean)value) {
        			if (fila_color != row) fila_color = row;
        		}   		
        	} else {
        		label = new Label(value == null ? null : value.toString());
        		if (fila_color == row) {
            		TableLayoutData layoutData = new TableLayoutData();
        			layoutData.setBackground(Color.ORANGE);            
        			label.setLayoutData(layoutData);
            	}
        	}        	
        	      	
            return label;
        }        
    };
	
    
	public void clear() {
		
		tInmueble.setText("");
		tInquilino.setText("");
		tPropietario.setText("");
		
		dataList = new Vector<Contrato>();
		oModel.setDataList(dataList);
		update(this.oModel);
		
	}
	
	
	public void update(ContratoListadoModel oModel) {
    	
		// Recorro la tabla para sacar el total de la
//    	DecimalFormat moneda = new DecimalFormat("$###,##0.00");
//    	List<ReciboCobroItem> lDatos = oModel.getDataList();
//    	float total = 0;
//    	for(ReciboCobroItem obj: lDatos) {
//    		total += obj.getMonto();
//    	}
//    	tTotal_recibo.setText(moneda.format(total));
    	
        // Refresco la lista
        this.oTable.setModel(oModel);
        this.oTable.setSelectionModel(new DefaultListSelectionModel());
        
    }
	
           
}