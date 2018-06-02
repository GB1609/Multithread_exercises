package magazzino;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Magazzino
{
	public static void main(String[] args)
	{
		Magazzino m = new Magazzino(new Random().nextInt(30));
		Visualizzatore v = new Visualizzatore(m);
		v.start();
		for (int i = 0; i < new Random().nextInt(5); i++)
			new Grossista(m).start();
		for (int i = 0; i < new Random().nextInt(5); i++)
			new Cliente(m).start();
	}
	private int grossista = 0;
	private ListaMateriali listaMateriali;
	private Lock lock = new ReentrantLock();
	private Condition c = this.lock.newCondition();
	private Condition conditionClient = this.lock.newCondition();
	public Magazzino(int dim)
	{
		// mi genera la prima volta in modo casuale la lista materiali
		this.listaMateriali = new ListaMateriali();
		for (int i = 0; i < dim; i++)
			this.listaMateriali.add(new DettagliArticoloMagazzino(i + 1, new Random().nextInt(101)));
		this.stampaMagazzino();
	}
	ListaMateriali putMateriali(ListaMateriali l)
	{
		this.lock.lock();
		this.grossista++;
		ListaMateriali toReturn = new ListaMateriali();
		try
		{
			for (int i = 0; i < l.getLength(); i++)
			{
				int cod = l.get(i).getCodiceArticolo();
				int quantita = l.get(i).getquantitaArticolo();
				int inPiu = this.listaMateriali.put(cod, quantita);
				if (inPiu > 0)
					toReturn.add(new DettagliArticoloMagazzino(cod, inPiu));
			}
			this.c.signalAll();
			this.grossista--;
			if (this.grossista == 0)
				this.conditionClient.signalAll();
			return toReturn;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public ListaMateriali getMateriali(ListaMateriali l)
	{
		this.lock.lock();
		try
		{
			while (this.grossista > 0)
				try
				{
					this.conditionClient.await();
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
			ListaMateriali toReturn = new ListaMateriali();
			while (!this.listaMateriali.verificaDisponibilitaOrdine(l))
				try
				{
					this.c.await();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			for (int i = 0; i < l.getLength(); i++)
			{
				int prelevato = this.listaMateriali.getArticolo(l.get(i).getCodiceArticolo(),
						l.get(i).getquantitaArticolo());
				toReturn.add(new DettagliArticoloMagazzino(l.get(i).getCodiceArticolo(), prelevato));
			}
			return toReturn;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public int getNumeroArticoli()
	{
		return this.listaMateriali.getLength();
	}
	public void stampaMagazzino()
	{
		this.lock.lock();
		this.listaMateriali.stampaLista();
		System.out.println();
		this.lock.unlock();
	}
}
