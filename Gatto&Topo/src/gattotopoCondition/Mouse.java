package gattotopoCondition;
public class Mouse extends Thread
{
	private Strip strip;
	public Mouse(Strip s)
	{
		this.strip = s;
	}
	@Override
	public void run()
	{
		while (!this.strip.moveMouse())
			try
			{
				sleep(100);
			}
			catch (InterruptedException e)
			{
			}
	}
}