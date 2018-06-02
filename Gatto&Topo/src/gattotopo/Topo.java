package gattotopo;
import java.util.Random;
public class Topo extends Thread
{
	private Striscia s;
	Random r = new Random();
	public Topo(Striscia s)
	{
		this.s = s;
	}
	public boolean randomDIR(int position)
	{
		if ((this.s.getLunghezzaStriscia() - 1) == position)
			return false;
		if (0 == position)
			return true;
		else
			return this.r.nextBoolean();
	}
	@Override
	public void run()
	{
		while (this.s.verifica())
			try
			{
				this.s.topoCammina();
				sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
	}
}
