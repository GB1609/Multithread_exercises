package GiovanniBrunetti161195;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Tavolo
{
	private int posaceneri[] = new int[3];
	private Giocatore giocatori[];
	private boolean haGiaFumato[]; // mi serve per evitare starvation
	private Lock lock = new ReentrantLock();
	private Condition condition = this.lock.newCondition();
	int dim;
	public Tavolo(int dim)
	{
		this.dim = dim;
		this.giocatori = new Giocatore[dim];
		this.haGiaFumato = new boolean[dim];
		Arrays.fill(this.posaceneri, -1);
		for (int i = 0; i < dim; i++)
			this.giocatori[i] = new Giocatore(this, i);
		Arrays.fill(this.haGiaFumato, false);
	}
	private void controllaSeHannoFumatoTutti()
	{
		boolean hannoFumatoTutti = true;
		for (int i = 0; i < this.dim; i++)
			if (!this.haGiaFumato[i])
			{
				hannoFumatoTutti = false;
				break;
			}
		if (hannoFumatoTutti)
			for (int i = 0; i < this.dim; i++)
				this.haGiaFumato[i] = false;
	}
	private int getPosacenereLibero()
	{
		for (int i = 0; i < 3; i++)
			if (this.posaceneri[i] == -1)
				return i;
		return -1;
	}
	private boolean nelleVicinanze(int indice)
	{
		for (int i = 0; i < 3; i++)
			if ((i == this.posaceneri[i]) || ((i - 1) == this.posaceneri[i])
					|| ((i + 1) == this.posaceneri[i]))
				return true;
		return false;
	}
	private boolean tuttiOccupati()
	{
		for (int i = 0; i < 3; i++)
			if (this.posaceneri[i] == -1)
				return false;
		return true;
	}
	public void lasciaPosacenere(int numeroPosacenere)
	{
		this.lock.lock();
		this.posaceneri[numeroPosacenere] = -1;
		this.condition.signalAll();
		this.lock.unlock();
	}
	public int prendiPosacenere(int position)
	{
		this.lock.lock();
		try
		{
			if (this.nelleVicinanze(position)) // se e nelle vicinanze puo fumare
															// tranquillamente
				return -1;
			else
			{
				while ((!this.nelleVicinanze(position) && this.tuttiOccupati())
						|| this.haGiaFumato[position]) // aspetta finche non e nelle
																	// vicinanze, non si libera
																	// un posacenere o, se ha gia
																	// fumato, da la precedenza a
																	// chi non lha fatoo
					try
					{
						this.condition.await();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				if (!this.tuttiOccupati())
				{
					int posacenerePreso = this.getPosacenereLibero();
					this.posaceneri[this.getPosacenereLibero()] = position;
					return posacenerePreso;
				}
				return -1;
			}
		}
		finally
		{
			this.haGiaFumato[position] = true;
			this.controllaSeHannoFumatoTutti();
			this.lock.unlock();
		}
	}
	public void startGame()
	{
		for (int i = 0; i < this.dim; i++)
			this.giocatori[i].start();
	}
}
