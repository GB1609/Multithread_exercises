package campionato;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class RegistroCampionato
{
	private Squadra squadre[];
	private ArrayList<Partita> partite = new ArrayList<>();
	private Lock lock = new ReentrantLock();
	public RegistroCampionato(Squadra squadre[])
	{
		super();
		this.squadre = squadre;
	}
	public void aggiornaClassica()
	{
	}
	public void aggiuntiPartita(Partita p)
	{
		this.partite.add(p);
	}
	public int cercaIndicePartita(Partita p)
	{
		int ciao = -1;
		for (int i = 0; i < this.partite.size(); i++)
			if (this.partite.get(i).equals(p))
				return i;
		return ciao;
	}
	public Squadra[] classificaProvvisoria()
	{
		this.lock.lock();
		try
		{
			Squadra classifica[] = new Squadra[this.squadre.length];
			return classifica;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public Squadra[] classificaUfficiale()
	{
		Squadra classifica[] = new Squadra[this.squadre.length];
		return classifica;
	}
	public void getRisultato(Partita p)
	{
	}
	public void registraGol(Partita p, boolean diCasaOppureNo)
	{
		this.partite.get(this.cercaIndicePartita(p)).aggiornaRisultato(diCasaOppureNo);
	}
	public void terminaPartita(Partita p)
	{
		this.partite.get(this.cercaIndicePartita(p)).setRisultatoDefinitivo();
	}
}
