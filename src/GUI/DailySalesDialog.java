package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import DB.DataTransfer;
import MainPackage.Controll;

public class DailySalesDialog implements ActionListener
{
	
	private Controll TempControll;
	private int Rows;
	private JPanel Panel,GridPanel;
	private JScrollPane Scroll;
	private JDialog DSD;
	private JButton OKButton;
	private MyObject[] My;
	private JLabel TotalLabel,TotalWithVatLabel,ProfitLabel;
	private JLabel ColumnName;
	
	public DailySalesDialog (Controll X)
	{
		TempControll = X;
	}


public void createDailySalesDialog(int Number)
{
	this.Rows = Number;
	
	DSD = new JDialog();
	
	final int Final = this.Rows*40+550;
	
	Panel = new JPanel()
	{
		protected void paintComponent(Graphics g)
			   {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(getClass().getResource("/ICON/Panel.jpg")).getImage(),0,0,1250,Final,null);  // setting background wallpaper
			    }
	};
	Panel.setPreferredSize(new Dimension(1250,this.Rows*40+330));  // 100+50+(100*8+3*7)+50, 50+this.Rows*30+(this.Rows-1)*10+50+60
	Panel.setLayout(null);
		
    
		
    ColumnName = new JLabel("   Serial          Item                      Quantity           Purchased (Unit)           Sold (Unit)           Total Purchase           Total Sale                      Profit               Cost (15% VAT)               Date                         Time",SwingConstants.LEFT);
	ColumnName.setForeground(Color.BLACK);
	ColumnName.setFont(new Font("SERRIF",Font.BOLD,12));
	ColumnName.setBounds(120,70,1200, 30);
	Panel.add(ColumnName);
		
	setComponentsOnTheGrid();
		
	TotalLabel = new JLabel("Total Sell   :   "+ getTotal()+"/=",SwingConstants.CENTER);
	TotalLabel.setForeground(Color.BLACK);
	TotalLabel.setFont(new Font("SERRIF",Font.BOLD,16));
	TotalLabel.setBounds(0,155+this.Rows*40,1250,30);
	Panel.add(TotalLabel);
	
	TotalWithVatLabel = new JLabel("Total Sell (including 15% VAT)  :  "+ getTotalWithVat()+"/=",SwingConstants.CENTER);
	TotalWithVatLabel.setForeground(Color.BLACK);
	TotalWithVatLabel.setFont(new Font("SERRIF",Font.BOLD,16));
	TotalWithVatLabel.setBounds(0,180+this.Rows*40,1250,30);
	Panel.add(TotalWithVatLabel);
	
	ProfitLabel = new JLabel("Total Profit   :   "+ getProfit()+"/=",SwingConstants.CENTER);
	ProfitLabel.setForeground(Color.BLACK);
	ProfitLabel.setFont(new Font("SERRIF",Font.BOLD,16));
	ProfitLabel.setBounds(0,205+this.Rows*40,1250,30);
	Panel.add(ProfitLabel);
	
	
    OKButton = new JButton("OK");
    OKButton.setFont(new Font("SERRIF",Font.BOLD,16));
	OKButton.setForeground(Color.BLACK);
	OKButton.setBounds(500,205+this.Rows*40+50,250,30); // 50+this.Rows*30+(this.Rows-1)*10+35
	OKButton.setOpaque(false);
	OKButton.addActionListener(this);
	
	Panel.add(OKButton);
		
		
	Scroll = new JScrollPane(Panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
	DSD.add(Scroll);
	DSD.setModal(true);
	DSD.setTitle("Daily Sales Report");
	DSD.setResizable (false);
	DSD.setLocation(75,100);
	DSD.setSize(1250,565);
	DSD.setVisible(true);
	DSD.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
}
	
	private void setComponentsOnTheGrid()
	{
		GridPanel = new JPanel(new GridLayout(0,11,5,10));
		My = new MyObject[this.Rows+1];
		
		
		for(int i = 0;i<this.Rows;i++)
		   {
			My[i] = new MyObject(i);
			GridPanel.add(My[i].Serial);
			GridPanel.add(My[i].Item);
			GridPanel.add(My[i].Taken);
			GridPanel.add(My[i].PerUnitPurchaseCost);
			GridPanel.add(My[i].PerUnitSalesCost);
			GridPanel.add(My[i].TotalPurchase);
			GridPanel.add(My[i].TotalSales);
			GridPanel.add(My[i].Profit);
			GridPanel.add(My[i].TotalWithVat);
			GridPanel.add(My[i].Date);
			GridPanel.add(My[i].Time);
		   }
		GridPanel.setBounds(50,100,1150,40*(this.Rows+1)-10); // 11*100+10*5,this.Rows*30+(this.Rows-1)*10
		GridPanel.setOpaque(false);
		
		Panel.add(GridPanel);
	}

private class MyObject 
	{
		JLabel Serial, Item,Taken,PerUnitSalesCost,PerUnitPurchaseCost,TotalSales,TotalPurchase,Profit,TotalWithVat,Date,Time;
		
