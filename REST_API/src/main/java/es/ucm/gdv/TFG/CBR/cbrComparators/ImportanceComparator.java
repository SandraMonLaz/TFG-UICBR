package es.ucm.gdv.TFG.CBR.cbrComparators;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.gdv.TFG.CBR.cbrComponents.RangeType;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Health;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Item.Importance;

public class ImportanceComparator implements LocalSimilarityFunction {

	@Override
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof Importance))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof Importance))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());


		Importance i1 = (Importance) o1;
		Importance i2 = (Importance) o2;
		
		return  1 - (Math.abs(i1.ordinal() - i1.ordinal()) / Importance.veryHigh.ordinal());
	}

	@Override
	public boolean isApplicable(Object o1, Object o2) {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof Importance;
		else if(o2==null)
			return o1 instanceof Importance;
		else
			return (o1 instanceof Importance)&&(o2 instanceof Importance);
	}

}
