import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ParametersSavings extends JPanel
{
	protected String parametersSaves;
	protected JTextArea textArea;
	protected JButton saveButton;
	protected JScrollPane scrollPanel;
	protected Parametres paramParent;
	protected ImageCaracteristique imageParent;
	protected Calendar cal;
	protected int count;
	//protected Date currentTime;
	
	public ParametersSavings(String textAreaInitializer, Parametres paramParent, ImageCaracteristique imageParent)
	{
		cal = Calendar.getInstance();
		parametersSaves = textAreaInitializer + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "\n";
		this.paramParent = paramParent;
		this.imageParent = imageParent;
		this.setPreferredSize(new Dimension(335,335));
		this.setLayout(new BorderLayout());
		
		this.count = 0;
		//currentTime = cal.getTime();
		
		// initialize textarea
		textArea = new JTextArea(parametersSaves);
		//textArea.setPreferredSize(new Dimension(325,225));
		
		// Initialize scroll Panel
		scrollPanel = new JScrollPane(textArea);
		
		// Initialize saveButton
		saveButton = new JButton("Save");
		saveButton.addActionListener(new SaveParametersListener());
		
		// Positioning
		this.add(scrollPanel, BorderLayout.CENTER);
		this.add(saveButton, BorderLayout.SOUTH);
	}
	
	// getteurs
	public String getTextArea()
	{
		return parametersSaves;
	}
	
	// SavePAramListener class
	class SaveParametersListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			count++;
			parametersSaves += count + ".";
			parametersSaves += paramParent.toString();
			parametersSaves += imageParent.toString();
			parametersSaves += "\n";
			
			textArea.setText(parametersSaves);
		}
	}

}
