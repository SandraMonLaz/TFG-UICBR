package es.ucm.gdv.TFG.CBR.cbrEngine;

import java.util.ArrayList;

import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;

/*
 * Clase que implementa un objeto combinado. Sirve para realizar la definición de la interfaz y la solución
 * de la herramienta
 * */
public class CombinedItem {

	public CombinedItem(ArrayList<ItemSol> items, ScreenPos screenPosition) {
		super();
		this.items = items;
		this.screenPosition = screenPosition;
	}
	public CombinedItem() {
		items = new ArrayList<ItemSol>();
	}
	ArrayList<ItemSol> items;
	private ScreenPos screenPosition;
	
	public ArrayList<ItemSol> getItems() {
		return items;
	}
	public void setItems(ArrayList<ItemSol> items) {
		this.items = items;
	}
	public ScreenPos getScreenPosition() {
		return screenPosition;
	}
	public void setScreenPosition(ScreenPos screenPosition) {
		this.screenPosition = screenPosition;
	}

	
	public void Add(ItemSol sol){
		items.add(sol);
	}
	
}
