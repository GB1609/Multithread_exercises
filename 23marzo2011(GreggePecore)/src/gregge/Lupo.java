package gregge;
import java.util.Random;
public class Lupo extends Thread
{
	Gregge g;
	public Lupo(Gregge g)
	{
		this.g = g;
	}
	@Override
	public void run()
	{
		while (true)
			try
			{
				sleep(new Random().nextInt(2000) + 1);
				this.g.mutaColore();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
	}
}
