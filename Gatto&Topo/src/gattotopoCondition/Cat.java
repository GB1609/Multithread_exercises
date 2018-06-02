package gattotopoCondition;
public class Cat extends Thread
{
	private Strip strip;
	public Cat(Strip s)
	{
		this.strip = s;
	}
	@Override
	public void run()
	{
		while (!this.strip.moveCat())
			try
			{
				sleep(100);
			}
			catch (InterruptedException e)
			{
			}
	}
}
