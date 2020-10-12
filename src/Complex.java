
public class Complex 
{
	protected double im,re;
	
	public Complex()
	{
		this.re=0;
		this.im=0;
	}
	
	public Complex(double re)
	{
		this.re=re;
		this.im=0;
	}
	
	public Complex(double re, double im)
	{
		this.re=re;
		this.im=im;
	}
	
	//obtenir les parties imaginaire et reelle
	public double getIm()
	{
		return im;
	}
	
	public double getRe()
	{
		return re;
	}
	
	//Changer les parties imaginaires et reelles
	public void setIm(double im)
	{
		this.im=im;
	}
	
	public void setRe(double re)
	{
		this.re=re;
	}
	
	public void setReIm(double re,double im)
	{
		this.re=re;
		this.im=im;
	}
	
	//Addition de deux complexes (qui en sortie fournie un nombres complexe)
	public Complex add(Complex z)
	{
		Complex result=new Complex();
		double pRe,pIm;
		pRe=this.getRe()+z.getRe();
		pIm=this.getIm()+z.getIm();
		result.setReIm(pRe, pIm);
		return result;
	}
	
	//Addition de deux complexes (Change le complexe sur lequel est effectue l'operation)
	public void plus(Complex z)
	{
		this.setReIm(this.getRe()+z.getRe(), this.getIm()+z.getIm());
	}
	
	//Multiplier les nombres complexes (donne un nombre complexe)
	public Complex mult(Complex z)
	{
		Complex result=new Complex();
		double pRe,pIm;
		pRe=this.getRe() * z.getRe() - this.getIm() * z.getIm();
		pIm=this.getRe() * z.getIm() + this.getIm() * z.getRe();
		result.setReIm(pRe, pIm);
		return result;
	}
	
	//Multiplier les nombres complexes (change le nombre complexe sur lequel l'operation est effectue)
	public void prod(Complex z)
	{
		this.setReIm(this.getRe() * z.getRe() - this.getIm() * z.getIm(),
				this.getRe() * z.getIm() + this.getIm() * z.getRe());
	}
	
	//Puissance d'un complexe (qui change ce complexe apres l'operation)
	public void puis(int n)
	{
		if (this.isZero())
		{
			this.re=0;
			this.im=0;
		}
		else
		{
			this.setReIm(Math.pow(Math.sqrt(this.getNormeCarre()), n) * Math.cos(n*this.getArg())
			, Math.pow(Math.sqrt(this.getNormeCarre()), n) * Math.sin(n*this.getArg()));
		}
	}
	
	public Complex power(int p)
	{
		if (p <= 1)
			return this;
		else
		{
			double resultRe = this.re, resultIm = this.im;
			double resultReTemp;
			int powerTemp = p;
			do
			{
				resultReTemp = this.re * resultRe - this.im * resultIm;
				resultIm = this.re * resultIm + this.im * resultRe;
				resultRe = resultReTemp;
				powerTemp--;
			}while (powerTemp >= 2);
			return new Complex(resultRe, resultIm);
		}
	}
	
	public Complex scalarMult(double k)
	{
		return new Complex(this.re * k, this.im * k);
	}
	
	//Puissance recursive
	public void puisRec(int n)
	{
		Complex z=new Complex(this.getRe(),this.getIm());
		for (int i=2;i<=n;i++)
			this.prod(z);
	}
	
	//Norme d'un complexe
	public double getNormeCarre()
	{
		return this.getRe() * this.getRe() + this.getIm() * this.getIm();
	}
	
	//Norme d'un complexe
		public double getNorme()
		{
			return Math.sqrt(this.getNormeCarre());
		}
	
	//Argument d'un nombre complexe
	public double getArg()
	{
		double arg;
		if (this.isZero())
			arg=0;
		else
		{
			if (this.getIm()==0)
			{
				if (this.getRe()>0)
					arg=0;
				else
					arg=Math.PI;
			}
			if (this.getRe()==0)
			{
				if (this.getIm()>0)
					arg=Math.PI/2;
				else
					arg=(3 * Math.PI)/2;
			}
			else if (this.getIm()>0 && this.getRe()>0)
				arg=Math.acos(this.getRe()/this.getNorme());
			else if (this.getIm()<0 && this.getRe()>0)
				arg=2 * Math.PI-Math.acos(this.getRe()/this.getNorme());
			else if(this.getIm()>0 && this.getRe()<0)
				arg=Math.acos(this.getRe()/this.getNorme());
			else
				arg=Math.acos(this.getRe()/this.getNorme())+Math.PI;
		}
		return arg;
	}
	
	//verifie si le complexe est le zero
	public boolean isZero()
	{
		boolean isZero=false;
		if (this.getRe()==0 && this.getIm()==0)
			isZero=true;
		return isZero;
	}
	
	//le tostring
	public String toString()
	{
		return re + (im>0 ? " + " : " ") + (im == 0 ? "" : im + "i");
	}

}
