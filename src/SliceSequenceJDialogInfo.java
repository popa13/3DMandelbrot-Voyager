import java.util.Scanner;

import javax.swing.JOptionPane;

public class SliceSequenceJDialogInfo
{
	// Variables d'instance
		protected String repositoryName;
		protected boolean[] coupeChoiceFirst;
		protected boolean[] coupeChoiceSecond;
		protected String typeOfSequence;
		protected String increment, startAxesValues, endAxesValues, maxAxesValues;
		protected String startCoucheMin, endCoucheMin, startCoucheMax, endCoucheMax;
		
		public SliceSequenceJDialogInfo()
		{
			
		}
		
		public SliceSequenceJDialogInfo(String repositoryName,
				boolean[] coupeChoiceFirst, boolean[] coupeChoiceSecond, String typeOfSequence, String increment, String startAxesValues, String endAxesValues, String maxAxesValues,
				String startCoucheMin, String endCoucheMin, String startCoucheMax, String endCoucheMax)
		{
			this.repositoryName = repositoryName;
			this.coupeChoiceFirst = coupeChoiceFirst;
			this.coupeChoiceSecond = coupeChoiceSecond;
			this.typeOfSequence = typeOfSequence;
			this.increment = increment;
			this.startAxesValues = startAxesValues;
			this.endAxesValues = endAxesValues;
			this.maxAxesValues = maxAxesValues;
			this.startCoucheMin = startCoucheMin;
			this.endCoucheMin = endCoucheMin;
			this.startCoucheMax = startCoucheMax;
			this.endCoucheMax = endCoucheMax;
		}
		
		public String getRepository()
		{
			return repositoryName;
		}
		
		public boolean[] getCoupeFirst()
		{
			return coupeChoiceFirst;
		}
		
		public boolean[] getCoupeSecond()
		{ 
			return coupeChoiceSecond;
		}
		
		public int getTypeOfSequence()
		{
			return  Integer.parseInt(typeOfSequence);
		}
		
		public double getIncrement()
		{
			double crement;
			Scanner scan = new Scanner(increment);
			
			crement = scan.nextDouble();
			scan.close();
			return crement;
		}
		
		public double getStartAxes()
		{
			double unDouble;
			Scanner scan = new Scanner(startAxesValues);
			unDouble = scan.nextDouble();
			return unDouble;
		}
		
		public double getEndAxes()
		{
			double unDouble;
			Scanner scan = new Scanner(endAxesValues);
			unDouble = scan.nextDouble();
			return unDouble;
		}
		
		public double getMaxAxes()
		{
			double unDouble;
			Scanner scan = new Scanner(maxAxesValues);
			unDouble = scan.nextDouble();
			return unDouble;
		}
		
		public int getStartCoucheMin()
		{
			return Integer.parseInt(startCoucheMin);
		}
		
		public int getEndCoucheMin()
		{
			return Integer.parseInt(endCoucheMin);
		}
		
		public int getStartCoucheMax()
		{
			return Integer.parseInt(startCoucheMax);
		}
		
		public int getEndCoucheMax()
		{
			return Integer.parseInt(endCoucheMax);
		}
		
		public String toString()
		{
			String allInfo = "Repository Name : " + repositoryName + "\n";
			
			allInfo = allInfo + "First slice : ";
			if (coupeChoiceFirst[0])
				allInfo += "1 ";
			else if (coupeChoiceFirst[1])
				allInfo += "i1 ";
			else if (coupeChoiceFirst[2])
				allInfo += "i2 ";
			else if (coupeChoiceFirst[3])
				allInfo += "i3 ";
			else if (coupeChoiceFirst[4])
				allInfo += "i4 ";
			else if (coupeChoiceFirst[5])
				allInfo += "j1 ";
			else if (coupeChoiceFirst[6])
				allInfo += "j2 ";
			else 
				allInfo += "j3 ";
			allInfo += "\n";
			
			allInfo = allInfo + "Second slice : ";
			if (coupeChoiceSecond[0])
				allInfo += "1 ";
			else if (coupeChoiceSecond[1])
				allInfo += "i1 ";
			else if (coupeChoiceSecond[2])
				allInfo += "i2 ";
			else if (coupeChoiceSecond[3])
				allInfo += "i3 ";
			else if (coupeChoiceSecond[4])
				allInfo += "i4 ";
			else if (coupeChoiceSecond[5])
				allInfo += "j1 ";
			else if (coupeChoiceSecond[6])
				allInfo += "j2 ";
			else 
				allInfo += "j3 ";
			allInfo += "\n";
				
			allInfo += "Number of Images : " + increment;
			
			return allInfo;
		}
}
