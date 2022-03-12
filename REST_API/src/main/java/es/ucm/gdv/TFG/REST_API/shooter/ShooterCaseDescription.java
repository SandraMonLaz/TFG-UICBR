package es.ucm.gdv.TFG.REST_API.shooter;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseDescription;

public class ShooterCaseDescription extends CaseDescription {
	private Ammo _ammo;
	private Compass _compass;
	private DeathsFeedback _deathFeedback;
	private Equipment _equipment;
	private GameTime _gameTime;
	private Habilities _habilities;
	private Health _health;
	private Minimap _minimap;
	private Objective _objective;
	private PointsReward _pointsReward;
	private Potions _potions;
	private Shield _shields;
	private Weapons _weapons;
	
	public ShooterCaseDescription() {
		super();
	}
	
	public Ammo get_ammo() {
		return _ammo;
	}
	
	public void set_ammo(Ammo _ammo) {
		this._ammo = _ammo;
	}
	
	public Compass get_compass() {
		return _compass;
	}
	
	public void set_compass(Compass _compass) {
		this._compass = _compass;
	}
	
	public DeathsFeedback get_deathFeedback() {
		return _deathFeedback;
	}
	
	public void set_deathFeedback(DeathsFeedback _deathFeedback) {
		this._deathFeedback = _deathFeedback;
	}
	
	public Equipment get_equipment() {
		return _equipment;
	}
	
	public void set_equipment(Equipment _equipment) {
		this._equipment = _equipment;
	}
	
	public GameTime get_gameTime() {
		return _gameTime;
	}
	
	public void set_gameTime(GameTime _gameTime) {
		this._gameTime = _gameTime;
	}
	
	public Habilities get_habilities() {
		return _habilities;
	}
	
	public void set_habilities(Habilities _habilities) {
		this._habilities = _habilities;
	}
	
	public Health get_health() {
		return _health;
	}
	
	public void set_health(Health _health) {
		this._health = _health;
	}
	
	public Minimap get_minimap() {
		return _minimap;
	}
	
	public void set_minimap(Minimap _minimap) {
		this._minimap = _minimap;
	}
	
	public Objective get_objective() {
		return _objective;
	}
	
	public void set_objective(Objective _objective) {
		this._objective = _objective;
	}
	
	public PointsReward get_pointsReward() {
		return _pointsReward;
	}
	
	public void set_pointsReward(PointsReward _pointsReward) {
		this._pointsReward = _pointsReward;
	}
	
	public Potions get_potions() {
		return _potions;
	}
	
	public void set_potions(Potions _potions) {
		this._potions = _potions;
	}
	
	public Shield get_shields() {
		return _shields;
	}
	
	public void set_shields(Shield _shields) {
		this._shields = _shields;
	}
	
	public Weapons get_weapons() {
		return _weapons;
	}
	
	public void set_weapons(Weapons _weapons) {
		this._weapons = _weapons;
	}
}
