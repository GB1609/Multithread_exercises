package gattoTopoConditionIANNI;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//
// Gatto topo con Lock e Condition di attesa per la stampa.
//
class Display extends Thread
{
	private Striscia striscia;
	public Display(Striscia s)
	{
		this.striscia = s;
	}
	@Override
	public void run()
	{
		System.out.println("First run Display");
		this.striscia.printStriscia();
		System.out.println("Il topo ha fatto la fine del topo");
	}
}
class Gatto extends Thread
{
	private Striscia striscia;
	public Gatto(Striscia s)
	{
		this.striscia = s;
	}
	@Override
	public void run()
	{
		System.out.println("First run Gatto");
		while (!this.striscia.muoviGatto())
			try
			{
				sleep(100);
			}
			catch (InterruptedException e)
			{
			}
	}
}
class Striscia
{
	private Lock lock = new ReentrantLock();
	private Condition cond = this.lock.newCondition();
	private final int LUNG = 40;
	private char striscia[] = new char[this.LUNG];
	private int topo, gatto, dirGatto;
	private boolean fine;
	public Striscia()
	{
		this.fine = false;
		this.dirGatto = 1;
		this.topo = (int) (this.LUNG * Math.random());
		this.gatto = (int) (this.LUNG * Math.random());
		for (int i = 0; i < this.LUNG; i++)
			this.striscia[i] = ' ';
		this.striscia[this.topo] = '.';
		this.striscia[this.gatto] = '*';
	}
	public boolean muoviGatto()
	{
		this.lock.lock();
		try
		{
			if (this.fine)
				return true;
			this.cond.signal();
			this.striscia[this.gatto] = ' ';
			this.gatto += this.dirGatto;
			if ((this.gatto == this.LUNG) || (this.gatto < 0))
			{
				this.dirGatto = -this.dirGatto;
				this.gatto += this.dirGatto;
			}
			if (this.gatto == this.topo)
			{
				this.fine = true;
				this.striscia[this.gatto] = '@';
				return true;
			}
			this.striscia[this.gatto] = '*';
			return false;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public boolean muoviTopo()
	{
		try
		{
			this.lock.lock();
			if (this.fine)
				return true;
			this.striscia[this.topo] = ' ';
			this.cond.signal();
			// valore compreso tra -1 e 1
			int salto = new Random().nextInt(3) - 1;
			if (((this.topo + salto) >= 0) && ((this.topo + salto) < this.LUNG))
				this.topo = (this.topo + salto);
			if (this.gatto != this.topo)
				this.striscia[this.topo] = '.';
			else
			{
				this.fine = true;
				this.striscia[this.topo] = '@';
				return true;
			}
			return false;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public void printStriscia()
	{
		try
		{
			this.lock.lock();
			while (!this.fine)
			{
				this.cond.await();
				System.out.print("|");
				System.out.print(this.striscia);
				System.out.print("|");
				System.out.print("\r");
			}
		}
		catch (InterruptedException e)
		{
		}
		finally
		{
			this.lock.unlock();
		}
	}
}
class Topo extends Thread
{
	private Striscia striscia;
	public Topo(Striscia s)
	{
		this.striscia = s;
	}
	@Override
	public void run()
	{
		System.out.println("First run Topo");
		while (!this.striscia.muoviTopo())
			try
			{
				sleep(50);
			}
			catch (InterruptedException e)
			{
			}
	}
}
public class MainGattoTopoDisplay
{
	public static void main(String[] args)
	{
		Striscia s = new Striscia();
		System.out.println("Start Gatto & Topo versione Lock+Condition");
		Gatto jerry = new Gatto(s);
		Topo tom = new Topo(s);
		Display display = new Display(s);
		System.out.println("Created");
		display.start();
		jerry.start();
		tom.start();
		System.out.println("Started");
	}
}