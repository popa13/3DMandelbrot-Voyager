import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;


public class Image extends JPanel
{
	protected BufferedImage[] image;
	protected String[] imageName;
	protected ImagePanel imagePanel;
	protected JPanel northPanel;
	protected JProgressBar progressBar;
	protected JLabel progressBarLabel;
	protected int[] sizeL;
	protected int[] sizeH;
	protected boolean onFrame, isPowerSequence;
	protected int nbImageMax;
	protected int nbImage;
	protected int currentIndexImage;
	protected JButton leftArrowButton, rightArrowButton;
	protected JPanel buttonPanel, centerPanel;
	protected JLabel nbImageOverMaxImage;
	protected JLabel imageNameLabel;
	
	public Image(int sizeL, int sizeH, int nbImageMax)
	{
		this.setPreferredSize(new Dimension(350,385));
		//this.setBackground(Color.BLUE);
		
		this.nbImageMax = nbImageMax;
		this.currentIndexImage = 1;
		this.nbImage = 1;
		
		image = new BufferedImage[nbImageMax];
		this.sizeL = new int[nbImageMax];
		this.sizeH = new int[nbImageMax];
		this.imageName = new String[nbImageMax];
		
		for (int i = 0; i < nbImageMax; i++)
		{
			this.imageName[i] = "";
			this.sizeL[i] = sizeL;
			this.sizeH[i] = sizeH;
			image[i] = new BufferedImage(sizeL + 1, sizeH + 1, BufferedImage.TYPE_INT_ARGB);
		}
		
		this.onFrame = false;
		this.isPowerSequence = false;
		
		TitledBorder title = BorderFactory.createTitledBorder("Image Render");
		this.setBorder(title);
		
		this.centerPanel = new JPanel();
		this.centerPanel.setPreferredSize(new Dimension(260, 280));
				
		this.imagePanel = new ImagePanel(251);
		this.imageNameLabel = new JLabel(imageName[0]);
		
		this.centerPanel.add(imagePanel, BorderLayout.CENTER);
		this.centerPanel.add(imageNameLabel, BorderLayout.SOUTH);
		
		//imagePanel.setPreferredSize(new Dimension(251,251));
		
		this.northPanel = new JPanel();
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBarLabel = new JLabel("% of completed task: ");
		
		northPanel.add(progressBarLabel);
		northPanel.add(progressBar);
		
		buttonPanel = new JPanel();
		leftArrowButton = new JButton("Previous");
		this.leftArrowButton.addActionListener(new LeftButtonActionListener());
		rightArrowButton = new JButton("Next");
		this.rightArrowButton.addActionListener(new RightButtonActionListener());
		this.rightArrowButton.setEnabled(false);
		this.leftArrowButton.setEnabled(false);
		this.nbImageOverMaxImage = new JLabel("" + (currentIndexImage) + " on " + nbImage);
		buttonPanel.add(leftArrowButton);
		buttonPanel.add(rightArrowButton);
		buttonPanel.add(nbImageOverMaxImage);
		
		//this.add(progressBarLabel, BorderLayout.NORTH);
		//this.add(progressBar, BorderLayout.NORTH);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public boolean setImage(BufferedImage newImage)
	{
		if (currentIndexImage < 1 || currentIndexImage > nbImageMax)
		{
			JOptionPane.showMessageDialog(null, "Process Avoided", "No more places", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		
		if (currentIndexImage == 0)
		{
			image[0] = newImage;
			this.sizeL[0] = newImage.getWidth();
			this.sizeH[0] = newImage.getHeight();
		}
		else
		{
			image[currentIndexImage -1] = newImage;
			this.sizeL[currentIndexImage -1] = newImage.getWidth();
			this.sizeH[currentIndexImage -1] = newImage.getHeight();
		}
		
		imagePanel.repaint();
		this.repaint();
		return false;
	}
	
	public boolean setNameImage(String name)
	{
		if (currentIndexImage < 1 || currentIndexImage > nbImageMax)
		{
			JOptionPane.showMessageDialog(null, "Process Avoided", "No more places", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		else
		{
			if (currentIndexImage == 0)
			{
				imageName[0] = name;
				imageNameLabel.setText("coupe " + imageName[0]);
			}
			else
			{
				imageName[currentIndexImage - 1] = name;
				imageNameLabel.setText("coupe " + imageName[currentIndexImage - 1]);
			}
			return false;
		}
		
	}
	
	public void nextImage()
	{
		if(nbImage == nbImageMax)
			nbImage = nbImageMax;
		else if (currentIndexImage == nbImage)
			nbImage++;
		{
			//do nothing
		}
		currentIndexImage++;
		if (currentIndexImage > nbImageMax)
		{
			currentIndexImage = nbImage;
		}
		nbImageOverMaxImage.setText("" + currentIndexImage + " on " + nbImage);
		imageNameLabel.setText("coupe" + imageName[currentIndexImage - 1]);
	}
	
	public void lastImage()
	{
		if(currentIndexImage <= 1)
		{
			currentIndexImage = 2;
		}
		//throw new ImageIndexOutOfBounds("No level down 1");
		currentIndexImage--;
		nbImageOverMaxImage.setText("" + currentIndexImage + " on " + nbImage);
		imageNameLabel.setText("coupe" + imageName[currentIndexImage - 1]);
	}
	
	public void isEnabledButton(boolean etat)
	{
		if (currentIndexImage == nbImage)
		{
			this.rightArrowButton.setEnabled(false);
			this.leftArrowButton.setEnabled(true);
		}
		else if (currentIndexImage <= 1)
		{
			this.leftArrowButton.setEnabled(false);
			this.rightArrowButton.setEnabled(true);
		}
		else if (nbImage == 1)
		{
			this.leftArrowButton.setEnabled(false);
			this.rightArrowButton.setEnabled(false);
		}
		else
		{
			this.rightArrowButton.setEnabled(etat);
			this.leftArrowButton.setEnabled(etat);
		}
	}
	
	public void setEnabledButton(boolean etat)
	{
		this.rightArrowButton.setEnabled(etat);
		this.leftArrowButton.setEnabled(etat);
	}
	
	public int getNbImage()
	{
		return nbImage;
	}
	
	public BufferedImage getImage()
	{
		return image[currentIndexImage - 1];
	}
	
	public BufferedImage[] getAllImage()
	{
		int tempIndex = nbImage;
		BufferedImage[] tempImages = new BufferedImage[nbImage];
		for (int i = 0; i < nbImage; i++)
		{
			tempImages[i] = new BufferedImage(image[i].getWidth(), image[i].getHeight(), BufferedImage.TYPE_INT_ARGB);
			tempImages[i] = image[i];
		}
		return tempImages;
	}
	
	public String[] getAllNames()
	{
		int tempIndex = nbImage;
		String[] names = new String[tempIndex];
		for(int i = 0; i < nbImage; i++)
		{
			names[i] = imageName[i];
		}
		return names;
	}
	
	/*public void paintComponent(Graphics g)
	{
		//super.paintComponent(g);
		//imagePanel.repaint();
		Graphics g2 = imagePanel.getGraphics();
		if (nbImage == 0)
		{
			int dx = (int) ((((double)251- sizeL[0])/251) * 125) + 37;
			int dy = (int)((((double)251 - sizeH[0])/251) * 125) + 50;
			
			if(image[0].getHeight() > 251 || image[0].getWidth() > 251)
				g2.drawImage(this.scale(image[0],  (double)251/image[0].getHeight()), 37, 50, imagePanel);
			else 
				g2.drawImage(image[0], dx, dy, imagePanel);
		}
		else
		{
			int dx = (int) ((((double)251- sizeL[nbImage -1])/251) * 125) + 37;
			int dy = (int)((((double)251 - sizeH[nbImage -1])/251) * 125) + 50;
		
			if(image[nbImage -1].getHeight() > 251 || image[nbImage -1].getWidth() > 251)
				g2.drawImage(this.scale(image[nbImage -1],  (double)251/image[nbImage -1].getHeight()), 37, 50, imagePanel);
			else 
				g2.drawImage(image[nbImage -1], dx, dy, imagePanel);
	
		}
	}*/
	
	public BufferedImage scale(BufferedImage bi, double scaleValue)
	{
        AffineTransform tx = new AffineTransform();
        tx.scale(scaleValue, scaleValue);
        AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
        BufferedImage biNew = new BufferedImage( (int) (bi.getWidth() * scaleValue),
                (int) (bi.getHeight() * scaleValue),
                bi.getType());
        return op.filter(bi, biNew);
    }
	
	public JProgressBar getProgressBar()
	{
		return progressBar;
	}
	
	public void setOnFrame(boolean etat)
	{
		onFrame = etat;
	}
	
	public void setIsPowerSequence(boolean etat)
	{
		isPowerSequence = etat;
	}
	
	class ImagePanel extends JPanel
	{
		public ImagePanel(int dimension)
		{
			this.setPreferredSize(new Dimension(251,251));
		}
		
		public void paintComponent(Graphics g)
		{
			if (currentIndexImage == 0)
			{
				int dx = (int) ((((double)251- sizeL[0])/251) * 125);
				int dy = (int)((((double)251 - sizeH[0])/251) * 125);
				
				if(image[0].getHeight() > 251 || image[0].getWidth() > 251)
					g.drawImage(scale(image[0],  (double)251/image[0].getHeight()), 0, 0, this);
				else 
					g.drawImage(image[0], dx, dy, this);
			}
			else if (currentIndexImage <= nbImageMax)
			{
				int dx = (int) ((((double)251- sizeL[currentIndexImage -1])/251) * 125);
				int dy = (int)((((double)251 - sizeH[currentIndexImage -1])/251) * 125);
			
				if(image[currentIndexImage -1].getHeight() > 251 || image[currentIndexImage -1].getWidth() > 251)
					g.drawImage(scale(image[currentIndexImage -1],  (double)251/image[currentIndexImage -1].getHeight()), 0, 0, this);
				else 
					g.drawImage(image[currentIndexImage -1], dx, dy, this);
			}
		}
	}
	
	class LeftButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(currentIndexImage >= 1)
			{
				lastImage();
				if (currentIndexImage == nbImage)
				{
					rightArrowButton.setEnabled(false);
					leftArrowButton.setEnabled(true);
				}
				else if (currentIndexImage == 1)
				{
					leftArrowButton.setEnabled(false);
					rightArrowButton.setEnabled(true);
				}
				else
				{
					rightArrowButton.setEnabled(true);
					leftArrowButton.setEnabled(true);
				}
				repaint();
				revalidate();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Impossible ! There is no image underneath.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class RightButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(currentIndexImage <= nbImageMax)
			{
				nextImage();
				if (currentIndexImage == nbImage)
				{
					rightArrowButton.setEnabled(false);
					leftArrowButton.setEnabled(true);
				}
				else if (currentIndexImage == 1)
				{
					leftArrowButton.setEnabled(false);
					rightArrowButton.setEnabled(true);
				}
				else
				{
					rightArrowButton.setEnabled(true);
					leftArrowButton.setEnabled(true);
				}
				repaint();
				revalidate();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Impossible! There is no more images.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
