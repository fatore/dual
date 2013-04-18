package br.usp.icmc.vicg.vp;

import javax.swing.JFrame;

import br.usp.icmc.vicg.vp.controller.ControllerHandle;
import br.usp.icmc.vicg.vp.model.data.DataMatrix;
import br.usp.icmc.vicg.vp.model.data.DataSet;
import br.usp.icmc.vicg.vp.model.data.DataSets;
import br.usp.icmc.vicg.vp.view.MainView;

public class App {

	public static void main(String[] args) throws Exception {

		MainView view = new MainView();
		view.setTitle("Dual Projections");
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.pack();
		view.setVisible(true);

		ControllerHandle.getInstance().init(
				view.getTopPanel(),
				view.getMiddlePanel(), 
				view.getBottomPanel(),
				view.getEastPanel(),
				view.getEastTopPanel());
		
		DataSet dataset = null;
		if (args.length > 0) {
		
			switch (args[0]) {
			
				case ("iris"): dataset = DataSets.iris; break;
				case ("wine"): dataset = DataSets.wine; break;
			}
		}
		else {
			
			dataset = DataSets.iris;
		}
		
		DataMatrix data = new DataMatrix(dataset);
		data.load(dataset);
		
		DataMatrix tData = data.getTranspose(true);
		
		ControllerHandle.getInstance().attachData(data, tData);
	}
}
