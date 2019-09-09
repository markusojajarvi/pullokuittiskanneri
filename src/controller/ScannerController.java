package controller;

import java.text.DecimalFormat;

import application.ScannerGUI;
import application.ScannerGUI_IF;
import model.ScannerModel;
import model.ScannerModel_IF;

public class ScannerController implements ScannerController_IF {
	ScannerGUI_IF gui;
	ScannerModel_IF model;
	DecimalFormat df2 = new DecimalFormat("0.00");
	
	
	public ScannerController(ScannerGUI gui) {
		this.gui = gui;
		model = new ScannerModel();
	}
	
	@Override
	public void updateSum(String sum) {
		if (model.isSumTrue(sum)) {
			double sumTotal = model.getSum(sum);
			double sumPrev = Double.parseDouble(model.getPrevSum());
			String nextLog = "+ " + df2.format(sumPrev) + "€\n";
			gui.insertToLog(nextLog);
			gui.setSum(sumTotal);
		} else {
			gui.insertToLog("VIRHEELLINEN SYÖTTÖ!\n");
		}
	}
	
	@Override
	public void undoLast() {
		String sumPrev = model.getPrevSum();
		if (sumPrev!=null) {
			String nextLog = "- " + df2.format(Double.parseDouble(sumPrev)) + "€\n";
			gui.insertToLog(nextLog);
			gui.setSum(model.undoLast());
		}

	}
	
	@Override
	public void clearSumTotal() {
		model.clearSumTotal();
		gui.clearLog();
		gui.setSum(0);
	}
	@Override
	public void insertIntoSum(double sum, String type) {
		gui.setSum(model.insertIntoSum(sum));
		if (type=="can") {
			gui.insertToLog("+ 1x Tölkki " + df2.format(sum) + "€\n");
		} else if (type=="bottle") {
			gui.insertToLog("+ 1x Pullo " + df2.format(sum) + "€\n");
		} else if (type=="otherInput") {
			gui.insertToLog("+ Manuaalisyöttö: " + df2.format(sum) + "€\n");
		} else {
			gui.insertToLog("+ " + df2.format(sum) + "€\n");
		}
	}

}
