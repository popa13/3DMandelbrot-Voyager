import javax.swing.JOptionPane;

public class Main 
{	
	public static void main(String[] args)
	{
		
		FrameApp frameApp = new FrameApp(250, 250);
		
	}

}

// Ancien code pour inclure les julia tricomplexes
/*
Object[] possibilities = {"Mandelbrot Sets", "Julia Sets", "None of these..."};
		
		int choice = JOptionPane.showOptionDialog(null, "Choose the type of set you want:", "Options for sets", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null,  possibilities, possibilities[2]);
		Object frameApp;
		
		if (choice == 0)
	else if (choice == 1)
			frameApp = new FrameAppJulia(250,250);
		else
		{
			JOptionPane.showMessageDialog(null, "Cancelling put an end to the App. GOOD BYE!", "Cancel Message", JOptionPane.CANCEL_OPTION,
					null);
			System.exit(0);
		}
*/