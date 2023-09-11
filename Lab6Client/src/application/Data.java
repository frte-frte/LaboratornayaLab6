package application;

import java.io.Serializable;
import java.util.Arrays;

public class Data implements Serializable {
    private String commandName;
    private String[] param;
    public Data(String commandName, String[] param) {
        this.commandName = commandName;
        this.param = param;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String[] getParam() {
        return param;
    }

    public void setParam(String[] param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "Data{" +
                "commandName='" + commandName + '\'' +
                ", param='" + Arrays.toString(param) + '\'' +
                '}';
    }
}
