import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class SliceSequenceJDialog extends JDialog 
{
	private FrameApp framePrincipalMandel;
	private SliceSequenceJDialogInfo sliceDialogInfo= new SliceSequenceJDialogInfo();
	
	protected JLabel repositoryJL;
	
	protected JTextField reelFirstJTF, im1FirstJTF, im2FirstJTF, im3FirstJTF, im4FirstJTF,
	hyp1FirstJTF, hyp2FirstJTF, hyp3FirstJTF;
	protected JTextField e1ReFirstJTF, e1ImFirstJTF, e2ReFirstJTF, e2ImFirstJTF, e3ReFirstJTF, e3ImFirstJTF, e4ReFirstJTF, e4ImFirstJTF;
	protected JTextField reelSecondJTF, im1SecondJTF, im2SecondJTF, im3SecondJTF, im4SecondJTF,
	hyp1SecondJTF, hyp2SecondJTF, hyp3SecondJTF;
	protected JTextField e1ReSecondJTF, e1ImSecondJTF, e2ReSecondJTF, e2ImSecondJTF, e3ReSecondJTF, e3ImSecondJTF, e4ReSecondJTF, e4ImSecondJTF;
	
	protected JCheckBox reelFirstCB, im1FirstCB, im2FirstCB, im3FirstCB, im4FirstCB,
	hyp1FirstCB, hyp2FirstCB, hyp3FirstCB;
	protected JCheckBox e1ReFirstCB, e1ImFirstCB, e2ReFirstCB, e2ImFirstCB, e3ReFirstCB, e3ImFirstCB, e4ReFirstCB, e4ImFirstCB;
	protected JCheckBox reelSecondCB, im1SecondCB, im2SecondCB, im3SecondCB, im4SecondCB,
	hyp1SecondCB, hyp2SecondCB, hyp3SecondCB;
	protected JCheckBox e1ReSecondCB, e1ImSecondCB, e2ReSecondCB, e2ImSecondCB, e3ReSecondCB, e3ImSecondCB, e4ReSecondCB, e4ImSecondCB;
	
	protected JRadioButton realFirstRB, idempFirstRB, realSecondRB, idempSecondRB;
	
	protected JPanel composantFirstPanel, composantIdempFirstPanel, composantSecondPanel, composantIdempSecondPanel;
	
	protected TitledBorder title;
	
	private JLabel firstSliceJL, secondSliceJL;
	private JLabel realSliceJL, IdempSliceJL;
	private JLabel startXJL, endXJL, startYJL, endYJL, startZJL, endZJL;
	private JLabel pasJL;
	
	private JTextField repositoryJTF, rotatoinXJTF, rotationYJTF, rotationZJTF;
	private JTextField startXJTF, endXJTF, startYJTF, endYJTF, startZJTF, endZJTF;
	private JTextField pasJTF;
	
	private boolean sendData;
	private JTextField incrementZJTF;
	private JLabel incrementZJL;
	private JTextField incrementYJTF;
	private JLabel incrementYJL;
	private JLabel incrementXJL;
	private JTextField incrementXJTF;
	
	// Constante pour la classe
		protected int JTFH = 25;
		protected int JTFL = 60;
		private JLabel incrementJL;
		private JTextField incrementJTF;
		
		private JLabel maxAxesValuesJL;
		private JTextField maxAxesValuesJTF;
		
		private JLabel startAxesValuesJL;
		private JTextField startAxesValuesJTF;
		
		private JLabel endAxesValuesJL;
		private JTextField endAxesValuesJTF;
		
		private JLabel startCoucheMinJL;
		private JLabel endCoucheMinJL;
		private JTextField startCoucheMinJTF;
		private JTextField endCoucheMinJTF;
		
		private JLabel startCoucheMaxJL;
		private JLabel endCoucheMaxJL;
		private JTextField startCoucheMaxJTF;
		private JTextField endCoucheMaxJTF;
	
	public SliceSequenceJDialog(JFrame parent, String title, boolean modal, FrameApp framePrincipal)
	{
		super(parent, title, modal);
		this.setSize(575, 775);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.framePrincipalMandel = framePrincipal;
		this.initComponent();
	}
	
	public void showTheDialog()
	{
		this.sendData = false;
		this.setVisible(true);
	}
	
	private void initComponent()
	{
		JPanel repositoryPanel = new JPanel();
		repositoryJL = new JLabel("Repository: ");
		repositoryJTF = new JTextField("Choose a repository");
		repositoryJTF.setPreferredSize(new Dimension(100, 20));
		
		JButton cheminButton = new JButton("...");
		cheminButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					JFileChooser filechoose = new JFileChooser();
					filechoose.setCurrentDirectory(new File("."));  /* ouvrir la boite de dialogue dans répertoire courant */
					filechoose.setDialogTitle("Save Image in a folder"); /* nom de la boite de dialogue */
				 
					//filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); /* pour afficher seulement les répertoires */
				 
					String approve = new String("Save"); /* Le bouton pour valider l’enregistrement portera la mention Enregistrer */
					int resultatEnregistrer = filechoose.showDialog(filechoose, approve); 
					if (resultatEnregistrer == JFileChooser.APPROVE_OPTION)
					{
						repositoryJTF.setText(filechoose.getSelectedFile().getAbsolutePath()); /* pour avoir le chemin absolu */
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Downloading path failed...");
				}
			}
		});
		repositoryPanel.add(repositoryJL);
		repositoryPanel.add(repositoryJTF);
		repositoryPanel.add(cheminButton);
		this.getContentPane().add(repositoryPanel, BorderLayout.NORTH);
		
		
		// Les composantes de la première coupe 3D	
		JPanel milieuPanel = new JPanel(new GridLayout(3,1));
		
		reelFirstJTF = new JTextField("0");
		reelFirstJTF.setEnabled(false);
		reelFirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		im1FirstJTF = new JTextField("0");
		im1FirstJTF.setEnabled(false);
		im1FirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		im2FirstJTF = new JTextField("0");
		im2FirstJTF.setEnabled(false);
		im2FirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		im3FirstJTF = new JTextField("0");
		im3FirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		im4FirstJTF = new JTextField("0");
		im4FirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		hyp1FirstJTF = new JTextField("0");
		hyp1FirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		hyp2FirstJTF = new JTextField("0");
		hyp2FirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		hyp3FirstJTF = new JTextField("0");
		hyp3FirstJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		
		reelFirstCB = new JCheckBox("1: ");
		reelFirstCB.setSelected(true);
		reelFirstCB.addActionListener(new CheckBoxListener());
		
		im1FirstCB = new JCheckBox("i1: ");
		im1FirstCB.setSelected(true);
		im1FirstCB.addActionListener(new CheckBoxListener());
		
		im2FirstCB = new JCheckBox("i2: ");
		im2FirstCB.setSelected(true);
		im2FirstCB.addActionListener(new CheckBoxListener());
		
		im3FirstCB = new JCheckBox("i3: ");
		im3FirstCB.addActionListener(new CheckBoxListener());
		
		im4FirstCB = new JCheckBox("i4: ");
		im4FirstCB.addActionListener(new CheckBoxListener());
		
		hyp1FirstCB = new JCheckBox("j1: ");
		hyp1FirstCB.addActionListener(new CheckBoxListener());
		
		hyp2FirstCB = new JCheckBox("j2: ");
		hyp2FirstCB.addActionListener(new CheckBoxListener());
		
		hyp3FirstCB = new JCheckBox("j3: ");
		hyp3FirstCB.addActionListener(new CheckBoxListener());
		
		composantFirstPanel = new JPanel(new GridLayout(4,2));
		title = BorderFactory.createTitledBorder("3D Components");
		composantFirstPanel.setBorder(title);
				
		JPanel reelFirstPanel = new JPanel();
		reelFirstPanel.add(reelFirstCB);
		reelFirstPanel.add(reelFirstJTF);
		composantFirstPanel.add(reelFirstPanel);
		
		JPanel im4FirstPanel = new JPanel();
		im4FirstPanel.add(im4FirstCB);
		im4FirstPanel.add(im4FirstJTF);
		composantFirstPanel.add(im4FirstPanel);
						
		JPanel im1FirstPanel = new JPanel();
		im1FirstPanel.add(im1FirstCB);
		im1FirstPanel.add(im1FirstJTF);
		composantFirstPanel.add(im1FirstPanel);
				
		JPanel hyp1FirstPanel = new JPanel();
		hyp1FirstPanel.add(hyp1FirstCB);
		hyp1FirstPanel.add(hyp1FirstJTF);
		composantFirstPanel.add(hyp1FirstPanel);
				
		JPanel im2FirstPanel = new JPanel();
		im2FirstPanel.add(im2FirstCB);
		im2FirstPanel.add(im2FirstJTF);
		composantFirstPanel.add(im2FirstPanel);
					
		JPanel hyp2FirstPanel = new JPanel();
		hyp2FirstPanel.add(hyp2FirstCB);
		hyp2FirstPanel.add(hyp2FirstJTF);
		composantFirstPanel.add(hyp2FirstPanel);
				
		JPanel im3FirstPanel = new JPanel();
		im3FirstPanel.add(im3FirstCB);
		im3FirstPanel.add(im3FirstJTF);
		composantFirstPanel.add(im3FirstPanel);
				
		JPanel hyp3FirstPanel = new JPanel();
		hyp3FirstPanel.add(hyp3FirstCB);
		hyp3FirstPanel.add(hyp3FirstJTF);
		composantFirstPanel.add(hyp3FirstPanel);
		
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
		
		composantIdempFirstPanel = new JPanel(new GridLayout(4,2));
		title = BorderFactory.createTitledBorder("3D Idemp. Components");
		composantIdempFirstPanel.setBorder(title);
				
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
		
		// Les composantes de la deuxième coupe 3D		
		reelSecondJTF = new JTextField("0");
		reelSecondJTF.setEnabled(false);
		reelSecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		im1SecondJTF = new JTextField("0");
		im1SecondJTF.setEnabled(false);
		im1SecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		im2SecondJTF = new JTextField("0");
		im2SecondJTF.setEnabled(false);
		im2SecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		im3SecondJTF = new JTextField("0");
		im3SecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		im4SecondJTF = new JTextField("0");
		im4SecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		hyp1SecondJTF = new JTextField("0");
		hyp1SecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		hyp2SecondJTF = new JTextField("0");
		hyp2SecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		hyp3SecondJTF = new JTextField("0");
		hyp3SecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
		
		reelSecondCB = new JCheckBox("1: ");
		reelSecondCB.setSelected(true);
		reelSecondCB.addActionListener(new CheckBoxListener());
		
		im1SecondCB = new JCheckBox("i1: ");
		im1SecondCB.setSelected(true);
		im1SecondCB.addActionListener(new CheckBoxListener());
		
		im2SecondCB = new JCheckBox("i2: ");
		im2SecondCB.setSelected(true);
		im2SecondCB.addActionListener(new CheckBoxListener());
		
		im3SecondCB = new JCheckBox("i3: ");
		im3SecondCB.addActionListener(new CheckBoxListener());
		
		im4SecondCB = new JCheckBox("i4: ");
		im4SecondCB.addActionListener(new CheckBoxListener());
		
		hyp1SecondCB = new JCheckBox("j1: ");
		hyp1SecondCB.addActionListener(new CheckBoxListener());
		
		hyp2SecondCB = new JCheckBox("j2: ");
		hyp2SecondCB.addActionListener(new CheckBoxListener());
		
		hyp3SecondCB = new JCheckBox("j3: ");
		hyp3SecondCB.addActionListener(new CheckBoxListener());
		
		composantSecondPanel = new JPanel(new GridLayout(4,2));
		title = BorderFactory.createTitledBorder("3D Components");
		composantSecondPanel.setBorder(title);
						
		JPanel reelSecondPanel = new JPanel();
		reelSecondPanel.add(reelSecondCB);
		reelSecondPanel.add(reelSecondJTF);
		composantSecondPanel.add(reelSecondPanel);
				
		JPanel im4SecondPanel = new JPanel();
		im4SecondPanel.add(im4SecondCB);
		im4SecondPanel.add(im4SecondJTF);
		composantSecondPanel.add(im4SecondPanel);
								
		JPanel im1SecondPanel = new JPanel();
		im1SecondPanel.add(im1SecondCB);
		im1SecondPanel.add(im1SecondJTF);
		composantSecondPanel.add(im1SecondPanel);
						
		JPanel hyp1SecondPanel = new JPanel();
		hyp1SecondPanel.add(hyp1SecondCB);
		hyp1SecondPanel.add(hyp1SecondJTF);
		composantSecondPanel.add(hyp1SecondPanel);
						
		JPanel im2SecondPanel = new JPanel();
		im2SecondPanel.add(im2SecondCB);
		im2SecondPanel.add(im2SecondJTF);
		composantSecondPanel.add(im2SecondPanel);
							
		JPanel hyp2SecondPanel = new JPanel();
		hyp2SecondPanel.add(hyp2SecondCB);
		hyp2SecondPanel.add(hyp2SecondJTF);
		composantSecondPanel.add(hyp2SecondPanel);
						
		JPanel im3SecondPanel = new JPanel();
		im3SecondPanel.add(im3SecondCB);
		im3SecondPanel.add(im3SecondJTF);
		composantSecondPanel.add(im3SecondPanel);
						
		JPanel hyp3SecondPanel = new JPanel();
		hyp3SecondPanel.add(hyp3SecondCB);
		hyp3SecondPanel.add(hyp3SecondJTF);
		composantSecondPanel.add(hyp3SecondPanel);
		
		// LEs composants de la deuxième coupe 3D, en idempotent		
				e1ReSecondJTF = new JTextField("0");
				e1ReSecondJTF.setEnabled(false);
				e1ReSecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e1ImSecondJTF = new JTextField("0");
				e1ImSecondJTF.setEnabled(false);
				e1ImSecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e2ReSecondJTF = new JTextField("0");
				e2ReSecondJTF.setEnabled(false);
				e2ReSecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e2ImSecondJTF = new JTextField("0");
				e2ImSecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e3ReSecondJTF = new JTextField("0");
				e3ReSecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e3ImSecondJTF = new JTextField("0");
				e3ImSecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e4ReSecondJTF = new JTextField("0");
				e4ReSecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				e4ImSecondJTF = new JTextField("0");
				e4ImSecondJTF.setPreferredSize(new Dimension(JTFL, JTFH));
				
				e1ReSecondCB = new JCheckBox("e1(Re): ");
				e1ReSecondCB.setSelected(true);
				e1ReSecondCB.addActionListener(new CheckBoxListener());
				
				e1ImSecondCB = new JCheckBox("e1(Im): ");
				e1ImSecondCB.setSelected(true);
				e1ImSecondCB.addActionListener(new CheckBoxListener());
				
				e2ReSecondCB = new JCheckBox("e2(Re): ");
				e2ReSecondCB.setSelected(true);
				e2ReSecondCB.addActionListener(new CheckBoxListener());
				
				e2ImSecondCB = new JCheckBox("e2(Im): ");
				e2ImSecondCB.addActionListener(new CheckBoxListener());
				
				e3ReSecondCB = new JCheckBox("e3(Re): ");
				e3ReSecondCB.addActionListener(new CheckBoxListener());
				
				e3ImSecondCB = new JCheckBox("e3(Im): ");
				e3ImSecondCB.addActionListener(new CheckBoxListener());
				
				e4ReSecondCB = new JCheckBox("e4(Re): ");
				e4ReSecondCB.addActionListener(new CheckBoxListener());
				
				e4ImSecondCB = new JCheckBox("e4(Im): ");
				e4ImSecondCB.addActionListener(new CheckBoxListener());
				
				composantIdempSecondPanel = new JPanel(new GridLayout(4,2));
				title = BorderFactory.createTitledBorder("3D Idemp. Components");
				composantIdempSecondPanel.setBorder(title);
						
				JPanel e1ReSecondPanel = new JPanel();
				e1ReSecondPanel.add(e1ReSecondCB);
				e1ReSecondPanel.add(e1ReSecondJTF);
				composantIdempSecondPanel.add(e1ReSecondPanel);
				
				JPanel e1ImSecondPanel = new JPanel();
				e1ImSecondPanel.add(e1ImSecondCB);
				e1ImSecondPanel.add(e1ImSecondJTF);
				composantIdempSecondPanel.add(e1ImSecondPanel);
								
				JPanel e2ReSecondPanel = new JPanel();
				e2ReSecondPanel.add(e2ReSecondCB);
				e2ReSecondPanel.add(e2ReSecondJTF);
				composantIdempSecondPanel.add(e2ReSecondPanel);
						
				JPanel e2ImSecondPanel = new JPanel();
				e2ImSecondPanel.add(e2ImSecondCB);
				e2ImSecondPanel.add(e2ImSecondJTF);
				composantIdempSecondPanel.add(e2ImSecondPanel);
						
				JPanel e3ReSecondPanel = new JPanel();
				e3ReSecondPanel.add(e3ReSecondCB);
				e3ReSecondPanel.add(e3ReSecondJTF);
				composantIdempSecondPanel.add(e3ReSecondPanel);
							
				JPanel e3ImSecondPanel = new JPanel();
				e3ImSecondPanel.add(e3ImSecondCB);
				e3ImSecondPanel.add(e3ImSecondJTF);
				composantIdempSecondPanel.add(e3ImSecondPanel);
						
				JPanel e4ReSecondPanel = new JPanel();
				e4ReSecondPanel.add(e4ReSecondCB);
				e4ReSecondPanel.add(e4ReSecondJTF);
				composantIdempSecondPanel.add(e4ReSecondPanel);
						
				JPanel e4ImSecondPanel = new JPanel();
				e4ImSecondPanel.add(e4ImSecondCB);
				e4ImSecondPanel.add(e4ImSecondJTF);
				composantIdempSecondPanel.add(e4ImSecondPanel);
			
		// INformations essentielles pour les données des coupes 3D
		
		JPanel infoPanel = new JPanel(new GridLayout(4,2));
		
		JPanel incrementPanel = new JPanel();
		incrementJL = new JLabel("Nb. de frame : ");
		incrementJTF = new JTextField("10");
		incrementJTF.setPreferredSize(new Dimension(40,20));
		incrementPanel.add(incrementJL);
		incrementPanel.add(incrementJTF);
		infoPanel.add(incrementPanel);
		
		JPanel maxAxesValuesPanel = new JPanel();
		maxAxesValuesJL = new JLabel("Val. max axes : ");
		maxAxesValuesJTF = new JTextField("2");
		maxAxesValuesJTF.setPreferredSize(new Dimension(40,20));
		maxAxesValuesPanel.add(maxAxesValuesJL);
		maxAxesValuesPanel.add(maxAxesValuesJTF);
		infoPanel.add(maxAxesValuesPanel);
		
		JPanel startAxesValuesPanel = new JPanel();
		startAxesValuesJL = new JLabel("Val. start axes : ");
		startAxesValuesJTF = new JTextField("2");
		startAxesValuesJTF.setPreferredSize(new Dimension(40,20));
		startAxesValuesPanel.add(startAxesValuesJL);
		startAxesValuesPanel.add(startAxesValuesJTF);
		infoPanel.add(startAxesValuesPanel);
		
		JPanel endAxesValuesPanel = new JPanel();
		endAxesValuesJL = new JLabel("Val. end axes : ");
		endAxesValuesJTF = new JTextField("2");
		endAxesValuesJTF.setPreferredSize(new Dimension(40,20));
		endAxesValuesPanel.add(endAxesValuesJL);
		endAxesValuesPanel.add(endAxesValuesJTF);
		infoPanel.add(endAxesValuesPanel);
		
		JPanel startCoucheMinPanel = new JPanel();
		startCoucheMinJL = new JLabel("Val. start Couche Min.: ");
		startCoucheMinJTF = new JTextField("7");
		startCoucheMinJTF.setPreferredSize(new Dimension(40,20));
		startCoucheMinPanel.add(startCoucheMinJL);
		startCoucheMinPanel.add(startCoucheMinJTF);
		infoPanel.add(startCoucheMinPanel);
		
		JPanel endCoucheMinPanel = new JPanel();
		endCoucheMinJL = new JLabel("Val. end Couche Min.: ");
		endCoucheMinJTF = new JTextField("10");
		endCoucheMinJTF.setPreferredSize(new Dimension(40,20));
		endCoucheMinPanel.add(endCoucheMinJL);
		endCoucheMinPanel.add(endCoucheMinJTF);
		infoPanel.add(endCoucheMinPanel);
		
		JPanel startCoucheMaxPanel = new JPanel();
		startCoucheMaxJL = new JLabel("Val. start Couche Max.: ");
		startCoucheMaxJTF = new JTextField("10");
		startCoucheMaxJTF.setPreferredSize(new Dimension(40,20));
		startCoucheMaxPanel.add(startCoucheMaxJL);
		startCoucheMaxPanel.add(startCoucheMaxJTF);
		infoPanel.add(startCoucheMaxPanel);
		
		JPanel endCoucheMaxPanel = new JPanel();
		endCoucheMaxJL = new JLabel("Val. end Couche Max.: ");
		endCoucheMaxJTF = new JTextField("17");
		endCoucheMaxJTF.setPreferredSize(new Dimension(40,20));
		endCoucheMaxPanel.add(endCoucheMaxJL);
		endCoucheMaxPanel.add(endCoucheMaxJTF);
		infoPanel.add(endCoucheMaxPanel);
		
		milieuPanel.add(infoPanel);
		
		// Les JRAdioButton
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
		
		e1ReSecondJTF.setEnabled(false);
		e1ImSecondJTF.setEnabled(false);
		e2ReSecondJTF.setEnabled(false);
		e2ImSecondJTF.setEnabled(false);
		e3ReSecondJTF.setEnabled(false);
		e3ImSecondJTF.setEnabled(false);
		e4ReSecondJTF.setEnabled(false);
		e4ImSecondJTF.setEnabled(false);
		
		
		composantIdempSecondPanel.setEnabled(false);
		e1ReSecondCB.setEnabled(false);
		e1ImSecondCB.setEnabled(false);
		e2ReSecondCB.setEnabled(false);
		e2ImSecondCB.setEnabled(false);
		e3ReSecondCB.setEnabled(false);
		e3ImSecondCB.setEnabled(false);
		e4ReSecondCB.setEnabled(false);
		e4ImSecondCB.setEnabled(false);
		
		realFirstRB = new JRadioButton("Real slice");
		realFirstRB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				composantFirstPanel.setEnabled(true);
				
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
				
				reelFirstCB.setEnabled(true);
				im1FirstCB.setEnabled(true);
				im2FirstCB.setEnabled(true);
				im3FirstCB.setEnabled(true);
				im4FirstCB.setEnabled(true);
				hyp1FirstCB.setEnabled(true);
				hyp2FirstCB.setEnabled(true);
				hyp3FirstCB.setEnabled(true);
				
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
				composantFirstPanel.setEnabled(false);
				
				reelFirstJTF.setEnabled(false);
				im1FirstJTF.setEnabled(false);
				im2FirstJTF.setEnabled(false);
				im3FirstJTF.setEnabled(false);
				im4FirstJTF.setEnabled(false);
				hyp1FirstJTF.setEnabled(false);
				hyp2FirstJTF.setEnabled(false);
				hyp3FirstJTF.setEnabled(false);
				
				reelFirstJTF.setText("0");
				im1FirstJTF.setText("0");
				im2FirstJTF.setText("0");
				im3FirstJTF.setText("0");
				im4FirstJTF.setText("0");
				hyp1FirstJTF.setText("0");
				hyp2FirstJTF.setText("0");
				hyp3FirstJTF.setText("0");
				
				reelFirstCB.setEnabled(false);
				im1FirstCB.setEnabled(false);
				im2FirstCB.setEnabled(false);
				im3FirstCB.setEnabled(false);
				im4FirstCB.setEnabled(false);
				hyp1FirstCB.setEnabled(false);
				hyp2FirstCB.setEnabled(false);
				hyp3FirstCB.setEnabled(false);
				
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
		
		realSecondRB = new JRadioButton("Real slice");
		realSecondRB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				composantSecondPanel.setEnabled(true);
				
				refreshJTFSecond();
				
				reelSecondCB.setEnabled(true);
				im1SecondCB.setEnabled(true);
				im2SecondCB.setEnabled(true);
				im3SecondCB.setEnabled(true);
				im4SecondCB.setEnabled(true);
				hyp1SecondCB.setEnabled(true);
				hyp2SecondCB.setEnabled(true);
				hyp3SecondCB.setEnabled(true);
				
				composantIdempSecondPanel.setEnabled(false);
				
				e1ReSecondJTF.setEnabled(false);
				e1ImSecondJTF.setEnabled(false);
				e2ReSecondJTF.setEnabled(false);
				e2ImSecondJTF.setEnabled(false);
				e3ReSecondJTF.setEnabled(false);
				e3ImSecondJTF.setEnabled(false);
				e4ReSecondJTF.setEnabled(false);
				e4ImSecondJTF.setEnabled(false);
				
				e1ReSecondJTF.setText("0");
				e1ImSecondJTF.setText("0");
				e2ReSecondJTF.setText("0");
				e2ImSecondJTF.setText("0");
				e3ReSecondJTF.setText("0");
				e3ImSecondJTF.setText("0");
				e4ReSecondJTF.setText("0");
				e4ImSecondJTF.setText("0");
				
				e1ReSecondCB.setEnabled(false);
				e1ImSecondCB.setEnabled(false);
				e2ReSecondCB.setEnabled(false);
				e2ImSecondCB.setEnabled(false);
				e3ReSecondCB.setEnabled(false);
				e3ImSecondCB.setEnabled(false);
				e4ReSecondCB.setEnabled(false);
				e4ImSecondCB.setEnabled(false);
			}
			
		});
		idempSecondRB = new JRadioButton("Idemp slice");
		idempSecondRB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				composantSecondPanel.setEnabled(false);
				
				reelSecondJTF.setEnabled(false);
				im1SecondJTF.setEnabled(false);
				im2SecondJTF.setEnabled(false);
				im3SecondJTF.setEnabled(false);
				im4SecondJTF.setEnabled(false);
				hyp1SecondJTF.setEnabled(false);
				hyp2SecondJTF.setEnabled(false);
				hyp3SecondJTF.setEnabled(false);
				
				reelSecondJTF.setText("0");
				im1SecondJTF.setText("0");
				im2SecondJTF.setText("0");
				im3SecondJTF.setText("0");
				im4SecondJTF.setText("0");
				hyp1SecondJTF.setText("0");
				hyp2SecondJTF.setText("0");
				hyp3SecondJTF.setText("0");
				
				reelSecondCB.setEnabled(false);
				im1SecondCB.setEnabled(false);
				im2SecondCB.setEnabled(false);
				im3SecondCB.setEnabled(false);
				im4SecondCB.setEnabled(false);
				hyp1SecondCB.setEnabled(false);
				hyp2SecondCB.setEnabled(false);
				hyp3SecondCB.setEnabled(false);
				
				composantIdempSecondPanel.setEnabled(true);
				
				refreshJTFSecondIdemp();
				
				e1ReSecondCB.setEnabled(true);
				e1ImSecondCB.setEnabled(true);
				e2ReSecondCB.setEnabled(true);
				e2ImSecondCB.setEnabled(true);
				e3ReSecondCB.setEnabled(true);
				e3ImSecondCB.setEnabled(true);
				e4ReSecondCB.setEnabled(true);
				e4ImSecondCB.setEnabled(true);
			}
			
		});
		realSecondRB.setSelected(true);
		
		ButtonGroup groupSecond = new ButtonGroup();
		groupSecond.add(realSecondRB);
		groupSecond.add(idempSecondRB);
		
		// LEs panels pour les coupes tridimensionnelles
		JPanel first3DSlicePanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		title = BorderFactory.createTitledBorder("First 3D slice:");
		first3DSlicePanel.setBorder(title);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = c.gridy = 0;
		first3DSlicePanel.add(realFirstRB, c);
		c.gridx = 1;
		c.gridy = 0;
		first3DSlicePanel.add(idempFirstRB,c);
		c.gridx = 0;
		c.gridy = 1;
		first3DSlicePanel.add(composantFirstPanel,c);
		c.gridx = 1;
		c.gridy = 1;
		first3DSlicePanel.add(composantIdempFirstPanel,c);
		
		
		milieuPanel.add(first3DSlicePanel);
		
		JPanel second3DSlicePanel = new JPanel(new GridBagLayout());
		title = BorderFactory.createTitledBorder("Second 3D slice:");
		second3DSlicePanel.setBorder(title);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = c.gridy = 0;
		second3DSlicePanel.add(realSecondRB, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		second3DSlicePanel.add(idempSecondRB, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		second3DSlicePanel.add(composantSecondPanel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		second3DSlicePanel.add(composantIdempSecondPanel, c);
		
		milieuPanel.add(second3DSlicePanel);
		
		this.getContentPane().add(milieuPanel);
		
		int JTFHorizontal = 30, JTFVertical = 20;
		int JPhorizontal = 165, JPVertical = 100;
		
		JPanel controlPanel = new JPanel();
		JButton okButton = new JButton("Proceed");
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String temp = "2";
				if (realFirstRB.isSelected())
				{
					if (realSecondRB.isSelected())
					{
						sliceDialogInfo = new SliceSequenceJDialogInfo(repositoryJTF.getText(),getComposantSelected() , getComposantSelectedSecond(), "1", incrementJTF.getText(), 
						startAxesValuesJTF.getText(), endAxesValuesJTF.getText(), maxAxesValuesJTF.getText(), startCoucheMinJTF.getText(), endCoucheMinJTF.getText(),
						startCoucheMaxJTF.getText(),endCoucheMaxJTF.getText());
					}
					else
					{
						sliceDialogInfo = new SliceSequenceJDialogInfo(repositoryJTF.getText(),getComposantSelected() , getComposantIdempSelectedSecond(), "2", incrementJTF.getText(), 
								startAxesValuesJTF.getText(), endAxesValuesJTF.getText(), maxAxesValuesJTF.getText(), startCoucheMinJTF.getText(), endCoucheMinJTF.getText(),
								startCoucheMaxJTF.getText(),endCoucheMaxJTF.getText());
					}
				}
				else
				{
					if (realSecondRB.isSelected())
					{
						sliceDialogInfo = new SliceSequenceJDialogInfo(repositoryJTF.getText(),getComposantIdempSelected() , getComposantSelectedSecond(), "3", incrementJTF.getText(), 
								startAxesValuesJTF.getText(), endAxesValuesJTF.getText(), maxAxesValuesJTF.getText(), startCoucheMinJTF.getText(), endCoucheMinJTF.getText(),
								startCoucheMaxJTF.getText(),endCoucheMaxJTF.getText());
					}
					else
					{
						sliceDialogInfo = new SliceSequenceJDialogInfo(repositoryJTF.getText(),getComposantIdempSelected() , getComposantIdempSelectedSecond(), "4", incrementJTF.getText(), 
								startAxesValuesJTF.getText(), endAxesValuesJTF.getText(), maxAxesValuesJTF.getText(), startCoucheMinJTF.getText(), endCoucheMinJTF.getText(),
								startCoucheMaxJTF.getText(),endCoucheMaxJTF.getText());
					}
				}
				framePrincipalMandel.doSliceSequence(sliceDialogInfo);
				setVisible(false);
				System.out.println(sliceDialogInfo.toString());
				sendData = true;
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				setVisible(false);
			}
		});
		
		controlPanel.add(okButton);
		controlPanel.add(cancelButton);
		this.getContentPane().add(controlPanel, BorderLayout.SOUTH);
	}
	
	// 
	public int getCount()
	{
		int count = 0;
		if (reelFirstCB.isSelected())
		{
			reelFirstJTF.setEnabled(false);
			reelFirstJTF.setText("0");
			count++;
		}
		else
		{
			reelFirstJTF.setEnabled(true);
		}
			
		if (im1FirstCB.isSelected())
		{
			im1FirstJTF.setEnabled(false);
			im1FirstJTF.setText("0");
			count++;
		}
		else
		{
			im1FirstJTF.setEnabled(true);
		}
		
		if (im2FirstCB.isSelected())
		{
			im2FirstJTF.setEnabled(false);
			im2FirstJTF.setText("0");
			count++;
		}
		else
		{
			im2FirstJTF.setEnabled(true);
		}
		
		if (im3FirstCB.isSelected())
		{
			im3FirstJTF.setEnabled(false);
			im3FirstJTF.setText("0");
			count++;
		}
		else
		{
			im3FirstJTF.setEnabled(true);
		}
		if (im4FirstCB.isSelected())
		{
			im4FirstJTF.setEnabled(false);
			im4FirstJTF.setText("0");
			count++;
		}
		else
		{
			im4FirstJTF.setEnabled(true);
		}
		
		if (hyp1FirstCB.isSelected())
		{
			hyp1FirstJTF.setEnabled(false);
			hyp1FirstJTF.setText("0");
			count++;
		}
		else
		{
			hyp1FirstJTF.setEnabled(true);
		}
		
		if (hyp2FirstCB.isSelected())
		{
			hyp2FirstJTF.setEnabled(false);
			hyp2FirstJTF.setText("0");
			count++;
		}
		else
		{
			hyp2FirstJTF.setEnabled(true);
		}
		
		if (hyp3FirstCB.isSelected())
		{
			hyp3FirstJTF.setEnabled(false);
			hyp3FirstJTF.setText("0");
			count++;
		}
		else
		{
			hyp3FirstJTF.setEnabled(true);
		}
		return count;
	}
	
	// MÉthode pour le nombre de checkbox coché
		public int getCountSecond()
		{
			int count = 0;
			if (reelSecondCB.isSelected())
			{
				reelSecondJTF.setEnabled(false);
				reelSecondJTF.setText("0");
				count++;
			}
			else
			{
				reelSecondJTF.setEnabled(true);
			}
				
			if (im1SecondCB.isSelected())
			{
				im1SecondJTF.setEnabled(false);
				im1SecondJTF.setText("0");
				count++;
			}
			else
			{
				im1SecondJTF.setEnabled(true);
			}
			
			if (im2SecondCB.isSelected())
			{
				im2SecondJTF.setEnabled(false);
				im2SecondJTF.setText("0");
				count++;
			}
			else
			{
				im2SecondJTF.setEnabled(true);
			}
			
			if (im3SecondCB.isSelected())
			{
				im3SecondJTF.setEnabled(false);
				im3SecondJTF.setText("0");
				count++;
			}
			else
			{
				im3SecondJTF.setEnabled(true);
			}
			if (im4SecondCB.isSelected())
			{
				im4SecondJTF.setEnabled(false);
				im4SecondJTF.setText("0");
				count++;
			}
			else
			{
				im4SecondJTF.setEnabled(true);
			}
			
			if (hyp1SecondCB.isSelected())
			{
				hyp1SecondJTF.setEnabled(false);
				hyp1SecondJTF.setText("0");
				count++;
			}
			else
			{
				hyp1SecondJTF.setEnabled(true);
			}
			
			if (hyp2SecondCB.isSelected())
			{
				hyp2SecondJTF.setEnabled(false);
				hyp2SecondJTF.setText("0");
				count++;
			}
			else
			{
				hyp2SecondJTF.setEnabled(true);
			}
			
			if (hyp3SecondCB.isSelected())
			{
				hyp3SecondJTF.setEnabled(false);
				hyp3SecondJTF.setText("0");
				count++;
			}
			else
			{
				hyp3SecondJTF.setEnabled(true);
			}
			return count;
		}
		
		//Méthode pour le nombre de checkbox coché
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
		
		// MÉthode pour le nombre de checkbox coché
				public int getCountIdempSecond()
				{
					int count = 0;
					if (e1ReSecondCB.isSelected())
					{
						count++;
					}
						
					if( e1ImSecondCB.isSelected())
					{
						count++;
					}
					
					if (e2ReSecondCB.isSelected())
					{
						count++;
					}
					
					if (e2ImSecondCB.isSelected())
					{
						count++;
					}

					if (e3ReSecondCB.isSelected())
					{
						count++;
					}
					
					if (e3ImSecondCB.isSelected())
					{
						count++;
					}
					
					if (e4ReSecondCB.isSelected())
					{
						count++;
					}
					
					if (e4ImSecondCB.isSelected())
					{
						count++;
					}
					return count;
				}
	
	// MÉthode qui permet de rafraîchir l'état des JTF
	public void refreshJTF()
	{
		if (reelFirstCB.isSelected())
		{
			reelFirstJTF.setEnabled(false);
			reelFirstJTF.setText("0");
		}
		else
		{
			reelFirstJTF.setEnabled(true);
		}
			
		if (im1FirstCB.isSelected())
		{
			im1FirstJTF.setEnabled(false);
			im1FirstJTF.setText("0");
		}
		else
		{
			im1FirstJTF.setEnabled(true);
		}
		
		if (im2FirstCB.isSelected())
		{
			im2FirstJTF.setEnabled(false);
			im2FirstJTF.setText("0");
		}
		else
		{
			im2FirstJTF.setEnabled(true);
		}
		
		if (im3FirstCB.isSelected())
		{
			im3FirstJTF.setEnabled(false);
			im3FirstJTF.setText("0");
		}
		else
		{
			im3FirstJTF.setEnabled(true);
		}
		if (im4FirstCB.isSelected())
		{
			im4FirstJTF.setEnabled(false);
			im4FirstJTF.setText("0");
		}
		else
		{
			im4FirstJTF.setEnabled(true);
		}
		
		if (hyp1FirstCB.isSelected())
		{
			hyp1FirstJTF.setEnabled(false);
			hyp1FirstJTF.setText("0");
		}
		else
		{
			hyp1FirstJTF.setEnabled(true);
		}
		
		if (hyp2FirstCB.isSelected())
		{
			hyp2FirstJTF.setEnabled(false);
			hyp2FirstJTF.setText("0");
		}
		else
		{
			hyp2FirstJTF.setEnabled(true);
		}
		
		if (hyp3FirstCB.isSelected())
		{
			hyp3FirstJTF.setEnabled(false);
			hyp3FirstJTF.setText("0");
		}
		else
		{
			hyp3FirstJTF.setEnabled(true);
		}
		
	}
	
	// refresh Text field second slice
	public void refreshJTFSecond()
	{
		if (reelSecondCB.isSelected())
		{
			reelSecondJTF.setEnabled(false);
			reelSecondJTF.setText("0");
		}
		else
		{
			reelSecondJTF.setEnabled(true);
		}
			
		if (im1SecondCB.isSelected())
		{
			im1SecondJTF.setEnabled(false);
			im1SecondJTF.setText("0");
		}
		else
		{
			im1SecondJTF.setEnabled(true);
		}
		
		if (im2SecondCB.isSelected())
		{
			im2SecondJTF.setEnabled(false);
			im2SecondJTF.setText("0");
		}
		else
		{
			im2SecondJTF.setEnabled(true);
		}
		
		if (im3SecondCB.isSelected())
		{
			im3SecondJTF.setEnabled(false);
			im3SecondJTF.setText("0");
		}
		else
		{
			im3SecondJTF.setEnabled(true);
		}
		if (im4SecondCB.isSelected())
		{
			im4SecondJTF.setEnabled(false);
			im4SecondJTF.setText("0");
		}
		else
		{
			im4SecondJTF.setEnabled(true);
		}
		
		if (hyp1SecondCB.isSelected())
		{
			hyp1SecondJTF.setEnabled(false);
			hyp1SecondJTF.setText("0");
		}
		else
		{
			hyp1SecondJTF.setEnabled(true);
		}
		
		if (hyp2SecondCB.isSelected())
		{
			hyp2SecondJTF.setEnabled(false);
			hyp2SecondJTF.setText("0");
		}
		else
		{
			hyp2SecondJTF.setEnabled(true);
		}
		
		if (hyp3SecondCB.isSelected())
		{
			hyp3SecondJTF.setEnabled(false);
			hyp3SecondJTF.setText("0");
		}
		else
		{
			hyp3SecondJTF.setEnabled(true);
		}
		
	}
	
	// refresh Text field second slice
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
		
	// Refresh the second JTF for idemp
		public void refreshJTFSecondIdemp()
		{
			if (e1ReSecondCB.isSelected())
			{
				e1ReSecondJTF.setEnabled(false);
				e1ReSecondJTF.setText("0");
			}
			else
			{
				e1ReSecondJTF.setEnabled(true);
			}
				
			if (e1ImSecondCB.isSelected())
			{
				e1ImSecondJTF.setEnabled(false);
				e1ImSecondJTF.setText("0");
			}
			else
			{
				e1ImSecondJTF.setEnabled(true);
			}
			
			if (e2ReSecondCB.isSelected())
			{
				e2ReSecondJTF.setEnabled(false);
				e2ReSecondJTF.setText("0");
			}
			else
			{
				e2ReSecondJTF.setEnabled(true);
			}
			
			if (e2ImSecondCB.isSelected())
			{
				e2ImSecondJTF.setEnabled(false);
				e2ImSecondJTF.setText("0");
			}
			else
			{
				e2ImSecondJTF.setEnabled(true);
			}
			if (e3ReSecondCB.isSelected())
			{
				e3ReSecondJTF.setEnabled(false);
				e3ReSecondJTF.setText("0");
			}
			else
			{
				e3ReSecondJTF.setEnabled(true);
			}
			
			if (e3ImSecondCB.isSelected())
			{
				e3ImSecondJTF.setEnabled(false);
				e3ImSecondJTF.setText("0");
			}
			else
			{
				e3ImSecondJTF.setEnabled(true);
			}
			
			if (e4ReSecondCB.isSelected())
			{
				e4ReSecondJTF.setEnabled(false);
				e4ReSecondJTF.setText("0");
			}
			else
			{
				e4ReSecondJTF.setEnabled(true);
			}
			
			if (e4ImSecondCB.isSelected())
			{
				e4ImSecondJTF.setEnabled(false);
				e4ImSecondJTF.setText("0");
			}
			else
			{
				e4ImSecondJTF.setEnabled(true);
			}
			
		}	
	
	public boolean[] getComposantSelected()
	{
		boolean[] choixCoupe = new boolean[8];
		choixCoupe[0] = reelFirstCB.isSelected();
		choixCoupe[1] = im1FirstCB.isSelected();
		choixCoupe[2] = im2FirstCB.isSelected();
		choixCoupe[3] = im3FirstCB.isSelected();
		choixCoupe[4] = im4FirstCB.isSelected();
		choixCoupe[5] = hyp1FirstCB.isSelected();
		choixCoupe[6] = hyp2FirstCB.isSelected();
		choixCoupe[7] = hyp3FirstCB.isSelected();
		
		return choixCoupe;
	}
	
	public boolean[] getComposantSelectedSecond()
	{
		boolean[] choixCoupe = new boolean[8];
		choixCoupe[0] = reelSecondCB.isSelected();
		choixCoupe[1] = im1SecondCB.isSelected();
		choixCoupe[2] = im2SecondCB.isSelected();
		choixCoupe[3] = im3SecondCB.isSelected();
		choixCoupe[4] = im4SecondCB.isSelected();
		choixCoupe[5] = hyp1SecondCB.isSelected();
		choixCoupe[6] = hyp2SecondCB.isSelected();
		choixCoupe[7] = hyp3SecondCB.isSelected();
		
		return choixCoupe;
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
	
	public boolean[] getComposantIdempSelectedSecond()
	{
		boolean[] choixCoupe = new boolean[8];
		choixCoupe[0] = e1ReSecondCB.isSelected();
		choixCoupe[1] = e1ImSecondCB.isSelected();
		choixCoupe[2] = e2ReSecondCB.isSelected();
		choixCoupe[3] = e2ImSecondCB.isSelected();
		choixCoupe[4] = e3ReSecondCB.isSelected();
		choixCoupe[5] = e3ImSecondCB.isSelected();
		choixCoupe[6] = e4ReSecondCB.isSelected();
		choixCoupe[7] = e4ImSecondCB.isSelected();
		return choixCoupe;
	}
	
	public double[] getComposantValue()
	{
		double[] values = new double[8];
		String desDoubles = reelFirstJTF.getText() + " " + im1FirstJTF.getText() + 
				" " + im2FirstJTF.getText() + " " + im3FirstJTF.getText() + 
				" " + im4FirstJTF.getText() + " " + hyp1FirstJTF.getText() + " "
				+ hyp2FirstJTF.getText() + " " + hyp3FirstJTF.getText();
		Scanner scan = new Scanner(desDoubles);
		
		//real part
		values[0] = scan.nextDouble();
		//im1 part
		values[1] = scan.nextDouble();
		//im2 part
		values[2] = scan.nextDouble();
		//im3 part
		values[3] = scan.nextDouble();
		//im4 part
		values[4] = scan.nextDouble();
		//hyp1 part
		values[5] = scan.nextDouble();
		//hyp2 part
		values[6] = scan.nextDouble();
		//hyp3 part
		values[7] = scan.nextDouble();
		
		return values;
	}
	
	public double[] getComposantValueSecond()
	{
		double[] values = new double[8];
		String desDoubles = reelSecondJTF.getText() + " " + im1SecondJTF.getText() + 
				" " + im2SecondJTF.getText() + " " + im3SecondJTF.getText() + 
				" " + im4SecondJTF.getText() + " " + hyp1SecondJTF.getText() + " "
				+ hyp2SecondJTF.getText() + " " + hyp3SecondJTF.getText();
		Scanner scan = new Scanner(desDoubles);
		
		//real part
		values[0] = scan.nextDouble();
		//im1 part
		values[1] = scan.nextDouble();
		//im2 part
		values[2] = scan.nextDouble();
		//im3 part
		values[3] = scan.nextDouble();
		//im4 part
		values[4] = scan.nextDouble();
		//hyp1 part
		values[5] = scan.nextDouble();
		//hyp2 part
		values[6] = scan.nextDouble();
		//hyp3 part
		values[7] = scan.nextDouble();
		
		return values;
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
	
	public double[] getComposantIdempValuesSecond()
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
	
	// Getteur
	public boolean getSendData()
	{
		return sendData;
	}
	
	class CheckBoxListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int count = 0;
			
			if (realFirstRB.isSelected())
			{
				count = getCount();
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
				count = getCountIdemp();
				refreshJTFFirstIdemp();
				if (count > 3)
				{
					JOptionPane jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "Vous ne pouvez cocher que 3 composantes" , "ERREUR", JOptionPane.ERROR_MESSAGE);
					JCheckBox objectSelected = (JCheckBox) e.getSource();
					objectSelected.setSelected(false);
					refreshJTFFirstIdemp(); // refresh des JTF
				}
			}
			
			if (realSecondRB.isSelected())
			{
				count = getCountSecond();
				if (count > 3)
				{
					JOptionPane jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "Vous ne pouvez cocher que 3 composantes" , "ERREUR", JOptionPane.ERROR_MESSAGE);
					JCheckBox objectSelected = (JCheckBox) e.getSource();
					objectSelected.setSelected(false);
					refreshJTFSecond(); // refresh des JTF
				}
			}
			else
			{
				count = getCountIdempSecond();
				refreshJTFSecondIdemp();
				if (count > 3)
				{
					JOptionPane jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "Vous ne pouvez cocher que 3 composantes" , "ERREUR", JOptionPane.ERROR_MESSAGE);
					JCheckBox objectSelected = (JCheckBox) e.getSource();
					objectSelected.setSelected(false);
					refreshJTFSecondIdemp(); // refresh des JTF
				}
			}
		}
	}

}
