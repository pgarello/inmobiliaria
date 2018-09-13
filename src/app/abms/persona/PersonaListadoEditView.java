package app.abms.persona;

import java.text.SimpleDateFormat;

import app.combos.ComboGANANCIAS;
import app.combos.ComboInscripcionIVA;
import app.combos.ComboLocalidades;
import app.combos.ComboTiposDocumento;
import ccecho2.base.CCColumn;
import ccecho2.base.CCDateField;
import ccecho2.base.CCLabel;
import ccecho2.base.CCRadioButton;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextArea;
import ccecho2.base.CCTextField;

import ccecho2.complex.MessageWindowPane;
import ccecho2.complex.ComboList.ComboList;


import framework.ui.generales.abms.ABMListadoEditView;

import datos.localidad.Localidad;
import datos.persona.Persona;
import datos.persona.PersonaFacade;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;

import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.layout.RowLayoutData;

@SuppressWarnings("serial")
public class PersonaListadoEditView extends ABMListadoEditView {
    
	
	private PersonaListadoView listado;
	private Persona oPersona;

	public PersonaListadoEditView(PersonaListadoView listado, Persona oPersona) {
		
		super();
		
		// Es la clase que me llamo
		this.listado = listado;
		
		this.oPersona = oPersona;
		
		// Seteo el título de la ventana
		this.setTitle("Modificación de Personas - Desde Listado");
		this.setHeight(new Extent(500, Extent.PX));
		this.setWidth(new Extent(600, Extent.PX));
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		asignarDatos();
		renderObjetos();
		
	}
	
	
	private CCColumn cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rResponsabilidad;
	
	private CCLabel lMensaje;
	private CCLabel lApellido, lNombres, lDireccion, lTelefono, lCelular, lDocTipo, lDocNum, lMail, 
					lLocalidad, lIVA, lCUIT, lFechaNac, lResponsabilidad, lRazonSocial, lObservaciones, lGANANCIAS;
	
	private CCTextField tApellido, tNombres, tDireccion, tTelefono, tCelular, tDoc, tMail, tCUIT, tRazonSocial;
	ComboList cboDocumentoTipo, cboLocalidades, cboIVA, cboGANANCIAS;

	CCRadioButton rbSexo, rbResponsabilidad1, rbResponsabilidad2;
	ButtonGroup bgResposabilidad;
	short persona_tipo = Persona.PersonaFisica;

	CCDateField dfFecha_nac;	
	private CCTextArea tObservaciones;
	
