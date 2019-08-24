Local Search
run the Local search
    1. put all Dataset files in the the folder.
    2. Copy the absolute path of folder.
    3. Enter the main directory of the program
    4. run the program LocalSearch by running the command: java com.ec.LocalSearch path, e.g. java com.ec.LocalSearch /*/*/*/TSP-Assignment1/src/resources

--------------------------Evolutionary Algorithm---------------------------
Running the EA
    1. Open SampleMain.java
    2. Create EvolutionAlgorithm object.
    3. Use setSelection(), setCrossover(), setMutation() method to specify the available algorithms.
    4. You can choose 
        3 Selection
            - Tournament, Fitness, Elitism
        3 Mutation
            - Insert, Swap, Inverse
        3 Crossover
            - Order, PMX, Cycle
    5. Use run() method to run the algorithm.
