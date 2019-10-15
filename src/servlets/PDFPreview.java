/*
 * PDFPreview.java
 *
 * Created on 5 de noviembre de 2007, 12:33
 */

package servlets;

import java.io.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.*;
import javax.servlet.http.*;

import app.beans.ItemTipo;
import app.beans.NovedadTipo;
import app.beans.Utiles;
import app.combos.ComboMeses;

import datos.Page;
import datos.contrato.Contrato;
import datos.contrato.ContratoComparator;
import datos.contrato.ContratoFacade;
import datos.contrato_novedad_cobro.ContratoNovedadCobro;
import datos.contrato_novedad_cobro.ContratoNovedadCobroProcesos;
import datos.factura.Factura;
import datos.factura.FacturaProcesos;
import datos.factura_item.FacturaItem;
import datos.inmueble.Inmueble;
import datos.inmueble.InmuebleFacade;
import datos.persona.Persona;
import datos.persona.PersonaComparator;
import datos.persona.PersonaFacade;
import datos.recibo_cobro.ReciboCobro;

import datos.recibo_cobro.ReciboCobroProcesos;
import datos.recibo_cobro_item.ReciboCobroItem;
import datos.recibo_pago.ReciboPago;
import datos.recibo_pago.ReciboPagoProcesos;
import datos.recibo_pago_item.ReciboPagoItem;

import net.sf.jasperreports.engine.JRDataSource;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;


/**
 * @author pgarello
 * @version
 */
@SuppressWarnings("serial")
public class PDFPreview extends HttpServlet {

	File reportFile = null;
	HashMap parameters = new HashMap();
	JRBeanCollectionDataSource oDS1 = null;
	
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    @SuppressWarnings({ "static-access", "unchecked" , "unused" })
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	
    	System.out.println("PDFPreview +++++++++++++++++++++++++++++++++++++++++++++++++");
    	
        // DataSource a usar de acuerdo al nombre del reporte
		JRDataSource jasperDataSource = null;
        
        // Archivo .jasper a usar de acuerdo al nombre del reporte
        // File reportFile = null;

        // Parámetros del reporte
        // HashMap parameters = new HashMap();
        
        // Obtengo el nombre del reporte
        String reporte = request.getParameter("reporte");
        String usuario = request.getParameter("usuario");
        
        //JRBeanCollectionDataSource oDS1 = null; // lo uso con una colección de objetos
        JRResultSetDataSource oDS2 = null; // lo uso con un RecordSet
		String reporte_nombre = "";
        
