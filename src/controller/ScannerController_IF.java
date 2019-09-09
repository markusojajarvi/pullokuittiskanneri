package controller;

public interface ScannerController_IF {

	void updateSum(String sum);

	void undoLast();

	void clearSumTotal();

	void insertIntoSum(double sum, String type);

}
