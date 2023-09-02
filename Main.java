import java.util.*;

class Main {

    private static void menu(Graph G) {
        Scanner clavier = new Scanner(System.in);
        System.out.println("");
        System.out.println("Que voulez vous faire ?");
        int condition = 3;
        while(condition > 2) {
            System.out.println("     Pour calculer la dégénérescence du graphe et la profondeur d'un sommet tapez 1. Pour afficher la coloration, tapez 2. Pour quitter le programme, tapez 0");
            condition = clavier.nextInt();
        }
        switch(condition) {
            case 1:
            Degenerescence dg = new Degenerescence();        
            dg.DegenerescenceEtCores(G, false);
            System.out.println("Si vous voulez afficher la profondeur d'un sommet tapez le numero du sommet. Tapez -1 dans le cas contraire");
            int sommet = clavier.nextInt();
            if(sommet >= 0) {
                int prof = dg.getProfondeur(G, sommet);
                System.out.println("La profondeur du sommet " + sommet + " est de " + prof);
            }
            break;
            case 2:
            ChromaNumber cn = new ChromaNumber();
            cn.obtenirNombreChromatique(G);
            System.out.println("Si vous voulez afficher toutes les couleurs par sommet tapez 0");
            int iterer = clavier.nextInt();
            if(iterer == 0) {
                cn.iterate();
            }
            break;
        }

    }

    private static void initGraphe() {
        /*
        Exemple de graphe: "https://algs4.cs.princeton.edu/41graph/largeG.txt";
        */
        Scanner clavier = new Scanner(System.in);
        System.out.print("Entrez le fichier de l'algorithme: ");
        String graphe = clavier.nextLine();
        System.out.println("Veuillez patienter... Initilialisation du graphe");
        long debut = System.currentTimeMillis();
        In in = new In(graphe);
        Graph G = new Graph(in);
        long fin = System.currentTimeMillis();
        long temps = fin - debut;
        System.out.println("Initialisation réussie! Temps en milisecondes pris pour l'initialisation: " + temps);
        menu(G);
    }


    public static void main(String[] args) {
        initGraphe();
    }
}