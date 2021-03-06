package es.ucm.gdv.TFG.CBR.cbrComponents;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.ItemId;

public class CaseSolution implements CaseComponent {

	Integer id = 1;
	ItemSolArray solutionItems;

	public CaseSolution() {
		solutionItems = new ItemSolArray();
	}
	
	public CaseSolution(CaseSolution other) {
		this.id = other.id;
		solutionItems = new ItemSolArray(other.solutionItems);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
