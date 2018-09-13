package app.abms.contrato;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import app.beans.Utiles;

import datos.contrato.Contrato;
import datos.contrato_actor.ContratoActor;

import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class ContratoListadoVencidosModel extends AbstractTableModel { 
	
	private List dataList;

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
		return 8; 
	} 
	
	/** Cantidad de Filas */
	public int getRowCount() { 
		return dataList.size(); 
	} 
	
	/** Aca va el valor de devolución */
	public Object getValueAt(int column, int row) {
		Contrato currentObject = (Contrato) dataList.get(row);		

		String inmueble = currentObject.getInmueble().getDireccion_completa();

		// Evaluo el propietario por si viene nulo 
		// ¿Puedo alquilar algo sin tener cargado el propietario? NOUPPPPP
		String propietario = currentObject.getInmueble().getPropietario().getDescripcion();
		
		String inquilino = ""; 
		Set<ContratoActor> actores = currentObject.getContratoActors();
		for(ContratoActor oActor: actores) {
			if (oActor.getId().getIdActorTipo() == ContratoActor.ActorTipoInquilino) {
				inquilino = oActor.getId().getPersona().getDescripcion();
			}
		}
		
		// calculo la cantidad de días que faltan para que venza el contrato
		
		Calendar fechaHasta = Calendar.getInstance();
		fechaHasta.setTime(currentObject.getFechaHasta());
		
		SimpleDateFormat fecha= new SimpleDateFormat("dd/MM/yyyy");
			
        switch (column) {
        	case 0: return row+1;
        	case 1: return currentObject.getYaRenovado();
        	case 2: return inmueble;
        	case 3: return fecha.format(currentObject.getFechaDesde());
        	case 4: return fecha.format(currentObject.getFechaHasta());
        	case 5: return Utiles.diasQueFaltan(fechaHasta);
        	case 6: return propietario;
        	case 7: return inquilino;
        	default: return "error";
        }

	} 
	
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("");
        	case 1: return null;
            case 2: return new String("Inmueble");
            case 3: return new String("Desde");
            case 4: return new String("Hasta");    
            case 5: return new String("Días");
            case 6: return new String("Propietario");
            case 7: return new String("Inquilino");
            default: return new String("ERROR");
        }
    }


}