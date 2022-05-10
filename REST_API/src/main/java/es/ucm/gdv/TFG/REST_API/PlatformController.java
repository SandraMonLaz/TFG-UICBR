package es.ucm.gdv.TFG.REST_API;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseDescription;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Abilities;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.CharacterInfo;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.CharacterProgress;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Collectable;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Health;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Item;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.LevelProgress;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Score;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Shields;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Time;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Weapons;
import es.ucm.gdv.TFG.CBR.cbrEngine.CBREngine;
import es.ucm.gdv.TFG.CBR.cbrEngine.SolCBR;
import es.ucm.gdv.TFG.REST_API.platform2D.PlatformQuery;

@RestController
public class PlatformController {
	private static int id = 0;
	
	@PostMapping("/platform")
	public SolCBR query(@RequestBody PlatformQuery i) {
		CaseDescription caseDes = new CaseDescription();
		caseDes.setId(id);
		
		Health health  = null;
		if(i.getHealth() != null){			
			health = new Health();
			health.setImportance(i.getHealth().getImportance());
			health.setType(i.getHealth().getRangeType());
		}
		caseDes.setHealth(health);
		 
		
		Score score = null;
		if(i.getScore() != null){			
			score = new Score();
			score.setImportance(i.getScore().getImportance());
		}
		caseDes.setScore(score);
		
		Shields shield = null;
		if(i.getShields() != null){
			shield = new Shields();
			shield.setImportance(i.getShields().getImportance());
			shield.setType(i.getShields().getRangeType());
		}
		caseDes.setShields(shield);
		
		Time time = new Time();
		time.setImportance(i.getTime().getImportance());
		time.setTimeUse(i.getTime().getUse());
		caseDes.setTime(time);
		
		
		Weapons weapons = null;
		if(i.getWeapons() != null){
			weapons = new Weapons();
			weapons.setImportance(i.getWeapons().getImportance());
			weapons.setnWeapons(i.getWeapons().getnWeapons());
			weapons.setUseType(i.getWeapons().getUse());
		}
		caseDes.setWeapons(weapons);
		
		LevelProgress lvlProgress = null;
		if(i.getLevelProgress() != null){
			lvlProgress = new LevelProgress();
			lvlProgress.setImportance(i.getLevelProgress().getImportance());
			lvlProgress.setProgressType(i.getLevelProgress().getProgressType());
			lvlProgress.setRangeType(i.getLevelProgress().getRangeType());
		}
		caseDes.setLevelProgress(lvlProgress);
		
		Collectable collectable = null;
		if(i.getCollectable() != null){
			collectable = new Collectable();
			collectable.setImportance(i.getCollectable().getImportance());
		}
		caseDes.setCollectable(collectable);
		
		CharacterProgress characterProgress = null;
		if(i.getCharacterProgress() != null){
			characterProgress = new CharacterProgress();	
			characterProgress.setImportance(i.getCharacterProgress().getImportance());
			characterProgress.setRangeType(i.getCharacterProgress().getRangeType());
		}
		caseDes.setCharacterProgress(characterProgress);
		
		CharacterInfo characterInfo = null;
		if(i.getCharacterinfo() != null){
			characterInfo = new CharacterInfo();
			characterInfo.setImportance(i.getCharacterinfo().getImportance());
		}
		caseDes.setCharacterinfo(characterInfo);
		
		Abilities abilities = null;
		if(i.getAbilities() != null){
			abilities = new Abilities();
			abilities.setImportance(i.getAbilities().getImportance());
			abilities.setnWeapons(i.getAbilities().getnWeapons());
			abilities.setUseType(i.getAbilities().getUse());
		}
		caseDes.setAbilities(abilities);
		
		CBRQuery query = new CBRQuery();
		query.setDescription(caseDes);
		try {
			CBREngine.getInstance().cycle(query);
			CBREngine.getInstance().postCycle();			 
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		id++;
		return CBREngine.getInstance().getSolution();
	}
}