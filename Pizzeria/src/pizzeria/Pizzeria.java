package pizzeria;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import blockingQueue.BlockingQueue;
public class Pizzeria
{
	private Lock l = new ReentrantLock();
	private Condition conditionPizzaiolo = this.l.newCondition();
	private Condition conditionClient = this.l.newCondition();
	private BlockingQueue<Order> orderToMake = new BlockingQueue<>(10);
	private BlockingQueue<Order> orderComplete = new BlockingQueue<>(30);
	public Order getOrdine()
	{
		this.l.lock();
		try
		{
			while (this.orderToMake.getFullSlot() == 0)
				try
				{
					this.conditionPizzaiolo.await();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			return this.orderToMake.take();
		}
		finally
		{
			this.l.unlock();
		}
	}
	public Order getPizze(Order o)
	{
		this.l.lock();
		try
		{
			if (this.orderComplete.isFull())
				this.conditionPizzaiolo.signalAll();
			while (this.orderComplete.search(o) == -1)
				try
				{
					this.conditionClient.await();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			return this.orderComplete.takeWithIndex(this.orderComplete.search(o));
		}
		finally
		{
			this.l.unlock();
		}
	}
	public void putOrdine(Order p)
	{
		this.l.lock();
		while (this.orderToMake.isFull())
			try
			{
				this.conditionClient.await();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		this.orderToMake.put(p);
		this.conditionPizzaiolo.signalAll();
		this.l.unlock();
	}
	public void putPizze(Order p)
	{
		this.l.lock();
		while (this.orderComplete.isFull())
			try
			{
				this.conditionPizzaiolo.await();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		this.conditionClient.signalAll();
		this.orderComplete.putFirstPositionFree(p);
		this.l.unlock();
	}
}
