package macinator;
public class Macinator extends Thread
{
	private int min, max, numbersFirst;
	private Barrier barrier;
	public Macinator(int min, int max, Barrier barrier)
	{
		this.min = min;
		this.max = max;
		this.numbersFirst = 0;
		this.barrier = barrier;
	}
	private void calculateNumberFirst()
	{
		for (int i = this.min; i < this.max; i++)
			if (this.isFirst(i))
				this.numbersFirst++;
	}
	private boolean isFirst(int i)
	{
		if (i < 4)
			return true;
		for (int a = 2; a < i; a++)
			if ((i % a) == 0)
				return false;
		return true;
	}
	public int getNumbersFirst()
	{
		return this.numbersFirst;
	}
	@Override
	public void run()
	{
		this.calculateNumberFirst();
		this.barrier.done();
	}
	public void setNumbersFirst(int numbersFirst)
	{
		this.numbersFirst = numbersFirst;
	}
}
