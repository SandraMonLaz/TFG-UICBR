package es.ucm.gdv.TFG.CBR.cbrComparators;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Time;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.platform2D.Time.TimeUse;

/*
 * Comparador para el elemento de tiempo
 * */
public class TimeComparator implements LocalSimilarityFunction{
	
	@Override
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		if ((o1 == null) && (o2 == null))
			return 0.5;
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof Time))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof Time))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());
		
		Time c1 = (Time) o1;
		Time c2 = (Time) o2;

		Importance i1 = c1.getImportance();
		Importance i2 = c2.getImportance();
		Interval interval = new Interval(Importance.values().length);
		
		TimeUse t1 = c1.getTimeUse();
		TimeUse t2 = c2.getTimeUse();
		Equal equal = new Equal();
		
		return 0.5 * interval.compute(i1.ordinal(), i2.ordinal()) + 0.5 * equal.compute(t1.ordinal(), t2.ordinal());
	}

	@Override
	public boolean isApplicable(Object o1, Object o2) {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof Time;
		else if(o2==null)
			return o1 instanceof Time;
		else
			return (o1 instanceof Time)&&(o2 instanceof Time);
	}
}
