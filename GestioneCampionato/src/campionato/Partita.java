package campionato;
public class Partita
{
	private Squadra casa;
	private Squadra ospite;
	private int golCasa;
	private int golOspite;
	private int nGiornata;
	private boolean risultatoDefinitivo;
	public Partita(Squadra casa, Squadra ospite, int nGiornata)
	{
		super();
		this.casa = casa;
		this.ospite = ospite;
		this.nGiornata = nGiornata;
		this.golCasa = this.golOspite = 0;
		this.risultatoDefinitivo = false;
	}
	public synchronized void aggiornaRisultato(boolean casa)
	{
		if (casa)
			this.golCasa++;
		else
			this.golOspite++;
	}
	public boolean equals(Partita p)
	{
		if ((this.casa.getNomeSquadra() == p.getCasa().getNomeSquadra())
				&& ((this.ospite.getNomeSquadra() == p.getOspite().getNomeSquadra())
						& (this.nGiornata == p.getnGiornata())))
			return true;
		return false;
	}
	public synchronized Squadra getCasa()
	{
		return this.casa;
	}
	public synchronized int getGolCasa()
	{
		return this.golCasa;
	}
	public synchronized int getGolOspite()
	{
		return this.golOspite;
	}
	public synchronized int getnGiornata()
	{
		return this.nGiornata;
	}
	public synchronized Squadra getOspite()
	{
		return this.ospite;
	}
	public synchronized boolean isRisultatoDefinitivo()
	{
		return this.risultatoDefinitivo;
	}
	public synchronized String risultato()
	{
		return this.golCasa + "-" + this.golOspite;
	}
	public synchronized void setRisultatoDefinitivo()
	{
		this.risultatoDefinitivo = true;
		if (this.golCasa > this.golOspite)
			this.casa.aggiungiPunti(3);
		else if (this.golCasa == this.golOspite)
		{
			this.casa.aggiungiPunti(1);
			this.ospite.aggiungiPunti(1);
		}
		else
			this.ospite.aggiungiPunti(3);
		this.casa.aggiungiPartita();
		this.ospite.aggiungiPartita();
	}
}
