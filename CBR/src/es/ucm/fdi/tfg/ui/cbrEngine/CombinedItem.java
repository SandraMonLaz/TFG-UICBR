package es.ucm.fdi.tfg.ui.cbrEngine;

import java.util.ArrayList;

import es.ucm.fdi.tfg.ui.cbrComponents.ItemSol;
import es.ucm.fdi.tfg.ui.cbrComponents.ItemSol.Scale;
import es.ucm.fdi.tfg.ui.cbrComponents.ItemSol.ScreenPos;

public class CombinedItem {

	public CombinedItem(ArrayList<ItemSol> items, ScreenPos screenPosition, Scale itemScale) {
		super();
		this.items = items;
		this.screenPosition = screenPosition;
		this.itemScale = itemScale;
	}
	public CombinedItem() {
		items = new ArrayList<ItemSol>();
	}
	ArrayList<ItemSol> items;
	private ScreenPos screenPosition;
	private Scale itemScale;
	
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
	public Scale getItemScale() {
		return itemScale;
	}
	public void setItemScale(Scale itemScale) {
		this.itemScale = itemScale;
	}
	
}