		public MyObject(int i)
		{
			
			Serial = new JLabel(Integer.toString(i+1)+". ",SwingConstants.RIGHT);
			Serial.setForeground(Color.BLACK);
			Serial.setFont(new Font("SERRIF",Font.BOLD,15));
			Serial.setOpaque(false);
			
			Item = new JLabel(DataTransfer.Items.elementAt(i),SwingConstants.CENTER);
			Item.setForeground(Color.BLACK);
			Item.setFont(new Font("SERRIF",Font.BOLD,15));
			Item.setOpaque(false);
			
			Taken = new JLabel(DataTransfer.Taken.elementAt(i),SwingConstants.CENTER);
			Taken.setForeground(Color.BLACK);
			Taken.setFont(new Font("SERRIF",Font.BOLD,15));
			Taken.setOpaque(false);
			
			
			PerUnitSalesCost = new JLabel(DataTransfer.PerUnitSalesCost.elementAt(i),SwingConstants.CENTER);
			PerUnitSalesCost.setForeground(Color.BLACK);
			PerUnitSalesCost.setFont(new Font("SERRIF",Font.BOLD,15));
			PerUnitSalesCost.setOpaque(false);
			
			
			PerUnitPurchaseCost = new JLabel(DataTransfer.PerUnitPurchaseCost.elementAt(i),SwingConstants.CENTER);
			PerUnitPurchaseCost.setForeground(Color.BLACK);
			PerUnitPurchaseCost.setFont(new Font("SERRIF",Font.BOLD,15));
			PerUnitPurchaseCost.setOpaque(false);
			
			
			TotalSales = new JLabel(DataTransfer.TotalSales.elementAt(i),SwingConstants.CENTER);
			TotalSales.setForeground(Color.BLACK);
			TotalSales.setFont(new Font("SERRIF",Font.BOLD,15));
			TotalSales.setOpaque(false);
			
			TotalPurchase = new JLabel(DataTransfer.TotalPurchase.elementAt(i),SwingConstants.CENTER);
			TotalPurchase.setForeground(Color.BLACK);
			TotalPurchase.setFont(new Font("SERRIF",Font.BOLD,15));
			TotalPurchase.setOpaque(false);
			
			
			Profit = new JLabel(DataTransfer.Profit.elementAt(i),SwingConstants.CENTER);
			Profit.setForeground(Color.BLACK);
			Profit.setFont(new Font("SERRIF",Font.BOLD,15));
			Profit.setOpaque(false);
			
			TotalWithVat= new JLabel(DataTransfer.TotalWithVat.elementAt(i),SwingConstants.CENTER);
			TotalWithVat.setForeground(Color.BLACK);
			TotalWithVat.setFont(new Font("SERRIF",Font.BOLD,15));
			TotalWithVat.setOpaque(false);
			
			Date = new JLabel(DataTransfer.Date.elementAt(i),SwingConstants.CENTER);
			Date.setForeground(Color.BLACK);
			Date.setFont(new Font("SERRIF",Font.BOLD,15));
			Date.setOpaque(false);
			
			Time = new JLabel(DataTransfer.Time.elementAt(i),SwingConstants.CENTER);
			Time.setForeground(Color.BLACK);
			Time.setFont(new Font("SERRIF",Font.BOLD,15));
			Time.setOpaque(false);
			
		}
	}

private String getTotal()
{
	float x = 0;
	for(int i=0; i<this.Rows;i++)
	   	x+= Float.parseFloat( My[i].TotalSales.getText().toString() );
	return Float.toString(x);
}


private String getProfit()
{
	float x = 0;
	for(int i=0; i<this.Rows;i++)
	   	x+= Float.parseFloat( My[i].Profit.getText().toString() );
	return Float.toString(x);
}

private String getTotalWithVat()
{
	float x = 0;
	for(int i=0; i<this.Rows;i++)
	   	x+= Float.parseFloat( My[i].TotalWithVat.getText().toString() );
	
	return Float.toString(x);
}

@Override
public void actionPerformed(ActionEvent AE) 
{
	if(AE.getSource()==OKButton)
	  {
		DSD.dispose();
	  }
}
	
	
}
