import java.awt.Graphics;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ImageFrame extends JFrame
{
	protected BufferedImage image;
	protected int sizeL, sizeH;
	protected JPanel imagePanel;
	protected JMenu menuSave;
	protected JMenuBar menuBar;
	protected JScrollPane scrollPane;
	
	public ImageFrame(String title, BufferedImage image)
	{
		super(title);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		imagePanel = new JPanel();
		imagePanel.setSize(400,400);
		
		// Build MenuBar
		initMenuBar();
		// Build ScrollBar
		initScrollBar();
	}
	
	public void setImage(BufferedImage image)
	{
		this.image = image;
		sizeL = image.getWidth();
		sizeH = image.getHeight();
		this.setSize(sizeL + 50, sizeH + 50);
		imagePanel.setSize(sizeL, sizeH);
		this.repaint();
	}
	
	public void paintComponents(Graphics g)
	{
		g.drawImage(image, 0,0, imagePanel);
		super.paintComponents(g);
	}
	
	public void initMenuBar()
	{
		menuBar = new JMenuBar();
		
		// Menu Sauvegarde
		menuSave = new JMenu("File");
		menuBar.add(menuSave);
		
		this.setJMenuBar(menuBar);
	}
	
	public void initScrollBar()
	{
		scrollPane = new JScrollPane(imagePanel);
		this.add(scrollPane);
	}
	
	class SaveActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JOptionPane jop1 = new JOptionPane();
			try
			{
				JFileChooser filechoose = new JFileChooser();
				filechoose.setCurrentDirectory(new File("."));  /* ouvrir la boite de dialogue dans répertoire courant */
				filechoose.setDialogTitle("Save Image in a folder"); /* nom de la boite de dialogue */
				 
				filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); /* pour afficher seulement les répertoires */
				 
				String approve = new String("Save"); /* Le bouton pour valider l’enregistrement portera la mention Enregistrer */
				int resultatEnregistrer = filechoose.showDialog(filechoose, approve); 
				if (resultatEnregistrer == JFileChooser.APPROVE_OPTION)
				{ /* Si l’utilisateur clique sur le bouton Enregistrer */
					String chemin = filechoose.getSelectedFile().getAbsolutePath()+"\\"; /* pour avoir le chemin absolu */
					/* ici il faut appeler une méthode pour écrire dans un fichier
				    	dans mon exemple je l'ai nommé enregistrer_txt et son prototype
				    	c'est void enregistrer_txt(String fichier, String texte)   */
					ImageIO.write(image, "PNG", new File(chemin + "Image3DDeg" + ".PNG"));
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
	}

}
