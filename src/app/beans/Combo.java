package app.beans;

import java.io.Serializable;
import java.util.Comparator;

public class Combo implements Serializable, Comparator<Combo> {
		
	private static final long serialVersionUID = -831284460029642984L;
	
	private String sValor;
	public String getSValor() {return sValor;}
	public void setValor(String value) {this.sValor = value;}
			
	private String sDescripcion;
	public String getSDescripcion() {return sDescripcion;}
	public void setDescripcion(String desc) {this.sDescripcion = desc;}		
	
	private String sDescripcionCorta;
	public String getSDescripcionCorta() {return sDescripcionCorta;}
	public void setDescripcionCorta(String descc) {this.sDescripcionCorta = descc;}		

	private boolean bDefecto;
	public boolean getBDefecto() {return bDefecto;}
	public void setBDefecto(boolean def) {this.bDefecto = def;}		
		
	/**
	 * Constructor de clase
	 * @param _sValor value del objeto combo
	 * @param _sDescripcion descripción que se muestra en el combo
	 * @param _bDefecto es el valor que se muestra por defecto
	 */
	public Combo(String _sValor, String _sDescripcion, boolean _bDefecto) {
		sValor = _sValor;
		sDescripcion = _sDescripcion;
		bDefecto = _bDefecto;
	}

	public Combo(String _sValor, String _sDescripcion, String _sDescripcionCorta , boolean _bDefecto) {
		sValor = _sValor;
		sDescripcion = _sDescripcion;
		sDescripcionCorta = _sDescripcionCorta;
		bDefecto = _bDefecto;
	}
	
	/**
	 * Lo uso para la instancia del Comparator
	 */
	public Combo() {}
	
		
	@Override
	public boolean equals(Object arg0) {
		
		boolean respuesta = false;
		Combo oCombo = (Combo)arg0;
		if (this.sDescripcion.equals(oCombo.getSDescripcion()) &&
			this.sValor.equals(oCombo.getSValor())) {
			respuesta = true;
		}
		
		return respuesta;
	}
	
	
	public int compare(Combo arg0, Combo arg1) {
		int resultado = 0;
		
		//combo oCombo0 = (combo) arg0;
		//combo oCombo1 = (combo) arg1;
		
		resultado = arg0.getSDescripcion().compareToIgnoreCase(arg1.getSDescripcion());
		
		return resultado;
	}
	
}