package sedie;
public class Display extends Thread
{
	public static void main(String[] args)
	{
		InsiemiSedie is = new InsiemiSedie(10);
		Partecipante partecipanti[] = new Partecipante[is.getNumSedie() + 1];
		Display display = new Display(is, partecipanti);
		display.start();
		for (int i = 0; i <= is.getNumSedie(); i++)
		{
			partecipanti[i] = new Partecipante(is);
			partecipanti[i].start();
		}
	}
	private InsiemiSedie insiemiSedie;
	private boolean stampastampa = true;
	private Partecipante[] partecipanti;
	public Display(InsiemiSedie is, Partecipante[] partecipanti)
	{
		this.insiemiSedie = is;
		this.partecipanti = partecipanti;
	}
	@Override
	public void run()
	{
		while (this.stampastampa)
		{
			try
			{
				sleep(500);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			this.insiemiSedie.stampaSedie();
			if (!this.insiemiSedie.getFree())
			{
				this.insiemiSedie.stampaSedie();
				this.stampastampa = false;
				this.stampaPartecipanti();
			}
		}
	}
	public void stampaPartecipanti()
	{
		for (Partecipante element : this.partecipanti)
			if (element.getSediaDoveSonoSeduto() >= 0)
				System.out.println("io, partecipante " + element.getName()
						+ " mi sono seduto alla sedia " + element.getSediaDoveSonoSeduto());
			else
				System.out.println("io, partecipante " + element.getName() + " sono rimasto alzato :(");
	}
}
