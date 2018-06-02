package magazzino;
import java.util.Random;
public class Grossista extends Thread
{
	private Magazzino magazzino;
	public Grossista(Magazzino m)
	{
		this.magazzino = m;
	}
	private ListaMateriali generateRandom()
	{
		ListaMateriali listaMateriali = new ListaMateriali();
		int dim = new Random().nextInt(this.magazzino.getNumeroArticoli() - 1) + 1;
		for (int i = 0; i < dim; i++)
			listaMateriali.add(new DettagliArticoloMagazzino(
					new Random().nextInt(this.magazzino.getNumeroArticoli()),
					new Random().nextInt(101)));
		return listaMateriali;
	}
	@Override
	public void run()
	{
		System.out.println("Arrivo Grossista");
		try
		{
			sleep(new Random().nextInt(3000) + 1);
			this.magazzino.putMateriali(this.generateRandom());
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
