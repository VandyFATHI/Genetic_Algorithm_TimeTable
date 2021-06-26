package genes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Population {

    private EmploiDuTemps[] solution;
    // le premier string représente une matière, à cette matière est lié une autre hashMap
    // Cet autre HashMap indique les jours et les créneaux autorisé pour la matière correspondante.
    /*Voici une facon de le voir
    [ matière -> [ jours -> créneaux] ]
    */
    private  final HashMap<String, HashMap<String, String>> horaireAuto = new HashMap<>();
    /*---Initialisation des horaires disponibles----*/
    {
        // ces Hashtable associeront les créneaux d'horaire pour une journée donnée.
        // Le premiet String représente la journée et le deuxième le(s) créneau(x) disponible
        // pour cette matière
        HashMap<String, String> creneauxMath = new HashMap<>();
        HashMap<String, String> creneauxPhys = new HashMap<>();
        HashMap<String, String> creneauxAngl = new HashMap<>();
        HashMap<String, String> creneauxHist = new HashMap<>();

        // Instanciation des créneaux aux matières correspondantes.
        String creneauMathLundi = "1";
        String creneauMathMardi = "6";
        String creneauMathjeudi = "1#6";
        creneauxMath.put("0",creneauMathLundi);
        creneauxMath.put("1",creneauMathMardi );
        creneauxMath.put("3",creneauMathjeudi);

        String creneauPhysLundi = "1#4";
        String creneauPhysMercredi = "7";
        String creneauPhysVendredi = "2";
        creneauxPhys.put("0",creneauPhysLundi);
        creneauxPhys.put("2",creneauPhysMercredi );
        creneauxPhys.put("4",creneauPhysVendredi);

        String creneauAnglMardi = "3";
        String creneauAnglMercredi = "1#4";
        String creneauAnglVendredi = "2#6";
        creneauxAngl.put("1",creneauAnglMardi);
        creneauxAngl.put("2",creneauAnglMercredi);
        creneauxAngl.put("4",creneauAnglVendredi);

        String creneauHistLundi = "6";
        String creneauHistMardi = "1";
        String creneauHistMercredi = "1";
        String creneauHistJeudi = "3#6";
        creneauxHist.put("0",creneauHistLundi);
        creneauxHist.put("1",creneauHistMardi);
        creneauxHist.put("2",creneauHistMercredi);
        creneauxHist.put("3",creneauHistJeudi);

        horaireAuto.put("Math",creneauxMath);
        horaireAuto.put("Phys",creneauxPhys);
        horaireAuto.put("Angl",creneauxAngl);
        horaireAuto.put("Hist",creneauxHist);

    }


    public Population(EmploiDuTemps[] s) {
        this.solution = s;
    }

    public EmploiDuTemps getEdt(int i) {
        return this.solution[i];
    }


    public String toString(){
        int i =0;
        for (EmploiDuTemps e: this.solution) {
            System.out.print(e);
            System.out.println("tableau "+ i);
            System.out.println(" Fitnesss = " + e.fitness() + "\n");
            i++;
        }
        return "----------------------";
    }

    /*
    Cette fonction fait l'union entre deux solutions. Si cette union donne une solution non valide, elle renvoie un emploi du temps vide
     */
    private EmploiDuTemps CrossOverEDT(EmploiDuTemps edt1, EmploiDuTemps edt2){
        EmploiDuTemps edtReturned = new EmploiDuTemps();
        edtReturned.setJour(0, edt1.getJour(0));
        edtReturned.setJour(1, edt1.getJour(1));
        edtReturned.setJour(2, edt2.getJour(2));
        edtReturned.setJour(3, edt2.getJour(3));
        edtReturned.setJour(4, edt2.getJour(4));

        if(edtReturned.edtNonValable()) return new EmploiDuTemps();
        return edtReturned;
    }

    /*
    Cette fonction effectue un l'étape du CrossOver dans l'algorithme génétique.
    Elle va combiner des solutions enesmbles, si ces solutions sont valides, elles sont gardées.
     */
    public void CrossOver(){
        // ind= indice qui nous permettra de combiner un individu fixe avec d'autres.
        int ind = 0;

        // indSolution est l'indice qui nous permettra de remplir le tableau avec des emploi du temps valide
        int indSolution = 4;

        //Si indSolution == 8, les 4 dernieres solutions restantes ont été défini.
        while(indSolution<8 ) {

            //Quand ind=3, les individus parents ont été combinés l'un de l'autre
            if (ind <= 3) {
                //on s'arrete à 5 sinon on combine des "parents" avec des "enfants"
                // C'est choix de 3 et 5 fonctionne que si on choisit 4 solutions parmis 8.
                //Ce choix de 4 solutions parmis 8 a été prix arbitrairement
                for (int i = 3; i < 5; i++) {
                    // Si ind>=i-2, on refait des combinaisons inutilement
                    if(ind >= i-2) continue;

                    // L'emploi du temps a pour premier moitié this.solution[int], et deuxième moitié this.solution[i-2]
                    this.solution[indSolution] = CrossOverEDT(this.solution[ind], this.solution[i - 2]);
                    if (!this.solution[indSolution].edtNonValable()) {
                        // Si la solution est valide, on ajouteras les solutions suivantes en passant à l'indice suivant
                        // sinon on réitère les CrossOvers
                        indSolution++;
                        break;
                    }

                    //Ici on inverse les moitiés
                    this.solution[indSolution] = CrossOverEDT(this.solution[i-2], this.solution[ind]);
                    if (!this.solution[indSolution].edtNonValable()) {
                        // Si la solution est valide, on ajouteras les solutions suivantes en passant à l'indice suivant
                        // sinon on réitère les CrossOvers
                        indSolution++;
                        break;
                    }
                }
                ind++;
            }else{
                // Dans cette situation, les combinaisons entre les parents ne donnent pas assez d'enfants valides.
                // On y met des invidues aléatoire.
                this.solution[indSolution] = RandomEmploiDuTemps();
                indSolution++;
            }
        }
        }

        /*
        Cette fonction retourne un emploi du temps aléatoire.
        Il est important d'miaginer la relation [ matière -> [ jours -> créneaux] ]
        ]
         */
    private EmploiDuTemps RandomEmploiDuTemps() {
        Random r = new Random();
        HashMap<String, String> jo;
        Object[] jour;
        String jourChoisis;
        String[] creneauxDeLaMatiere;
        String creneauxChoisis;
        EmploiDuTemps edtr = new EmploiDuTemps();

        // Tant que l'emploi de temps retournée n'est pas valide on réitère l'opération.
        while(edtr.edtNonValable()){
            edtr = new EmploiDuTemps();
            // On prend toutes les matières
            for (String j : this.horaireAuto.keySet()) {

                // Jo est le hashMap [ jours -> creneaux ]
                jo = horaireAuto.get(j);

                //On met dans un  tableau les jours
                jour = jo.keySet().toArray();

                //On choisit aléatoirement un des jours disponibles
                jourChoisis = (String) jour[r.nextInt(jour.length)];

                //Si il y a plusieurs créneaux pour une même journée on les sépare
                creneauxDeLaMatiere = jo.get(jourChoisis).split("#");

                //On choisit aléatoirement un créneaux dans une journée
                creneauxChoisis = creneauxDeLaMatiere[r.nextInt(creneauxDeLaMatiere.length)];
                //edtr est l'emploi du temps aléatoirement retourné
                edtr.getJour(Integer.parseInt(jourChoisis)).setCours(Integer.parseInt(creneauxChoisis), j);
            }
        }
        return edtr;
    }


    /*
    Cette fonction modifie aléatoirement l'emplacement d'une matière donnée.
     */
    private void Randomchoice(int edt, String matiere) {
        Random r = new Random();
        // On récupère les jours les créneaux disponible pour la matiere donnée
        HashMap<String, String> jourCreneau = horaireAuto.get(matiere);
        String jourChoisis;
        String[] creneauxDeLaMatiere;
        String creneauxChoisis;
        boolean quit = false;

        //les clés de jourCreneau sont les jours de cours, les valeurs sont les creneaux
        Object[] jour = jourCreneau.keySet().toArray();
        while (!quit) {
            //choisis un jour aléatoirement
            jourChoisis = (String) jour[r.nextInt(jour.length)];
            creneauxDeLaMatiere = jourCreneau.get(jourChoisis).split("#");
            creneauxChoisis = creneauxDeLaMatiere[r.nextInt(creneauxDeLaMatiere.length)];
            // On considère qu'une matière ne peut pas prendre la place d'une autre matière. Si le nouveau creneau est déjà pris
            // On prend un autre emplacement
            if (this.solution[edt].getJour(Integer.parseInt(jourChoisis)).getCours(Integer.parseInt(creneauxChoisis)).equals("")) {
                this.solution[edt].getJour(Integer.parseInt(jourChoisis)).setCours(Integer.parseInt(creneauxChoisis), matiere);
                quit = true;
            }

        }
    }

    /*
    Cette fonction effectue l'étape de mutation dans l'algorithme génétique;
    On considère une mutation le changement de créneau d'une matière
     */
    public void Mutation(){
        Random r = new Random();
        int edt;
        int jour;
        String matiere;
        ArrayList<Integer> creneaux = new ArrayList<>();
        int creneauChoisis;
        edt = r.nextInt(this.solution.length);
        jour = r.nextInt(5);
        // Si la journée sélectionnée est vide, aucune modification se fait
        if(!this.solution[edt].getJour(jour).JourVide()){
            for (int i = 1; i <9 ; i++) {
                //Si l'emplacement n'est pas vide, alors il y a une matière. Mais il peut y avoir plusieurs cours dand une journée
                //On stocke les créneaux
                if(!this.solution[edt].getJour(jour).getCours(i).equals("")){
                    creneaux.add(i);
                }
            }
            //On choisit aléatoirement un des deux créneaux
            creneauChoisis = creneaux.get(r.nextInt(creneaux.size()));
            //On récupère la matière situé à ce créneaux
            matiere = this.solution[edt].getJour(jour).getCours(creneauChoisis);
            //On vide l'emplacement pour éviter qu'il y ait plusieurs fois la même matière
            this.solution[edt].getJour(jour).setCours(creneauChoisis, "");
            //On effectue les changements aléatoirement
            Randomchoice(edt, matiere);
        }
    }


    /*
    Cette fonction tri les différents emplois du temps (les solutions) de la fitness la plus élevée
    à la fitness la moins élevée.
     */
    public void SortByFitness(){
        EmploiDuTemps solutionTmp;
        EmploiDuTemps[] solutionr = this.solution;
        for (int i = 0; i < solutionr.length; i++) {
            while(i>0 && solutionr[i].fitness()<solutionr[i-1].fitness()){
                solutionTmp = solutionr[i];
                solutionr[i] = solutionr[i-1];
                solutionr[i-1] = solutionTmp;
                i--;
            }
        }
        this.solution = solutionr;
    }

/*
Cette fonction sélectionne les meilleurs individus et les individus valides.
Il faut partir du principe que l'on choisit 4 solutions.
 */
    public void Selection(){
        EmploiDuTemps edtr;
        //Ce compteur sert à sélectionner les 4 individus
        int ct=0;
        for (int i = 0; i < this.solution.length; i++) {
            if(!this.solution[i].edtNonValable()){
                edtr = this.solution[i];
                this.solution[ct] = edtr;
                ct++;
            }
            if(ct==4) return;
        }
    }


}