        if (reporte.equals("comprobanteReciboCobro")) {
        
        	// Busco el objeto a imprimir
        	String id_recibo_cobro = request.getParameter("id_recibo_cobro");
        	
        	ReciboCobro oRecibo = null;
        	try {
				oRecibo = ReciboCobroProcesos.buscarReciboPorID(Integer.parseInt(id_recibo_cobro));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}	
			
        	// Tengo una colección de 1 recibo
        	Collection<ReciboCobroItem> lRecibos = new Vector<ReciboCobroItem>();
        	lRecibos = oRecibo.getReciboCobroItems();
        	oDS1 = new JRBeanCollectionDataSource(lRecibos);
        	
        	parameters.put("inquilino", oRecibo.getPersona().getDescripcion());
        	//parameters.put("localidad", oRecibo.getPersona().getLocalidad().getDescripcion());
        	
        	String numero_completo = Utiles.complete(""+oRecibo.getNumero(), 8);
        	parameters.put("numero", numero_completo);
        	
        	parameters.put("fechaEmision", oRecibo.getFechaEmision());
        	parameters.put("leyenda", oRecibo.getLeyenda());
        	parameters.put("total", oRecibo.getTotal());
        	parameters.put("domicilio", oRecibo.getPersona().getDireccion() + " - " + oRecibo.getPersona().getLocalidad().getDescripcion());
        	parameters.put("cuit", oRecibo.getPersona().getCuit());
        	
        	parameters.put("responsabilidad", oRecibo.getPersona().getResponsabilidadIVA());
        	
        	parameters.put("monto_letras", "Son pesos " + Utiles.getNumberToString(oRecibo.getTotal()));
        	
        	reportFile = new File(getServletContext().getRealPath("/reportes/comprobante_cobro.jasper"));
            // Long cobradorID = new Long(request.getParameter("cobradorID"));
            //jasperDataSource = new JRDataSourceComprobanteEntregaRecibos(cobradorID);

/********************************************************************************************************************/
        	
        } else if (reporte.equals("comprobanteReciboPago")) {
            
        	// Busco el objeto a imprimir
        	String id_recibo_pago = request.getParameter("id_recibo_pago");
        	//System.out.println("PDFPreview " + id_recibo_pago);
        	ReciboPago oRecibo = null;
        	try {
				oRecibo = ReciboPagoProcesos.buscarReciboPorID(Integer.parseInt(id_recibo_pago));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}	
			
        	// Tengo una colección de 1 recibo
        	Collection<ReciboPagoItem> lRecibos = new Vector<ReciboPagoItem>();
        	Collection<ReciboPagoItem> lRecibos_impresion = new Vector<ReciboPagoItem>();
        	lRecibos = oRecibo.getReciboPagoItems();
        	
        	/**
        	 * Tengo que agrupar los datos de los Item que son comisión
        	 * para eso filtro la colección
        	 */
        	Double suma_comisiones = new Double(0), suma_iva = new Double(0);
        	for (ReciboPagoItem obj : lRecibos) {
        		
//        		System.out.println(obj.getMonto());
//        		System.out.println(obj.getDescripcion());
//        		System.out.println(obj.getContratoNovedadPago().getContrato().getInmueble().getDireccion_completa());
//        		System.out.println(obj.getItemTipo());
//        		System.out.println(obj.getContratoNovedadPago().getPeriodoCuota());
//        		System.out.println(obj.getContratoNovedadPago().getContrato().getInmueble().getLocalidad().getDescripcion());
//        		System.out.println(obj.getContratoNovedadPago().getContrato().getFin_contrato());
//        		System.out.println(obj.getReciboPago().getNumero());

        		if (obj.getIdItemTipo() == ItemTipo.ItemComisionAlquiler) {
        			suma_comisiones += obj.getMonto();
        		} else if (obj.getIdItemTipo() == ItemTipo.ItemIVA) {
        			suma_iva += obj.getMonto();
        		} else {
        			lRecibos_impresion.add(obj);
        		}        		
        		
        	}
        
        	// Agrego los items siempre que no sea igual a CERO
        	if (suma_comisiones != 0) {        		        	
        		ReciboPagoItem oItemComision = new ReciboPagoItem();
        		oItemComision.setMonto(suma_comisiones);
        		oItemComision.setDescripcion("Suma comisiones");
        		oItemComision.setIdItemTipo(ItemTipo.ItemComisionAlquiler);
        		lRecibos_impresion.add(oItemComision);
        	}
        	
        	if (suma_iva != 0) {
        		ReciboPagoItem oItemIVA = new ReciboPagoItem();
        		oItemIVA.setMonto(suma_iva);
        		oItemIVA.setDescripcion("IVA sobre comisiones");
        		oItemIVA.setIdItemTipo(ItemTipo.ItemIVA);
        		lRecibos_impresion.add(oItemIVA);
        	}
        	
        	oDS1 = new JRBeanCollectionDataSource(lRecibos_impresion);
        	
        	parameters.put("propietario", oRecibo.getPersona().getDescripcion());
        	//System.out.println(oRecibo.getPersona().getDescripcion());
        	parameters.put("localidad", oRecibo.getPersona().getLocalidad().getDescripcion());
        	
        	String numero_completo = Utiles.complete(""+oRecibo.getNumero(), 8);
        	parameters.put("numero", numero_completo);
        	//System.out.println(numero_completo);
        	
        	parameters.put("fechaEmision", oRecibo.getFechaEmision());
        	//System.out.println(oRecibo.getFechaEmision());
        	
        	parameters.put("leyenda", oRecibo.getLeyenda());
        	//System.out.println(oRecibo.getLeyenda());
        	String cuit_dni = oRecibo.getPersona().getCUIT_DNI();
        	
        	if (oRecibo.getPersona().comprobanteA()) {        	
        		parameters.put("subtotal", oRecibo.getTotal() - (- suma_iva));
        		parameters.put("iva", -suma_iva);
        	} else {
        		parameters.put("subtotal", null);
        		parameters.put("iva", null);
        	}
        	parameters.put("total", oRecibo.getTotal());
        	
        	parameters.put("domicilio", oRecibo.getPersona().getDireccion());
        	parameters.put("localidad", oRecibo.getPersona().getLocalidad().getDescripcion());
        	parameters.put("cuit", cuit_dni);
        	//System.out.println("cuit_dni:" + cuit_dni);
        	
        	parameters.put("responsabilidad", oRecibo.getPersona().getResponsabilidadIVA());
        	//System.out.println(oRecibo.getPersona().getResponsabilidadIVA());
        	
        	parameters.put("monto_letras", "Son pesos " + Utiles.getNumberToString(oRecibo.getTotal()));
        	
        	reportFile = new File(getServletContext().getRealPath("/reportes/comprobante_pago.jasper"));
            // Long cobradorID = new Long(request.getParameter("cobradorID"));
            //jasperDataSource = new JRDataSourceComprobanteEntregaRecibos(cobradorID);

/********************************************************************************************************************/        	
        	
        } else if (reporte.equals("factura")) {
            
        	System.out.println("PDFPreview reporte: FACTURA");
        	
        	// Busco el objeto a imprimir
        	String id_factura = request.getParameter("id_factura");
        	//System.out.println("PDFPreview " + id_recibo_pago);
        	Factura oFactura = null;
        	try {
        		oFactura = FacturaProcesos.buscarFacturaPorID(Integer.parseInt(id_factura));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}	
			
        	// Tengo una colección de 1 recibo
        	Collection<FacturaItem> lItems = new Vector<FacturaItem>();
        	Collection<FacturaItem> lItems_impresion = new Vector<FacturaItem>();
        	lItems = oFactura.getFacturaItems();
        	
        	/**
        	 * Tengo que agrupar los datos de los Item que son comisión
        	 * para eso filtro la colección
        	 */
        	Double suma_comisiones = new Double(0), suma_iva = new Double(0);
        	for (FacturaItem obj : lItems) {
        		
//        		System.out.println(obj.getMonto());
//        		System.out.println(obj.getDescripcion());
//        		System.out.println(obj.getContratoNovedadPago().getContrato().getInmueble().getDireccion_completa());
        		System.out.println(obj.getIdItemTipo() +  " - " + ItemTipo.getDescripcion(obj.getIdItemTipo()));
//        		System.out.println(obj.getContratoNovedadPago().getPeriodoCuota());
//        		System.out.println(obj.getContratoNovedadPago().getContrato().getInmueble().getLocalidad().getDescripcion());
//        		System.out.println(obj.getContratoNovedadPago().getContrato().getFin_contrato());
//        		System.out.println(obj.getReciboPago().getNumero());

        		if (obj.getIdItemTipo() == ItemTipo.ItemComisionAlquiler) {
        			suma_comisiones += obj.getMonto();
        		} else if (obj.getIdItemTipo() == ItemTipo.ItemIVA) {
        			suma_iva += obj.getMonto();
        		} else {
        			lItems_impresion.add(obj);
        		}        		
        		
        	}
        
        	// Agrego los items siempre que no sea igual a CERO
        	if (suma_comisiones != 0) {        		        	
        		FacturaItem oItem = new FacturaItem();
        		oItem.setMonto(suma_comisiones);
        		oItem.setDescripcion("Suma comisiones");
        		oItem.setIdItemTipo(ItemTipo.ItemComisionAlquiler);
        		lItems_impresion.add(oItem);
        	}
        	
        	if (suma_iva != 0) {
        		FacturaItem oItemIVA = new FacturaItem();
        		oItemIVA.setMonto(suma_iva);
        		oItemIVA.setDescripcion("IVA sobre comisiones");
        		oItemIVA.setIdItemTipo(ItemTipo.ItemIVA);
        		lItems_impresion.add(oItemIVA);
        	}
        	
        	oDS1 = new JRBeanCollectionDataSource(lItems_impresion);
        	
        	parameters.put("propietario", oFactura.getPersona().getDescripcion());
        	//System.out.println(oRecibo.getPersona().getDescripcion());
        	parameters.put("localidad", oFactura.getPersona().getLocalidad().getDescripcion());
        	
        	String numero_completo = Utiles.complete(""+oFactura.getNumero(), 8);
        	parameters.put("numero", numero_completo);
        	//System.out.println(numero_completo);
        	
        	parameters.put("fechaEmision", oFactura.getFechaEmision());
        	//System.out.println(oRecibo.getFechaEmision());
        	
        	parameters.put("leyenda", oFactura.getLeyenda());
        	//System.out.println(oRecibo.getLeyenda());
        	String cuit_dni = oFactura.getPersona().getCUIT_DNI();
        	
        	if (oFactura.getPersona().comprobanteA()) {        	
        		parameters.put("subtotal", oFactura.getTotal() - (- suma_iva));
        		parameters.put("iva", -suma_iva);
        	} else {
        		parameters.put("subtotal", null);
        		parameters.put("iva", null);
        	}
        	parameters.put("total", oFactura.getTotal());
        	
        	parameters.put("domicilio", oFactura.getPersona().getDireccion());
        	parameters.put("localidad", oFactura.getPersona().getLocalidad().getDescripcion());
        	parameters.put("cuit", cuit_dni);
        	//System.out.println("cuit_dni:" + cuit_dni);
        	
        	parameters.put("responsabilidad", oFactura.getPersona().getResponsabilidadIVA());
        	//System.out.println(oRecibo.getPersona().getResponsabilidadIVA());
        	
        	parameters.put("monto_letras", "Son pesos " + Utiles.getNumberToString(oFactura.getTotal()));
        	
        	reportFile = new File(getServletContext().getRealPath("/reportes/factura.jasper"));
            // Long cobradorID = new Long(request.getParameter("cobradorID"));
            //jasperDataSource = new JRDataSourceComprobanteEntregaRecibos(cobradorID);        	

/********************************************************************************************************************/        	
        	
        } else if (reporte.equals("listadoPersonas")) {
        	
        	// Tengo que buscar los datos
        	Collection<Persona> lPersonas = PersonaFacade.findAll();
        	
        	// ordeno la coleccion
        	lPersonas = Utiles.OrdenarCollection( lPersonas, new PersonaComparator(""));
        	
        	oDS1 = new JRBeanCollectionDataSource(lPersonas);
        	reportFile = new File(getServletContext().getRealPath("/reportes/listado_personas.jasper"));
        	parameters.put("usuario", usuario);
        	parameters.put("path", "http://" + request.getServerName()+":"+request.getServerPort()+"/");

/********************************************************************************************************************/        	
        	
        } else if (reporte.equals("listadoInmueble")) {
        	
        	// Tengo que buscar los datos
        	Collection<Inmueble> lInmuebles = InmuebleFacade.findAll();
        	
        	// ordeno la coleccion
        	// lInmuebles = Utiles.OrdenarCollection( lInmuebles, new PersonaComparator(""));
        	
        	oDS1 = new JRBeanCollectionDataSource(lInmuebles);
        	reportFile = new File(getServletContext().getRealPath("/reportes/listado_inmuebles.jasper"));
        	parameters.put("usuario", usuario);
        	parameters.put("path", "http://" + request.getServerName()+":"+request.getServerPort()+"/");
        
/********************************************************************************************************************/        	
        	
        } else if (reporte.equals("listadoContratos")) {
        	
        	// Tengo que buscar los datos
        	Collection<Contrato> lContratos = ContratoFacade.findAll();
        	
        	// ordeno la coleccion
        	lContratos = Utiles.OrdenarCollection( lContratos, new ContratoComparator(""));
        	
        	for (Contrato oContrato : lContratos) {
        		ContratoFacade.completarInmueble(oContrato);
        	}
        	
        	oDS1 = new JRBeanCollectionDataSource(lContratos);
        	reportFile = new File(getServletContext().getRealPath("/reportes/listado_contratos.jasper"));
        	parameters.put("usuario", usuario);
        	parameters.put("path", "http://" + request.getServerName()+":"+request.getServerPort()+"/");

/********************************************************************************************************************/        	
        	
        } else if (reporte.equals("listadoCobros")) {	
        
        	this.listadoCobros(usuario, request);
        	
        	// SI QUIERO QUE SALGA EN EXCEL
        	this.imprimirODS(request, response, "listado_recibosCobro");
        	return;

/********************************************************************************************************************/        	
        	
        } else if (reporte.equals("listadoPagos")) {	
            
        	this.listadoPagos(usuario, request);
        	
        	// SI QUIERO QUE SALGA EN EXCEL
        	this.imprimirODS(request, response, "listado_recibosPago");
        	return;


/********************************************************************************************************************/        	
        	
        } else if (reporte.equals("listadoGanancia")) {	
            
        	this.listadoGanancia(usuario, request);
        	
        	// SI QUIERO QUE SALGA EN EXCEL
        	this.imprimirODS(request, response, "listado_recibosGanancia");
        	return;
        	
        	
/********************************************************************************************************************/        	
        	
        } else if (reporte.equals("deuda")) {	
            
        	this.listadoDeudas(usuario, request);
        	
        	// SI QUIERO QUE SALGA EN EXCEL
        	this.imprimirODS(request, response, "listado_deudas");
        	return;
        	
        } else {
        	
        	// salgo
        	throw new IOException("Reporte no generado");
        	
        }
        
                                    

