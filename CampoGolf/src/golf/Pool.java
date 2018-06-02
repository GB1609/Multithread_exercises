package golf;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Pool
{
	private final static int numeroPalline = 20;
	public static void main(String[] args)
	{
		Pool p = new Pool();
		for (int i = 0; i < 10; i++)
			new Giocatore(p).start();
	}
	private int palline[] = new int[numeroPalline];
	private Lock lock = new ReentrantLock();
	private int pallineDisponibili;
	private Condition condition = this.lock.newCondition();
	public Pool()
	{
		this.pallineDisponibili = numeroPalline;
		for (int i = 0; i < numeroPalline; i++)
			this.palline[i] = i;
	}
	public void noleggiaPalline(int numPalline)
	{
		this.lock.lock();
		try
		{
			while (numPalline > this.pallineDisponibili)
				this.condition.await();
			this.pallineDisponibili -= numPalline;
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
	public void restituisctiPalline(int numPalline)
	{
		this.lock.lock();
		this.pallineDisponibili += numPalline;
		this.condition.signalAll();
		System.out.println(this.pallineDisponibili);
		this.lock.unlock();
	}
}
