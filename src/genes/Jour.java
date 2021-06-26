package genes;

import java.util.HashMap;

public class Jour {
    // l'integer represente le créneaux horaire de la journée et  le string represente le cour
    private HashMap<Integer, String> cour = new HashMap<>();
    private static final int CRENEAUX = 9;

    public Jour(){
        for(int i=1; i<CRENEAUX; i++){
            this.cour.put(i, "");
        }
    }

    public String getCours(int creneau){ return this.cour.get(creneau); }

    public void setCours(int creneau, String c){ this.cour.put(creneau, c); }

    public boolean JourVide(){
        for(int i=1; i<CRENEAUX; i++){
            if(!this.cour.get(i).equals("")){
                return false;
            }
        }
        return true;
    }
}
