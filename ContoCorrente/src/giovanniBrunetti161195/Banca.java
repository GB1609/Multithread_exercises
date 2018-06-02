package giovanniBrunetti161195;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Banca
{
	Lock lock = new ReentrantLock();
	ArrayList<ContoBancario> contiBancari = new ArrayList<ContoBancario>();
	public void aggiuniConto(ContoBancario e)
	{
		this.contiBancari.add(e);
	}
	public int[] getContiIDs()
	{
		int conti[] = new int[this.contiBancari.size()];
		for (int i = 0; i < this.contiBancari.size(); i++)
			conti[i] = this.contiBancari.get(i).getIdentificativoCodice();
		return conti;
	}
	public int getSaldo(int c)
	{
		for (int i = 0; i < this.contiBancari.size(); i++)
			if (c == this.contiBancari.get(i).getIdentificativoCodice())
				return this.contiBancari.get(i).getSaldo();
		return -1;// Se non e presente il conto richiesto
	}
	public boolean trasferisci(int a, int b, int s)
	{
		ContoBancario c1 = null;
		ContoBancario c2 = null;
		for (int i = 0; i < this.contiBancari.size(); i++)
			if (this.contiBancari.get(i).getIdentificativoCodice() == a)
				c1 = this.contiBancari.get(i);
			else if (this.contiBancari.get(i).getIdentificativoCodice() == b)
				c2 = this.contiBancari.get(i);
		if (c1.getSaldo() >= s)
		{
			Transazioni t = new Transazioni(c1, c2, s);
			c1.addTransazione(t);
			c2.addTransazione(t);
			return true;
		}
		return false;
	}
}