            try {
            	            	
                //String jr = JasperCompileManager.compileReportToFile(request.getRealPath("/reportes/comprobante_prueba.jrxml"));                
                //byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),parameters, new JREmptyDataSource());
            	
            	Locale oLocale = new Locale("es", "AR");
            	parameters.put(JRParameter.REPORT_LOCALE, oLocale);
            	
                byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),parameters, oDS1);
				
                ServletOutputStream ouputStream = response.getOutputStream();
                response.setContentType("application/pdf");
				response.setContentLength(bytes.length);
				String name = reportFile.getName().substring(reportFile.getName().lastIndexOf("/") + 1,reportFile.getName().length() - 6) + "pdf";
				
				/** 1ro. descarga el archivo pdf y después lo habre con el programa acrobat, aparte del navegador */
				//response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");				
				//ouputStream.write(bytes,0,bytes.length);
				//ouputStream.flush();
				
				/** Habre el archivo pdf dentro de una ventana del navegador - plugin */
				response.setHeader("Content-disposition","inline; filename=\"" + name + "\"");
				ouputStream.write(bytes);
				
				ouputStream.close();
	
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }            
        
    }

    
    @SuppressWarnings("unchecked")
	private void listadoCobros(String usuario, HttpServletRequest request) {
    	
    	// Levanto los filtros
    	String filtro_fecha_desde = request.getParameter("filtro_desde");
    	Date dFiltro_fecha_desde = null;
    	if (!filtro_fecha_desde.equals("0")) dFiltro_fecha_desde = new Date(Long.parseLong(filtro_fecha_desde));
    	
    	String filtro_fecha_hasta = request.getParameter("filtro_hasta");
    	Date dFiltro_fecha_hasta = null;
    	if (!filtro_fecha_hasta.equals("0")) dFiltro_fecha_hasta = new Date(Long.parseLong(filtro_fecha_hasta));
    	
    	int filtro_nro_recibo = Integer.parseInt(request.getParameter("filtro_nro"));
    	 
    	int page_number = 0;
    	int page_size = 500;
    	int filtro_id_propiedad = 0;
    	int filtro_id_inquilino = 0;
    	
    	// Tengo que buscar los datos
    	Page pRecibos = ReciboCobroProcesos.findByFilter(	page_number, 
															page_size, 
															filtro_id_propiedad, 
															filtro_id_inquilino, 
															filtro_nro_recibo, 
															dFiltro_fecha_desde, 
															dFiltro_fecha_hasta);
    	
    	System.out.println("PDFPreview Listado Recibos de cobro " + pRecibos.getList().size());    	
    	//ArrayList<ReciboCobro> recibos = (ArrayList<ReciboCobro>) pRecibos.getList();
    	ArrayList<ReciboCobro> recibos = new ArrayList<ReciboCobro> ( pRecibos.getList());
    	try {
			ReciboCobroProcesos.completar(recibos);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	for (ReciboCobro obj : recibos) {
    		obj.getPersona().getDescripcion();
    	}
    	
    	
    	oDS1 = new JRBeanCollectionDataSource(recibos);
    	reportFile = new File(getServletContext().getRealPath("/reportes/listado_recibosCobro.jasper"));
    	parameters.put("usuario", usuario);
    	parameters.put("path", "http://" + request.getServerName()+":"+request.getServerPort()+"/");
    	parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
    	return;
    }
    
    
    @SuppressWarnings("unchecked")
	private void listadoPagos(String usuario, HttpServletRequest request) {
    	
    	// Levanto los filtros
    	String filtro_fecha_desde = request.getParameter("filtro_desde");
    	Date dFiltro_fecha_desde = null;
    	if (!filtro_fecha_desde.equals("0")) dFiltro_fecha_desde = new Date(Long.parseLong(filtro_fecha_desde));
    	
    	String filtro_fecha_hasta = request.getParameter("filtro_hasta");
    	Date dFiltro_fecha_hasta = null;
    	if (!filtro_fecha_hasta.equals("0")) dFiltro_fecha_hasta = new Date(Long.parseLong(filtro_fecha_hasta));
    	
    	int filtro_nro_recibo = Integer.parseInt(request.getParameter("filtro_nro"));
    	 
    	int page_number = 0;
    	int page_size = 500;
    	int filtro_id_propiedad = 0;
    	int filtro_id_inquilino = 0;
    	
    	// Tengo que buscar los datos
    	Page pRecibos = ReciboPagoProcesos.findByFilter(	page_number, 
															page_size, 
															filtro_id_propiedad, 
															filtro_id_inquilino, 
															filtro_nro_recibo, 
															dFiltro_fecha_desde, 
															dFiltro_fecha_hasta);
    	
    	System.out.println("Recibos de pago " + pRecibos.getList().size());    	
    	ArrayList<ReciboPago> recibos = (ArrayList<ReciboPago>) pRecibos.getList();
    	try {
			ReciboPagoProcesos.completar(recibos);
		} catch (Exception e) {
			e.printStackTrace();
		}
//    	for (ReciboCobro obj : recibos) {
//    		System.out.println(obj.getPersona().getDescripcion());
//    	}
    	
    	
    	oDS1 = new JRBeanCollectionDataSource(recibos);
    	reportFile = new File(getServletContext().getRealPath("/reportes/listado_recibosPago.jasper"));
    	parameters.put("usuario", usuario);
    	parameters.put("path", "http://" + request.getServerName()+":"+request.getServerPort()+"/");
    	parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
    	return;
    }
    
    
    @SuppressWarnings("unchecked")
	private void listadoGanancia(String usuario, HttpServletRequest request) {
    	
    	// Levanto los filtros
    	String filtro_fecha_desde = request.getParameter("filtro_desde");
    	Date dFiltro_fecha_desde = null;
    	if (!filtro_fecha_desde.equals("0")) dFiltro_fecha_desde = new Date(Long.parseLong(filtro_fecha_desde));
    	
    	String filtro_fecha_hasta = request.getParameter("filtro_hasta");
    	Date dFiltro_fecha_hasta = null;
    	if (!filtro_fecha_hasta.equals("0")) dFiltro_fecha_hasta = new Date(Long.parseLong(filtro_fecha_hasta));
    	
    	int filtro_nro_recibo = Integer.parseInt(request.getParameter("filtro_nro"));
    	 
    	int page_number = 0;
    	int page_size = 500;
    	int filtro_id_ganancia = 0;
    	int filtro_id_propietario = 0;
    	
    	// Tengo que buscar los datos
    	Page pRecibos = ReciboPagoProcesos.findGananciaByFilter(page_number, 
    															page_size, 
    															filtro_id_ganancia, 
    															filtro_id_propietario, 
    															filtro_nro_recibo, 
    															dFiltro_fecha_desde, 
    															dFiltro_fecha_hasta);
    	
    	System.out.println("Liquidación de Ganancias " + pRecibos.getList().size());    	
    	ArrayList<ReciboPagoItem> recibos = (ArrayList<ReciboPagoItem>) pRecibos.getList();
    	try {
			ReciboPagoProcesos.completarItems(recibos);
		} catch (Exception e) {
			e.printStackTrace();
		}
//    	for (ReciboCobro obj : recibos) {
//    		System.out.println(obj.getPersona().getDescripcion());
//    	}
    	
    	
    	oDS1 = new JRBeanCollectionDataSource(recibos);
    	reportFile = new File(getServletContext().getRealPath("/reportes/listado_recibosGanancia.jasper"));
    	parameters.put("usuario", usuario);
    	parameters.put("path", "http://" + request.getServerName()+":"+request.getServerPort()+"/");
    	return;
    }
    
    
    
    private ComboMeses oComboMeses;
    @SuppressWarnings("unchecked")
	private void listadoDeudas(String usuario, HttpServletRequest request) {
    	
    	// Levanto los filtros
    	String filtro_mes = request.getParameter("mes");    	
    	String filtro_anio = request.getParameter("anio");
    	 
    	// Tengo que buscar los datos
    	List<ContratoNovedadCobro> lNovedades = ContratoNovedadCobroProcesos.buscarNovedadesCobroDeuda(Short.parseShort(filtro_mes), Short.parseShort(filtro_anio)); 
    		    		    	
    	//System.out.println("Deudas " + lNovedades.size());
    	
    	for(ContratoNovedadCobro obj:lNovedades) {
    		obj.getContrato().getInmueble().getDireccion_completa();
    		// Si es un movimiento de alquiler le complemento el saldo PAGADO
    		if (obj.getIdNovedadTipo() == NovedadTipo.Alquiler)
    			ContratoNovedadCobroProcesos.completarConPagado(obj);
    	}
    	
    	oComboMeses = new ComboMeses(100,22,11,true,(short)2);
    	
    	oDS1 = new JRBeanCollectionDataSource(lNovedades);
    	reportFile = new File(getServletContext().getRealPath("/reportes/listado_deudas.jasper"));
    	parameters.put("usuario", usuario);
    	parameters.put("periodo", "Filtro: " + oComboMeses.getText(Short.parseShort(filtro_mes)) + "/" + filtro_anio);
    	parameters.put("path", "http://" + request.getServerName()+":"+request.getServerPort()+"/");
    	parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
    	return;
    }
    
    
    
    private void imprimirODS(HttpServletRequest request, HttpServletResponse response, String reporte_jrxml) {
    	
    	/*********************************************************/
    	/** PERSONALIZO EL REPORTE PARA QUE SALGA EN FORMATO ODS */
    	/*********************************************************/

    	HttpSession session = request.getSession();
    	
    	try {
    	
        	//busco los archivos para saber si el jrxml se modificó y lo tenco que volver a compilar
			String pathJrxml = session.getServletContext().getRealPath("/reportes/"+reporte_jrxml+".jrxml");
			String pathJasper = session.getServletContext().getRealPath("/reportes/"+reporte_jrxml+".jasper");
			File fileJRXML = new File(pathJrxml);
			File fileJasper = new File(pathJasper);
			//veo si el jrxml se modificó para volver a compilar el archivo
			if(fileJRXML.lastModified() > fileJasper.lastModified()){				
				JasperCompileManager.compileReportToFile(pathJrxml, pathJasper);
			}
        	        	
        	InputStream reportStream = session.getServletContext().getResourceAsStream("/reportes/"+reporte_jrxml+".jasper");
        	response.setContentType("application/xls");
   			response.setHeader("Content-Disposition", "attachment; filename=\""+reporte_jrxml+".xls\"");
        	
   			Locale oLocale = new Locale("es", "AR");
        	parameters.put(JRParameter.REPORT_LOCALE, oLocale);
   			
   			JasperPrint jasperPrint = JasperFillManager.fillReport(	reportStream, parameters , oDS1);

			JRXlsExporter exporter = new JRXlsExporter();

			ServletOutputStream servletOutputStream = response.getOutputStream();
			
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reporte_jrxml+".xls");
			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporter.exportReport();
							
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }

}