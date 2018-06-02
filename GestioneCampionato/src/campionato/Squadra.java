package campionato;
import java.util.Arrays;
import java.util.Collections;
public class Squadra
{
	private String nomeSquadra;
	private int partiteGiocate;
	private int punti;
	public Squadra(String nomeSquadra)
	{
		super();
		this.nomeSquadra = nomeSquadra;
		this.partiteGiocate = 0;
		this.punti = 0;
	}
	public void aggiungiPartita()
	{
		this.partiteGiocate++;
	}
	public void aggiungiPunti(int punti)
	{
		this.punti += punti;
	}
	public String getNomeSquadra()
	{
		return this.nomeSquadra;
	}
	public int getPartiteGiocate()
	{
		return this.partiteGiocate;
	}
	public int getPunti()
	{
		return this.punti;
	}
	public boolean maggiore(Squadra s)
	{
		if (s.getPunti() > this.punti)
			return false;
		else if (s.getPunti() < this.punti)
			return true;
		else
		{
			String squadre[] = { s.getNomeSquadra(), this.nomeSquadra };
			Collections.sort(Arrays.asList(squadre));
			if (s.getNomeSquadra().equals(squadre[0]))
				return false;
			else
				return true;
		}
	}
}
