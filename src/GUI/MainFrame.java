package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import DB.DataTransfer;
import MainPackage.Controll;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.regex.Pattern;

public class MainFrame implements ActionListener
{

	private JFrame Frame;
	private JRadioButton RB;
	private JPanel Panel;
	private JTextField User;
	JComboBox ItemCombo,DateCombo;
	JButton ShowButton,EntryButton,SetUserButton,ItemButton;
	private JPasswordField Pass;
	private Controll TempControll;
	private Border SalesEntryTitle = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),"    SALES ENTRY    ",TitledBorder.TOP,TitledBorder.TOP,new Font("Serif", Font.BOLD | Font.ITALIC, 20));
	private Border DailySalesTitle = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),"                          ",TitledBorder.TOP,TitledBorder.TOP,new Font("Serif", Font.BOLD | Font.ITALIC, 20));
	private Border ItemEntryTitle = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),"    ITEM ENTRY    ",TitledBorder.TOP,TitledBorder.TOP,new Font("Serif", Font.BOLD | Font.ITALIC, 20));
	private Border DBMSTitle = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),"  AUTHENTICATION  ",TitledBorder.TOP,TitledBorder.TOP,new Font("Serif", Font.BOLD | Font.ITALIC, 20));
	private JTextField ProductField;
	private JTextField CostField;

	public MainFrame(Controll X)
	{
		TempControll = X;
	}

	public void  createMainframe()
	{
	    Frame = new JFrame("SIMPLE INVENTORY MANAGEMENT");
		setLookAndFeel();

		Panel = new JPanel()
		{
			protected void paintComponent(Graphics g)
				   {
					super.paintComponent(g);
					g.drawImage(new ImageIcon(getClass().getResource("/ICON/Main.jpg")).getImage(),0,0,900,600,null);  // setting background wallpaper
					((TitledBorder) SalesEntryTitle).setTitleColor(Color.BLACK);//(new Color(0,162,232));

					DBMSTitle.paintBorder(this, g, 200,17, 400, 125);  //
					ItemEntryTitle.paintBorder(this, g, 200,147, 400, 140);  //
					SalesEntryTitle.paintBorder(this, g, 200,292, 400, 120);  //
					DailySalesTitle.paintBorder(this, g, 200,424, 400, 120);  //


				   }
		};
		Panel.setLayout(null);
		Frame.getContentPane().add(Panel);

		JLabel UserLabel = new JLabel("Username :");
		UserLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		UserLabel.setForeground(Color.BLACK);
		UserLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		UserLabel.setBounds(311, 46, 89, 18);
		Panel.add(UserLabel);

		JLabel PasswordLabel = new JLabel("Password :");
		PasswordLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		PasswordLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		PasswordLabel.setForeground(Color.BLACK);
		PasswordLabel.setBounds(311, 65, 89, 22);
		Panel.add(PasswordLabel);

		User = new JTextField();
		User.setOpaque(false);
		User.setForeground(Color.BLACK);
		User.setHorizontalAlignment(SwingConstants.CENTER);
		User.setBounds(404, 46, 132, 19);
		User.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		Panel.add(User);
		User.setColumns(10);

		Pass = new JPasswordField();
		Pass.setForeground(Color.BLACK);
		Pass.setHorizontalAlignment(SwingConstants.CENTER);
		Pass.setOpaque(false);
		Pass.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		Pass.setBounds(404, 66, 132, 20);
		Panel.add(Pass);


		JLabel ItemLabel = new JLabel("NUMBER OF ITEMS  :");
		ItemLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ItemLabel.setForeground(Color.BLACK);
		ItemLabel.setBounds(213, 324, 155, 38);
		Panel.add(ItemLabel);

		JLabel DateLabel = new JLabel("DATE  :  ");
		DateLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		DateLabel.setForeground(Color.BLACK);
		DateLabel.setBounds(259, 456, 80, 29);
		Panel.add(DateLabel);

		ItemCombo = new JComboBox();
		for(int i=1;i<=100;i++)
			ItemCombo.addItem(i);
		ItemCombo.setBounds(378, 335, 197, 20);
		ItemCombo.setEditable(true);
		Panel.add(ItemCombo);

		DateCombo = new JComboBox();
		DateCombo.setBounds(333, 462, 226, 20);
		DateCombo.setEnabled(false);
		Panel.add(DateCombo);

	    ShowButton = new JButton("Show");
	    ShowButton.setForeground(Color.BLACK);
	    ShowButton.setBounds(311, 493, 207, 23);
		ShowButton.addActionListener(this);
		ShowButton.setEnabled(false);
		Panel.add(ShowButton);

		EntryButton = new JButton("Entry");
		EntryButton.setForeground(Color.BLACK);
		EntryButton.setBounds(311, 366, 207, 23);
		EntryButton.addActionListener(this);
		Panel.add(EntryButton);

		SetUserButton = new JButton("Set MySQL User");
		SetUserButton.setForeground(Color.BLACK);
		SetUserButton.setBounds(311, 97, 225, 23);
		SetUserButton.addActionListener(this);
		Panel.add(SetUserButton);

		RB = new JRadioButton("Daily Sales");
		RB.setHorizontalAlignment(SwingConstants.CENTER);
		RB.setContentAreaFilled(false);
		RB.setForeground(Color.BLACK);
		RB.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		RB.setBounds(6, 420, 778, 29);
		RB.addActionListener(this);
		Panel.add(RB);

		JLabel lblProduct = new JLabel("Product : ");
		lblProduct.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblProduct.setBounds(311, 180, 80, 29);
		Panel.add(lblProduct);

		JLabel lblCostperUnit = new JLabel("Cost (Per Unit)  : ");
		lblCostperUnit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCostperUnit.setBounds(259, 204, 132, 27);
		Panel.add(lblCostperUnit);

		ItemButton = new JButton("Add Item");
		ItemButton.setBounds(311, 240, 207, 23);
		ItemButton.addActionListener(this);
		Panel.add(ItemButton);

		ProductField = new JTextField();
		ProductField.setBounds(388, 186, 187, 20);
		Panel.add(ProductField);
		ProductField.setColumns(10);

		CostField = new JTextField();
		CostField.setBounds(388, 209, 187, 20);
		Panel.add(CostField);
		CostField.setColumns(10);

		Frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Frame.addWindowListener(new java.awt.event.WindowAdapter()  // to give user a option if sure to close the whole app
        {
 	      @Override
 	      public void windowClosing(java.awt.event.WindowEvent windowEvent)
 	       {
 	        if( JOptionPane.showConfirmDialog(null, "Do you want to exit this application ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION )
 	    	   {
 	        	System.exit(0);
 	    	   }
 	       }
 	    });
		Frame .setSize(800, 600);
		Frame.setLocation(250,50);
		Frame.setVisible(true);
		Frame.setResizable(false);

	}



	@Override
	public void actionPerformed(ActionEvent AE)
	{


		   if(AE.getSource()==RB)
		   {
			   if(TempControll.ConnectionManagerObject.createConnection())
				{
					ShowButton.setEnabled(true);
					DateCombo.setEnabled(true);
					Vector<String> Dates = TempControll.DataEntryObject.getEnrolledDates();
					DateCombo.removeAllItems();
					for(String date : Dates)
						DateCombo.addItem(date);

				}
		   }
		   if(AE.getSource()==SetUserButton)
			{

			    String Username,Password;
				Username = User.getText();
				Password = Pass.getText();
				if(Username!=null && Password!=null)
					TempControll.ConnectionManagerObject.setUserPass(Username, Password);
				User.setText("");
				Pass.setText("");
				JOptionPane.showMessageDialog(Frame, "User has been set for MySQL database management system.","Success", JOptionPane.INFORMATION_MESSAGE);

			}
			if(AE.getSource()==EntryButton)
			{

				if(TempControll.ConnectionManagerObject.createConnection())
					if(ItemCombo.getSelectedIndex()>=0)
					  {
						int Number = Integer.parseInt(ItemCombo.getSelectedItem().toString());
						TempControll.EntryDialogObject.createEntryDialog(Number);
					  }
			}

			if(AE.getSource()==ShowButton)
			{

				if(TempControll.ConnectionManagerObject.createConnection())
				{
					if(DateCombo.getSelectedIndex()>=0)
					  {
						String Date = DateCombo.getSelectedItem().toString();
						TempControll.DataEntryObject.showResult(Date);
						int Size = DataTransfer.Items.size();
						if(Size>0)
						  {
							TempControll.DailySalesDialogObject.createDailySalesDialog(Size);
						  }
						else
						  {
							JOptionPane.showMessageDialog(Frame, "No data found for selected date.", "No Data !!", JOptionPane.ERROR_MESSAGE);
						  }

					  }
					else
					  JOptionPane.showMessageDialog(Frame, "Make Sure Data is available is Date Field.", "No Date Found !!", JOptionPane.ERROR_MESSAGE);
				}
			}

			if(AE.getSource()==ItemButton)
			{
				if(TempControll.ConnectionManagerObject.createConnection())
				{
					String ID = ProductField.getText();
					String Cost = CostField.getText();
					if( !ID.equals("") && !Cost.equals("") )
					{
						if(!((Pattern.matches("([0-9]*)\\.([0-9]*)", Cost)||Pattern.matches("([0-9]*)",Cost)) && !Cost.equals(".")&& !Cost.equals("")))
							JOptionPane.showMessageDialog(Frame, "Please, check all data are valid.", "Invalid Data", JOptionPane.ERROR_MESSAGE);
						else
						{
							if(TempControll.DataEntryObject.hasItem(ID))
								JOptionPane.showMessageDialog(Frame, "Can't add this item.", "Error", JOptionPane.ERROR_MESSAGE);
							else
							{
								if(TempControll.DataEntryObject.addItem(ID, Cost) )
								{
									ProductField.setText("");
									CostField.setText("");
									JOptionPane.showMessageDialog(Frame, "Item added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
								}
							}
						}
					}
					else
					{
						JOptionPane.showMessageDialog(Frame, "Complete Both Field.", "Empty Field", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

	}

	private void setLookAndFeel()
	{
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		    }
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
	}
}
