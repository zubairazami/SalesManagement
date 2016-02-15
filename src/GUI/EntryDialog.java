package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import MainPackage.Controll;

public class EntryDialog implements ActionListener
{

	private Border OKStatus = new BevelBorder(BevelBorder.LOWERED,Color.GRAY,Color.BLACK);
	
	private Border WrongStatus = new BevelBorder(BevelBorder.LOWERED,Color.RED,Color.BLACK);
	private Controll TempControll;
	private int ItemNumber;
	private JButton EntryButton;
	private JPanel Panel,GridPanel;
	private JScrollPane Scroll;
	private JDialog ED;
	private MyObject[] My;
	private JCheckBox Check;
	private JLabel ColumnName,CheckLabel;
	private Vector<String> Items,Sold,UnitCost,ItemList;
	
	
	public EntryDialog(Controll X)
	{
		TempControll = X;
		Items = new Vector<String>();
		Sold = new Vector<String>();
		UnitCost = new Vector<String>();
		ItemList = new Vector<String>();
	}
	
	
	public void createEntryDialog(int Number)
	{
		this.ItemNumber = Number;
	    final int Final =  this.ItemNumber*40+500;	
		ED = new JDialog();
		
		Panel = new JPanel()
		{
			protected void paintComponent(Graphics g)
				   {
					super.paintComponent(g);
					g.drawImage(new ImageIcon(getClass().getResource("/ICON/Panel.jpg")).getImage(),0,0,900,Final,null);  // setting background wallpaper
				   }
		};
		Panel.setPreferredSize(new Dimension(600,this.ItemNumber*40+250));  // 100+50+(100*8+3*7)+50, 50+this.ItemNumber*30+(this.ItemNumber-1)*10+50+60
		Panel.setLayout(null);
			
	    
			
	    ColumnName = new JLabel("          Item                         Quantity                     Per Unit Cost",SwingConstants.CENTER);
		ColumnName.setForeground(Color.BLACK);
		ColumnName.setFont(new Font("SERRIF",Font.BOLD,16));
		ColumnName.setBounds(160,70,460, 30);
		Panel.add(ColumnName);
			
		
		
		CheckLabel = new JLabel("Uncheck all",SwingConstants.LEFT);
		CheckLabel.setForeground(Color.BLACK);
		CheckLabel.setFont(new Font("SERRIF",Font.BOLD,16));
		
		Check = new JCheckBox();
		Check.setForeground(Color.BLACK);
		Check.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,12));
		Check.setHorizontalAlignment(SwingConstants.RIGHT);
		Check.setOpaque(false);
		Check.setSelected(true);
		Check.addActionListener(this);
		
		this.ItemList.clear();
		this.ItemList.addAll(TempControll.DataEntryObject.getEnrolledItems());
		
		setComponentsOnTheGrid();
			
			
	    EntryButton = new JButton("Entry");
	    EntryButton.setFont(new Font("SERRIF",Font.BOLD,16));
		EntryButton.setForeground(Color.BLACK);
		EntryButton.setBounds(265,175+this.ItemNumber*40,250,30); // 50+this.ItemNumber*30+(this.ItemNumber-1)*10+35
		EntryButton.setOpaque(false);
		EntryButton.addActionListener(this);
		Panel.add(EntryButton);
			
			//EntryButton.addActionListener(this);
			
		Scroll = new JScrollPane(Panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
		ED.getContentPane().add(Scroll);
		ED.setModal(true);
		ED.setTitle("Data Entry");
		ED.setResizable (false);
		ED.setLocation(250,50);
		ED.setSize(800,600);
		ED.setVisible(true);
		ED.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
		
		private void setComponentsOnTheGrid()
		{
			GridPanel = new JPanel(new GridLayout(0,4,5,10));
			My = new MyObject[this.ItemNumber+1];
			
			
			for(int i = 0;i<this.ItemNumber;i++)
			   {
				My[i] = new MyObject(i);
				GridPanel.add(My[i].CheckBox);
				GridPanel.add(My[i].Item);
				GridPanel.add(My[i].Taken);
				GridPanel.add(My[i].PerUnitCost);
			   }
			GridPanel.setBounds(10,100,610,40*(this.ItemNumber+1)-10); // 4*150+3*3,this.ItemNumber*30+(this.ItemNumber-1)*10
			GridPanel.setOpaque(false);
			GridPanel.add(Check);
			GridPanel.add(CheckLabel);
			Panel.add(GridPanel);
		}
	
	private class MyObject 
	{
		JCheckBox CheckBox;
		JTextField Taken,PerUnitCost;
		JComboBox Item;
		public MyObject(int i)
		{
			CheckBox = new JCheckBox();
			CheckBox.setForeground(Color.BLACK);
			CheckBox.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
			CheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
			CheckBox.setOpaque(false);
			CheckBox.setSelected(true);
			
			Item = new JComboBox();
			Item.setForeground(Color.BLACK);
			Item.setFont(new Font("SERRIF",Font.BOLD,12));
			Item.setOpaque(false);
			Item.setMaximumRowCount(5);
			
			for(int j=0; j<ItemList.size();j++)
			   Item.addItem(ItemList.elementAt(j));
			
			
			Taken = new JTextField();
			Taken.setForeground(Color.BLACK);
			Taken.setFont(new Font("SERRIF",Font.BOLD,15));
			Taken.setOpaque(false);
			Taken.setBorder(OKStatus);
			Taken.setHorizontalAlignment(SwingConstants.CENTER);
			
			PerUnitCost = new JTextField();
			PerUnitCost.setForeground(Color.BLACK);
			PerUnitCost.setFont(new Font("SERRIF",Font.BOLD,15));
			PerUnitCost.setOpaque(false);
			PerUnitCost.setBorder(OKStatus);
			PerUnitCost.setHorizontalAlignment(SwingConstants.CENTER);
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent AE)
	{
		if(AE.getSource()==Check)
		 {
			boolean Selection = Check.isSelected();
			
			if(Selection)
				CheckLabel.setText("Uncheck All");
			else
				CheckLabel.setText("Check All");
				
			for(int i=0;i<this.ItemNumber;i++)
			    My[i].CheckBox.setSelected(Selection);
		 }
		if(AE.getSource()==EntryButton)
		{
			if(TempControll.ConnectionManagerObject.createConnection())
			  {
			   setToDefault();	
			   if(hasProblem())
			   	 {
				   JOptionPane.showMessageDialog(ED,"Make sure all data are valid.", "Invalid Data", JOptionPane.ERROR_MESSAGE); 
			   	 }
			   else
			     {
				   gatherData();
				   Vector<String> X = TempControll.DataEntryObject.entryData(Items, Sold, UnitCost);
				   if(!(X.size()<=0))
				     {
					  JOptionPane.showMessageDialog(ED, "Total Cost : "+X.elementAt(0)+"\nTotal Cost (including 15% vat)  : "+X.elementAt(1), "Total Cost", JOptionPane.INFORMATION_MESSAGE);  
				     }
				   ED.dispose();
			     }
			  }
		}
	}
	
	private boolean hasProblem()
	{
		boolean Flag = false;
		String Text ;
		
		for(int i=0;i<this.ItemNumber;i++)
		  if(My[i].CheckBox.isSelected())
			{
			  
			  if(My[i].Item.getSelectedIndex()<0)
		      	{
				  My[i].Item.setBorder(WrongStatus);
				  Flag = true ;
		      	}
		    
			  Text = My[i].Taken.getText().toString();
			  if(!((Pattern.matches("([0-9]*)\\.([0-9]*)", Text)||Pattern.matches("([0-9]*)",Text)) && !Text.equals(".")&& !Text.equals("")))
			    {
				  My[i].Taken.setBorder(WrongStatus);
				  Flag = true ;
			    }
			  
			  Text = My[i].PerUnitCost.getText().toString();
			  if(!((Pattern.matches("([0-9]*)\\.([0-9]*)", Text)||Pattern.matches("([0-9]*)",Text)) && !Text.equals(".")&& !Text.equals("")))
		      	{
				  My[i].PerUnitCost.setBorder(WrongStatus);
				  Flag = true ;
		      	}
			}
		
		return Flag;
	}
	
	
	
	
	
	private void setToDefault()
	{
		for(int i=0;i<this.ItemNumber;i++)
		   {
			My[i].Item.setBorder(OKStatus);
			My[i].PerUnitCost.setBorder(OKStatus);
			My[i].Taken.setBorder(OKStatus);
		   }
	}
	
	private void gatherData()
	{
		Items.clear();
		Sold.clear();
		UnitCost.clear();
		for(int i=0;i<this.ItemNumber;i++)
		   if(My[i].CheckBox.isSelected())
		     {
			   Items.add(My[i].Item.getSelectedItem().toString());
			   Sold.add(My[i].Taken.getText().toString());
			   UnitCost.add(My[i].PerUnitCost.getText().toString());
		     }
	}

}
