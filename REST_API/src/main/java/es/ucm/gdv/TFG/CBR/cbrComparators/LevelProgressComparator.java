package es.ucm.gdv.TFG.CBR.cbrComparators;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.LevelProgress;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;
import es.ucm.gdv.TFG.REST_API.platform2D.LevelProgress.ProgressType;

public class LevelProgressComparator implements LocalSimilarityFunction{
	@Override
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof LevelProgress))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof LevelProgress))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());
		
		LevelProgress c1 = (LevelProgress) o1;
		LevelProgress c2 = (LevelProgress) o2;

		Importance i1 = c1.getImportance();
		Importance i2 = c2.getImportance();
		Interval interval = new Interval(Importance.values().length);
		
		RangeType r1 = c1.getRangeType();
		RangeType r2 = c2.getRangeType();
		
		ProgressType t1 = c1.getProgressType();
		ProgressType t2 = c2.getProgressType();
		
		Equal equal = new Equal();
		
		return 0.3 * interval.compute(i1, i2) + 0.3 * equal.compute(r1, r2) + 0.4 * equal.compute(t1, t2);
	}

	@Override
	public boolean isApplicable(Object o1, Object o2) {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof LevelProgress;
		else if(o2==null)
			return o1 instanceof LevelProgress;
		else
			return (o1 instanceof LevelProgress)&&(o2 instanceof LevelProgress);
	}
}
