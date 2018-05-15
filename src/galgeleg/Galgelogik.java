    package galgeleg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import javax.jws.WebService;

@WebService(endpointInterface = "galgeleg.GalgeI")
public class Galgelogik implements GalgeI {
  ArrayList<String> muligeOrd = new ArrayList<String>();
  private String ordet;
  private ArrayList<String> brugteBogstaver = new ArrayList<String>();
  private String synligtOrd, logStatus;
  private int antalForkerteBogstaver, antalLiv; //score = 0, streak = 0;
  private boolean spilletErVundet;
  private boolean spilletErTabt;


  public ArrayList<String> getBrugteBogstaver() {
    return brugteBogstaver;
  }

  public String getSynligtOrd() {
    return synligtOrd;
  }

  public String getOrdet() {
    return ordet;
  }

  public int getAntalForkerteBogstaver() {
    return antalForkerteBogstaver;
  }

  public boolean erSpilletVundet() {
    return spilletErVundet;
  }

  public boolean erSpilletTabt() {
    return spilletErTabt;
  }
  
  public int getAntalLiv() {
      return antalLiv;
    }
  

 
  public Galgelogik() throws RemoteException {
//    muligeOrd.add("bil");
//    muligeOrd.add("computer");
//    muligeOrd.add("programmering");
//    muligeOrd.add("motorvej");
//    muligeOrd.add("busrute");
//    muligeOrd.add("gangsti");
//    muligeOrd.add("skovsnegl");
//    muligeOrd.add("solsort");
//    muligeOrd.add("seksten");
//    muligeOrd.add("sytten");
//    nulstil();
      try {
          hentOrd();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  public void nulstil() {
    brugteBogstaver.clear();
    antalLiv = 6;
    antalForkerteBogstaver = 0;
    spilletErVundet = false;
    spilletErTabt = false;
    ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
    opdaterSynligtOrd();
  }


  private void opdaterSynligtOrd() {
    synligtOrd = "";
    spilletErVundet = true;
    for (int n = 0; n < ordet.length(); n++) {
      String bogstav = ordet.substring(n, n + 1);
      if (brugteBogstaver.contains(bogstav)) {
        synligtOrd = synligtOrd + bogstav;
      } else {
        synligtOrd = synligtOrd + "*";
        spilletErVundet = false;
      }
    }
  }

  public void gætBogstav(String bogstav) {
       if (bogstav.length() != 1) return;

        System.out.println("Der gættes på bogstavet: " + bogstav);

        if (brugteBogstaver.contains(bogstav))return;

        if (spilletErVundet || spilletErTabt) return;

        if (ordet.contains(bogstav)) {
            System.out.println("Bogstavet var korrekt: " + bogstav);
            //streak++;

//            if (streak == 2) {
//                score = score + 100;
//            }
//            else if (streak == 3) {
//                score = score + 150;
//            }
//            else {
//                score = score + 50;
//            }
        }
        else {
            // Vi gættede på et bogstav der ikke var i ordet.
            System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
//            streak = 0;
//            score = score - 50;

            antalLiv = antalLiv - 1;
            antalForkerteBogstaver = antalForkerteBogstaver + 1;

            if (antalLiv == 0) {
                spilletErTabt = true;
            }
        }
        brugteBogstaver.add(bogstav);

        opdaterSynligtOrd();
  }

  public String logStatus() {
    System.out.println("---------- ");
    System.out.println("- ordet (skjult) = " + ordet);
    System.out.println("- synligtOrd = " + synligtOrd);
    System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
    System.out.println("- brugeBogstaver = " + brugteBogstaver);
    if (spilletErTabt) System.out.println("- SPILLET ER TABT");
    if (spilletErVundet) System.out.println("- SPILLET ER VUNDET");
    System.out.println("---------- ");
    logStatus = "-----------------------------------------------"
            + " \n Ord: " + synligtOrd
            + "\n Forkerte Bogstaver: " + antalForkerteBogstaver
            + "\n Brugte bogstaver: " + brugteBogstaver 
            + "\n -----------------------------------------------";
    return logStatus;
  }

  public static String hentUrl(String url) throws IOException {
    System.out.println("Henter data fra " + url);
    BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
    StringBuilder sb = new StringBuilder();
    String linje = br.readLine();
    while (linje != null) {
      sb.append(linje + "\n");
      linje = br.readLine();
    }
    return sb.toString();
  }


  public void hentOrd() throws Exception {
    String data = hentUrl("http://www.foxnews.com/");
    //System.out.println("data = " + data);

    data = data.substring(data.indexOf("<body")). // fjern headere
            replaceAll("<.+?>", " ").toLowerCase(). // fjern tags
            replaceAll("&#198;", "æ"). // erstat HTML-tegn
            replaceAll("&#230;", "æ"). // erstat HTML-tegn
            replaceAll("&#216;", "ø"). // erstat HTML-tegn
            replaceAll("&#248;", "ø"). // erstat HTML-tegn
            replaceAll("&oslash;", "ø"). // erstat HTML-tegn
            replaceAll("&#229;", "å"). // erstat HTML-tegn
            replaceAll("[^a-zæøå]", " "). // fjern tegn der ikke er bogstaver
            replaceAll(" [a-zæøå] "," "). // fjern 1-bogstavsord
            replaceAll(" [a-zæøå][a-zæøå] "," "); // fjern 2-bogstavsord

    System.out.println("data = " + data);
    System.out.println("data = " + Arrays.asList(data.split("\\s+")));
    muligeOrd.clear();
    muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

    System.out.println("muligeOrd = " + muligeOrd);
    nulstil();
  }
}
