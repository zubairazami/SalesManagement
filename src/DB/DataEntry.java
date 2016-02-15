package DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;

import MainPackage.Controll;




public class DataEntry 
{
		
		private ConnectionManager TempCM ;
		private Statement EStatement;
		private ResultSet EResultSet;
		public DataEntry(Controll x)
		{
		 TempCM = x.ConnectionManagerObject;  
		}
		
		public Vector<String> getEnrolledDates()
		{
			Vector<String> Dates = new Vector<String>();
			closeAll();
			try {
				 EStatement = TempCM.MyConnection.createStatement();
				 EResultSet = EStatement.executeQuery("SELECT DISTINCT date FROM data ;");
				 
				 if(EResultSet.next())
			       {
					 EResultSet.previous();
					 while(EResultSet.next())
					 {
						 Dates.add(EResultSet.getObject(1).toString());
					 }
					 
			       }
			     else
			       {
			    	 
			    	 Dates.clear();
			    	 closeAll();
			    	 
			       }
			    }
			catch (SQLException e) 
			    {
				 Dates.clear();
				 closeAll(); 
				 JOptionPane.showMessageDialog(null, "Error in getEnrolledDates.\nAn unexpected change found in Database.\nPlease restore your Database.");
			    }
			
			
			return Dates;
		}
		
		public boolean addItem(String ID,String Cost)
		{
			boolean Flag = true;
			closeAll();
			try 
			  {
				EStatement = TempCM.MyConnection.createStatement();
				EStatement.executeUpdate("INSERT INTO purchase ( `id` , `cost` ) VALUES ( '"+ID+"',  '"+Cost+"');");
			  } 
			catch (SQLException e) 
			  {
				JOptionPane.showMessageDialog(null, "Error in addItem.\nAn unexpected change found in Database.\nPlease restore your Database.");
			    Flag = false;
			  }
			closeAll();
			return Flag;
			
		}
		
		
		public boolean hasItem(String ID)
		{
			boolean Flag = false;
			closeAll();
			try 
			  {
				EStatement = TempCM.MyConnection.createStatement();
				EResultSet = EStatement.executeQuery("SELECT id FROM purchase WHERE id = '"+ID+"' ;");
			    if(EResultSet.next())
			    	Flag = true;
			    
			  } 
			catch (SQLException e) 
			  {
				JOptionPane.showMessageDialog(null, "Error in addItem.\nAn unexpected change found in Database.\nPlease restore your Database.");
			    Flag =true;
			  }
			closeAll();
			return false;
		}
		
		
		public Vector<String>getEnrolledItems()
		{
			closeAll();
			Vector<String>Items = new Vector<String>();
			try 
			  {
				EStatement = TempCM.MyConnection.createStatement();
			    EResultSet = EStatement.executeQuery("SELECT id FROM purchase;");
			    EResultSet.previous();
			    while(EResultSet.next())
			         Items.add(EResultSet.getObject(1).toString());
			  } 
			catch (SQLException e) 
			  {
				Items.clear();
				JOptionPane.showMessageDialog(null, "Error in getEnrolledItems.\nAn unexpected change found in Database.\nPlease restore your Database.");
			  }
			closeAll();
			return Items;
		}
		
