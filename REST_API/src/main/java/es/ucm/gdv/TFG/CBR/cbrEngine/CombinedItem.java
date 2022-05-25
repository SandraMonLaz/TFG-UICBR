package es.ucm.gdv.TFG.CBR.cbrEngine;

import java.util.ArrayList;

import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;

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
	
	public void Add(ItemSol sol){
		if(sol.getItemScale().ordinal() > this.itemScale.ordinal()){
			this.itemScale = sol.getItemScale();
		}
		items.add(sol);
	}
	
}
