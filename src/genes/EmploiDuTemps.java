package genes;


public class EmploiDuTemps {
    private Jour[] jour = new Jour[5];
    private String[][] aff = new String[9][6];

    /*
    Cette fonction s'occupe de l'affichage sur le terminal
     */
    private void affInitialisation() {
        this.aff[0][0] = "Creneaux ";
        this.aff[0][1] = "Lund";
        this.aff[0][2] = "Mard";
        this.aff[0][3] = "Merc";
        this.aff[0][4] = "Jeud";
        this.aff[0][5] = " Vend";
        this.aff[1][0] = " 9h - 10h ";
        this.aff[2][0] = "10h - 11h";
        this.aff[3][0] = "11h - 12h";
        this.aff[4][0] = "12h - 13h";
        this.aff[5][0] = "13h - 14h";
        this.aff[6][0] = "14h - 15h";
        this.aff[7][0] = "15h - 16h";
        this.aff[8][0] = "16h - 17h";

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 6; j++) {
                if (this.jour == null || this.jour[j-1].getCours(i).equals("")) this.aff[i][j] = "    ";
                else this.aff[i][j] = this.jour[j - 1].getCours(i);
            }
        }
    }

    public EmploiDuTemps() {
        for (int i = 0; i < 5; i++) {
            this.jour[i] = new Jour();
        }
        affInitialisation();
    }

    public Jour getJour(int d) {
        return this.jour[d];
    }

    public void setJour(int i, Jour j){
        this.jour[i] = j;
    }


    @Override
    public String toString() {
        String affichage = "";
        affInitialisation();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 6; j++) {
                if (this.aff[i][j] == null) affichage += "[ ]";
                else affichage = affichage.concat(" [ " + this.aff[i][j] + " ] ");
            }
            affichage += "\n";
        }
        return affichage;
    }

    /*
     * Cette fonction retourne le nombre de créneaux vides dans une journée donnée entre deux matières.
     */
    private int CreneauxVideJour(int d) {
        int ct = 0;
        int ctTmp = 0;
        for (int i = 1; i < 9; i++) {
            if (!this.jour[d].getCours(i).equals("")) {
                i++;
                while (i < 9 && this.jour[d].getCours(i).equals("")) {
                    ctTmp++;
                    i++;
                    if (i == 9 && this.jour[d].getCours(i - 1).equals("")) {
                        ctTmp = 0;
                    }
                }
                ct+=ctTmp;
                ctTmp = 0;
                i--;
            }
        }
        return ct;
    }

    /*
    Compte le nombre de jours où l'individu doit être présent en cours.
     */
    private int JoursDeCours(){
        int ct=0;
        for (Jour j: this.jour) {
            if(!j.JourVide()) ct++;
        }
        return ct;
    }



    /*
    Cette fonction retourne "true" si l'emploi du temps est non valide, c-a-d soit il manque un cours, soit il y a un cours
    au moins deux fois.
     */
    public boolean edtNonValable(){
        int Math=0, Phys=0, Angl=0, Hist=0;
        for (Jour j: this.jour) {
            for (int i = 1; i < 9; i++) {
                switch (j.getCours(i)){
                    case "Math":
                        Math++;
                        break;
                    case "Phys":
                        Phys++;
                        break;
                    case "Angl":
                        Angl++;
                        break;
                    case "Hist":
                        Hist++;
                        break;
                }
            }
        }
        // Si l'une des variables ne vaut pas 1, alors il y a un cours manquant ou un cours en trop
        return Math !=1 || Phys !=1 || Angl !=1 || Hist !=1;
    }

    /*
    Cette fonction calcule le critère de sélection d'un emploi du temps
    Nous avons arbitrairement choisis les pénalités.
    Plus le fitness est élevé, plus il y a de contraintes.
     */
    public int fitness(){
        int fitness=0;
        int PenaliteJourDeCours = 50;
        int PenaliteCreneauxVide = 5;
        for (int i = 0; i <5 ; i++) {
            fitness += CreneauxVideJour(i) * PenaliteCreneauxVide;
        }
        fitness += JoursDeCours() * PenaliteJourDeCours;
        return fitness;
    }
}
