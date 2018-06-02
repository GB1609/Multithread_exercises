package giovanniBrunetti161195;
import java.util.Random;
public class Cliente extends Thread
{
	private final Banca banca;
	private final ContoBancario contoBancario;
	public Cliente(final ContoBancario contoBancario, final Banca banca)
	{
		this.contoBancario = contoBancario;
		this.banca = banca;
	}
	@Override
	public void run()
	{
		final Random random = new Random();
		final int mioContoID = this.contoBancario.getIdentificativoCodice();
		final int[] contiIDs = this.banca.getContiIDs();
		while (true)
		{
			final int ammontare = random.nextInt(1000);
			Integer destinatario = mioContoID;
			do
				destinatario = contiIDs[random.nextInt(contiIDs.length)];
			while (destinatario.equals(mioContoID));
			System.out.println("Il mio [" + mioContoID + "] saldo prima del trasferimento e' di "
					+ this.banca.getSaldo(mioContoID) + " euro");
			final boolean trasferisci = this.banca.trasferisci(mioContoID, destinatario, ammontare);
			if (!trasferisci)
				System.out.println("Non sono [" + mioContoID
						+ "] riuscito a trasferire i soldi perche' non ne ho abbastanza");
			System.out.println("Il mio [" + mioContoID + "] saldo dopo il trasferimento e' di "
					+ this.banca.getSaldo(mioContoID) + " euro");
			try
			{
				Thread.sleep(random.nextInt(1000));
			}
			catch (final InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
