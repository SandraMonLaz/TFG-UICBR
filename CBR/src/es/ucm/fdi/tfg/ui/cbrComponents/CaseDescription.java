package es.ucm.fdi.tfg.ui.cbrComponents;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

public class CaseDescription implements CaseComponent {

	Integer id;

	Health health;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Health getHealth() {
		return health;
	}

	public void setHealth(Health health) {
		this.health = health;
	}

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", CaseDescription.class);
	}
	
	@Override
	public String toString() {
		return "CaseDescription [id=" + id + ", health=" + health +  "]";
	}
	
}
