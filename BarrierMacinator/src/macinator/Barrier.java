package macinator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Barrier
{
	private int nThread, threadFinish;
	private Lock lock = new ReentrantLock();
	private Condition condition = this.lock.newCondition();
	public Barrier(int nThread)
	{
		this.nThread = nThread;
		this.threadFinish = 0;
	}
	public void await()
	{
		this.lock.lock();
		try
		{
			this.threadFinish++;
			if (this.threadFinish == this.nThread)
				this.condition.signalAll();
			while (this.threadFinish < this.nThread)
				this.condition.await();
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
	public void done()
	{
		this.lock.lock();
		try
		{
			this.threadFinish++;
			if (this.threadFinish == this.nThread)
				this.condition.signalAll();
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public void reset()
	{
		this.lock.lock();
		try
		{
			while (this.threadFinish < this.nThread)
				this.condition.await();
			this.threadFinish = 0;
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
}
