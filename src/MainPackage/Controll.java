package MainPackage;

import DB.*; 
import GUI.*;

public class Controll 

{
	public static ConnectionManager ConnectionManagerObject;
	public static MainFrame MainFrameObject; 
	public static DataEntry DataEntryObject;
	public static EntryDialog EntryDialogObject;
	public static DailySalesDialog DailySalesDialogObject;
	
	
	public static void main(String[] args) 
	{
		
		Controll Controller = new Controll();
		ConnectionManagerObject = new ConnectionManager(Controller);
		MainFrameObject = new MainFrame(Controller);
		DataEntryObject = new DataEntry(Controller);
		MainFrameObject.createMainframe();
		EntryDialogObject = new EntryDialog(Controller);
		DailySalesDialogObject = new DailySalesDialog(Controller);
	}

}
