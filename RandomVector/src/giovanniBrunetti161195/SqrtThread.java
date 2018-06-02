package giovanniBrunetti161195;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
public class SqrtThread extends Thread
{
	CyclicBarrier barrier;
	double result;
	public SqrtThread(CyclicBarrier b, double n)
	{
		this.barrier = b;
		this.result = n;
	}
	public double getResult()
	{
		return this.result;
	}
	@Override
	public void run()
	{
		this.result = Math.sqrt(this.result);
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
