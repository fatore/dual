package br.usp.icmc.vicg.vp.model.data;

public class DataSets {

	public static String pexFolder = "D:\\Dropbox\\work\\datasets\\PEX\\";
	public static String micFolder = "D:\\Dropbox\\work\\datasets\\mic\\";
	public static String uciFolder = "D:\\Dropbox\\work\\datasets\\uci\\";
	public static String dataFolder = "data\\";
	
	public static String idh = pexFolder + "idh-2006.data";
	public static String tumor = pexFolder + "primary-tumor.data";
	public static String diabetes = pexFolder + "diabetes.data";
	public static String messages4 = pexFolder + "messages4.data";

	public static String who = micFolder + "WHO.csv";
	public static String Spellman = micFolder + "Spellman.csv";
	public static String microNo = micFolder + "MicrobiomeNoMetadata.csv";
	public static String microWith = micFolder + "MicrobiomeWithMetadata.csv";
	
	public static DataSet attributes = new DataSet(
			dataFolder + "attributes.csv", 0, null, new Integer[]{0});
	
	public static DataSet iris = new DataSet(
			dataFolder + "Iris.csv", null, 4, new Integer[]{4});
	
	public static DataSet wine = new DataSet(
			dataFolder + "wine.csv", null, 13, new Integer[]{13});
	
	public static DataSet wisconsin = new DataSet(
			uciFolder + "wisconsin.csv", null, 10, new Integer[]{0,10});
	
	public static DataSet mlb = new DataSet(
			micFolder + "MLB2008.csv", 0, 2, new Integer[]{0,1,2});
}
