package gregge;
public class Pecora
{
	private boolean bianca;
	private int numeroPecora;
	public Pecora(int i)
	{
		this.bianca = true;
		this.numeroPecora = i;
	}
	public int getNumeroPecora()
	{
		return this.numeroPecora;
	}
	public boolean isBianca()
	{
		return this.bianca;
	}
	public void setBianca(boolean bianca)
	{
		this.bianca = bianca;
	}
}
