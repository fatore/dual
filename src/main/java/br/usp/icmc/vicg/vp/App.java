package br.usp.icmc.vicg.vp;

import javax.swing.JFrame;

import br.usp.icmc.vicg.vp.controller.ControllerHandle;
import br.usp.icmc.vicg.vp.model.data.DataLoader;
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

		ControllerHandle.getInstance().initView(
				view.getTopPanel(),
				view.getMiddlePanel(), 
				view.getBottomPanel());
		
		DataMatrix dataMatrix = null;
		if (args.length > 0) {
		
			dataMatrix = DataLoader.loadData(new DataSet(args[0],
					Integer.parseInt(args[1])));
		}
		else {
			
			dataMatrix = DataLoader.loadData(DataSets.iris);
		}
		
		ControllerHandle.getInstance().attachData(dataMatrix, false);
	}
}
