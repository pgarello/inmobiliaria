package resources.reports;

import java.util.Collection;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ReciboCobro implements JRDataSource{

	// Atributos
	Collection cItems;
	
	/**
	 * CONTRUCTOR
	 */
	public ReciboCobro() {
		
	}
	
	public Object getFieldValue(JRField arg0) throws JRException {
		return null;
	}

	public boolean next() throws JRException {
		return false;
	}

	/*
	 import java.io.*;
	 4   import java.util.*;
	 5   
	 6   public class JRCSVDataSource implements dori.jasper.engine.JRDataSource {
	 7       
	 8       String row = "";
	 9       LineNumberReader lineNumberReader;
	 10      
	 11      /** Creates a new instance of JRCVSDataSource /
	 12      public JRCSVDataSource(String cvsFile) {
	 13          try {
	 14              lineNumberReader = new LineNumberReader( new FileReader(cvsFile));
	 15          } catch (Exception ex) { }
	 16      }
	 17      
	 18      public Object getFieldValue(dori.jasper.engine.JRField jRField) throws dori.jasper.engine.JRException {
	 19          String field = jRField.getName();
	 20          int fieldPosition = Integer.parseInt(field.substring(7)); // Strip COLUMN_ 
	 21          StringTokenizer st = new StringTokenizer(row,";");
	 22          while (st.hasMoreTokens())
	 23          {
	 24              fieldPosition--; // The column is not 0 indexed.
	 25              String token = st.nextToken();
	 26              if (fieldPosition == 0) return token;
	 27          }
	 28          return null; // Column not found...
	 29      }
	 30      
	 31      public boolean next() throws dori.jasper.engine.JRException {
	 32          try {
	 33              row = lineNumberReader.readLine();
	 34              if (row.length()>0) return true;
	 35          } catch (Exception ex) { }
	 36          return false;
	 37      }   
	 38  }
*/
	
}
