
/*
 * 		COMMENTAIRES: *-METTRE LES OPTION GENERATE et QUITTER DANS UNE
 * 					  BARRE DE MENU OKAY
 * 					  -AJOUTER AUSSI UN PANNEAU POU  LA SAUVEGARDE
 * 					  EN DIRECT DES PARAMÈTRES DE L'IMAGE
 * 					  -REMPLACER LE PANNEAU DIMENSIONS PAR UN
 * 					  -PANNEAU PERMETTANT DE MODIFIER LES COUL
 * 					  *-Faire une classe Tribrot qui prend en 
 * 					  paramètre le panneau image et qui le 
 * 					  modifiera en temps et lieu et faire une
 * 					  condition sur Generate dans le menu 
 * 					  pour s'assurer que le processus n'est pas
 * 					  déjà lancé OKAY
 * 					  -Ajouter une exception pour la saisi des données
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;


public class FrameApp extends JFrame
{	
	protected JPanel mainPanel = (JPanel) this.getContentPane();
	protected ImageFrame renderImageFrame;
	
	protected JTabbedPane ongletsPane;
	
	protected Parametres onglet1Param;
	protected ImageCaracteristique onglet2Image;
	protected ParametersSavings onglet3Saves;
	protected Image panelG;
	
	protected JMenuBar menuBar;
	protected JMenu menuFichier, menuAction, menuSauvegarde, menuEdit, menuViewProcess;
	protected JMenuItem menuItemSauvegarderCurrent, menuItemQuitter;
	protected JMenuItem menuItemGenerate;
	protected JMenuItem menuItemRestoration;
	protected JMenuItem menuItemSetAppropriateParameter;
	protected JMenuItem menuItemSettings;
	protected JMenuItem menuItemSauvegardeParameter;
	protected JMenuItem menuItemDoPowerSequence;
	protected JMenuItem menuItemStopGenerating;
	protected JMenuItem menuItemSaveAllImage;
	protected JRadioButtonMenuItem menuItemRBNoViewProcess, menuItemRBPointProcess, menuItemRBSliceProcess,
					menuItemRBLineProcess;
	protected ButtonGroup menuViewProcessButtonGroup;
	protected Tribrot t;
	protected JCheckBoxMenuItem menuItemCBRenderEmplacement;
	
	protected boolean doPowerSequence;
	private JMenuItem menuItemRotate;
	private JCheckBoxMenuItem menuRotateProcess;
	private JMenu menuAddsOn;
	private JCheckBoxMenuItem menuViewAxes;
	private JMenuItem menuItemGenerateOcta;
	private Object menuItenGenerate;
	private JMenuItem menuItemGenerateRotSequence;
	private JMenuItem menuItemGenerateDistEstimation;
	
	private RotationSequenceJDialog rotDialog;
	
	protected SliceSequenceJDialog sliceDialog;
	
	private JMenu menuAffichage;
	private JMenuItem menuItemShowFullImage;
	private JMenuItem menuItemGenerateSliceSequence;
	//private FullImageFrame imageWindowFull;
	
	public FrameApp(int dimL, int dimH)
	{
		super("Java - 3DFractals rendering program");
		renderImageFrame = new ImageFrame("Image Rendering Frame", new BufferedImage(251,251,BufferedImage.TYPE_INT_RGB));
		// Panneau où apparaîtra l'image
		// à gauche
		panelG = new Image(dimL, dimH, 25);
		doPowerSequence = false;
		
		// Panneau des paramètres de l'images et de la coupe
		// à droite
		initOnglet();
		
		// Barre de menu		
		initMenuBar();
			
		//Le Frame
		this.setJMenuBar(menuBar);
		
		//mainPanel.setBackground(Color.white);
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.add(panelG);
		mainPanel.add(ongletsPane);
		
		this.setSize(770,625);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		// Message aux utilisateur
		String message = "Some parameters can't be modified:\n"
				+ "\t -The computation algorithm for rendering the image\n";
		String titleMessage = "INFO: Not accessible for the moment";
		showMessageUtil(message, titleMessage);
		
		// Dialog for rotations
		rotDialog = new RotationSequenceJDialog(null, "Rotation sequence dialog", false, this);
		
		// Dialog for slices
		sliceDialog = new SliceSequenceJDialog(null, "Slice Sequence Dialog", false, this);
		
		// Un test pour le changement d'image
		//imageTest();
		/*BufferedImage I;
		JFileChooser chooser = new JFileChooser();

		int retour = chooser.showDialog( this, "Charger l'image" );

		// si l'utilisateur a cliqué sur le bouton "Charger l'image"
		if ( retour == 	JFileChooser.APPROVE_OPTION )
		{
			String path = chooser.getSelectedFile().getPath();
			String name = chooser.getSelectedFile().getName();
			
			I = toBufferedImage(getToolkit().getImage(path));
			System.out.println("ICI, l'image a été téléchargée!");
		}
		else
		{
			I = new BufferedImage(499, 499, BufferedImage.TYPE_INT_RGB);
			System.out.println("Malheureusement");
		}
			
		panelG.setImage(I);*/
		
		// Initialisation de Tribrot
		t = new Tribrot(panelG, renderImageFrame, this);
		
	}

	// Méthode initialisation des onglets: panneau de droite
	public void initOnglet()
	{
		ongletsPane = new JTabbedPane(SwingConstants.TOP);
		ongletsPane.setPreferredSize(new Dimension(350,550));
		
			//Onglet des paramètres
			onglet1Param = new Parametres();
			//onglet1Param.setVisibilityStyle(false);
			//onglet1Param.setVisibilityInterior(true);
			ongletsPane.addTab("Parameters", onglet1Param);
		
			//Onglet pour caractéristique de l'image
			onglet2Image = new ImageCaracteristique();
			onglet2Image.setVisibilityInterior(true);
			onglet2Image.setVisibilityStyle(false);
			ongletsPane.addTab("Image", onglet2Image);
			
			// Onglet des paramètres saves
			onglet3Saves = new ParametersSavings("", onglet1Param, onglet2Image);
			ongletsPane.addTab("Parameters and Characterics saves", onglet3Saves);
	}
	
	//Méthode pour initialiser la barre de menu
	public void initMenuBar()
	{
		menuBar = new JMenuBar();
			
			// Menu Fichier
			menuFichier = new JMenu("File");
			// Menu sauvegarde
			menuSauvegarde = new JMenu("Save");
			menuItemSauvegarderCurrent = new JMenuItem("Save current image");
			menuItemSauvegarderCurrent.addActionListener(new SauvegarderListener(1));
			menuItemSaveAllImage = new JMenuItem("Save all images");
			menuItemSaveAllImage.addActionListener(new SauvegarderListener(2));
			menuItemSauvegardeParameter = new JMenuItem("Save List of Parameters in a File");
			menuItemSauvegardeParameter.addActionListener(new SauvegardeParameterActionListener());
			menuSauvegarde.add(menuItemSauvegarderCurrent);
			menuSauvegarde.add(menuItemSaveAllImage);
			menuSauvegarde.add(menuItemSauvegardeParameter);
			
			// Item quiter
			menuItemQuitter = new JMenuItem("Quit");
			menuItemQuitter.addActionListener(new QuitterListener());
			
			menuFichier.add(menuSauvegarde);
			menuFichier.add(menuItemQuitter);
			menuBar.add(menuFichier);
			
			// Menu edit
			menuEdit = new JMenu("Edit");
			menuItemRestoration = new JMenuItem("Restore parameters");
			menuItemRestoration.addActionListener(new RestoreActionListener());
			menuItemSetAppropriateParameter = new JMenuItem("Set appropriate parameters");
			menuItemSetAppropriateParameter.addActionListener(new AppropActionListener());
			menuItemSettings = new JMenuItem("Settings");
			menuItemSettings.addActionListener(new SettingsActionListener());
			menuItemDoPowerSequence = new JMenuItem("Do a Power sequence: false");
			menuItemDoPowerSequence.addActionListener(new DoPowerSequenceActionListener());
			menuItemDoPowerSequence.setVisible(false);
			//menuItemCBRenderEmplacement = new JCheckBoxMenuItem("Render in Exterior Frame");
			//menuItemCBRenderEmplacement.addActionListener(new RenderImageEmplacementListener());
			menuEdit.add(menuItemRestoration);
			menuEdit.add(menuItemSetAppropriateParameter);
			menuEdit.add(menuItemSettings);
			menuEdit.add(menuItemDoPowerSequence);
			//menuEdit.add(menuItemCBRenderEmplacement);
			
			menuViewProcess = new JMenu("View Process");
			menuItemRBNoViewProcess = new JRadioButtonMenuItem("No process view");
			menuItemRBPointProcess = new JRadioButtonMenuItem("Rendering point by point");
			menuItemRBLineProcess = new JRadioButtonMenuItem("Rendering line by line");
			menuItemRBSliceProcess = new JRadioButtonMenuItem("Rendering slice by slice");
			menuItemRBNoViewProcess.setSelected(true);
			
			menuItemRBPointProcess.setVisible(false);
			menuItemRBLineProcess.setVisible(false);
			
			menuViewProcessButtonGroup = new ButtonGroup();
			menuViewProcessButtonGroup.add(menuItemRBNoViewProcess);
			menuViewProcessButtonGroup.add(menuItemRBPointProcess);
			menuViewProcessButtonGroup.add(menuItemRBLineProcess);
			menuViewProcessButtonGroup.add(menuItemRBSliceProcess);
			
			menuViewProcess.add(menuItemRBNoViewProcess);
			menuViewProcess.add(menuItemRBPointProcess);
			menuViewProcess.add(menuItemRBLineProcess);
			menuViewProcess.add(menuItemRBSliceProcess);
			
			menuViewProcess.setEnabled(true);
			
			menuEdit.add(menuViewProcess);
			
			menuAddsOn = new JMenu("Adds On:");
			
			menuRotateProcess = new JCheckBoxMenuItem("AllowRotation");
			menuRotateProcess.addActionListener(new AllowRotationActionListener());
			menuRotateProcess.setEnabled(true);
			menuRotateProcess.setSelected(false);
			menuAddsOn.add(menuRotateProcess);
			
			menuViewAxes = new JCheckBoxMenuItem("Render axes X, Y, Z");
			menuViewAxes.setSelected(false);
			menuViewAxes.setEnabled(false);
			menuAddsOn.add(menuViewAxes);
			
			menuEdit.add(menuAddsOn);
			
			menuBar.add(menuEdit);
			
			// MEnu ACtion
			menuAction = new JMenu("Action");
			menuItemGenerate = new JMenuItem("Generate", KeyEvent.VK_F5);
			menuItemGenerate.addActionListener(new GenerateListener());
			
			menuItemGenerateOcta = new JMenuItem("Octaèdre");
			menuItemGenerateOcta.addActionListener(new GenerateOctaListener());
			
			menuItemGenerateRotSequence = new JMenuItem("Rotation Seq.");
			menuItemGenerateRotSequence.addActionListener(new GenerateSequenceRotationListener());
					
			menuItemStopGenerating = new JMenuItem("Stop Generating");
			menuItemStopGenerating.addActionListener(new StopGeneratingActionListener());
			
			menuItemGenerateSliceSequence = new JMenuItem("Slice Sequence");
			menuItemGenerateSliceSequence.addActionListener(new GenerateSliceActionListener());
			
			menuItemGenerateDistEstimation = new JMenuItem("Dist. Est.");
			menuItemGenerateDistEstimation.addActionListener(new GenerateDistEstActionListener());
			
			
			menuAction.add(menuItemGenerate);
			menuAction.add(menuItemGenerateOcta);
			menuAction.add(menuItemGenerateRotSequence);
			menuAction.add(menuItemGenerateSliceSequence);
			menuAction.add(menuItemStopGenerating);
			menuAction.add(menuItemGenerateDistEstimation);
			//menuAction.add(menuItemDoPowerSequence);
			menuBar.add(menuAction);
			
			// MEnu affichage
			menuAffichage = new JMenu("Show");
			menuItemShowFullImage = new JMenuItem("Full Image");
			menuItemShowFullImage.addActionListener(new ShowFullImageListener());
			menuAffichage.add(menuItemShowFullImage);
			menuBar.add(menuAffichage);
	}
	
	// Méthode pour afficher un message aux utilisateurs sur l'état du programme
	public void showMessageUtil(String message, String title)
	{
		JOptionPane.showMessageDialog(null, message , title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	// Méthode pour tester la modification de l'image dans le panneau de gauche
	public void imageTest()
	{
		BufferedImage newimage = new BufferedImage(250+1, 250 +1, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < 251; i++)
		{
			for (int j = 0; j < 251; j++)
			{
				Color c = new Color(i,j, 75);
				newimage.setRGB(i,j,c.getRGB());
			}
		}
		
		panelG.nextImage();
		panelG.setImage(newimage);
	}
	
	// Méthode static pour bloquer le menuItem Generate
	public void setEnabledGenerate(boolean etat)
	{
		menuItemGenerate.setEnabled(etat);
	}
	
	public void setEnabledGenerateOcta(boolean etat)
	{
		menuItemGenerateOcta.setEnabled(etat);
	}
	
	public void setEnableSettings(boolean etat)
	{
		menuItemSettings.setEnabled(etat);
	}
	
	public void setEnabledRotationCB(boolean etat)
	{
		menuRotateProcess.setEnabled(etat);
	}
	
	public boolean getRotationAllowed()
	{
		return menuRotateProcess.isSelected();
	}
	
	public void setEnableRotationSequence(boolean etat)
	{
		menuItemGenerateRotSequence.setEnabled(etat);
	}
	
	public void setEnableSliceSequence(boolean etat)
	{
		menuItemGenerateSliceSequence.setEnabled(etat);
	}
	
	public void setEnabledDistanceEst(boolean etat)
	{
		menuItemGenerateDistEstimation.setEnabled(etat);
	}
	//Classe pour action sur itemSauvegarder (À venir)
	class ShowFullImageListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e_)
		{
			FullImageFrame imageWindowFull = new FullImageFrame("Full Image View", panelG.getImage());
			//imageWindowFull.setImage(panelG.getImage());
			imageWindowFull.setVisible(true);
			//imageWindowFull.repaint();
		}
	}
	class SauvegarderListener implements ActionListener
	{
		protected int saveType;
		
		SauvegarderListener(int saveType)
		{
			this.saveType = saveType;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			JOptionPane jop1;
			if (saveType == 1)
			{
				try
				{
					//JFileChooser filechoose = new JFileChooser();
					//filechoose.setCurrentDirectory(new File("."));  /* ouvrir la boite de dialogue dans répertoire courant */
					//filechoose.setDialogTitle("Save Image in a folder"); /* nom de la boite de dialogue */
 				 
					//filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); /* pour afficher seulement les répertoires */
 				 
					//String approve = new String("Save"); /* Le bouton pour valider l’enregistrement portera la mention Enregistrer */
					/*int resultatEnregistrer = filechoose.showDialog(filechoose, approve); 
					if (resultatEnregistrer == JFileChooser.APPROVE_OPTION)
					{ /* Si l’utilisateur clique sur le bouton Enregistrer */
						//String chemin = filechoose.getSelectedFile().getAbsolutePath()+"\\"; /* pour avoir le chemin absolu */
						/* ici il faut appeler une méthode pour écrire dans un fichier
 				    	dans mon exemple je l'ai nommé enregistrer_txt et son prototype
 				    	c'est void enregistrer_txt(String fichier, String texte)   */
						//ImageIO.write(panelG.getImage(), "PNG", new File(chemin + "Image3DDeg" + t.getPower() + ".PNG"));
						// enregistrer_txt(chemin+"fichier1.txt", "texte A");
						//enregistrer_txt(chemin+"fichier2.txt", "texte B");
						//enregistrer_txt(chemin+"fichier3.txt", "texte C");
						// et vous pouvez enregistrer autant de fichiers que vous voulez 
					//}
					
					JFileChooser filechoose = new JFileChooser();
					filechoose.setCurrentDirectory(new File("."));  /* ouvrir la boite de dialogue dans répertoire courant */
					filechoose.setDialogTitle("Save Image in a folder"); /* nom de la boite de dialogue */
 				 
					//filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); /* pour afficher seulement les répertoires */
 				 
					String approve = new String("Save"); /* Le bouton pour valider l’enregistrement portera la mention Enregistrer */
					int resultatEnregistrer = filechoose.showDialog(filechoose, approve); 
					if (resultatEnregistrer == JFileChooser.APPROVE_OPTION)
					{ /* Si l’utilisateur clique sur le bouton Enregistrer */
						String chemin = filechoose.getSelectedFile().getAbsolutePath()+"-Image3DDeg" + t.getPower() + ".PNG"; /* pour avoir le chemin absolu */
						/* ici il faut appeler une méthode pour écrire dans un fichier
 				    	dans mon exemple je l'ai nommé enregistrer_txt et son prototype
 				    	c'est void enregistrer_txt(String fichier, String texte)   */
						ImageIO.write(panelG.getImage(), "PNG", new File(chemin));
						// enregistrer_txt(chemin+"fichier1.txt", "texte A");
						//enregistrer_txt(chemin+"fichier2.txt", "texte B");
						//enregistrer_txt(chemin+"fichier3.txt", "texte C");
						// et vous pouvez enregistrer autant de fichiers que vous voulez 
					}
				}
				catch (IOException err)
				{
					jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "Save error" , "ERREUR", JOptionPane.ERROR_MESSAGE);
				} 
				catch(NullPointerException err)
				{
					jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "No image generated" , "ERREUR", JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				try
				{
					BufferedImage[] imageTab = panelG.getAllImage();
					String[] namesTab = panelG.getAllNames();
					String chemin = "";
					String namePlusChemin = "";
					
					//showMessageUtil("Not available for the moment", "Availability Message");
					JFileChooser filechoose = new JFileChooser();
					filechoose.setCurrentDirectory(new File("."));  /* ouvrir la boite de dialogue dans répertoire courant */
					filechoose.setDialogTitle("Save Image in a folder"); /* nom de la boite de dialogue */
					
					//filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); /* pour afficher seulement les répertoires */
					filechoose.setFileSelectionMode(JFileChooser.FILES_ONLY);
					
					String approve = new String("Save"); /* Le bouton pour valider l’enregistrement portera la mention Enregistrer */
					int resultatEnregistrer = filechoose.showDialog(filechoose, approve); 
					if (resultatEnregistrer == JFileChooser.APPROVE_OPTION)
					{ 
						
						chemin = filechoose.getSelectedFile().getAbsolutePath();
						for (int i = 0; i < panelG.getNbImage(); i++)
						{
							namePlusChemin = chemin + "-" + namesTab[i] + ".PNG";
							ImageIO.write(imageTab[i], "PNG", new File(namePlusChemin));
						} 
					}
				}
				catch (IOException err)
				{
					jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "Save error" , "ERREUR", JOptionPane.ERROR_MESSAGE);
				} 
				catch(NullPointerException err)
				{
					jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "No image generated" , "ERREUR", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	//Classe pour action sur itemQuitter
	class QuitterListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
	
	//Classe pour action sur itemGenerate
	class GenerateListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int p, p2;
			boolean[] coupe;
			boolean viewAxes, goRotation;
			double[] componentValues;
			int style, pourcentageInt;
			int minIter,maxIter;
			int[] dimension;
			double[] axes;
			double[] rot;
			int viewProcess = 1;
			int typeOfSlice;
			double[] light;
			
			t.setStop(false);
			
			try
			{
				p = onglet1Param.getPower();
				p2 = onglet1Param.getSecondPower();
				coupe = onglet2Image.getComposantSelected();
				componentValues = onglet2Image.getComposantValue();
			
				if (onglet2Image.styleJuliaSelected())
					style = 3;
				else
					style = 1;
				pourcentageInt = onglet2Image.getInteriorPoucentage();
				minIter = onglet2Image.getCoucheMin();
				maxIter = onglet2Image.getCoucheMax();
			
				dimension = onglet2Image.getDimension();
				axes = onglet1Param.getAxes();
				rot = onglet1Param.getRotationValues();
				
				light = onglet2Image.getLightComponentVector();
				
				if(menuItemRBNoViewProcess.isSelected())
					viewProcess = 1;
				if (menuItemRBPointProcess.isSelected())
					viewProcess = 2;
				if (menuItemRBSliceProcess.isSelected())
					viewProcess = 4;
				if (menuItemRBLineProcess.isSelected())
					viewProcess = 3;
				typeOfSlice = onglet2Image.getTypeOfSlice();
				
				viewAxes = menuViewAxes.isSelected();
				goRotation = menuRotateProcess.isSelected();
				
				// Message pour vérifier les paramètres
				/*String paramRecup = " Power: " + p + " ; \n" + 
				" Axes\n" + "minX: " + axes[0] + " maxX: " + axes[1] +
				"\nminY: " + axes[2] + " maxY: " + axes[3] +
				"\nminZ: " + axes[4] + " maxZ: " + axes[5] +
				"\n% Interior: " + pourcentageInt +
				"\nDimensions\n" + "largeur: " + dimension[0] + " hauteur: " + dimension[1] + " profondeur: " + dimension[2] +
				"\n Composantes\n" + "1: " + componentValues[0] + " i4: " + componentValues[4] +
				"\ni1: " + componentValues[1] + " j1: " + componentValues[5] +
				"\ni2: " + componentValues[2] + " j2: " + componentValues[6] +
				"\ni3: " + componentValues[3] + " j3: " + componentValues[7];
			
				Tricomplex c = new Tricomplex();
				c.setComponent(1.5, 1.5, 1.5, coupe, componentValues);
				paramRecup = paramRecup + "\n c = " + c.toString();
				
				showMessageUtil(paramRecup, "Paramètres récupérés");
				 */
				
				//panelG.nextImage();
				Object[] possibilities = {"overwrite current image;", "write to the next emplacement."};
				String answer = (String) JOptionPane.showInputDialog(null, "Do you want to ...", "Display of images", JOptionPane.PLAIN_MESSAGE, null, possibilities, "write to the next emplacement.");
				
				if (answer == null || panelG.getNbImage() == 25)
				{
					JOptionPane.showMessageDialog(null, "Process avoided", "Information Message", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					if(answer.equals("write to the next emplacement."))
					{
						panelG.nextImage();
					}
					t.setParam(p, p2, doPowerSequence, coupe, componentValues, style, minIter, maxIter,
							dimension, axes, pourcentageInt,rot, viewProcess, viewAxes, false, false, light, typeOfSlice);
					Thread processusCalc = new Thread(t);
					processusCalc.start();
				}
			}
			catch(PowerException err)
			{
				err.afficherMessageErreur();
			}
			catch(SameAxesValuesException err)
			{
				err.afficherMessageErreur();
			}
			catch(NumberFormatException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
			"\t-You write a decimal number with a dot. Replace the dot by a comma." +
						"\n\t-You write a space between the numbers. Erase them immediately!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(InputMismatchException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
					"\t-You write a decimal number with a dot. Replace the dot by a comma." +
					"\n\t-You write a space between the numbers. Erase them immediately!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(NoSuchElementException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
						"\t-You forget to write a number in a field.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			finally
			{
				onglet2Image.repaint();
				onglet2Image.revalidate();
			}
		}
	}
	
	class GenerateDistEstActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int p, p2;
			boolean[] coupe;
			boolean viewAxes, goRotation;
			double[] componentValues;
			int style, pourcentageInt;
			int minIter,maxIter;
			int[] dimension;
			double[] axes;
			double[] rot;
			int viewProcess = 1;
			double[] light;
			double epsilon;
			
			t.setStop(false);
			
			try
			{
				p = onglet1Param.getPower();
				p2 = onglet1Param.getSecondPower();
				coupe = onglet2Image.getComposantSelected();
				componentValues = onglet2Image.getComposantValue();
			
				if (onglet2Image.styleJuliaSelected())
					style = 3;
				else
					style = 1;
				pourcentageInt = onglet2Image.getInteriorPoucentage();
				minIter = onglet2Image.getCoucheMin();
				maxIter = onglet2Image.getCoucheMax();
			
				dimension = onglet2Image.getDimension();
				epsilon = onglet2Image.getEpsilonDist();
				axes = onglet1Param.getAxes();
				rot = onglet1Param.getRotationValues();
				
				light = onglet2Image.getLightComponentVector();
				
				if(menuItemRBNoViewProcess.isSelected())
					viewProcess = 1;
				if (menuItemRBPointProcess.isSelected())
					viewProcess = 2;
				if (menuItemRBSliceProcess.isSelected())
					viewProcess = 4;
				if (menuItemRBLineProcess.isSelected())
					viewProcess = 3;
				
				viewAxes = menuViewAxes.isSelected();
				goRotation = menuRotateProcess.isSelected();
				
				// Message pour vérifier les paramètres
				/*String paramRecup = " Power: " + p + " ; \n" + 
				" Axes\n" + "minX: " + axes[0] + " maxX: " + axes[1] +
				"\nminY: " + axes[2] + " maxY: " + axes[3] +
				"\nminZ: " + axes[4] + " maxZ: " + axes[5] +
				"\n% Interior: " + pourcentageInt +
				"\nDimensions\n" + "largeur: " + dimension[0] + " hauteur: " + dimension[1] + " profondeur: " + dimension[2] +
				"\n Composantes\n" + "1: " + componentValues[0] + " i4: " + componentValues[4] +
				"\ni1: " + componentValues[1] + " j1: " + componentValues[5] +
				"\ni2: " + componentValues[2] + " j2: " + componentValues[6] +
				"\ni3: " + componentValues[3] + " j3: " + componentValues[7];
			
				Tricomplex c = new Tricomplex();
				c.setComponent(1.5, 1.5, 1.5, coupe, componentValues);
				paramRecup = paramRecup + "\n c = " + c.toString();
				
				showMessageUtil(paramRecup, "Paramètres récupérés");
				 */
				
				//panelG.nextImage();
				Object[] possibilities = {"overwrite current image;", "write to the next emplacement."};
				String answer = (String) JOptionPane.showInputDialog(null, "Do you want to ...", "Display of images", JOptionPane.PLAIN_MESSAGE, null, possibilities, "write to the next emplacement.");
				
				if (answer == null || panelG.getNbImage() == 25)
				{
					JOptionPane.showMessageDialog(null, "Process avoided", "Information Message", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					if(answer.equals("write to the next emplacement."))
					{
						panelG.nextImage();
					}
					t.setParamDistanceEst(p, p2, doPowerSequence, coupe, componentValues, style, minIter, maxIter,
							dimension, axes, pourcentageInt,rot, viewProcess, viewAxes, false, false, light, epsilon, onglet2Image.getTypeOfSlice());
					Thread processusCalc = new Thread(t);
					processusCalc.start();
				}
			}
			catch(PowerException err)
			{
				err.afficherMessageErreur();
			}
			catch(SameAxesValuesException err)
			{
				err.afficherMessageErreur();
			}
			catch(NumberFormatException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
			"\t-You write a decimal number with a dot. Replace the dot by a comma." +
						"\n\t-You write a space between the numbers. Erase them immediately!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(InputMismatchException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
					"\t-You write a decimal number with a dot. Replace the dot by a comma." +
					"\n\t-You write a space between the numbers. Erase them immediately!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(NoSuchElementException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
						"\t-You forget to write a number in a field.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			finally
			{
				onglet2Image.repaint();
				onglet2Image.revalidate();
			}
		}
	}
	
	class GenerateOctaListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int p, p2;
			boolean[] coupe;
			boolean viewAxes, goRotation;
			double[] componentValues;
			int style, pourcentageInt;
			int minIter,maxIter;
			int[] dimension;
			double[] axes;
			double[] rot;
			int viewProcess = 1;
			double[] light;
			
			t.setStop(false);
			
			try
			{
				p = onglet1Param.getPower();
				p2 = onglet1Param.getSecondPower();
				coupe = onglet2Image.getComposantSelected();
				componentValues = onglet2Image.getComposantValue();
			
				if (onglet2Image.styleJuliaSelected())
					style = 3;
				else
					style = 1;
				pourcentageInt = onglet2Image.getInteriorPoucentage();
				minIter = onglet2Image.getCoucheMin();
				maxIter = onglet2Image.getCoucheMax();
			
				dimension = onglet2Image.getDimension();
				axes = onglet1Param.getAxes();
				rot = onglet1Param.getRotationValues();
				
				light = onglet2Image.getLightComponentVector();
				
				if(menuItemRBNoViewProcess.isSelected())
					viewProcess = 1;
				if (menuItemRBPointProcess.isSelected())
					viewProcess = 2;
				if (menuItemRBSliceProcess.isSelected())
					viewProcess = 4;
				if (menuItemRBLineProcess.isSelected())
					viewProcess = 3;
				
				viewAxes = menuViewAxes.isSelected();
				goRotation = menuRotateProcess.isSelected();
				
				// Message pour vérifier les paramètres
				/*String paramRecup = " Power: " + p + " ; \n" + 
				" Axes\n" + "minX: " + axes[0] + " maxX: " + axes[1] +
				"\nminY: " + axes[2] + " maxY: " + axes[3] +
				"\nminZ: " + axes[4] + " maxZ: " + axes[5] +
				"\n% Interior: " + pourcentageInt +
				"\nDimensions\n" + "largeur: " + dimension[0] + " hauteur: " + dimension[1] + " profondeur: " + dimension[2] +
				"\n Composantes\n" + "1: " + componentValues[0] + " i4: " + componentValues[4] +
				"\ni1: " + componentValues[1] + " j1: " + componentValues[5] +
				"\ni2: " + componentValues[2] + " j2: " + componentValues[6] +
				"\ni3: " + componentValues[3] + " j3: " + componentValues[7];
			
				Tricomplex c = new Tricomplex();
				c.setComponent(1.5, 1.5, 1.5, coupe, componentValues);
				paramRecup = paramRecup + "\n c = " + c.toString();
				
				showMessageUtil(paramRecup, "Paramètres récupérés");
				 */
				
				//panelG.nextImage();
				Object[] possibilities = {"overwrite current image;", "write to the next emplacement."};
				String answer = (String) JOptionPane.showInputDialog(null, "Do you want to ...", "Display of images", JOptionPane.PLAIN_MESSAGE, null, possibilities, "write to the next emplacement.");
				
				if (answer == null || panelG.getNbImage() == 25)
				{
					JOptionPane.showMessageDialog(null, "Process avoided", "Information Message", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					if(answer.equals("write to the next emplacement."))
					{
						panelG.nextImage();
					}
					t.setParam(p, p2, doPowerSequence, coupe, componentValues, style, minIter, maxIter,
							dimension, axes, pourcentageInt,rot, viewProcess, viewAxes, false, true, light, onglet2Image.getTypeOfSlice());
					Thread processusCalc = new Thread(t);
					processusCalc.start();
				}
			}
			catch(PowerException err)
			{
				err.afficherMessageErreur();
			}
			catch(SameAxesValuesException err)
			{
				err.afficherMessageErreur();
			}
			catch(NumberFormatException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
			"\t-You write a decimal number with a dot. Replace the dot by a comma." +
						"\n\t-You write a space between the numbers. Erase them immediately!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(InputMismatchException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
					"\t-You write a decimal number with a dot. Replace the dot by a comma." +
					"\n\t-You write a space between the numbers. Erase them immediately!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(NoSuchElementException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
						"\t-You forget to write a number in a field.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			finally
			{
				onglet2Image.repaint();
				onglet2Image.revalidate();
			}
		}
	}
	
	class GenerateSequenceRotationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			rotDialog.showTheDialog();
		}
	}
	
	class GenerateSliceActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			sliceDialog.showTheDialog();
		}
	}
	
	public void doRotationSequence(RotationSequenceJDialogInfo rotDialogInfo)
	{
		if (rotDialogInfo != null)
		{
			int p, p2;
			boolean[] coupe;
			boolean viewAxes, goRotation;
			double[] componentValues;
			int style, pourcentageInt;
			int minIter,maxIter;
			int[] dimension;
			double[] axes;
			double[] rot = new double[3], rotEnd = new double[3]
					, increment = new double[3];
			int viewProcess = 1;
			
			String repositoryName;
			double[] rotX = new double[3];
			double[] rotY = new double[3];
			double[] rotZ = new double[3];
			
			try
			{
				p = onglet1Param.getPower();
				p2 = onglet1Param.getSecondPower();
				coupe = onglet2Image.getComposantSelected();
				componentValues = onglet2Image.getComposantValue();
			
				if (onglet2Image.styleJuliaSelected())
					style = 3;
				else
					style = 1;
				pourcentageInt = onglet2Image.getInteriorPoucentage();
				minIter = onglet2Image.getCoucheMin();
				maxIter = onglet2Image.getCoucheMax();
			
				dimension = onglet2Image.getDimension();
				axes = onglet1Param.getAxes();
				
				repositoryName = rotDialogInfo.getRepository();
				
				rotX = rotDialogInfo.getFromToXRot();
				rotY = rotDialogInfo.getFromToYRot();
				rotZ = rotDialogInfo.getFromToZRot();
				
				rot[0] = rotX[0];
				rot[1] = rotY[0];
				rot[2] = rotZ[0];
				rotEnd[0] = rotX[1]; rotEnd[1] = rotY[1]; rotEnd[2] = rotZ[1];
				increment[0] = rotX[2]; increment[1] = rotY[2]; increment[2] = rotZ[2];
				
				if(menuItemRBNoViewProcess.isSelected())
					viewProcess = 1;
				if (menuItemRBPointProcess.isSelected())
					viewProcess = 2;
				if (menuItemRBSliceProcess.isSelected())
					viewProcess = 4;
				if (menuItemRBLineProcess.isSelected())
					viewProcess = 3;
				
				viewAxes = menuViewAxes.isSelected();
				goRotation = menuRotateProcess.isSelected();
				
				// Message pour vérifier les paramètres
				/*String paramRecup = " Power: " + p + " ; \n" + 
				" Axes\n" + "minX: " + axes[0] + " maxX: " + axes[1] +
				"\nminY: " + axes[2] + " maxY: " + axes[3] +
				"\nminZ: " + axes[4] + " maxZ: " + axes[5] +
				"\n% Interior: " + pourcentageInt +
				"\nDimensions\n" + "largeur: " + dimension[0] + " hauteur: " + dimension[1] + " profondeur: " + dimension[2] +
				"\n Composantes\n" + "1: " + componentValues[0] + " i4: " + componentValues[4] +
				"\ni1: " + componentValues[1] + " j1: " + componentValues[5] +
				"\ni2: " + componentValues[2] + " j2: " + componentValues[6] +
				"\ni3: " + componentValues[3] + " j3: " + componentValues[7];
			
				Tricomplex c = new Tricomplex();
				c.setComponent(1.5, 1.5, 1.5, coupe, componentValues);
				paramRecup = paramRecup + "\n c = " + c.toString();
				
				showMessageUtil(paramRecup, "Paramètres récupérés");
				 */
				
				t.setParamSequenceRotation(p, p2, doPowerSequence, coupe, componentValues, style, minIter, maxIter,
						dimension, axes, pourcentageInt,rot, rotEnd, increment, viewProcess, viewAxes, false, false, repositoryName, onglet2Image.getTypeOfSlice());
				Thread processusCalc = new Thread(t);
				processusCalc.start();
				
			}
			catch(PowerException err)
			{
				err.afficherMessageErreur();
			}
			catch(SameAxesValuesException err)
			{
				err.afficherMessageErreur();
			}
			catch(NumberFormatException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
			"\t-You write a decimal number with a dot. Replace the dot by a comma." +
						"\n\t-You write a space between the numbers. Erase them immediately!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(InputMismatchException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
					"\t-You write a decimal number with a dot. Replace the dot by a comma." +
					"\n\t-You write a space between the numbers. Erase them immediately!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(NoSuchElementException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
						"\t-You forget to write a number in a field.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			finally
			{
				onglet2Image.repaint();
				onglet2Image.revalidate();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Aucune information n'a été rentrée)");
			System.out.println("Je suis plutôt ici");
		}
	}
	
	public void doSliceSequence(SliceSequenceJDialogInfo sliceDialogInfo)
	{
		if (sliceDialogInfo != null)
		{
			int p, p2;
			boolean[] coupe;
			boolean viewAxes, goRotation;
			boolean[] coupeStart = new boolean[8];
			boolean[] coupeEnd = new boolean[8];
			double[] componentValues = new double[8];
			int style, pourcentageInt;
			int minIter,maxIter;
			int totalIncrement;
			int[] dimension;
			double[] axes;
			double[] rot = new double[3];
			int viewProcess = 1;
			double startAxesValues, endAxesValues, maxAxesValues;
			int[] coucheMin = new int[2];
			int[] coucheMax = new int[2];
			
			int typeOfSequence;
			
			String repositoryName;
			
			try
			{
				p = onglet1Param.getPower();
				p2 = onglet1Param.getSecondPower();
			
				if (onglet2Image.styleJuliaSelected())
					style = 3;
				else
					style = 1;
				pourcentageInt = onglet2Image.getInteriorPoucentage();
				minIter = onglet2Image.getCoucheMin();
				maxIter = onglet2Image.getCoucheMax();
			
				dimension = onglet2Image.getDimension();
				axes = onglet1Param.getAxes();
				rot = onglet1Param.getRotationValues();
				
				repositoryName = sliceDialogInfo.getRepository();
				totalIncrement = (int) sliceDialogInfo.getIncrement();
				coupeStart = sliceDialogInfo.getCoupeFirst();
				coupeEnd = sliceDialogInfo.getCoupeSecond();
				startAxesValues = sliceDialogInfo.getStartAxes();
				endAxesValues = sliceDialogInfo.getEndAxes();
				maxAxesValues = sliceDialogInfo.getMaxAxes();
				
				coucheMin[0] = sliceDialogInfo.getStartCoucheMin();
				coucheMin[1] = sliceDialogInfo.getEndCoucheMin();
				
				coucheMax[0] = sliceDialogInfo.getStartCoucheMax();
				coucheMax[1] = sliceDialogInfo.getEndCoucheMax();
				
				for (int j = 0; j < 8; j++)
					componentValues[j] = 0;
				
				if(menuItemRBNoViewProcess.isSelected())
					viewProcess = 1;
				if (menuItemRBPointProcess.isSelected())
					viewProcess = 2;
				if (menuItemRBSliceProcess.isSelected())
					viewProcess = 4;
				if (menuItemRBLineProcess.isSelected())
					viewProcess = 3;
				
				viewAxes = menuViewAxes.isSelected();
				goRotation = menuRotateProcess.isSelected();
				typeOfSequence = sliceDialogInfo.getTypeOfSequence();
				
				// Message pour vérifier les paramètres
				/*String paramRecup = " Power: " + p + " ; \n" + 
				" Axes\n" + "minX: " + axes[0] + " maxX: " + axes[1] +
				"\nminY: " + axes[2] + " maxY: " + axes[3] +
				"\nminZ: " + axes[4] + " maxZ: " + axes[5] +
				"\n% Interior: " + pourcentageInt +
				"\nDimensions\n" + "largeur: " + dimension[0] + " hauteur: " + dimension[1] + " profondeur: " + dimension[2] +
				"\n Composantes\n" + "1: " + componentValues[0] + " i4: " + componentValues[4] +
				"\ni1: " + componentValues[1] + " j1: " + componentValues[5] +
				"\ni2: " + componentValues[2] + " j2: " + componentValues[6] +
				"\ni3: " + componentValues[3] + " j3: " + componentValues[7];
			
				Tricomplex c = new Tricomplex();
				c.setComponent(1.5, 1.5, 1.5, coupe, componentValues);
				paramRecup = paramRecup + "\n c = " + c.toString();
				
				showMessageUtil(paramRecup, "Paramètres récupérés");
				 */
				
				t.setParamSequenceSlice(p, p2, doPowerSequence, coupeStart, coupeEnd, componentValues,
						componentValues, startAxesValues, endAxesValues, maxAxesValues, style, minIter, maxIter,
						dimension, axes, pourcentageInt, rot, totalIncrement, viewProcess, viewAxes, false, false, 
						repositoryName, coucheMin, coucheMax, onglet2Image.getLightComponentVector(), typeOfSequence);
				Thread processusCalc = new Thread(t);
				processusCalc.start();
				
			}
			catch(PowerException err)
			{
				err.afficherMessageErreur();
			}
			catch(SameAxesValuesException err)
			{
				err.afficherMessageErreur();
			}
			catch(NumberFormatException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
			"\t-You write a decimal number with a dot. Replace the dot by a comma." +
						"\n\t-You write a space between the numbers. Erase them immediately!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(InputMismatchException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
					"\t-You write a decimal number with a dot. Replace the dot by a comma." +
					"\n\t-You write a space between the numbers. Erase them immediately!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(NoSuchElementException err)
			{
				JOptionPane.showMessageDialog(null, "Check the following errors: \n" + 
						"\t-You forget to write a number in a field.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			finally
			{
				onglet2Image.repaint();
				onglet2Image.revalidate();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Aucune information n'a été rentrée)");
			System.out.println("Je suis plutôt ici");
		}
	}
	
	class RestoreActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			showMessageUtil("Not available for the moment", "Availability Message");
		}
	}
	
	class AppropActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try 
			{
				onglet1Param.setAxesJTF();
			} 
			catch (PowerException err) 
			{
				err.afficherMessageErreur();
			}
		}
	}
	
	class SauvegardeParameterActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showDialog(chooser, "save");
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
			    FileOutputStream stream = null;
			    PrintStream out = null;
			    try 
			    {
			        File file = chooser.getSelectedFile();
			        stream = new FileOutputStream(file); 
			        String text = onglet3Saves.getTextArea();
			        out = new PrintStream(stream);
			        out.print(text);                  //This will overwrite existing contents

			    } 
			    catch (Exception ex) 
			    {
			        //do something
			    } 
			    finally 
			    {
			        try 
			        {
			            if(stream!=null) stream.close();
			            if(out!=null) out.close();
			        } catch (Exception ex) 
			        {
			            //do something
			        }
			    }
			}
		}
	}
	
	class DoPowerSequenceActionListener implements ActionListener
	{
		//private FrameApp appFrame;
		String powerString = "Do a power Sequence: ";
		//public DoPowerSequenceActionListener(FrameApp appFrame)
		//{
			//this.appFrame = appFrame;
		//}
		
		public void actionPerformed(ActionEvent e)
		{
			if (doPowerSequence)
			{
				doPowerSequence = false;
				onglet1Param.setEnabledPower2(false);
				menuItemDoPowerSequence.setText(powerString + "false");
			}
			else
			{
				doPowerSequence = true;
				onglet1Param.setEnabledPower2(true);
				menuItemDoPowerSequence.setText(powerString + "true");
			}
			//showMessageUtil("Not available for the moment", "Availability Message");
			//PowerFrame powerFrame = new PowerFrame(750,500, appFrame);
			//powerFrame.setVisible(true);
		}
	}
	
	class StopGeneratingActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			t.setStop(true);
		}
	}
	
	class RenderImageEmplacementListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			renderImageFrame.setVisible(menuItemCBRenderEmplacement.isSelected());
		}
	}
	
	class SettingsActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//SettingsJDialog settings = new SettingsJDialog("Change Maximum iterations",null ,true);
			showMessageUtil("Not available for the moment", "Availability Message");
		}
	}
	
	class AllowRotationActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (menuRotateProcess.isSelected())
			{
				//menuItemRotate.setEnabled(true);
				onglet1Param.setEnabledRotation(true);
			}
			else
			{
				menuItemRotate.setEnabled(false);
				onglet1Param.setEnabledRotation(false);
			}
		}
	}
	
	public static BufferedImage toBufferedImage(java.awt.Image img)
	{
	   
	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(499, 499, BufferedImage.TYPE_INT_RGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
}
