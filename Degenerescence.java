import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Degenerescence {
    static private int Degenerescence = 0;
    static private int degreeMax = 0;
    static private List< Integer > sommetParDegree = new ArrayList();
    static private List< Integer >  listeCores = new ArrayList<>();
    static private List< Integer > emplacementSommets = new ArrayList<>();
    static private List< Integer > nbrDegree = new ArrayList<>();
    static private List < Integer > degreeParSommet = new ArrayList<>();

    private static void init(Graph graphe, boolean copyOrder) {

        for (int sommet = 0; sommet < graphe.V(); sommet++) {//Ajoute le degrée de chaque sommet
            listeCores.add(-1);
            emplacementSommets.add(-1);
            degreeParSommet.add(graphe.degree(sommet));
            if (graphe.degree(sommet) > degreeMax) {
                degreeMax = graphe.degree(sommet);
            }
        }

        for(int i = 0; i <= degreeMax; i++) {nbrDegree.add(0);} //initilise

        for(int j = 0; j < graphe.V(); j++) {
            int degAchanger =  graphe.degree(j);
            nbrDegree.set(degAchanger, nbrDegree.get(degAchanger) + 1);} //Calcule combien de sommets de degrée x il y a

        
        int start = 0;
        int num;    
        for(int d = 0; d <= degreeMax; d++) { // determine les positions de la premiere occurence d'un nouveau degree
            num = nbrDegree.get(d);
            nbrDegree.set(d, start);
            start=num+start;
        }


        for(int sommet = 0; sommet < graphe.V(); sommet++) {//On place les sommets du graphe dans listeCores au bon endroit grâce à nbrDegree. On sauvegarde l'emplacement du sommet dans emplacementSommets. 
            emplacementSommets.set(sommet, nbrDegree.get(graphe.degree(sommet)));
            listeCores.set(emplacementSommets.get(sommet), sommet);
            nbrDegree.set(graphe.degree(sommet), (nbrDegree.get(graphe.degree(sommet)) + 1));
        }

        for(int d = degreeMax; d > 1; d--) {//Les degrees ont ete modifies par la fonction precedente, ils sont remis à la bonne place en les décalants d'une place vers la droite.
            nbrDegree.set(d, nbrDegree.get(d-1));
        }
        nbrDegree.set(0, 1);

        if(copyOrder) {
            sommetParDegree = listeCores;
        }

    }

    private static void calculeCore(Graph graphe) {

        for(int i = 1; i < graphe.V(); i++) {
            int sommet = listeCores.get(i);

            for(int voisin: graphe.adj(sommet)) {
                if(degreeParSommet.get(voisin) > degreeParSommet.get(sommet)) {

                    int degreeVoisin = degreeParSommet.get(voisin);
                    int posVoisin = emplacementSommets.get(voisin);
                    int posCoreMAJ = nbrDegree.get(degreeVoisin);
                    int autreSommet = listeCores.get(posCoreMAJ);

                    if (voisin != autreSommet) {//Met à jour dynamiquement le voisin dans la liste des Cores 

                    Collections.swap(emplacementSommets, voisin, autreSommet);
                    Collections.swap(listeCores, posVoisin, posCoreMAJ);                 
                    }
                    nbrDegree.set(degreeVoisin, (nbrDegree.get(degreeVoisin)+1)); //Met à jour la table des degrées
                    degreeParSommet.set(voisin, degreeParSommet.get(voisin)-1); //Met à jour le degrée du voisin
                }
            }
        }

    }

    public List<Integer> getListeCores() {
        return listeCores;
    }

    public List<Integer> getSommetParDegree() {
        return sommetParDegree;
    }

    public int getProfondeur(Graph G, int sommet) {
        return degreeParSommet.get(listeCores.get(emplacementSommets.get(sommet)));
    }

    public int getDegenerescence(Graph G) {

        return degreeParSommet.get(listeCores.get(G.V()-1));
    }

    public void DegenerescenceEtCores(Graph G, boolean copyList) {
        StdOut.println("On commence le comptage de la degenenrence");
        long debut2 = System.currentTimeMillis();
        init(G, copyList);
        calculeCore(G);
        long fin2 = System.currentTimeMillis();

        StdOut.println("Temps d'execution en milli secondes: " + (fin2 - debut2));
        Degenerescence = getDegenerescence(G);

        StdOut.println("Degeneresence: " + Degenerescence);
        StdOut.println(" ");

    }

    
}
