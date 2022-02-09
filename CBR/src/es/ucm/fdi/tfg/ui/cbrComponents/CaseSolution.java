package es.ucm.fdi.tfg.ui.cbrComponents;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

public class CaseSolution implements CaseComponent {

	private ItemSol[] solutionItems;
	
	public CaseSolution() {
		solutionItems = new ItemSol[ItemId.MAX_ITEMS.ordinal()];
	}

	@Override
	public Attribute getIdAttribute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ItemSol[] getSolutionItems() {
		return solutionItems;
	}

	public void setSolutionItems(ItemSol[] solutionItems) {
		this.solutionItems = solutionItems;
	}
}
