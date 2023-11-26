package simulator.util.datagenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VesselnameGenerator {
	
	static final List<String> names = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
	{
	    add("BigCargo");
	    add("Dumbo");
	    add("Dickie");
	    add("FishersFritze");
	    add("Tim-Tuna");
	    add("Fishly");
	    add("FlotteKarotte");
	    add("Titanic");
	    add("FerryTail");
	    add("Sailermoon");
	    add("DrunkenSailor");
	    add("SteveSailor");
	    add("Tuggy");
	    add("SeaFlorian");
	    add("BigContainer");
	    add("Bertha");
	    add("HeinBloed");
	    add("KaptainBlaubaer");
	    add("Martha");
	    add("Benjamin");
	    add("Norge");
	    add("Kristian");
	    add("Sverige");
	    add("OrientExpress");
	    add("Roberto");
	    add("El Martino");
	    add("Smutje");
	    add("SegelSiegfried");
	    add("Partyschiffchen");
	    add("Albatross");
	    add("Bismarck");
	    add("Columbus");
	    add("Dynamic Wave");
	    add("Eisvogel");
	    add("Frische Briese");
	    add("Gugelhupf");
	    add("Haubentaucher");
	    add("Iltis");
	    add("James Boot");
	    add("Klabautermann");
	    add("SchnelleLibelle");
	    add("Maracuja");
	    add("Nautilus");
	    add("Ora et labora");
	    add("Pfeffermuehle");
	    add("Quietscheentchen");
	    add("RaspberryBiBoot");
	    add("Santa Maria");
	    add("Treibholz");
	    add("UnsinkBar");
	    add("Vogelfrei");
	    add("Wasserfloh");
	    add("Xanthippe");
	    add("Ypsilon");
	    add("ZuSpaet");
	}};

	public synchronized static String next() {
		int rnd = ThreadLocalRandom.current().nextInt(0, names.size()-1);
		return names.get(rnd);
	}
	
}
