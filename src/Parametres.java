
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class Parametres extends JPanel
{
	//Les LABELS: Pour indiquer quelle information à entrer
	protected JLabel puissanceLabel, puissance2Label;  
	protected JLabel minXLabel,minYLabel, minZLabel, maxXLabel, maxYLabel, maxZLabel;
	protected JLabel rotXLabel, rotYLabel, rotZLabel;
	
	//LES TEXTFIELD: Pour écrire les infos
	protected JTextField puissanceJTF, puissance2JTF;
	protected JTextField minXJTF, minYJTF, minZJTF, maxXJTF, maxYJTF, maxZJTF;
	protected JTextField rotXJTF, rotYJTF, rotZJTF;
	
	// LES CHECKBOX
	protected JCheckBox rotCB;
	
	// Constante
	int JTFH = 25;
	int JTFL = 60;
	
	// Decimal format
	protected static DecimalFormat df = new DecimalFormat("#.####");
	
	public Parametres()
	{
		this.setPreferredSize(new Dimension(335,385));
		//this.setBackground(Color.GREEN);
		
		//Initialisation des JLabel
		initLabel();
		
		
		//Initialisation des JtextField
		initJTF();
		
		//Destination des Labels et JPanel
		initPosComponent();
				
	}
	
	// MÉthode getteur
	
	public int getPower() throws PowerException
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(puissanceJTF.getText());
		int power = scan.nextInt();
		if (power < 2)
			throw new PowerException("First power must be greater than 2");
		return power;
	}
	
	public int getSecondPower() throws PowerException
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(puissance2JTF.getText() + " " + puissanceJTF.getText());
		int power = getPower();
		int power2 = scan.nextInt();
		if (power2 < 2)
			throw new PowerException("Second power must be greater than 2");
		if (power > power2)
			throw new PowerException("First power must be less or equals to the second power");
		return power2;
	}
	
	public double[] getAxes() throws SameAxesValuesException
	{
		String argumentsAxes = minXJTF.getText() + "  " + maxXJTF.getText() + 
				" " + minYJTF.getText() + " " + maxYJTF.getText() +
				" " + minZJTF.getText() + " " + maxZJTF.getText();
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(argumentsAxes);
		
		double[] axes = new double[6];
		
		// minX et maxX
		axes[0] = scan.nextDouble();
		axes[1] = scan.nextDouble();
		if (axes[0] == axes[1])
			throw new SameAxesValuesException("Same value on the x axis");
		//minY et maxY
		axes[2] = scan.nextDouble();
		axes[3] = scan.nextDouble();
		if (axes[2] == axes[3])
			throw new SameAxesValuesException("Same value on the y axis");
		
		// minZ et maxZ
		axes[4] = scan.nextDouble();
		axes[5] = scan.nextDouble();
		if (axes[4] == axes[5])
			throw new SameAxesValuesException("Same value on the z axis");
		
		return axes;
	}
	
	public double[] getRotationValues()
	{
		double[] rotationValues = new double[3];
		String rotation = rotXJTF.getText() + " " + rotYJTF.getText() + 
				" " + rotZJTF.getText();
		Scanner scan = new Scanner(rotation);
		
		rotationValues[0] = scan.nextDouble();
		rotationValues[1] = scan.nextDouble();
		rotationValues[2] = scan.nextDouble();
		
		return rotationValues;
	}
	
	public void setEnableRotation(boolean etat)
	{
		rotXLabel.setEnabled(etat);
		rotYLabel.setEnabled(etat);
		rotZLabel.setEnabled(etat);
		rotXJTF.setEnabled(etat);
		rotYJTF.setEnabled(etat);
		rotZJTF.setEnabled(etat);
		rotXJTF.setText("0");
		rotYJTF.setText("0");
		rotZJTF.setText("0");
	}
	
	// MÉthode pour initialiser les Labels
	public void initLabel()
	{
		puissanceLabel = new JLabel("Power : ");
		puissance2Label = new JLabel(" to ");
		minXLabel = new JLabel("minX: ");
		minYLabel = new JLabel("minY: ");
		minZLabel = new JLabel("minZ: ");
		maxXLabel = new JLabel("maxX: ");
		maxYLabel = new JLabel("maxY: ");
		maxZLabel = new JLabel("maxZ: ");
		rotXLabel = new JLabel("Rotate from X axis: ");
		rotYLabel = new JLabel("Rotate from Y axis: ");
		rotZLabel = new JLabel("Rotate from Z axis: ");
	}
	
	// MÉthode pour initialiser les JtextFields
	public void initJTF()
	{
		//Puissance JTF
		puissanceJTF = new JTextField("2");
		puissanceJTF.setPreferredSize(new Dimension(JTFL,JTFH));
		
		puissance2JTF = new JTextField("2");
		puissance2JTF.setPreferredSize(new Dimension(JTFL, JTFH));
		puissance2JTF.setEnabled(true);
		
		// AXES JTEXTFIELD (JTF)
		minXJTF = new JTextField("-2");
		minXJTF.setPreferredSize(new Dimension(JTFL,JTFH));
		minYJTF = new JTextField("-2");
		minYJTF.setPreferredSize(new Dimension(JTFL,JTFH));
		minZJTF = new JTextField("-2");
		minZJTF.setPreferredSize(new Dimension(JTFL,JTFH));
		maxXJTF = new JTextField("2");
		maxXJTF.setPreferredSize(new Dimension(JTFL,JTFH));
		maxYJTF = new JTextField("2");
		maxYJTF.setPreferredSize(new Dimension(JTFL,JTFH));
		maxZJTF = new JTextField("2");
		maxZJTF.setPreferredSize(new Dimension(JTFL,JTFH));
		
		// Rotation JTF
		rotXJTF = new JTextField("0");
		rotXJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		rotXJTF.setEnabled(false);
		rotYJTF = new JTextField("0");
		rotYJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		rotYJTF.setEnabled(false);
		rotZJTF = new JTextField("0");
		rotZJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		rotZJTF.setEnabled(false);
	}
	//Méthode pour initialiser le positionnement des composants
	public void initPosComponent()
	{
		TitledBorder title;
			
		// La puissance
		JPanel puissancePanel = new JPanel(new GridLayout(1,2));
		puissancePanel.add(puissanceLabel);
		puissancePanel.add(puissanceJTF);
		puissancePanel.add(puissance2Label);
		puissancePanel.add(puissance2JTF);
		this.add(puissancePanel);
			
		//Les axes
		JPanel axePanel = new JPanel(new GridLayout(3,1));
		title = BorderFactory.createTitledBorder("Axes");
		axePanel.setBorder(title);
			
			//Axe des X
			JPanel axeXPanel = new JPanel();
			axeXPanel.add(minXLabel);
			axeXPanel.add(minXJTF);
			axeXPanel.add(maxXLabel);
			axeXPanel.add(maxXJTF);
			axePanel.add(axeXPanel);
				
			//Axe des Y
			JPanel axeYPanel = new JPanel();
			axeYPanel.add(minYLabel);
			axeYPanel.add(minYJTF);
			axeYPanel.add(maxYLabel);
			axeYPanel.add(maxYJTF);
			axePanel.add(axeYPanel);
				
			//Axe des Z
			JPanel axeZPanel = new JPanel();
			axeZPanel.add(minZLabel);
			axeZPanel.add(minZJTF);
			axeZPanel.add(maxZLabel);
			axeZPanel.add(maxZJTF);
			axePanel.add(axeZPanel);
				
		this.add(axePanel);
		
		// Les rotations selon X, Y et Z 
		JPanel rotationPanel = new JPanel(new GridLayout(3,1));
		title = BorderFactory.createTitledBorder("Rotations (in degree)");
		rotationPanel.setBorder(title);
			
			// LE CheckBox
			/*rotCB = new JCheckBox("Enable Rotation");
			rotCB.addActionListener(new EnableRotationActionListener());
			rotationPanel.add(rotCB);*/
			
			// Rotation selon axe X
			JPanel rotXPanel = new JPanel();
			rotXPanel.add(rotXLabel);
			rotXPanel.add(rotXJTF);
			rotationPanel.add(rotXPanel);
			
			// Rotation selon axe des y
			JPanel rotYPanel = new JPanel();
			rotYPanel.add(rotYLabel);
			rotYPanel.add(rotYJTF);
			rotationPanel.add(rotYPanel);
			
			// Rotation selon axe des z
			JPanel rotZPanel = new JPanel();
			rotZPanel.add(rotZLabel);
			rotZPanel.add(rotZJTF);
			rotationPanel.add(rotZPanel);
		
		this.add(rotationPanel);
	}
	
	// Setteur
	public void setEnabledPower2(boolean etat)
	{
		puissance2JTF.setEnabled(etat);
	}
	
	public void setAxesJTF() throws PowerException
	{
		int p = this.getPower();
		double valuepos = Math.pow(2,(double)1/(p-1));
		double valueneg = -valuepos;
		DecimalFormat dfm = new DecimalFormat("#.####");
		
		minXJTF.setText(dfm.format(valueneg));
		maxXJTF.setText(dfm.format(valuepos));
		minYJTF.setText(dfm.format(valueneg));
		maxYJTF.setText(dfm.format(valuepos));
		minZJTF.setText(dfm.format(valueneg));
		maxZJTF.setText(dfm.format(valuepos));
	}
	
	public void setEnabledRotation(boolean etat)
	{
		rotXJTF.setEnabled(etat);
		rotYJTF.setEnabled(etat);
		rotZJTF.setEnabled(etat);
		if (!etat)
		{
			rotXJTF.setText("0");
			rotYJTF.setText("0");
			rotZJTF.setText("0");
		}
	}
	
	//Méthode toString()
	public String toString()
	{
		String unString;
		unString = "Power: " + puissanceJTF.getText() + "\n";
		unString = unString + "Axes :\n";
		unString = unString + "minX = " + minXJTF.getText() + "\t" + "maxX = " + maxXJTF.getText() + "\n";
		unString = unString + "minY = " + minYJTF.getText() + "\t" + "maxY = " + maxYJTF.getText() + "\n";
		unString = unString + "minZ = " + minZJTF.getText() + "\t" + "maxZ = " + maxZJTF.getText() + "\n";
		unString = unString + "Rotation :\n";
		unString = unString + "rotX =" + rotXJTF.getText() + "; rotY = " + rotYJTF.getText() + "; rotZ = " + rotZJTF.getText() + "\n";
		return unString;
	}
	
	class EnableRotationActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try 
			{
				double[] axes = getAxes();
				if(axes[0] != -axes[1] || axes[2] != -axes[3] || axes[4] != -axes[5])
					setEnabledRotation(false);
				else
					setEnabledRotation(true);
			} 
			catch (SameAxesValuesException err) 
			{
				// TODO Auto-generated catch block
				err.afficherMessageErreur();
			}
		}
	}
}
