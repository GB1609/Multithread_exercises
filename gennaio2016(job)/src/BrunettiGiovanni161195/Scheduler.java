package BrunettiGiovanni161195;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Scheduler extends Thread
{
	private int RECALC;
	private ArrayList<Job> job;
	private ArrayList<Integer> turniNonEseguitoJob;
	private Lock lock = new ReentrantLock();
	public Scheduler()
	{
		this.RECALC = 4;
		this.job = new ArrayList<Job>();
		this.turniNonEseguitoJob = new ArrayList<Integer>();
	}
	private void aggiornaPrioritaTuttiJOB(ArrayList<Integer> daEseguire, int max)
	{
		for (int i = 0; i < max; i++)
			for (int a = 0; a < this.job.size(); a++)
				if (daEseguire.get(i) == a)
				{
					this.resettaPriorita(a);
					this.turniNonEseguitoJob.set(a, 0);
				}
				else
				{
					int t = this.turniNonEseguitoJob.get(a);
					this.turniNonEseguitoJob.set(a, t + 1);
					if (this.turniNonEseguitoJob.get(a) == this.RECALC)
						this.job.get(a).changePriorita();
				}
	}
	private ArrayList<Integer> cercaDaEseguire(int max)
	{
		ArrayList<Integer> daEseguire = new ArrayList<>();
		for (int i = 0; i < this.job.size(); i++)
			if (this.job.get(i).getPriorita() == max)
				daEseguire.add(i);
		return daEseguire;
	}
	private int searchMaxPriority()
	{
		int max = this.job.get(0).getPrioritaIniziale();
		for (Job j : this.job)
			if (j.getPriorita() < max)
				max = j.getPriorita();
		System.out.println(max);
		return max;
	}
	public void addJob(Job j)
	{
		this.lock.lock();
		this.job.add(j);
		this.turniNonEseguitoJob.add(0);
		this.lock.unlock();
	}
	public void aggiornaPrioritaSingoloJob(int i)
	{
		this.job.get(i).changePriorita();
	}
	public void changeRecalc(int newRecalc)
	{
		this.lock.lock();
		this.RECALC = newRecalc;
		this.lock.unlock();
	}
	public void changeStateJob(int index, int state)
	{
		this.lock.lock();
		this.job.get(index).changeState(state);
		this.lock.unlock();
	}
	public void removeJob(int index)
	{
		this.lock.lock();
		this.job.remove(index);
		this.turniNonEseguitoJob.remove(index);
		this.lock.unlock();
	}
	public void resettaPriorita(int i)
	{
		this.job.get(i).resetPriorita();
	}
	@Override
	public void run()
	{
		while (true)
		{
			this.lock.lock();
			int max = this.searchMaxPriority();
			ArrayList<Integer> daEseguire = this.cercaDaEseguire(max);
			int numeroDaEseguire;
			numeroDaEseguire = daEseguire.size();
			if (numeroDaEseguire > 4)
				numeroDaEseguire = 4;
			CyclicBarrier b = new CyclicBarrier(numeroDaEseguire + 1);
			for (int i = 0; i < (numeroDaEseguire - 1); i++)
				new EseguiJob(b, this.job.get(daEseguire.get(i))).start();
			new EseguiJob(b, this.job.get(daEseguire.get(numeroDaEseguire - 1))).start();
			try
			{
				b.await();
			}
			catch (InterruptedException | BrokenBarrierException e)
			{
				e.printStackTrace();
			}
			this.aggiornaPrioritaTuttiJOB(daEseguire, numeroDaEseguire);
			this.lock.unlock();
		}
	}
}
