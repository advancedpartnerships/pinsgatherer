package pinsgatherer.sikuli;

import pinsgatherer.helper.PropertiesManager;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;

public class SikuliScripts {

    private static SikuliScripts instance = new SikuliScripts();
    public static SikuliScripts getScripts() {
        return instance;
    }

    public boolean completeForm(String params)  {
        return runScript("register.skl", params) && runScript("additional.skl", params);
    }

    public String recoverPin() {
        pasteToClipboard("ERROR");
        if(runScript("recover.skl")) {
            String pin = copyFromClipboard();
            if(!"ERROR".equals(pin)) {
                return pin;
            }
        }
        return null;
    }

    private void pasteToClipboard(String contents) {
        StringSelection transferable = new StringSelection(contents);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable, transferable);
    }

    private String copyFromClipboard() {
        Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if(contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                return (String)contents.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean runScript(String script) {
        return runScript(script, "");
    }

    private boolean runScript(String script, String params) {
    	String sikuliPath = PropertiesManager.getProperty("pinsgatherer.sikuli_path");
        try {
            Process sikuli = Runtime.getRuntime().exec(sikuliPath + " \"" + new File(script).getCanonicalPath() + "\" " + params);
            int retVal = -1;
            try {
                retVal = sikuli.waitFor();
            } catch (InterruptedException ignore) {}
            if(retVal == -1) {
                try {
                    retVal = sikuli.exitValue();
                } catch (IllegalThreadStateException e) {
                    retVal = 1;
                }
            }
            return retVal == 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
