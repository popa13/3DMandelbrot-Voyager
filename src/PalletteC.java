// Auteur: Pierre-Olivier Parise
// CouleurC.java

//*************************************************************
//****													   ****
//***	Role:Classe permettant de creer un tableau de  		***
//***		differentes couleur selon le standard RGB  		***
//***		ou HSB (HUE SATURATION BRIGHTNESS)				***
//****													   ****
//*************************************************************

//PAckage utilise
import java.awt.*;

public class PalletteC 
{
	float[] tabCouleurHSB;
	int[][] tabCouleurRGB;
	int[][] tabCouleurRGB2;
	float[] tabCouleurHSB2;
	float[] tabCouleurHSB3;
	
	//constructeur
	public PalletteC(int rowHSB)
	{
		tabCouleurHSB=new float[rowHSB+1];
		tabCouleurHSB2=new float[rowHSB+1];
		tabCouleurHSB3=new float[rowHSB+1];
	}
	
	public PalletteC(int rowRGB, int columnRGB)
	{
		tabCouleurRGB=new int[rowRGB+1][columnRGB];
		tabCouleurRGB2=new int[rowRGB+1][columnRGB];
	}
	
	public PalletteC(int rowHSB,int rowRGB, int columnRGB)
	{
		tabCouleurHSB=new float[rowHSB+1];
		tabCouleurHSB2=new float[rowHSB+1];
		tabCouleurHSB3=new float[rowHSB+1];
		tabCouleurRGB=new int[rowRGB+1][columnRGB];
		tabCouleurRGB2=new int[rowRGB+1][columnRGB];
	}
	
