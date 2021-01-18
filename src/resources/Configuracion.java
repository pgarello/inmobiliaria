package resources;

//import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import java.util.Calendar;
import java.util.Properties;

public class Configuracion extends Properties {

	private static final long serialVersionUID = 2454612648495324016L;

	private static Configuracion miConfiguracion;
	
	public static Configuracion getInstance() {
		
		//if (miConfiguracion == null) {
			miConfiguracion = new Configuracion();
		//}
		
		return miConfiguracion;
	}
	
	private Configuracion() {
		super(loadFile("inmobiliaria.ini"));
	}


	public Configuracion(Properties defaults) {
		super(defaults);
	}

	public Configuracion(String archivo) {
		super(loadFile(archivo));
	}
	

	/**
	 * @param
	 */
	// public static void main(String[] args) {}
	
	
	private static Properties loadFile(String file) {
				
		Properties props = new Properties();
		
		FileInputStream oInputStream = null;
		
		try {
			oInputStream = new FileInputStream( System.getProperty("catalina.base") + "/conf/" + file);
			
			// Ambiente PRODUCCION
			//oInputStream = new FileInputStream( "/etc/digitalizacion/" + file);
			
			// Ambiente de TEST
			// oInputStream = new FileInputStream( "/home/pgarello/" + file);
			
			props.load(oInputStream);
		} catch(Exception e) {
			e.printStackTrace();
		
		} finally {
			try {
				oInputStream.close();
			} catch (IOException e) {}
		}
					
		return props;
	}
	
	public String getProperty(String valor) {
		
		String respuesta = "";

		try {
			respuesta = super.getProperty(valor).trim();

			if (respuesta == null) respuesta = "";

		} catch(NullPointerException npe) {
			// no hago nada
			npe.printStackTrace();
		}

		/*
		try {
			respuesta = super.getProperty(valor.toUpperCase()).trim();		
		} catch(NullPointerException npe) {
			// no hago nada
		}
			
		if (respuesta.equals(""))
			try {
				respuesta = super.getProperty(valor.toLowerCase()).trim();					
			} catch(NullPointerException npe) {
				// no hago nada
			}
		*/
		return respuesta;
		
	}
	

}