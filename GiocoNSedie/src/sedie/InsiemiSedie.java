package sedie;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class InsiemiSedie
{
	private Lock lock = new ReentrantLock();
	private int seduti = 0;
	private Sedia sedie[];
	private int numSedie;
	public InsiemiSedie(int numSedie)
	{
		this.numSedie = numSedie;
		this.sedie = new Sedia[numSedie];
		for (int i = 0; i < numSedie; i++)
			this.sedie[i] = new Sedia(i);
	}
	public synchronized boolean getFree()
	{
		for (Sedia s : this.sedie)
			if (!s.isOccupata())
				return true;
		return false;
	}
	public int getNumSedie()
	{
		return this.numSedie;
	}
	public Sedia[] getSedie()
	{
		return this.sedie;
	}
	public int getSeduti()
	{
		return this.seduti;
	}
	public boolean occupaSedia(int i)
	{
		this.lock.lock();
		if (!this.sedie[i].isOccupata())
		{
			this.sedie[i].setOccupata(true);
			this.lock.unlock();
			return true;
		}
		else
		{
			this.lock.unlock();
			return false;
		}
	}
	public void stampaSedie()
	{
		String stampa = "";
		for (int i = 0; i < this.getNumSedie(); i++)
			if (this.getSedie()[i].isOccupata())
				stampa += "O";
			else
				stampa += "L";
		System.out.println(stampa);
	}
}
