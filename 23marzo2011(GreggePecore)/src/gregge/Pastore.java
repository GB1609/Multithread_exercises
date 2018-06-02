package gregge;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
public class Pastore extends Thread
{
	private Gregge g;
	private CyclicBarrier barrier;
	private boolean trovata;
	private int min;
	private int max;
	public Pastore(Gregge g, CyclicBarrier barrier, int min, int max)
	{
		this.g = g;
		this.barrier = barrier;
		this.min = min;
		this.max = max;
	}
	public boolean isTrovata()
	{
		return this.trovata;
	}
	@Override
	public void run()
	{
		this.trovata = this.g.cercaNera(this.min, this.max);
		try
		{
			this.barrier.await();
		}
		catch (InterruptedException | BrokenBarrierException e)
		{
			e.printStackTrace();
		}
	}
}
