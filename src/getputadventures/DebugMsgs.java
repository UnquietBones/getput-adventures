package getputadventures;

public class DebugMsgs {

    // Huzzah for generic debug messages!

    private boolean showDebug;

    public DebugMsgs(boolean mainDebug) {
        showDebug = mainDebug;
    }

    public void debugLong() {
        if (showDebug) {
            System.out.println("--------------------------------");
        }
    }

    public void debugShort() {
        if (showDebug) {
            System.out.println("----------------");
        }
    }

    public void debugOutput(String outputThis) {
        if (showDebug) {
            System.out.println(outputThis);
        }
    }
}
