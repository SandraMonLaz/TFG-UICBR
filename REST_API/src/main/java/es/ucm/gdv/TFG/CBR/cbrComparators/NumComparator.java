package es.ucm.gdv.TFG.CBR.cbrComparators;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

public class NumComparator implements LocalSimilarityFunction {

	public NumComparator(double maxValue) {
		this.max = maxValue;
	}
	
	@Override
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		if ((o1 == null) && (o2 == null))
			return 0.5;
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof Number))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof Number))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());

		Number i1 = (Number) o1;
		Number i2 = (Number) o2;
		
		double v1 = i1.doubleValue();
		double v2 = i2.doubleValue();
		
		return 1 - ((double) Math.abs(v1 - v2) / this.max);
	}

	@Override
	public boolean isApplicable(Object o1, Object o2) {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof Number;
		else if(o2==null)
			return o1 instanceof Number;
		else
			return (o1 instanceof Number)&&(o2 instanceof Number);
	}

	private double max;
}
