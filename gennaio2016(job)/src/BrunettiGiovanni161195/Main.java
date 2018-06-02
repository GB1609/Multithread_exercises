package BrunettiGiovanni161195;
public class Main
{
	public static void main(String[] args)
	{
		Scheduler gb = new Scheduler();
		for (int i = 0; i < (/* new Random().nextInt(40) + */ 6); i++)
			gb.addJob(new Job(i));
		gb.start();
	}
}
