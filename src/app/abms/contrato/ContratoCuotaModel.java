package app.abms.contrato;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import app.beans.Cuota;
import app.beans.Utiles;

import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class ContratoCuotaModel extends AbstractTableModel { 
	
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
		return 4; 
	} 
	
	/** Cantidad de Filas */
	public int getRowCount() { 
		return dataList.size(); 
	} 
	
	public void setValueAt(	Object aValue,
            				int rowIndex,
            				int columnIndex) {
		
		System.out.println("SetValueAt " + rowIndex + " - " + columnIndex);
		
	}
	
	/** Aca va el valor de devolución */
	public Object getValueAt(int column, int row) {
		
		Cuota currentObject = (Cuota) dataList.get(row);
		
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat moneda = new DecimalFormat("###,##0.00");
		
		String periodo = currentObject.getPeriodo_anio() + " - " + Utiles.convertirMes(currentObject.getPeriodo_mes());
//		
//		String vencimiento = "";
//		try { 
//			vencimiento = fecha.format(currentObject.getFechaVencimiento());
//		} catch(NullPointerException npe) {}
//		
//		String novedad_tipo = NovedadTipo.getDescripcion(currentObject.getIdNovedadTipo());
		
//		CCTextField editable = new CCTextField();		
//		editable.setWidth(new Extent(100, Extent.PX));
//		editable.setHeight(new Extent(18, Extent.PX));		
//		editable.setText(moneda.format(currentObject.getValor()));
//		editable.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");
		
        switch (column) {
        	case 0: return currentObject.getCuota();
        	case 1: return periodo;
        	case 2: return fecha.format(currentObject.getFecha_vencimiento());
        	case 3: return moneda.format(currentObject.getValor());
        	
//        	
//        	case 3:
//        		// Si viene la fila 0 inicializo el acumulador
//        		if (row == 0) total_monto = 0;
//        		total_monto += currentObject.getMonto();
//        		//System.out.println("TOTAL: " + column + " - " + row + ": "+ total);		
//        		return moneda.format(currentObject.getMonto());
//        		
//        	case 4: 
//        		if (row == 0) total_cobro = 0;
//        		total_cobro += currentObject.getSaldo();       
//        		System.out.println("TOTAL: " + column + " - " + row + ": "+ total_cobro);	
//        		return moneda.format(currentObject.getSaldo());
//        	case 5: return vencimiento;
        	default: return "error";
        }

	} 
	
	
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("Cuota");
            case 1: return new String("Periodo");
            case 2: return new String("Vencimiento");
            case 3: return new String("Monto");
//            case 4: return new String("Saldo");
//            case 5: return new String("Vencimiento");
            default: return new String("ERROR");
        }
    }
	
}