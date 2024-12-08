package com.hacktheborder;


import com.hacktheborder.controller.DebuggingController;
import com.hacktheborder.controller.EndGameScreenController;
import com.hacktheborder.controller.GameController;
import com.hacktheborder.controller.MainController;
import com.hacktheborder.controller.MainMenuController;
import com.hacktheborder.controller.MultipleChoiceController;
import com.hacktheborder.controller.SettingsController;
import com.hacktheborder.model.Question;
import com.hacktheborder.model.QuestionHolder;
import com.hacktheborder.model.Settings;
import com.hacktheborder.model.Team;
import com.hacktheborder.utilities.AnimationEffects;
import com.hacktheborder.utilities.SQLConnector;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.util.Duration;


@SuppressWarnings("exports")

public class ApplicationManager {

    
    private final static Parent                     MAIN                            = Main.main;

    private final static Node                       MAIN_MENU                       = Main.mainMenu;
    private final static Node                       GAME_PANEL                      = Main.game;
    private final static Node                       MULIPLE_CHOICE_PANEL            = Main.multipleChoice;
    private final static Node                       DEBUGGING_PANEL                 = Main.debugging;
    private final static Node                       END_GAME_PANEL                  = Main.endGame;
    public final static Node                       SETTINGS                  = Main.settingsContainer;

    private final static MainController             MAIN_CONTROLLER                 = Main.mainController;
    private final static MainMenuController         MAIN_MENU_CONTROLLER            = Main.mainMenuController;
    private final static GameController             GAME_CONTROLLER                 = Main.gameController;
    private final static MultipleChoiceController   MULTIPLE_CHOICE_CONTROLLER      = Main.multipleChoiceController;
    private final static DebuggingController        DEBUGGING_CONTROLLER            = Main.debuggingController;
    private final static EndGameScreenController    END_GAME_SCREEN_CONTROLLER      = Main.endGameScreenController;
    public final static SettingsController         SETTINGS_CONTROLLER             = Main.settingsController;

    private final static QuestionHolder             QUESTION_HOLDER                 = new QuestionHolder();

    private final static String                     READ_ONLY_HTML_FILE             = "/com/hacktheborder/html/codemirror-readonly.html";
    private final static String                     EDITABLE_HTML_FILE              = "/com/hacktheborder/html/codemirror-editable.html";

    private static       Question                   currentQuestion;
    private static       Team                       currentTeam;

    public static Settings settings = new Settings();



    public static void onMainMenuSubmitButtonPressed() {
        MainControllerManager.displayGamePanel();
    }


    public static void initialize() {
        
        currentQuestion = QUESTION_HOLDER.getNextQuestion();
        AnimationEffects.initialize(settings);
    }


    public static void updateSettings(Settings newSettings) {
        settings = newSettings;
        AnimationEffects.initialize(settings);
        System.out.println(settings);
        if(settings.isOnlineGame()) {
            updateLeaderBoard();
        }
    }


    public static void updateLeaderBoard() {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(10), event -> {
                MAIN_CONTROLLER.updateLeaderBoard(new SQLConnector().getTopFive());
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); 
        timeline.play();
    }





























    public static class QuestionManager {

        public static Question getCurrentQuestion() {
            return currentQuestion;
        }

        public static void getNextQuestion() {
            if (!QUESTION_HOLDER.isEmpty()) {
                AnimationManager.restartQuestionAndScoreTimelines();
                currentQuestion = QUESTION_HOLDER.getNextQuestion();
                GameControllerManager.displayMultipleChoicePanel();

                
            } else {
                MAIN_CONTROLLER.addCenter(END_GAME_PANEL);
                AnimationManager.stopAllTimelines();
            }
        }

        public static String getCurrentQuestionAnswer() {
            return currentQuestion.getQuestionType();
        }

        public static void updateMultipleChoiceWrongAttempts() {
            currentQuestion.updateMultipleChoiceWrongAttempts();
        }

        public static void updateDebuggingAttempts(String debuggingAttempt, boolean correct) {
            if (!correct)
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
            System.out.println("Editable WebView content loaded successfully.");
            displayEditableHTMLFile();
            GAME_CONTROLLER.displayDebuggingPanel(MULIPLE_CHOICE_PANEL, DEBUGGING_PANEL);
            GAME_CONTROLLER.setTextToDebug();
            DEBUGGING_CONTROLLER.setTextToDebug(currentQuestion.getExpectedOutput());
            AnimationManager.restartQuestionAndScoreTimelines();
        }

        public static void displayMultipleChoicePanel() {
            System.out.println("Non-Editable WebView content loaded successfully.");
            displayReadOnlyHTMLFile();
            GAME_CONTROLLER.displayMultipleChoice(DEBUGGING_PANEL, MULIPLE_CHOICE_PANEL);
            MULTIPLE_CHOICE_CONTROLLER.resetButtons();
        }

        public static void resetCodeArea() {
            displayEditableHTMLFile();
        }
        
    }

























    public static class MainControllerManager {

        public static void displayGamePanel() {
            MAIN_CONTROLLER.addCenter(GAME_PANEL);
            TeamManager.setupCurrentTeam(MAIN_MENU_CONTROLLER.getTeamNameTextFieldText());
            GameControllerManager.displayReadOnlyHTMLFile();
            AnimationEffects.startAllTimelines();
        }

        public static void endGamePanel() {
            MAIN_CONTROLLER.addCenter(END_GAME_PANEL);
            AnimationManager.stopAllTimelines();
        }

    }

}
 