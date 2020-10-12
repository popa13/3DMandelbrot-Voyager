import javax.swing.JOptionPane;


public class SameAxesValuesException extends Exception
{
	public SameAxesValuesException(String message)
	{
		super(message);
	}
	
	public void afficherMessageErreur()
	{
		JOptionPane.showMessageDialog(null, this.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
	}
}
