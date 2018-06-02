package filosofiDeadLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
class Bacchetta
{
	Lock lock = new ReentrantLock();
	void lasciaBacchetta()
	{
		this.lock.unlock();
	}
	void prendiBacchetta()
	{
		this.lock.lock();
	}
}
class Filosofo extends Thread
{
	private Tavolo t;
	private int posizione;
	public Filosofo(Tavolo t, int pos)
	{
		this.t = t;
		this.posizione = pos;
		this.setName("Philip " + this.posizione);
	}
	void mangia()
	{
		System.out.println("Il filosofo " + this.getName() + " vuole mangiare");
		this.t.bacchetta[this.posizione].prendiBacchetta();
		System.out.println("Il filosofo " + this.getName() + " prende prima bacchetta");
		this.t.bacchetta[(this.posizione + 1) % 5].prendiBacchetta();
		System.out.println("Il filosofo " + this.getName() + " comincia a mangiare.");
		try
		{
			sleep((int) Math.random() * 2000);
		}
		catch (InterruptedException e)
		{
		}
		System.out.println("Il filosofo " + this.getName() + " termina di mangiare.");
		this.t.bacchetta[this.posizione].lasciaBacchetta();
		System.out.println("Il filosofo " + this.getName() + " lascia prima bacchetta.");
		this.t.bacchetta[(this.posizione + 1) % 5].lasciaBacchetta();
		System.out.println("Il filosofo " + this.getName() + " lascia seconda bacchetta.");
	}
	void pensa()
	{
		System.out.println("Il filosofo " + this.getName() + " pensa.");
		try
		{
			sleep((int) Math.random() * 4000);
		}
		catch (InterruptedException e)
		{
		}
		System.out.println("Il filosofo " + this.getName() + " smette di pensare.");
	}
	@Override
	public void run()
	{
		while (true)
		{
			this.pensa();
			this.mangia();
		}
	}
}
class Tavolo
{
	Bacchetta[] bacchetta = new Bacchetta[5];
	public Tavolo()
	{
		for (int i = 0; i < 5; i++)
			this.bacchetta[i] = new Bacchetta();
	}
}
public class filosofiSempliciMain
{
	public static void main(String[] args)
	{
		Tavolo tavolo = new Tavolo();
		Filosofo[] filosofi = new Filosofo[5];
		for (int i = 0; i < 5; i++)
		{
			filosofi[i] = new Filosofo(tavolo, i);
			filosofi[i].start();
		}
	}
}