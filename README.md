# Test Mitochondrial Fixation

Very basic (currently uncommented) Java program to simulate a population of
sexually reproducing organisms with four initial genotypes, varying only in two 
nuclear alleles and one mitochondrial (or otherwise female-linked) gene with 
four variants.

Each allele and each homozygous allele and each mitochondrial variant is 
assigned an independent fitness weighting.

Initial population levels are set for original genomes. In each generation,
genomes freely interbreed in proportion to their current proportion in the
overall population and produce children according to the fitness of the 
various child genotypes.

The program will run for a specified number of generations and will report 
every the abundance of each genome in the nth generation. The output is as 
a CSV file.
