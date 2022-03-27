package es.ucm.gdv.TFG.CBR.cbrComponents.items;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;
import es.ucm.gdv.TFG.REST_API.platform2D.Time.TimeUse;

public class Time extends Item {
	private TimeUse timeUse;
	
	public TimeUse getTimeUse() {
		return timeUse;
	}

	public void setTimeUse(TimeUse timeUse) {
		this.timeUse = timeUse;
	}

	@Override
	public void fromString(String content) throws Exception {
		String[] splited = content.split("\\|");
		super.fromString(splited[0]);	
		this.timeUse = TimeUse.valueOf(splited[1]);
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		return s + "|" + this.timeUse.toString();
	}
	
	@Override
	public ItemSol adapt(ItemSol solutionTime, CaseSolution solution) {
		//Si la solucion que nos ofrece no tiene CHARACTER_PROGRESS lo creamos
		if(solutionTime == null) {
			solutionTime = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "tiempoChrono", ItemId.TIME);
		}
		
		solutionTime.setScreenPosition(ScreenPos.TOP_CENTER);
		
		if(this.timeUse == TimeUse.countdown) solutionTime.setImage("tiempoCountdown");
		else solutionTime.setImage("tiempoChrono");
		
		if(this.importance.ordinal() >= Importance.high.ordinal()) {
			solutionTime.setItemScale(Scale.MEDIUM);
		}
		else {
			solutionTime.setItemScale(Scale.SMALL);
		}
		
		solution.setSolItem(solutionTime, ItemId.TIME);
		return solutionTime;
	}
}
