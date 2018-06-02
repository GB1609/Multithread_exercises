package gattotopoCondition;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
public class Strip
{
	private char strip[];
	private int dim;
	Random r = new Random();
	private ReentrantLock lock = new ReentrantLock();
	private Condition cond = this.lock.newCondition();
	private int posMouse, posCat;
	private int directionMouseRight;
	private boolean finishRun;
	public Strip(int dim)
	{
		this.dim = dim;
		this.strip = new char[dim];
		for (int i = 0; i < this.dim; i++)
			this.strip[i] = '_';
		this.posCat = (int) (Math.random() * dim);
		this.directionMouseRight = 1;
		this.posizionateMouse();
		this.strip[this.posMouse] = 'M';
		this.strip[this.posCat] = 'C';
	}
	private void posizionateMouse()
	{
		this.directionMouseRight = 1;
		this.finishRun = false;
		this.posMouse = (int) (Math.random() * this.dim);
		if (this.posMouse == this.posCat)
			this.posizionateMouse();
	}
	public boolean moveCat()
	{
		this.lock.lock();
		try
		{
			if (this.finishRun)
				return true;
			if ((this.posCat == (this.strip.length - 1)) || (this.posCat == 0))
				this.directionMouseRight += -(this.directionMouseRight * 2);
			this.strip[this.posCat] = '_';
			this.strip[this.posCat = this.posCat + this.directionMouseRight] = 'C';
			if (this.posCat == this.posMouse)
			{
				this.finishRun = true;
				this.posCat = '$';
				return true;
			}
			return false;
		}
		finally
		{
			this.cond.signal();
			this.lock.unlock();
		}
	}
	public boolean moveMouse()
	{
		this.lock.lock();
		try
		{
			if (this.finishRun)
				return true;
			boolean dir;
			if (this.posMouse == 0)
				dir = true;
			else if (this.posMouse == (this.strip.length - 1))
				dir = false;
			else
				dir = this.r.nextBoolean();
			this.strip[this.posMouse] = '_';
			if (dir)
				this.strip[this.posMouse = this.posMouse + 1] = 'M';
			else
				this.strip[this.posMouse = this.posMouse - 1] = 'M';
			if (this.posCat == this.posMouse)
			{
				this.posCat = '$';
				this.finishRun = true;
				return true;
			}
			return false;
		}
		finally
		{
			this.cond.signal();
			this.lock.unlock();
		}
	}
	public void print()
	{
		try
		{
			this.lock.lock();
			while (!this.finishRun)
			{
				this.cond.await();
				System.out.print("[");
				System.out.print(this.strip);
				System.out.println("]");
			}
		}
		catch (InterruptedException e)
		{
		}
		finally
		{
			this.lock.unlock();
		}
	}
}
