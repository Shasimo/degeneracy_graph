import java.util.List;
import java.util.ArrayList;

public class ChromaNumber {
    private static List< Integer> couleurParSommet = new ArrayList<Integer>();
    private static List< Integer> ordreSommets = new ArrayList<Integer>();
    private static int couleurmax = -1;
    private static int degenerescence;
    private static int chromaticNumber = -1;

    private void init(Graph G) {
    Degenerescence dg = new Degenerescence();
    dg.DegenerescenceEtCores(G, true);
    degenerescence = dg.getDegenerescence(G);
    List <Integer> list = dg.getListeCores();
    for(int i: list) {
        couleurParSommet.add(-1);
        ordreSommets.add(i);
    }

    }

    private static int obtenirCouleur(List<Integer> listeCouleurVoisin) {
        int couleur = 0;
        while(listeCouleurVoisin.indexOf(couleur) != -1) {
            couleur++;
        }
        couleurmax = Math.max(couleur, couleurmax);
        return couleur;
    }

    public void iterate() {
        StdOut.println("Commencons...");
        for(int i = 0; i < couleurParSommet.size(); i++) {
            StdOut.println("Sommet numero " + i + " possede la couleur:" + couleurParSommet.get(i));
        }
    }

    private static void procedureNC(Graph G) {
        List <Integer> listeCouleurVoisin = new ArrayList <Integer> ();

        for(int j = ordreSommets.size() - 1; j >= 0; j--) {
            int sommet = ordreSommets.get(j);

            for(int voisin: G.adj(sommet)) {
                int couleur_voisin = couleurParSommet.get(voisin);
                if(( couleur_voisin != -1) && (listeCouleurVoisin.indexOf(couleur_voisin) == -1)) {//Ajoute les différentes couleur des voisins à la liste
                    listeCouleurVoisin.add(couleur_voisin);
                }
            }

            int notre_couleur = obtenirCouleur(listeCouleurVoisin);
            couleurParSommet.set(sommet, notre_couleur);
            listeCouleurVoisin.clear();
        }

    }


    public void obtenirNombreChromatique(Graph G) {
        init(G);
        StdOut.println();
        StdOut.println("----------------------------------------------");
        StdOut.println();
        long debut = System.currentTimeMillis();
        procedureNC(G);
        long fin = System.currentTimeMillis();
        chromaticNumber = couleurmax;
        StdOut.println("Le nombre chromatique est: " + chromaticNumber + ". La degeneresence est: " + degenerescence);
        StdOut.println("Temps pris pour calculer le nombre chromatique en milisecondes: " + (fin - debut));
    }
}