//---------------------------------------------------------- 																															   ***
//	Auteur: Pierre-Olivier Parise							
//	Role: Classe permettant de modéliser un nombre
//	Tricomplexe avec ces composantes reelle, im. et hyp.	
// 	Tricomplex.java										    
//-----------------------------------------------------------

//a completer: le toString et la multiplication de deux nombre 
//tricomplexe qui donne un nouveau nombre tricomplexe


public class Tricomplex
{
	//		****************************************
	//		**				  				      **
	//		**  	Variables d'Instances: 	      **
	//		**		8 nombres reels			      **
	//		**				  				      **
	//		****************************************
	protected double re,im1,im2,im3,im4,hyp1,hyp2,hyp3;
	private Complex z1, z2, z3, z4;
	
	//		**********************
	//		**			  	    **
	//		**   Contructeurs   **
	//		**			  	    **
	//		**********************
	public Tricomplex()
	{
		this.re=0;
		this.im1=0;
		this.im2=0;
		this.im3=0;
		this.im4=0;
		this.hyp1=0;
		this.hyp2=0;
		this.hyp3=0;
		this.z1 = new Complex();
		this.z2 = new Complex();
		this.z3 = new Complex();
		this.z4 = new Complex();
	}
	
	public Tricomplex(double re,double im1, double im2, double im3, 
			double im4, double hyp1, double hyp2,double hyp3)
	{
		this.re=re;
		this.im1=im1;
		this.im2=im2;
		this.im3=im3;
		this.im4=im4;
		this.hyp1=hyp1;
		this.hyp2=hyp2;
		this.hyp3=hyp3;
		this.z1 = new Complex();
		this.z2 = new Complex();
		this.z3 = new Complex();
		this.z4 = new Complex();
		setIdempotent();
	}
	
	Tricomplex (Complex e1, Complex e2, Complex e3, Complex e4)
	{
		z1 = new Complex(e1.getRe(), e1.getIm());
		z2 = new Complex(e2.getRe(), e2.getIm());
		z3 = new Complex(e3.getRe(), e3.getIm());
		z4 = new Complex(e4.getRe(), e4.getIm());
		
		setReals();
	}
	
	/*
	public Tricomplex(double[] tab)
	{
		for (int i=0;i<zeta.length;i++)
			zeta[i]=tab[i];
	}
	*/
	
	//			******************
	//			**			  	**
	//			**   Getters	**
	//			**			  	**
	//			******************
	
	//partie Reelle
	public double getRe()
	{
		return re;
	}
	
	//partie Imaginairei1
	public double getIm1()
	{
		return im1;
	}
	
	//partie Imaginairei2
	public double getIm2()
	{
		return im2;
	}
	
	//partie Imaginairei3
	public double getIm3()
	{
		return im3;
	}
	
	//partie Imaginairei4
	public double getIm4()
	{
		return im4;
	}
	
	//partie hyperboliquej1
	public double getHyp1()
	{
		return hyp1;
	}
	
	//partie hyperboliquej2
	public double getHyp2()
	{
		return hyp2;
	}
	
	//partie hyperboliquej3
	public double getHyp3()
	{
		return hyp3;
	}
	
	// Première composante idempotente
	public Complex getE1()
	{
		Complex z = new Complex(z1.getRe(), z1.getIm());
		return z;
	}
	
	public Complex getE2()
	{
		Complex z = new Complex(z2.getRe(), z2.getIm());
		return z;
	}
	
	public Complex getE3()
	{
		Complex z = new Complex(z3.getRe(), z3.getIm());
		return z;
	}
	
	public Complex getE4()
	{
		Complex z = new Complex(z4.getRe(), z4.getIm());
		return z;
	}
	
	//tout le nombre tricomplexe par son tableau (vecteur)
	//public double[] getAll()
	//{
	//	return zeta;
	//}
	
	//		******************
	//		**			  	**
	//		**   Setters	**
	//		**			  	**
	//		******************
	
	//changer partie reelle
	public void setRe(double re)
	{
		this.re=re;
	}
	
