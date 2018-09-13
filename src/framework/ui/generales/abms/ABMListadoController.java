/*
 * AcercaDeController.java
 *
 * Created on 30 de abril de 2007, 10:52
 *
 */

package framework.ui.generales.abms;

//import framework.abstractMVC.controllers.AbstractController;
//import framework.abstractMVC.interfaces.MVCAddViewInterface;
//import framework.abstractMVC.interfaces.MVCEditViewInterface;
import framework.nr.generales.abms.ABMSessionBeanInterface;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
//import oracle.toplink.essentials.exceptions.EJBQLException;

/**
 * Controlador de ABM comunes
 **/
public class ABMListadoController {
    /** SessionBean contra el que interactua el controlador */
    protected ABMSessionBeanInterface session;
    /** Tamaño del vector que representa una página de datos */
    private int pageSize = 2;
    /** Clase a instanciar como vista de edición */
    protected Class editView;
    /** Clase a instanciar como vista de alta de registro */
    protected Class addView;
    /** Orden en que se mostrarán los datos, debe coincidir con
     *  una propiedad del entity tratado
     */
    private String order = "";
    /** Lista de propiedades adicionales a obtener en los hash */
    private ArrayList<String> detailKeys = new ArrayList<String>();
    
    public ABMListadoController(java.util.Observable model,
                                Class listView,
                                Class editView,
                                Class addView,
                                String SessionName,
                                String order,
                                boolean getDataOnInit,
                                ArrayList<String> detailKeys) {
        //super(model, listView);
        this.editView = editView;
        this.addView = addView;
        this.session = lookupSessionBean(SessionName);
        this.order = order;
        this.detailKeys = detailKeys;
        if (getDataOnInit) {
            this.first();
        }
        
    }
    
    /**
     *  Obtiene la primera página de datos y la carga en el modelo
     */
    public void first() {
    	/*
        String[] filterValues = ((ABMListadoModel) this.model).getFilterValues();
        Vector<Hashtable> lista = new Vector<Hashtable>();
        if (filterValues.length == 0) {
            lista = this.session.findByPage(0, this.pageSize,
                    this.order, this.detailKeys);
        } else {
            lista = this.session.findByPage(0, this.pageSize,
                    this.order, this.detailKeys, filterValues);
        }
        ((ABMListadoModel)this.model).setDataPage(lista);
        ((ABMListadoModel)this.model).setCurrentPage(0);
        ((ABMListadoModel)this.model).notifyObservers();
        */
    }

    /**
     *  Obtiene la primera página y la carga en el modelo
     */
    public void previous() {
    	/*
        int page = ((ABMListadoModel) this.model).getCurrentPage() - 1;
        if (page >= 0) {
            String[] filterValues = ((ABMListadoModel) this.model).getFilterValues();
            Vector<Hashtable> lista = new Vector<Hashtable>();
            if (filterValues.length == 0) {
                lista = this.session.findByPage(page, pageSize,
                        this.order, this.detailKeys);
            } else {
                lista = this.session.findByPage(page, pageSize,
                        this.order, this.detailKeys, filterValues);
            }
            if (lista.size() > 0) {
                ((ABMListadoModel) this.model).setDataPage(lista);
                ((ABMListadoModel) this.model).setCurrentPage(page);
                ((ABMListadoModel) this.model).notifyObservers();
            }
        }
        */
    }

    /**
     *  Obtiene la siguiente página y la carga en el modelo
     */
    public void next() {
    	/*
        int page = ((ABMListadoModel) this.model).getCurrentPage() + 1;
        String[] filterValues = ((ABMListadoModel) this.model).getFilterValues();
        Vector<Hashtable> lista = new Vector<Hashtable>();
        if (filterValues.length == 0) {
            lista = this.session.findByPage(page, pageSize,
                    this.order, this.detailKeys);
        } else {
            lista = this.session.findByPage(page, pageSize,
                    this.order, this.detailKeys, filterValues);
        }
        if (lista.size() > 0) {
            ((ABMListadoModel) this.model).setDataPage(lista);
            ((ABMListadoModel) this.model).setCurrentPage(page);
            ((ABMListadoModel) this.model).notifyObservers();
        }
        */
    }

    /**
     *  Obtiene la última página y la carga en el modelo
     */
    public void last() {
    	/*
        String[] filterValues = ((ABMListadoModel) this.model).getFilterValues();
        Vector<Hashtable> lista = new Vector<Hashtable>();
        int paginas = 0;
        if (filterValues.length == 0) {
            paginas = this.session.getPageCount(pageSize);
            lista = this.session.findByPage(paginas, pageSize,
                    this.order, this.detailKeys);
        } else {
            paginas = this.session.getPageCount(pageSize, filterValues);
            lista = this.session.findByPage(paginas, pageSize,
                    this.order, this.detailKeys, filterValues);
        }
        if (lista.size() > 0) {
            ((ABMListadoModel) this.model).setDataPage(lista);
            ((ABMListadoModel) this.model).setCurrentPage(paginas);
            ((ABMListadoModel) this.model).notifyObservers();
        }
        */
    }


