package framework.ui.principal;


import echopointng.BorderEx;
import echopointng.Separator;
import framework.grales.seguridad.FWUsuario;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.seguridad.FWContentPaneLogin;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.HttpImageReference;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import nextapp.echo2.webrender.WebRenderServlet;

import ccecho2.base.CCMenu;
import ccecho2.base.CCMenuBar;
import ccecho2.base.CCMenuItem;

public class FWMenuBarPrincipal extends CCMenuBar implements nextapp.echo2.app.event.ActionListener {
    
	private static final long serialVersionUID = 1L;
	final ImageReference downArrow = new HttpImageReference("resources/image/menu_submenuButton.gif", new Extent(11), new Extent(11));
	private FWUsuario oFWUsuario;
	
    public FWMenuBarPrincipal(FWUsuario usuario) {
        super();
        
        this.setBorder(BorderEx.NONE);
        
        oFWUsuario = usuario;
        
        // *****************************************************
        // Creo el menú
        // *****************************************************
        // CCMenu mMenu = new CCMenu("ABMs");

        // *****************************************************
        // Itero entre las tareas
        // *****************************************************
        
        /** Por ahora cargo en forma estática el menú - Para todos los usuarios igual
        Iterator i = usuario.getPermisos().iterator();
        while (i.hasNext()) {
            FWTarea tarea = (FWTarea) i.next();
            CCMenuItem miItem = new CCMenuItem(tarea.getDescripcion());
            miItem.setCommand(tarea.getComando());
            
            miItem.addActionListener(
                new ActionListener() {
                    private static final long serialVersionUID = 1L;
                    public void actionPerformed(ActionEvent e) {
                        ((FWContentPanePrincipal) getApplicationInstance()
                                .getDefaultWindow().getContent()).abrirVentana(
                                        ((CCMenuItem)e.getSource()).getCommand());
                    }
                }
            );

            mMenu.add(miItem);
        }
        */
        
// Menú nivel 0 -----------------------------------------------------------------
        CCMenu mMenu = new CCMenu("Principal");
        //mMenu.setSubmenuImage(downArrow);
        
        CCMenuItem miItem = new CCMenuItem("Acerca de ...");
        miItem.setCommand("app.principal.WindowPaneAcercaDe");
        miItem.addActionListener(this);
        mMenu.add(miItem);

        CCMenuItem miItem_1 = new CCMenuItem("Volver al Login");
        miItem_1.addActionListener(
        		new ActionListener() {
        			private static final long serialVersionUID = 1L;
                    public void actionPerformed(ActionEvent e) {
                    	
                    	// Cierro la sesion antes de salir
//                    	ContainerContext containerContext = (ContainerContext)getApplicationInstance().getContextProperty( ContainerContext.CONTEXT_PROPERTY_NAME );
//                    	HttpSession session = containerContext.getSession();
//                    	if ( session != null ){
//                    	     session.invalidate();
//                    	}
                    	
                        getApplicationInstance().getDefaultWindow().setContent(new FWContentPaneLogin());                    	
                    	//getApplicationInstance().doInit();
                    	//getApplicationInstance().init();
                    }
                }
        );
        //mMenu.add(miItem_1);

        CCMenuItem miItem_2 = new CCMenuItem("Salir");
        miItem_2.addActionListener(
        		new ActionListener() {
        			private static final long serialVersionUID = 1L;
                    public void actionPerformed(ActionEvent e) {
                    	
                    	System.out.println("Saliendo 1");
                    	
                    	
//                    	ContainerContext containerContext = (ContainerContext)getApplicationInstance().getContextProperty( ContainerContext.CONTEXT_PROPERTY_NAME );
//                    	HttpSession session = containerContext.getSession();
                    	
                    	getApplicationInstance().getDefaultWindow().setContent(new FWContentPaneLogin(false));
                    	//System.out.println("Saliendo 2 " + session);
                    	
                    	// Cierro la sesion antes de salir
//                    	if ( session != null ){
//                    	     session.invalidate();
//                    	}
                    	
                    	WebRenderServlet.getActiveConnection().getRequest().getSession().invalidate();
                    	
                    	//ApplicationInstance.getActive().enqueueCommand(new BrowserRedirectCommand("Logout"));
                    	//System.out.println("Saliendo 3");
                    }
                }
        );
        mMenu.add(miItem_2);
        
        
        add(mMenu);
        
// Menú 1 nivel 0 -----------------------------------------------------------------
        CCMenu mMenu1 = new CCMenu("ABMs");

        CCMenu mMenu1_1 = new CCMenu("ABM Usuarios");
	    	CCMenuItem miItem1_1_1 = new CCMenuItem("Listado Usuarios");
	    	miItem1_1_1.setCommand("app.abms.usuario.UsuarioListadoView");
	    	miItem1_1_1.addActionListener(this);
	    	miItem1_1_1.setSeguridad(true);
	    	mMenu1_1.add(miItem1_1_1);
	
	    	CCMenuItem miItem1_1_2 = new CCMenuItem("Modificar clave");
	    	miItem1_1_2.setCommand("app.abms.usuario.UsuarioClaveEdit");
	    	miItem1_1_2.addActionListener(this);
	    	mMenu1_1.add(miItem1_1_2);
    	
    	mMenu1.add(mMenu1_1);     	

        CCMenu mMenu1_2 = new CCMenu("ABM Personas");
        	CCMenuItem miItem1_2_1 = new CCMenuItem("Alta Personas");
        	miItem1_2_1.setCommand("app.abms.persona.PersonaAddView");
        	miItem1_2_1.addActionListener(this);
        	mMenu1_2.add(miItem1_2_1);

        	CCMenuItem miItem1_2_2 = new CCMenuItem("Listado Personas");
        	miItem1_2_2.setCommand("app.abms.persona.PersonaFiltroView");
        	miItem1_2_2.addActionListener(this);
        	mMenu1_2.add(miItem1_2_2);
        	
        mMenu1.add(mMenu1_2);     	
        
        CCMenu mMenu1_3 = new CCMenu("ABM Propiedades");
	    	CCMenuItem miItem1_3_1 = new CCMenuItem("Alta Propiedades");
	    	miItem1_3_1.setCommand("app.abms.inmueble.InmuebleAddView");
	    	miItem1_3_1.addActionListener(this);
	    	mMenu1_3.add(miItem1_3_1);
	
	    	CCMenuItem miItem1_3_2 = new CCMenuItem("Listado Propiedades");
	    	miItem1_3_2.setCommand("app.abms.inmueble.InmuebleFiltroView");
	    	miItem1_3_2.addActionListener(this);
	    	mMenu1_3.add(miItem1_3_2);
    	
	    	mMenu1_3.add(new Separator());
	    	
	    	CCMenuItem miItem1_3_3 = new CCMenuItem("ABM Tipos de Prop.");
	    	miItem1_3_3.setCommand("app.abms.inmueble.InmuebleTipoListadoView");
	    	miItem1_3_3.addActionListener(this);
	    	mMenu1_3.add(miItem1_3_3);
	    	
    	mMenu1.add(mMenu1_3);
    	
    	
    	CCMenu mMenu1_4 = new CCMenu("ABM Utiles");
    		CCMenuItem miItem1_4_1 = new CCMenuItem("Localidades");
	    	miItem1_4_1.setCommand("app.abms.localidad.LocalidadListadoView");
	    	miItem1_4_1.addActionListener(this);
	    	mMenu1_4.add(miItem1_4_1);
	    	
	    	CCMenuItem miItem1_4_2 = new CCMenuItem("Edificios");
	    	miItem1_4_2.setCommand("app.abms.edificio.EdificioListadoView");
	    	miItem1_4_2.addActionListener(this);
	    	mMenu1_4.add(miItem1_4_2);
    		
    	mMenu1.add(mMenu1_4);
        
    	add(mMenu1);
    	
// Menú 2 nivel 0 -----------------------------------------------------------------
        CCMenu mMenu2 = new CCMenu("Contratos");
    	
        CCMenu mMenu2_1 = new CCMenu("ABM Contratos");
    		CCMenuItem miItem2_1_1 = new CCMenuItem("Alta Contratos");
    		miItem2_1_1.setCommand("app.abms.contrato.ContratoAddView");
    		miItem2_1_1.addActionListener(this);
    		mMenu2_1.add(miItem2_1_1);
        
    		CCMenuItem miItem2_1_2 = new CCMenuItem("Listado Contratos");
    		miItem2_1_2.setCommand("app.abms.contrato.ContratoFiltroView");
    		miItem2_1_2.addActionListener(this);
    		mMenu2_1.add(miItem2_1_2);
    		
    		mMenu2_1.add(new Separator());
    		
    		CCMenuItem miItem2_1_3 = new CCMenuItem("Contratos Por Vencer");
    		miItem2_1_3.setCommand("app.abms.contrato.ContratoListadoVencidosView");
    		miItem2_1_3.addActionListener(this);
    		mMenu2_1.add(miItem2_1_3);
    		
    		//mMenu2_1.add(new Separator());
    	
    		CCMenuItem miItem2_1_4 = new CCMenuItem("Rescindir Contrato");
    		miItem2_1_4.setCommand("app.abms.contrato.ContratoRescindirFiltroView");
    		miItem2_1_4.addActionListener(this);
    		mMenu2_1.add(miItem2_1_4);
    		
    	mMenu2.add(mMenu2_1);
    	
    	CCMenu mMenu2_2 = new CCMenu("ABM Impuestos");
			CCMenuItem miItem2_2_1 = new CCMenuItem("Alta Impuestos");
			miItem2_2_1.setCommand("app.abms.impuesto.ImpuestoAddView");
			miItem2_2_1.addActionListener(this);
			mMenu2_2.add(miItem2_2_1);
			
			CCMenuItem miItem2_2_2 = new CCMenuItem("Listado de Impuestos");
			miItem2_2_2.setCommand("app.abms.impuesto.ImpuestoFiltroView");
			miItem2_2_2.addActionListener(this);
			mMenu2_2.add(miItem2_2_2);
    	
		mMenu2.add(mMenu2_2);
			
        add(mMenu2);
        
        
// Menú 3 nivel 0 -----------------------------------------------------------------
        CCMenu mMenu3 = new CCMenu("Comprobantes");
    	
        CCMenu mMenu3_1 = new CCMenu("Liquidación Pago");
			CCMenuItem miItem3_1_1 = new CCMenuItem("Alta de Comprobantes");
			miItem3_1_1.setCommand("app.abms.comprobante.pago.PagoAddView");
			miItem3_1_1.addActionListener(this);
			mMenu3_1.add(miItem3_1_1);
	
			CCMenuItem miItem3_1_2 = new CCMenuItem("Listado de Comprobantes");
			miItem3_1_2.setCommand("app.abms.comprobante.pago.PagoFiltroView");
			miItem3_1_2.addActionListener(this);
			mMenu3_1.add(miItem3_1_2);
			
			mMenu3_1.add(new Separator());
			
			CCMenuItem miItem3_1_3 = new CCMenuItem("Retención GANANCIA");
			miItem3_1_3.setCommand("app.abms.comprobante.pago.GananciaFiltroView");
			miItem3_1_3.addActionListener(this);
			mMenu3_1.add(miItem3_1_3);
			
		mMenu3.add(mMenu3_1);
        
		CCMenu mMenu3_2 = new CCMenu("Liquidación Cobro");
			CCMenuItem miItem3_2_1 = new CCMenuItem("Alta de Comprobantes");
			miItem3_2_1.setCommand("app.abms.comprobante.cobro.CobroAddView");
			miItem3_2_1.addActionListener(this);
			mMenu3_2.add(miItem3_2_1);
    
			CCMenuItem miItem3_2_2 = new CCMenuItem("Listado de Comprobantes");
			miItem3_2_2.setCommand("app.abms.comprobante.cobro.CobroFiltroView");
			miItem3_2_2.addActionListener(this);
			mMenu3_2.add(miItem3_2_2);
		mMenu3.add(mMenu3_2);
		
		CCMenu mMenu3_3 = new CCMenu("Facturas - Comisiones");
			CCMenuItem miItem3_3_1 = new CCMenuItem("Alta de Facturas");
			miItem3_3_1.setCommand("app.abms.comprobante.factura.FacturaAddView");
			miItem3_3_1.addActionListener(this);
			mMenu3_3.add(miItem3_3_1);
			
			CCMenuItem miItem3_3_2 = new CCMenuItem("Listado de Facturas");
			miItem3_3_2.setCommand("app.abms.comprobante.factura.FacturaFiltroView");
			miItem3_3_2.addActionListener(this);
			mMenu3_3.add(miItem3_3_2);
		
		mMenu3.add(mMenu3_3);
    		
        add(mMenu3);

        
// Menú 4 nivel 0 -----------------------------------------------------------------
        CCMenu mMenu4 = new CCMenu("Informes");
        
        CCMenuItem miItem4_1 = new CCMenuItem("Info Persona");		
        miItem4_1.addActionListener(
        		new ActionListener() {
        			private static final long serialVersionUID = 1L;
                    public void actionPerformed(ActionEvent e) {
                    	FWContentPanePrincipal oCPPrincipal = ((FWContentPanePrincipal) getApplicationInstance().getDefaultWindow().getContent());
                    	oCPPrincipal.cerrarTodasLasVentana();
                    }
                }
        );        
		mMenu4.add(miItem4_1);        
        
		CCMenuItem miItem4_2 = new CCMenuItem("Estadística");		
			miItem4_2.setCommand("app.informes.EstadisticaFiltroView");
			miItem4_2.addActionListener(this);        
		mMenu4.add(miItem4_2);
		
        add(mMenu4);
        
    }

	public void actionPerformed(ActionEvent e) {
		FWContentPanePrincipal oCPPrincipal = ((FWContentPanePrincipal) getApplicationInstance().getDefaultWindow().getContent());
		//oCPPrincipal.inicializarPosition();
		oCPPrincipal.cerrarTodasLasVentana();
		
		// Evaluo si es un menú con seguridad ....
		boolean seguridad = ((CCMenuItem)e.getSource()).isSeguridad();
		if (seguridad) {
			
			if(!oFWUsuario.getAdministrador()) {
				
				oCPPrincipal.abrirVentana(new FWWindowPaneMensajes("Acceso NO AUTORIZADO","Solamente el usuario administrador puede visualizar esta pantalla."));
	        	return;				
			}
			
		}
		
		oCPPrincipal.abrirVentana(((CCMenuItem)e.getSource()).getCommand());
	}
	
}