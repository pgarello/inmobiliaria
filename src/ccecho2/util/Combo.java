package ccecho2.util;

/**
 * Datos genéricos usado para los combos
 */
public class Combo {
	
	// Código
	private String sCode;
	
	// Descripción del código
	private String sDescript;
	private String sDescript_corta;
	
	// Valor por defecto	
	private boolean bDefecto; 
	
	/**
	 * Constructor de la clase
	 */
	public Combo( String sCode, String sDescript, boolean bDefecto) {
		 this.sCode = sCode;
		 this.sDescript = sDescript;
		 this.bDefecto = bDefecto;
	}

	/**
	 * Constructor de la clase
	 */
	public Combo( String sCode, String sDescript, String sDescript_corta, boolean bDefecto) {
		 this.sCode = sCode;
		 this.sDescript = sDescript;
		 this.sDescript_corta = sDescript_corta;
		 this.bDefecto = bDefecto;
	}


	
	/**
	 * Retorna el codigo
	 */
	public String getSCode() {return this.sCode;}
	
	/**
	 * Retorna el descripcion del codigo
	 */
	public String getSDescript() {return this.sDescript;}
	public String getSDescript_corta() {return this.sDescript_corta;}
	
	/**
	 * Retorna el valor por defecto
	 */
	public boolean getBDefecto() {return this.bDefecto;}
	
}