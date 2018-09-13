/*
 * ComboList.java
 *
 * Created on 20 de junio de 2007, 07:33
 *
 */

package ccecho2.complex.ComboList;

import ccecho2.base.CCComboBox;
import ccecho2.base.interfaces.DataSourceLinkeable;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import nextapp.echo2.app.list.DefaultListModel;

/**
 *
 * @author luigi
 */
public class ComboList extends CCComboBox implements Serializable, DataSourceLinkeable {
	
	private static final long serialVersionUID = 1L;
	
    private DefaultListModel listModel = null;
    
    private ComboListItem[] lista = null;
    
    private Vector valores;
    private Hashtable dataSource = null;
    private String field = "";
    
    /** Creates a new instance of ComboList */
    @SuppressWarnings("unchecked")
	public ComboList(String sessionName, String keyDetail, String keyValue) {
        super();

        // Obtengo el session contra el que trabajo
        ComboSessionBeanInterface session = lookupSessionBean(sessionName);

        Hashtable[] listaHash = session.getHashList();
        lista = new ComboListItem[listaHash.length];
        valores = new Vector();
        for (int i = 0; i< listaHash.length; i++) {
            Hashtable hash = listaHash[i];
            Hashtable hashValue = (Hashtable) hash.get("values");
            ComboListItem item = new ComboListItem(
                                            (String) hashValue.get(keyDetail),
                                            hashValue.get(keyValue));
            this.lista[i] = item;
            this.valores.add(hashValue.get(keyDetail));
        }
        // Obtengo el modelo del session
        this.listModel = new DefaultListModel(this.lista);
        // Creo el combo a partir del modelo
        this.setListModel(this.listModel);
    }
    
    
    public ComboList() {
        super();
    }
    
    @SuppressWarnings("unchecked")
	public void LlenarCombo(ComboListItem[] lCombos) {

        // Recorro el vector de objetos combo

        lista = lCombos;
        valores = new Vector();
        for (int i = 0; i< lCombos.length; i++) {
            
            ComboListItem item = lCombos[i];            
            //this.valores.add((String)item.getValue());
            this.valores.add((String)item.toString());
            
            if (item.getSeleccionado()) this.setSelectedPosition(i);
            
        }
        
        // Obtengo el modelo del session
        this.listModel = new DefaultListModel(this.lista);
        
        // Creo el combo a partir del modelo
        this.setListModel(this.listModel);
    }
    
    
    /**
    *  Obtengo via JNDI una instancia del SessionBean
    */
    private ComboSessionBeanInterface lookupSessionBean(String SessionName) {
        try {
            Context c = new InitialContext();
            return (ComboSessionBeanInterface) c.lookup(SessionName);
        }
        catch(NamingException ne) {
            ne.printStackTrace();
            throw new RuntimeException(ne);
        }
    }
    
    /**
     * Busca y devuelve el valor asociado al item seleccionaod actualmente en
     * el combo
     * @throws ccecho2.complex.ComboList.NoItemSelectedException Si no hay
     * ningun item seleccionado
     * @return El valor definido como clave para el item seleccionado 
     * actualmente
     */
    public String getSelectedId() throws NoItemSelectedException {
    	//System.out.println("ComboList.getSelectedId " + this.getText() + " datos: " + valores.size());
        int posicion = this.valores.indexOf(this.getText());
        if (posicion < 0) {
            throw new NoItemSelectedException();
        }
        ComboListItem item = this.lista[this.valores.indexOf(this.getText())];
        return String.valueOf(item.getValue());
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Hashtable getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(Hashtable dataSource) {
        this.dataSource = dataSource;
    }

    @SuppressWarnings("unchecked")
	public boolean updateDataSource() {
        boolean actualizo = false;
        Object obj = ((Hashtable) this.getDataSource().get("values")).get(this.getField());
        Class clase = obj.getClass();
        try {
            Constructor c = clase.getConstructor(String.class);
            ((Hashtable) this.getDataSource().get("values")).put(this.getField(), c.newInstance(this.getSelectedId()));
            actualizo = true;
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (NoItemSelectedException nise) {
        }
        return actualizo;
    }
    
    /**
     * Setea como seleccionado el item de la lista del combo que tiene un value 
     * igual al id pasado como parámetro
     * Uso: combo.setSetectedText(new Integer(0));
     * @param id El id del elemento que se desea seleccionar en el combo
     * 
     * No anda cuando el value no es un entero (por ejemplo si uso | en el)
     * 
     */
    public void setSelectedText(Integer id) {
        List list = Arrays.asList(this.lista);
        for (ListIterator it = list.listIterator(); it.hasNext();) {
            ComboListItem item = (ComboListItem)it.next();
            if (Integer.valueOf(item.getValue().toString()).equals(id)) {
                this.setText(item.toString());
                return;
            }
        }
    }
    
    
    public void setSelectedText(String id) {
        List list = Arrays.asList(this.lista);
        for (ListIterator it = list.listIterator(); it.hasNext();) {
            ComboListItem item = (ComboListItem)it.next();
            if (item.getValue().toString().equals(id)) {
                this.setText(item.toString());
                return;
            }
        }
    }
    
    public void setSelectedPosition(int posicion) {
        List list = Arrays.asList(this.lista);
        int indice = 0;
        for (ListIterator it = list.listIterator(); it.hasNext();) {
        	indice++;
            ComboListItem item = (ComboListItem)it.next();
            if (indice == posicion) {
                this.setText(item.toString());
                return;
            }
        }
    }
    

    
    public String getText(Short id) {
    	String salida = "";
        List list = Arrays.asList(this.lista);
        for (ListIterator it = list.listIterator(); it.hasNext();) {
            ComboListItem item = (ComboListItem)it.next();
            if (Short.valueOf(item.getValue().toString()).equals(id)) {
                this.setText(item.toString());
                salida = item.toString();
            }
        }        
        return salida;
    }
    
}
