package ru.otus.homework06.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework06.adapter.Registrable;
import ru.otus.homework06.repository.IoCDictionary;
import ru.otus.homework06.repository.UObject;
import ru.otus.homework06.repository.impl.ScopesDictionaryImpl;
import ru.otus.homework06.service.Command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RegisterAdapterPropertiesCommand implements Command {
    private static final IoCDictionary<Object> handler = ScopesDictionaryImpl.getInstance().getCurrentScope();
    private final Registrable registrable;
    private final Object[] classes;

    @Override
    public void execute() {
        Class classAdapter = (Class) classes[0];
        List<String> returnRegiterProperties = new ArrayList<>();
        String registerProperty;

        for (Method method : classAdapter.getDeclaredMethods()) {
            if (!method.getReturnType().equals(Void.TYPE)) {
                registerProperty = regiterGetter(classAdapter, method);
                returnRegiterProperties.add(registerProperty);
            } else {
                if (method.getParameterCount() > 0) {
                    registerProperty = registerSetter(classAdapter, method);
                    returnRegiterProperties.add(registerProperty);
                }
            }
        }

        registrable.setRegisterNames(returnRegiterProperties.toArray());
    }

    private String registerSetter(Class classAdapter, Method method) {
        String setterName = classAdapter.getSimpleName() + "." +
                firstLetterToDown(method.getName().replace("set", ""));

        String setterNamwWithSet = setterName + ".set";

        handler.add(setterNamwWithSet,
                (args) -> {
                    UObject obj = (UObject) args[0];
                    obj.setProperty(setterName, args[1]);
                    return null;
                });

        return setterNamwWithSet;
    }

    private String regiterGetter(Class classAdapter, Method method) {
        String getterName = classAdapter.getSimpleName() + "." +
                firstLetterToDown(method.getName().replace("get", ""));
        String getterNamwWithSet = getterName + ".get";
        handler.add(getterNamwWithSet,
                (args) -> {
                    UObject obj = (UObject) args[0];
                    return obj.getProperty(getterName);
                });

        return getterNamwWithSet;
    }

    private String firstLetterToDown(String value) {
        return value.substring(0, 1).toLowerCase() + value.substring(1);
    }
}
