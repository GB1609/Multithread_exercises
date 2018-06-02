package golfSenzaStarvation;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class PoolWithoutStarvation
{
	private final static int numeroPalline = 20;
	public static void main(String[] args)
	{
		PoolWithoutStarvation p = new PoolWithoutStarvation();
		for (int i = 0; i < 10; i++)
			new Giocatore(p).start();
	}
	private int turno;
	private int prossimoTurno;
	private int palline[] = new int[numeroPalline];
	private Lock lock = new ReentrantLock();
	private int pallineDisponibili;
	private Condition condition = this.lock.newCondition();
	public PoolWithoutStarvation()
	{
		this.pallineDisponibili = numeroPalline;
		for (int i = 0; i < numeroPalline; i++)
			this.palline[i] = i;
		this.turno = 0;
		this.prossimoTurno = 1;
	}
	public void noleggiaPalline(int numPalline)
	{
		this.lock.lock();
		int turnoOttenuto = ++this.turno;
		try
		{
			while ((numPalline > this.pallineDisponibili) || (this.prossimoTurno != turnoOttenuto))
				this.condition.await();
			this.pallineDisponibili -= numPalline;
			this.prossimoTurno++;
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
		this.lock.unlock();
	}
}
