package fr.irit.amak.dramas;

import java.util.Comparator;

public class AreaComparator implements Comparator<Area>{

	@Override
	public int compare(Area o1, Area o2) {
		Double crit1 = o1.computeCriticality();
		Double crit2 = o2.computeCriticality();
		int resCrit = crit2.compareTo(crit1);


		if (resCrit == 0) {
			Integer X1 = o1.getX();
			Integer X2 = o2.getX();
			int resX = X2.compareTo(X1);
			if (resX == 0) {
				Integer y1 = o1.getY();
				Integer y2 = o2.getY();
				return y1.compareTo(y2);
			}
			return resX;
		}
		return resCrit;
	}

}