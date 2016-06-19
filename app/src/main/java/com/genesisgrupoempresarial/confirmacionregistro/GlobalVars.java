package com.genesisgrupoempresarial.confirmacionregistro;

public final class GlobalVars {

    private static String urlBase = "http://www.genesisgrupoempresarial.com/Contratos/android";
    private static String urlTypeFile = ".php";
    private static GlobalVars globalVarsInstance;

    protected GlobalVars() {
    }

    public static synchronized GlobalVars getGlobalVarsInstance() {
        if (null == globalVarsInstance) {
            globalVarsInstance = new GlobalVars();
        }
        return globalVarsInstance;
    }

    public static String getUrlTypeFile() {
        return urlTypeFile;
    }

    public String getUrlBase() {
        return urlBase;
    }

}
