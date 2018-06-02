package giovanniBrunetti161195;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
public class Main
{
	private final static int NUMERO_DI_CLIENTI = 10;
	public static void main(final String[] args)
	{
		final Banca banca = new Banca();
		final List<Cliente> clienti = new LinkedList<>();
		for (int i = 0; i < Main.NUMERO_DI_CLIENTI; i++)
		{
			final ContoBancario contoBancario = new ContoBancario(i, new Random().nextInt(10000));
			banca.aggiuniConto(contoBancario);
			clienti.add(new Cliente(contoBancario, banca));
		}
		for (final Cliente cliente : clienti)
			cliente.start();
	}
}
