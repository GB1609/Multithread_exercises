import java.util.Random;
public class Creator extends Thread
{
	Negozio negozio;
	Barbiere barbiere;
	public Creator()
	{
		negozio = new Negozio();
		barbiere = new Barbiere(negozio);
	}
	@Override
	public void run()
	{
		barbiere.start();
		while (true)
		{
			int sleepCasuale = new Random().nextInt(3000);
			try
			{
				sleep(sleepCasuale);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			Cliente c = new Cliente(negozio);
			c.start();
		}
	}
}
