import java.util.Scanner;

import javax.swing.JOptionPane;

public class RotationSequenceJDialogInfo 
{
	// Variables d'instance
	private String repositoryName, fromXRot, toXRot, fromYRot, toYRot,
	fromZRot, toZRot, stepX, stepY, stepZ;
	
	public RotationSequenceJDialogInfo()
	{
		
	}
	
	public RotationSequenceJDialogInfo(String repositoryName,
			String fromXRot, String toXRot, String fromYRot, String toYRot,
			String fromZRot, String toZRot, String stepX, String stepY, String stepZ)
	{
		this.repositoryName = repositoryName;
		this.fromXRot = fromXRot;
		this.toXRot = toXRot;
		this.fromYRot = fromYRot;
		this.toYRot = toYRot;
		this.fromZRot = fromZRot;
		this.toZRot = toZRot;
		this.stepX = stepX;
		this.stepY = stepY;
		this.stepZ = stepZ;
	}
	
	public String getRepository()
	{
		return repositoryName;
	}
	
	public double[] getFromToXRot()
	{
		double[] rot = new double[3];
		if(fromXRot != null && toXRot != null && stepX != null)
		{
			Scanner scan = new Scanner(fromXRot + " " + toXRot + " " + stepX);
		
			rot[0] = scan.nextDouble();
			rot[1] = scan.nextDouble();
			rot[2] = scan.nextDouble();
		
			scan.close();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Aucune information");
		}
		
		return rot;
	}
	
	public double[] getFromToYRot()
	{
		double[] rot = new double[3];
		if (fromYRot != null && toYRot != null && stepY != null)
		{
			Scanner scan = new Scanner(fromYRot + " " + toYRot + " " + stepY);
		
			rot[0] = scan.nextDouble();
			rot[1] = scan.nextDouble();
			rot[2] = scan.nextDouble();
		
			scan.close();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Aucune information");
		}
		
		return rot;
	}
	
	public double[] getFromToZRot()
	{
		double[] rot = new double[3];
		if(fromZRot != null && toZRot != null && stepZ != null)
		{
			Scanner scan = new Scanner(fromZRot + " " + toZRot + " " + stepZ);
		
			rot[0] = scan.nextDouble();
			rot[1] = scan.nextDouble();
			rot[2] = scan.nextDouble();
			
			System.out.println(rot[0] + " " + rot[1] + " " + rot[2]);
			scan.close();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Aucune information");
		}
		
		return rot;
	}
	
	public String toString()
	{
		String allInfo = "Rotation in X: from " + fromXRot + " to " + toXRot + "\n"
				+ "Rotation in Y: from " + fromYRot + " to " + toYRot + "\n"
				+ "Rotation in Z: from " + fromZRot + " to " + toZRot + "\n";
		return allInfo;
	}
}