	//changer partie imaginairei1
	public void setIm1(double im1)
	{
		this.im1=im1;
	}
	
	//changer partie imaginairei2
	public void setIm2(double im2)
	{
		this.im2=im2;
	}
	
	//changer partie imaginairei3
	public void setIm3(double im3)
	{
		this.im3=im3;
	}
	
	//changer partie imaginairei4
	public void setIm4(double im4)
	{
		this.im4=im4;
	}
	
	//Changer la partie hyperboliquej1
	public void setHyp1(double hyp1)
	{
		this.hyp1=hyp1;
	}
	
	//Changer la partie hyperboliquej2
	public void setHyp2(double hyp2)
	{
		this.hyp2=hyp2;
	}
	
	//Changer la partie hyperboliquej3
	public void setHyp3(double hyp3)
	{
		this.hyp3=hyp3;
	}
	
	// Initialiser la décomposition sur les quatres éléments idempotents
	public void setIdempotent()
	{
		double cX, cY;
		cX=this.getRe()+this.getHyp3()+this.getHyp1()-this.getHyp2();
		cY=this.getIm1()+this.getIm4()-this.getIm2()+this.getIm3();
		z1.setReIm(cX, cY);
		
		cX=this.getRe()+this.getHyp3()-this.getHyp1()+this.getHyp2();
		cY=this.getIm1()+this.getIm4()+this.getIm2()-this.getIm3();
		z2.setReIm(cX, cY);
		
		cX=this.getRe()-this.getHyp3()+this.getHyp1()+this.getHyp2();
		cY=this.getIm1()-this.getIm4()-this.getIm2()-this.getIm3();
		z3.setReIm(cX, cY);
		
		cX=this.getRe()-this.getHyp3()-this.getHyp1()-this.getHyp2();
		cY=this.getIm1()-this.getIm4()+this.getIm2()+this.getIm3();
		z4.setReIm(cX, cY);
	}
	
	public void setReals()
	{
		this.re = (z1.getRe() + z2.getRe() + z3.getRe() + z4.getRe())/4;
		this.im1 = (z1.getIm() + z2.getIm() + z3.getIm() + z4.getIm())/4;
		this.hyp1 = (z1.getRe() - z2.getRe() + z3.getRe() - z4.getRe())/4;
		this.im2 = (-z1.getIm() + z2.getIm() - z2.getIm() + z2.getIm())/4;
		this.hyp2 = (-z1.getRe() + z2.getRe() + z3.getRe() - z4.getRe())/4;
		this.im3 = (z1.getIm() - z2.getIm() - z3.getIm() + z4.getIm())/4;
		this.hyp3 = (z1.getRe() + z2.getRe() - z3.getRe() - z4.getRe())/4;
		this.im4 = (z1.getIm() + z2.getIm() - z3.getIm() - z4.getIm())/4;
	}
	
	//Changer toutes les composantes en meme temps
	public void setAll(double re,double im1, double im2, double im3
		, double im4, double hyp1, double hyp2,double hyp3)
	{
		this.re=re;
		this.im1=im1;
		this.im2=im2;
		this.im3=im3;
		this.im4=im4;
		this.hyp1=hyp1;
		this.hyp2=hyp2;
		this.hyp3=hyp3;
	}
	