		public Vector<String> entryData(Vector<String>Item,Vector<String>Taken,Vector<String>UnitCost)
		{
			closeAll();
			Vector<String> Data= new Vector<String>();
			Vector<String> PurchaseCost;
			String CurrentDate, CurrentTime;
			CurrentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
			CurrentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			String TotalSales, TotalVat,TotalPurchase,profit;
			float TotalCost=0;
			int Size = Item.size();
			try 
			  {
				PurchaseCost = getPurchaseCost(Item);
				EStatement = TempCM.MyConnection.createStatement();
				for(int i=0; i<Size; i++)
				   {
					TotalCost+= Float.parseFloat(Taken.elementAt(i))*Float.parseFloat(UnitCost.elementAt(i));
					TotalSales = Float.toString(Float.parseFloat(Taken.elementAt(i))*Float.parseFloat(UnitCost.elementAt(i)));
					TotalPurchase = Float.toString(Float.parseFloat(Taken.elementAt(i))*Float.parseFloat(PurchaseCost.elementAt(i)));
					TotalVat = Float.toString(  (float)( Float.parseFloat(TotalSales)+Float.parseFloat(TotalSales)*0.15 )  );
				    profit = Float.toString( (float) Float.parseFloat(TotalSales)-Float.parseFloat(TotalPurchase));
				    EStatement.executeUpdate("INSERT INTO `salesdata`.`data` (`item` , `taken` , `perunitpurchasecost` , `perunitsalescost` , `totalsale` , `totalpurchase` , `profit` , `totalwithvat` , `date` , `time` )VALUES ('"+Item.elementAt(i)+"', '"+Taken.elementAt(i)+"', '"+PurchaseCost.elementAt(i)+"', '"+UnitCost.elementAt(i)+"', '"+TotalSales+"', '"+TotalPurchase+"', '"+profit+"', '"+TotalVat+"', '"+CurrentDate+"', '"+CurrentTime+"');");
				   }
				Data.add(Float.toString(TotalCost));
				Data.add(Float.toString((float)(TotalCost*.15+TotalCost)));
				closeAll();
				return Data;
			  } 
			catch (SQLException e) 
			  {
				Data.clear();
				closeAll();
				JOptionPane.showMessageDialog(null, "Error in entryData.\nAn unexpected change found in Database.\nPlease restore your Database.");
			    return Data;
			  }
		}
		
		private Vector<String>getPurchaseCost(Vector<String>Selected) throws SQLException
		{
			closeAll();
			Vector<String>PurchaseCost = new Vector<String>();
			int Size = Selected.size();
			
				EStatement = TempCM.MyConnection.createStatement();
			    for(int i=0;i<Size;i++)
			    {
			    	EResultSet = EStatement.executeQuery("SELECT cost FROM purchase WHERE id = '"+Selected.elementAt(i)+"' ;");
			    	if(EResultSet.next())
			    		PurchaseCost.add(EResultSet.getObject(1).toString());
			    }
			closeAll();
			return PurchaseCost;
		}
		
		public void showResult(String Date)
		{
			closeAll();
			DataTransfer.refreshAll();
			try 
			  {
				EStatement = TempCM.MyConnection.createStatement();
				EResultSet = EStatement.executeQuery("SELECT * FROM data WHERE date = '"+Date+"'ORDER BY time ASC;");
				EResultSet.previous();
				while(EResultSet.next())
				     { 
						DataTransfer.Items.add(EResultSet.getObject(1).toString());
						DataTransfer.Taken.add(EResultSet.getObject(2).toString());
						DataTransfer.PerUnitPurchaseCost.add(EResultSet.getObject(3).toString());
						DataTransfer.PerUnitSalesCost.add(EResultSet.getObject(4).toString());
						DataTransfer.TotalSales.add(EResultSet.getObject(5).toString());
						DataTransfer.TotalPurchase.add(EResultSet.getObject(6).toString());
						DataTransfer.Profit.add(EResultSet.getObject(7).toString());
						DataTransfer.TotalWithVat.add(EResultSet.getObject(8).toString());
						DataTransfer.Date.add(EResultSet.getObject(9).toString());
						DataTransfer.Time.add(EResultSet.getObject(10).toString());
				     }
			  } 
			catch (SQLException e) 
			  {
				
				closeAll();
				JOptionPane.showMessageDialog(null, "Error in showData.\nAn unexpected change found in Database.\nPlease restore your Database.");
			  }
			
		}
		
		private void  closeAll()
		{
			if(EResultSet!=null)
			   EResultSet = null;
			if(EStatement!=null)
			   EStatement = null;
		}
	    
		
		
		
			
			
			
			
			
			
	}

