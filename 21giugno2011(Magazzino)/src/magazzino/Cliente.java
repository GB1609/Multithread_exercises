package magazzino;
import java.util.Random;
public class Cliente extends Thread
{
	private Magazzino magazzino;
	public Cliente(Magazzino m)
	{
		this.magazzino = m;
	}
	private ListaMateriali generateRandom()
	{
		ListaMateriali listaMateriali = new ListaMateriali();
		int dim = new Random().nextInt(this.magazzino.getNumeroArticoli());
		for (int i = 0; i < dim; i++)
			listaMateriali.add(new DettagliArticoloMagazzino(
					new Random().nextInt(this.magazzino.getNumeroArticoli()),
					new Random().nextInt(101)));
		return listaMateriali;
	}
	@Override
	public void run()
	{
		System.out.println("Arrivo CLiente");
		try
		{
			sleep(new Random().nextInt(3000) + 1);
			this.magazzino.getMateriali(this.generateRandom());
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