	//Methode pour initialiser les palettes de couleur HSB
	public void setHSB()
	{
		for (int i=0;i<tabCouleurHSB.length;i++)
        {
        	//Remplissage du tabelau 3 tabCouleurHSB3
			if (i<10)
        		tabCouleurHSB3[i]=(float)(0.9-i*0.065);
    		else if (i-10*(i/10)==1)
    			tabCouleurHSB3[i]=(tabCouleurHSB3[i-10]
    				+tabCouleurHSB3[i-9])/2;
    		else if (i-10*(i/10)==2)
    			tabCouleurHSB3[i]=(tabCouleurHSB3[i-10]+
    				tabCouleurHSB3[i-9])/2;
    		else if (i-10*(i/10)==3)
    			tabCouleurHSB3[i]=(tabCouleurHSB3[i-10]+
    				tabCouleurHSB3[i-9])/2;
    		else if (i-10*(i/10)==4)
    			tabCouleurHSB3[i]=(tabCouleurHSB3[i-10]+
    				tabCouleurHSB3[i-9])/2;
    		else if (i-10*(i/10)==5)
    			tabCouleurHSB3[i]=(tabCouleurHSB3[i-10]+
    				tabCouleurHSB3[i-9])/2;
    		else if (i-10*(i/10)==6)
    			tabCouleurHSB3[i]=(tabCouleurHSB3[i-10]+
    				tabCouleurHSB3[i-9])/2;
    		else if (i-10*(i/10)==7)
    			tabCouleurHSB3[i]=(tabCouleurHSB3[i-10]+
    				tabCouleurHSB3[i-9])/2;
    		else if (i-10*(i/10)==8)
    			tabCouleurHSB3[i]=(tabCouleurHSB3[i-10]+
    				tabCouleurHSB3[i-9])/2;
    		else
    			tabCouleurHSB3[i]=(tabCouleurHSB3[i-10]+
    				tabCouleurHSB3[i-9])/2;
			
			//Remplissage du tableau tabCouleurHSB
			if (i-200*(i/200)<=25)
        		tabCouleurHSB[i]=(float)(0.9-i*0.0284);
        	else if (i-200*(i/200)>25 && i-200*(i/200)<=50)
        		tabCouleurHSB[i]=(tabCouleurHSB[i-26]+
        			tabCouleurHSB[i-25])/2;
        	else if (i-200*(i/200)>50 && i-200*(i/200)<=75)
        		tabCouleurHSB[i]=(tabCouleurHSB[i-51]+
        			tabCouleurHSB[i-25])/2;
        	else if (i-200*(i/200)>75 && i-200*(i/200)<=100)
        		tabCouleurHSB[i]=(tabCouleurHSB[i-75]+
        			tabCouleurHSB[i-50])/2;
        	else if (i-200*(i/200)>100 && i-200*(i/200)<=125)
        		tabCouleurHSB[i]=(tabCouleurHSB[i-50]+
        			tabCouleurHSB[i-101])/2;
        	else if (i-200*(i/200)>125 && i-200*(i/200)<=150)
        		tabCouleurHSB[i]=(tabCouleurHSB[i-100]+
        			tabCouleurHSB[i-50])/2;
        	else if (i-200*(i/200)>150 && i-200*(i/200)<=175)
        		tabCouleurHSB[i]=(tabCouleurHSB[i-125]+
        			tabCouleurHSB[i-75])/2;
        	else
        		tabCouleurHSB[i]=(tabCouleurHSB[i-175]+
        			tabCouleurHSB[i-75])/2; 
			
			// Remplissage du tableau tabCouleurHSB2
        	if (i<10)
        	{
        		tabCouleurHSB2[i]=(float)(1.0-i*0.1);
        	}
        	else
        	{
        		if (i-10*(i/10)==0)
        			tabCouleurHSB2[i]=(tabCouleurHSB2[i-10]
        				+tabCouleurHSB[i-9])/2;
        		else if (i-10*(i/10)==1)
        			tabCouleurHSB2[i]=(tabCouleurHSB2[i-10]
        				+tabCouleurHSB2[i-9])/2;
        		else if (i-10*(i/10)==2)
        			tabCouleurHSB2[i]=(tabCouleurHSB2[i-10]+
        				tabCouleurHSB2[i-9])/2;
        		else if (i-10*(i/10)==3)
        			tabCouleurHSB2[i]=(tabCouleurHSB2[i-10]+
        				tabCouleurHSB2[i-9])/2;
        		else if (i-10*(i/10)==4)
        			tabCouleurHSB2[i]=(tabCouleurHSB2[i-10]+
        				tabCouleurHSB2[i-9])/2;
        		else if (i-10*(i/10)==5)
        			tabCouleurHSB2[i]=(tabCouleurHSB2[i-10]+
        				tabCouleurHSB2[i-9])/2;
        		else if (i-10*(i/10)==6)
        			tabCouleurHSB2[i]=(tabCouleurHSB2[i-10]+
        				tabCouleurHSB2[i-9])/2;
        		else if (i-10*(i/10)==7)
        			tabCouleurHSB2[i]=(tabCouleurHSB2[i-10]+
        				tabCouleurHSB2[i-9])/2;
        		else if (i-10*(i/10)==8)
        			tabCouleurHSB2[i]=(tabCouleurHSB2[i-10]+
        				tabCouleurHSB2[i-9])/2;
        		else
        			tabCouleurHSB2[i]=(tabCouleurHSB2[i-10]+
        				tabCouleurHSB2[i-9])/2;
        	}
			
        }
	}
	
