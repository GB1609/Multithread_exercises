package golf;
import java.util.Random;
public class Giocatore extends Thread
{
	private Pool p;
	private Random r;
	public Giocatore(Pool p)
	{
		super();
		this.p = p;
		this.r = new Random();
	}
	@Override
	public void run()
	{
		try
		{
			sleep(this.r.nextInt(1200) + 1);
			int pallineDaRichiedere = this.r.nextInt(10) + 1;
			System.out.println(this.getName() + " ha richiesto di giocare con " + pallineDaRichiedere);
			this.p.noleggiaPalline(pallineDaRichiedere);
			System.out.println(this.getName() + " ha noleggiato " + pallineDaRichiedere + " palline");
			sleep(this.r.nextInt(1000) * pallineDaRichiedere);
			this.p.restituisctiPalline(pallineDaRichiedere);
			System.out.println(this.getName() + " ha restituito " + pallineDaRichiedere + " palline");
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
