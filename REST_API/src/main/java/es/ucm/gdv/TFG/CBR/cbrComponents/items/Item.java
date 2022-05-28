
package es.ucm.gdv.TFG.CBR.cbrComponents.items;


import es.ucm.fdi.gaia.jcolibri.connector.TypeAdaptor;
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.REST_API.Importance;
/*
 * Clase que corresponde a un elemento en interfaz.
 * Consta de una importancia y de un método adapt para la primera fase de adaptación
 * */
public class Item implements TypeAdaptor {

	Importance importance;
	
	public Importance getImportance() {
		return importance;
	}
	public void setImportance(Importance importance) {
		this.importance = importance;
	}
	@Override
	public void fromString(String content) throws Exception {
		 this.importance = Importance.valueOf(content);		
	}
	@Override
	public String toString() {
		return this.importance.toString();
	}
	
	/*
	 * Adapta la solución (solution) con el elemento(this)
	 * */
	public ItemSol adapt(ItemSol itemSol, CaseSolution solution) {
		return itemSol;
	}
	
}
