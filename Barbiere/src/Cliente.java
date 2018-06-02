public class Cliente extends Thread
{
	Negozio negozio;
	int id;
	public Cliente()
	{
	}
	public Cliente(Negozio negozio)
	{
		this.negozio = negozio;
	}
	@Override
	public void run()
	{
		if (this.negozio.sieditiNelSalone(this))
			while (this.negozio.clienteStaTagliandoICapelli(this.id))
			{
			}
		else
			System.out.println("me ne vado");
	}
}
