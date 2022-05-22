package es.ucm.gdv.TFG.CBR.cbrComparators;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Weapons;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.UseType;

public class WeaponsComparator implements LocalSimilarityFunction {

	@Override
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		if ((o1 == null) && (o2 == null))
			return 0.5;
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof Weapons))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof Weapons))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());


		Weapons i1 = (Weapons) o1;
		Weapons i2 = (Weapons) o2;
		
		Importance imp1 = i1.getImportance();
		Importance imp2 = i2.getImportance();
		int maxDist = Importance.values().length;
		Interval interval = new Interval(maxDist);
			
		UseType v1 = i1.getUseType();
		UseType v2 = i2.getUseType();
		Equal eq = new Equal();
		
		int n1 = i1.getnWeapons();
		int n2 = i2.getnWeapons();
		NumComparator numComp = new NumComparator(Math.max(n1, n2));
		
		return 0.33*interval.compute(imp1.ordinal(), imp2.ordinal()) + 0.33*eq.compute(v1.ordinal(), v2.ordinal()) + 0.34*numComp.compute(n1, n2);
	}

	@Override
	public boolean isApplicable(Object o1, Object o2) {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof Weapons;
		else if(o2==null)
			return o1 instanceof Weapons;
		else
			return (o1 instanceof Weapons)&&(o2 instanceof Weapons);
	}

}
