package es.ucm.gdv.TFG.REST_API.shooter;

public class ShooterQuery {
	
	public ShooterQuery(Ammo ammo, Compass compass, DeathsFeedback deathsFeedback, Equipment equipment,
			GameTime gameTime, Habilities habilities, Health health, Minimap minimap, Objective objective,
			PointsReward pointsReward, Potions potions, Shield shield, Weapons weapons) {
		super();
		this.ammo = ammo;
		this.compass = compass;
		this.deathsFeedback = deathsFeedback;
		this.equipment = equipment;
		this.gameTime = gameTime;
		this.habilities = habilities;
		this.health = health;
		this.minimap = minimap;
		this.objective = objective;
		this.pointsReward = pointsReward;
		this.potions = potions;
		this.shield = shield;
		this.weapons = weapons;
	}
	
	public Ammo getAmmo() {
		return ammo;
	}
	public Compass getCompass() {
		return compass;
	}
	public DeathsFeedback getDeathsFeedback() {
		return deathsFeedback;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public GameTime getGameTime() {
		return gameTime;
	}
	public Habilities getHabilities() {
		return habilities;
	}
	public Health getHealth() {
		return health;
	}
	public Minimap getMinimap() {
		return minimap;
	}
	public Objective getObjective() {
		return objective;
	}
	public PointsReward getPointsReward() {
		return pointsReward;
	}
	public Potions getPotions() {
		return potions;
	}
	public Shield getShield() {
		return shield;
	}
	public Weapons getWeapons() {
		return weapons;
	}

	private final Ammo ammo;
	private final Compass compass;
	private final DeathsFeedback deathsFeedback;
	private final Equipment equipment;
	private final GameTime gameTime;
	private final Habilities habilities;
	private final Health health;
	private final Minimap minimap;
	private final Objective objective;
	private final PointsReward pointsReward;
	private final Potions potions;
	private final Shield shield;
	private final Weapons weapons;

}
