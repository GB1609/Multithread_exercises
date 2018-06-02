package BrunettiGiovanni161195;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
public class EseguiJob extends Thread
{
	private CyclicBarrier b;
	private Job j;
	public EseguiJob(CyclicBarrier b, Job j)
	{
		super();
		this.b = b;
		this.j = j;
	}
	@Override
	public void run()
	{
		while (this.j.esegui() == 0)
		{
		}
		try
		{
			this.b.await();
		}
		catch (InterruptedException | BrokenBarrierException e)
		{
			e.printStackTrace();
		}
	}
}
