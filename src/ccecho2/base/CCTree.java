/*
 * CCTree.java
 *
 * Created on 27 de septiembre de 2006, 17:14
 *
 */

package ccecho2.base;

/**
 *
 * @author SShadow
 */

import echopointng.Tree;
import echopointng.tree.*;

public class CCTree extends Tree {
    
    TreeModel model;
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
    /** Creates a new instance of CCTree */
    public CCTree() {
        super();
        model = this.getModel();
        root = (DefaultMutableTreeNode)this.getModel().getRoot();
    }
    
    public CCTree(TreeNode root) {
        super(root);
    }
    
    public CCTree(TreeModel newModel) {
        super(newModel);
    }
    
    public CCTree(TreeNode root, boolean asksAllowsChilden) {
        super(root,asksAllowsChilden);
    }
    
    public void addNode(String text) {
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(text);
        this.root.add(nodo);
    }
    
    public void addNode(DefaultMutableTreeNode nodo) {
        this.root.add(nodo);
    }
    
    public void addNode(DefaultMutableTreeNode nodo, String text) {
        //DefaultMutableTreeNode padre = this.model.getChild(nodo,0);
        //padre.add(nodo);
    }
    
    public void addNode(DefaultMutableTreeNode nodo, DefaultMutableTreeNode hijo) {
        
    }
    
}
