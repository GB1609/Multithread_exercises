package magazzino;
public class Visualizzatore extends Thread
{
	Magazzino magazzino;
	public Visualizzatore(Magazzino m)
	{
		this.magazzino = m;
	}
	@Override
	public void run()
	{
		System.out.println("partenza visualizzatore");
		while (true)
			try
			{
				sleep(1500);
				this.magazzino.stampaMagazzino();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
	}
}
