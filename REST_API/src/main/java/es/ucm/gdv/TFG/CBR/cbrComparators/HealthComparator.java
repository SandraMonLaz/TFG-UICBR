package es.ucm.gdv.TFG.CBR.cbrComparators;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.gdv.TFG.CBR.cbrComponents.Health;
import es.ucm.gdv.TFG.CBR.cbrComponents.RangeType;

public class HealthComparator implements LocalSimilarityFunction {

	@Override
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof Health))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof Health))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());


		Health i1 = (Health) o1;
		Health i2 = (Health) o2;
		
		RangeType v1 = i1.getType();
		RangeType v2 = i2.getType();
		return Math.abs((v1.ordinal() - v2.ordinal()) - 1);
	}

	@Override
	public boolean isApplicable(Object o1, Object o2) {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof Health;
		else if(o2==null)
			return o1 instanceof Health;
		else
			return (o1 instanceof Health)&&(o2 instanceof Health);
	}

}
