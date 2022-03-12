package es.ucm.gdv.TFG.REST_API.platform2D;

public class PlatformQuery {
	
	public PlatformQuery(CharacterInfo characterinfo, CharacterProgress characterProgress, Collectable collectable,
			Habilities habilities, Health health, LevelProgress levelProgress, Score score, Shields shields, Time time,
			Weapons weapons) {
		super();
		this.characterinfo = characterinfo;
		this.characterProgress = characterProgress;
		this.collectable = collectable;
		this.habilities = habilities;
		this.health = health;
		this.levelProgress = levelProgress;
		this.score = score;
		this.shields = shields;
		this.time = time;
		this.weapons = weapons;
	}
	
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
	public Habilities getHabilities() {
		return habilities;
	}
	public void setHabilities(Habilities habilities) {
		this.habilities = habilities;
	}
	public Health getHealth() {
		return health;
	}
	public void setHealth(Health health) {
		this.health = health;
	}
	public LevelProgress getLevelProgress() {
		return levelProgress;
	}
	public void setLevelProgress(LevelProgress levelProgress) {
		this.levelProgress = levelProgress;
	}
	public Score getScore() {
		return score;
	}
	public void setScore(Score score) {
		this.score = score;
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


	CharacterInfo characterinfo;
	CharacterProgress characterProgress;
	Collectable collectable;
	Habilities habilities;
	Health health;
	LevelProgress levelProgress;
	Score score;
	Shields shields;
	Time time;
	Weapons weapons;
	
}
