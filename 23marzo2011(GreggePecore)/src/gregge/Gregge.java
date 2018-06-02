package gregge;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Gregge
{
	private int numeroPecore;
	private int numeroPastori;
	private int numeroTosatori;
	private final int numeroLupi = 2;
	private Lock lock = new ReentrantLock();
	private Condition conditionLupi = this.lock.newCondition();
	private Pecora pecore[];
	private boolean pastoriOTosatoriPresenti;
	public Gregge(int numeroPecore, int numeroPastori, int numeroTosatori)
	{
		super();
		this.numeroPecore = numeroPecore;
		this.numeroPastori = numeroPastori;
		this.numeroTosatori = numeroTosatori;
		this.pastoriOTosatoriPresenti = false;
	}
	public int calcolaPorzione(int numero)
	{
		int porzione = -1;
		porzione = this.numeroPecore / numero;
		return porzione;
	}
	public boolean cercaBianca()
	{
		this.lock.lock();
		try
		{
			for (int i = 0; i < this.numeroPecore; i++)
				if (this.pecore[i].isBianca())
					return true;
			return false;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public boolean cercaNera(int min, int max)
	{
		for (int i = min; i < max; i++)
			if (!this.pecore[i].isBianca())
				return true;
		return false;
	}
	public boolean esistePecoraNera()
	{
		this.lock.lock();
		try
		{
			System.out.println("pastori going");
			this.pastoriOTosatoriPresenti = true;
			int porzione = this.calcolaPorzione(this.numeroPastori);
			CyclicBarrier barrier = new CyclicBarrier(this.numeroPastori + 1);
			Pastore pastori[] = new Pastore[this.numeroPastori];
			int inizio = 0;
			int fine = porzione;
			for (int i = 0; i < (this.numeroPastori - 1); i++)
			{
				pastori[i] = new Pastore(this, barrier, inizio, fine);
				pastori[i].start();
				inizio = fine;
				fine += porzione;
				if ((fine + porzione) > this.numeroPecore)
					fine = this.numeroPecore;
			}
			pastori[this.numeroPastori - 1] = new Pastore(this, barrier, inizio, fine);
			pastori[this.numeroPastori - 1].start();
			try
			{
				System.out.println("aspettimao che i " + this.numeroPastori + " pastori finiscano");
				barrier.await();
				System.out.println("i pastori hanno finito");
			}
			catch (InterruptedException | BrokenBarrierException e)
			{
				e.printStackTrace();
			}
			this.pastoriOTosatoriPresenti = false;
			for (int i = 0; i < this.numeroPastori; i++)
				if (pastori[i].isTrovata())
					return true;
			return false;
		}
		finally
		{
			this.conditionLupi.signalAll();
			this.lock.unlock();
		}
	}
	public void mutaColore()
	{
		this.lock.lock();
		try
		{
			while (this.pastoriOTosatoriPresenti || !this.cercaBianca())
				this.conditionLupi.await();
			int pecoraCasuale = new Random().nextInt(this.numeroPecore);
			while (!this.pecore[pecoraCasuale].isBianca())
				pecoraCasuale = new Random().nextInt(this.numeroPecore);
			this.pecore[pecoraCasuale].setBianca(false);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public void stampaGregge()
	{
		this.lock.lock();
		int cont = 0;
		for (int i = 0; i < this.numeroPecore; i++)
		{
			if (this.pecore[i].isBianca())
				System.out.print('O');
			else
				System.out.print('@');
			cont++;
			if (cont == 5)
			{
				cont = 0;
				System.out.println();
			}
		}
		System.out.println();
		this.lock.unlock();
	}
	public void startGregge()
	{
		this.pecore = new Pecora[this.numeroPecore];
		for (int i = 0; i < this.numeroPecore; i++)
			this.pecore[i] = new Pecora(i + 1);
		new Display(this).start();
		for (int i = 0; i < this.numeroLupi; i++)
			new Lupo(this).start();
	}
	public void tosa(int min, int max)
	{
		for (int i = min; i < max; i++)
			this.pecore[i].setBianca(true);
	}
	public void tosaTutte()
	{
		this.lock.lock();
		try
		{
			System.out.println("entra tosa tutte");
			this.pastoriOTosatoriPresenti = true;
			int porzione = this.calcolaPorzione(this.numeroTosatori);
			CyclicBarrier barrier = new CyclicBarrier(this.numeroTosatori + 1);
			Tosatore tosatori[] = new Tosatore[this.numeroTosatori];
			int inizio = 0;
			int fine = porzione;
			for (int i = 0; i < (this.numeroTosatori - 1); i++)
			{
				System.out.println(i);
				tosatori[i] = new Tosatore(this, barrier, inizio, fine);
				tosatori[i].start();
				inizio = fine;
				fine += porzione;
				if ((fine + porzione) > this.numeroPecore)
					fine = this.numeroPecore;
			}
			System.out.println(this.numeroTosatori - 1);
			tosatori[this.numeroTosatori - 1] = new Tosatore(this, barrier, inizio, fine);
			tosatori[this.numeroTosatori - 1].start();
			try
			{
				System.out.println("aspettiamo che i " + this.numeroTosatori + " tosatori finiscano");
				barrier.await();
				System.out.println("i tosatori hanno finito");
			}
			catch (InterruptedException | BrokenBarrierException e)
			{
				e.printStackTrace();
			}
			this.pastoriOTosatoriPresenti = false;
			this.conditionLupi.signalAll();
		}
		finally
		{
			this.lock.unlock();
		}
	}
}
