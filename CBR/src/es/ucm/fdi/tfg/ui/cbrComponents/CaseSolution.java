package es.ucm.fdi.tfg.ui.cbrComponents;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

public class CaseSolution implements CaseComponent {

	private ItemSolArray solutionItems;
	Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CaseSolution() {
		solutionItems = new ItemSolArray(ItemId.MAX_ITEMS.ordinal());
	}

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", CaseSolution.class);
	}
	
	public ItemSolArray getSolutionItems() {
		return solutionItems;
	}

	public void setSolutionItems(ItemSolArray solutionItems) {
		this.solutionItems = solutionItems;
	}
	
	public void setSolItem(ItemSol solutionItem, ItemId id) {
		this.solutionItems.getValues()[id.ordinal()] = solutionItem;
	}
	
	@Override
	public String toString() {
		return "CaseSolution [id=" + id + ", solutionItems=" + solutionItems.toString() +  "]";
	}
}
