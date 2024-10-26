import java.util.*;

// Evelyn Salas
// CSE 143 AO with Stuart Reges
// Homework 5
// Randomly generates elements of BNF grammar

public class GrammarSolver {
    private TreeMap<String, List<String[]>> grammarMap;
    
    
    //Initializes new grammer rules
    // @param Accepts a list of grammer rules
    // @throws IllegalArgumentException if rules list is empty or if there are 
    // multiple rules per line
    public GrammarSolver(List<String> rules) {
        if (rules.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            //Map<String, String[]> 
            grammarMap = new TreeMap<String, List<String[]>>();
            for (String str: rules) {
                String[] termSeparator = str.split("::=");
                String nonTerm = termSeparator[0];
                String[] grammar = termSeparator[1].split("[|]");
                List<String[]> terminal = new ArrayList<String[]>();
                for(int i = 0; i < grammar.length; i++){
                    grammar[i] = grammar[i].trim();
                    terminal.add(grammar[i].split("[ \t]+"));
                }
                if (grammarMap.containsKey(nonTerm)){
                    throw new IllegalArgumentException();
                } else {
                    grammarMap.put(nonTerm, terminal);
                }
            }
        }
    } 

    // Checks if the given symbol is a grammar rule
    // @param String symbol, symbols that be grammar rules
    // @returns true if symbol is a grammar rule
    public boolean grammarContains(String symbol){
        return grammarMap.containsKey(symbol);
    }

    // Generates the number of random occurances for a given type of word
    // @param string symbol the grammar type of a word
    // @param int times number of occurances
    // @returns returns random number and the given grammar type
    // @throws IllegalArgumentException if times is negative or if symbol does not exist
    public String[] generate(String symbol, int times){
        if(times < 0 || !grammarContains(symbol)) { 
            throw new IllegalArgumentException();
        } else {
            String[] grammarStr = new String[times];
            for (int i = 0; i < times; i++) {
                String grammarResult = "";
                List<String[]> tokens = grammarMap.get(symbol);
                int randNum = (int)(Math.random() * tokens.size());
                for (int j = 0; j < tokens.get(randNum).length; j++) {
                    String tokenString = tokens.get(randNum)[j];
                    grammarResult += generate(tokenString, times + 1);
                }
                grammarStr[i] = grammarResult.trim();
            }
            return grammarStr;
        } 
    }
    
    // returns a list of symbols
    // @returns a string of grammar symbols
    public String getSymbols(){
        return grammarMap.keySet().toString();
    }
}