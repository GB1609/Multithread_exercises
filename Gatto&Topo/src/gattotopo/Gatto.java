package gattotopo;
public class Gatto extends Thread
{
	private Striscia s;
	private boolean right = true;
	private int position;
	public Gatto(Striscia s)
	{
		this.s = s;
	}
	public int getPosition()
	{
		return this.position;
	}
	public boolean getRight()
	{
		return this.right;
	}
	@Override
	public void run()
	{
		while (this.s.verifica())
			try
			{
				this.s.gattoCammina();
				sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
	}
	public void setRight(boolean b)
	{
		this.right = b;
	}
}
