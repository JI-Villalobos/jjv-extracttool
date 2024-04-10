package org.jjv.instance;

import java.util.ArrayList;
import java.util.List;

public class LogCollectedInstance {
    private static List<String> logs;

    public static List<String> getLogs(){
        return logs;
    }
    public static void addLog(String log){
        if (logs == null)
            logs = new ArrayList<>();

        logs.add(log);
    }

    public static void clean() {
        logs.clear();
    }
}
