/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testmitochondrialfixation;

/**
 *
 * @author Platyptilia
 */
public class Genome {
    
    public static final int MITOCHONDRIAL_0 = 0;
    public static final int MITOCHONDRIAL_1 = 1;
    public static final int MITOCHONDRIAL_2 = 2;
    public static final int MITOCHONDRIAL_3 = 3;
    public static final int MATERNAL_A = 4;
    public static final int MATERNAL_B = 8;
    public static final int PATERNAL_A = 16;
    public static final int PATERNAL_B = 32;
    
    public static final int GENOME_MASK = 63;
    public static final int GENOME_LENGTH = 5;

    private int genome = 0;

    public Genome(String g) {
        genome = fromString(g);
    }
    
    /**
     *
     * @param g
     */
    public Genome(int g) {
        genome = g;
    }
    
    public int getPaternalA() {
        return genome & PATERNAL_A;
    }
    
    public int getPaternalB() {
        return genome & PATERNAL_B;
    }
    
    public int getMaternalA() {
        return genome & MATERNAL_A;
    }
    
    public int getMaternalB() {
        return genome & MATERNAL_B;
    }
    
    public int getMitochondrial() {
        return genome & MITOCHONDRIAL_3;
    }

    static Genome breed(Genome paternal, Genome maternal, boolean paternalPaternalA, boolean paternalPaternalB, boolean maternalPaternalA, boolean maternalPaternalB) {
        int g = maternal.getMitochondrial();
        g |= (paternalPaternalA ? paternal.getPaternalA() : (((paternal.getMaternalA() == MATERNAL_A) ? PATERNAL_A : 0)));
        g |= (paternalPaternalB ? paternal.getPaternalB() : (((paternal.getMaternalB() == MATERNAL_B) ? PATERNAL_B : 0)));
        g |= (maternalPaternalA ? (((maternal.getPaternalA() == PATERNAL_A) ? MATERNAL_A : 0)) : maternal.getMaternalA());
        g |= (maternalPaternalB ? (((maternal.getPaternalB() == PATERNAL_B) ? MATERNAL_B : 0)) : maternal.getMaternalB());
        return new Genome(g);
    }
   
    private int fromString(String s) {
        int g = 0;
        if (s.length() == GENOME_LENGTH) {
            if (s.charAt(0) == 'A') g |= PATERNAL_A;
            if (s.charAt(1) == 'B') g |= PATERNAL_B;
            if (s.charAt(2) == 'A') g |= MATERNAL_A;
            if (s.charAt(3) == 'B') g |= MATERNAL_B;
            switch (s.charAt(4)) {
                case '1':
                    g |= MITOCHONDRIAL_1;
                    break;
                case '2':
                    g |= MITOCHONDRIAL_2;
                    break;
                case '3':
                    g |= MITOCHONDRIAL_3;
                    break;
                default:
                    break;
            }
        }
        return g;
    }
    
    public String toString() {
        char[] genes = new char[5];
        genes[0] = (getPaternalA() == PATERNAL_A) ? 'A' : 'a';
        genes[1] = (getPaternalB() == PATERNAL_B) ? 'B' : 'b';
        genes[2] = (getMaternalA() == MATERNAL_A) ? 'A' : 'a';
        genes[3] = (getMaternalB() == MATERNAL_B) ? 'B' : 'b';
        switch (genome & MITOCHONDRIAL_3) {
            case MITOCHONDRIAL_0: genes[4] = '0'; break;
            case MITOCHONDRIAL_1: genes[4] = '1'; break;
            case MITOCHONDRIAL_2: genes[4] = '2'; break;
            case MITOCHONDRIAL_3: genes[4] = '3'; break;
            default: genes[4] = '?'; break;
        }
        return new String(genes);
    }
    
    public int getGenome() {
        return genome;
    }
}
