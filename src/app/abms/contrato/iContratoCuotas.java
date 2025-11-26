package app.abms.contrato;

import java.util.List;

import app.beans.Cuota;
import datos.contrato.Contrato;

public interface iContratoCuotas {

	public void procesarCuotas(List<Cuota> lCuotas);
	
	public Contrato getContrato();
	
}
