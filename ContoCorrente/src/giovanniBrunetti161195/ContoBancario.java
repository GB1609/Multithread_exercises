package giovanniBrunetti161195;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class ContoBancario
{
	private Lock lock = new ReentrantLock();
	private int identificativoCodice;
	private int saldo;
	private LinkedList<Transazioni> transazioni = new LinkedList<>();
	public ContoBancario(int identificativoCodice, int saldoIniziale)
	{
		this.identificativoCodice = identificativoCodice;
		this.saldo = saldoIniziale;
	}
	// addTransazione mi aggiunge la transazione t e aggiorna anchen il saldo
	public void addTransazione(Transazioni t)
	{
		this.lock.lock();
		this.transazioni.add(t);
		if (this.transazioni.size() >= 50)
			this.transazioni.removeFirst();
		if (t.getC1().getIdentificativoCodice() == this.identificativoCodice)
			this.saldo -= t.getSommaTrasferimento();
		else
			this.saldo += t.getSommaTrasferimento();
		this.lock.unlock();
	}
	public int getIdentificativoCodice()
	{
		return this.identificativoCodice;
	}
	public int getSaldo()
	{
		this.lock.lock();
		try
		{
			return this.saldo;
		}
		finally
		{
			this.lock.unlock();
		}
	}
	public LinkedList<Transazioni> getTransazioni()
	{
		this.lock.lock();
		try
		{
			return this.transazioni;
		}
		finally
		{
			this.lock.unlock();
		}
	}
}
