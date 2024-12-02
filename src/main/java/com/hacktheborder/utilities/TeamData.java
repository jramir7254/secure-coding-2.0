package com.hacktheborder.utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import com.hacktheborder.model.Question;
import com.hacktheborder.model.Team;




public class TeamData {
    StringBuilder out = new StringBuilder();


    
    public TeamData(Team teamA) {
        String s = "{" + 
                        "\n   Team Name: %s" + 
                        "\n   Game Score: %d" + 
                        "\n   High Score: %d" +
                        "\n   Question Info: ["+ 
                        "\n   ]" + 
                "\n}";
        s = String.format(s, teamA.getTeamName(), 2, 3);
        out.append(s);
    
        
     

    }

    public void addQuestionData(Question currentQuestion) {
        int index = out.lastIndexOf("},");
        if(index == -1) {
            out.insert(out.indexOf("[") + 1, currentQuestion);
        } else {
            out.insert(index + 2, currentQuestion);
        }

    }

    public String toString() {
        return out.toString();
    }


}


