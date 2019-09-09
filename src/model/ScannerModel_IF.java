package model;

public interface ScannerModel_IF {

	double getSum(String sum);

	void clearSumTotal();

	boolean isSumTrue(String sum);

	double undoLast();

	String getPrevSum();

	double insertIntoSum(double sum);
	
}
