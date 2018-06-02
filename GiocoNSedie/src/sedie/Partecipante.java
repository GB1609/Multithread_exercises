package sedie;
public class Partecipante extends Thread
{
	private InsiemiSedie is;
	private int sediaDoveSonoSeduto;
	public Partecipante(InsiemiSedie is)
	{
		this.is = is;
		this.sediaDoveSonoSeduto = -1;
	}
	public int getSediaDoveSonoSeduto()
	{
		return this.sediaDoveSonoSeduto;
	}
	@Override
	public void run()
	{
		try
		{
			sleep((long) (Math.random() * 10000));
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < this.is.getNumSedie(); i++)
			if (this.is.occupaSedia(i))
			{
				this.sediaDoveSonoSeduto = i;
				break;
			}
	}
}
