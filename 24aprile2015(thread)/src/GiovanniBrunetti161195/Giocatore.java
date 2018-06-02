package GiovanniBrunetti161195;
import java.util.Random;
public class Giocatore extends Thread
{
	private Tavolo t;
	private int position;
	public Giocatore(Tavolo t, int position)
	{
		super();
		this.position = position;
		this.t = t;
	}
	public int getPosition()
	{
		return this.position;
	}
	@Override
	public void run()
	{
		while (true)
		{
			System.out.println("il giocarore " + this.getName() + " chiede il posacenere");
			int posacenereUsato = this.t.prendiPosacenere(this.getPosition());
			System.out.println("il giocarore " + this.getName() + " ha preso il posacenere");
			try
			{
				sleep(new Random().nextInt(3000));
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			if (posacenereUsato != -1)
			{
				this.t.lasciaPosacenere(posacenereUsato);
				System.out.println("il giocarore " + this.getName() + " ha lasciato posacenere");
			}
			else
				System.out.println("giocatore in posizione " + this.position
						+ " sto fumando con un posacenere nelle vicinanze");
		}
	}
}
