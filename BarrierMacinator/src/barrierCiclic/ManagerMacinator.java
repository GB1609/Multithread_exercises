package barrierCiclic;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
public class ManagerMacinator
{
	public static void main(String[] args)
	{
		System.out.println(new ManagerMacinator().countFirst(1, 200000));
	}
	private int nThread = Runtime.getRuntime().availableProcessors();
	public int countFirst(int min, int max)
	{
		CyclicBarrier b = new CyclicBarrier(this.nThread + 1);
		int firsts = 0;
		int newMin = min;
		Macinator macinator[] = new Macinator[this.nThread];
		if (min > max)
			return 0;
		int sum = max / this.nThread;
		int partition = sum;
		for (int i = 0; i < (this.nThread - 1); i++)
		{
			macinator[i] = new Macinator(newMin, partition, b);
			macinator[i].start();
			newMin = partition + 1;
			partition += sum;
		}
		macinator[this.nThread - 1] = new Macinator(newMin, partition, b);
		macinator[this.nThread - 1].start();
		try
		{
			b.await();
		}
		catch (InterruptedException | BrokenBarrierException e)
		{
			e.printStackTrace();
		}
		for (int i = 0; i < this.nThread; i++)
			firsts += macinator[i].getNumbersFirst();
		return firsts;
	}
}
