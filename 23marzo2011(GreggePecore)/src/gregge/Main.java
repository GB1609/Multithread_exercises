package gregge;
import java.util.Random;
public class Main
{
	public static void main(String[] args)
	{
		int numeroPecore = 0;
		int numeroPastori = 0;
		int numeroTosatori = 0;
		while (numeroPecore < 50)
			numeroPecore = new Random().nextInt(100);
		numeroPastori = new Random().nextInt(Runtime.getRuntime().availableProcessors()) + 1;
		numeroTosatori = new Random().nextInt(Runtime.getRuntime().availableProcessors()) + 1;
		Gregge g = new Gregge(numeroPecore, numeroPastori, numeroTosatori);
		g.startGregge();
		for (int i = 0; i < 6; i++)
			try
			{
				Thread.sleep(new Random().nextInt(2000));
				System.out.println("primo round");
				if (g.esistePecoraNera())
					g.tosaTutte();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
	}
}
