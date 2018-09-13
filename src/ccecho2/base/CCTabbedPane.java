/*
 * CCTabbedPane.java
 *
 * Created on 27 de septiembre de 2006, 11:41
 *
 */

package ccecho2.base;

import echopointng.tabbedpane.*;

/**
 *
 * @author SShadow
 */
public class CCTabbedPane extends echopointng.TabbedPane {
    
    /** Creates a new instance of CCTabbedPane */
    
    DefaultTabModel tabModel = new DefaultTabModel();
    
    public CCTabbedPane() {
        super();
        this.setModel(tabModel);
        this.setTabSpacing(0);
    }
    
    public CCTabbedPane(echopointng.tabbedpane.TabModel tabModel) {
        super(tabModel);
    }
    
    
    
    public void addTab(String text, nextapp.echo2.app.Component tabContent)  {
        this.tabModel.addTab(text, tabContent);
    }
    
    public void selectTab(int tab) {
        this.getSelectionModel().setSelectedIndex(tab);
    }

}
