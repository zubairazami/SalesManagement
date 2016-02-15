package DB;
import java.sql.Connection;        
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import MainPackage.*;


public class ConnectionManager 
{
	public Connection MyConnection;
	private Connection VDBMSC,RootConnection;
	private Statement RootStatement ;
	private ResultSet RootResultSet;
	
	private String Username,Password;
	private String ConnectionString = "jdbc:mysql://localhost/salesdata";
	private String ConnectionStringNoDB = "jdbc:mysql://localhost/";
	private Controll TempControll;
	private ResultSet VDBMSRS;
	private Statement VDBMSS;
	private String SQL[] = new String[6];
	
	public ConnectionManager(Controll x)
	{
		TempControll = x;
		SQL[0]  =  "CREATE DATABASE salesdata;";
		SQL[1]  =  "USE salesdata;";
		SQL[2]  =  "DROP TABLE IF EXISTS `batch`;";
		SQL[3]  =  "CREATE TABLE IF NOT EXISTS `data` ( `item` varchar(500) NOT NULL, `taken` float NOT NULL, `perunitpurchasecost` float NOT NULL,`perunitsalescost` float NOT NULL, `totalsale` float NOT NULL, `totalpurchase` float NOT NULL, `profit` float NOT NULL, `totalwithvat` float NOT NULL, `date` varchar(20) NOT NULL, `time` varchar(20) NOT NULL ) ;";
		SQL[4]  =  "CREATE TABLE IF NOT EXISTS `purchase` ( `id` varchar(500) NOT NULL, `cost` float NOT NULL, PRIMARY KEY (`id`));";
		SQL[5]  =  "USE salesdata";
	}

	public boolean createConnection()
	{
	  try 
	    {
		  
		  if(validateConnection() && validateConnectiontoDBMS())
			return true; 
	   	 validateConnectiontoDBMS();
	   	 MyConnection = DriverManager.getConnection(ConnectionString,Username,Password);
		 return true;
	    }	  
	  catch (SQLException e) 
		{  
		   JOptionPane.showMessageDialog(null,e.getErrorCode() + " : "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		   if(e.getErrorCode()==1045)
			   JOptionPane.showMessageDialog(null, "The Username & Password you've entered is invalid.\nSet the Username & Password for MySQL Database Management System correctly.", "Invalid Username Password Combination", JOptionPane.ERROR_MESSAGE);
		   if(e.getErrorCode()==1049)
		     JOptionPane.showMessageDialog(null, "Required database is missing.\nMost probably, It has been deleted recently.\nTo overcome this problem please, restart the application.", "Database Missing", JOptionPane.ERROR_MESSAGE);
		   if(e.getErrorCode()==0)
		      JOptionPane.showMessageDialog(null,"Can't connect to MySQL server on 'localhost'.\n Ensure MySQL Database Management System is running on your computer.", "Connection Interruption", JOptionPane.ERROR_MESSAGE);
		   return false; 
		}
	}
	
	public void setUserPass(String U,String P)
	{
		Username = U;
		Password = P;
	}
	

	public void closeConnection()
	{
	   if(MyConnection!=null)
		 {
		  try{MyConnection.close();} 
	      catch (SQLException e) { e.printStackTrace(); }
		 }
	}
	
	private boolean validateConnection()
	{		
		if(MyConnection==null) return false;
		else
		    try {
			     if(MyConnection.isValid(0))
			        return true;
			     else 
			       {
				    return false; // it would work when created connection looses it's validity because of MySQL being turned off during the usage of this application  or any connection kill (by administrator)
				   }
		        } 
		    catch (SQLException e) 
		        {
		         return false;  // see the manual of isValid function of  
		        }
	}
	
	private boolean validateConnectiontoDBMS() throws SQLException
	{
		int NumberOfRows,Response;
		VDBMSC = DriverManager.getConnection(ConnectionStringNoDB, Username,Password);
		VDBMSS = VDBMSC.createStatement();	
		VDBMSRS = VDBMSS.executeQuery("SHOW DATABASES LIKE 'salesdata'");
		if(!VDBMSRS.next())
		   for(String sql: SQL)
		 		{
			     VDBMSS.executeUpdate(sql);
		 		}
		else
		  {
			VDBMSS.executeUpdate("USE salesdata;");
		    VDBMSRS = VDBMSS.executeQuery("SHOW TABLES");
//		    VDBMSRS.last();
//		    NumberOfRows = VDBMSRS.getRow();		    
//		    if(NumberOfRows!=1)
//		      {
//		    	if(JOptionPane.showConfirmDialog(null, "Unauthorized change has been found in database : termresultcalculator .\nProbably, one or more Tables have been dropped which may stop the application to work correctly.\nDo you want to create the database again? ", "Change in Database Table", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
//		    	  {
//		    		for(int i = 1; i<5; i++)
//			    	  VDBMSS.executeUpdate(SQL[i]);
//		    		JOptionPane.showMessageDialog(null, "Changes have been made.","Success", JOptionPane.INFORMATION_MESSAGE);
//		          }
//		      }
		  }
		closeVDBMS();
		return true;
	}
	
	private void closeVDBMS()
	{
		if(VDBMSRS!=null)
		  {
			try {VDBMSRS.close();} 
		    catch (SQLException e1) {e1.printStackTrace();}
		  }
		if(VDBMSS!=null)
		  {
			try {VDBMSS.close();} 
		    catch (SQLException e1) {e1.printStackTrace();}
		  }
	    if(VDBMSC!=null)
	      {
	       try {VDBMSC.close();} 
	       catch (SQLException e) {e.printStackTrace();}
	      }
	}
	
	private void closeRoot()
	{
		try 
		  {
		   if(RootResultSet!=null)
			 RootResultSet.close();
		   if(RootStatement!=null)
			 RootStatement.close();
		   if(RootConnection!=null)
			 RootConnection.close();
		  } 
		catch (SQLException e) 
		  {
			e.printStackTrace();
		  }
	}
}