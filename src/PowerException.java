import javax.swing.JOptionPane;


public class PowerException extends Exception
{
	public PowerException(String message)
	{
		super(message);
	}
	
	public void afficherMessageErreur()
	{
		JOptionPane.showMessageDialog(null, this.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
	}

}
