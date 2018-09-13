package app.abms.comprobante.cobro;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import app.beans.ItemTipo;
import app.beans.Utiles;
import app.combos.ComboItemTipo;

import ccecho2.base.CCButton;
import ccecho2.base.CCColumn;
import ccecho2.base.CCLabel;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;
import ccecho2.complex.ComboList.ComboList;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.event.ActionEvent;

import datos.recibo_cobro_item.ReciboCobroItem;
import echopointng.Separator;

import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMAddView;
//import framework.ui.principal.FWContentPanePrincipal;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class ItemEditView extends ABMAddView {
	
    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal;
	
	private CCLabel lDirContrato, lPeriodoCuota, lMovimiento, lLeyenda, lMonto, lTotalItem;
	private CCTextField tDirContrato, tPeriodoCuota, tMovimiento, tLeyenda, tMonto, tTotalItem;
	    
    private CCButton btnIntereses;
    private Component compMovimiento;
    private ComboList cboItemTipo;
    
    private FWContentPanePrincipal CPPrincipal;
    
    private ReciboCobroItem oReciboCobroItem;
    private CobroAddView refClaseLlama;
    private int index;
	
    public ItemEditView () {
    	
        super();
        
        this.setTitle("");
        this.setWidth(new Extent(500, Extent.PX));

    }

    
    public ItemEditView (CobroAddView refClaseLlama, ReciboCobroItem oReciboCobroItem, int index) {
    	
        super();
        
        CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
        
        this.refClaseLlama = refClaseLlama;
        this.oReciboCobroItem = oReciboCobroItem;
        this.index = index;
        
        this.setModal(true);
        
        // Evaluo si estoy en un ALTA o en una EDICION
        if (oReciboCobroItem == null) {
        	this.setTitle("Alta Items del Comprobante");
        } else {
        	this.setTitle("Edición Items del Comprobantes");
        }
        
        this.setWidth(new Extent(600, Extent.PX));
        
		// Agrego los componentes de la pantalla
		crearObjetos();		
		
		DecimalFormat moneda = new DecimalFormat("###,##0.00");
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		
		if (oReciboCobroItem != null) {
			
			// Tengo que evaluar si es una Impuesto (varios) para mostrar otro tipo de información referida al impuesto
			
			if (oReciboCobroItem.getIdItemTipo() == ItemTipo.ItemImpuestos) {
				
				/* Tengo que buscar los datos del impuesto ¿? 
				 * Uso un completar en la fachada de proceso o hago lógica en el objeto RECIBOCOBROITEM
				 * ojo puede ser q ya haya pagado y que quede un saldo ... no el total del impuesto 
				 */
				//oReciboCobroItem.getContratoNovedadCobro().get
				
			}
			
			
			String direccion_inmueble = "", periodo = "", monto_novedad = "";
			try {
				direccion_inmueble = 	oReciboCobroItem.getContratoNovedadCobro().getContrato().getInmueble().getDireccion_completa() + " / " + 
				 						oReciboCobroItem.getContratoNovedadCobro().getContrato().getInmueble().getLocalidad().getDescripcion();
				
				periodo = 	oReciboCobroItem.getContratoNovedadCobro().getPeriodoCuota() + " - Venc. " + 
				 			fecha.format(oReciboCobroItem.getContratoNovedadCobro().getFechaVencimiento());
				
				// No todos los movimientos tienen saldo
				
				monto_novedad = moneda.format(oReciboCobroItem.getContratoNovedadCobro().getSaldo());
				
			} catch(NullPointerException npe) {}
			
			tDirContrato.setText(direccion_inmueble);			
			tPeriodoCuota.setText(periodo);			
			tMovimiento.setText(ItemTipo.getDescripcion(oReciboCobroItem.getIdItemTipo()));
			compMovimiento = tMovimiento;
			
			tLeyenda.setText(oReciboCobroItem.getDescripcion());
			tMonto.setText(monto_novedad);
			tTotalItem.setText(moneda.format(oReciboCobroItem.getMonto()));
			
		} else {
			
			/* 
			 * Tiene que seleccionar que tipo de movimiento quiere agregar 
			 * Posibles movimientos:
			 * 		5 Impuestos
			 * 		6 Varios - Genérico
			 * 		7 Intereses
			 */
			
			compMovimiento = cboItemTipo;
			tTotalItem.setText("0,00");			
			
		}
		
		renderObjetos();

    }

    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(5, Extent.PX));
        cLabels.setInsets(new Insets(5));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(5, Extent.PX));
        cTexts.setInsets(new Insets(5));
        
        
        /* DATOS */
        
        lDirContrato = new CCLabel("Dirección:",22);        
        tDirContrato = new CCTextField(300,22,10,true);
        tDirContrato.setEnabled(false);
           
        lPeriodoCuota = new CCLabel("Periodo-Cuota:",22);        
        tPeriodoCuota = new CCTextField(300,22,20,true);
        tPeriodoCuota.setEnabled(false);
        
        lMovimiento = new CCLabel("Movimiento:",22);        
        tMovimiento = new CCTextField(200,22,20,true);
        tMovimiento.setEnabled(false);
        cboItemTipo = new ComboItemTipo(100,22);

        lLeyenda = new CCLabel("Leyenda:",22);        
        tLeyenda = new CCTextField(400,22,20,true);

        lMonto = new CCLabel("Total Movimiento:",22);        
        tMonto = new CCTextField(200,22,20,true);
        //tMonto.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");
        tMonto.setEnabled(false);

        lTotalItem = new CCLabel("Total Item:",22);        
        tTotalItem = new CCTextField(200,22,20,true);
        tTotalItem.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");
    	
        /* Configuro el boton de cálculo de intereses */
        btnIntereses = new CCButton("Calcula Intereses");
        btnIntereses.setActionCommand("intereses");
        btnIntereses.addActionListener(this);

    	
        
    }
    
    
    private void renderObjetos() {
        
    	// Agrego la columna al ContentPane Principal
    	cpPrincipal.add(_cPrincipal);

        _cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        
        
        cLabels.add(lDirContrato);
        cLabels.add(lPeriodoCuota);
        cLabels.add(lMovimiento);
        cLabels.add(lLeyenda);
        cLabels.add(lMonto);
        cLabels.add(lTotalItem);

        cTexts.add(tDirContrato);
        cTexts.add(tPeriodoCuota);
        cTexts.add(compMovimiento);
        cTexts.add(tLeyenda);
        cTexts.add(tMonto);
        cTexts.add(tTotalItem);
        
        // Agrego el boton de los intereses a la barra inferior
        this.rBotones.add(new Separator());
        this.rBotones.add(btnIntereses);
        
        
        ApplicationInstance.getActive().setFocusedComponent(tLeyenda);
        
    }
    
    
    
    /* Si quiero usar polimorfismo del método */
    @SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {    	
    	
    	if (e.getActionCommand().equals("ok")) {
    		
    		
			double total_item = (double)Utiles.ParseFloat(tTotalItem.getText());
			//double total_novedad = ((double)Utiles.ParseFloat(tMonto.getText())) - total_item;
    		
    		// Tengo que preguntar si viene a un nuevo item o es una modificación
    		
    		if (this.oReciboCobroItem != null) {
    			
    			// MODIFICACION
    		
    			//System.out.println("MONTO TOTAL ITEM antes: " + tTotalItem.getText() + " - " + Utiles.ParseFloat(tTotalItem.getText()));
    		
    			/** Reglas de NEGOCIO */
    			
    			/* Valido el monto del item no supere el total del mismo 
    			 * Puede ser que el ITEM no tenga SALDO, por ejemplo una Comisión de Alquiler GENERICA - 18/07/2014  
    			 */
    			double monto_max = 0;
    			if (oReciboCobroItem.getContratoNovedadCobro() != null) {
    				monto_max = oReciboCobroItem.getContratoNovedadCobro().getSaldo();
    			    			    			
	    			if (total_item > monto_max) {
	    			
	    				CPPrincipal.abrirVentana(new FWWindowPaneMensajes("Error Edición Items","El monto no puede superar al saldo del item.",(short)1));
	    				return;
	    				
	    			}
    			} // Fin IF oReciboCobroItem.getContratoNovedadCobro() != null
    			
    			// PROCESO -----------------------------------------------------------------------------
				
    			oReciboCobroItem.setDescripcion(tLeyenda.getText());
    			oReciboCobroItem.setMonto(total_item);
    			//oReciboCobroItem.getContratoNovedadCobro().setMonto(total_novedad);
    		
    			refClaseLlama.oModel.getDataList().set(index, oReciboCobroItem);
    			refClaseLlama.update(refClaseLlama.oModel);
    		
    			this.exit();
    			return;
    			
    		} else {
    			
    			// ALTA
    			
    			this.oReciboCobroItem = new ReciboCobroItem();
    			
    			oReciboCobroItem.setDescripcion(tLeyenda.getText());
    			oReciboCobroItem.setMonto(total_item);
    			
    			try {
					oReciboCobroItem.setIdItemTipo(Short.parseShort(cboItemTipo.getSelectedId()));
				} catch (Exception e1) {
					e1.printStackTrace();
					// revienta como un sapoooo
				}
				
				refClaseLlama.oModel.getDataList().add(oReciboCobroItem);
				refClaseLlama.update(refClaseLlama.oModel);
	    		
    			this.exit();
    			return;
				
    		}
    		
    		
    	} 
    	
    	// Tiro el evento para arriba en la gerarquia de objetos !!!!!!!!!! NO LLEGA ACA
    	super.actionPerformed(e);
    	
    }
            
} // Fin clase