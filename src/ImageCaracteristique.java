import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class ImageCaracteristique extends JPanel
{
	// Les JLabel: Pour indiquer les information à entrer
	protected JLabel dimensionLabel;
	protected JLabel CoucheMinLabel, CoucheMaxLabel;
	protected JLabel styleLabel;
	protected JLabel lightLabelX, lightLabelY, lightLabelZ;
	protected JLabel epsLabel;
	
	// Les JTextFields: Pour entrer des valeurs numériques à des
	// composantes
	protected JTextField dimensionJTF;
	protected JTextField CoucheMaxJTF, CoucheMinJTF;
	protected JTextField pourcentageIntJTF;
	protected JTextField reelJTF, im1JTF, im2JTF, im3JTF, im4JTF,
		hyp1JTF, hyp2JTF, hyp3JTF;
	protected JTextField e1ReFirstJTF, e1ImFirstJTF, e2ReFirstJTF, e2ImFirstJTF, e3ReFirstJTF, e3ImFirstJTF, e4ReFirstJTF, e4ImFirstJTF;
	protected JTextField lightXJTF, lightYJTF, lightZJTF;
	protected JTextField epsJTF;
	
	//Un JRadioButton: Pour le style de la coupe à choisir selon la façon de tracer l'image, avec des rainures ou avec le théorèmes de Fatou-Julia généralisé
	protected JRadioButton styleRayeJRB;
	protected JRadioButton styleJuliaJRB;
	protected ButtonGroup styleBG;
	
	// Les CheckBox: Pour cocher les composantes
	protected JCheckBox reelCB, im1CB, im2CB, im3CB, im4CB,
		hyp1CB, hyp2CB, hyp3CB;
	protected JCheckBox e1ReFirstCB, e1ImFirstCB, e2ReFirstCB, e2ImFirstCB, e3ReFirstCB, e3ImFirstCB, e4ReFirstCB, e4ImFirstCB;
	protected JRadioButton realFirstRB, idempFirstRB;
	
	//Un CheckBox: Pour indiquer si on veut voir l'intérieur ou pas de l'ensemble
	protected JCheckBox interieurCB, grisCB;
	
	// Constante pour la classe
	protected int JTFH = 25;
	protected int JTFL = 60;
	
	// Titre pour les BORDER
	protected TitledBorder title;
	
	// Panels essentiels
	protected JPanel caracteristicPanel, composantPanel, composantIdempFirstPanel, dimensionPanel, rotationPanel;
	protected JScrollPane scrollCaracteristicPanel, scrollcomposantPanel;
	
	public ImageCaracteristique()
	{
		this.setPreferredSize(new Dimension(335,335));
		this.setLayout(new GridLayout(2,1));
		
		// Initialisation des JLabel
		dimensionLabel = new JLabel("Dimensions (width, heigth, depth): ");
		CoucheMinLabel = new JLabel("Iter. min: ");
		CoucheMaxLabel = new JLabel("Iter. max: ");
		styleLabel = new JLabel("Image Look: ");
		
		lightLabelX = new JLabel("Light X : ");
		lightLabelY = new JLabel("Light Y : ");
		lightLabelZ = new JLabel("Light Z : ");
		
		epsLabel = new JLabel("Distance Maximale :");
		

		composantIdempFirstPanel = new JPanel(new GridLayout(4,2));
		title = BorderFactory.createTitledBorder("3D Idemp. Components");
		composantIdempFirstPanel.setBorder(title);
		
		// Initialisation des JTextField
		initJTF();
		
		// Initialisation des checkboxes
		initCheckBox();
		
		// Positionnement des composants
		initPosComponent();
		
		// Ajout des Panels principaux
		this.add(scrollCaracteristicPanel);
		//this.add(composantPanel);
		this.add(scrollcomposantPanel);
	}
	
	// Méthodes getteur
	public int[] getDimension()
	{
		int[] dimension = new int[3];
		String desIntegers = dimensionJTF.getText();
		Scanner scan = new Scanner(desIntegers);
		
		// largeur
		dimension[0] = scan.nextInt();
		
		//hauteur
		dimension[1] = dimension[0];
		
		//profondeur
		dimension[2] = dimension[0];
		
		return dimension;
	}
	
	public int getCoucheMin()
	{
		Scanner scan = new Scanner(CoucheMinJTF.getText());
		return scan.nextInt();
	}
	
	public int getCoucheMax()
	{
		Scanner scan = new Scanner(CoucheMaxJTF.getText());
		return scan.nextInt();
	}
	
	public int getInteriorPoucentage()
	{
		Scanner scan = new Scanner(pourcentageIntJTF.getText());
		return scan.nextInt();
	}
	
	public boolean[] getComposantSelected()
	{
		boolean[] choixCoupe = new boolean[8];
		if (realFirstRB.isSelected())
		{
			choixCoupe[0] = reelCB.isSelected();
			choixCoupe[1] = im1CB.isSelected();
			choixCoupe[2] = im2CB.isSelected();
			choixCoupe[3] = im3CB.isSelected();
			choixCoupe[4] = im4CB.isSelected();
			choixCoupe[5] = hyp1CB.isSelected();
			choixCoupe[6] = hyp2CB.isSelected();
			choixCoupe[7] = hyp3CB.isSelected();
		
			return choixCoupe;
		}
		else
		{
			choixCoupe[0] = e1ReFirstCB.isSelected();
			choixCoupe[1] = e1ImFirstCB.isSelected();
			choixCoupe[2] = e2ReFirstCB.isSelected();
			choixCoupe[3] = e2ImFirstCB.isSelected();
			choixCoupe[4] = e3ReFirstCB.isSelected();
			choixCoupe[5] = e3ImFirstCB.isSelected();
			choixCoupe[6] = e4ReFirstCB.isSelected();
			choixCoupe[7] = e4ImFirstCB.isSelected();
			return choixCoupe;
		}
	}
	
	public boolean[] getComposantIdempSelected()
	{
		boolean[] choixCoupe = new boolean[8];
		choixCoupe[0] = e1ReFirstCB.isSelected();
		choixCoupe[1] = e1ImFirstCB.isSelected();
		choixCoupe[2] = e2ReFirstCB.isSelected();
		choixCoupe[3] = e2ImFirstCB.isSelected();
		choixCoupe[4] = e3ReFirstCB.isSelected();
		choixCoupe[5] = e3ImFirstCB.isSelected();
		choixCoupe[6] = e4ReFirstCB.isSelected();
		choixCoupe[7] = e4ImFirstCB.isSelected();
		return choixCoupe;
	}
	
	public double[] getLightComponentVector()
	{
		double[] light = new double[3];
		
		light[0] = Double.parseDouble(lightXJTF.getText());
		light[1] = Double.parseDouble(lightYJTF.getText());
		light[2] = Double.parseDouble(lightZJTF.getText());
		
		return light;
	}
	
	public double getEpsilonDist()
	{
		return Double.parseDouble(epsJTF.getText());
	}
	
	public double[] getComposantValue()
	{
		double[] values = new double[8];
		if (realFirstRB.isSelected())
		{
			String desDoubles = reelJTF.getText() + " " + im1JTF.getText() + 
				" " + im2JTF.getText() + " " + im3JTF.getText() + 
				" " + im4JTF.getText() + " " + hyp1JTF.getText() + " "
				+ hyp2JTF.getText() + " " + hyp3JTF.getText();
			Scanner scan = new Scanner(desDoubles);
			
			//real part
			values[0] = Double.parseDouble(reelJTF.getText());
			//im1 part
			values[1] = Double.parseDouble(im1JTF.getText());
			//im2 part
			values[2] = Double.parseDouble(im2JTF.getText());
			//im3 part
			values[3] = Double.parseDouble(im3JTF.getText());
			//im4 part
			values[4] = Double.parseDouble(im4JTF.getText());
			//hyp1 part
			values[5] = Double.parseDouble(hyp1JTF.getText());
			//hyp2 part
			values[6] = Double.parseDouble(hyp2JTF.getText());
			//hyp3 part
			values[7] = Double.parseDouble(hyp3JTF.getText());
		
			return values;
		}
		else
		{
			values[0] = Double.parseDouble(e1ReFirstJTF.getText());
			values[1] = Double.parseDouble(e1ImFirstJTF.getText());
			values[2] = Double.parseDouble(e2ReFirstJTF.getText());
			values[3] = Double.parseDouble(e2ImFirstJTF.getText());
			values[4] = Double.parseDouble(e3ReFirstJTF.getText());
			values[5] = Double.parseDouble(e3ImFirstJTF.getText());
			values[6] = Double.parseDouble(e4ReFirstJTF.getText());
			values[7] = Double.parseDouble(e4ImFirstJTF.getText());
			
			return values;
		}
	}
	
	public double[] getComposantIdempValue()
	{
		double[] values = new double[8];
		
		values[0] = Double.parseDouble(e1ReFirstJTF.getText());
		values[1] = Double.parseDouble(e1ImFirstJTF.getText());
		values[2] = Double.parseDouble(e2ReFirstJTF.getText());
		values[3] = Double.parseDouble(e2ImFirstJTF.getText());
		values[4] = Double.parseDouble(e3ReFirstJTF.getText());
		values[5] = Double.parseDouble(e3ImFirstJTF.getText());
		values[6] = Double.parseDouble(e4ReFirstJTF.getText());
		values[7] = Double.parseDouble(e4ImFirstJTF.getText());
		
		return values;
	}
	
	public double[] getComposant()
	{
		if (realFirstRB.isSelected())
		{
			return getComposantValue();
		}
		else
		{
			return getComposantIdempValue();
		}
	}
	
	public int getTypeOfSlice()
	{
		if (realFirstRB.isSelected())
		{
			return 1;
		}
		else
			return 2;
	}
	
	public boolean styleJuliaSelected()
	{
		return styleJuliaJRB.isSelected();
	}
	
	public boolean styleRayeSelected()
	{
		return styleRayeJRB.isSelected();
	}
	
	//Méthode setteur
	public void setVisibilityStyle(boolean access)
	{
		styleJuliaJRB.setEnabled(access);
		styleRayeJRB.setEnabled(access);
	}
	
	public void setVisibilityInterior(boolean access)
	{
		interieurCB.setEnabled(access);
	}
	
	//Classe pour écouter le radioButton intérieur
	class CheckBoxListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (realFirstRB.isSelected())
			{	
				int count = getCount();
				if (count > 3)
				{
					JOptionPane jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "Vous ne pouvez cocher que 3 composantes" , "ERREUR", JOptionPane.ERROR_MESSAGE);
					JCheckBox objectSelected = (JCheckBox) e.getSource();
					objectSelected.setSelected(false);
					refreshJTF(); // refresh des JTF
				}
			}
			else
			{
				int count = getCountIdemp();
				refreshJTFFirstIdemp();
				if (count > 3)
				{
					JOptionPane jOp1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "Vous ne pouvez cocher que 3 composantes" , "ERREUR", JOptionPane.ERROR_MESSAGE);
					JCheckBox objectSelected = (JCheckBox) e.getSource();
					objectSelected.setSelected(false);
					refreshJTFFirstIdemp();
				}
				
			}
		}
	}
	
	// Fonction permettant d'obtenir le nombre de checkBox
	// cochés
	public int getCount()
	{
		int count = 0;
		if (reelCB.isSelected())
		{
			reelJTF.setEnabled(false);
			reelJTF.setText("0");
			count++;
		}
		else
		{
			reelJTF.setEnabled(true);
		}
			
		if (im1CB.isSelected())
		{
			im1JTF.setEnabled(false);
			im1JTF.setText("0");
			count++;
		}
		else
		{
			im1JTF.setEnabled(true);
		}
		
		if (im2CB.isSelected())
		{
			im2JTF.setEnabled(false);
			im2JTF.setText("0");
			count++;
		}
		else
		{
			im2JTF.setEnabled(true);
		}
		
		if (im3CB.isSelected())
		{
			im3JTF.setEnabled(false);
			im3JTF.setText("0");
			count++;
		}
		else
		{
			im3JTF.setEnabled(true);
		}
		if (im4CB.isSelected())
		{
			im4JTF.setEnabled(false);
			im4JTF.setText("0");
			count++;
		}
		else
		{
			im4JTF.setEnabled(true);
		}
		
		if (hyp1CB.isSelected())
		{
			hyp1JTF.setEnabled(false);
			hyp1JTF.setText("0");
			count++;
		}
		else
		{
			hyp1JTF.setEnabled(true);
		}
		
		if (hyp2CB.isSelected())
		{
			hyp2JTF.setEnabled(false);
			hyp2JTF.setText("0");
			count++;
		}
		else
		{
			hyp2JTF.setEnabled(true);
		}
		
		if (hyp3CB.isSelected())
		{
			hyp3JTF.setEnabled(false);
			hyp3JTF.setText("0");
			count++;
		}
		else
		{
			hyp3JTF.setEnabled(true);
		}
		return count;
	}
	
	public int getCountIdemp()
	{
		int count = 0;
		if (e1ReFirstCB.isSelected())
		{
			count++;
		}
			
		if( e1ImFirstCB.isSelected())
		{
			count++;
		}
		
		if (e2ReFirstCB.isSelected())
		{
			count++;
		}
		
		if (e2ImFirstCB.isSelected())
		{
			count++;
		}

		if (e3ReFirstCB.isSelected())
		{
			count++;
		}
		
		if (e3ImFirstCB.isSelected())
		{
			count++;
		}
		
		if (e4ReFirstCB.isSelected())
		{
			count++;
		}
		
		if (e4ImFirstCB.isSelected())
		{
			count++;
		}
		return count;
	}
	
	// MÉthode qui permet de rafraîchir l'état des JTF
	public void refreshJTF()
	{
		if (reelCB.isSelected())
		{
			reelJTF.setEnabled(false);
			reelJTF.setText("0");
		}
		else
		{
			reelJTF.setEnabled(true);
		}
			
		if (im1CB.isSelected())
		{
			im1JTF.setEnabled(false);
			im1JTF.setText("0");
		}
		else
		{
			im1JTF.setEnabled(true);
		}
		
		if (im2CB.isSelected())
		{
			im2JTF.setEnabled(false);
			im2JTF.setText("0");
		}
		else
		{
			im2JTF.setEnabled(true);
		}
		
		if (im3CB.isSelected())
		{
			im3JTF.setEnabled(false);
			im3JTF.setText("0");
		}
		else
		{
			im3JTF.setEnabled(true);
		}
		if (im4CB.isSelected())
		{
			im4JTF.setEnabled(false);
			im4JTF.setText("0");
		}
		else
		{
			im4JTF.setEnabled(true);
		}
		
		if (hyp1CB.isSelected())
		{
			hyp1JTF.setEnabled(false);
			hyp1JTF.setText("0");
		}
		else
		{
			hyp1JTF.setEnabled(true);
		}
		
		if (hyp2CB.isSelected())
		{
			hyp2JTF.setEnabled(false);
			hyp2JTF.setText("0");
		}
		else
		{
			hyp2JTF.setEnabled(true);
		}
		
		if (hyp3CB.isSelected())
		{
			hyp3JTF.setEnabled(false);
			hyp3JTF.setText("0");
		}
		else
		{
			hyp3JTF.setEnabled(true);
		}
		
	}
	
	public void refreshJTFFirstIdemp()
	{
		if (e1ReFirstCB.isSelected())
		{
			e1ReFirstJTF.setEnabled(false);
			e1ReFirstJTF.setText("0");
		}
		else
		{
			e1ReFirstJTF.setEnabled(true);
		}
			
		if (e1ImFirstCB.isSelected())
		{
			e1ImFirstJTF.setEnabled(false);
			e1ImFirstJTF.setText("0");
		}
		else
		{
			e1ImFirstJTF.setEnabled(true);
		}
		
		if (e2ReFirstCB.isSelected())
		{
			e2ReFirstJTF.setEnabled(false);
			e2ReFirstJTF.setText("0");
		}
		else
		{
			e2ReFirstJTF.setEnabled(true);
		}
		
		if (e2ImFirstCB.isSelected())
		{
			e2ImFirstJTF.setEnabled(false);
			e2ImFirstJTF.setText("0");
		}
		else
		{
			e2ImFirstJTF.setEnabled(true);
		}
		if (e3ReFirstCB.isSelected())
		{
			e3ReFirstJTF.setEnabled(false);
			e3ReFirstJTF.setText("0");
		}
		else
		{
			e3ReFirstJTF.setEnabled(true);
		}
		
		if (e3ImFirstCB.isSelected())
		{
			e3ImFirstJTF.setEnabled(false);
			e3ImFirstJTF.setText("0");
		}
		else
		{
			e3ImFirstJTF.setEnabled(true);
		}
		
		if (e4ReFirstCB.isSelected())
		{
			e4ReFirstJTF.setEnabled(false);
			e4ReFirstJTF.setText("0");
		}
		else
		{
			e4ReFirstJTF.setEnabled(true);
		}
		
		if (e4ImFirstCB.isSelected())
		{
			e4ImFirstJTF.setEnabled(false);
			e4ImFirstJTF.setText("0");
		}
		else
		{
			e4ImFirstJTF.setEnabled(true);
		}
		
	}
	
	// Méthode pour position les composants
	public void initPosComponent()
	{
		caracteristicPanel = new JPanel(new GridLayout(6,1));
		title = BorderFactory.createTitledBorder("Image Characteristics");
		caracteristicPanel.setBorder(title);
		
		// Les dimensions de l'image
		dimensionPanel = new JPanel();
		dimensionPanel.add(dimensionLabel);
		dimensionPanel.add(dimensionJTF);
		
		caracteristicPanel.add(dimensionPanel);
		
		//Le Style
		JPanel stylePanel = new JPanel();
		styleRayeJRB = new JRadioButton("Lace");
				
		styleJuliaJRB = new JRadioButton("JF Theorem");
		styleJuliaJRB.setSelected(true);
			
		styleBG = new ButtonGroup();//On regroupe les  radioBut ensembles
		styleBG.add(styleRayeJRB);
		styleBG.add(styleJuliaJRB);
			
		stylePanel.add(styleLabel);
		stylePanel.add(styleRayeJRB);
		stylePanel.add(styleJuliaJRB);
		
		caracteristicPanel.add(stylePanel);
		
		//Couches minimales et maximales
		JPanel couchePanel = new JPanel(new GridLayout(1,3));
				
			//Couche minimum
			JPanel coucheMinPanel = new JPanel();
			coucheMinPanel.add(CoucheMinLabel);
			coucheMinPanel.add(CoucheMinJTF);
			couchePanel.add(coucheMinPanel);
					
			//Couche maximum
			JPanel coucheMaxPanel = new JPanel();
			coucheMaxPanel.add(CoucheMaxLabel);
			coucheMaxPanel.add(CoucheMaxJTF);
			couchePanel.add(coucheMaxPanel);
			
			caracteristicPanel.add(couchePanel);
		
		//Pourcentage
		JPanel interieurPanel = new JPanel();
		interieurCB = new JCheckBox("% interior view: ");
		interieurCB.addActionListener(new IntCheckBoxListener());
			
		pourcentageIntJTF.setEnabled(false);
		// Gris
		interieurPanel.add(grisCB);
		interieurPanel.add(interieurCB);
		interieurPanel.add(pourcentageIntJTF);
		caracteristicPanel.add(interieurPanel);
		
		// Light
		JPanel lightPanel = new JPanel(new GridLayout(1,3));
		
			JPanel lightXPanel = new JPanel();
			lightXPanel.add(lightLabelX);
			lightXPanel.add(lightXJTF);
			
			JPanel lightYPanel = new JPanel();
			lightYPanel.add(lightLabelY);
			lightYPanel.add(lightYJTF);
			
			JPanel lightZPanel = new JPanel();
			lightZPanel.add(lightLabelZ);
			lightZPanel.add(lightZJTF);
			
		lightPanel.add(lightXPanel);
		lightPanel.add(lightYPanel);
		lightPanel.add(lightZPanel);
		
		caracteristicPanel.add(lightPanel);
		
		// Espilon Distance minimal Panel
		JPanel espPanel = new JPanel();
		espPanel.add(epsLabel);
		espPanel.add(epsJTF);
		
		caracteristicPanel.add(espPanel);
		
		scrollCaracteristicPanel = new JScrollPane(caracteristicPanel);
		
		// Les composantes de la coupe 3D
		composantPanel = new JPanel(new GridLayout(4,2));
		title = BorderFactory.createTitledBorder("3D Components");
		composantPanel.setBorder(title);
		
		JPanel reelPanel = new JPanel();
		reelPanel.add(reelCB);
		reelPanel.add(reelJTF);
		composantPanel.add(reelPanel);
		
		JPanel im4Panel = new JPanel();
		im4Panel.add(im4CB);
		im4Panel.add(im4JTF);
		composantPanel.add(im4Panel);
				
		JPanel im1Panel = new JPanel();
		im1Panel.add(im1CB);
		im1Panel.add(im1JTF);
		composantPanel.add(im1Panel);
		
		JPanel hyp1Panel = new JPanel();
		hyp1Panel.add(hyp1CB);
		hyp1Panel.add(hyp1JTF);
		composantPanel.add(hyp1Panel);
		
		JPanel im2Panel = new JPanel();
		im2Panel.add(im2CB);
		im2Panel.add(im2JTF);
		composantPanel.add(im2Panel);
			
		JPanel hyp2Panel = new JPanel();
		hyp2Panel.add(hyp2CB);
		hyp2Panel.add(hyp2JTF);
		composantPanel.add(hyp2Panel);
		
		JPanel im3Panel = new JPanel();
		im3Panel.add(im3CB);
		im3Panel.add(im3JTF);
		composantPanel.add(im3Panel);
		
		JPanel hyp3Panel = new JPanel();
		hyp3Panel.add(hyp3CB);
		hyp3Panel.add(hyp3JTF);
		composantPanel.add(hyp3Panel);
				
		JPanel e1ReFirstPanel = new JPanel();
		e1ReFirstPanel.add(e1ReFirstCB);
		e1ReFirstPanel.add(e1ReFirstJTF);
		composantIdempFirstPanel.add(e1ReFirstPanel);
		
		JPanel e1ImFirstPanel = new JPanel();
		e1ImFirstPanel.add(e1ImFirstCB);
		e1ImFirstPanel.add(e1ImFirstJTF);
		composantIdempFirstPanel.add(e1ImFirstPanel);
						
		JPanel e2ReFirstPanel = new JPanel();
		e2ReFirstPanel.add(e2ReFirstCB);
		e2ReFirstPanel.add(e2ReFirstJTF);
		composantIdempFirstPanel.add(e2ReFirstPanel);
				
		JPanel e2ImFirstPanel = new JPanel();
		e2ImFirstPanel.add(e2ImFirstCB);
		e2ImFirstPanel.add(e2ImFirstJTF);
		composantIdempFirstPanel.add(e2ImFirstPanel);
				
		JPanel e3ReFirstPanel = new JPanel();
		e3ReFirstPanel.add(e3ReFirstCB);
		e3ReFirstPanel.add(e3ReFirstJTF);
		composantIdempFirstPanel.add(e3ReFirstPanel);
					
		JPanel e3ImFirstPanel = new JPanel();
		e3ImFirstPanel.add(e3ImFirstCB);
		e3ImFirstPanel.add(e3ImFirstJTF);
		composantIdempFirstPanel.add(e3ImFirstPanel);
				
		JPanel e4ReFirstPanel = new JPanel();
		e4ReFirstPanel.add(e4ReFirstCB);
		e4ReFirstPanel.add(e4ReFirstJTF);
		composantIdempFirstPanel.add(e4ReFirstPanel);
				
		JPanel e4ImFirstPanel = new JPanel();
		e4ImFirstPanel.add(e4ImFirstCB);
		e4ImFirstPanel.add(e4ImFirstJTF);
		composantIdempFirstPanel.add(e4ImFirstPanel);
		
		JPanel first3DSlicePanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = c.gridy = 0;
		first3DSlicePanel.add(realFirstRB, c);
		c.gridx = 1;
		c.gridy = 0;
		first3DSlicePanel.add(idempFirstRB,c);
		c.gridx = 0;
		c.gridy = 1;
		first3DSlicePanel.add(composantPanel,c);
		c.gridx = 1;
		c.gridy = 1;
		first3DSlicePanel.add(composantIdempFirstPanel,c);
		
		first3DSlicePanel.add(composantPanel);
		first3DSlicePanel.add(composantIdempFirstPanel);
		
		scrollcomposantPanel = new JScrollPane(first3DSlicePanel);
	}
	
	// Méthode pour initiliser les JTextFields
	public void initJTF()
	{
		dimensionJTF = new JTextField("250");
		dimensionJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		
		CoucheMinJTF = new JTextField("7");
		CoucheMinJTF.setPreferredSize(new Dimension(JTFL,JTFH));
		CoucheMaxJTF = new JTextField("14");
		CoucheMaxJTF.setPreferredSize(new Dimension(JTFL,JTFH));
		pourcentageIntJTF = new JTextField("0");
		pourcentageIntJTF.setPreferredSize(new Dimension(JTFL,JTFH));
		
		lightXJTF = new JTextField("2");
		lightXJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		lightYJTF = new JTextField("0");
		lightYJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		lightZJTF = new JTextField("0");
		lightZJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		
		epsJTF = new JTextField("0.1");
		epsJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		
		reelJTF = new JTextField("0");
		reelJTF.setEnabled(false);
		reelJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		im1JTF = new JTextField("0");
		im1JTF.setEnabled(false);
		im1JTF.setPreferredSize(new Dimension(JTFL, JTFH));
		im2JTF = new JTextField("0");
		im2JTF.setEnabled(false);
		im2JTF.setPreferredSize(new Dimension(JTFL, JTFH));
		im3JTF = new JTextField("0");
		im3JTF.setPreferredSize(new Dimension(JTFL, JTFH));
		im4JTF = new JTextField("0");
		im4JTF.setPreferredSize(new Dimension(JTFL, JTFH));
		hyp1JTF = new JTextField("0");
		hyp1JTF.setPreferredSize(new Dimension(JTFL, JTFH));
		hyp2JTF = new JTextField("0");
		hyp2JTF.setPreferredSize(new Dimension(JTFL, JTFH));
		hyp3JTF = new JTextField("0");
		hyp3JTF.setPreferredSize(new Dimension(JTFL, JTFH));
		
		// LEs composants de la première coupe 3D, en idempotent		
				e1ReFirstJTF = new JTextField("0");
				e1ReFirstJTF.setEnabled(false);
				e1ReFirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e1ImFirstJTF = new JTextField("0");
				e1ImFirstJTF.setEnabled(false);
				e1ImFirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e2ReFirstJTF = new JTextField("0");
				e2ReFirstJTF.setEnabled(false);
				e2ReFirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e2ImFirstJTF = new JTextField("0");
				e2ImFirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e3ReFirstJTF = new JTextField("0");
				e3ReFirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e3ImFirstJTF = new JTextField("0");
				e3ImFirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e4ReFirstJTF = new JTextField("0");
				e4ReFirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e4ImFirstJTF = new JTextField("0");
				e4ImFirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
	}
	
	// Méthode pour initialiser les checkbox
	public void initCheckBox()
	{
		reelCB = new JCheckBox("1: ");
		reelCB.setSelected(true);
		reelCB.addActionListener(new CheckBoxListener());
		
		im1CB = new JCheckBox("i1: ");
		im1CB.setSelected(true);
		im1CB.addActionListener(new CheckBoxListener());
		
		im2CB = new JCheckBox("i2: ");
		im2CB.setSelected(true);
		im2CB.addActionListener(new CheckBoxListener());
		
		im3CB = new JCheckBox("i3: ");
		im3CB.addActionListener(new CheckBoxListener());
		
		im4CB = new JCheckBox("i4: ");
		im4CB.addActionListener(new CheckBoxListener());
		
		hyp1CB = new JCheckBox("j1: ");
		hyp1CB.addActionListener(new CheckBoxListener());
		
		hyp2CB = new JCheckBox("j2: ");
		hyp2CB.addActionListener(new CheckBoxListener());
		
		hyp3CB = new JCheckBox("j3: ");
		hyp3CB.addActionListener(new CheckBoxListener());
		
		e1ReFirstCB = new JCheckBox("e1(Re): ");
		e1ReFirstCB.setSelected(true);
		e1ReFirstCB.addActionListener(new CheckBoxListener());
		
		e1ImFirstCB = new JCheckBox("e1(Im): ");
		e1ImFirstCB.setSelected(true);
		e1ImFirstCB.addActionListener(new CheckBoxListener());
		
		e2ReFirstCB = new JCheckBox("e2(Re): ");
		e2ReFirstCB.setSelected(true);
		e2ReFirstCB.addActionListener(new CheckBoxListener());
		
		e2ImFirstCB = new JCheckBox("e2(Im): ");
		e2ImFirstCB.addActionListener(new CheckBoxListener());
		
		e3ReFirstCB = new JCheckBox("e3(Re): ");
		e3ReFirstCB.addActionListener(new CheckBoxListener());
		
		e3ImFirstCB = new JCheckBox("e3(Im): ");
		e3ImFirstCB.addActionListener(new CheckBoxListener());
		
		e4ReFirstCB = new JCheckBox("e4(Re): ");
		e4ReFirstCB.addActionListener(new CheckBoxListener());
		
		e4ImFirstCB = new JCheckBox("e4(Im): ");
		e4ImFirstCB.addActionListener(new CheckBoxListener());
		
		grisCB = new JCheckBox("Gris");
		grisCB.addActionListener(new GrisActionListener());
		
		composantIdempFirstPanel.setEnabled(false);
		
		e1ReFirstJTF.setEnabled(false);
		e1ImFirstJTF.setEnabled(false);
		e2ReFirstJTF.setEnabled(false);
		e2ImFirstJTF.setEnabled(false);
		e3ReFirstJTF.setEnabled(false);
		e3ImFirstJTF.setEnabled(false);
		e4ReFirstJTF.setEnabled(false);
		e4ImFirstJTF.setEnabled(false);
		
		e1ReFirstCB.setEnabled(false);
		e1ImFirstCB.setEnabled(false);
		e2ReFirstCB.setEnabled(false);
		e2ImFirstCB.setEnabled(false);
		e3ReFirstCB.setEnabled(false);
		e3ImFirstCB.setEnabled(false);
		e4ReFirstCB.setEnabled(false);
		e4ImFirstCB.setEnabled(false);
		
		realFirstRB = new JRadioButton("Real slice");
		realFirstRB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				composantPanel.setEnabled(true);
				
				refreshJTF();
				
				/*reelFirstJTF.setEnabled(false);
				im1FirstJTF.setEnabled(false);
				im2FirstJTF.setEnabled(false);
				im3FirstJTF.setEnabled(true);
				im4FirstJTF.setEnabled(true);
				hyp1FirstJTF.setEnabled(true);
				hyp2FirstJTF.setEnabled(true);
				hyp3FirstJTF.setEnabled(true);
				
				reelFirstJTF.setText("0");
				im1FirstJTF.setText("0");
				im2FirstJTF.setText("0");
				im3FirstJTF.setText("0");
				im4FirstJTF.setText("0");
				hyp1FirstJTF.setText("0");
				hyp2FirstJTF.setText("0");
				hyp3FirstJTF.setText("0");*/
				
				reelCB.setEnabled(true);
				im1CB.setEnabled(true);
				im2CB.setEnabled(true);
				im3CB.setEnabled(true);
				im4CB.setEnabled(true);
				hyp1CB.setEnabled(true);
				hyp2CB.setEnabled(true);
				hyp3CB.setEnabled(true);
				
				composantIdempFirstPanel.setEnabled(false);
				
				e1ReFirstJTF.setEnabled(false);
				e1ImFirstJTF.setEnabled(false);
				e2ReFirstJTF.setEnabled(false);
				e2ImFirstJTF.setEnabled(false);
				e3ReFirstJTF.setEnabled(false);
				e3ImFirstJTF.setEnabled(false);
				e4ReFirstJTF.setEnabled(false);
				e4ImFirstJTF.setEnabled(false);
				
				e1ReFirstJTF.setText("0");
				e1ImFirstJTF.setText("0");
				e2ReFirstJTF.setText("0");
				e2ImFirstJTF.setText("0");
				e3ReFirstJTF.setText("0");
				e3ImFirstJTF.setText("0");
				e4ReFirstJTF.setText("0");
				e4ImFirstJTF.setText("0");
				
				e1ReFirstCB.setEnabled(false);
				e1ImFirstCB.setEnabled(false);
				e2ReFirstCB.setEnabled(false);
				e2ImFirstCB.setEnabled(false);
				e3ReFirstCB.setEnabled(false);
				e3ImFirstCB.setEnabled(false);
				e4ReFirstCB.setEnabled(false);
				e4ImFirstCB.setEnabled(false);
			}
			
		});
		
		idempFirstRB = new JRadioButton("Idemp slice");
		idempFirstRB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				composantPanel.setEnabled(false);
				
				reelJTF.setEnabled(false);
				im1JTF.setEnabled(false);
				im2JTF.setEnabled(false);
				im3JTF.setEnabled(false);
				im4JTF.setEnabled(false);
				hyp1JTF.setEnabled(false);
				hyp2JTF.setEnabled(false);
				hyp3JTF.setEnabled(false);
				
				reelJTF.setText("0");
				im1JTF.setText("0");
				im2JTF.setText("0");
				im3JTF.setText("0");
				im4JTF.setText("0");
				hyp1JTF.setText("0");
				hyp2JTF.setText("0");
				hyp3JTF.setText("0");
				
				reelCB.setEnabled(false);
				im1CB.setEnabled(false);
				im2CB.setEnabled(false);
				im3CB.setEnabled(false);
				im4CB.setEnabled(false);
				hyp1CB.setEnabled(false);
				hyp2CB.setEnabled(false);
				hyp3CB.setEnabled(false);
				
				composantIdempFirstPanel.setEnabled(true);
				
				refreshJTFFirstIdemp();
				
				/*e1ReFirstJTF.setEnabled(false);
				e1ImFirstJTF.setEnabled(false);
				e2ReFirstJTF.setEnabled(false);
				e2ImFirstJTF.setEnabled(true);
				e3ReFirstJTF.setEnabled(true);
				e3ImFirstJTF.setEnabled(true);
				e4ReFirstJTF.setEnabled(true);
				e4ImFirstJTF.setEnabled(true);*/
				
				/*e1ReFirstJTF.setText("0");
				e1ImFirstJTF.setText("0");
				e2ReFirstJTF.setText("0");
				e2ImFirstJTF.setText("0");
				e3ReFirstJTF.setText("0");
				e3ImFirstJTF.setText("0");
				e4ReFirstJTF.setText("0");
				e4ImFirstJTF.setText("0");*/
				
				e1ReFirstCB.setEnabled(true);
				e1ImFirstCB.setEnabled(true);
				e2ReFirstCB.setEnabled(true);
				e2ImFirstCB.setEnabled(true);
				e3ReFirstCB.setEnabled(true);
				e3ImFirstCB.setEnabled(true);
				e4ReFirstCB.setEnabled(true);
				e4ImFirstCB.setEnabled(true);
			}
		});
		realFirstRB.setSelected(true);
		
		ButtonGroup groupFirst = new ButtonGroup();
		groupFirst.add(realFirstRB);
		groupFirst.add(idempFirstRB);
	}
	
	// Méthode toString
	public String toString()
	{
		String unString = "Image Dimensions: \n";
		unString = unString + "Width = " + dimensionJTF.getText() + "; Heigth = " + dimensionJTF.getText() + "; Depth = " + dimensionJTF.getText() + "\n";
		unString = unString + "Rendering algorithm: Fatou-Julia Theorem (JF Theorem)\n";
		unString = unString + "Divergence layers: \n";
		unString = unString + "minimum iteration = " + CoucheMinJTF.getText() + "; maximum iteration: " + CoucheMaxJTF.getText() + "\n";
		unString = unString + "% of interior view = " + pourcentageIntJTF.getText() + "\n";
		unString = unString + "Compenents (* = chose for the 3D slice) :\n";
		unString = unString + "1: " + (reelCB.isSelected()? " *" : reelJTF.getText()) + "; i4: " + (im4CB.isSelected() ? " *" : im4JTF.getText()) + "\n";
		unString = unString + "i1: " + (im1CB.isSelected() ? " *": im1JTF.getText()) + "; j1: " + (hyp1CB.isSelected() ? " *" : hyp1JTF.getText()) + "\n";
		unString = unString + "i2: " + (im2CB.isSelected() ? " *": im2JTF.getText()) + "; j2: " + (hyp2CB.isSelected() ? " *" : hyp2JTF.getText()) + "\n";
		unString = unString + "i3: " + (im3CB.isSelected() ? " *" : im3JTF.getText()) + "; j3: " + (hyp3CB.isSelected() ? " *" : hyp3JTF.getText()) + "\n";
		return unString;
	}

	//Classe pour écouter le radioButton intérieur
	class IntCheckBoxListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(interieurCB.isSelected())
				pourcentageIntJTF.setEnabled(true);
			else
			{
				pourcentageIntJTF.setEnabled(false);
				pourcentageIntJTF.setText("0");
			}
		}
	}
	
	class GrisActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(grisCB.isSelected())
			{
				CoucheMinJTF.setText("" + Tribrot.getMaxIterT());
				CoucheMaxJTF.setText("" + Tribrot.getMaxIterT());
			}
			else
			{
				CoucheMinJTF.setText("7");
				CoucheMaxJTF.setText("14");
			}
		}
	}
}