	//Methode pour initialiser la palette de couleur RGB
	public void setRGB()
	{
		for (int i=0; i<tabCouleurRGB.length-1;i++)
        {
        	if (i-100*(i/100)<10)
        	{
        		tabCouleurRGB[i][0]=153+(i-100*(i/100))*10;
        		tabCouleurRGB[i][1]=0;
        		tabCouleurRGB[i][2]=0;
        	}
        	else if (i-100*(i/100)>=10 & i-100*(i/100)<20)
        	{
        		tabCouleurRGB[i][0]=153+(i-100*(i/100))*10-100;
        		tabCouleurRGB[i][1]=76+(i-100*(i/100))*5-50;
        		tabCouleurRGB[i][2]=0;
        	}
        	else if (i-100*(i/100)>=20 & i-100*(i/100)<30)
        	{
        		tabCouleurRGB[i][0]=153+(i-100*(i/100))*10-200;
        		tabCouleurRGB[i][1]=153+(i-100*(i/100))*10-200;
        		tabCouleurRGB[i][2]=0;
        	}
        	else if (i-100*(i/100)>=30 & i-100*(i/100)<40)
        	{
        		tabCouleurRGB[i][0]=76+(i-100*(i/100))*5-150;
        		tabCouleurRGB[i][1]=153+(i-100*(i/100))*10-300;
        		tabCouleurRGB[i][2]=0;
        	}
        	else if (i-100*(i/100)>=40 & i-100*(i/100)<50)
        	{
        		tabCouleurRGB[i][0]=0;
        		tabCouleurRGB[i][1]=153+(i-100*(i/100))*10-400;
        		tabCouleurRGB[i][2]=0;
        	}
        	else if (i-100*(i/100)>=50 & i-100*(i/100)<60)
        	{
        		tabCouleurRGB[i][0]=153+(i-100*(i/100))*10-500;
        		tabCouleurRGB[i][1]=0;
        		tabCouleurRGB[i][2]=153+(i-100*(i/100))*10-500;
        	}
        	else if (i-100*(i/100)>=60 & i-100*(i/100)<70)
        	{
        		tabCouleurRGB[i][0]=0;
        		tabCouleurRGB[i][1]=153+(i-100*(i/100))*10-600;
        		tabCouleurRGB[i][2]=76+(i-100*(i/100))*5-300;
        	}
        	else if (i-100*(i/100)>=70 & i-100*(i/100)<80)
        	{
        		tabCouleurRGB[i][0]=0;
        		tabCouleurRGB[i][1]=153+(i-100*(i/100))*10-700;
        		tabCouleurRGB[i][2]=153+(i-100*(i/100))*10-700;
        	}
        	else if (i-100*(i/100)>=80 & i-100*(i/100)<90)
        	{
        		tabCouleurRGB[i][0]=0;
        		tabCouleurRGB[i][1]=76+((i-100*(i/100))*10)/2-400;
        		tabCouleurRGB[i][2]=153+(i-100*(i/100))*10-800;
        	}
        	else if (i-100*(i/100)>=90 & i-100*(i/100)<99)
        	{
        		tabCouleurRGB[i][0]=0;
        		tabCouleurRGB[i][1]=0;
        		tabCouleurRGB[i][2]=153+(i-100*(i/100))*10-900;
        	}
        	else
	        {
	        	
	        }
        }
		tabCouleurRGB[tabCouleurRGB.length-1][0]=0;
		tabCouleurRGB[tabCouleurRGB.length-1][1]=201;
		tabCouleurRGB[tabCouleurRGB.length-1][2]=36;
	}
	
