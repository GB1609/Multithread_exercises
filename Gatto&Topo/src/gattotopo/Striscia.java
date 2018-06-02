package gattotopo;
import java.util.concurrent.locks.ReentrantLock;
public class Striscia
{
	private int lunghezzaStriscia;
	private char stringa[];
	private ReentrantLock l = new ReentrantLock();
	private Topo t;
	private Gatto g;
	private int positionGatto;
	private int positionTopo;
	private boolean runDisplay;
	public Striscia(int lunStriscia)
	{
		this.runDisplay = true;
		this.lunghezzaStriscia = lunStriscia;
		this.stringa = new char[lunStriscia];
		this.t = new Topo(this);
		this.g = new Gatto(this);
		this.positionGatto = (int) (Math.random() * lunStriscia);
		this.posizionaTopo();
		for (int i = 0; i < this.lunghezzaStriscia; i++)
			this.stringa[i] = '_';
		this.stringa[this.positionTopo] = 'T';
		this.stringa[this.positionGatto] = 'G';
		this.t.start();
		this.g.start();
	}
	private void posizionaTopo()
	{
		this.positionTopo = (int) (Math.random() * this.lunghezzaStriscia);
		if (this.positionTopo == this.positionGatto)
			this.posizionaTopo();
	}
	public void gattoCammina()
	{
		this.l.lock();
		this.stringa[this.positionGatto] = '_';
		if (((this.positionGatto == (this.lunghezzaStriscia - 1)) && this.g.getRight())
				|| ((this.positionGatto == 0) && !this.g.getRight()))
			this.g.setRight(!this.g.getRight());
		if (this.g.getRight())
			this.positionGatto++;
		else
			this.positionGatto--;
		this.stringa[this.positionGatto] = 'G';
		this.l.unlock();
	}
	public int getLunghezzaStriscia()
	{
		return this.lunghezzaStriscia;
	}
	public char[] getStringa()
	{
		return this.stringa;
	}
	public boolean isRunDisplay()
	{
		return this.runDisplay;
	}
	public void stampa()
	{
		this.l.lock();
		String daStampare = "< ";
		for (char c : this.stringa)
			daStampare += c + " ";
		daStampare += " >";
		System.out.println(daStampare);
		this.l.unlock();
	}
	public void topoCammina()
	{
		this.l.lock();
		this.stringa[this.positionTopo] = '_';
		if (this.t.randomDIR(this.positionTopo))
			this.positionTopo++;
		else
			this.positionTopo--;
		this.stringa[this.positionTopo] = 'T';
		this.l.unlock();
	}
	public boolean verifica()
	{
		try
		{
			this.l.lock();
			if (this.positionGatto == this.positionTopo)
			{
				this.stringa[this.positionGatto] = 'M';
				this.runDisplay = false;
			}
			return this.runDisplay;
		}
		finally
		{
			this.l.unlock();
		}
	}
}