	// Fixer les composants à une coupe spécifique d'un Julia (voir classe Julia pour l'utilisation des cette méthode
	public void setComponentJulia(double pX, double pY, double pZ, boolean[] coupe)
	{
		int count = 0;
		
		//PArtie réelle
		if (count < 3 && coupe[0])
		{
			switch(count)
			{
				case 0:
					this.setRe(pX);
					break;
				case 1:
					this.setRe(pY);
					break;
				case 2:
					this.setRe(pZ);
					break;
			}
			count++;
		}
			
		//PArtie imaginaire i1
		if (count < 3 && coupe[1])
		{
			switch(count)
			{
				case 0:
					this.setIm1(pX);
					break;
				case 1:
					this.setIm1(pY);
					break;
				case 2:
					this.setIm1(pZ);
					break;
			}
			count++;
		}
		
		//Partie imaginaire i2
		if (count < 3 && coupe[2])
		{
			switch(count)
			{
				case 0:
					this.setIm2(pX);
					break;
				case 1:
					this.setIm2(pY);
					break;
				case 2:
					this.setIm2(pZ);
					break;
			}
			count++;
		}
		
		//Partie imaginaire i3
		if (count < 3 && coupe[3])
		{
			switch(count)
			{
				case 0:
					this.setIm3(pX);
					break;
				case 1:
					this.setIm3(pY);
					break;
				case 2:
					this.setIm3(pZ);
					break;
			}
			count++;
		}
		
		//PArtie imaginaire i4
		if (count < 3 && coupe[4])
		{
			switch(count)
			{
				case 0:
					this.setIm4(pX);
					break;
				case 1:
					this.setIm4(pY);
					break;
				case 2:
					this.setIm4(pZ);
					break;
			}
			count++;
		}
		
		//PArtie hyperbolique j1
		if (count < 3 && coupe[5])
		{
			switch(count)
			{
				case 0:
					this.setHyp1(pX);
					break;
				case 1:
					this.setHyp1(pY);
					break;
				case 2:
					this.setHyp1(pZ);
					break;
			}
			count++;
		}
		
		//PArtie hyperbolique j2
		if (count < 3 && coupe[6])
		{
			switch(count)
			{
				case 0:
					this.setHyp2(pX);
					break;
				case 1:
					this.setHyp2(pY);
					break;
				case 2:
					this.setHyp2(pZ);
					break;
			}
			count++;
		}
		
		//Partie hyperbolique j3
		if (count < 3 && coupe[7])
		{
			switch(count)
			{
				case 0:
					this.setHyp3(pX);
					break;
				case 1:
					this.setHyp3(pY);
					break;
				case 2:
					this.setHyp3(pZ);
					break;
			}
			count++;
		}
		this.setIdempotent();
	}
	
	//Changer certain composants selon un tableau boolean (voir classe Tribrot)
	public void setComponent(double pX, double pY, double pZ, boolean[] coupe,
			double[] componentValue)
	{
		int count = 0;
		
		//PArtie réelle
		if (count < 3 && coupe[0])
		{
			switch(count)
			{
				case 0:
					this.setRe(pX);
					break;
				case 1:
					this.setRe(pY);
					break;
				case 2:
					this.setRe(pZ);
					break;
			}
			count++;
		}
		else
			this.setRe(componentValue[0]);
		
		//PArtie imaginaire i1
		if (count < 3 && coupe[1])
		{
			switch(count)
			{
				case 0:
					this.setIm1(pX);
					break;
				case 1:
					this.setIm1(pY);
					break;
				case 2:
					this.setIm1(pZ);
					break;
			}
			count++;
		}
		else
			this.setIm1(componentValue[1]);
		
		//Partie imaginaire i2
		if (count < 3 && coupe[2])
		{
			switch(count)
			{
				case 0:
					this.setIm2(pX);
					break;
				case 1:
					this.setIm2(pY);
					break;
				case 2:
					this.setIm2(pZ);
					break;
			}
			count++;
		}
		else
			this.setIm2(componentValue[2]);
		
		//Partie imaginaire i3
		if (count < 3 && coupe[3])
		{
			switch(count)
			{
				case 0:
					this.setIm3(pX);
					break;
				case 1:
					this.setIm3(pY);
					break;
				case 2:
					this.setIm3(pZ);
					break;
			}
			count++;
		}
		else
			this.setIm3(componentValue[3]);
		
		//PArtie imaginaire i4
		if (count < 3 && coupe[4])
		{
			switch(count)
			{
				case 0:
					this.setIm4(pX);
					break;
				case 1:
					this.setIm4(pY);
					break;
				case 2:
					this.setIm4(pZ);
					break;
			}
			count++;
		}
		else
			this.setIm4(componentValue[4]);
		
		//PArtie hyperbolique j1
		if (count < 3 && coupe[5])
		{
			switch(count)
			{
				case 0:
					this.setHyp1(pX);
					break;
				case 1:
					this.setHyp1(pY);
					break;
				case 2:
					this.setHyp1(pZ);
					break;
			}
			count++;
		}
		else
			this.setHyp1(componentValue[5]);
		
		//PArtie hyperbolique j2
		if (count < 3 && coupe[6])
		{
			switch(count)
			{
				case 0:
					this.setHyp2(pX);
					break;
				case 1:
					this.setHyp2(pY);
					break;
				case 2:
					this.setHyp2(pZ);
					break;
			}
			count++;
		}
		else
			this.setHyp2(componentValue[6]);
		
		//Partie hyperbolique j3
		if (count < 3 && coupe[7])
		{
			switch(count)
			{
				case 0:
					this.setHyp3(pX);
					break;
				case 1:
					this.setHyp3(pY);
					break;
				case 2:
					this.setHyp3(pZ);
					break;
			}
			count++;
		}
		else
			this.setHyp3(componentValue[7]);
		
		this.setIdempotent();
	}
	
