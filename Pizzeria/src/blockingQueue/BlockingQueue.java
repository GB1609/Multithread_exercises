package blockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class BlockingQueue<T>
{
	private T queue[];
	private Lock lock = new ReentrantLock();
	private Condition conditionEmpty = this.lock.newCondition();
	private Condition conditionFull = this.lock.newCondition();
	private int in, out, fullSlot;
	@SuppressWarnings("unchecked")
	public BlockingQueue(int dim)
	{
		this.in = 0;
		this.out = 0;
		this.fullSlot = 0;
		this.queue = (T[]) new Object[dim];
	}
	public int getFullSlot()
	{
		this.lock.lock();
		try
		{
			return this.fullSlot;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public boolean isEmpty() // verifica se è vuota
	{
		this.lock.lock();
		try
		{
			if (this.fullSlot == 0)
				return true;
			return false;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public boolean isFull() // verifica se è piena
	{
		this.lock.lock();
		try
		{
			if (this.fullSlot == this.queue.length)
				return true;
			return false;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public void printQueue() // stampa gli elementi della coda
	{
		for (int i = 0; i < this.fullSlot; i++)
			if (this.queue[i] != null)
				this.queue[i].toString();
	}
	public void put(T toInsert) // inserisce in modo FIFO
	{
		this.lock.lock();
		try
		{
			while (this.fullSlot == this.queue.length)
				try
				{
					this.conditionFull.await();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			this.queue[this.in] = toInsert;
			this.in = (this.in + 1) % this.queue.length;
			if (this.fullSlot == 0)
				this.conditionEmpty.signal();
			this.fullSlot++;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public void putFirstPositionFree(T toInsert) // mi cerca la prima posizione
																// libera e inserisce
	{
		this.lock.lock();
		try
		{
			int index = 0;
			while (this.fullSlot == this.queue.length)
				try
				{
					this.conditionFull.await();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			for (int i = 0; i < this.queue.length; i++)
				if (this.queue[i] == null)
					index = i;
			this.queue[index] = toInsert;
			if (this.fullSlot == 0)
				this.conditionEmpty.signal();
			this.fullSlot++;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public int search(T other) // mi un oggetto nella lista
	{
		int toReturn = -1;
		if (this.fullSlot == 0)
			return toReturn;
		for (int i = 0; i < this.queue.length; i++)
			if (this.queue[i] != null)
				if (this.queue[i].equals(other))
					return i;
		return toReturn;
	}
	public T take() // prende un oggetto in ordine Fifo
	{
		T toReturn;
		this.lock.lock();
		try
		{
			while (this.fullSlot == 0)
				try
				{
					this.conditionEmpty.await();
				}
				catch (InterruptedException e)
				{
				}
			toReturn = this.queue[this.out];
			this.out = (this.out + 1) % this.queue.length;
			if (this.fullSlot == this.queue.length)
				this.conditionFull.signal();
			this.fullSlot--;
			return toReturn;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public T takeWithIndex(int index) // mi preleva l0oggetto nell'indice cercato
	{
		T toReturn;
		this.lock.lock();
		try
		{
			while (this.fullSlot == 0)
				try
				{
					this.conditionEmpty.await();
				}
				catch (InterruptedException e)
				{
				}
			toReturn = this.queue[index];
			if (this.fullSlot == this.queue.length)
				this.conditionFull.signal();
			this.fullSlot--;
			return toReturn;
		}
		finally
		{
			this.queue[index] = null;
			this.lock.unlock();
		}
	}
}
