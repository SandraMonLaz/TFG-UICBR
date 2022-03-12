package es.ucm.gdv.TFG.CBR.cbrComponents;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

public class CaseDescription implements CaseComponent {

	Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", CaseDescription.class);
	}
	
	@Override
	public String toString() {
		return "CaseDescription [id=" + id + "]";
	}
	
}