	public void setComponentIdempotent(double pX, double pY, double pZ, boolean[] coupe,
			double[] componentValue)
	{
		int count = 0;
		
		//PArtie réelle
		if (count < 3 && coupe[0])
		{
			switch(count)
			{
				case 0:
					this.z1.setRe(pX);
					break;
				case 1:
					this.z1.setRe(pY);
					break;
				case 2:
					this.z1.setRe(pZ);
					break;
			}
			count++;
		}
		else
			this.z1.setRe(componentValue[0]);
		
		//PArtie imaginaire i1
		if (count < 3 && coupe[1])
		{
			switch(count)
			{
				case 0:
					this.z1.setIm(pX);
					break;
				case 1:
					this.z1.setIm(pY);
					break;
				case 2:
					this.z1.setIm(pZ);
					break;
			}
			count++;
		}
		else
			this.z1.setIm(componentValue[1]);
		
		//Partie imaginaire i2
		if (count < 3 && coupe[2])
		{
			switch(count)
			{
				case 0:
					this.z2.setRe(pX);
					break;
				case 1:
					this.z2.setRe(pY);
					break;
				case 2:
					this.z2.setRe(pZ);
					break;
			}
			count++;
		}
		else
			this.z2.setRe(componentValue[2]);
		
		//Partie imaginaire i3
		if (count < 3 && coupe[3])
		{
			switch(count)
			{
				case 0:
					this.z2.setIm(pX);
					break;
				case 1:
					this.z2.setIm(pY);
					break;
				case 2:
					this.z2.setIm(pZ);
					break;
			}
			count++;
		}
		else
			this.z2.setIm(componentValue[3]);
		
		//PArtie imaginaire i4
		if (count < 3 && coupe[4])
		{
			switch(count)
			{
				case 0:
					this.z3.setRe(pX);
					break;
				case 1:
					this.z3.setRe(pY);
					break;
				case 2:
					this.z3.setRe(pZ);
					break;
			}
			count++;
		}
		else
			this.z3.setRe(componentValue[4]);
		
		//PArtie hyperbolique j1
		if (count < 3 && coupe[5])
		{
			switch(count)
			{
				case 0:
					this.z3.setIm(pX);
					break;
				case 1:
					this.z3.setIm(pY);
					break;
				case 2:
					this.z3.setIm(pZ);
					break;
			}
			count++;
		}
		else
			this.z3.setIm(componentValue[5]);
		
		//PArtie hyperbolique j2
		if (count < 3 && coupe[6])
		{
			switch(count)
			{
				case 0:
					this.z4.setRe(pX);
					break;
				case 1:
					this.z4.setRe(pY);
					break;
				case 2:
					this.z4.setRe(pZ);
					break;
			}
			count++;
		}
		else
			this.z4.setRe(componentValue[6]);
		
		//Partie hyperbolique j3
		if (count < 3 && coupe[7])
		{
			switch(count)
			{
				case 0:
					this.z4.setIm(pX);
					break;
				case 1:
					this.z4.setIm(pY);
					break;
				case 2:
					this.z4.setIm(pZ);
					break;
			}
			count++;
		}
		else
			this.z4.setIm(componentValue[7]);
		
		this.setReals();
	}
	
