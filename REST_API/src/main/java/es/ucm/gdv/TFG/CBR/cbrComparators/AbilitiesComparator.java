package es.ucm.gdv.TFG.CBR.cbrComparators;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Abilities;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.UseType;

/*
 * Comparador para el elemento de habilidades
 * */
public class AbilitiesComparator implements LocalSimilarityFunction {

	@Override
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		//Si no existe ni en la consulta ni en el caso se devuelve un valor que no modifique
		//la similitud
		if ((o1 == null) && (o2 == null))
			return 0.5;
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof Abilities))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof Abilities))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());


		Abilities i1 = (Abilities) o1;
		Abilities i2 = (Abilities) o2;
		
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
		
		//Calculamos la similitud
		return 0.33*interval.compute(imp1.ordinal(), imp2.ordinal()) + 0.33*eq.compute(v1.ordinal(), v2.ordinal()) + 0.34*numComp.compute(n1, n2);
	}

	@Override
	public boolean isApplicable(Object o1, Object o2) {
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof Abilities;
		else if(o2==null)
			return o1 instanceof Abilities;
		else
			return (o1 instanceof Abilities)&&(o2 instanceof Abilities);
	}

}
