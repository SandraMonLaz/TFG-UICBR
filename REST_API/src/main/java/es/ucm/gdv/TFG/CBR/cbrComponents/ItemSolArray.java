package es.ucm.gdv.TFG.CBR.cbrComponents;

import es.ucm.fdi.gaia.jcolibri.connector.TypeAdaptor;

public class ItemSolArray implements TypeAdaptor {
	
	private ItemSol[] values;
	private int size;
	
	public ItemSol[] getValues() {
		return values;
	}

	public void setValues(ItemSol[] values) {
		this.values = values;
	}
	
	/**
	 * Contructor
	 * @param size tama�o para inicializar el array de valores
	 */
	public ItemSolArray(int size) {
		this.size = size;
		values = new ItemSol[size];
	}
	
    /**
	 * Returns a string representation of the type.
	 */
	@Override
	public String toString() {
		String aux = "";
		
		//concatenamos cada caracter seguido del separador si no es el ultimo
		for(int i=0; i<size; i++) {
			aux+= this.values[i].toString();
			if(i<size-1) aux +=  "|";//Cada componente se separa con una A
		}
		
		return aux;
	}
	
	@Override
	public void fromString(String content) throws Exception {
		//Los componentes estan separados por As
		String[] splited = content.split("\\|");
		size = splited.length;//Inicializamos el size
		
		values = new ItemSol[size];
		
		//Inicializamos cada valor
		for(int i=0; i<size; i++)
			this.values[i].fromString(splited[i]);
		
	}
}
