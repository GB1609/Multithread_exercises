package cyclicBarrier;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
public class SommatoriParziali extends Thread
{
	private int start, end;
	private CyclicBarrier b;
	private int[] v1, v2, toReturn;
	private boolean plus;
	public SommatoriParziali(boolean plus, int start, int end, CyclicBarrier b, int[] v1, int[] v2)
	{
		super();
		this.start = start;
		this.end = end;
		this.b = b;
		this.v1 = v1;
		this.v2 = v2;
		this.toReturn = new int[end - start];
		this.plus = plus;
	}
	public int[] getToReturn()
	{
		return this.toReturn;
	}
	@Override
	public void run()
	{
		if (this.plus)
			this.sommaParte();
		else
			this.sottraiParte();
		try
		{
			this.b.await();
		}
		catch (InterruptedException | BrokenBarrierException e)
		{
			e.printStackTrace();
		}
	}
	public void sommaParte()
	{
		int k = 0;
		for (int i = this.start; i < this.end; i++)
		{
			this.toReturn[k] = this.v1[i] + this.v2[i];
			k++;
		}
	}
	public void sottraiParte()
	{
		int k = 0;
		for (int i = this.start; i < this.end; i++)
		{
			this.toReturn[k] = this.v1[i] - this.v2[i];
			k++;
		}
	}
}
