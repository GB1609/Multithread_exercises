package giovanniBrunetti161195;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Pizzeria
{
	public static void main(String[] args)
	{
		new Pizzeria();
	}
	private Lock lock = new ReentrantLock();
	private PizzaioloPazzo joker;
	private FriendshipGod bacco;
	//
	// Qui metterai i tavoli che arrivano. Se non ti piace il Vector, usa la
	// struttura dati che preferisci.
	//
	public Vector<String> tavoliASedere;
	Pizzeria()
	{
		this.joker = new PizzaioloPazzo();
		this.joker.start();
		this.bacco = new FriendshipGod();
		this.bacco.start();
		this.tavoliASedere = new Vector<>();
		String[] nomi = { "MARIO", "LUCIA", "MARIA", "ELENA" };
		Cameriere[] sottoPagato = new Cameriere[4];
		for (int i = 0; i < 4; i++)
		{
			sottoPagato[i] = new Cameriere(nomi[i], this);
			sottoPagato[i].start();
		}
	}
	public String convertiPizza(String pizza)
	{
		String pizza2 = pizza;
		pizza2 = pizza2.replace("(", "[");
		pizza2 = pizza2.replace(")", "]");
		return pizza2;
	}
	public FriendshipGod getBacco()
	{
		return this.bacco;
	}
	public PizzaioloPazzo getJoker()
	{
		return this.joker;
	}
	public void mettiASedere(String tavolo)
	{
		this.lock.lock();
		this.tavoliASedere.add(tavolo);
		this.lock.unlock();
	}
	public boolean servi(String pizzaDaServire)
	{
		this.lock.lock();
		try
		{
			String pizzaDaServire2 = this.convertiPizza(pizzaDaServire);
			for (int index = 0; index < this.tavoliASedere.size(); index++)
				if (this.tavoliASedere.get(index).indexOf(pizzaDaServire2) >= 0)
				{
					System.out.println(this.tavoliASedere.get(index));
					String s = this.tavoliASedere.get(index)
							.replaceFirst("\\[" + pizzaDaServire2 + "\\]", pizzaDaServire);
					this.tavoliASedere.set(index, new String(s));
					System.out.println(this.tavoliASedere.get(index));
					return true;
				}
			return false; // se nessuno aspetta questa pizza
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public boolean verificaCompletamentoTavoli()
	{
		this.lock.lock();
		try
		{
			boolean rimosso = false;
			for (int index = 0; index < this.tavoliASedere.size(); index++)
				if (this.tavoliASedere.get(index).indexOf("[") == -1)
				{
					this.tavoliASedere.remove(index);
					rimosso = true; // se non trova nella stringa [ è servito e lo
											// rimuove.
				}
			return rimosso; // ritorna false se non ci sono tavoli da rimuovere
		}
		finally
		{
			this.lock.unlock();
		}
	}
}
