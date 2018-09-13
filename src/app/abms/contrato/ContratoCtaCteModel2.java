package app.abms.contrato;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import app.beans.NovedadTipo;
import app.beans.Utiles;

//import datos.contrato_novedad_cobro.ContratoNovedadCobro;
import datos.contrato_novedad_pago.ContratoNovedadPago;

import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class ContratoCtaCteModel2 extends AbstractTableModel { 
	
	private List dataList;
	
	private double total_monto;
	private double total_cobro;
		
	public double getTotal_cobro() {
		return total_cobro;
	}

	public double getTotal_monto() {
		return total_monto;
	}

	/**
     * @return the dataList
     */
    public List getDataList() {
        return dataList;
    }

    /**
     * @param dataList the dataList to set
     */
    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

	
	/** Cantidad de Columnas */
	public int getColumnCount() { 
		return 6; 
	} 
	
	/** Cantidad de Filas */
	public int getRowCount() { 
		return dataList.size(); 
	} 
	
	/** Aca va el valor de devolución */
	public Object getValueAt(int column, int row) {
		ContratoNovedadPago currentObject = (ContratoNovedadPago) dataList.get(row);
		
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat moneda = new DecimalFormat("$ ###,##0.00");
		
		String periodo = currentObject.getPeriodoAnio() + " - " + Utiles.convertirMes(currentObject.getPeriodoMes());
		
		String vencimiento = "";
		try { 
			vencimiento = fecha.format(currentObject.getFechaLiquidacion());
		} catch(NullPointerException npe) {}
		
		String novedad_tipo = NovedadTipo.getDescripcion(currentObject.getIdNovedadTipo());
		
		// Si el tipo de novedad es un IMPUESTO - Busco cual es el mismo
		if (currentObject.getIdNovedadTipo() == NovedadTipo.Impuesto) {
			novedad_tipo += "-"+currentObject.getImpuesto()+"("+ currentObject.getImpuestoCuota()+"/"+currentObject.getImpuestoAnio()+")";
		}
		
        switch (column) {
        	case 0: return currentObject.getContratoCuota();
        	case 1: return periodo;
        	case 2: return novedad_tipo;
        	
        	case 3:
        		// Si viene la fila 0 inicializo el acumulador
        		if (row == 0) total_monto = 0;
        		total_monto += currentObject.getMonto();
        		//System.out.println("TOTAL: " + column + " - " + row + ": "+ total);		
        		return moneda.format(currentObject.getMonto());
        		
        	case 4: 
        		if (row == 0) total_cobro = 0;
        		total_cobro += currentObject.getSaldo();       
        		System.out.println("TOTAL: " + column + " - " + row + ": "+ total_cobro);	
        		return moneda.format(currentObject.getSaldo());
        	case 5: return vencimiento;
        	default: return "error";
        }

	} 
	
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("Cuota");
            case 1: return new String("Periodo");
            case 2: return new String("Movimiento");
            case 3: return new String("Monto");
            case 4: return new String("Saldo");
            case 5: return new String("Liquidación");
            default: return new String("ERROR");
        }
    }


}