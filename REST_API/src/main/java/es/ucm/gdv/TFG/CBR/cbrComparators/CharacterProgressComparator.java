package es.ucm.gdv.TFG.CBR.cbrComparators;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.CharacterProgress;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;

public class CharacterProgressComparator implements LocalSimilarityFunction {

	@Override
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		if ((o1 == null) && (o2 == null))
			return 0.5;
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof CharacterProgress))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof CharacterProgress))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());


		CharacterProgress i1 = (CharacterProgress) o1;
		CharacterProgress i2 = (CharacterProgress) o2;
		
		Importance imp1 = i1.getImportance();
		Importance imp2 = i2.getImportance();
		
		int maxDist = Math.max(imp1.ordinal(), imp2.ordinal());
		Interval interval = new Interval(maxDist);
			
		RangeType v1 = i1.getRangeType();
		RangeType v2 = i2.getRangeType();
		
		Equal eq = new Equal();
		
		return 0.5*interval.compute(imp1.ordinal(), imp2.ordinal()) + 0.5*eq.compute(v1.ordinal(), v2.ordinal());
	}

	@Override
	public boolean isApplicable(Object o1, Object o2) {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof CharacterProgress;
		else if(o2==null)
			return o1 instanceof CharacterProgress;
		else
			return (o1 instanceof CharacterProgress)&&(o2 instanceof CharacterProgress);
	}

}
