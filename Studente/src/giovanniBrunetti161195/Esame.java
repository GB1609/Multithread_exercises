package giovanniBrunetti161195;
public class Esame
{
	private int voto;
	private int codice;
	private boolean lode;
	private int date;
	public Esame(int voto, int codice, boolean lode, Data date)
	{
		super();
		this.voto = voto;
		this.codice = codice;
		this.lode = lode;
		this.date = date.sottrai(new Data(1, 1, 2000), Data.DAY);
	}
	public int getCodice()
	{
		return this.codice;
	}
	public int getDate()
	{
		return this.date;
	}
	public int getVoto()
	{
		return this.voto;
	}
	public boolean isLode()
	{
		return this.lode;
	}
}
