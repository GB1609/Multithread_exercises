package magazzino;
import java.util.ArrayList;
public class ListaMateriali
{
	private static final int quantitaMax = 100;
	private ArrayList<DettagliArticoloMagazzino> articoli;
	public ListaMateriali()
	{
		this.articoli = new ArrayList<DettagliArticoloMagazzino>();
	}
	private ArrayList<DettagliArticoloMagazzino> getArticoli()
	{
		return this.articoli;
	}
	public void add(DettagliArticoloMagazzino dam)
	{
		this.articoli.add(dam);
	}
	public DettagliArticoloMagazzino get(int i)
	{
		return this.articoli.get(i);
	}
	public int getArticolo(int cod, int quantita)
	{
		for (DettagliArticoloMagazzino a : this.articoli)
			if (a.getCodiceArticolo() == cod)
				if (quantita > a.getquantitaArticolo())
				{
					a.sottraiQuantita(a.getquantitaArticolo());
					return a.getquantitaArticolo();
				}
		return quantita;
	}
	public int getLength()
	{
		return this.articoli.size();
	}
	public int put(int cod, int quantita)
	{
		int disp = quantitaMax - this.articoli.get(cod).getquantitaArticolo();
		int inPiu = 0;
		if (quantita > disp)
		{
			inPiu = quantita - disp;
			this.articoli.get(cod).aggiungiQuantita(disp);
		}
		else
			this.articoli.get(cod).aggiungiQuantita(quantita);
		return inPiu;
	}
	public void stampaLista()
	{
		for (DettagliArticoloMagazzino a : this.articoli)
			System.out.println(a.toString());
	}
	public boolean verificaDisponibilitaOrdine(ListaMateriali l)
	{
		for (DettagliArticoloMagazzino b : l.getArticoli())
			for (DettagliArticoloMagazzino a : this.articoli)
				if (a.unCiNiSu() && (a.getCodiceArticolo() == b.getCodiceArticolo()))
					return false;
		return true;
	}
}
