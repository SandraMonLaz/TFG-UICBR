package es.ucm.gdv.TFG.CBR.cbrComponents;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Health;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Score;

public class CaseDescription implements CaseComponent {

	Integer id = 0;

	Health health;
	Score score;

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

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
		return "CaseDescription [id=" + id + ", health=" + health + ", score=" + score +"]";
	}
	
}
