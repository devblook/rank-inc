package me.bryang.rankinc.services.command;

import me.bryang.rankinc.manager.FileManager;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;

import javax.inject.Inject;
import javax.inject.Named;

public class CommandTranslator implements TranslationProvider {

    @Inject @Named("messages")
    private FileManager messagesFile;

    @Override
    public String getTranslation(Namespace namespace, String key) {

        switch (key) {
            case "sender.only-player":
                return messagesFile.getString("error.no-console");

            case "command.no-permission":
                return messagesFile.getString("error.no-permission");

        }
        return "Si ves este mensaje, contacta con el programador.";
    }

}
