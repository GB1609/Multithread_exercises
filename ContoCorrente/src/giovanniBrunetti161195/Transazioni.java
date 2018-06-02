package giovanniBrunetti161195;
public class Transazioni
{
	ContoBancario c1;
	ContoBancario c2;
	int transazione;
	public Transazioni(ContoBancario c1, ContoBancario c2, int transazione)
	{
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.transazione = transazione;
	}
	public ContoBancario getC1()
	{
		return this.c1;
	}
	public ContoBancario getC2()
	{
		return this.c2;
	}
	public int getSommaTrasferimento()
	{
		return this.transazione;
	}
}