	// Changer toutes les composantes par un tableau
	//public void setAllByTab(double[] tab)
	//{
	//	for (int i=0;i<zeta.length;i++)
	//		zeta[i]=tab[i];
	//}
	
	//		*******************
	//		**				 **
	//		**  Operations   **
	//		**			  	 **
	//		*******************
	
	//**************
	//  ADDITION
	//**************
	
	//Addition de deux tricomplexes (qui en sortie fournie un 
	//nombres tricomplexe)
	public Tricomplex add(Tricomplex z)
	{
		double pRe,pIm,pIm2,pIm3,pIm4,
			pHyp1,pHyp2,pHyp3;
		z.setIdempotent();
		pRe=re+z.getRe();
		pIm=im1+z.getIm1();
		pIm2=im2+z.getIm2();
		pIm3=im3+z.getIm3();
		pIm4=im4+z.getIm4();
		pHyp1=hyp1+z.getHyp1();
		pHyp2=hyp2+z.getHyp2();
		pHyp3=hyp3+z.getHyp3();
		return new Tricomplex(pRe,pIm,pIm2,pIm3,
			pIm4,pHyp1,pHyp2,pHyp3);
			
	}
	
	public Tricomplex addIdemp(Tricomplex z)
	{
		return new Tricomplex(z1.add(z.getE1()), z2.add(z.getE2()), z3.add(z.getE4()), z4.add(z.getE4()));
	}
		
	//Addition de deux tricomplexes (Change le tricomplexe 
	//sur lequel est effectue l'operation)
	public void plus(Tricomplex z)
	{
		double pRe,pIm1,pIm2,pIm3,pIm4,
			pHyp1,pHyp2,pHyp3;
		pRe=re+z.getRe();
		pIm1=im1+z.getIm1();
		pIm2=im2+z.getIm2();
		pIm3=im3+z.getIm3();
		pIm4=im4+z.getIm4();
		pHyp1=hyp1+z.getHyp1();
		pHyp2=hyp2+z.getHyp2();
		pHyp3=hyp3+z.getHyp3();
		
		this.setAll(pRe,pIm1,pIm2,pIm3,
			pIm4,pHyp1,pHyp2,pHyp3);
		this.setIdempotent();
	}
		
	//*******************
	//  MULTIPLICATION
	//*******************
		
	//Multiplier par un scalaire k
	public Tricomplex scalMult(double k)
	{
		double pRe,pIm1,pIm2,pIm3,pIm4,
			pHyp1,pHyp2,pHyp3;
		pRe = k * this.re;
		pIm1 = k * this.im1;
		pIm2 = k * this.im2; 
		pIm3 = k * this.im3; 
		pIm4 = k * this.im4;
		pHyp1 = k * this.hyp1;
		pHyp2 = k * this.hyp2;
		pHyp3 = k * this.hyp3; 
		Tricomplex result=new Tricomplex(pRe,pIm1,pIm2,pIm3,
				pIm4,pHyp1,pHyp2,pHyp3);
		
		return result;
	}
	
	public Tricomplex scalMultIdemp(double k)
	{
		return new Tricomplex(z1.scalarMult(k), z2.scalarMult(k), z3.scalarMult(k), z4.scalarMult(k));
	}
	
