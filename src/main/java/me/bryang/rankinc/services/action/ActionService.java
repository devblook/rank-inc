package me.bryang.rankinc.services.action;

import me.bryang.rankinc.actions.Action;
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


    public ActionService(){
        actionManager.put(ActionType.MESSAGE, new SendMessageAction());
        actionManager.put(ActionType.COMMAND, new PerformCommandAction());

    }
    @Override
    public void init() {

        for (String line : configFile.getStringList("settings.global-actions")){

            String actionType = line.split(":")[0];

            Action action = actionManager.get(actionType.toUpperCase());
            action.setLine(line.substring(actionType.length()));

            globalAction.add(action);
        }

        Set<String> ranksKeys = configFile.getKeys(false);
        ranksKeys.remove("last-rank");

        for (String rankKey : ranksKeys){

            List<Action> actionList = new ArrayList<>();

            for (String line : ranksFile.getStringList(rankKey + ".actions")) {

                String actionType = line.split(":")[0];

                Action action = actionManager.get(actionType.toUpperCase());
                action.setLine(line.substring(actionType.length()));

                actionList.add(action);
            }

            ranksAction.put(rankKey, actionList);

        }
    }

}
