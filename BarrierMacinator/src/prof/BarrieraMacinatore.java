package prof;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Barrier {

	private final int numeroThreads;
	private int threadArrivati;
	private final Lock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();

	public Barrier(final int n) {
		System.out.println("numero thread barriera: "+n);
		numeroThreads = n;
		threadArrivati = 0;
	}

	public void await() {

		lock.lock();

		try {
			threadArrivati++;
			if (threadArrivati == numeroThreads)
				condition.signalAll();

			while (threadArrivati < numeroThreads)
				condition.await();
		} catch (final InterruptedException e) {

		} finally {

			lock.unlock();
		}

	}

	/*
	 * Termina e aspetta che terminino tutti gli altri!
	 */

	
	public void reset() throws InterruptedException {

		lock.lock();

		/*
		 * Impedisce di azzerare una barriera ancora in uso. Nella CyclicBarrier
		 * di Java, resettare una barriera in uso provoca invece una eccezione.
		 */
		try {

			while (threadArrivati < numeroThreads)
				condition.await();
			threadArrivati = 0;
		} finally {

			lock.unlock();
		}
	}

}

public class BarrieraMacinatore {
	private final static int NThreadMax = Runtime.getRuntime().availableProcessors();

	// private final static int NThreadMax = 1;

	public static void main(final String[] args) {

		final long start = System.nanoTime();
		final BarrieraMacinatore mac = new BarrieraMacinatore();
		System.out.println("Primi tra 1'000'000 e 10'000'000: " + mac.contaPrimi(1, 1000));
		final long elapsed = System.nanoTime() - start;
		System.out.println("Tempo trascorso: " + (double) elapsed / 1000000000 + " secondi.");
	}

	public int contaPrimi(final int MIN, final int MAX) {
		if (MAX < MIN)
			return 0;

		int threadReali = BarrieraMacinatore.NThreadMax;

		int fetta = (MAX - MIN + 1) / threadReali;
		while (fetta == 0) {
			threadReali--;
			fetta = (MAX - MIN + 1) / threadReali;
		}
		final Barrier b = new Barrier(threadReali + 1);

		// creazione e invocazione thread.
		final Macinatore[] ciucci = new Macinatore[threadReali];
		for (int i = 0; i < threadReali - 1; i++) {
			final int min = MIN + i * fetta;
			final int max = min + fetta - 1;
			ciucci[i] = new Macinatore(min, max, b);
			ciucci[i].start();
		}
		ciucci[threadReali - 1] = new Macinatore(MIN + (threadReali - 1) * fetta, MAX, b);
		ciucci[threadReali - 1].start();

		b.await();

		int totale = 0;
		for (int i = 0; i < threadReali; i++)
			totale += ciucci[i].getTotale();
		return totale;
		// raccolta risultati

	}
}

class Macinatore extends Thread

{

	private final int min, max;
	private int totale;
	private final Barrier barriera;

	public Macinatore(final int min, final int max, final Barrier b) {
		this.min = min;
		this.max = max;
		totale = 0;
		barriera = b;
	}

	private void contaPrimiSeq() {

		for (int i = min; i <= max; i++)
			if (eprimo(i))
				totale++;

	}

	private boolean eprimo(final int n) {
		if (n <= 3)
			return true;
		if (n % 2 == 0)
			return false;
		for (int i = 3; i <= Math.sqrt(n); i += 2)
			if (n % i == 0)
				return false;

		return true;
	}

	public int getTotale() {
		return totale;
	}

	@Override
	public void run() {
		contaPrimiSeq();
		barriera.done();
	}
}
