package GiovanniBrunetti161195;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class BlockinQueueQualsiasi<T>
{
	private int size;
	private BlockingQueue4<T> blockings[];
	private Lock lock = new ReentrantLock();
	public BlockinQueueQualsiasi(int dimensione)
	{
		if ((dimensione % 4) != 0)
			this.size = (dimensione / 4) + 1;
		else
			this.size = dimensione / 4;
		this.blockings = new BlockingQueue4[this.size];
	}
	public void put(T element) throws InterruptedException
	{
		this.lock.lock();
		boolean inserito = false;
		for (int i = this.size - 1; i > 0; i--)
			if (!(this.blockings[i].size() == 4))
			{
				this.blockings[i].put(element);
				inserito = true;
				break;
			}
		if (!inserito)
			this.blockings[0].put(element);
		this.lock.unlock();
	}
	public T take() throws InterruptedException
	{
		this.lock.lock();
		try
		{
			return this.blockings[this.size - 1].take();
		}
		finally
		{
			for (int i = this.size - 1; i >= 0; i--)
				if ((i != 0) && !(this.blockings[i - 1].isEmpty()))
					this.blockings[i].put(this.blockings[i - 1].take());
			this.lock.unlock();
		}
	}
}
