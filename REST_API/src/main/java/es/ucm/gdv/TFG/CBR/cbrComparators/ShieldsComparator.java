package es.ucm.gdv.TFG.CBR.cbrComparators;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Shields;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;

public class ShieldsComparator implements LocalSimilarityFunction {

	@Override
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		if ((o1 == null) && (o2 == null))
			return 0.5;
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof Shields))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof Shields))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());


		Shields i1 = (Shields) o1;
		Shields i2 = (Shields) o2;
		
		Importance imp1 = i1.getImportance();
		Importance imp2 = i2.getImportance();
		
		int maxDist = Importance.values().length;
		Interval interval = new Interval(maxDist);
			
		RangeType v1 = i1.getType();
		RangeType v2 = i2.getType();
		
		Equal eq = new Equal();
		
		return 0.5*interval.compute(imp1.ordinal(), imp2.ordinal()) + 0.5*eq.compute(v1.ordinal(), v2.ordinal());
	}

	@Override
	public boolean isApplicable(Object o1, Object o2) {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof Shields;
		else if(o2==null)
			return o1 instanceof Shields;
		else
			return (o1 instanceof Shields)&&(o2 instanceof Shields);
	}

}