    private void crearObjetos() {
    	
        cPrincipal = new CCColumn();
        cPrincipal.setCellSpacing(new Extent(20));
        cPrincipal.setInsets(new Insets(10));
        rPrincipal = new CCRow();

        rMensaje = new CCRow();
        rMensaje.setAlignment(Alignment.ALIGN_CENTER);
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10));
        cLabels.setInsets(new Insets(10));
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10));
        cTexts.setInsets(new Insets(10));
        
        //tUsuario.setText(oUsuario.getUsuario());
        //tUsuario.setEnabled(false);
        //tUsuario.setBackground(Color.LIGHTGRAY);
        
        /*
         Ver que datos permito modificar -
         Tener en cuenta si ya forma parte de un contrato vigente/vencido !!!
         */

        /*******************************************************************/
        rResponsabilidad = new CCRow(22);
        lResponsabilidad = new CCLabel("Responsabilidad:",22);
        bgResposabilidad = new ButtonGroup();
        rbResponsabilidad1 = new CCRadioButton("Física", 1);
        rbResponsabilidad2 = new CCRadioButton("Jurídica", 2);
        
        rbResponsabilidad1.setEnabled(false);
        rbResponsabilidad2.setEnabled(false);
        
        //rbResponsabilidad1.addActionListener(this);
        //rbResponsabilidad2.addActionListener(this);
        
        lApellido = new CCLabel("Apellido:",22);        
        tApellido = new CCTextField(300,22,10,true);
        tApellido.setEnabled(false); // solo se puede ver - no modificar
        
        lNombres = new CCLabel("Nombres:",22);        
        tNombres = new CCTextField(300,22,20,true);
        tNombres.setEnabled(false); // solo se puede ver - no modificar

        lDocTipo = new CCLabel("Tipo de Doc.:",22);
        cboDocumentoTipo = new ComboTiposDocumento(100,22,30,true);
        
        lDocNum = new CCLabel("Número de Doc.:",22);
        tDoc = new CCTextField(100,22,40,true);
        // ver expresiones regulares para un documento
        
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

        lFechaNac = new CCLabel("Fecha nacimiento:",22);
        dfFecha_nac = new CCDateField();
        
        //dfFecha_nac.setStyleName("Default");
        dfFecha_nac.setFocusTraversalParticipant(true);
        
        lObservaciones = new CCLabel("Observaciones:",22*7);
        tObservaciones = new CCTextArea(300,22,7,true);
        
        /*******************************************************************/
        
        rBotones = new CCRow();
        rBotones.setInsets(new Insets(10));
        rBotones.setAlignment(Alignment.ALIGN_CENTER);

        lMensaje = new CCLabel();
        lMensaje.setForeground(Color.RED);

    }
    
    private void renderObjetos() {
        
    	// Agrego la columna al ContentPane Principal
    	cpPrincipal.add(cPrincipal);
    	
        cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        cPrincipal.add(rMensaje);
        cPrincipal.add(rBotones);

        RowLayoutData cLabelLayoutData = new RowLayoutData();
        cLabelLayoutData.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabels.setLayoutData(cLabelLayoutData);
        
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

        rMensaje.add(lMensaje);


        ApplicationInstance.getActive().setFocusedComponent(lResponsabilidad);
        
    }
    
    
    private void asignarDatos() {
    
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	
        
        tDireccion.setText(oPersona.getDireccion());
        
        try {
        	if (oPersona.getLocalidad().getIdLocalidad() != null) {
        		cboLocalidades.setSelectedText(oPersona.getLocalidad().getIdLocalidad());
        	}
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
        tTelefono.setText(oPersona.getTelefono());
        tCelular.setText(oPersona.getCelular());
        tMail.setText(oPersona.getMail());
        cboIVA.setSelectedText(new Integer(oPersona.getIdInscripcionIva()));
        cboGANANCIAS.setSelectedText(new Integer(oPersona.getIdInscripcionGanancias()));
        tCUIT.setText(oPersona.getCuit());
        tObservaciones.setText(oPersona.getObservaciones());
        
        
        // Evaluo la respondabilidad
        if (oPersona.getIdPersonaTipo() == Persona.PersonaFisica) {
        	
        	tApellido.setText(oPersona.getApellido());
            tNombres.setText(oPersona.getNombres());
            cboDocumentoTipo.setSelectedText(new Integer(oPersona.getIdDocumentoTipo()));
            tDoc.setText(""+oPersona.getDocumentoNro());
            dfFecha_nac.getTextField().setText(sdf.format(oPersona.getFechaNacimiento()));
        	
        	rbResponsabilidad1.setSelected(true);        	
        	
        	//tApellido.setEnabled(true);
			//tNombres.setEnabled(true);
			cboDocumentoTipo.setEnabled(true);
			tDoc.setEnabled(true);
			dfFecha_nac.setEnabled(true);
			
			persona_tipo = Persona.PersonaFisica;			
			//tRazonSocial.setEnabled(false);
			
        } else if (oPersona.getIdPersonaTipo() == Persona.PersonaJuridica) {
        	
        	rbResponsabilidad2.setSelected(true);
        	
			//tApellido.setEnabled(false);
			//tNombres.setEnabled(false);
			cboDocumentoTipo.setEnabled(false);
			tDoc.setEnabled(false);
			dfFecha_nac.setEnabled(false);			
			persona_tipo = Persona.PersonaJuridica;			
			
			tRazonSocial.setText(oPersona.getRazonSocial());
			//tRazonSocial.setEnabled(true);
        }

    	
    }
    
    
    public boolean update() {
        
    	boolean salida = true;
    	
    	// Valido la integridad de los datos
    	/*
    	String clave1 = tPassword1.getText().trim();
    	String clave2 = tPassword2.getText().trim();
    	*/
    	
    		    	
	    // Levanto los valores ingresados y se lo seteo al objeto    	
    	if (persona_tipo == Persona.PersonaFisica) {
    		
    		// valido el número de documento
    		try {
    			@SuppressWarnings("unused")
				long documento = Long.parseLong(tDoc.getText());
    		} catch(NumberFormatException nfe) {
    			// Informo el error
    			salida = false;
    			new MessageWindowPane("Ingrese nuevamente el documento, solamente número (sin puntos).");
    			return salida;
    		}
    		
    		try {
				
    			oPersona.setIdPersonaTipo(Persona.PersonaFisica);
        		oPersona.setApellido(tApellido.getText());
        		oPersona.setNombres(tNombres.getText());        		
        		oPersona.setDocumentoNro(Long.parseLong(tDoc.getText()));
        		oPersona.setCuit(tCUIT.getText());
        		oPersona.setFechaNacimiento(dfFecha_nac.getSelectedDate().getTime());
    			
    			//System.out.println("Datos COMBO " + cboDocumentoTipo.getSelectedId() + " - " + cboIVA.getSelectedId());
    			
    			oPersona.setIdDocumentoTipo(Short.parseShort(cboDocumentoTipo.getSelectedId()));
    			oPersona.setIdInscripcionIva(Short.parseShort(cboIVA.getSelectedId()));
    			oPersona.setIdInscripcionGanancias(Short.parseShort(cboGANANCIAS.getSelectedId()));
				
			} catch (Exception e) {
				// Aca hay un error en la carga de datos
				e.printStackTrace();
				salida = false;				
			}
			
			
			
    	} else if (persona_tipo == Persona.PersonaJuridica) {
    		
    		
    		try {
        		oPersona.setIdPersonaTipo(Persona.PersonaJuridica);    		
        		oPersona.setRazonSocial(tRazonSocial.getText());
        		oPersona.setCuit(tCUIT.getText());
				oPersona.setIdInscripcionIva(Short.parseShort(cboIVA.getSelectedId()));
				oPersona.setIdInscripcionGanancias(Short.parseShort(cboGANANCIAS.getSelectedId()));
			} catch (Exception e) {
				// Aca hay un error en la carga de datos
				e.printStackTrace();
				salida = false;
			}
    		
    		
    	} else {
    		// Aca hay un error en la carga de datos
    	}

    	
    	/** Datos Fijos */
		try {			
			oPersona.setDireccion(tDireccion.getText());
			
			Localidad oLocalidad = new Localidad(Integer.parseInt(cboLocalidades.getSelectedId()));
			oPersona.setLocalidad(oLocalidad);
			
	    	oPersona.setCelular(tCelular.getText());
	    	oPersona.setMail(tMail.getText());
	    	oPersona.setTelefono(tTelefono.getText());
	    	oPersona.setObservaciones(tObservaciones.getText());
			
		} catch (Exception e1) {
			e1.printStackTrace();
			salida = false;
			
		}
    	


	    	
    	// Actualizo
		if (salida) {
	    	try {
	    		PersonaFacade.update(oPersona);
	    		
		    	// Actualizo la tabla
		        listado.ActualizarDatos();
		    		
		    } catch(Exception e) {
		    	e.printStackTrace();
		    	salida = false;
		    	new MessageWindowPane(e.getMessage());
		    	//new MessageWindowPane("Se produjo un error en la actualización");
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
    
    
    public void redireccion() {
    	// redirecciono cuando la inserción es correcta a la edición
    	crearObjetos();
		asignarDatos();
		renderObjetos();
    }

}