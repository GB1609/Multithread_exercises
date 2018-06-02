package gregge;
public class Display extends Thread
{
	private Gregge gregge;
	public Display(Gregge gregge)
	{
		this.gregge = gregge;
	}
	@Override
	public void run()
	{
		System.out.println("PARTENZA DISPLAY");
		while (true)
			try
			{
				sleep(500);
				this.gregge.stampaGregge();
				System.out.println();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
	}
}
