package es.ucm.gdv.TFG.CBR.cbrComponents;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Abilities;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.CharacterInfo;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.CharacterProgress;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Collectable;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Health;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.LevelProgress;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Score;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Shields;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Time;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Weapons;


public class CaseDescription implements CaseComponent {

	Integer id = 0;

	Health health;
	Score score;
	Abilities abilities;
	CharacterInfo characterinfo;
	CharacterProgress characterProgress;
	Collectable collectable;
	LevelProgress levelProgress;
	Shields shields;
	Time time;
	Weapons weapons;
	
	public CharacterInfo getCharacterinfo() {
		return characterinfo;
	}

	public void setCharacterinfo(CharacterInfo characterinfo) {
		this.characterinfo = characterinfo;
	}

	public CharacterProgress getCharacterProgress() {
		return characterProgress;
	}

	public void setCharacterProgress(CharacterProgress characterProgress) {
		this.characterProgress = characterProgress;
	}

	public Collectable getCollectable() {
		return collectable;
	}

	public void setCollectable(Collectable collectable) {
		this.collectable = collectable;
	}

	public LevelProgress getLevelProgress() {
		return levelProgress;
	}

	public void setLevelProgress(LevelProgress levelProgress) {
		this.levelProgress = levelProgress;
	}

	public Shields getShields() {
		return shields;
	}

	public void setShields(Shields shields) {
		this.shields = shields;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Weapons getWeapons() {
		return weapons;
	}

	public void setWeapons(Weapons weapons) {
		this.weapons = weapons;
	}


	public Abilities getAbilities() {
		return abilities;
	}

	public void setAbilities(Abilities abilities) {
		this.abilities = abilities;
	}

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
		return "CaseDescription [id=" + id + ", health=" + health + ", score=" + score + "abilities=" + this.abilities + 
				"characterinfo=" + this.characterinfo + "characterProgress=" + this.characterProgress + "collectable=" + this.collectable +
				"levelProgress=" + this.levelProgress + "shields=" + this.shields + "time=" + this.time +
				"weapons=" + this.weapons +"]";
	}
	
}
