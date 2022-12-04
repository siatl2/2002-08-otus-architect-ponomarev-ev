package ru.otus.homework06.service.impl;

import ru.otus.homework06.repository.IoCDictionary;
import ru.otus.homework06.repository.UObject;
import ru.otus.homework06.service.Initializer;

public class InitializerAdaptersProperties implements Initializer<IoCDictionary<Object>> {
    private static final String[] SETTERS_FOR_INITIALIZE = new String[]{
            "GenerateableAdapter.sourceFiles",
            "CompilableAdapter.compileFiles",
            "LoadableAdapter.classesName",
            "RegistrableAdapter.registerName"
    };

    private static final String[] GETTERS_FOR_INITIALIZE = new String[]{
            "GenerateableAdapter.sourceFiles",
            "CompilableAdapter.compileFiles",
            "LoadableAdapter.classesName",
            "RegistrableAdapter.registerName"
    };

    @Override
    public void initialize(IoCDictionary<Object> handler) {
        initializeSetters(handler);
        initializeGetters(handler);
    }

    private void initializeSetters(IoCDictionary<Object> handler) {
        for (String setterName : SETTERS_FOR_INITIALIZE) {
            String setterNamwWithSet = setterName + ".set";
            handler.add(setterNamwWithSet,
                    (args) -> {
                        UObject obj = (UObject) args[0];
                        obj.setProperty(setterName, args[1]);
                        return null;
                    });
        }
    }

    private void initializeGetters(IoCDictionary<Object> handler) {
        for (String getterName : GETTERS_FOR_INITIALIZE) {
            String getterNamwWithSet = getterName + ".get";
            handler.add(getterNamwWithSet,
                    (args) -> {
                        UObject obj = (UObject) args[0];
                        return obj.getProperty(getterName);
                    });
        }
    }
}