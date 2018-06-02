package BrunettiGiovanni161195;
import java.util.Random;
public class Job
{
	int number;
	private int state;
	private int prioritaIniziale;
	private int priorita;
	public Job(int i)
	{
		System.out.println("creato " + i);
		this.number = i;
		this.state = 0;
		this.prioritaIniziale = 7;
		this.priorita = this.prioritaIniziale;
		if (i == 6)
			this.priorita = 2;
	}
	public void changePriorita()
	{
		this.priorita--;
	}
	public void changeState(int state)
	{
		this.state = state;
	}
	public int esegui()
	{
		System.out.println("job " + this.number + " viene eseguito con priorita" + this.priorita);
		return new Random().nextInt(2);
	}
	public int getPriorita()
	{
		return this.priorita;
	}
	public int getPrioritaIniziale()
	{
		return this.prioritaIniziale;
	}
	public int getState()
	{
		return this.state;
	}
	public void resetPriorita()
	{
		this.priorita = this.prioritaIniziale;
	}
}
