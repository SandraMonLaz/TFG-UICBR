package es.ucm.gdv.TFG.CBR.cbrComparators;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.CharacterInfo;
import es.ucm.gdv.TFG.REST_API.Importance;

public class CharacterInfoComparator implements LocalSimilarityFunction {

	@Override
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof CharacterInfo))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof CharacterInfo))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());
		
		CharacterInfo c1 = (CharacterInfo) o1;
		CharacterInfo c2 = (CharacterInfo) o2;

		Importance i1 = c1.getImportance();
		Importance i2 = c2.getImportance();
		Interval interval = new Interval(Importance.values().length);
		
		return interval.compute(i1, i2);
	}

	@Override
	public boolean isApplicable(Object o1, Object o2) {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof CharacterInfo;
		else if(o2==null)
			return o1 instanceof CharacterInfo;
		else
			return (o1 instanceof CharacterInfo)&&(o2 instanceof CharacterInfo);
	}

}
