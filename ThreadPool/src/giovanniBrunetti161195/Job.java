package giovanniBrunetti161195;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
abstract class Job implements Runnable
{
	boolean running;
	boolean terminato;
	Condition startCondition;
	Condition endCondition;
	Job(Lock l)
	{
		this.startCondition = l.newCondition();
		this.endCondition = l.newCondition();
		this.running = this.terminato = false;
	}
	@Override
	public abstract void run();
}