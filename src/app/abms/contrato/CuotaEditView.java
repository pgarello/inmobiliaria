package app.abms.contrato;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import app.beans.Cuota;
import app.beans.Utiles;


import ccecho2.base.CCColumn;
import ccecho2.base.CCLabel;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.event.ActionEvent;

import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMAddView;

import framework.ui.principal.FWContentPanePrincipal;

/**
 * @author pablo
 * URL para ver: http://www.chuidiang.com/java/ejemplos/JFormattedTextField/EjemplosJFormattedTextField.php
 */

@SuppressWarnings("serial")
public class CuotaEditView extends ABMAddView {
	
    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal;
	
	private CCLabel lCuotaNumero, lPeriodoCuota, lVencimiento, lMonto, lTotal;
	private CCTextField tCuotaNumero, tPeriodoCuota, tVencimiento, tMonto, tTotal;
    
    //private FWContentPanePrincipal CPPrincipal;
    
    private Cuota oCuota;
    private ContratoAddCuotasListadoView refClaseLlama;
    private int index;
	
    private FWContentPanePrincipal CPPrincipal;
    
    public CuotaEditView () {
    	
        super();
        
        this.setTitle("");
        this.setWidth(new Extent(500, Extent.PX));

    }

    
    public CuotaEditView (ContratoAddCuotasListadoView refClaseLlama, Cuota oCuota, int index) {
    	
        super();
        
        //CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
        
        this.refClaseLlama = refClaseLlama;
        this.oCuota = oCuota;
        this.index = index;
        
        this.setModal(true);
        
       	this.setTitle("Edición Cuota de CONTRATO");
        this.setWidth(new Extent(500, Extent.PX));
        
        CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
        
		// Agrego los componentes de la pantalla
		crearObjetos();		
		
		// Asigno los datos a los objetos
		DecimalFormat moneda = new DecimalFormat("###,##0.00");
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
			
		tCuotaNumero.setText(""+oCuota.getCuota());
		
		String periodo = oCuota.getPeriodo_anio() + " - " + Utiles.convertirMes(oCuota.getPeriodo_mes());
		tPeriodoCuota.setText(periodo);			
		tVencimiento.setText(fecha.format( oCuota.getFecha_vencimiento() ));
		tMonto.setText(moneda.format(oCuota.getValor()));
			
		// Dibujo la pantalla
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
        
        lCuotaNumero = new CCLabel("Cuota:",22);        
        tCuotaNumero = new CCTextField(300,22,10,true);
        tCuotaNumero.setEnabled(false);
           
        lPeriodoCuota = new CCLabel("Período:",22);        
        tPeriodoCuota = new CCTextField(300,22,20,true);
        tPeriodoCuota.setEnabled(false);
        
        lVencimiento = new CCLabel("Vencimiento:",22);        
        tVencimiento = new CCTextField(200,22,20,true);
        tVencimiento.setEnabled(false);

        lMonto = new CCLabel("Monto:",22);        
        tMonto = new CCTextField(200,22,20,true);
        tMonto.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");
        tMonto.setEnabled(true);
    	
        /* Configuro el boton de cálculo de intereses */
        
    }
    
    
    private void renderObjetos() {
        
    	// Agrego la columna al ContentPane Principal
    	cpPrincipal.add(_cPrincipal);

        _cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        
        
        cLabels.add(lCuotaNumero);
        cLabels.add(lPeriodoCuota);
        cLabels.add(lVencimiento);
        cLabels.add(lMonto);

        cTexts.add(tCuotaNumero);
        cTexts.add(tPeriodoCuota);
        cTexts.add(tVencimiento);
        cTexts.add(tMonto);
        
        // Agrego el boton de los intereses a la barra inferior
        //this.rBotones.add(new Separator());
        //this.rBotones.add(btnIntereses);
        
        ApplicationInstance.getActive().setFocusedComponent(tMonto);
        
    }
    
    
    
    /* Si quiero usar polimorfismo del método */
    @SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {    	
    	
    	if (e.getActionCommand().equals("ok")) {
    		
    		
    		
    			/** Reglas de NEGOCIO */
    			
    			/* Valido el monto de la cuota sea positivo ¡? */
//    			double monto_max = oReciboCobroItem.getMonto();
//    			if (total_item > monto_max) {
//    			
//    				CPPrincipal.abrirVentana(new FWWindowPaneMensajes("Error Edición Items","El monto no puede superar al saldo del item.",(short)1));
//    				return;
//    				
//    			} else {
//    			
    			
    			/* Valido para que se cumplan las expresiones regulares */
    			boolean campo_valido = Pattern.matches(tMonto.getRegex(), tMonto.getText());
    			if (!campo_valido) {
    				CPPrincipal.abrirVentana(new FWWindowPaneMensajes("Valores Inválidos","El monto ingresado no es correcto.",(short)1));    				
    				return;
    				
    			}
    			
    			
    			/** Tengo que actualizar todas las cuotas seleccionadas */
    				double total_cuota = (double)Utiles.ParseFloat(tMonto.getText());
    				refClaseLlama.actualizarCuotas(total_cuota);
    		
    				
//	    			this.oCuota.setValor(total_cuota);
//	    		
//	    			refClaseLlama.oModel.getDataList().set(index, oCuota);
//	    			refClaseLlama.update(refClaseLlama.oModel,0,0);
	    		
	    			this.exit();
	    			return;
//    			}
//    			
//    		} 
    		
    		
    	} // Fin if (ok) 
    	
    	// Tiro el evento para arriba en la gerarquia de objetos !!!!!!!!!! NO LLEGA ACA
    	super.actionPerformed(e);
    	
    }
            
} // Fin clase