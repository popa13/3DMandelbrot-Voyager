/*
 *  COMMENTAIRES : - Séparer le code en plusieur morceau dans la méthode run. Ceci améliorera les performances
 *  				 en enlevant des conditions à vérifier à chaque tour de boucle
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class Tribrot implements Runnable
{
	protected Image imagePanel;
	protected ImageFrame imageFrame;
	protected FrameApp principalFrame;
	//protected int[][][] matrixImage;
	
	//Declaration des variables d'instances: 
		//					-une image pixel;
		//					-un entier pour la puissance;
		//					-une chaine de caractere pour le message à enregistrer dans la sauvegarde et;
		//					-un objet de format pour formater a deux chiffre apres la virgule les nombres reels
	protected BufferedImage I;
	//protected BufferedImage baseI;
	protected int p, p2,cpu;
	protected boolean doPowerSequence;
	protected String message;
	protected DecimalFormat frm= new DecimalFormat(".##");
	protected double limModIter, limModIterS, factz;
	protected static int MAXITER1=100;
	protected static int MAXITER2=100;
	protected long tempsDebut,tempsFin,temps;
	protected boolean[] coupe;
	protected double[] componentValues;
	protected int style;
	protected int pourcentageInt;
	
	protected int ck;
	protected int[] ck1=new int[2],ck2=new int[2],ck3=new int[2],ck4=new int[2],ckTot=new int[2];
	protected Tricomplex c;
	protected double pX, pY, pZ;
	protected float Brightness;
	double cX,cY;
	int xTemp, yTemp;
	
	protected int[][] zoom;
	protected int[] dimension;
	protected double[] axes;
	protected double[][] axesPower;
	protected double[] modulusPower;
	
	protected int minIter,maxIter;
	
	protected static PalletteC pal;
	protected static float[] tabHSB, tabHSB2;
	protected double[] rot;
	protected Rotation rotOp;
	
	protected Color col;
	protected JProgressBar progress;
	
	protected boolean stop, stopErreur;
	protected boolean viewProcessSlice, viewProcessPoint, viewProcessLine, viewProcessNo;
	protected boolean renderInFrame;
	protected boolean isThereRotation;
	protected boolean isRenderOctaedreThm;
	private int nbBrot;
	private double[] minModulus;
	private boolean viewAxes;
	private double totalIncrement;
	private double[] increment;
	private double[] rotEnd;
	private String chemin;
	private boolean renderRotationSequence;
	protected boolean renderSliceSequence;
	protected boolean renderDistanceEst;
	private double[] componentValuesStart;
	private boolean[] coupeStart;
	private boolean[] coupeEnd;
	private double[] componentValuesEnd;
	private double startAxesValues;
	private double endAxesValues;
	private double maxAxesValues;
	
	private double lightX, lightY, lightZ, normLight;
	private double epsilon;
	private int[] coucheMax;
	private int[] coucheMin;
	private int typeOfSequence;
	private int typeOfSlice;
	
	public Tribrot(Image imagePanel, ImageFrame imageFrame, FrameApp principalFrame)
	{
        this.imagePanel = imagePanel;
        this.imageFrame = imageFrame;
        this.principalFrame = principalFrame;
        pal = new PalletteC(Tribrot.getMaxIterM() + 1, 1000, 3);
		pal.setHSB();
		tabHSB = pal.getPalHSB();
		tabHSB2 = pal.getPalHSB2();
		stop = false;
		stopErreur = false;
		renderInFrame = false;
		isThereRotation = false;
		isRenderOctaedreThm = false;
		renderDistanceEst = false;
		viewAxes = false;
		this.progress = imagePanel.getProgressBar();
		
		c = new Tricomplex();
		this.rot = new double[3];
		this.rotEnd = new double[3];
		this.increment = new double[3];
		
		lightX = lightY = lightZ = 2;
		normLight = Math.sqrt(lightX * lightX + lightY * lightY + lightZ * lightZ);
		
		//this.baseI = I;
	}
	
	public void setParam(int p, int p2, boolean doPowerSequence, boolean[] coupe, double[] componentValues, int style,int minIter, int maxIter, 
			int[] dimension, double[] axes, int pourcentageInt, double[] rot, int viewProcess, boolean viewAxes, boolean renderInFrame,
			boolean isrenderOctaedreThm, double[] light, int typeOfSlice)
	{
		
		this.dimension = dimension;
		this.I = new BufferedImage(dimension[0] + 1, dimension[1] + 1, BufferedImage.TYPE_INT_ARGB);
		//resetImage();
		//this.matrixImage = new int[dimension[0] + 1][dimension[1] + 1][dimension[2] + 1];
		this.renderInFrame = renderInFrame;
		this.viewAxes = viewAxes;
		this.isRenderOctaedreThm = isrenderOctaedreThm;
		this.renderRotationSequence = false;
		this.renderSliceSequence = false;
		this.renderDistanceEst = false;
		
		this.lightX = light[0];
		this.lightY = light[1];
		this.lightZ = light[2];
		
		this.typeOfSlice = typeOfSlice;
		
		switch (viewProcess)
		{
		case 1:
			viewProcessNo = true;
			viewProcessLine = false;
			viewProcessPoint = false;
			viewProcessSlice = false;
			break;
		case 2:
			viewProcessPoint = true;
			viewProcessLine = false;
			viewProcessSlice = false;
			viewProcessNo = false;
			break;
		case 3:
			viewProcessLine = true;
			viewProcessPoint = false;
			viewProcessSlice = false;
			viewProcessNo = false;
			break;
		case 4:
			viewProcessSlice = true;
			viewProcessLine = false;
			viewProcessPoint = false;
			viewProcessNo = false;
			break;
			default:
				viewProcessNo = true;
				viewProcessPoint = viewProcessLine = viewProcessSlice = false;
		}
		
		this.p = p;
		this.p2 = p2;
		this.nbBrot = p2 - p + 1;
		this.doPowerSequence = doPowerSequence;
		this.coupe = coupe;
		this.message = "";
		if (coupe[0])
			message += "1";
		if (coupe[1])
			message += "i1";
		if (coupe[2])
			message += "i2";
		if (coupe[3])
			message += "i3";
		if (coupe[4])
			message += "i4";
		if (coupe[5])
			message += "j1";
		if (coupe[6])
			message += "j2";
		if (coupe[7])
			message += "j3";
		this.componentValues = componentValues;
		this.style = style;
		this.minIter = minIter;
		this.maxIter = maxIter;
		
		/*this.axes = axes;
		this.limModIter = Math.pow(2.0,(double)1/(p-1));
		this.limModIterS = limModIter * limModIter;*/
		
		this.axesPower = new double[nbBrot][6];
		
		this.axesPower[0] = axes;
		modulusPower = new double[nbBrot];
		minModulus = new double[nbBrot];
		modulusPower[0] = Math.pow(2.0, (double)1/(p -1));
		minModulus[0] = (p - 1)/(p * Math.pow(p, (double)1/(p-1)));
		
		int newPower;
		for(int i = 1; i < nbBrot; i++)
		{
			newPower = p + i;
			modulusPower[i] = Math.pow(2.0, (double)1/(p + i - 1));
			minModulus[i] = (newPower - 1)/(newPower * Math.pow(newPower, (double)1/((newPower-1))));
			axesPower[i][0] = axesPower[i][2] = axesPower[i][4] = -modulusPower[i];
			axesPower[i][1] = axesPower[i][3] = axesPower[i][5] = modulusPower[i];
		}
		
		this.rotOp = new Rotation(rot[0], rot[1], rot[2], 1);
		if (rot[0] != 0 || rot[1] != 0 || rot[2] != 0)
		{
			isThereRotation = true;
			rotOp.doRotation(lightX, lightY, lightZ);
			lightX = rotOp.getEndPointX();
			lightY = rotOp.getEndPointY();
			lightZ = rotOp.getEndPointZ();
		}
		else
			isThereRotation = false;
		
		this.zoom = new int[nbBrot][3];
		for(int i = 0; i < nbBrot ; i++)
		{
			this.zoom[i][0] = (int)(dimension[0]/(axesPower[i][1] - axesPower[i][0]));
			this.zoom[i][1] = (int)(dimension[1]/(axesPower[i][3] - axesPower[i][2]));
			this.zoom[i][2] = (int)(dimension[2]/(axesPower[i][5] - axesPower[i][4]));
		}
		
		this.pourcentageInt = pourcentageInt;
		
		//this.factz = 1/((double)(100-pourcentageInt)/100);
		this.factz = 1 - (double)pourcentageInt/100;
		//System.out.println("Je suis passé par là.");
	}
       
	public void run()
	{
		
		principalFrame.setEnabledDistanceEst(false);
		principalFrame.setEnabledGenerate(false);
		principalFrame.setEnableSettings(false);
		principalFrame.setEnabledRotationCB(false);
		principalFrame.setEnabledGenerateOcta(false);
		principalFrame.setEnableRotationSequence(false);
		principalFrame.setEnableSliceSequence(false);
		imagePanel.setEnabledButton(false);
		stop = false;
		stopErreur = false;
		
		if (!renderRotationSequence && !renderSliceSequence && !renderDistanceEst)
		{
			progress.setMinimum(0);
			progress.setMaximum((int) (nbBrot * factz * dimension[2]) + nbBrot - 1);
		
		
			progress.setValue(0);
		
			if (p != p2)
			{
				for (int i = 0; i < nbBrot; i++)
				{
					imagePanel.setNameImage(message + "-3DImageDeg" + (p + i));
					
					this.calcNormal(i);
					if (stopErreur)
					{
						JOptionPane.showMessageDialog(null, "Process Stopped Too Many Images", "Stop Message", JOptionPane.ERROR_MESSAGE);
						break;
					}
					
					if (i < nbBrot - 1)
					{
						I = new BufferedImage(dimension[2] + 1, dimension[2] + 1, BufferedImage.TYPE_INT_ARGB);
						//resetImage();
						imagePanel.nextImage();
					}
					if (stop)
					{
						JOptionPane.showMessageDialog(null, "Process stopped!", "Stop Message", JOptionPane.INFORMATION_MESSAGE);
						break;
					}
				}
				this.progress.setValue((int) (dimension[2] * factz * nbBrot) + nbBrot - 1);
			}	
			else
			{
				if (isRenderOctaedreThm)
				{
					this.calcOctaedre(0);
				}
				else
				{
					imagePanel.setNameImage(message + "-3DImageDeg" + p);
					this.calcNormal(0);
				}
					
				if (stopErreur)
				{
					JOptionPane.showMessageDialog(null, "Process Stopped Too Many Images", "Stop Message", JOptionPane.ERROR_MESSAGE);
				}
				System.out.print((stop ? "Process Stop" : "Process not stopped"));
				if (stop)
					JOptionPane.showMessageDialog(null, "Process Stopped", "Stop Message", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if (!renderRotationSequence && !renderDistanceEst && renderSliceSequence)
		{
			progress.setMinimum(0);
			int progressMaximum = (int) totalIncrement;
			progress.setMaximum(progressMaximum);
		
		
			progress.setValue(0);
			
			this.calcSliceSequence(0);
			
			if (stopErreur)
			{
				JOptionPane.showMessageDialog(null, "Process Stopped Too Many Images", "Stop Message", JOptionPane.ERROR_MESSAGE);
			}
			System.out.print((stop ? "Process Stop" : "Process not stopped"));
			if (stop)
				JOptionPane.showMessageDialog(null, "Process Stopped", "Stop Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (!renderRotationSequence && renderDistanceEst && !renderSliceSequence)
		{
			progress.setMinimum(0);
			progress.setMaximum((int) (nbBrot * factz * dimension[2]) + nbBrot - 1);
		
		
			progress.setValue(0);
			
			System.out.println("Je suis ici !");
		
			progress.setValue(0);
			
			this.calcEstimatinDist(0);
			
			if (stopErreur)
			{
				JOptionPane.showMessageDialog(null, "Process Stopped Too Many Images", "Stop Message", JOptionPane.ERROR_MESSAGE);
			}
			System.out.print((stop ? "Process Stop" : "Process not stopped"));
			if (stop)
				JOptionPane.showMessageDialog(null, "Process Stopped", "Stop Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			progress.setMinimum(0);
			int progressMaximum = (int) totalIncrement;
			progress.setMaximum(progressMaximum);
		
		
			progress.setValue(0);
			
			this.calcRotationSequence(0);
			
			if (stopErreur)
			{
				JOptionPane.showMessageDialog(null, "Process Stopped Too Many Images", "Stop Message", JOptionPane.ERROR_MESSAGE);
			}
			System.out.print((stop ? "Process Stop" : "Process not stopped"));
			if (stop)
				JOptionPane.showMessageDialog(null, "Process Stopped", "Stop Message", JOptionPane.INFORMATION_MESSAGE);
		}
		
		isRenderOctaedreThm = false;
		principalFrame.setEnabledDistanceEst(true);
		principalFrame.setEnabledGenerate(true);
		principalFrame.setEnableSettings(true);
		principalFrame.setEnabledRotationCB(true);
		principalFrame.setEnabledGenerateOcta(true);
		principalFrame.setEnableRotationSequence(true);
		principalFrame.setEnableSliceSequence(true);
		imagePanel.isEnabledButton(true);
		
		principalFrame.revalidate();
		principalFrame.repaint();
		
	}
	
	//Méthode pour verifier si le module des iteres de Q_{p,c} est borne apres 130 itérations
	public static int check(Tricomplex c, int p, double limModIter)
	{
		Tricomplex z = new Tricomplex();
		int i=0;
		while (i<MAXITER1 && z.getEuclMod2()<=limModIter * limModIter)
		{
			z.puisRec(p);
			z.plus(c);
			i++;
		}
		return i;
	}
	
	//Méthode pour verifier si les composantes idempotentes appartiennent a l'ensemble de Mandelbrot complexe
	// et donc pour determiner si le Julia associe est connexe
	public static int[] checkMandelbrot(double cx,double cy,int p, double limModIter)
	{
		Complex c=new Complex(cx,cy);
		Complex z=new Complex();
		int[] result=new int[2];
		result[0]=0;
		result[1]=1;
		while (z.getNormeCarre() <= limModIter*limModIter && result[1]<MAXITER2) 
        {
            z.puisRec(p);
            z.plus(c);
            result[1]++;
        }
		if (result[1]==MAXITER2)
			result[0]=1;
		return result;
	}
	
	//Methode pour retourner MAXITER1 et MAXITER2
    public static int getMaxIterT()
    {
       	return MAXITER1;
    }
    
    public static int getMaxIterM()
    {
    	return MAXITER2;
    }
    
    // Méthode pour mettre à jour si on stop le processus
    public void setStop(boolean etat)
    {
    	this.stop = etat;
    }
    
    //Méthode pour retourner l'image
    public BufferedImage getImage() throws NullPointerException
    {
    	return I;
    }
    
    // Méthode pour changer MAXITER1 et MAXITER2
    public static void setMaximumIterations(int iterT, int iterM)
    {
    	MAXITER1 = iterT;
    	MAXITER2 = iterM;
    	pal = new PalletteC(Tribrot.getMaxIterM() + 1, 1000, 3);
		pal.setHSB();
		tabHSB = pal.getPalHSB();
		tabHSB2 = pal.getPalHSB2();
    }
    
    public String toString()
    {
    	String result;
    	result = "Multibrot d'ordre" + p;
    	return result;
    }
    
    public int getPower()
    {
    	return p;
    }
    
    public int getSecondPower()
    {
    	return p2;
    }
    
    // MÉthode pour calculer seulement l'octaèdre par le théorème
    public void calcOctaedre(int i)
    {
    	int pixX = 0, pixY = 0;
    	int progressValue = progress.getValue();
    	int zoomX = zoom[i][0], zoomY = zoom[i][1], zoomZ = zoom[i][2];
    	double minX = axesPower[i][0], maxX = axesPower[i][1];
    	double minY = axesPower[i][2], maxY = axesPower[i][3];
    	double minZ = axesPower[i][4], maxZ = axesPower[i][5];
    	long timeStart, timeEnd;
    	int newXaxes, newYaxes, newZaxes;
    	
    	int tPix;
    	int coinYPix;
    	int coinXPixNeg;
    	int coinXPixPos;
    	int coinZPix;
    	int lPix;
    	
    	int coinUnitX =(int)( (-minX + 1) * zoomX);
    	int coinUnitY =(int)( (-minY + 1) * zoomY);
    	int coinUnitZ =(int)( (-minZ + 1) * zoomZ);
    	int lUnitPix = Math.abs(coinUnitX - dimension[0]/2);
    	
    	double l;
    	double t;
    	
    	if (p % 2 == 0)
    	{
    		t = (-p*(Math.pow(2*p, (double)1/(p-1)) - 1) - 1)/
    				(2 * Math.pow(p, (double)p/(p-1)));
    		l = (p*(Math.pow(2*p, (double)1/(p-1)) + 1) - 1)/
    				(2 * Math.pow(p, (double)p/(p-1)));
    		tPix = (int)((-minX + t) * zoomX);
    		coinXPixNeg = (int)((-minX + t - l) * zoomX);
    		coinXPixPos = (int)((-minX + t + l) * zoomX);
    		coinYPix = (int)((-minY + l) * zoomY);
    		coinZPix = (int)((-minZ + l) * zoomZ);
    		lPix = Math.abs(coinYPix - dimension[1]/2);
    	}
    	else
    	{
    		t = 0;
    		l = (p-1)/(Math.pow(p, (double)p/(p-1)));
    		tPix = dimension[0]/2;
    		coinXPixPos = (int)((-minX + l) * zoomX);
    		coinYPix = (int)((-minY + l) * zoomY);
    		coinZPix = (int)((-minZ + l) * zoomZ);
    		lPix = Math.abs(coinYPix - dimension[1]/2);
    	}
    	
    	
    	
    	timeStart = System.currentTimeMillis();    	
    	for (int z=0; z <= dimension[2]; z++)
		{
			progress.setValue(progressValue++);
			for (int y=0; y < I.getHeight();y++)
				{
					for (int x=0;x<I.getWidth();x++)
					{
						tempsDebut=System.currentTimeMillis();
						if (stop)
						{
							z = dimension[2];
							break;
						}
						
						//On fixe les parties du nombre c
						pX=(double)x/zoomX+minX;
						pY=(double)y/zoomY + minY;
						pZ=(double)(z * factz)/zoomZ+minZ;
						
						//On fait la rotation désirée
						if (isThereRotation)
						{
							rotOp.doRotation(x - dimension[0]/2, y - dimension[1]/2, z - dimension[2]/2);
							newXaxes = (int)rotOp.getEndPointX() + dimension[0]/2;
							newYaxes = (int)rotOp.getEndPointY() + dimension[1]/2;
							newZaxes = (int)rotOp.getEndPointZ() + dimension[2]/2;
						}
						else
						{
							newXaxes = x;
							newYaxes = y;
							newZaxes = z;
						}
						
						/*if (coupe[0] & coupe[5] & coupe[6] ||
							coupe[0] & coupe[6] & coupe[7] ||
							coupe[0] & coupe[5] & coupe[7] ||
							coupe[5] & coupe[6] & coupe[7])
							Brightness=(float)1 - (float)(z)/(dimension[2]);
						else
						{
							Brightness=(float)0.1+(float)(9*z)/(10*dimension[2]);
							//Brightness = (float)Brightness;
						}*/
						Brightness = (float)(pX * 2 + pY * 2 + pZ * 2/ (pX * pX + pY * pY + pZ * pZ) + 1)/2;
						
						// L'axe des x
						if ((z == dimension[2]/2 || z == dimension[2]/2 - 1 || z == dimension[2]/2 - 2 || z == dimension[2]/2 + 1 || 
								z == dimension[2]/2 + 2) && (y == dimension[1]/2 || y == dimension[1]/2 - 1 || y == dimension[1]/2 - 2 
								|| y == dimension[1]/2 + 1 || y == dimension[1]/2 + 2) && (x != dimension[0]/2))
						{
							if (newXaxes >= 0 && newYaxes >= 0 && newXaxes < I.getWidth() && newYaxes < I.getHeight())
								I.setRGB(newXaxes, newYaxes, Color.RED.getRGB());
						}
						
						// L'axe des y
						if ((z == dimension[2]/2 || z == dimension[2]/2 - 1 || z == dimension[2]/2 - 2 || z == dimension[2]/2 + 1 || 
								z == dimension[2]/2 + 2) && (x == dimension[0]/2 || x == dimension[0]/2 - 1 || x == dimension[0]/2 - 2 
								|| x == dimension[0]/2 + 1 || x == dimension[0]/2 + 2) && (y != dimension[1]/2))
						{
							if (newXaxes >= 0 && newYaxes >= 0 && newXaxes < I.getWidth() && newYaxes < I.getHeight())
								I.setRGB(newXaxes, newYaxes, Color.RED.getRGB());
						}
						
						// L'axe des z
						if ((x == dimension[0]/2 || x == dimension[0]/2 - 1 || x == dimension[0]/2 - 2 || x == dimension[0]/2 + 1 || 
								x == dimension[0]/2 + 2) && (y == dimension[1]/2 || y == dimension[1]/2 - 1 || y == dimension[1]/2 - 2 
								|| y == dimension[1]/2 + 1 || y == dimension[1]/2 + 2) && (z != dimension[2]/2))
						{
							if (newXaxes >= 0 && newYaxes >= 0 && newXaxes < I.getWidth() && newYaxes < I.getHeight())
								I.setRGB(newXaxes, newYaxes, Color.RED.getRGB());
						}
						
						// L'octaèdre
						if (Math.abs(x - tPix) + Math.abs(y - dimension[1]/2) + Math.abs(z - dimension[2]/2) <= lPix)
						{
							col=Color.getHSBColor((float)0.6, (float)1,1-(float)Brightness);
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes<I.getHeight())
							{
								I.setRGB(newXaxes, newYaxes,col.getRGB());	
							}
						}
						
						// Frontières de l'octaèdre
						if ((x == tPix || x == tPix - 1 || x == tPix + 1 || x == tPix -2 || x == tPix + 2) && Math.abs(y - dimension[1]/2) + Math.abs(z - dimension[2]/2) == lPix)
						{
							col=Color.getHSBColor((float)0.6, (float)1,1);
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes<I.getHeight())
							{
								I.setRGB(newXaxes, newYaxes,col.getRGB());	
							}
						}
						else if ((y == dimension[1]/2 || y == dimension[1]/2 - 1 || y == dimension[1]/2 + 1 || y == dimension[1]/2 - 2 || y == dimension[1]/2 +2) && Math.abs(x - tPix) + Math.abs(z - dimension[2]/2) == lPix)
						{
							col=Color.getHSBColor((float)0.6, 1, 1);
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes<I.getHeight())
							{
								I.setRGB(newXaxes, newYaxes,col.getRGB());	
							}
						}
						else if ((z == dimension[2]/2 || z == dimension[2]/2 - 1 || z == dimension[2]/2 + 1 || z == dimension[2]/2 - 2 || z == dimension[2]/2 + 2)  && Math.abs(x - tPix) + Math.abs(y - dimension[1]/2) == lPix)
						{
							col=Color.getHSBColor((float)0.6, 1, 1);
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes<I.getHeight())
							{
								I.setRGB(newXaxes, newYaxes,col.getRGB());	
							}
						}
						
						//Frontière de l'ensemble limite
						if ((x == dimension[0]/2 || x == dimension[0]/2 - 1 || x == dimension[0]/2 + 1 || x == dimension[0]/2 - 2 || x == dimension[0]/2 + 2) && Math.abs(y - dimension[1]/2) + Math.abs(z - dimension[2]/2) == lUnitPix)
						{
							col=Color.getHSBColor((float)0.0, (float)0.0,(float)Brightness);
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes<I.getHeight())
							{
								I.setRGB(newXaxes, newYaxes,col.getRGB());	
							}
						}
						if ((y == dimension[1]/2 || y == dimension[1]/2 - 1 || y == dimension[1]/2 + 1 || y == dimension[1]/2 - 2 || y == dimension[1]/2 + 2) && Math.abs(x - dimension[0]/2) + Math.abs(z - dimension[2]/2) == lUnitPix)
						{
							col=Color.getHSBColor((float)0.0, (float)0.0,(float)Brightness);
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes<I.getHeight())
							{
								I.setRGB(newXaxes, newYaxes,col.getRGB());	
							}
						}
						if ((z == dimension[2]/2 || z == dimension[2]/2 - 1 || z == dimension[2]/2 + 1 || z == dimension[2]/2 - 2 || z == dimension[2]/2 + 2) && Math.abs(x - dimension[0]/2) + Math.abs(y - dimension[1]/2) == lUnitPix)
						{
							col=Color.getHSBColor((float)0.0, (float)0.0,(float)Brightness);
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes<I.getHeight())
							{
								I.setRGB(newXaxes, newYaxes,col.getRGB());	
							}
						}
						
						
					if(viewProcessPoint)
						stopErreur = imagePanel.setImage(I);
					}//FIN boucle en x
					if(viewProcessLine)
						stopErreur = imagePanel.setImage(I);
				}	//FIN boucle en y
			if (viewProcessSlice)
				stopErreur = imagePanel.setImage(I);//System.out.println();
		}	//FIN boucle en z
    	if(viewProcessNo)
			stopErreur = imagePanel.setImage(I); 
    	
    	
    	timeEnd = System.currentTimeMillis();
    	timeEnd = timeEnd - timeStart;
    	System.out.print(timeEnd/3600000 + "hrs. : ");
    	timeEnd = timeEnd % 3600000;
    	System.out.print(timeEnd/60000 + "min. : ");
    	timeEnd = timeEnd % 60000;
    	System.out.print(timeEnd/1000 + "sec.");
    	System.out.println();
    		
    }
    
    // Méthode pour calculer facon normale
    public void calcNormal(int i)
    {
    	int pixX = 0, pixY = 0;
    	int progressValue = progress.getValue();
    	int zoomX = zoom[i][0], zoomY = zoom[i][1], zoomZ = zoom[i][2];
    	double minX = axesPower[i][0], maxX = axesPower[i][1];
    	double minY = axesPower[i][2], maxY = axesPower[i][3];
    	double minZ = axesPower[i][4], maxZ = axesPower[i][5];
    	long timeStart, timeEnd;
    	int newXaxes, newYaxes, newZaxes;
    	double normalX, normalY, normalZ;
    	double norm;
    	normalX = 0;
    	normalY = 0;
    	normalZ = 1;
    	norm = Math.sqrt(normalX * normalX + normalY * normalY + normalZ * normalZ);
    	
    	double xAxeX, xAxeY, xAxeZ;
    	xAxeX = 1;
    	xAxeY = xAxeZ = 0;
    	
    	double yAxeX, yAxeY, yAxeZ;
    	yAxeY = 1;
    	yAxeX = yAxeZ = 0;
    	
    	double zAxeX, zAxeY, zAxeZ;
    	zAxeZ = 1;
    	zAxeX = zAxeY = 0;
    	
    	if (isThereRotation)
    	{
    		rotOp.doRotation(normalX, normalY, normalZ);
    		normalX = rotOp.getEndPointX();
    		normalY = rotOp.getEndPointY();
    		normalZ = rotOp.getEndPointZ();
    		normalX = normalX/norm;
    		normalY = normalY/norm;
    		normalZ = normalZ/norm;
    		
    		rotOp.doRotation(xAxeX, xAxeY, xAxeZ);
    		xAxeX = rotOp.getEndPointX();
    		xAxeY = rotOp.getEndPointY();
    		xAxeZ = rotOp.getEndPointZ();
    		
    		rotOp.doRotation(yAxeX, yAxeY, yAxeZ);
    		yAxeX = rotOp.getEndPointX();
    		yAxeY = rotOp.getEndPointY();
    		yAxeZ = rotOp.getEndPointZ();
    	}
    	
    	System.out.println("Normal x : " + normalX + "  Normal y : " + normalY + "  Normal z : " + normalZ);
    	
    	Complex idemp = new Complex();
    		
    	double scalProd =  (maxZ - minZ) * factz + minZ;
    	//int coupeZ = (int) (factz * dimension[2]);
    	boolean onPlane = true;
    	
    	timeStart = System.currentTimeMillis();    	
    	for (int z = 0; z <= dimension[2]; z++)
		{
			progress.setValue(progressValue++);
			for (int y=0; y < I.getHeight();y++)
				{
					for (int x=0;x<I.getWidth();x++)
					{
						tempsDebut=System.currentTimeMillis();
						if (stop)
						{
							z = dimension[2];
							break;
						}
						
						//On fixe les parties du nombre c
						pX = (double)x/zoomX+minX;
						pY = maxY - (double)y/zoomY;
						pZ = (double)(z)/zoomZ+minZ;
						
						// On fait la rotation désirée
						if (isThereRotation)
						{
							rotOp.doRotation(pX, pY, pZ);
							pX = rotOp.getEndPointX();
							pY = rotOp.getEndPointY();
							pZ = rotOp.getEndPointZ();
						}
							
						/*if (axeX.isOn(pX, pY, pZ))
							I.setRGB(x, y, Color.white.getRGB());
						if (axeY.isOn(pX, pY, pZ))
							I.setRGB(x, y, Color.white.getRGB());
						if (axeZ.isOn(pX, pY, pZ))
							I.setRGB(x, y, Color.white.getRGB());*/
						Brightness = (float)(((pX * lightX + pY * lightY + pZ * lightZ)/ ( normLight * Math.sqrt(pX * pX + pY * pY + pZ * pZ))) + 1)/2;
						
						switch (typeOfSlice)
						{
						case 1:
							c.setComponent(pX, pY, pZ, coupe, componentValues);
							break;
						case 2:
							c.setComponentIdempotent(pX, pY, pZ, coupe, componentValues);
							break;
							default:
								c.setComponent(pX, pY, pZ, coupe, componentValues);
								break;
						}
						
						/*if (coupe[0] & coupe[5] & coupe[6] ||
							coupe[0] & coupe[6] & coupe[7] ||
							coupe[0] & coupe[5] & coupe[7] ||
							coupe[5] & coupe[6] & coupe[7])
							Brightness=(float)1 - (float)(z)/(dimension[2]);
						else
						{
							Brightness=(float)0.1+(float)(9*z)/(10*dimension[2]);
							//Brightness = (float)Brightness;
						}*/
						
						ck=Tribrot.check(c,p + i, modulusPower[i]);
						onPlane = normalPlanEval(normalX, normalY, normalZ, pX, pY, pZ, scalProd);
						if (onPlane)
						{
							if(!onPlane)
							{
							//a la coupe
							if  (ck >= minIter && ck <= maxIter)
							{
								col = Color.getHSBColor(tabHSB2[ck], 1, Brightness);
						
								//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
								if (x>=0 && x<I.getWidth() && y>=0 && y < I.getHeight())
								{
									I.setRGB(x, y,col.getRGB());
								}	
							}
							}
							else
							{
							// On teste pour accélérer les calculs
							if (ck >= minIter & ck <= maxIter)
							{
									//Obtenir des informations sur les ensemble de Julia rempli en decomposant le nombre c sur ces composantes
								//idempotentes bicomplexes et tricomplexes
								idemp = c.getE1();
								cX = idemp.getRe();
								cY = idemp.getIm();
								if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
									ck1=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
								else
								{
									ck1[0] = 1;
									ck1[1] = MAXITER2;
								}
								
								idemp = c.getE2();
								cX = idemp.getRe();
								cY = idemp.getIm();
								if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
									ck2=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
								else
								{
									ck2[0] = 1;
									ck2[1] = MAXITER2;
								}
								
								idemp = c.getE3();
								cX = idemp.getRe();
								cY = idemp.getIm();
								if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
									ck3=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
								else
								{
									ck3[0] = 1;
									ck3[1] = MAXITER2;
								}
								
								idemp = c.getE4();
								cX = idemp.getRe();
								cY = idemp.getIm();
								if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
									ck4=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
								else
								{
									ck4[0] = 1;
									ck4[1] = MAXITER2;
								}
								
								ckTot[0]=ck1[0]+ck2[0]+ck3[0]+ck4[0];
								ckTot[1]=(ck1[1]+ck2[1]+ck3[1]+ck4[1])/4;
								
				
								//Selon l'entier retourne, on verifie combien de Julia sont Connexe et on associe une couleur aux pixels
								//selon l'entier retourner par la methode check. A l'interieur de chaque cas (les cases), des conditionnels
								//permettent de faire le bon choix de couleur en fonction de l'entier ckTot obtenu par la methode checkMandelbrot.
								//Les couleurs sont obtenues selon un degrade en profondeur (selon l'axe z et donc selon les valeurs de z).
								
								switch (ckTot[0])
								{
									case 0:
									{
										//Cas ou Julia Cantor (au sens tricomplexe)
										col=Color.getHSBColor(tabHSB[ckTot[1]], 1, Brightness);
										break;
									}
									case 1:
									{
										//Cas d'un Julia connexes
										//Degrade de Jaune
										col=Color.getHSBColor((float)0.17, 1, Brightness);
										break;
									}
									case 2:
									{
										//Cas de deux Julia connexes
										//Degrade de rouge
										col=Color.getHSBColor((float)0.0, 1, Brightness);
										break;
									}
									case 3:
									{
										//Cas de trois Julia connexes
										//Degrade de orange
										col=Color.getHSBColor((float)0.083, 1, Brightness);
										break;
									}
									case 4:
									{
										//Cas ou 4 Julia connexe
										col=Color.getHSBColor((float)0.0, 0,(float)Brightness);
										break;
									}
									default:
										col=Color.white;	
									break;
								}
								//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
								if (x>=0 && y<I.getWidth() && x>=0 && y<I.getHeight())
								{
									I.setRGB(x, y,col.getRGB());
								}
							}
						}
						}
					if(viewProcessPoint)
						stopErreur = imagePanel.setImage(I);
					}//FIN boucle en x
					if(viewProcessLine)
						stopErreur = imagePanel.setImage(I);
				}	//FIN boucle en y
			if (viewProcessSlice)
				stopErreur = imagePanel.setImage(I);//System.out.println();
		}	//FIN boucle en z
    	if(viewProcessNo)
			stopErreur = imagePanel.setImage(I); 
    
    	timeEnd = System.currentTimeMillis();
    	timeEnd = timeEnd - timeStart;
    	System.out.print(timeEnd/3600000 + "hrs. : ");
    	timeEnd = timeEnd % 3600000;
    	System.out.print(timeEnd/60000 + "min. : ");
    	timeEnd = timeEnd % 60000;
    	System.out.print(timeEnd/1000 + "sec.");
    	System.out.println();
    		
    }
    
    public void setParamDistanceEst(int p, int p2, boolean doPowerSequence, boolean[] coupe, double[] componentValues, int style,int minIter, int maxIter, 
			int[] dimension, double[] axes, int pourcentageInt, double[] rot, int viewProcess, boolean viewAxes, boolean renderInFrame,
			boolean isrenderOctaedreThm, double[] light, double epsilon, int typeOfSlice)
	{
		
		this.dimension = dimension;
		this.I = new BufferedImage(dimension[0] + 1, dimension[1] + 1, BufferedImage.TYPE_INT_ARGB);
		//resetImage();
		//this.matrixImage = new int[dimension[0] + 1][dimension[1] + 1][dimension[2] + 1];
		this.renderInFrame = renderInFrame;
		this.viewAxes = viewAxes;
		this.isRenderOctaedreThm = isrenderOctaedreThm;
		this.renderRotationSequence = false;
		this.renderSliceSequence = false;
		this.renderDistanceEst = true;
		
		this.epsilon = epsilon;
		
		this.lightX = light[0];
		this.lightY = light[1];
		this.lightZ = light[2];
		
		this.typeOfSlice = typeOfSlice;
		
		switch (viewProcess)
		{
		case 1:
			viewProcessNo = true;
			viewProcessLine = false;
			viewProcessPoint = false;
			viewProcessSlice = false;
			break;
		case 2:
			viewProcessPoint = true;
			viewProcessLine = false;
			viewProcessSlice = false;
			viewProcessNo = false;
			break;
		case 3:
			viewProcessLine = true;
			viewProcessPoint = false;
			viewProcessSlice = false;
			viewProcessNo = false;
			break;
		case 4:
			viewProcessSlice = true;
			viewProcessLine = false;
			viewProcessPoint = false;
			viewProcessNo = false;
			break;
			default:
				viewProcessNo = true;
				viewProcessPoint = viewProcessLine = viewProcessSlice = false;
		}
		
		this.p = p;
		this.p2 = p2;
		this.nbBrot = p2 - p + 1;
		this.doPowerSequence = doPowerSequence;
		this.coupe = coupe;
		this.message = "";
		if (coupe[0])
			message += "1";
		if (coupe[1])
			message += "i1";
		if (coupe[2])
			message += "i2";
		if (coupe[3])
			message += "i3";
		if (coupe[4])
			message += "i4";
		if (coupe[5])
			message += "j1";
		if (coupe[6])
			message += "j2";
		if (coupe[7])
			message += "j3";
		this.componentValues = componentValues;
		this.style = style;
		this.minIter = minIter;
		this.maxIter = maxIter;
		
		/*this.axes = axes;
		this.limModIter = Math.pow(2.0,(double)1/(p-1));
		this.limModIterS = limModIter * limModIter;*/
		
		this.axesPower = new double[nbBrot][6];
		
		this.axesPower[0] = axes;
		modulusPower = new double[nbBrot];
		minModulus = new double[nbBrot];
		modulusPower[0] = Math.pow(2.0, (double)1/(p -1));
		minModulus[0] = (p - 1)/(p * Math.pow(p, (double)1/(p-1)));
		
		int newPower;
		for(int i = 1; i < nbBrot; i++)
		{
			newPower = p + i;
			modulusPower[i] = Math.pow(2.0, (double)1/(p + i - 1));
			minModulus[i] = (newPower - 1)/(newPower * Math.pow(newPower, (double)1/((newPower-1))));
			axesPower[i][0] = axesPower[i][2] = axesPower[i][4] = -modulusPower[i];
			axesPower[i][1] = axesPower[i][3] = axesPower[i][5] = modulusPower[i];
		}
		
		this.rotOp = new Rotation(rot[0], rot[1], rot[2], 1);
		if (rot[0] != 0 || rot[1] != 0 || rot[2] != 0)
		{
			isThereRotation = true;
			/*rotOp.doRotation(lightX, lightY, lightZ);
			lightX = rotOp.getEndPointX();
			lightY = rotOp.getEndPointY();
			lightZ = rotOp.getEndPointZ();*/
		}
		else
			isThereRotation = false;
		
		this.zoom = new int[nbBrot][3];
		for(int i = 0; i < nbBrot ; i++)
		{
			this.zoom[i][0] = (int)(dimension[0]/(axesPower[i][1] - axesPower[i][0]));
			this.zoom[i][1] = (int)(dimension[1]/(axesPower[i][3] - axesPower[i][2]));
			this.zoom[i][2] = (int)(dimension[2]/(axesPower[i][5] - axesPower[i][4]));
		}
		
		this.pourcentageInt = pourcentageInt;
		
		//this.factz = 1/((double)(100-pourcentageInt)/100);
		this.factz = 1 - (double)pourcentageInt/100;
		//System.out.println("Je suis passé par là.");
	}
    
    // Méthode pour calculer facon normale
    public void calcEstimatinDist(int i)
    {
    	int pixX = 0, pixY = 0;
    	int progressValue = progress.getValue();
    	int zoomX = zoom[i][0], zoomY = zoom[i][1], zoomZ = zoom[i][2];
    	double minX = axesPower[i][0], maxX = axesPower[i][1];
    	double minY = axesPower[i][2], maxY = axesPower[i][3];
    	double minZ = axesPower[i][4], maxZ = axesPower[i][5];
    	double[] distEst = new double[4];
    	double distTot = 0;
    	double[] distTemp = new double[2];
    	long timeStart, timeEnd;
    	int newXaxes, newYaxes, newZaxes;
    	double normalX, normalY, normalZ;
    	normalX = 0;
    	normalY = 0;
    	normalZ = 1;
    		
    	double scalProd =  (maxZ - minZ) * factz + minZ;
    	int coupeZ = (int) (factz * dimension[2]);
    	boolean onPlane = true;
    	
    	timeStart = System.currentTimeMillis();    	
    	for (int z=0; z <= coupeZ; z++)
		{
			progress.setValue(progressValue++);
			for (int y=0; y < I.getHeight();y++)
				{
					for (int x=0;x<I.getWidth();x++)
					{
						tempsDebut=System.currentTimeMillis();
						if (stop)
						{
							z = dimension[2];
							break;
						}
						
						//On fixe les parties du nombre c
						pX=(double)x/zoomX+minX;
						pY=(double)y/zoomY + minY;
						pZ=(double)(z)/zoomZ+minZ;
						//onPlane = normalPlanEval(normalX, normalY, normalZ, pX, pY, pZ, scalProd);
						
						//On fait la rotation désirée
						if (isThereRotation)
						{
							rotOp.doRotation(pX, pY, pZ);
							pX = rotOp.getEndPointX();
							pY = rotOp.getEndPointY();
							pZ = rotOp.getEndPointZ();
						}
						
						/*if (isThereRotation)
						{
							rotOp.doRotation(x - dimension[0]/2, y - dimension[1]/2, z - dimension[2]/2);
							newXaxes = (int)rotOp.getEndPointX() + dimension[0]/2;
							newYaxes = (int)rotOp.getEndPointY() + dimension[1]/2;
							newZaxes = (int)rotOp.getEndPointZ() + dimension[2]/2;
						}
						else*/
						{
							newXaxes = x;
							newYaxes = y;
							newZaxes = z;
						}
							
						/*if (axeX.isOn(pX, pY, pZ))
							I.setRGB(x, y, Color.white.getRGB());
						if (axeY.isOn(pX, pY, pZ))
							I.setRGB(x, y, Color.white.getRGB());
						if (axeZ.isOn(pX, pY, pZ))
							I.setRGB(x, y, Color.white.getRGB());*/
						Brightness = (float)(((pX * lightX + pY * lightY + pZ * lightZ)/ ( normLight * Math.sqrt(pX * pX + pY * pY + pZ * pZ))) + 1)/2;
						
						switch (typeOfSlice)
						{
						case 1:
							c.setComponent(pX, pY, pZ, coupe, componentValues);
							break;
						case 2:
							c.setComponentIdempotent(pX, pY, pZ, coupe, componentValues);
							break;
							default:
								c.setComponent(pX, pY, pZ, coupe, componentValues);
								break;
						}
						
						cX=c.getRe()+c.getHyp3()+c.getHyp1()-c.getHyp2();
						cY=c.getIm1()+c.getIm4()-c.getIm2()+c.getIm3();
						
						if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
						{
							distTemp = this.distanceEstimation(new Complex(cX, cY));
							distEst[0] = distTemp[0];
							ck1[0] = (int)distTemp[1];
							ck1[1] = (int)distTemp[2];
						}
						else
						{
							distEst[0] = 0;
							ck1[0] = 1;
							ck1[1] = MAXITER2;
						}
						
						cX=c.getRe()+c.getHyp3()-c.getHyp1()+c.getHyp2();
						cY=c.getIm1()+c.getIm4()+c.getIm2()-c.getIm3();
						if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
						{
							distTemp = this.distanceEstimation(new Complex(cX, cY));
							distEst[1] = distTemp[0];
							ck2[0] = (int)distTemp[1];
							ck2[1] = (int)distTemp[2];
						}
						else
						{
							distEst[1] = 0;
							ck2[0] = 1;
							ck2[1] = MAXITER2;
						}
						
						cX=c.getRe()-c.getHyp3()+c.getHyp1()+c.getHyp2();
						cY=c.getIm1()-c.getIm4()-c.getIm2()-c.getIm3();
						if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
						{
							distTemp =this.distanceEstimation(new Complex(cX, cY));
							distEst[2] = distTemp[0];
							ck3[0] = (int)distTemp[1];
							ck3[1] = (int)distTemp[2];
						}
						else
						{
							distEst[2] = 0;
							ck3[0] = 1;
							ck3[1] = MAXITER2;
						}
						
						cX=c.getRe()-c.getHyp3()-c.getHyp1()-c.getHyp2();
						cY=c.getIm1()-c.getIm4()+c.getIm2()+c.getIm3();
						if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
						{
							distTemp =this.distanceEstimation(new Complex(cX, cY));
							distEst[3] = distTemp[0];
							ck4[0] = (int)distTemp[1];
							ck4[1] = (int)distTemp[2];
						}
						else
						{
							distEst[3] = 0;
							ck4[0] = 1;
							ck4[1] = MAXITER2;
						}
						
						distTot = Math.sqrt((distEst[0] + distEst[1] + distEst[2] + distEst[3])/4);
						ckTot[0]=ck1[0]+ck2[0]+ck3[0]+ck4[0];
						ckTot[1]=(ck1[1]+ck2[1]+ck3[1]+ck4[1])/4;
						
						/*System.out.println("Composante 1 :" + ck1[0] + "  Composante 2 :" + ck2[0] + "\n"
								 + "Composante 3 : " + ck3[0] + "Composante 4 : " + ck4[0]);
						
						System.out.println("Le nombre total de dedans est : " + ckTot[0]);*/
						
		
						//Selon l'entier retourne, on verifie combien de Julia sont Connexe et on associe une couleur aux pixels
						//selon l'entier retourner par la methode check. A l'interieur de chaque cas (les cases), des conditionnels
						//permettent de faire le bon choix de couleur en fonction de l'entier ckTot obtenu par la methode checkMandelbrot.
						//Les couleurs sont obtenues selon un degrade en profondeur (selon l'axe z et donc selon les valeurs de z).
						
						//System.out.println("La distance est :" + distTot);
						if (distTot > epsilon)
						{
							col = Color.BLACK;
						}
						else
							{
								switch (ckTot[0])
								{
								case 0:
								{
									//Cas ou Julia Cantor (au sens tricomplexe)
									col=Color.getHSBColor(tabHSB[ckTot[1]], 1, Brightness);
									break;
								}
								case 1:
								{
									//Cas d'un Julia connexes
									//Degrade de Jaune
									col=Color.getHSBColor((float)0.17, 1, Brightness);
									break;
								}
								case 2:
								{
									//Cas de deux Julia connexes
									//Degrade de rouge
									col=Color.getHSBColor((float)0.0, 1, Brightness);
									break;
								}
								case 3:
								{
									//Cas de trois Julia connexes
									//Degrade de orange
									col=Color.getHSBColor((float)0.083, 1, Brightness);
									break;
								}
								case 4:
								{
									//Cas ou 4 Julia connexe
									col=Color.getHSBColor((float)0.0, 0, Brightness);
									break;
								}
								default:
									col=Color.white;	
									break;
							}
							}
						//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
						if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes<I.getHeight() && col != Color.BLACK)//
						{
							I.setRGB(newXaxes, newYaxes, col.getRGB());
						}
					ckTot[0] = 0;
					ckTot[1] = 0;
					if(viewProcessPoint)
						stopErreur = imagePanel.setImage(I);
					}//FIN boucle en x
					if(viewProcessLine)
						stopErreur = imagePanel.setImage(I);
				}	//FIN boucle en y
			if (viewProcessSlice)
				stopErreur = imagePanel.setImage(I);//System.out.println();
		}	//FIN boucle en z
    	if(viewProcessNo)
			stopErreur = imagePanel.setImage(I); 
    
    	timeEnd = System.currentTimeMillis();
    	timeEnd = timeEnd - timeStart;
    	System.out.print(timeEnd/3600000 + "hrs. : ");
    	timeEnd = timeEnd % 3600000;
    	System.out.print(timeEnd/60000 + "min. : ");
    	timeEnd = timeEnd % 60000;
    	System.out.print(timeEnd/1000 + "sec.");
    	System.out.println();
    		
    }
    
    private double[] distanceEstimation(Complex cValue)
	{
		Complex z = new Complex(cValue.getRe(), cValue.getIm());
		Complex dz = new Complex(1, 0);
		Complex dzTemp = new Complex();
		Complex zTemp = new Complex(cValue.getRe(), cValue.getIm());
		Complex one = new Complex(1, 0);
		double[] info = new double[3];
		double dist = 0;
		int overIter = 5;
		//double corConstantDistance = 255/Math.log(Math.max(Math.max(axes[1] - axes[0], axes[3] - axes[2]), axes[5] - axes[4]));
		
		int count = 1;
		double maxMod = modulusPower[0] * modulusPower[0];
		
		while (z.getNormeCarre() <= maxMod && count < MAXITER1)
		{
			zTemp = z.power(p).add(cValue);
			dzTemp = z.power(p- 1).scalarMult(p).mult(dz).add(one);
			dz.setReIm(dzTemp.getRe(), dzTemp.getIm());
			z.setReIm(zTemp.getRe(), zTemp.getIm());
			count++;
		}
		
		// DEBUG
		// System.out.println(z.Modulus() * Math.log(z.Modulus()/dz.Modulus()));
		
		if (count >= MAXITER1)
		{
			info[0] = 0;
			info[1] = 1;
			info[2] = MAXITER2;
		}
		else
		{
			info[1] = 0;
			info[2] = count;
			
			if (count < 10)
			{
				for (int i = 0; i < overIter; i++)
				{
					zTemp = z.power(p).add(cValue);
					dzTemp = z.power(p- 1).scalarMult(p).mult(dz).add(one);
					dz.setReIm(dzTemp.getRe(), dzTemp.getIm());
					z.setReIm(zTemp.getRe(), zTemp.getIm());
				}
			}
			
			double normeZ = z.getNorme();
			double normeZp = dz.getNorme();
			
			//dist = normeZ * Math.log(normeZ)/(2 * normeZp * Math.pow(normeZ, Math.pow(p, count)));
			dist =  (p * z.getNorme() * Math.log(z.getNorme())/ dz.getNorme());
			info[0] = dist * dist;
			/*if (dist >= 0.01)
			{
				info[0] = 0 - (int)(corConstantDistance * Math.log(dist));//(int)(50 * Math.abs(dist));
				if (info[0] < 0)
					info[0] = -info[0];
			}
			else
			{
				info[0] = 0;
			}*/
		}
		
		return info;
		
	}
    
    boolean normalPlanEval(double componentNx, double componentNy, double componentNz, double pointX, double pointY, double pointZ, double scalarProduct)
    {
    	double eval;
    	eval = componentNx * pointX + componentNy * pointY + componentNz * pointZ - scalarProduct;
    	
    	if (eval >= 0)
    		return false;
    	else
    		return true;
    }
    
    public void setParamSequenceRotation(int p, int p2, boolean doPowerSequence, boolean[] coupe, double[] componentValues, int style,int minIter, int maxIter, 
			int[] dimension, double[] axes, int pourcentageInt, double[] rot, double[] endRot, double[] increment, int viewProcess, boolean viewAxes, boolean renderInFrame,
			boolean isrenderOctaedreThm, String chemin, int typeOfSlice)
	{
		
		this.dimension = dimension;
		this.I = new BufferedImage(dimension[0] + 1, dimension[1] + 1, BufferedImage.TYPE_INT_ARGB);
		//resetImage();
		//this.matrixImage = new int[dimension[0] + 1][dimension[1] + 1][dimension[2] + 1];
		this.renderInFrame = renderInFrame;
		this.viewAxes = viewAxes;
		this.isRenderOctaedreThm = isrenderOctaedreThm;
		this.renderRotationSequence = true;
		this.renderDistanceEst = false;
		this.chemin = chemin;
		this.typeOfSlice = typeOfSlice;
		
		switch (viewProcess)
		{
		case 1:
			viewProcessNo = true;
			viewProcessLine = false;
			viewProcessPoint = false;
			viewProcessSlice = false;
			break;
		case 2:
			viewProcessPoint = true;
			viewProcessLine = false;
			viewProcessSlice = false;
			viewProcessNo = false;
			break;
		case 3:
			viewProcessLine = true;
			viewProcessPoint = false;
			viewProcessSlice = false;
			viewProcessNo = false;
			break;
		case 4:
			viewProcessSlice = true;
			viewProcessLine = false;
			viewProcessPoint = false;
			viewProcessNo = false;
			break;
			default:
				viewProcessNo = true;
				viewProcessPoint = viewProcessLine = viewProcessSlice = false;
		}
		
		this.p = p;
		this.p2 = p2;
		this.nbBrot = p2 - p + 1;
		this.doPowerSequence = doPowerSequence;
		this.coupe = coupe;
		this.message = "";
		if (coupe[0])
			message += "1";
		if (coupe[1])
			message += "i1";
		if (coupe[2])
			message += "i2";
		if (coupe[3])
			message += "i3";
		if (coupe[4])
			message += "i4";
		if (coupe[5])
			message += "j1";
		if (coupe[6])
			message += "j2";
		if (coupe[7])
			message += "j3";
		this.componentValues = componentValues;
		this.style = style;
		this.minIter = minIter;
		this.maxIter = maxIter;
		
		/*this.axes = axes;
		this.limModIter = Math.pow(2.0,(double)1/(p-1));
		this.limModIterS = limModIter * limModIter;*/
		
		this.axesPower = new double[nbBrot][6];
		
		this.axesPower[0] = axes;
		modulusPower = new double[nbBrot];
		minModulus = new double[nbBrot];
		modulusPower[0] = Math.max(Math.pow(2.0, (double)1/(p -1)), c.getEuclMod2());
		minModulus[0] = (p - 1)/(p * Math.pow(p, (double)1/(p-1)));
		
		int newPower;
		for(int i = 1; i < nbBrot; i++)
		{
			newPower = p + i;
			modulusPower[i] = Math.max(Math.pow(2.0, (double)1/(p + i - 1)), c.getEuclMod2());
			minModulus[i] = (newPower - 1)/(newPower * Math.pow(newPower, (double)1/((newPower-1))));
			axesPower[i][0] = axesPower[i][2] = axesPower[i][4] = -modulusPower[i];
			axesPower[i][1] = axesPower[i][3] = axesPower[i][5] = modulusPower[i];
		}
		
		this.rot[0] = rot[0];
		this.rot[1] = rot[1];
		this.rot[2] = rot[2];
		this.rotEnd[0] = endRot[0];
		this.rotEnd[1] = endRot[1];
		this.rotEnd[2] = endRot[2];
		this.increment[0] = increment[0];
		this.increment[1] = increment[1];
		this.increment[2] = increment[2];
		this.totalIncrement = increment[0] + increment[1] + increment[2];
		this.rotOp = new Rotation(rot[0], rot[1], rot[2], 1);
		
		isThereRotation = true;
		
		this.zoom = new int[nbBrot][3];
		for(int i = 0; i < nbBrot ; i++)
		{
			this.zoom[i][0] = (int)(dimension[0]/(axesPower[i][1] - axesPower[i][0]));
			this.zoom[i][1] = (int)(dimension[1]/(axesPower[i][3] - axesPower[i][2]));
			this.zoom[i][2] = (int)(dimension[2]/(axesPower[i][5] - axesPower[i][4]));
		}
		
		this.pourcentageInt = pourcentageInt;
		
		//this.factz = 1/((double)(100-pourcentageInt)/100);
		this.factz = 1 - (double)pourcentageInt/100;
		//System.out.println("Je suis passé par là.");
		
	}
    
    public void calcRotationSequence(int i)
    {
    	int pixX = 0, pixY = 0;
    	int progressValue = progress.getValue();
    	int zoomX = zoom[i][0], zoomY = zoom[i][1], zoomZ = zoom[i][2];
    	double minX = axesPower[i][0], maxX = axesPower[i][1];
    	double minY = axesPower[i][2], maxY = axesPower[i][3];
    	double minZ = axesPower[i][4], maxZ = axesPower[i][5];
    	long timeStart, timeEnd;
    	int newXaxes, newYaxes, newZaxes;
    	double normalX, normalY, normalZ;
    	normalX = 0;
    	normalY = 0;
    	normalZ = 1;
    	double wX = 0, wY = 0, wZ = 0;
    	
    	double scalProd =  (maxZ - minZ) * factz + minZ;
    	int coupeZ = (int) (factz * dimension[2]);
    	boolean onPlane = true;
    	
    	timeStart = System.currentTimeMillis();  
    	for (int j = 0; j <= totalIncrement; j++)
    	{  		
    		rotOp.setAngleDeg(rot[0] + (j) * (rotEnd[0] - rot[0])/totalIncrement, rot[1] + (j)* (rotEnd[1] - rot[1])/totalIncrement,
    				rot[2] + (j) * (rotEnd[2] - rot[2])/totalIncrement);
    		System.out.println(rotOp.toString());
    		
    		for (int z=0; z <= coupeZ; z++)
    		{
    			for (int y=0; y < I.getHeight();y++)
    			{
    				for (int x=0;x<I.getWidth();x++)
					{
						tempsDebut=System.currentTimeMillis();
						if (stop || stopErreur)
						{
							z = dimension[2];
							break;
						}
						
						//On fixe les parties du nombre c
						pX=(double)x/zoomX+minX;
						pY=maxY - (double)y/zoomY;
						pZ=(double)(z)/zoomZ+minZ;
						//onPlane = normalPlanEval(normalX, normalY, normalZ, pX, pY, pZ, scalProd);
						
						//On fait la rotation désirée
						if (isThereRotation)
						{
							rotOp.doRotation(x - dimension[0]/2, y - dimension[1]/2, z - dimension[2]/2);
							rotOp.doRotation(pX, pY, pZ);
							pX = rotOp.getEndPointX();
							pY = rotOp.getEndPointY();
							pZ = rotOp.getEndPointZ();
						}
							
						/*if (axeX.isOn(pX, pY, pZ))
							I.setRGB(x, y, Color.white.getRGB());
						if (axeY.isOn(pX, pY, pZ))
							I.setRGB(x, y, Color.white.getRGB());
						if (axeZ.isOn(pX, pY, pZ))
							I.setRGB(x, y, Color.white.getRGB());*/
						
						switch (typeOfSlice)
						{
						case 1:
							c.setComponent(pX, pY, pZ, coupe, componentValues);
							break;
						case 2:
							c.setComponentIdempotent(pX, pY, pZ, coupe, componentValues);
							break;
							default:
								c.setComponent(pX, pY, pZ, coupe, componentValues);
								break;
						}
						
						
						if (coupe[0] & coupe[5] & coupe[6] ||
							coupe[0] & coupe[6] & coupe[7] ||
							coupe[0] & coupe[5] & coupe[7] ||
							coupe[5] & coupe[6] & coupe[7])
							Brightness=(float)1 - (float)(z)/(dimension[2]);
						else
						{
							Brightness=(float)0.1+(float)(9*z)/(10*dimension[2]);
							//Brightness = (float)Brightness;
						}
						
						ck=Tribrot.check(c, p + i, modulusPower[i]);
						
						if(z == coupeZ)
						{
							//a la coupe
							if  (ck >= minIter && ck <= maxIter)
							{
								col = Color.getHSBColor(tabHSB2[ck], 1, (float)0.1+(float)(9*z)/(10*dimension[2]));
						
								//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
								if (x>=0 && x<I.getWidth() && y>=0 && y < I.getHeight())
								{
									I.setRGB(x, y, col.getRGB());
								}	
							}
						}
						else
						{
							// On teste pour accélérer les calculs
							if (ck >= minIter & ck <= maxIter)
							{
									//Obtenir des informations sur les ensemble de Julia rempli en decomposant le nombre c sur ces composantes
								//idempotentes bicomplexes et tricomplexes
								
								cX=c.getRe()+c.getHyp3()+c.getHyp1()-c.getHyp2();
								cY=c.getIm1()+c.getIm4()-c.getIm2()+c.getIm3();
								ck1 = Tribrot.checkMandelbrot(cX, cY, p+i , modulusPower[i]);
								
								cX=c.getRe()+c.getHyp3()-c.getHyp1()+c.getHyp2();
								cY=c.getIm1()+c.getIm4()+c.getIm2()-c.getIm3();
								ck2 = Tribrot.checkMandelbrot(cX, cY, p+i , modulusPower[i]);
								
								cX=c.getRe()-c.getHyp3()+c.getHyp1()+c.getHyp2();
								cY=c.getIm1()-c.getIm4()-c.getIm2()-c.getIm3();
								ck3 = Tribrot.checkMandelbrot(cX, cY, p+i , modulusPower[i]);
								
								cX=c.getRe()-c.getHyp3()-c.getHyp1()-c.getHyp2();
								cY=c.getIm1()-c.getIm4()+c.getIm2()+c.getIm3();
								ck4 = Tribrot.checkMandelbrot(cX, cY, p+i , modulusPower[i]);
								
								ckTot[0]=ck1[0]+ck2[0]+ck3[0]+ck4[0];
								ckTot[1]=(ck1[1]+ck2[1]+ck3[1]+ck4[1])/4;
								
								//Selon l'entier retourne, on verifie combien de Julia sont Connexe et on associe une couleur aux pixels
								//selon l'entier retourner par la methode check. A l'interieur de chaque cas (les cases), des conditionnels
								//permettent de faire le bon choix de couleur en fonction de l'entier ckTot obtenu par la methode checkMandelbrot.
								//Les couleurs sont obtenues selon un degrade en profondeur (selon l'axe z et donc selon les valeurs de z).
								
								//col = Color.getHSBColor(tabHSB[ckTot[1]], 1, Brightness);
								//if (x % 10 == 0)
									//col = Color.getHSBColor(0, 0, Brightness);
								
								switch (ckTot[0])
								{
									case 0:
									{
										//Cas ou Julia Cantor (au sens tricomplexe)
										col=Color.getHSBColor(tabHSB[ckTot[1]], 1, Brightness);
										break;
									}
									case 1:
									{
										//Cas d'un Julia connexes
										//Degrade de Jaune
										col=Color.getHSBColor((float)0.17, 1, Brightness);
										break;
									}
									case 2:
									{
										//Cas de deux Julia connexes
										//Degrade de rouge
										col=Color.getHSBColor((float)0.0, 1, Brightness);
										break;
									}
									case 3:
									{
										//Cas de trois Julia connexes
										//Degrade de orange
										col=Color.getHSBColor((float)0.083, 1, Brightness);
										break;
									}
									case 4:
									{
										//Cas ou 4 Julia connexe
										col=Color.getHSBColor((float)0.0, 0,(float)Brightness);
										break;
									}
									default:
										col=Color.white;	
									break;
								}
								//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
								if (x>=0 && x<I.getWidth() && y>=0 && y<I.getHeight())
								{
									I.setRGB(x, y, col.getRGB());
								}
							}
						}
					
					}//FIN boucle en x
					
				}	//FIN boucle en y
		}	//FIN boucle en z
    	try 
    	{
    		ImageIO.write(I, "PNG", new File(chemin + progressValue + ".PNG"));
		} catch (Exception e) 
    	{
			// TODO: handle exception
		}
    	progress.setValue(progressValue++);
    	I = new BufferedImage(I.getWidth(), I.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	}
    	
    	timeEnd = System.currentTimeMillis();
    	timeEnd = timeEnd - timeStart;
    	System.out.print(timeEnd/3600000 + "hrs. : ");
    	timeEnd = timeEnd % 3600000;
    	System.out.print(timeEnd/60000 + "min. : ");
    	timeEnd = timeEnd % 60000;
    	System.out.print(timeEnd/1000 + "sec.");
    	System.out.println();
    }
    
    public void setParamSequenceSlice(int p, int p2, boolean doPowerSequence, boolean[] coupeStart, boolean[] coupeEnd, double[] componentValuesStart,
    		double[] componentValuesEnd, double startAxesValues, double endAxesValues, double maxAxesValues, int style,int minIter, int maxIter, int[] dimension, double[] axes, int pourcentageInt, double[] rot, double totalIncrement, int viewProcess, boolean viewAxes, boolean renderInFrame, boolean isrenderOctaedreThm, String chemin,
    		int[] coucheMin, int[] coucheMax, double[] light, int typeOfSequence)
    {
    	this.dimension = dimension;
		this.I = new BufferedImage(dimension[0] + 1, dimension[1] + 1, BufferedImage.TYPE_INT_ARGB);
    	//resetImage();
		//this.matrixImage = new int[dimension[0] + 1][dimension[1] + 1][dimension[2] + 1];
		this.renderInFrame = renderInFrame;
		this.viewAxes = viewAxes;
		this.isRenderOctaedreThm = isrenderOctaedreThm;
		this.renderRotationSequence = false;
		this.renderSliceSequence = true;
		this.chemin = chemin;
		
		this.coucheMax = coucheMax;
		this.coucheMin = coucheMin;
		
		this.lightX = light[0];
		this.lightY = light[1];
		this.lightZ = light[2];
		
		this.startAxesValues = startAxesValues;
		this.endAxesValues = endAxesValues;
		this.maxAxesValues = maxAxesValues;
		
		switch (viewProcess)
		{
		case 1:
			viewProcessNo = true;
			viewProcessLine = false;
			viewProcessPoint = false;
			viewProcessSlice = false;
			break;
		case 2:
			viewProcessPoint = true;
			viewProcessLine = false;
			viewProcessSlice = false;
			viewProcessNo = false;
			break;
		case 3:
			viewProcessLine = true;
			viewProcessPoint = false;
			viewProcessSlice = false;
			viewProcessNo = false;
			break;
		case 4:
			viewProcessSlice = true;
			viewProcessLine = false;
			viewProcessPoint = false;
			viewProcessNo = false;
			break;
			default:
				viewProcessNo = true;
				viewProcessPoint = viewProcessLine = viewProcessSlice = false;
		}
		
		this.p = p;
		this.p2 = p2;
		this.nbBrot = p2 - p + 1;
		this.doPowerSequence = doPowerSequence;
		this.coupeStart = coupeStart;
		this.message = "Start-";
		if (coupeStart[0])
			message += "1";
		if (coupeStart[1])
			message += "i1";
		if (coupeStart[2])
			message += "i2";
		if (coupeStart[3])
			message += "i3";
		if (coupeStart[4])
			message += "i4";
		if (coupeStart[5])
			message += "j1";
		if (coupeStart[6])
			message += "j2";
		if (coupeStart[7])
			message += "j3";
		message+="_";
		this.componentValuesStart = componentValuesStart;
		
		this.coupeEnd = coupeEnd;
		this.message += "End-";
		if (coupeEnd[0])
			message += "1";
		if (coupeEnd[1])
			message += "i1";
		if (coupeEnd[2])
			message += "i2";
		if (coupeEnd[3])
			message += "i3";
		if (coupeEnd[4])
			message += "i4";
		if (coupeEnd[5])
			message += "j1";
		if (coupeEnd[6])
			message += "j2";
		if (coupeEnd[7])
			message += "j3";
		this.componentValuesEnd = componentValuesEnd;
		
		this.style = style;
		this.minIter = minIter;
		this.maxIter = maxIter;
		
		/*this.axes = axes;
		this.limModIter = Math.pow(2.0,(double)1/(p-1));
		this.limModIterS = limModIter * limModIter;*/
		
		this.axesPower = new double[nbBrot][6];
		
		this.axesPower[0] = axes;
		modulusPower = new double[nbBrot];
		minModulus = new double[nbBrot];
		modulusPower[0] = Math.max(Math.pow(2.0, (double)1/(p -1)), c.getEuclMod2());
		minModulus[0] = (p - 1)/(p * Math.pow(p, (double)1/(p-1)));
		
		int newPower;
		for(int i = 1; i < nbBrot; i++)
		{
			newPower = p + i;
			modulusPower[i] = Math.max(Math.pow(2.0, (double)1/(p + i - 1)), c.getEuclMod2());
			minModulus[i] = (newPower - 1)/(newPower * Math.pow(newPower, (double)1/((newPower-1))));
			axesPower[i][0] = axesPower[i][2] = axesPower[i][4] = -modulusPower[i];
			axesPower[i][1] = axesPower[i][3] = axesPower[i][5] = modulusPower[i];
		}
		
		this.rot[0] = rot[0];
		this.rot[1] = rot[1];
		this.rot[2] = rot[2];
		this.totalIncrement = totalIncrement;
		this.rotOp = new Rotation(rot[0], rot[1], rot[2], 1);
		
		if (rot[0] != 0 || rot[1] != 0 || rot[2] != 0)
			isThereRotation = true;
		else
			isThereRotation = false;
		
		this.zoom = new int[nbBrot][3];
		for(int i = 0; i < nbBrot ; i++)
		{
			this.zoom[i][0] = (int)(dimension[0]/(axesPower[i][1] - axesPower[i][0]));
			this.zoom[i][1] = (int)(dimension[1]/(axesPower[i][3] - axesPower[i][2]));
			this.zoom[i][2] = (int)(dimension[2]/(axesPower[i][5] - axesPower[i][4]));
		}
		
		this.pourcentageInt = pourcentageInt;
		
		//this.factz = 1/((double)(100-pourcentageInt)/100);
		this.factz = 1 - (double)pourcentageInt/100;
		//System.out.println("Je suis passé par là.");
		
		this.typeOfSequence = typeOfSequence;
    }
    
    public void calcSliceSequence(int i)
    {
    		switch (typeOfSequence)
    		{
    		case 1:
    			realToReal(i);
    			System.out.println("I'm Here");
    			break;
    		case 2:
    			realToIdemp(i);
    			System.out.println("REal to Idemp");
    			break;
    		case 3:
    			idempToReal(i);
    			System.out.println("IDemp to REal");
    			break;
    		case 4:
    			idempToIdemp(i);
    			System.out.println("Idemp to Idemp");
    			break;
    		default:
    			realToReal(i);
    		}
    }
    
    private void realToReal(int i)
    {
    	Tricomplex cStart = new Tricomplex();
    	Tricomplex cEnd = new Tricomplex();

    	int pixX = 0, pixY = 0;
    	int progressValue = progress.getValue();
    	int zoomX = zoom[i][0], zoomY = zoom[i][1], zoomZ = zoom[i][2];
    	
    	/*double minXStart = axesPower[i][0], maxXStart = axesPower[i][1];
    	double minYStart = axesPower[i][2], maxYStart = axesPower[i][3];
    	double minZStart = axesPower[i][4], maxZStart = axesPower[i][5];*/
    	double minX = axesPower[i][0], maxX = axesPower[i][1];
    	double minY = axesPower[i][2], maxY = axesPower[i][3];
    	double minZ= axesPower[i][4], maxZ = axesPower[i][5];
    	/*double minXEnd = -0.5, maxXEnd = 0.5;
    	double minYEnd = -0.5, maxYEnd = 0.5;
    	double minZEnd = -0.5, maxZEnd = 0.5;*/
    	/*double minXEnd = axesPowerEnd[i][0], maxXEnd = axesPowerEnd[i][1];
    	double minYEnd = axesPowerEnd[i][2], maxYEnd = axesPowerEnd[i][3];
    	double minZEnd = axesPowerEnd[i][4], maxZEnd = axesPowerEnd[i][5];*/
    	
    	long timeStart, timeEnd;
    	int newXaxes, newYaxes, newZaxes;
    	double normalX, normalY, normalZ;
    	//double minX, maxX, minY, maxY, minZ, maxZ;
    	normalX = 0;
    	normalY = 0;
    	normalZ = 1;
    	
    	double t = 0;
    	double u = 0;
    	/*coupeStart = new boolean[8];
    	coupeStart[0] = coupeStart[5] = coupeStart[6] = true;
    	coupeStart[1] = coupeStart[2] = coupeStart[3] = coupeStart[4] = coupeStart[7] = false;
    	boolean[] coupeEnd = new boolean[8];
    	coupeEnd[0] = coupeEnd[1] = coupeEnd[2] = coupeEnd[3] = coupeEnd[4] = false;
    	coupeEnd[5] = coupeEnd[6] = coupeEnd[7] = true;
    	double[] componentValuesStart = new double[8];
    	double[] componentValuesEnd = new double[8];*/
    		
    	for (int j = 0; j < 8; j++)
    	{
    		componentValuesStart[j] = 0;
    		componentValuesEnd[j] = 0;
    	}
    		
    	double scalProd =  (maxZ - minZ) * factz + minZ;
    	int coupeZ = (int) (factz * dimension[2]);
    	boolean onPlane = true;
    	
    	timeStart = System.currentTimeMillis();  
    	for (int h = 0; h <= (int)totalIncrement; h++)
    	{
    		t = (double)h / totalIncrement; 
    		
    		if (h < totalIncrement/2)
    			u =  startAxesValues + 2 * (maxAxesValues - startAxesValues) * t;
    		else
    			u = (endAxesValues - 2*(endAxesValues - maxAxesValues)) + 2 * (endAxesValues - maxAxesValues) * t;
    		//System.out.print(u);
    		
    		minX = -u; maxX = u;
    		minY = -u; maxY = u;
    		minZ = -u; maxZ = u;
    		
    		zoomX = (int) (dimension[0]/(maxX - minX));
    		zoomY = (int) (dimension[1]/(maxY - minY));
    		zoomZ = (int) (dimension[2]/(maxY - minY));
    		
    		minIter = (int) (coucheMin[0] + (coucheMin[1] - coucheMin[0]) * t);
    		maxIter = (int) (coucheMax[0] + (coucheMax[1] - coucheMax[0]) * t);
    		
    		System.out.println("Couche min. : " + minIter);
    		System.out.println("Couche max. : " + maxIter);
    		
		for (int z=0; z <= coupeZ; z++)
		{
			for (int y=0; y < I.getHeight();y++)
			{
				for (int x=0;x<I.getWidth();x++)
				{
					tempsDebut=System.currentTimeMillis();
					if (stop)
					{
						z = dimension[2];
						break;
					}
					
					//On fixe les parties du nombre c
					pX=(double)x/zoomX+minX;
					pY=maxY - (double)y/zoomY;
					pZ=(double)(z)/zoomZ+minZ;
					newXaxes = x;
					newYaxes = y;
					newZaxes = z;
					//onPlane = normalPlanEval(normalX, normalY, normalZ, pX, pY, pZ, scalProd);
					
					//On fait la rotation désirée
					if (isThereRotation)
					{
						rotOp.doRotation(pX, pY, pZ);
						pX = rotOp.getEndPointX();
						pY = rotOp.getEndPointY();
						pZ = rotOp.getEndPointZ();
						/*rotOp.doRotation(x - dimension[0]/2, y - dimension[1]/2, z - dimension[2]/2);
						newXaxes = (int)rotOp.getEndPointX() + dimension[0]/2;
						newYaxes = (int)rotOp.getEndPointY() + dimension[1]/2;
						newZaxes = (int)rotOp.getEndPointZ() + dimension[2]/2;*/
					}
					/*else
					{
						newXaxes = x;
						newYaxes = y;
						newZaxes = z;
					}*/
						
					/*if (axeX.isOn(pX, pY, pZ))
						I.setRGB(x, y, Color.white.getRGB());
					if (axeY.isOn(pX, pY, pZ))
						I.setRGB(x, y, Color.white.getRGB());
					if (axeZ.isOn(pX, pY, pZ))
						I.setRGB(x, y, Color.white.getRGB());*/
					
					cStart.setComponent(pX, pY, pZ, coupeStart, componentValuesStart);
					cEnd.setComponent(pX, pY, pZ, coupeEnd, componentValuesEnd);
					
					c = cStart.scalMult(1-t).add(cEnd.scalMult(t));
					
					
						//Brightness=(float)0.1+(float)(9*z)/(10*dimension[2]);
						//Brightness = (float)Brightness;
					Brightness = (float)(((pX * lightX + pY * lightY + pZ * lightZ)/ ( normLight * Math.sqrt(pX * pX + pY * pY + pZ * pZ))) + 1)/2;
					
					
					ck=Tribrot.check(c,p + i, modulusPower[i]);
					
					/*if(z == coupeZ)
					{
						//a la coupe
						if  (ck >= minIter && ck <= maxIter)
						{
							col = Color.getHSBColor(tabHSB2[ck], 1, (float)0.1+(float)(9*z)/(10*dimension[2]));
					
							//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes < I.getHeight())
							{
								I.setRGB(newXaxes,newYaxes,col.getRGB());
							}	
						}
					}
					else*/
					{
						// On teste pour accélérer les calculs
						if (ck >= minIter & ck <= maxIter)
						{
								//Obtenir des informations sur les ensemble de Julia rempli en decomposant le nombre c sur ces composantes
							//idempotentes bicomplexes et tricomplexes
							cX=c.getRe()+c.getHyp3()+c.getHyp1()-c.getHyp2();
							cY=c.getIm1()+c.getIm4()-c.getIm2()+c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck1=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck1[0] = 1;
								ck1[1] = MAXITER2;
							}
							
							cX=c.getRe()+c.getHyp3()-c.getHyp1()+c.getHyp2();
							cY=c.getIm1()+c.getIm4()+c.getIm2()-c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck2=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck2[0] = 1;
								ck2[1] = MAXITER2;
							}
							
							cX=c.getRe()-c.getHyp3()+c.getHyp1()+c.getHyp2();
							cY=c.getIm1()-c.getIm4()-c.getIm2()-c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck3=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck3[0] = 1;
								ck3[1] = MAXITER2;
							}
							
							cX=c.getRe()-c.getHyp3()-c.getHyp1()-c.getHyp2();
							cY=c.getIm1()-c.getIm4()+c.getIm2()+c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck4=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck4[0] = 1;
								ck4[1] = MAXITER2;
							}
							
							ckTot[0]=ck1[0]+ck2[0]+ck3[0]+ck4[0];
							ckTot[1]=(ck1[1]+ck2[1]+ck3[1]+ck4[1])/4;
							
			
							//Selon l'entier retourne, on verifie combien de Julia sont Connexe et on associe une couleur aux pixels
							//selon l'entier retourner par la methode check. A l'interieur de chaque cas (les cases), des conditionnels
							//permettent de faire le bon choix de couleur en fonction de l'entier ckTot obtenu par la methode checkMandelbrot.
							//Les couleurs sont obtenues selon un degrade en profondeur (selon l'axe z et donc selon les valeurs de z).
							
							switch (ckTot[0])
							{
								case 0:
								{
									//Cas ou Julia Cantor (au sens tricomplexe)
									col=Color.getHSBColor(tabHSB[ckTot[1]], 1, Brightness);
									break;
								}
								case 1:
								{
									//Cas d'un Julia connexes
									//Degrade de Jaune
									col=Color.getHSBColor((float)0.17, 1, Brightness);
									break;
								}
								case 2:
								{
									//Cas de deux Julia connexes
									//Degrade de rouge
									col=Color.getHSBColor((float)0.0, 1, Brightness);
									break;
								}
								case 3:
								{
									//Cas de trois Julia connexes
									//Degrade de orange
									col=Color.getHSBColor((float)0.083, 1, Brightness);
									break;
								}
								case 4:
								{
									//Cas ou 4 Julia connexe
									col=Color.getHSBColor((float)0.0, 0,(float)Brightness);
									break;
								}
								default:
									col=Color.white;	
								break;
							}
							//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes<I.getHeight())
							{
								I.setRGB(newXaxes, newYaxes,col.getRGB());
							}
						}
					}
				}//FIN boucle en x
			}	//FIN boucle en y
		}//FIN boucle en z
		try 
    	{
    		ImageIO.write(I, "PNG", new File(chemin + progressValue + ".PNG"));
		} catch (Exception e) 
    	{
			// TODO: handle exception
		}
    	progress.setValue(progressValue++);
    	I = new BufferedImage(I.getWidth(), I.getHeight(), BufferedImage.TYPE_INT_ARGB);

    	timeEnd = System.currentTimeMillis();
		timeEnd = timeEnd - timeStart;
		System.out.print(timeEnd/3600000 + "hrs. : ");
		timeEnd = timeEnd % 3600000;
		System.out.print(timeEnd/60000 + "min. : ");
		timeEnd = timeEnd % 60000;
		System.out.print(timeEnd/1000 + "sec.");
		System.out.println();
    	}
    }
    
    private void realToIdemp(int i)
    {
    	Tricomplex cStart = new Tricomplex();
    	Tricomplex cEnd = new Tricomplex();

    	int pixX = 0, pixY = 0;
    	int progressValue = progress.getValue();
    	int zoomX = zoom[i][0], zoomY = zoom[i][1], zoomZ = zoom[i][2];
    	
    	/*double minXStart = axesPower[i][0], maxXStart = axesPower[i][1];
    	double minYStart = axesPower[i][2], maxYStart = axesPower[i][3];
    	double minZStart = axesPower[i][4], maxZStart = axesPower[i][5];*/
    	double minX = axesPower[i][0], maxX = axesPower[i][1];
    	double minY = axesPower[i][2], maxY = axesPower[i][3];
    	double minZ= axesPower[i][4], maxZ = axesPower[i][5];
    	/*double minXEnd = -0.5, maxXEnd = 0.5;
    	double minYEnd = -0.5, maxYEnd = 0.5;
    	double minZEnd = -0.5, maxZEnd = 0.5;*/
    	/*double minXEnd = axesPowerEnd[i][0], maxXEnd = axesPowerEnd[i][1];
    	double minYEnd = axesPowerEnd[i][2], maxYEnd = axesPowerEnd[i][3];
    	double minZEnd = axesPowerEnd[i][4], maxZEnd = axesPowerEnd[i][5];*/
    	
    	long timeStart, timeEnd;
    	int newXaxes, newYaxes, newZaxes;
    	double normalX, normalY, normalZ;
    	//double minX, maxX, minY, maxY, minZ, maxZ;
    	normalX = 0;
    	normalY = 0;
    	normalZ = 1;
    	
    	double t = 0;
    	double u = 0;
    	/*coupeStart = new boolean[8];
    	coupeStart[0] = coupeStart[5] = coupeStart[6] = true;
    	coupeStart[1] = coupeStart[2] = coupeStart[3] = coupeStart[4] = coupeStart[7] = false;
    	boolean[] coupeEnd = new boolean[8];
    	coupeEnd[0] = coupeEnd[1] = coupeEnd[2] = coupeEnd[3] = coupeEnd[4] = false;
    	coupeEnd[5] = coupeEnd[6] = coupeEnd[7] = true;
    	double[] componentValuesStart = new double[8];
    	double[] componentValuesEnd = new double[8];*/
    		
    	for (int j = 0; j < 8; j++)
    	{
    		componentValuesStart[j] = 0;
    		componentValuesEnd[j] = 0;
    	}
    		
    	double scalProd =  (maxZ - minZ) * factz + minZ;
    	int coupeZ = (int) (factz * dimension[2]);
    	boolean onPlane = true;
    	
    	timeStart = System.currentTimeMillis();  
    	for (int h = 0; h <= (int)totalIncrement; h++)
    	{
    		t = (double)h / totalIncrement; 
    		
    		if (h < totalIncrement/2)
    			u =  startAxesValues + 2 * (maxAxesValues - startAxesValues) * t;
    		else
    			u = (endAxesValues - 2*(endAxesValues - maxAxesValues)) + 2 * (endAxesValues - maxAxesValues) * t;
    		//System.out.print(u);
    		
    		minX = -u; maxX = u;
    		minY = -u; maxY = u;
    		minZ = -u; maxZ = u;
    		
    		zoomX = (int) (dimension[0]/(maxX - minX));
    		zoomY = (int) (dimension[1]/(maxY - minY));
    		zoomZ = (int) (dimension[2]/(maxY - minY));
    		
    		minIter = (int) (coucheMin[0] + (coucheMin[1] - coucheMin[0]) * t);
    		maxIter = (int) (coucheMax[0] + (coucheMax[1] - coucheMax[0]) * t);
    		
    		System.out.println("Couche min. : " + minIter);
    		System.out.println("Couche max. : " + maxIter);
    		
		for (int z=0; z <= coupeZ; z++)
		{
			for (int y=0; y < I.getHeight();y++)
			{
				for (int x=0;x<I.getWidth();x++)
				{
					tempsDebut=System.currentTimeMillis();
					if (stop)
					{
						z = dimension[2];
						break;
					}
					
					//On fixe les parties du nombre c
					pX=(double)x/zoomX+minX;
					pY=maxY - (double)y/zoomY;
					pZ=(double)(z)/zoomZ+minZ;
					newXaxes = x;
					newYaxes = y;
					newZaxes = z;
					//onPlane = normalPlanEval(normalX, normalY, normalZ, pX, pY, pZ, scalProd);
					
					//On fait la rotation désirée
					if (isThereRotation)
					{
						rotOp.doRotation(pX, pY, pZ);
						pX = rotOp.getEndPointX();
						pY = rotOp.getEndPointY();
						pZ = rotOp.getEndPointZ();
						/*rotOp.doRotation(x - dimension[0]/2, y - dimension[1]/2, z - dimension[2]/2);
						newXaxes = (int)rotOp.getEndPointX() + dimension[0]/2;
						newYaxes = (int)rotOp.getEndPointY() + dimension[1]/2;
						newZaxes = (int)rotOp.getEndPointZ() + dimension[2]/2;*/
					}
					/*else
					{
						newXaxes = x;
						newYaxes = y;
						newZaxes = z;
					}*/
						
					/*if (axeX.isOn(pX, pY, pZ))
						I.setRGB(x, y, Color.white.getRGB());
					if (axeY.isOn(pX, pY, pZ))
						I.setRGB(x, y, Color.white.getRGB());
					if (axeZ.isOn(pX, pY, pZ))
						I.setRGB(x, y, Color.white.getRGB());*/
					
					cStart.setComponent(pX, pY, pZ, coupeStart, componentValuesStart);
					cEnd.setComponentIdempotent(pX, pY, pZ, coupeEnd, componentValuesEnd);
					//System.out.println(cEnd.toStringIdemp());
					
					c = cStart.scalMult(1-t).add(cEnd.scalMult(t));
					
					
						//Brightness=(float)0.1+(float)(9*z)/(10*dimension[2]);
						//Brightness = (float)Brightness;
					Brightness = (float)(((pX * lightX + pY * lightY + pZ * lightZ)/ ( normLight * Math.sqrt(pX * pX + pY * pY + pZ * pZ))) + 1)/2;
					
					
					ck=Tribrot.check(c,p + i, modulusPower[i]);
					
					/*if(z == coupeZ)
					{
						//a la coupe
						if  (ck >= minIter && ck <= maxIter)
						{
							col = Color.getHSBColor(tabHSB2[ck], 1, (float)0.1+(float)(9*z)/(10*dimension[2]));
					
							//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes < I.getHeight())
							{
								I.setRGB(newXaxes,newYaxes,col.getRGB());
							}	
						}
					}
					else*/
					{
						// On teste pour accélérer les calculs
						if (ck >= minIter & ck <= maxIter)
						{
								//Obtenir des informations sur les ensemble de Julia rempli en decomposant le nombre c sur ces composantes
							//idempotentes bicomplexes et tricomplexes
							cX=c.getRe()+c.getHyp3()+c.getHyp1()-c.getHyp2();
							cY=c.getIm1()+c.getIm4()-c.getIm2()+c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck1=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck1[0] = 1;
								ck1[1] = MAXITER2;
							}
							
							cX=c.getRe()+c.getHyp3()-c.getHyp1()+c.getHyp2();
							cY=c.getIm1()+c.getIm4()+c.getIm2()-c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck2=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck2[0] = 1;
								ck2[1] = MAXITER2;
							}
							
							cX=c.getRe()-c.getHyp3()+c.getHyp1()+c.getHyp2();
							cY=c.getIm1()-c.getIm4()-c.getIm2()-c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck3=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck3[0] = 1;
								ck3[1] = MAXITER2;
							}
							
							cX=c.getRe()-c.getHyp3()-c.getHyp1()-c.getHyp2();
							cY=c.getIm1()-c.getIm4()+c.getIm2()+c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck4=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck4[0] = 1;
								ck4[1] = MAXITER2;
							}
							
							ckTot[0]=ck1[0]+ck2[0]+ck3[0]+ck4[0];
							ckTot[1]=(ck1[1]+ck2[1]+ck3[1]+ck4[1])/4;
							
			
							//Selon l'entier retourne, on verifie combien de Julia sont Connexe et on associe une couleur aux pixels
							//selon l'entier retourner par la methode check. A l'interieur de chaque cas (les cases), des conditionnels
							//permettent de faire le bon choix de couleur en fonction de l'entier ckTot obtenu par la methode checkMandelbrot.
							//Les couleurs sont obtenues selon un degrade en profondeur (selon l'axe z et donc selon les valeurs de z).
							
							switch (ckTot[0])
							{
								case 0:
								{
									//Cas ou Julia Cantor (au sens tricomplexe)
									col=Color.getHSBColor(tabHSB[ckTot[1]], 1, Brightness);
									break;
								}
								case 1:
								{
									//Cas d'un Julia connexes
									//Degrade de Jaune
									col=Color.getHSBColor((float)0.17, 1, Brightness);
									break;
								}
								case 2:
								{
									//Cas de deux Julia connexes
									//Degrade de rouge
									col=Color.getHSBColor((float)0.0, 1, Brightness);
									break;
								}
								case 3:
								{
									//Cas de trois Julia connexes
									//Degrade de orange
									col=Color.getHSBColor((float)0.083, 1, Brightness);
									break;
								}
								case 4:
								{
									//Cas ou 4 Julia connexe
									col=Color.getHSBColor((float)0.0, 0,(float)Brightness);
									break;
								}
								default:
									col=Color.white;	
								break;
							}
							//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes<I.getHeight())
							{
								I.setRGB(newXaxes, newYaxes,col.getRGB());
							}
						}
					}
				}//FIN boucle en x
			}	//FIN boucle en y
		}//FIN boucle en z
		try 
    	{
    		ImageIO.write(I, "PNG", new File(chemin + progressValue + ".PNG"));
		} catch (Exception e) 
    	{
			// TODO: handle exception
		}
    	progress.setValue(progressValue++);
    	//resetImage();
    	I = new BufferedImage(I.getWidth(), I.getHeight(), BufferedImage.TYPE_INT_ARGB);

    	timeEnd = System.currentTimeMillis();
		timeEnd = timeEnd - timeStart;
		System.out.print(timeEnd/3600000 + "hrs. : ");
		timeEnd = timeEnd % 3600000;
		System.out.print(timeEnd/60000 + "min. : ");
		timeEnd = timeEnd % 60000;
		System.out.print(timeEnd/1000 + "sec.");
		System.out.println();
    	}
    }
    
    private void idempToReal(int i)
    {
    	Tricomplex cStart = new Tricomplex();
    	Tricomplex cEnd = new Tricomplex();

    	int pixX = 0, pixY = 0;
    	int progressValue = progress.getValue();
    	int zoomX = zoom[i][0], zoomY = zoom[i][1], zoomZ = zoom[i][2];
    	
    	/*double minXStart = axesPower[i][0], maxXStart = axesPower[i][1];
    	double minYStart = axesPower[i][2], maxYStart = axesPower[i][3];
    	double minZStart = axesPower[i][4], maxZStart = axesPower[i][5];*/
    	double minX = axesPower[i][0], maxX = axesPower[i][1];
    	double minY = axesPower[i][2], maxY = axesPower[i][3];
    	double minZ= axesPower[i][4], maxZ = axesPower[i][5];
    	/*double minXEnd = -0.5, maxXEnd = 0.5;
    	double minYEnd = -0.5, maxYEnd = 0.5;
    	double minZEnd = -0.5, maxZEnd = 0.5;*/
    	/*double minXEnd = axesPowerEnd[i][0], maxXEnd = axesPowerEnd[i][1];
    	double minYEnd = axesPowerEnd[i][2], maxYEnd = axesPowerEnd[i][3];
    	double minZEnd = axesPowerEnd[i][4], maxZEnd = axesPowerEnd[i][5];*/
    	
    	long timeStart, timeEnd;
    	int newXaxes, newYaxes, newZaxes;
    	double normalX, normalY, normalZ;
    	//double minX, maxX, minY, maxY, minZ, maxZ;
    	normalX = 0;
    	normalY = 0;
    	normalZ = 1;
    	
    	double t = 0;
    	double u = 0;
    	/*coupeStart = new boolean[8];
    	coupeStart[0] = coupeStart[5] = coupeStart[6] = true;
    	coupeStart[1] = coupeStart[2] = coupeStart[3] = coupeStart[4] = coupeStart[7] = false;
    	boolean[] coupeEnd = new boolean[8];
    	coupeEnd[0] = coupeEnd[1] = coupeEnd[2] = coupeEnd[3] = coupeEnd[4] = false;
    	coupeEnd[5] = coupeEnd[6] = coupeEnd[7] = true;
    	double[] componentValuesStart = new double[8];
    	double[] componentValuesEnd = new double[8];*/
    		
    	for (int j = 0; j < 8; j++)
    	{
    		componentValuesStart[j] = 0;
    		componentValuesEnd[j] = 0;
    	}
    		
    	double scalProd =  (maxZ - minZ) * factz + minZ;
    	int coupeZ = (int) (factz * dimension[2]);
    	boolean onPlane = true;
    	
    	timeStart = System.currentTimeMillis();  
    	for (int h = 0; h <= (int)totalIncrement; h++)
    	{
    		t = (double)h / totalIncrement; 
    		
    		if (h < totalIncrement/2)
    			u =  startAxesValues + 2 * (maxAxesValues - startAxesValues) * t;
    		else
    			u = (endAxesValues - 2*(endAxesValues - maxAxesValues)) + 2 * (endAxesValues - maxAxesValues) * t;
    		//System.out.print(u);
    		
    		minX = -u; maxX = u;
    		minY = -u; maxY = u;
    		minZ = -u; maxZ = u;
    		
    		zoomX = (int) (dimension[0]/(maxX - minX));
    		zoomY = (int) (dimension[1]/(maxY - minY));
    		zoomZ = (int) (dimension[2]/(maxY - minY));
    		
    		minIter = (int) (coucheMin[0] + (coucheMin[1] - coucheMin[0]) * t);
    		maxIter = (int) (coucheMax[0] + (coucheMax[1] - coucheMax[0]) * t);
    		
    		System.out.println("Couche min. : " + minIter);
    		System.out.println("Couche max. : " + maxIter);
    		
		for (int z=0; z <= coupeZ; z++)
		{
			for (int y=0; y < I.getHeight();y++)
			{
				for (int x=0;x<I.getWidth();x++)
				{
					tempsDebut=System.currentTimeMillis();
					if (stop)
					{
						z = dimension[2];
						break;
					}
					
					//On fixe les parties du nombre c
					pX=(double)x/zoomX+minX;
					pY=maxY - (double)y/zoomY;
					pZ=(double)(z)/zoomZ+minZ;
					newXaxes = x;
					newYaxes = y;
					newZaxes = z;
					//onPlane = normalPlanEval(normalX, normalY, normalZ, pX, pY, pZ, scalProd);
					
					//On fait la rotation désirée
					if (isThereRotation)
					{
						rotOp.doRotation(pX, pY, pZ);
						pX = rotOp.getEndPointX();
						pY = rotOp.getEndPointY();
						pZ = rotOp.getEndPointZ();
						/*rotOp.doRotation(x - dimension[0]/2, y - dimension[1]/2, z - dimension[2]/2);
						newXaxes = (int)rotOp.getEndPointX() + dimension[0]/2;
						newYaxes = (int)rotOp.getEndPointY() + dimension[1]/2;
						newZaxes = (int)rotOp.getEndPointZ() + dimension[2]/2;*/
					}
					/*else
					{
						newXaxes = x;
						newYaxes = y;
						newZaxes = z;
					}*/
						
					/*if (axeX.isOn(pX, pY, pZ))
						I.setRGB(x, y, Color.white.getRGB());
					if (axeY.isOn(pX, pY, pZ))
						I.setRGB(x, y, Color.white.getRGB());
					if (axeZ.isOn(pX, pY, pZ))
						I.setRGB(x, y, Color.white.getRGB());*/
					
					cStart.setComponentIdempotent(pX, pY, pZ, coupeStart, componentValuesStart);
					cEnd.setComponent(pX, pY, pZ, coupeEnd, componentValuesEnd);
					
					c = cStart.scalMult(1-t).add(cEnd.scalMult(t));
					
					
						//Brightness=(float)0.1+(float)(9*z)/(10*dimension[2]);
						//Brightness = (float)Brightness;
					Brightness = (float)(((pX * lightX + pY * lightY + pZ * lightZ)/ ( normLight * Math.sqrt(pX * pX + pY * pY + pZ * pZ))) + 1)/2;
					
					
					ck=Tribrot.check(c,p + i, modulusPower[i]);
					
					/*if(z == coupeZ)
					{
						//a la coupe
						if  (ck >= minIter && ck <= maxIter)
						{
							col = Color.getHSBColor(tabHSB2[ck], 1, (float)0.1+(float)(9*z)/(10*dimension[2]));
					
							//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes < I.getHeight())
							{
								I.setRGB(newXaxes,newYaxes,col.getRGB());
							}	
						}
					}
					else*/
					{
						// On teste pour accélérer les calculs
						if (ck >= minIter & ck <= maxIter)
						{
								//Obtenir des informations sur les ensemble de Julia rempli en decomposant le nombre c sur ces composantes
							//idempotentes bicomplexes et tricomplexes
							cX=c.getRe()+c.getHyp3()+c.getHyp1()-c.getHyp2();
							cY=c.getIm1()+c.getIm4()-c.getIm2()+c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck1=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck1[0] = 1;
								ck1[1] = MAXITER2;
							}
							
							cX=c.getRe()+c.getHyp3()-c.getHyp1()+c.getHyp2();
							cY=c.getIm1()+c.getIm4()+c.getIm2()-c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck2=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck2[0] = 1;
								ck2[1] = MAXITER2;
							}
							
							cX=c.getRe()-c.getHyp3()+c.getHyp1()+c.getHyp2();
							cY=c.getIm1()-c.getIm4()-c.getIm2()-c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck3=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck3[0] = 1;
								ck3[1] = MAXITER2;
							}
							
							cX=c.getRe()-c.getHyp3()-c.getHyp1()-c.getHyp2();
							cY=c.getIm1()-c.getIm4()+c.getIm2()+c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck4=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck4[0] = 1;
								ck4[1] = MAXITER2;
							}
							
							ckTot[0]=ck1[0]+ck2[0]+ck3[0]+ck4[0];
							ckTot[1]=(ck1[1]+ck2[1]+ck3[1]+ck4[1])/4;
							
			
							//Selon l'entier retourne, on verifie combien de Julia sont Connexe et on associe une couleur aux pixels
							//selon l'entier retourner par la methode check. A l'interieur de chaque cas (les cases), des conditionnels
							//permettent de faire le bon choix de couleur en fonction de l'entier ckTot obtenu par la methode checkMandelbrot.
							//Les couleurs sont obtenues selon un degrade en profondeur (selon l'axe z et donc selon les valeurs de z).
							
							switch (ckTot[0])
							{
								case 0:
								{
									//Cas ou Julia Cantor (au sens tricomplexe)
									col=Color.getHSBColor(tabHSB[ckTot[1]], 1, Brightness);
									break;
								}
								case 1:
								{
									//Cas d'un Julia connexes
									//Degrade de Jaune
									col=Color.getHSBColor((float)0.17, 1, Brightness);
									break;
								}
								case 2:
								{
									//Cas de deux Julia connexes
									//Degrade de rouge
									col=Color.getHSBColor((float)0.0, 1, Brightness);
									break;
								}
								case 3:
								{
									//Cas de trois Julia connexes
									//Degrade de orange
									col=Color.getHSBColor((float)0.083, 1, Brightness);
									break;
								}
								case 4:
								{
									//Cas ou 4 Julia connexe
									col=Color.getHSBColor((float)0.0, 0,(float)Brightness);
									break;
								}
								default:
									col=Color.white;	
								break;
							}
							//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes<I.getHeight())
							{
								I.setRGB(newXaxes, newYaxes,col.getRGB());
							}
						}
					}
				}//FIN boucle en x
			}	//FIN boucle en y
		}//FIN boucle en z
		try 
    	{
    		ImageIO.write(I, "PNG", new File(chemin + progressValue + ".PNG"));
		} catch (Exception e) 
    	{
			// TODO: handle exception
		}
    	progress.setValue(progressValue++);
    	I = new BufferedImage(I.getWidth(), I.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	//resetImage();

    	timeEnd = System.currentTimeMillis();
		timeEnd = timeEnd - timeStart;
		System.out.print(timeEnd/3600000 + "hrs. : ");
		timeEnd = timeEnd % 3600000;
		System.out.print(timeEnd/60000 + "min. : ");
		timeEnd = timeEnd % 60000;
		System.out.print(timeEnd/1000 + "sec.");
		System.out.println();
    	}
    }
    
    private void idempToIdemp(int i)
    {
    	Tricomplex cStart = new Tricomplex();
    	Tricomplex cEnd = new Tricomplex();

    	int pixX = 0, pixY = 0;
    	int progressValue = progress.getValue();
    	int zoomX = zoom[i][0], zoomY = zoom[i][1], zoomZ = zoom[i][2];
    	
    	/*double minXStart = axesPower[i][0], maxXStart = axesPower[i][1];
    	double minYStart = axesPower[i][2], maxYStart = axesPower[i][3];
    	double minZStart = axesPower[i][4], maxZStart = axesPower[i][5];*/
    	double minX = axesPower[i][0], maxX = axesPower[i][1];
    	double minY = axesPower[i][2], maxY = axesPower[i][3];
    	double minZ= axesPower[i][4], maxZ = axesPower[i][5];
    	/*double minXEnd = -0.5, maxXEnd = 0.5;
    	double minYEnd = -0.5, maxYEnd = 0.5;
    	double minZEnd = -0.5, maxZEnd = 0.5;*/
    	/*double minXEnd = axesPowerEnd[i][0], maxXEnd = axesPowerEnd[i][1];
    	double minYEnd = axesPowerEnd[i][2], maxYEnd = axesPowerEnd[i][3];
    	double minZEnd = axesPowerEnd[i][4], maxZEnd = axesPowerEnd[i][5];*/
    	
    	long timeStart, timeEnd;
    	int newXaxes, newYaxes, newZaxes;
    	double normalX, normalY, normalZ;
    	//double minX, maxX, minY, maxY, minZ, maxZ;
    	normalX = 0;
    	normalY = 0;
    	normalZ = 1;
    	
    	double t = 0;
    	double u = 0;
    	/*coupeStart = new boolean[8];
    	coupeStart[0] = coupeStart[5] = coupeStart[6] = true;
    	coupeStart[1] = coupeStart[2] = coupeStart[3] = coupeStart[4] = coupeStart[7] = false;
    	boolean[] coupeEnd = new boolean[8];
    	coupeEnd[0] = coupeEnd[1] = coupeEnd[2] = coupeEnd[3] = coupeEnd[4] = false;
    	coupeEnd[5] = coupeEnd[6] = coupeEnd[7] = true;
    	double[] componentValuesStart = new double[8];
    	double[] componentValuesEnd = new double[8];*/
    		
    	for (int j = 0; j < 8; j++)
    	{
    		componentValuesStart[j] = 0;
    		componentValuesEnd[j] = 0;
    	}
    		
    	double scalProd =  (maxZ - minZ) * factz + minZ;
    	int coupeZ = (int) (factz * dimension[2]);
    	boolean onPlane = true;
    	
    	timeStart = System.currentTimeMillis();  
    	for (int h = 0; h <= (int)totalIncrement; h++)
    	{
    		t = (double)h / totalIncrement; 
    		
    		if (h < totalIncrement/2)
    			u =  startAxesValues + 2 * (maxAxesValues - startAxesValues) * t;
    		else
    			u = (endAxesValues - 2*(endAxesValues - maxAxesValues)) + 2 * (endAxesValues - maxAxesValues) * t;
    		//System.out.print(u);
    		
    		minX = -u; maxX = u;
    		minY = -u; maxY = u;
    		minZ = -u; maxZ = u;
    		
    		zoomX = (int) (dimension[0]/(maxX - minX));
    		zoomY = (int) (dimension[1]/(maxY - minY));
    		zoomZ = (int) (dimension[2]/(maxY - minY));
    		
    		minIter = (int) (coucheMin[0] + (coucheMin[1] - coucheMin[0]) * t);
    		maxIter = (int) (coucheMax[0] + (coucheMax[1] - coucheMax[0]) * t);
    		
    		System.out.println("Couche min. : " + minIter);
    		System.out.println("Couche max. : " + maxIter);
    		
		for (int z=0; z <= coupeZ; z++)
		{
			for (int y=0; y < I.getHeight();y++)
			{
				for (int x=0;x<I.getWidth();x++)
				{
					tempsDebut=System.currentTimeMillis();
					if (stop)
					{
						z = dimension[2];
						break;
					}
					
					//On fixe les parties du nombre c
					pX=(double)x/zoomX+minX;
					pY=maxY - (double)y/zoomY;
					pZ=(double)(z)/zoomZ+minZ;
					newXaxes = x;
					newYaxes = y;
					newZaxes = z;
					//onPlane = normalPlanEval(normalX, normalY, normalZ, pX, pY, pZ, scalProd);
					
					//On fait la rotation désirée
					if (isThereRotation)
					{
						rotOp.doRotation(pX, pY, pZ);
						pX = rotOp.getEndPointX();
						pY = rotOp.getEndPointY();
						pZ = rotOp.getEndPointZ();
						/*rotOp.doRotation(x - dimension[0]/2, y - dimension[1]/2, z - dimension[2]/2);
						newXaxes = (int)rotOp.getEndPointX() + dimension[0]/2;
						newYaxes = (int)rotOp.getEndPointY() + dimension[1]/2;
						newZaxes = (int)rotOp.getEndPointZ() + dimension[2]/2;*/
					}
					/*else
					{
						newXaxes = x;
						newYaxes = y;
						newZaxes = z;
					}*/
						
					/*if (axeX.isOn(pX, pY, pZ))
						I.setRGB(x, y, Color.white.getRGB());
					if (axeY.isOn(pX, pY, pZ))
						I.setRGB(x, y, Color.white.getRGB());
					if (axeZ.isOn(pX, pY, pZ))
						I.setRGB(x, y, Color.white.getRGB());*/
					
					cStart.setComponentIdempotent(pX, pY, pZ, coupeStart, componentValuesStart);
					cEnd.setComponentIdempotent(pX, pY, pZ, coupeEnd, componentValuesEnd);
					
					c = cStart.scalMult(1-t).add(cEnd.scalMult(t));
					/*System.out.println("cStart: " + cStart.toStringIdemp());
					System.out.println("cEnd: " + cEnd.toStringIdemp());
					System.out.println("c: " + c.toStringIdemp());
					System.out.println("c(reals): " + c.toString());*/
					
					
						//Brightness=(float)0.1+(float)(9*z)/(10*dimension[2]);
						//Brightness = (float)Brightness;
					Brightness = (float)(((pX * lightX + pY * lightY + pZ * lightZ)/ ( normLight * Math.sqrt(pX * pX + pY * pY + pZ * pZ))) + 1)/2;
					
					
					ck=Tribrot.check(c,p + i, modulusPower[i]);
					
					/*if(z == coupeZ)
					{
						//a la coupe
						if  (ck >= minIter && ck <= maxIter)
						{
							col = Color.getHSBColor(tabHSB2[ck], 1, (float)0.1+(float)(9*z)/(10*dimension[2]));
					
							//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes < I.getHeight())
							{
								I.setRGB(newXaxes,newYaxes,col.getRGB());
							}	
						}
					}
					else*/
					{
						// On teste pour accélérer les calculs
						if (ck >= minIter & ck <= maxIter)
						{
								//Obtenir des informations sur les ensemble de Julia rempli en decomposant le nombre c sur ces composantes
							//idempotentes bicomplexes et tricomplexes
							cX=c.getRe()+c.getHyp3()+c.getHyp1()-c.getHyp2();
							cY=c.getIm1()+c.getIm4()-c.getIm2()+c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck1=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck1[0] = 1;
								ck1[1] = MAXITER2;
							}
							
							cX=c.getRe()+c.getHyp3()-c.getHyp1()+c.getHyp2();
							cY=c.getIm1()+c.getIm4()+c.getIm2()-c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck2=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck2[0] = 1;
								ck2[1] = MAXITER2;
							}
							
							cX=c.getRe()-c.getHyp3()+c.getHyp1()+c.getHyp2();
							cY=c.getIm1()-c.getIm4()-c.getIm2()-c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck3=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck3[0] = 1;
								ck3[1] = MAXITER2;
							}
							
							cX=c.getRe()-c.getHyp3()-c.getHyp1()-c.getHyp2();
							cY=c.getIm1()-c.getIm4()+c.getIm2()+c.getIm3();
							if (cX * cX + cY * cY > minModulus[i] * minModulus[i])
								ck4=Tribrot.checkMandelbrot(cX,cY,p + i,modulusPower[i]);
							else
							{
								ck4[0] = 1;
								ck4[1] = MAXITER2;
							}
							
							ckTot[0]=ck1[0]+ck2[0]+ck3[0]+ck4[0];
							ckTot[1]=(ck1[1]+ck2[1]+ck3[1]+ck4[1])/4;
							
			
							//Selon l'entier retourne, on verifie combien de Julia sont Connexe et on associe une couleur aux pixels
							//selon l'entier retourner par la methode check. A l'interieur de chaque cas (les cases), des conditionnels
							//permettent de faire le bon choix de couleur en fonction de l'entier ckTot obtenu par la methode checkMandelbrot.
							//Les couleurs sont obtenues selon un degrade en profondeur (selon l'axe z et donc selon les valeurs de z).
							
							switch (ckTot[0])
							{
								case 0:
								{
									//Cas ou Julia Cantor (au sens tricomplexe)
									col=Color.getHSBColor(tabHSB[ckTot[1]], 1, Brightness);
									break;
								}
								case 1:
								{
									//Cas d'un Julia connexes
									//Degrade de Jaune
									col=Color.getHSBColor((float)0.17, 1, Brightness);
									break;
								}
								case 2:
								{
									//Cas de deux Julia connexes
									//Degrade de rouge
									col=Color.getHSBColor((float)0.0, 1, Brightness);
									break;
								}
								case 3:
								{
									//Cas de trois Julia connexes
									//Degrade de orange
									col=Color.getHSBColor((float)0.083, 1, Brightness);
									break;
								}
								case 4:
								{
									//Cas ou 4 Julia connexe
									col=Color.getHSBColor((float)0.0, 0,(float)Brightness);
									break;
								}
								default:
									col=Color.white;	
								break;
							}
							//On color le pixel selon un deplacement de z/3 unite sur l'axe des x
							if (newXaxes>=0 && newXaxes<I.getWidth() && newYaxes>=0 && newYaxes<I.getHeight())
							{
								I.setRGB(newXaxes, newYaxes,col.getRGB());
							}
						}
					}
				}//FIN boucle en x
			}	//FIN boucle en y
		}//FIN boucle en z
		try 
    	{
    		ImageIO.write(I, "PNG", new File(chemin + progressValue + ".PNG"));
		} catch (Exception e) 
    	{
			// TODO: handle exception
		}
    	progress.setValue(progressValue++);
    	//resetImage();
    	I = new BufferedImage(I.getWidth(), I.getHeight(), BufferedImage.TYPE_INT_ARGB);

    	timeEnd = System.currentTimeMillis();
		timeEnd = timeEnd - timeStart;
		System.out.print(timeEnd/3600000 + "hrs. : ");
		timeEnd = timeEnd % 3600000;
		System.out.print(timeEnd/60000 + "min. : ");
		timeEnd = timeEnd % 60000;
		System.out.print(timeEnd/1000 + "sec.");
		System.out.println();
    	}
    	
    }
    
	/*private void resetImage()
	{
		// Create a buffered image with transparency
	    I = new BufferedImage(499, 499, BufferedImage.TYPE_INT_RGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = I.createGraphics();
	    bGr.drawImage(baseI, 0, 0, null);
	    bGr.dispose();
	}*/
}