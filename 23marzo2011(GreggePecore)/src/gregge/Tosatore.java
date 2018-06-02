package gregge;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
public class Tosatore extends Thread
{
	private Gregge g;
	private CyclicBarrier barrier;
	private boolean trovata;
	private int min;
	private int max;
	public Tosatore(Gregge g, CyclicBarrier barrier, int min, int max)
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
		System.out.println("entro run tosa");
		this.g.tosa(this.min, this.max);
		try
		{
			System.out.println("io tosatore " + this.getName() + " inzio");
			this.barrier.await();
			System.out.println("io tosatore " + this.getName() + " ho finito");
		}
		catch (InterruptedException | BrokenBarrierException e)
		{
			e.printStackTrace();
		}
	}
}
