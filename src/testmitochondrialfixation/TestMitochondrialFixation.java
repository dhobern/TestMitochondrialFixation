/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testmitochondrialfixation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Platyptilia
 */
public class TestMitochondrialFixation {
    
    private static final String fileName = "evolution.csv";
    
    private static final double advantageA = 1.05;
    private static final double advantageAA = 1.07;
    private static final double advantageB = 1.03;
    private static final double advantageBB = 1.03;
    private static final double advantage0 = 1.01;
    private static final double advantage1 = 1.009;
    private static final double advantage2 = 1.001;
    private static final double advantage3 = 1.005;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        FileWriter fw = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(TestMitochondrialFixation.class.getName()).log(Level.SEVERE, null, ex);
                }
            } fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            
            double population[] = new double[Genome.GENOME_MASK + 1];
            for (int i = 0; i <= Genome.GENOME_MASK; i++) {
                population[i] = 0;
            }   
            population[new Genome("abab0").getGenome()] = 0.5;
            population[new Genome("abaB1").getGenome()] = 0.25;
            population[new Genome("aBab2").getGenome()] = 0.125;
            population[new Genome("Abab3").getGenome()] = 0.125;
            printGenomes(bw, Genome.GENOME_MASK);
            printPopulation(bw, population, Genome.GENOME_MASK);
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 10; j++) {
                    population = breedPopulation(population, Genome.GENOME_MASK);
                }
                printPopulation(bw, population, Genome.GENOME_MASK);
            }
            
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(TestMitochondrialFixation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(TestMitochondrialFixation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void printGenomes(BufferedWriter bw, int max) throws IOException {
        for (int i = 0; i <= max; i++) {
            bw.write((new Genome(i)).toString());
            bw.write((i < max) ? "," : ",,Total 0, Total 1, Total 2, Total 3\n");
        }
    }

    private static void printPopulation(BufferedWriter bw, double[] population, int max) throws IOException {
        for (int i = 0; i <= max; i++) {
            bw.write(String.format("%5.5f", population[i]));
            bw.write(",");
        }
        bw.write(",");
        double total = 0;
        for (int i = 0; i <= max; i += 4) total += population[i];
        bw.write(String.format("%5.5f", total));
        bw.write(",");
        total = 0;
        for (int i = 1; i <= max; i += 4) total += population[i];
        bw.write(String.format("%5.5f", total));
        bw.write(",");
        total = 0;
        for (int i = 2; i <= max; i += 4) total += population[i];
        bw.write(String.format("%5.5f", total));
        bw.write(",");
        total = 0;
        for (int i = 3; i <= max; i += 4) total += population[i];
        bw.write(String.format("%5.5f", total));
        bw.write("\n");
    }

    private static double[] breedPopulation(double[] population, int max) {
        
        double[] newPopulation = new double[max + 1];
        for (int i = 0; i <= max; i++) {
            newPopulation[i] = 0;
        }   
        
        for (int p = 0; p <= max; p++) {
            Genome paternal = new Genome(p);
            for (int m = 0; m <= max; m++) {
                double populationBoost = population[p] * population[m] * 0.0625;
                Genome maternal = new Genome(m);
                Genome child = Genome.breed(paternal, maternal, false, false, false, false); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, false, false, false, true); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, false, false, true, false); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, false, false, true, true); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, false, true, false, false); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, false, true, false, true); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, false, true, true, false); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, false, true, true, true); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, true, false, false, false); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, true, false, false, true); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, true, false, true, false); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, true, false, true, true); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, true, true, false, false); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, true, true, false, true); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, true, true, true, false); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
                child = Genome.breed(paternal, maternal, true, true, true, true); newPopulation[child.getGenome()] += populationBoost * getAdvantage(child);
            }
        }
        
        double total = 0;
        for (int i = 0; i <= max; i++) {
            total += newPopulation[i];
        }
        for (int i = 0; i <= max; i++) {
            newPopulation[i] /= total;
        }
        
        return newPopulation;
    }

    private static double getAdvantage(Genome g) {
        double advantage = 1;
        if (g.getMaternalA() == Genome.MATERNAL_A && g.getPaternalA() == Genome.PATERNAL_A) advantage *= advantageAA;
        else if (g.getMaternalA() == Genome.MATERNAL_A || g.getPaternalA() == Genome.PATERNAL_A) advantage *= advantageA;
        if (g.getMaternalB() == Genome.MATERNAL_B && g.getPaternalB() == Genome.PATERNAL_B) advantage *= advantageBB;
        else if (g.getMaternalB() == Genome.MATERNAL_B || g.getPaternalB() == Genome.PATERNAL_B) advantage *= advantageB;
        switch (g.getMitochondrial()) {
            case Genome.MITOCHONDRIAL_0: advantage *= advantage0; break;
            case Genome.MITOCHONDRIAL_1: advantage *= advantage1; break;
            case Genome.MITOCHONDRIAL_2: advantage *= advantage2; break;
            case Genome.MITOCHONDRIAL_3: advantage *= advantage3; break;
        }
        return advantage;
    }

}
