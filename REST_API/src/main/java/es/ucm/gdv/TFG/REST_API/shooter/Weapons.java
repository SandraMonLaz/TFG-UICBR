package es.ucm.gdv.TFG.REST_API.shooter;

import es.ucm.gdv.TFG.REST_API.Importance;

public class Weapons {
	
	public enum WeaponShow{
		current,
		both
	}
	
	public Weapons(Importance importance, WeaponShow weaponShow) {
		super();
		this.importance = importance;
		this.weaponShow = weaponShow;
	}
	
	public Importance getImportance() {
		return importance;
	}
	public WeaponShow getWeaponShow() {
		return weaponShow;
	}

	private final Importance importance;
	private final WeaponShow weaponShow;
}
