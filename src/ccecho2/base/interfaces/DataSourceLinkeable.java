package ccecho2.base.interfaces;

import java.util.Hashtable;

/**
 *Interfaz implementada por componentes que
 *puedan ser vinculados a orígenes de datos
 */
public interface DataSourceLinkeable {
    // obtiene la clave de búsqueda del origen de datos
    public String getField();
    // Setea la clave de búsqueda del origen de datos
    public void setField(String field);
    // Obtiene el Hashtable que representa el origen de datos
    public Hashtable getDataSource();
    // Setea el Hashtable que representa el origen de datos
    public void setDataSource(Hashtable dataSource);
    // Actualiza el origen de datos según el valor actual
    public boolean updateDataSource ();
}
