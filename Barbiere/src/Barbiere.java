public class Barbiere extends Thread
{
	Negozio negozio;
	public Barbiere(Negozio negozio)
	{
		this.negozio = negozio;
	}
	@Override
	public void run()
	{
		while (true)
			this.negozio.barbiereTagliaICapelli();
	}
}
