import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Negozio
{
	private static final int POSTI_A_SEDERE = 10;
	private boolean clientiAttendonoIlTurno;
	private boolean devoRimuovereIlClienteCheHaTagliatoICapelli;
	private boolean barbiereDorme;
	private Lock lock = new ReentrantLock();
	private Condition conditionBarbiere = this.lock.newCondition();
	private Condition conditionCliente = this.lock.newCondition();
	private List<Cliente> clientiNelSalone = new ArrayList<Cliente>();
	private int ordineDiTaglioDeiCapelli[] = new int[POSTI_A_SEDERE];
	private int idThreadSeduti[] = new int[POSTI_A_SEDERE];
	private int contatoreDeiClientiSeduti;
	private int clienteCheDeveTagliarsiICapelli;
	private int indiceDelClienteDaRimuovere;
	public Negozio()
	{
		this.barbiereDorme = true;
		this.devoRimuovereIlClienteCheHaTagliatoICapelli = false;
		this.contatoreDeiClientiSeduti = -1;
		this.clientiAttendonoIlTurno = false;
		this.clienteCheDeveTagliarsiICapelli = -1;
		for (int i = 0; i < this.idThreadSeduti.length; i++)
		{
			this.ordineDiTaglioDeiCapelli[i] = -1;
			this.idThreadSeduti[i] = -1;
		}
	}
	public void barbiereTagliaICapelli()
	{
		this.lock.lock();
		try
		{
			while (this.barbiereDorme)
				try
				{
					this.conditionBarbiere.await();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			for (int i = 0; i < this.ordineDiTaglioDeiCapelli.length; i++)
				if (this.ordineDiTaglioDeiCapelli[i] == 0)
				{
					this.indiceDelClienteDaRimuovere = i;
					break;
				}
			this.clienteCheDeveTagliarsiICapelli = this.idThreadSeduti[this.indiceDelClienteDaRimuovere];
			System.out.println(this.clienteCheDeveTagliarsiICapelli);
			System.out.println("taglio i capelli a: " + this.clienteCheDeveTagliarsiICapelli);
			this.devoRimuovereIlClienteCheHaTagliatoICapelli = true;
			this.clientiAttendonoIlTurno = false;
			this.conditionCliente.signalAll();
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public boolean clienteStaTagliandoICapelli(int id_cliente)
	{
		this.lock.lock();
		try
		{
			while (this.clientiAttendonoIlTurno)
				try
				{
					this.conditionCliente.await();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			if ((this.clienteCheDeveTagliarsiICapelli == id_cliente)
					&& this.devoRimuovereIlClienteCheHaTagliatoICapelli)
			{
				this.ordineDiTaglioDeiCapelli[this.indiceDelClienteDaRimuovere] = -1;
				this.idThreadSeduti[this.indiceDelClienteDaRimuovere] = -1;
				for (int i = 0; i < this.ordineDiTaglioDeiCapelli.length; i++)
					if (this.ordineDiTaglioDeiCapelli[i] != -1)
						this.ordineDiTaglioDeiCapelli[i]--;
				System.out.println("il cliente " + this.clienteCheDeveTagliarsiICapelli
						+ " si è appena tagliato i capelli");
				Cliente clienteDaRimuovere = new Cliente();
				for (Cliente c : this.clientiNelSalone)
					if (c.id == id_cliente)
						clienteDaRimuovere = c;
				this.clientiNelSalone.remove(clienteDaRimuovere);
				this.devoRimuovereIlClienteCheHaTagliatoICapelli = false;
				this.clientiAttendonoIlTurno = true;
				this.contatoreDeiClientiSeduti--;
				this.clienteCheDeveTagliarsiICapelli = -1;
				this.indiceDelClienteDaRimuovere = -1;
				if (this.clientiNelSalone.isEmpty())
				{
					this.barbiereDorme = true;
					try
					{
						this.conditionBarbiere.await();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					this.barbiereDorme = true;
					this.conditionBarbiere.signal();
				}
				return false;
			}
			else
				return true;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public boolean sieditiNelSalone(Cliente c)
	{
		this.lock.lock();
		try
		{
			if (this.contatoreDeiClientiSeduti < (POSTI_A_SEDERE - 1))
			{
				this.contatoreDeiClientiSeduti++;
				c.id = this.contatoreDeiClientiSeduti;
				int ordine = -1;
				int posizioneLibera = -1;
				for (int i = 0; i < this.ordineDiTaglioDeiCapelli.length; i++)
				{
					if (this.ordineDiTaglioDeiCapelli[i] > ordine)
						ordine = this.ordineDiTaglioDeiCapelli[i];
					if (this.ordineDiTaglioDeiCapelli[i] == -1)
						posizioneLibera = i;
				}
				ordine += 1;
				this.ordineDiTaglioDeiCapelli[posizioneLibera] = ordine;
				this.idThreadSeduti[posizioneLibera] = this.contatoreDeiClientiSeduti;
				this.clientiNelSalone.add(c);
				System.out.println("il cliente: " + c.id + "si è seduto");
				if (this.barbiereDorme)
				{
					this.barbiereDorme = false;
					this.conditionBarbiere.signal();
				}
				this.clientiAttendonoIlTurno = true;
				try
				{
					this.conditionCliente.await();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				return true;
			}
			else
				return false;
		}
		finally
		{
			this.lock.unlock();
		}
	}
}
