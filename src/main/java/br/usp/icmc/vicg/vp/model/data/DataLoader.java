package br.usp.icmc.vicg.vp.model.data;

import java.io.IOException;

public class DataLoader {

	public static DataMatrix loadData(DataSet dataset) throws IOException {
		
		DataMatrix data = new DataMatrix(dataset);
		data.load();
		return data;
	}
}