	//Multiplier les nombres tricomplexes (donne un nombre tricomplexe)
	public Tricomplex mult(Tricomplex z)
	{
		//double pRe,pIm1,pIm2,pIm3,pIm4,pHyp1,pHyp2,pHyp3;
		Complex e1, e2, e3, e4;
		z.setIdempotent();
		this.setIdempotent();
		
		e1 = z1.mult(this.getE1());
		e2 = z2.mult(this.getE2());
		e3 = z3.mult(this.getE3());
		e4 = z4.mult(this.getE4());
		
		return new Tricomplex(e1, e2, e3, e4);
		
		/*pRe = re * z.getRe() - im1 * z.getIm1() - im2 * z.getIm2() -
			im3 * z.getIm3() - im4 * z.getIm4() + hyp1 * z.getHyp1()
			+ hyp2 * z.getHyp2() + hyp3 * z.getHyp3();
		pIm1 = re * z.getIm1() + im1 * z.getRe() - im2 * z.getHyp1() -
			im3 * z.getHyp2() + im4 * z.getHyp3() - hyp1 * z.getIm2()
			- hyp2 * z.getIm3() + hyp3 * z.getIm4();
		pIm2 = re * z.getIm2() - im1 * z.getHyp1() + im2 * z.getRe() -
			im3 * z.getHyp3() + im4 * z.getHyp2() - hyp1 * z.getIm1()
			+ hyp2 * z.getIm4() - hyp3 * z.getIm3(); 
		pIm3 = re * z.getIm3() - im1 * z.getHyp2() - im2 * z.getHyp3() +
			im3 * z.getRe() + im4 * z.getHyp1() + hyp1 * z.getIm4() - 
			hyp2 * z.getIm1() - hyp3 * z.getIm2();
		pIm4 = re * z.getIm4() + im1 * z.getHyp3() + im2 * z.getHyp2() +
			im3 * z.getHyp1() + im4 * z.getRe() + hyp1 * z.getIm3() + 
			hyp2 * z.getIm2() + hyp3 * z.getIm1();
		pHyp1 = re * z.getHyp1() + im1 * z.getIm2() + im2 * z.getIm1() -
			im3 * z.getIm4() - im4 * z.getIm3() + hyp1 * z.getRe()  - 
			hyp2 * z.getHyp3() - hyp3 * z.getHyp2();
		pHyp2 = re * z.getHyp2() + im1 * z.getIm3() - im2 * z.getIm4() +
			im3 * z.getIm1() - im4 * z.getIm2() - hyp1 * z.getHyp3() +
			hyp2 * z.getRe() - hyp3 * z.getHyp1();
		pHyp3 = re * z.getHyp3() - im1 * z.getIm4() + im2 * z.getIm3() +
			im3 * z.getIm2() - im4 * z.getIm1() - hyp1 * z.getHyp2() -
			hyp2 * z.getHyp1() + hyp3 * z.getRe();
		Tricomplex result=new Tricomplex(pRe,pIm1,pIm2,pIm3,pIm4,pHyp1,pHyp2,pHyp3);
		return result;*/
	}
	