    /**
     *  Elimina el registro referenciado por la fila actual del modelo
     */
    public void delete(int currentRow) {
    	/*
        // Obtengo cantidad de filas en la página actual
        int maxPageRow = ((ABMListadoModel) this.model).getDataPage().size();
        // Si la fila a borrar se encuentra contenida en la página
        if ((currentRow >= 0) && (currentRow < maxPageRow)) {
            // Borro la fila mediante el SessionBean
            this.session.destroy((Hashtable) ((ABMListadoModel) this.model)
                    .getDataPage().get(currentRow));
        }

        // Obtengo cantidad de páginas
        int paginas = this.session.getPageCount(pageSize);
        // Obtengo página actual
        int page = ((ABMListadoModel) this.model).getCurrentPage();
        /* si la página actual queda fuera del intervalo, actualizo la
         *  el valor para darle la última disponible
         /
        if (page > paginas) {
            page = paginas;
        }

        /* Recupero la página para actualizar el modelo y
         * notificar a las vistas
         /
        Vector<Hashtable> lista = this.session.findByPage(page, this.pageSize,
                this.order, this.detailKeys);
        ((ABMListadoModel) this.model).setDataPage(lista);
        ((ABMListadoModel) this.model).setCurrentPage(page);
        ((ABMListadoModel) this.model).notifyObservers();
        */
    }

    /**
     *  Actualiza el registro referenciado por la fila actual del modelo
     */
    public void update(Hashtable hash) {
        // Envío a editar el hashtable al SessionBean
        
    	/*
    	this.session.edit(hash);

        // Obtengo cantidad de páginas
        int paginas = this.session.getPageCount(pageSize);
        // Obtengo número de página actual
        int page = ((ABMListadoModel) this.model).getCurrentPage();
        /* Si la página actual está fuera del 
         *  rango seteo la última como actual
         /
        if (page > paginas) {
            page = paginas;
        }

        // Obtengo la pagina actual y notifico a las vistas
        Vector<Hashtable> lista = this.session.findByPage(page, this.pageSize,
                this.order, this.detailKeys);
        ((ABMListadoModel) this.model).setDataPage(lista);
        ((ABMListadoModel) this.model).setCurrentPage(page);
        ((ABMListadoModel) this.model).notifyObservers();
        */
    }

    /**
     *  Actualiza el registro referenciado por la fila actual del modelo
     */
    public void add(Hashtable hash) {
    	/*
        // Envío a editar el hashtable al SessionBean
        this.session.create(hash);

        // Obtengo cantidad de páginas
        int paginas = this.session.getPageCount(pageSize);
        // Obtengo número de página actual
        int page = ((ABMListadoModel) this.model).getCurrentPage();
        /* Si la página actual está fuera del 
         *  rango seteo la última como actual
         /
        if (page > paginas) {
            page = paginas;
        }

        // Obtengo la pagina actual y notifico a las vistas
        Vector<Hashtable> lista = this.session.findByPage(page, this.pageSize,
                this.order, this.detailKeys);
        ((ABMListadoModel) this.model).setDataPage(lista);
        ((ABMListadoModel) this.model).setCurrentPage(page);
        ((ABMListadoModel) this.model).notifyObservers();
        */
    }

    /**
     *  Instancia una vista de edición para la fila actual del modelo
     */
    public void editRow(int currentRow) {
    	/*
        int maxPageRow = ((ABMListadoModel) this.model).getDataPage().size();
        if ((currentRow >= 0) && (currentRow < maxPageRow)) {
            ((ABMListadoModel) this.model).setCurrentRow(currentRow);
            this.addEditView(this.editView);
        }
        */
    }

    public void addRow() {
    	/*
        ((ABMListadoModel) this.model).setEmpty(this.session.getEmpty());
        this.addAddView(this.addView);
        */
    }

    /**
     *  Instancia una vista de edición
     */
    public void addEditView(Class editView) {
    	/*
        MVCEditViewInterface view;
        try {
            // Instancia la vista
            view = (MVCEditViewInterface) editView.newInstance();
            // Referencia al controlador en la vista
            view.setController(this);
            // La vista como observadora del modelo
            this.model.addObserver(view);
            this.model.notifyObservers();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }
        */
    }

    /**
     *  Instancia una vista de edición
     */
    public void addAddView(Class addView) {
    	/*
        MVCAddViewInterface view;
        try {
            // Instancia la vista
            view = (MVCAddViewInterface) addView.newInstance();
            // Referencia al controlador en la vista
            view.setController(this);
            // La vista como observadora del modelo
            this.model.addObserver(view);
            ((ABMListadoModel) this.model).setCurrentRow(-1);
            this.model.notifyObservers();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }
        */
    }

    
    /**
     *  Remueve una vista de la lista de Observers dek modelo
     */
    public void removeEditView() {//MVCEditViewInterface editView) {
        // Quito la vista del modelo
        // this.model.deleteObserver(editView);
    }
    
    public void removeAddView(){ //MVCAddViewInterface addView) {
        // Quito la vista del modelo
        // this.model.deleteObserver(addView);
    }

    /**
     *  Obtengo via JNDI una instancia del SessionBean
     */
    private ABMSessionBeanInterface lookupSessionBean(String SessionName) {
        try {
            Context c = new InitialContext();
            return (ABMSessionBeanInterface) c.lookup(SessionName);
        }
        catch(NamingException ne) {
            ne.printStackTrace();
            throw new RuntimeException(ne);
        }
    }

    void getFilteredList(String[] argumentos) {
        //((ABMListadoModel)this.model).setFilterValues(argumentos);
        this.first();
    }

}