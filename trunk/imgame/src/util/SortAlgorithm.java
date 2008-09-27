package util;

/**
 * 
 * @author zico
 * 
 */
public class SortAlgorithm {

	/**
	 * get the min and max value of the given array
	 * returns[]:returns[0]-min,returns[1]-max
	 * 
	 * @param origin
	 * @return
	 */
	public double[] getMaxMinNumber(double origin[]) {

		double returns[] = { 0, 0 };

		for (int i = 0; i < origin.length; i++) {
			if (origin[i] < returns[0]) {
				returns[0] = origin[i];
			}

			if (origin[i] > returns[1]) {
				returns[1] = origin[i];
			}
		}

		return returns;
	}

	public double[] getMaxAvgMinNumber(double origin[]) {

		double returns[] = { 0.0, 0.0, 0.0 };
		double sum = 0.0;
		for (int i = 0; i < origin.length; i++) {
			if (origin[i] < returns[0]) {
				returns[0] = origin[i];
			}
			if (origin[i] > returns[2]) {
				returns[2] = origin[i];
			}
			sum += origin[i];
		}
		returns[1] = sum / origin.length;
		return returns;
	}
}
