package magazzino;
public class DettagliArticoloMagazzino
{
	private int codiceArticolo;
	private int quantitaArticolo;
	public DettagliArticoloMagazzino(int codiceArticolo, int quantitaArticolo)
	{
		super();
		this.codiceArticolo = codiceArticolo;
		this.quantitaArticolo = quantitaArticolo;
	}
	public void aggiungiQuantita(int quantita)
	{
		this.quantitaArticolo += quantita;
	}
	public int getCodiceArticolo()
	{
		return this.codiceArticolo;
	}
	public int getquantitaArticolo()
	{
		return this.quantitaArticolo;
	}
	public void sottraiQuantita(int quantita)
	{
		this.quantitaArticolo -= quantita;
	}
	@Override
	public String toString()
	{
		return "<codiceArticolo=" + this.codiceArticolo + ", quantitaArticolo="
				+ this.quantitaArticolo + ">";
	}
	public boolean unCiNiSu()
	{
		if (this.quantitaArticolo == 0)
			return true;
		return false;
	}
}
