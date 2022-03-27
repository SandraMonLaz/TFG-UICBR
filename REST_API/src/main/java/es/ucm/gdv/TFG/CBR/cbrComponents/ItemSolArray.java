package es.ucm.gdv.TFG.CBR.cbrComponents;

import es.ucm.fdi.gaia.jcolibri.connector.TypeAdaptor;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.ItemId;

public class ItemSolArray implements TypeAdaptor {
	
	private ItemSol[] values;
	private int size;
	
	/**
	 * Contructor
	 * @param size tama�o para inicializar el array de valores
	 */
	public ItemSolArray() {
		this.size = ItemId.MAX_ITEMS.ordinal();
		values = new ItemSol[this.size];
	}
	
	/**
	 * Getter de los items de la solucion
	 * @return items de la solucion
	 */
	public ItemSol[] getValues() {
		return values;
	}

	/**
	 * Setter de los valores
	 * @param values valores
	 */
	public void setValues(ItemSol[] values) {
		this.values = values;
	}
	
    /**
	 * Returns a string representation of the type.
	 */
	@Override
	public String toString() {
		String aux = "";
		
		//concatenamos cada caracter seguido del separador si no es el ultimo
		for(int i=0; i<size; i++) {
			//Si el componente no se encuentra en la solucion se indica con "null"
			if(this.values[i] == null) aux += "null";
			else {
				aux += this.values[i].toString();
				if(i<size-1) aux +=  "|";	//Cada componente se separa con una A				
			}
		}
		
		return aux;
	}
	
	@Override
	public void fromString(String content) throws Exception {
		//Los componentes estan separados por As
		String[] splited = content.split("\\|");
		size = ItemId.MAX_ITEMS.ordinal();//Inicializamos el size
		
		values = new ItemSol[size];
		
		//Inicializamos cada valor
		for(int i=0; i<size; i++) {
			if(splited[i] != "null") {
				this.values[i] = new ItemSol();
				this.values[i].fromString(splited[i]);				
			}
		}
		
	}
}
