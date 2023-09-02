import java.util.ArrayList;
import java.math.BigDecimal;
import java.lang.Math;
import java.util.List;

class DegenerancyAlgorithm {
    static private List< Integer >DejaVisites  = new ArrayList<>();
    static private List< Integer > nbrVoisinsParSommet = new ArrayList();
    static private List< Integer > emplacementSommet = new ArrayList();
    static private List< ArrayList < Integer >>  tableSommetsParDegree = new ArrayList<ArrayList <Integer>> ();

    private static void init(Graph graph) {
        for (int sommet = 0; sommet < graph.V(); sommet++) {
            
            nbrVoisinsParSommet.add(graph.degree(sommet));

            if(graph.degree(sommet) >= tableSommetsParDegree.size()) {
                for(int i = tableSommetsParDegree.size(); i <= graph.degree(sommet); i++) {
                    ArrayList<Integer> SommetsDeDegreeX = new ArrayList<Integer>();
                    tableSommetsParDegree.add(SommetsDeDegreeX);
                }
            }
            DejaVisites.add(-1);
            tableSommetsParDegree.get(graph.degree(sommet)).add(sommet);
            int taille = tableSommetsParDegree.get(graph.degree(sommet)).size() -1;
            int index = tableSommetsParDegree.get(graph.degree(sommet)).indexOf(sommet);
            emplacementSommet.add(taille);
        }  
        
    }

    private static void majLiaison(int sommet, Graph G) {
        for (int w: G.adj(sommet)) {

            if(DejaVisites.get(w) == -1) {
                int nouveauDegree = nbrVoisinsParSommet.get(w) - 1;
                nbrVoisinsParSommet.set(w, Integer.valueOf(nouveauDegree));
                int indiceAEnlever = tableSommetsParDegree.get(nbrVoisinsParSommet.get(w)).indexOf(nouveauDegree);
                tableSommetsParDegree.get(nouveauDegree).remove(indiceAEnlever);
                tableSommetsParDegree.get((nouveauDegree - 1)).add(sommet);
            }
        }
    }

    private static void majLiaisons(int sommet, int degree) {
        int indiceAEnlever = tableSommetsParDegree.get(degree+1).indexOf(sommet);
        tableSommetsParDegree.get(degree+1).remove(indiceAEnlever);
        tableSommetsParDegree.get(degree).add(sommet);

    }

    private static void obtenirProgresEnPourcent(int a, int b) {
        BigDecimal pourcent;
        BigDecimal d1 = new BigDecimal(a);
        BigDecimal d2 = new BigDecimal(b);
        BigDecimal cent = new BigDecimal(100);
        pourcent = d1.divide(d2);
        pourcent = pourcent.multiply(cent);
        StdOut.println("Progres: " + pourcent + "%");
    }


    private static int trouverDegenerescence(Graph G) {
        int k = 0;
            for(int s = 0; s < tableSommetsParDegree.size(); s++) {
                   while(tableSommetsParDegree.get(s).size() > 0) {
                    k = Math.max(k, s);
                    int sommet = tableSommetsParDegree.get(s).remove(0);
                    DejaVisites.set(sommet, sommet);

                    for (int w: G.adj(sommet)) {

                        if(DejaVisites.get(w) == -1){
                            if(G.degree(w) >= G.degree(sommet)) {
                                int nouveauDegree = nbrVoisinsParSommet.get(w) - 1;
                            nbrVoisinsParSommet.set(w, Integer.valueOf(nouveauDegree));
                            majLiaisons(w, nouveauDegree);
                            }
                            else {
                                DejaVisites.set(w, w);
                            }
                            
                        }
                    }

            }
        }
        return k;
    }

    private static void iterate(Graph G) {
        for (int v = 0; v < G.V(); v++)
              for (int w : G.adj(v))
                 StdOut.println(v + "-" + w);
    }

    public List <Integer> getDejaVisites() {
        return DejaVisites;
    }

    public int dgAlgo(Graph G) {
        
        //iterate(G)
        init(G);
        int size = tableSommetsParDegree.size();
        StdOut.println("Taille de tableSommetsParDegree: " + size);
        long debut1 = System.currentTimeMillis();
        int degenerescence = trouverDegenerescence(G);
        long fin1 = System.currentTimeMillis();
        StdOut.println("La degenerecence de ce graphe est: " + degenerescence);
        StdOut.println("Temps:" + (fin1-debut1));

        return degenerescence;
    }
}