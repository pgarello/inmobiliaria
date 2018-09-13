/*
 * LabeledTextField.java
 *
 * Created on 1 de marzo de 2007, 16:33
 *
 */

package ccecho2.complex;

import ccecho2.base.CCLabel;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;

/**
 *
 * @author SShadow
 */
public class LabeledTextField extends CCRow {
    
    private CCLabel label = new CCLabel("");
    private CCTextField textField = new CCTextField();
    
    /** Creates a new instance of LabeledTextField */
    public LabeledTextField(String label) {
        super();
        this.getLabel().setText(label);
        
        this.add(this.label);
        this.add(this.textField);
        
    }
    
    /* 
     * Establece el texto que se mostrar como etiqueta del campo
     */
    public void setLabelText(String label){
        this.getLabel().setText(label);
    }
    
    /* 
     * Establecer el valor de la caja de texto
     */
    public void setTextFieldValue(String s){
        this.getTextField().setText(s);
    }

    /* 
     * Devuelve el CCLabel que est� utilizando el componente
     */
    public CCLabel getLabel() {
        return label;
    }

    /* 
     * Devuelve el CCTextField que est� utilizando el componente
     */
    public CCTextField getTextField() {
        return textField;
    }

    
}

