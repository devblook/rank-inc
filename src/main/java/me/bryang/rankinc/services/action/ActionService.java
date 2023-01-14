package me.bryang.rankinc.services.action;

import me.bryang.rankinc.actions.Action;
import me.bryang.rankinc.actions.ConsolePerformCommandAction;
import me.bryang.rankinc.actions.PerformCommandAction;
import me.bryang.rankinc.actions.SendMessageAction;
import me.bryang.rankinc.manager.FileManager;
import me.bryang.rankinc.services.Service;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@InjectAll
public class ActionService implements Service {

    private FileManager configFile;

    @Named("ranks")
    private FileManager ranksFile;

    private List<Action> globalAction;
    private Map<String, List<Action>> ranksAction;

    private Map<ActionType, Action> actionManager;

    @Override
    public void init() {

        actionManager.put(ActionType.MESSAGE, new SendMessageAction());
        actionManager.put(ActionType.COMMAND, new PerformCommandAction());
        actionManager.put(ActionType.CONSOLE_COMMAND, new ConsolePerformCommandAction());

        configFile.getStringList("settings.global-actions")
                .forEach(line -> globalAction.add(convertToAction(line)));

        Set<String> ranksKeys = ranksFile.getKeys(false);
        ranksKeys.remove("last-rank");

        ranksKeys.forEach(rankKey -> {

            List<Action> actionList = new ArrayList<>();
            for (String line : ranksFile.getStringList(rankKey + ".actions")){

                if (line.isEmpty()) {
                    continue;
                }

                actionList.add(convertToAction(line));
            }

            ranksAction.put(rankKey, actionList);
        });

    }

    public Action convertToAction(String line){

        String actionType = line.split(":")[0];


        Action action = actionManager.get(ActionType.valueOf(actionType.toUpperCase()));
        action.setLine(line.substring(actionType.length()));

        return action;
    }
}
