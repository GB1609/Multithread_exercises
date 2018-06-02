package gattotopo;
public class Display extends Thread
{
	public static void main(String[] args)
	{
		Striscia s = new Striscia(10);
		new Display(s).start();
	}
	Striscia s;
	public Display(Striscia s)
	{
		this.s = s;
	}
	@Override
	public void run()
	{
		while (this.s.isRunDisplay())
		{
			try
			{
				sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			this.s.stampa();
		}
		System.out.println("The mouse is in the stomack of Cat. Finally!");
	}
}
