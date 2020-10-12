
public class Rotation
{
	protected double rotX, rotY, rotZ;
	protected double rotDX, rotDY, rotDZ;
	protected double[] endPoint;
	
	public Rotation(double rotX, double rotY, double rotZ, int typeAngle)
	{
		// En degré
		if (typeAngle == 1)
			setAngleDeg(rotX, rotY, rotZ);
		else if(typeAngle == 2)
			setAngleRad(rotX, rotY, rotZ);
		else
			setAngleDeg(rotX, rotY, rotZ);
		endPoint = new double[3];
	}
	
	// Méthode setteur
	public void setAngleDeg(double rotDX, double rotDY, double rotDZ)
	{
		this.rotDX = rotDX;
		this.rotDY = rotDY;
		this.rotDZ = rotDZ;
		
		this.rotX = rotDX * Math.PI / 180;
		this.rotY = rotDY * Math.PI / 180;
		this.rotZ = rotDZ * Math.PI / 180;
	}
	
	public void setAngleRad(double rotX, double rotY, double rotZ)
	{
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		
		rotDX = (double)((180 * rotX)/Math.PI);
		rotDY = (double)((180 * rotY)/Math.PI);
		rotDZ = (double)((180 * rotZ)/Math.PI);
	}
	
	//getteur
	public double[] getAngleDeg()
	{
		double[] rot = new double[3];
		rot[0] = rotDX;
		rot[1] = rotDY;
		rot[2] = rotDZ;
		
		return rot;
	}
	
	public double[] getAngleRad()
	{
		double[] rot = new double[3];
		rot[0] = rotX;
		rot[1] = rotY;
		rot[2] = rotZ;
		
		return rot;
	}
	
	public double getEndPointX()
	{
		return endPoint[0];
	}
	
	public double getEndPointY()
	{
		return endPoint[1];
	}
	
	public double getEndPointZ()
	{
		return endPoint[2];
	}
	
	// Méthode
	public void doRotation(double x, double y, double z)
	{		
		doRotationX(x,y,z);
		doRotationY(endPoint[0], endPoint[1], endPoint[2]);
		doRotationZ(endPoint[0], endPoint[1], endPoint[2]);
	}
	
	protected void doRotationX(double x, double y, double z)
	{
		endPoint[0] = x;
		endPoint[1] = Math.cos(rotX) * y - Math.sin(rotX) * z;
		endPoint[2] = Math.sin(rotX) * y + Math.cos(rotX) * z;
	}
	
	protected void doRotationY(double x, double y, double z)
	{
		endPoint[0] = Math.cos(rotY) * x - Math.sin(rotY) * z;
		endPoint[1] = y;
		endPoint[2] = Math.sin(rotY) * x + Math.cos(rotY) * z;
	}
	
	protected void doRotationZ(double x, double y, double z)
	{
		endPoint[0] = Math.cos(rotZ) * x - Math.sin(rotZ) * y;
		endPoint[1] = Math.sin(rotZ) * x + Math.cos(rotZ) * y;
		endPoint[2] = z;
	}
	
	// to string
	public String toString()
	{
		String rotationString = new String();
		rotationString = "           " + "X" + "   " + "Y" +  "   " + "Z\n";
		rotationString = rotationString + "In Degree :" + rotDX + " " + rotDY + " " + rotDZ + "\n";
		rotationString = rotationString + "In radian :" + rotX + " " + rotY + " " + rotZ;
		return rotationString;
	}

}
