package es.ucm.gdv.TFG.REST_API.platform2D;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseDescription;

public class PlatformCaseDescription extends CaseDescription {

	private CharacterInfo _characterInfo;
	private CharacterProgress _characterProgress;
	private Collectable _collectable;
	private Habilities _habilities;
	private Health _health;
	private LevelProgress _levelProgress;
	private Score _score;
	private Shields _shields;
	private Time _time;
	private Weapons _weapons;
	
	public PlatformCaseDescription() {
		super();
	}
	
	public CharacterInfo get_characterInfo() {
		return _characterInfo;
	}
	
	public void set_characterInfo(CharacterInfo _characterInfo) {
		this._characterInfo = _characterInfo;
	}
	
	public CharacterProgress get_characterProgress() {
		return _characterProgress;
	}
	
	public void set_characterProgress(CharacterProgress _characterProgress) {
		this._characterProgress = _characterProgress;
	}
	
	public Collectable get_collectable() {
		return _collectable;
	}
	
	public void set_collectable(Collectable _collectable) {
		this._collectable = _collectable;
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

	public LevelProgress get_levelProgress() {
		return _levelProgress;
	}

	public void set_levelProgress(LevelProgress _levelProgress) {
		this._levelProgress = _levelProgress;
	}

	public Score get_score() {
		return _score;
	}

	public void set_score(Score _score) {
		this._score = _score;
	}

	public Shields get_shields() {
		return _shields;
	}

	public void set_shields(Shields _shields) {
		this._shields = _shields;
	}

	public Time get_time() {
		return _time;
	}

	public void set_time(Time _time) {
		this._time = _time;
	}

	public Weapons get_weapons() {
		return _weapons;
	}

	public void set_weapons(Weapons _weapons) {
		this._weapons = _weapons;
	}
}
