package com.hacktheborder;


import com.hacktheborder.controller.DebuggingController;
import com.hacktheborder.controller.EndGameScreenController;
import com.hacktheborder.controller.GameController;
import com.hacktheborder.controller.MainController;
import com.hacktheborder.controller.MainMenuController;
import com.hacktheborder.controller.MultipleChoiceController;
import com.hacktheborder.model.Question;
import com.hacktheborder.model.QuestionHolder;
import com.hacktheborder.model.Team;
import com.hacktheborder.utilities.AnimationEffects;

import javafx.scene.Node;
import javafx.scene.Parent;


@SuppressWarnings("exports")

public class ApplicationManager {

    
    private final static Parent                     MAIN                            = Main.main;

    private final static Node                       MAIN_MENU                       = Main.mainMenu;
    private final static Node                       GAME_PANEL                      = Main.game;
    private final static Node                       MULIPLE_CHOICE_PANEL            = Main.multipleChoice;
    private final static Node                       DEBUGGING_PANEL                 = Main.debugging;
    private final static Node                       END_GAME_PANEL                  = Main.endGame;

    private final static MainController             MAIN_CONTROLLER                 = Main.mainController;
    private final static MainMenuController         MAIN_MENU_CONTROLLER            = Main.mainMenuController;
    private final static GameController             GAME_CONTROLLER                 = Main.gameController;
    private final static MultipleChoiceController   MULTIPLE_CHOICE_CONTROLLER      = Main.multipleChoiceController;
    private final static DebuggingController        DEBUGGING_CONTROLLER            = Main.debuggingController;
    private final static EndGameScreenController    END_GAME_SCREEN_CONTROLLER      = Main.endGameScreenController;

    private final static QuestionHolder             QUESTION_HOLDER                 = new QuestionHolder();

    private final static String                     READ_ONLY_HTML_FILE             = "/com/hacktheborder/html/codemirror-readonly.html";
    private final static String                     EDITABLE_HTML_FILE              = "/com/hacktheborder/html/codemirror-editable.html";

    private static       Question                   currentQuestion;
    private static       Team                       currentTeam;


    public static class QuestionManager {
        public static Question getCurrentQuestion() {
            return currentQuestion;
        }

        public static String getCurrentQuestionAnswer() {
            return currentQuestion.getQuestionType();
        }

        public static void updateMultipleChoiceWrongAttempts() {
            currentQuestion.updateMultipleChoiceWrongAttempts();
        }

        public static void updateDebuggingAttempts(String debuggingAttempt, boolean correct) {
            if(!correct)
                currentQuestion.updateDebuggingWrongAttempts();
            currentQuestion.addDebugingAttempt(debuggingAttempt);
        }

        public static String getSanitizedJavaCode() {
            return currentQuestion.getJavaCode()
            .replace("\\", "\\\\") 
            .replace("'", "\\'")   
            .replace("\n", "\\n")    
            .replace("\r", "");   
        }
    }



    public static class TeamManager {

        public static void setupCurrentTeam(String teamName) {
            currentTeam = new Team(teamName, 0, 0);
            MAIN_CONTROLLER.initializeLabels(teamName.toUpperCase());
        }

        public static void addQuestionData() {
            currentTeam.currentTeamData.addQuestionData(currentQuestion);
        }

        public static Team getCurrentTeam() {
            return currentTeam;
        }

        public static void updateTeamScore() {
            currentTeam.updateCurrentScore(AnimationEffects.getCurrentQuestionScore());
            AnimationManager.animateAndResetTimelineAndScore();
        }

        public static void printTeamData() {
            System.out.println(currentTeam.currentTeamData);
        }
    }



    public static class AnimationManager {
        public static void animateWrongAnswerChoice(Node node) {
            AnimationEffects.playShakeEffect(node, "#e3210b", false, true);
            AnimationEffects.wrongAnswerPenalty();
        }

        public static void animateCorrectAnswerChoice(Node node) {
            AnimationEffects.playShakeEffect(node, "#24e327", true, false);
        }

        public static void animateAndResetTimelineAndScore() {
            AnimationEffects.animateUpdatedTeamScore(currentTeam.getCurrentGameScore());
            AnimationEffects.stopAndResetQuestionAndScoreTimelines();
        }

        public static void restartQuestionAndScoreTimelines() {
            AnimationEffects.startQuestionScoreTimeline();
            AnimationEffects.startQuestionTimer();
        }

        public static void stopAllTimelines() {
            AnimationEffects.stopAllTimelines();
        }
    }



    public static class GameControllerManager {
        public static String getReadOnlyHTMLFile() {
            return READ_ONLY_HTML_FILE;
        }

        public static void displayReadOnlyHTMLFile() {
            String code = String.format("setEditorContent('%s')", QuestionManager.getSanitizedJavaCode());
            GAME_CONTROLLER.loadWebViewContent(READ_ONLY_HTML_FILE, code);
        }

        public static void displayEditableHTMLFile() {
            String code = String.format("setEditorContent('%s'); setEditable(%s);", QuestionManager.getSanitizedJavaCode(), currentQuestion.getNonEditableLines());
            GAME_CONTROLLER.loadWebViewContent(EDITABLE_HTML_FILE, code);
        }

        public static String getCodeMirrorText() {
            return GAME_CONTROLLER.getEditorContent();
        }

        public static void displayDebuggingPanel() {
            displayEditableHTMLFile();
            int index = GAME_CONTROLLER.centerVBox.getChildren().indexOf(MULTIPLE_CHOICE_CONTROLLER.buttonVBoxContainer);
            GAME_CONTROLLER.centerVBox.getChildren().set(index, DEBUGGING_PANEL);
            GAME_CONTROLLER.setTextToDebug();
            DEBUGGING_CONTROLLER.setTextToDebug(currentQuestion.getExpectedOutput());
            AnimationManager.restartQuestionAndScoreTimelines();
        }

        public static void resetCodeArea() {
            displayEditableHTMLFile();
        }
    }





    public static void onMainMenuSubmitButtonPressed() {
        MAIN_CONTROLLER.addCenter(GAME_PANEL);
        GameControllerManager.displayReadOnlyHTMLFile();
        AnimationEffects.startAllTimelines();
        TeamManager.setupCurrentTeam(MAIN_MENU_CONTROLLER.getTeamNameTextFieldText());
    }


    public static void initialize() {
        
        currentQuestion = QUESTION_HOLDER.getNextQuestion();
        AnimationEffects.initialize();
    }












   




    public static void setupNextQuestion() {
        if(!QUESTION_HOLDER.isEmpty()) {
            AnimationManager.restartQuestionAndScoreTimelines();
            currentQuestion = QUESTION_HOLDER.getNextQuestion();
            GAME_CONTROLLER.displayMultipleChoice();
            MULTIPLE_CHOICE_CONTROLLER.resetButtons();
            GameControllerManager.displayReadOnlyHTMLFile();
        } else {
            MAIN_CONTROLLER.addCenter(END_GAME_PANEL);
            AnimationManager.stopAllTimelines();
        }
    }




}
 