	//Methode pour initialiser la palette de couleur RGB2
		public void setRGB2()
		{
			for (int i=0; i<tabCouleurRGB.length-1;i++)
	        {
	        	if (i-100*(i/100)<10)
	        	{
	        		tabCouleurRGB[i][0]=10-(i-100*(i/100));
	        		tabCouleurRGB[i][1]=10-(i-100*(i/100));
	        		tabCouleurRGB[i][2]=10-(i-100*(i/100));
	        	}
	        	else if (i-100*(i/100)>=10 & i-100*(i/100)<20)
	        	{
	        		tabCouleurRGB[i][0]=30+20-(i-100*(i/100));
	        		tabCouleurRGB[i][1]=30+20-(i-100*(i/100));
	        		tabCouleurRGB[i][2]=30+20-(i-100*(i/100));
	        	}
	        	else if (i-100*(i/100)>=20 & i-100*(i/100)<30)
	        	{
	        		tabCouleurRGB[i][0]=40+30-(i-100*(i/100));
	        		tabCouleurRGB[i][1]=40+30-(i-100*(i/100));
	        		tabCouleurRGB[i][2]=40+30-(i-100*(i/100));
	        	}
	        	else if (i-100*(i/100)>=30 & i-100*(i/100)<40)
	        	{
	        		tabCouleurRGB[i][0]=70+40-(i-100*(i/100));
	        		tabCouleurRGB[i][1]=70+40-(i-100*(i/100));
	        		tabCouleurRGB[i][2]=70+40-(i-100*(i/100));
	        	}
	        	else if (i-100*(i/100)>=40 & i-100*(i/100)<50)
	        	{
	        		tabCouleurRGB[i][0]=90+50-(i-100*(i/100));
	        		tabCouleurRGB[i][1]=90+50-(i-100*(i/100));
	        		tabCouleurRGB[i][2]=90+50-(i-100*(i/100));
	        	}
	        	else if (i-100*(i/100)>=50 & i-100*(i/100)<60)
	        	{
	        		tabCouleurRGB[i][0]=110+60-(i-100*(i/100));
	        		tabCouleurRGB[i][1]=110+60-(i-100*(i/100));
	        		tabCouleurRGB[i][2]=110+60-(i-100*(i/100));
	        	}
	        	else if (i-100*(i/100)>=60 & i-100*(i/100)<70)
	        	{
	        		tabCouleurRGB[i][0]=130+70-(i-100*(i/100));
	        		tabCouleurRGB[i][1]=130+70-(i-100*(i/100));
	        		tabCouleurRGB[i][2]=130+70-(i-100*(i/100));
	        	}
	        	else if (i-100*(i/100)>=70 & i-100*(i/100)<80)
	        	{
	        		tabCouleurRGB[i][0]=150+80-(i-100*(i/100));
	        		tabCouleurRGB[i][1]=150+80-(i-100*(i/100));
	        		tabCouleurRGB[i][2]=150+80-(i-100*(i/100));
	        	}
	        	else if (i-100*(i/100)>=80 & i-100*(i/100)<90)
	        	{
	        		tabCouleurRGB[i][0]=170+90-(i-100*(i/100));
	        		tabCouleurRGB[i][1]=170+90-(i-100*(i/100));
	        		tabCouleurRGB[i][2]=170+90-(i-100*(i/100));
	        	}
	        	else if (i-100*(i/100)>=90 & i-100*(i/100)<99)
	        	{
	        		tabCouleurRGB[i][0]=190+100-(i-100*(i/100));
	        		tabCouleurRGB[i][1]=190+100-(i-100*(i/100));
	        		tabCouleurRGB[i][2]=190+100-(i-100*(i/100));
	        	}
	        	else
		        {
		        	
		        }
	        }
			tabCouleurRGB[tabCouleurRGB.length-1][0]=0;
			tabCouleurRGB[tabCouleurRGB.length-1][1]=201;
			tabCouleurRGB[tabCouleurRGB.length-1][2]=36;
		}
	
	//Getter pour le tableau tabCouleurHSB
	public float[] getPalHSB()
	{
		return this.tabCouleurHSB;
	}
	
	//Getteur pour le tableau tabCouleurHSB2
	public float[] getPalHSB2()
	{
		return this.tabCouleurHSB2;
	}
	
	//Getteur pour le tableau tabCouleurRGB
	public int[][] getPalRGB()
	{
		return this.tabCouleurRGB;
	}
	
	//Getteur pour le tableau tabCouleurRGB2
		public int[][] getPalRGB2()
		{
			return this.tabCouleurRGB2;
		}
	
	//La methode toString: elle met en chaine de caracteres
	//les numeros associe à chaque ligne des tableaux
	public String toStringHSB()
	{
		String result;
		result="Couleur 1 = " + tabCouleurHSB[0];
		for (int i=1;i<tabCouleurHSB.length;i++)
		{
			result=result+"Couleur " + (i+1) + "= " +
				tabCouleurHSB[i] + "\t";
			if (i%10==0)
				result=result+"\n";
		}
		return result;
	}
	
	public String toStringRGB()
	{
		String result;
		result="Couleur 1 = " + tabCouleurRGB[0];
		for (int i=1;i<tabCouleurRGB.length;i++)
		{
			result=result+"Couleur " + (i+1) + "= " +
				tabCouleurRGB[i] + "\t";
			if (i%10==0)
				result=result+"\n";
		}
		return result;
	}

}

