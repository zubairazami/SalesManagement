package DB;
import java.util.Vector; 

public class DataTransfer 
{
	public static Vector<String> Items = new Vector<String>();
	public static Vector<String> Taken = new Vector<String>();
	public static Vector<String> PerUnitSalesCost = new Vector<String>();
	public static Vector<String> PerUnitPurchaseCost = new Vector<String>();
	public static Vector<String> TotalSales = new Vector<String>();
	public static Vector<String> TotalPurchase = new Vector<String>();
	public static Vector<String> Date = new Vector<String>();
	public static Vector<String> Time = new Vector<String>();
	public static Vector<String> TotalWithVat = new Vector<String>();
	public static Vector<String> Profit = new Vector<String>();
	public static void refreshAll()
	{
		Items.clear();
		Taken.clear();
		PerUnitSalesCost.clear();
		PerUnitPurchaseCost.clear();
		TotalSales.clear();
		TotalPurchase.clear();
		Date.clear();
		Time.clear();
		TotalWithVat.clear();
		Profit.clear();
	}

}