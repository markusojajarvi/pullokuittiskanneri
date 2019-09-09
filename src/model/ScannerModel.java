package model;

import java.util.ArrayList;
import java.util.List;

public class ScannerModel implements ScannerModel_IF {
	private double sumTotal = 0;
	private String sumStr;
	private double sumFinal;
	private ArrayList<Double> sums = new ArrayList<Double>();
	
	
	@Override
	public boolean isSumTrue(String sum) {
		if (sum.matches("[0-9]+") && sum.length() == 13) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public double getSum(String sum) {
		sumStr = sum.substring(7);
		sumStr = sumStr.substring(0,(sumStr.length()-1));
		System.out.println(sumStr);
		sumFinal = (Double.parseDouble(sumStr)/100);
		sums.add(sumFinal);
		
		System.out.println(sumFinal);
		
		sumTotal = sumTotal + sumFinal;
		
		return sumTotal;
	}
	
	@Override
	public void clearSumTotal() {
		sumTotal = 0;
		sums.removeAll(sums);
	}
	
	@Override
	public double undoLast() {
		double undo = 0;
		if (sums.size()>=1) {
			undo = sums.get((sums.size()-1));
			sums.remove((sums.size()-1));
		}
		return sumTotal = sumTotal - undo;
	}
	@Override
	public String getPrevSum() {
		try {
			return Double.toString(sums.get((sums.size()-1)));
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
		
	}
	@Override
	public double insertIntoSum(double sum) {
		sums.add(sum);
		sumTotal = sumTotal + sum;	
		return sumTotal;
	}
}
