public class TranslationProtocol {
    private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;
    private static final int NUMJOKES = 5;
    private int state = WAITING;
    private static String language_to_translate = "";
    private static String str_To_Be_Translated = "";

    // protocol!
    public String processInput(String theInput) {
        String theOutput = null;
        if (state == WAITING) { // Waiting
            theOutput = "Please specify Translation(\"en-sk\", \"en-ru\", \"en-de\", etc.) [NO SPACES]";
            state = SENTKNOCKKNOCK;

        } else if (state == SENTKNOCKKNOCK) { // Sent Knock Knock
            language_to_translate = theInput;
            theOutput = "Specify what to translate";
            state = SENTCLUE;
        } else if (state == SENTCLUE) { // Sent clue
            str_To_Be_Translated = theInput;
            Test_HTTP_XML translationConnector = new Test_HTTP_XML();
            theOutput = "Translation = "+ translationConnector.get_response(str_To_Be_Translated,language_to_translate);
            state = ANOTHER;

        } else if (state == ANOTHER) {
            if ((theInput.equals("y")) || (theInput.equals("Y"))){
                theOutput = "Specify what to translate";
                state = SENTCLUE;
            } else {
                theOutput = "Bye.";
                state = WAITING;
            }

        }
        return theOutput;
    }
}
/*

 */