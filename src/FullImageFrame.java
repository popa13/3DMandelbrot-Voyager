import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FullImageFrame extends JFrame
{
	private BufferedImage image;
	private JScrollPane scrollPane;
	private JPanel imagePanel;
	
	public FullImageFrame(String title, BufferedImage image)
	{
		super(title);
		this.setSize(new Dimension(400,400));
		this.image = image;
		imagePanel = new JPanel();
		this.scrollPane = new JScrollPane(imagePanel);
		this.getContentPane().add(scrollPane);
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(image, 5, 5, null);
	}
	
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}
}