	//Multiplier les nombres tricomplexes (change
	//le nombre tricomplexe sur lequel l'operation est effectue)
	public void prod(Tricomplex z)
	{
		double pRe,pIm1,pIm2,pIm3,pIm4,pHyp1,pHyp2,pHyp3;
		pRe = re * z.getRe() - im1 * z.getIm1() - im2 * z.getIm2() -
			im3 * z.getIm3() - im4 * z.getIm4() + hyp1 * z.getHyp1()
			+ hyp2 * z.getHyp2() + hyp3 * z.getHyp3();
		pIm1 = re * z.getIm1() + im1 * z.getRe() - im2 * z.getHyp1() -
			im3 * z.getHyp2() + im4 * z.getHyp3() - hyp1 * z.getIm2()
			- hyp2 * z.getIm3() + hyp3 * z.getIm4();
		pIm2 = re * z.getIm2() - im1 * z.getHyp1() + im2 * z.getRe() - 
			im3 * z.getHyp3() + im4 * z.getHyp2() - hyp1 * z.getIm1() 
			+ hyp2 * z.getIm4() - hyp3 * z.getIm3(); 
		pIm3 = re * z.getIm3() - im1 * z.getHyp2() - im2 * z.getHyp3() +
			im3 * z.getRe() + im4 * z.getHyp1() + hyp1 * z.getIm4() 
			- hyp2 * z.getIm1() - hyp3 * z.getIm2();
		pIm4 = re * z.getIm4() + im1 * z.getHyp3() + im2 * z.getHyp2() +
			im3 * z.getHyp1() + im4 * z.getRe() + hyp1 * z.getIm3() 
			+ hyp2 * z.getIm2() + hyp3 * z.getIm1();
		pHyp1 = re * z.getHyp1() + im1 * z.getIm2() + im2 * z.getIm1() -
			im3 * z.getIm4() - im4 * z.getIm3() + hyp1 * z.getRe()  
			- hyp2 * z.getHyp3() - hyp3 * z.getHyp2();
		pHyp2 = re * z.getHyp2() + im1 * z.getIm3() - im2 * z.getIm4() +
			im3 * z.getIm1() - im4 * z.getIm2() - hyp1 * z.getHyp3() 
			+ hyp2 * z.getRe() - hyp3 * z.getHyp1();
		pHyp3 = re * z.getHyp3() - im1 * z.getIm4() + im2 * z.getIm3() +
			im3 * z.getIm2() - im4 * z.getIm1() - hyp1 * z.getHyp2() 
			- hyp2 * z.getHyp1() + hyp3 * z.getRe(); 
		this.setAll(pRe, pIm1, pIm2, pIm3, pIm4, pHyp1, pHyp2, pHyp3);
		this.setIdempotent();
	}
			
	//Puissance recursive
	public void puisRec(int p)
	{
		Tricomplex z=new Tricomplex(this.re, this.im1, 
			this.im2, this.im3, this.im4,
				this.hyp1, this.hyp2, this.hyp3);
		for (int i=2;i<=p;i++)
			this.prod(z);
	}
	
	//		***************
	//		**			 **
	//		**  Normes   **
	//		**			 **
	//		***************
	//********************
	// NORME EUCLIDIENNE
	//********************
	public double getEuclMod2()
	{
		double norme = re*re + im1*im1 + im2*im2 + im3*im3
			+ im4*im4 + hyp1*hyp1 + hyp2*hyp2 + hyp3*hyp3;
		return norme;
	}
	
	public boolean isGreaterThanInNorm(double value)
	{
		boolean isGreater = true;
		this.setIdempotent();
		if(z1.getNormeCarre() >= value * value &
		z2.getNormeCarre() >= value * value &
		z3.getNormeCarre() >= value * value &
			z4.getNormeCarre() >= value * value)
			isGreater = true;
		else
			isGreater = false;
		
		return isGreater;
		
	}
	
	//***************************************
	//  verifie si le Tricomplexe est le zero
	//***************************************
	public boolean isZero()
	{
		boolean isZero=false;
		if (re==0 && im1==0 && im2==0 && im3 == 0 && im4==0
			&& hyp2==0 && hyp3 == 0 && hyp1==0)
			isZero=true;
		return isZero;
	}
	
	//		*****************
	//		**			   **
	//		**  toString   **
	//		**			   **
	//		*****************
	public String toString()
	{
		return re + (im1>=0 ?  " + " : "  ") + im1 + "i1 " + (im2>=0 ?  " + " : "  ")
			+ im2 + "i2 " + (im3>=0 ?  " + " : "  ") + im3 + "i3 " + (im4>=0 ?  ""
			+ " + " : "  ") + im4 + "i1i2i3 " + 
			(hyp1>=0 ?  " + " : "  ") + hyp1 + "j1 " + 
			(hyp2>=0 ?  " + " : "  ") + hyp2 + "j2 " + (hyp1>=0 ?  " + " : "  ")
			+ hyp3 + "j3 ";
	}
	
	public String toStringIdemp()
	{
		return "(" + z1.toString() + ")e1 + (" + z2.toString() + ")e2 + (" + z3.toString() + ")e3 + (" + z4.toString() + ")e4";
	}
}

