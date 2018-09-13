package app.abms.persona;

import app.beans.Utiles;
import app.combos.ComboGANANCIAS;
import app.combos.ComboInscripcionIVA;
import app.combos.ComboLocalidades;
import app.combos.ComboTiposDocumento;
import ccecho2.base.CCColumn;
import ccecho2.base.CCContentPaneEx;
import ccecho2.base.CCDateField;
import ccecho2.base.CCLabel;
import ccecho2.base.CCRadioButton;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextArea;
import ccecho2.base.CCTextField;

import ccecho2.complex.ComboList.ComboList;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;

import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;


import datos.localidad.Localidad;
import datos.persona.PersonaFacade;
import datos.persona.Persona;


import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMAddView;

@SuppressWarnings("serial")
public class PersonaAddView extends ABMAddView {
	
	
    public PersonaAddView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Alta de Personas");
		this.setHeight(new Extent(550, Extent.PX));
		this.setWidth(new Extent(600, Extent.PX));
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
    }
    
    
    
    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rResponsabilidad;
	
	private CCLabel lMensaje;
	private CCLabel lApellido, lNombres, lDireccion, lTelefono, lCelular, lDocTipo, lDocNum, lMail, 
					lLocalidad, lIVA, lGANANCIAS, lCUIT, lFechaNac, lResponsabilidad, lRazonSocial, lObservaciones, lVacio;
	private CCTextField tApellido, tNombres, tDireccion, tTelefono, tCelular, tDoc, tMail, tCUIT, tRazonSocial;
	ComboList cboDocumentoTipo, cboLocalidades, cboIVA, cboGANANCIAS;
	
	CCRadioButton rbSexo, rbResponsabilidad1, rbResponsabilidad2;
	ButtonGroup bgResposabilidad;
	short persona_tipo = Persona.PersonaFisica;
	
	private CCTextArea tObservaciones;
	
	CCDateField dfFecha_nac;
	//DateChooser dcFecha_nac;
	
	// Tengo que cargar un combooooo el de localidades (dinámico) -- LISTO
	// Tengo otro combo - el de tipo de documento (estático) -- LISTO
	// Y la fecha de nacimiento ????? calendar -- LISTO
	// Falta el campo observaciones 
	
    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10, Extent.PX));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10, Extent.PX));
        cTexts.setInsets(new Insets(10));
        
        
        /*******************************************************************/
        
        lVacio = new CCLabel("",22);
        
        rResponsabilidad = new CCRow(22);
        lResponsabilidad = new CCLabel("Responsabilidad:",22);
        bgResposabilidad = new ButtonGroup();
        rbResponsabilidad1 = new CCRadioButton("Física", 1);
        rbResponsabilidad2 = new CCRadioButton("Jurídica", 2);
        rbResponsabilidad1.setSelected(true);
        
        rbResponsabilidad1.addActionListener(this);
        rbResponsabilidad2.addActionListener(this);
        
        lApellido = new CCLabel("Apellido:",22);        
        tApellido = new CCTextField(300,22,10,true);
           
        lNombres = new CCLabel("Nombres:",22);        
        tNombres = new CCTextField(300,22,20,true);

        lDocTipo = new CCLabel("Tipo de Doc.:",22);
        cboDocumentoTipo = new ComboTiposDocumento(100,22,30,true);
        
        lDocNum = new CCLabel("Número de Doc.:",22);
        tDoc = new CCTextField(100,22,40,true);
        tDoc.setRegex("^(\\d){0,3}(\\.(\\d){3})*$");
        
        lDireccion = new CCLabel("Calle y Número:",22);
        tDireccion = new CCTextField(300,22,50, true);
        
        lLocalidad = new CCLabel("Localidad:",22);
        cboLocalidades = new ComboLocalidades(100,22,60,true);
        
        lTelefono = new CCLabel("Teléfono:",22);
        tTelefono = new CCTextField(100,22,70,true);
        tTelefono.setMaximumLength(100);
        
        lCelular = new CCLabel("Celular:",22);
        tCelular = new CCTextField(100,22,80,true);
        tCelular.setMaximumLength(100);
        
        lMail = new CCLabel("Correo electrónico:",22);
        tMail = new CCTextField(300,22,90,true);
        tMail.setMaximumLength(100);
        
        lRazonSocial = new CCLabel("Razón Social:",22);
        tRazonSocial = new CCTextField(300,22,100,true);
        tRazonSocial.setEnabled(false);
        
        lIVA = new CCLabel("Responsabilidad IVA:",22);
        cboIVA = new ComboInscripcionIVA(200,22,110,true);

        lGANANCIAS = new CCLabel("GANANCIAS:",22);
        cboGANANCIAS = new ComboGANANCIAS(200,22,110,true,false);
        
        lCUIT = new CCLabel("CUIT:",22);
        tCUIT = new CCTextField(100,22,120,true);
        tCUIT.setMaximumLength(13);
        //tCUIT.setRegex("^(\\d){2}(-(\\d){8})(-\\d{1})");

        lFechaNac = new CCLabel("Fecha nacimiento:",22);
        dfFecha_nac = new CCDateField();
        //dcFecha_nac = new DateChooser();
        
        
        //dfFecha_nac.setStyleName("Default");
        //dfFecha_nac.setFocusTraversalParticipant(true);
        
        lObservaciones = new CCLabel("Observaciones:",22*8);
        tObservaciones = new CCTextArea(300,22,8,true);
                
        /*******************************************************************/
        rBotones = new CCRow();
        rBotones.setInsets(new Insets(10));
        rBotones.setAlignment(Alignment.ALIGN_CENTER);

        
        /*******************************************************************/
        rMensaje = new CCRow();
        rMensaje.setAlignment(Alignment.ALIGN_CENTER);

        lMensaje = new CCLabel();
        lMensaje.setForeground(Color.RED);

    }
    
    
    private void renderObjetos() {
        
    	// Agrego la columna al ContentPane Principal
    	cpPrincipal.add(_cPrincipal);

        _cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        
        //rPrincipal.setBorder(new Border(1, Color.BLUE,Border.STYLE_DASHED));
        
        _cPrincipal.add(rMensaje);
        _cPrincipal.add(rBotones);        
        
        // Fila que contiene el RadioButton de Responsabilidad ante IVA
        rResponsabilidad.add(rbResponsabilidad1);        
        rResponsabilidad.add(rbResponsabilidad2);
        
        rbResponsabilidad1.setGroup(bgResposabilidad);
        rbResponsabilidad2.setGroup(bgResposabilidad);
        
        cLabels.add(lResponsabilidad);
        cLabels.add(lApellido);
        cLabels.add(lNombres);
        cLabels.add(lDocTipo);
        cLabels.add(lDocNum);
        cLabels.add(lFechaNac);
        cLabels.add(lDireccion);
        cLabels.add(lLocalidad);
        cLabels.add(lTelefono);
        cLabels.add(lCelular);
        cLabels.add(lMail);
        cLabels.add(lRazonSocial);
        cLabels.add(lIVA);
        cLabels.add(lGANANCIAS);
        cLabels.add(lCUIT);        
        cLabels.add(lObservaciones);        
        
        cTexts.add(rResponsabilidad);
        cTexts.add(tApellido);
        cTexts.add(tNombres);        
        cTexts.add(cboDocumentoTipo);
        cTexts.add(tDoc);
        cTexts.add(dfFecha_nac);
        cTexts.add(tDireccion);
        cTexts.add(cboLocalidades);
        cTexts.add(tTelefono);
        cTexts.add(tCelular);
        cTexts.add(tMail);
        cTexts.add(tRazonSocial);
        cTexts.add(cboIVA);
        cTexts.add(cboGANANCIAS);
        cTexts.add(tCUIT);
        cTexts.add(tObservaciones);
        //cTexts.add(lVacio);
                
        rMensaje.add(lMensaje);

        ApplicationInstance.getActive().setFocusedComponent(tApellido);
        
    }
	
	
    public boolean insert() {

    	boolean salida = true;
    	
    	// Levanto los valores ingresados
    	Persona oPersona = new Persona();
    	
    	// Evaluo Persona FISICA o JURIDICA
    	
    	/** Datos Variables */
    	
    	if (persona_tipo == Persona.PersonaFisica) {
    		
    		// Valido los datos ingresados que no sean vacios
    		if (tApellido.getText().trim().length() == 0 || tNombres.getText().trim().length() == 0) {
    			ApplicationInstance.getActive().setFocusedComponent(tApellido);
    			new FWWindowPaneMensajes("El Apellido y Nombre no pueden ser vacios.", "ERROR");
            	return false;
    		}
    		
    		try {
    			
    			oPersona.setIdPersonaTipo(Persona.PersonaFisica);
    			oPersona.setApellido(tApellido.getText().trim());
    			oPersona.setNombres(tNombres.getText().trim());    		
    			
    			if (!tDoc.getText().equals("")) oPersona.setDocumentoNro(Utiles.ParseLong(tDoc.getText()));
    			
    			oPersona.setCuit(tCUIT.getText());
    			oPersona.setFechaNacimiento(dfFecha_nac.getSelectedDate().getTime());
    						
    			//System.out.println("Datos COMBO " + cboDocumentoTipo.getSelectedId() + " - " + cboIVA.getSelectedId());
    			
    			oPersona.setIdDocumentoTipo(Short.parseShort(cboDocumentoTipo.getSelectedId()));
    			oPersona.setIdInscripcionIva(Short.parseShort(cboIVA.getSelectedId()));
    			oPersona.setIdInscripcionGanancias(Short.parseShort(cboGANANCIAS.getSelectedId()));
				
			} catch (Exception e) {
				// Aca hay un error en la carga de datos
				e.printStackTrace();
								
				// Informo de el error
				salida = false;
			}
			
			
			
    	} else if (persona_tipo == Persona.PersonaJuridica) {
    		
    		// Valido los datos ingresados que no sean vacios
    		if (tRazonSocial.getText().trim().length() == 0) {
    			ApplicationInstance.getActive().setFocusedComponent(tRazonSocial);
    			new FWWindowPaneMensajes("La razón social no pueden ser vacia.", "ERROR");
            	return false;
    		}
    		
    		try {
				
        		oPersona.setIdPersonaTipo(Persona.PersonaJuridica);
        		oPersona.setRazonSocial(tRazonSocial.getText().trim());
        		oPersona.setCuit(tCUIT.getText());
    			oPersona.setIdInscripcionIva(Short.parseShort(cboIVA.getSelectedId()));
    			oPersona.setIdInscripcionGanancias(Short.parseShort(cboGANANCIAS.getSelectedId()));
				
			} catch (Exception e) {
				// Aca hay un error en la carga de datos
				e.printStackTrace();
				
				// Informo de el error
				salida = false;
			}
    		    		
    	} else {
    		// Aca hay un error en la carga de datos
    		salida = false;
    	}
    	
    	/** Datos Fijos */    	
		try {
			
			oPersona.setDireccion(tDireccion.getText());
			
			Localidad oLocalidad = new Localidad(Integer.parseInt(cboLocalidades.getSelectedId()));
			oPersona.setLocalidad(oLocalidad);
		
		} catch (Exception e1) {
			e1.printStackTrace();
			
			// Informo de el error - puede que no sea necesario ingresar el domicilio
			// Por ejemplo cuando es un inquilino, el domicilio es el del CONTRATO ja
			// salida = false;
			
		}
			
		try {
			
	    	oPersona.setCelular(tCelular.getText());
	    	oPersona.setMail(tMail.getText());
	    	oPersona.setTelefono(tTelefono.getText());
	    	oPersona.setObservaciones(tObservaciones.getText());
			
		} catch (Exception e1) {
			e1.printStackTrace();
			
			// Informo de el error
			salida = false;
			
		}    	
    	
    	// Inserto -- Siempre que no haya saltado algún error de validación de datos
		if (salida) {
			
	    	try {
	    		PersonaFacade.save(oPersona);	    		    		
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    		salida = false;
	    		//new MessageWindowPane(e.getMessage());
	    	}
		}
    	
    	return salida;

    }
    
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getSource() instanceof CCRadioButton) {
    		
    		CCRadioButton obj = (CCRadioButton)ae.getSource();
    		short value = Short.parseShort(obj.getActionCommand());
    		
    		if (value == Persona.PersonaFisica) {
    			
    			tApellido.setEnabled(true);
    			tNombres.setEnabled(true);
    			cboDocumentoTipo.setEnabled(true);
    			tDoc.setEnabled(true);
    			dfFecha_nac.setEnabled(true);
    			
    			persona_tipo = Persona.PersonaFisica;
    			
    			tRazonSocial.setEnabled(false);
    			
    		} else if (value == Persona.PersonaJuridica) {
    			
    			tApellido.setEnabled(false);
    			tNombres.setEnabled(false);
    			cboDocumentoTipo.setEnabled(false);
    			tDoc.setEnabled(false);
    			dfFecha_nac.setEnabled(false);
    			
    			persona_tipo = Persona.PersonaJuridica;
    			
    			tRazonSocial.setEnabled(true);
    			
    		}
    			
    	}
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
    	
    }
    
    public void salir() {
    	this.cpPrincipal = new CCContentPaneEx();
    	crearObjetos();
		renderObjetos();
    }
           
}