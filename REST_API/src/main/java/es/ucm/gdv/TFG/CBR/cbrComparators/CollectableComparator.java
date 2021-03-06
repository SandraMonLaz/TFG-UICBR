package es.ucm.gdv.TFG.CBR.cbrComparators;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Collectable;
import es.ucm.gdv.TFG.REST_API.Importance;

/*
 * Comparador para el elemento de coleccionables
 * */
public class CollectableComparator implements LocalSimilarityFunction{

	@Override
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		if ((o1 == null) && (o2 == null))
			return 0.5;
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof Collectable))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof Collectable))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());
		
		Collectable c1 = (Collectable) o1;
		Collectable c2 = (Collectable) o2;

		Importance i1 = c1.getImportance();
		Importance i2 = c2.getImportance();
		Interval interval = new Interval(Math.max(i1.ordinal(), i2.ordinal()));
		
		return interval.compute(i1.ordinal(), i2.ordinal());
	}

	@Override
	public boolean isApplicable(Object o1, Object o2) {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof Collectable;
		else if(o2==null)
			return o1 instanceof Collectable;
		else
			return (o1 instanceof Collectable)&&(o2 instanceof Collectable);
	}

}
