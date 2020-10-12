import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class RotationSequenceJDialog extends JDialog
{
	private FrameApp framePrincipalMandel;
	private RotationSequenceJDialogInfo rotDialogInfo= new RotationSequenceJDialogInfo();
	
	private JLabel repositoryJL, rotationXJL, rotationYJL, rotationZJL;
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
	
	public RotationSequenceJDialog(JFrame parent, String title, boolean modal, FrameApp framePrincipal)
	{
		super(parent, title, modal);
		this.setSize(500, 200);
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
		
		int JTFHorizontal = 30, JTFVertical = 20;
		int JPhorizontal = 165, JPVertical = 100;
		
		JPanel rotXPanel = new JPanel();
		TitledBorder titleX = BorderFactory.createTitledBorder("Rotation in X");
		rotXPanel.setBorder(titleX);
		rotXPanel.setPreferredSize(new Dimension(JPhorizontal, JPVertical));
		startXJL = new JLabel("From: ");
		startXJTF = new JTextField("0");
		startXJTF.setPreferredSize(new Dimension(JTFHorizontal,JTFVertical));
		endXJL = new JLabel("to: ");
		endXJTF = new JTextField("90");
		endXJTF.setPreferredSize(new Dimension(JTFHorizontal,JTFVertical));
		incrementXJL = new JLabel("Increment: ");
		incrementXJTF = new JTextField("10");
		incrementXJTF.setPreferredSize(new Dimension(JTFHorizontal, JTFVertical));
		rotXPanel.add(startXJL);
		rotXPanel.add(startXJTF);
		rotXPanel.add(endXJL);
		rotXPanel.add(endXJTF);
		rotXPanel.add(incrementXJL);
		rotXPanel.add(incrementXJTF);
		this.getContentPane().add(rotXPanel, BorderLayout.WEST);
		
		JPanel rotYPanel = new JPanel();
		TitledBorder titleY = BorderFactory.createTitledBorder("Rotation in Y");
		rotYPanel.setBorder(titleY);
		rotYPanel.setPreferredSize(new Dimension(JPhorizontal, JPVertical));;
		startYJL = new JLabel("From: ");
		startYJTF = new JTextField("0");
		startYJTF.setPreferredSize(new Dimension(JTFHorizontal, JTFVertical));
		endYJL = new JLabel("to: ");
		endYJTF = new JTextField("90");
		endYJTF.setPreferredSize(new Dimension(JTFHorizontal, JTFVertical));
		incrementYJL = new JLabel("Increment: ");
		incrementYJTF = new JTextField("10");
		incrementYJTF.setPreferredSize(new Dimension(JTFHorizontal, JTFVertical));
		rotYPanel.add(startYJL);
		rotYPanel.add(startYJTF);
		rotYPanel.add(endYJL);
		rotYPanel.add(endYJTF);
		rotYPanel.add(incrementYJL);
		rotYPanel.add(incrementYJTF);
		this.getContentPane().add(rotYPanel, BorderLayout.CENTER);
		
		JPanel rotZPanel = new JPanel();
		TitledBorder titleZ = BorderFactory.createTitledBorder("Rotation in Z");
		rotZPanel.setBorder(titleZ);
		rotZPanel.setPreferredSize(new Dimension(JPhorizontal, JPVertical));
		startZJL = new JLabel("From: ");
		startZJTF = new JTextField("0");
		startZJTF.setPreferredSize(new Dimension(JTFHorizontal, JTFVertical));
		endZJL = new JLabel("to: ");
		endZJTF = new JTextField("90");
		endZJTF.setPreferredSize(new Dimension(JTFHorizontal, JTFVertical));
		incrementZJL = new JLabel("Increment: ");
		incrementZJTF = new JTextField("10");
		incrementZJTF.setPreferredSize(new Dimension(JTFHorizontal, JTFVertical));
		rotZPanel.add(startZJL);
		rotZPanel.add(startZJTF);
		rotZPanel.add(endZJL);
		rotZPanel.add(endZJTF);
		rotZPanel.add(incrementZJL);
		rotZPanel.add(incrementZJTF);
		this.getContentPane().add(rotZPanel, BorderLayout.EAST);
		
		JPanel controlPanel = new JPanel();
		JButton okButton = new JButton("Proceed");
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				rotDialogInfo = new RotationSequenceJDialogInfo(repositoryJTF.getText(),startXJTF.getText(), endXJTF.getText(),
						startYJTF.getText(), endYJTF.getText(), startZJTF.getText(), endZJTF.getText(),
						incrementXJTF.getText(), incrementYJTF.getText(), incrementZJTF.getText());
				framePrincipalMandel.doRotationSequence(rotDialogInfo);
				setVisible(false);
				System.out.println(rotDialogInfo.toString());
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
	
	// Getteur
	public boolean getSendData()
	{
		return sendData;
	}
}
