package com.hacktheborder.utilities;

public class ScoreCalculator {
    private static final double BASE_SCORE = 100.0;
    private static final double P1 = 5.0;  // Time penalty factor
    private static final double P2 = 10.0; // Wrong selections penalty factor

    public static int calculateScore(int timeTaken, int wrongSelections) {
        // Square root penalty for time taken
        double timePenalty = P1 * Math.sqrt(timeTaken);
        
        // Penalty for wrong selections raised to the power of 1.5
        double wrongSelectionPenalty = P2 * Math.pow(wrongSelections, 1.25);
        
        // Final score calculation
        int score =(int)(BASE_SCORE - timePenalty - wrongSelectionPenalty);
        
        // Ensure score doesn't go below 0
        return Math.max(0, score);
    }
}
