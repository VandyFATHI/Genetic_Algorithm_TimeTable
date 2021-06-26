package test;

/*
@author Vandy FATHI
 @title: Genetic Algorithm, Algorithme Génétique
 @Subject: Task shceduling for Timetable
 */

import genes.*;

public class Main {

    public static void main(String[] args) {

        /*------- Initialisation de la population initiale------------ */
        EmploiDuTemps edt1 = new EmploiDuTemps();
        edt1.getJour(0).setCours(1, "Math");
        edt1.getJour(4).setCours(2, "Phys");
        edt1.getJour(2).setCours(1, "Angl");
        edt1.getJour(3).setCours(6, "Hist");

        EmploiDuTemps edt2 = new EmploiDuTemps();
        edt2.getJour(3).setCours(6, "Math");
        edt2.getJour(0).setCours(1, "Phys");
        edt2.getJour(4).setCours(2, "Angl");
        edt2.getJour(1).setCours(1, "Hist");

        EmploiDuTemps edt3 = new EmploiDuTemps();
        edt3.getJour(3).setCours(6, "Math");
        edt3.getJour(2).setCours(7, "Phys");
        edt3.getJour(2).setCours(4, "Angl");
        edt3.getJour(3).setCours(1, "Hist");

        EmploiDuTemps edt4 = new EmploiDuTemps();
        edt4.getJour(3).setCours(6, "Math");
        edt4.getJour(4).setCours(2, "Phys");
        edt4.getJour(4).setCours(6, "Angl");
        edt4.getJour(2).setCours(1, "Hist");

        EmploiDuTemps edt5 = new EmploiDuTemps();
        edt5.getJour(0).setCours(1, "Math");
        edt5.getJour(0).setCours(4, "Phys");
        edt5.getJour(1).setCours(3, "Angl");
        edt5.getJour(1).setCours(1, "Hist");

        EmploiDuTemps edt6 = new EmploiDuTemps();
        edt6.getJour(3).setCours(1, "Math");
        edt6.getJour(4).setCours(2, "Phys");
        edt6.getJour(4).setCours(6, "Angl");
        edt6.getJour(3).setCours(6, "Hist");

        EmploiDuTemps edt7 = new EmploiDuTemps();
        edt7.getJour(3).setCours(1, "Math");
        edt7.getJour(0).setCours(4, "Phys");
        edt7.getJour(1).setCours(3, "Angl");
        edt7.getJour(3).setCours(3, "Hist");

        EmploiDuTemps edt8 = new EmploiDuTemps();
        edt8.getJour(1).setCours(6, "Math");
        edt8.getJour(0).setCours(4, "Phys");
        edt8.getJour(2).setCours(1, "Angl");
        edt8.getJour(1).setCours(1, "Hist");

        /*------- Affichage des emplois du temps ainsi que leur fitness-------*/
        System.out.println(edt1);
        System.out.println("edt1 fitness = " + edt1.fitness());
        System.out.println(edt2);
        System.out.println("edt2 fitness = " + edt2.fitness());
        System.out.println(edt3);
        System.out.println("edt3 fitness = " + edt3.fitness());
        System.out.println(edt4);
        System.out.println("edt4 fitness = " + edt4.fitness());
        System.out.println(edt5);
        System.out.println("edt5 fitness = " + edt5.fitness());
        System.out.println(edt6);
        System.out.println("edt6 fitness = " + edt6.fitness());
        System.out.println(edt7);
        System.out.println("edt7 fitness = " + edt7.fitness());
        System.out.println(edt8);
        System.out.println("edt8 fitness = " + edt8.fitness());

        EmploiDuTemps[] edts = {edt1,edt2,edt3,edt4,edt5,edt6,edt7,edt8};
        Population solutionGeneration = new Population(edts);
        Population solutionFitness = new Population(edts);


    // on peut changer generation pour des résultats plus ou moins pertinents
        int generation = 100;
        AlgoGeneticGeneration(solutionGeneration, generation );
        System.out.println("\n--------------SOLUTION "+generation+" GENERATION------------");
        System.out.println(solutionGeneration.getEdt(0));
        System.out.println("fitness = " + solutionGeneration.getEdt(0).fitness());
        System.out.println("\n\n");

        int fitness = 115;
        AlgoGeneticFitness(solutionFitness, fitness );
        System.out.println("--------------SOLUTION FITNESS = "+ fitness+"------------");
        System.out.println(solutionFitness.getEdt(0));
        System.out.println("fitness = " + solutionFitness.getEdt(0).fitness());

    }

    private static void AlgoGeneticGeneration(Population solutions, int generation){
        solutions.SortByFitness();
        int g=0;
        while (g < generation){
            System.out.println("Generation " + g +"...");
            solutions.Selection();
            solutions.CrossOver();
            solutions.Mutation();
            solutions.SortByFitness();
            g++;
        }
    }

    private static void AlgoGeneticFitness(Population solutions, int fitness){
        solutions.SortByFitness();
        int g=0;
        System.out.println("Generation " + g +"...");
        while (fitness< solutions.getEdt(0).fitness()){
            System.out.println("Generation " + g +"...");
            solutions.Selection();
            solutions.CrossOver();
            solutions.Mutation();
            solutions.SortByFitness();
            g++;
        }
    }
}
