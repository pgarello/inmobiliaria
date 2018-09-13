package ccecho2.base;

import nextapp.echo2.app.RadioButton;

@SuppressWarnings("serial")
public class CCRadioButton extends RadioButton {

	public CCRadioButton() {
		super();
		this.initComponent();
	}
	
	public CCRadioButton(String s) {
		super(s);
		this.initComponent();
	}

	public CCRadioButton(String s, int value) {
		super(s);
		this.initComponent();
		
		this.setActionCommand("" + value);
		
	}
	
	// Inicializa el componente
    private void initComponent(){
         this.setStyleName("Default");
    }
	
}
