package ru.otus.homework06.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework06.adapter.Adapter;
import ru.otus.homework06.adapter.Registrable;
import ru.otus.homework06.repository.IoCDictionary;
import ru.otus.homework06.repository.UObject;
import ru.otus.homework06.repository.impl.ScopesDictionaryImpl;
import ru.otus.homework06.repository.impl.UObjectImpl;
import ru.otus.homework06.service.Command;

import java.lang.reflect.Constructor;

@RequiredArgsConstructor
public class RegisterAdapterCommand implements Command {
    private static final UObject uObject = UObjectImpl.getInstanse();
    private final Registrable registrable;
    private final Object[] classes;

    @Override
    public void execute() {
        Class clazz = (Class) classes[0];
        IoCDictionary<Adapter> handler = ScopesDictionaryImpl.getInstance().getCurrentScope();

        String registerAdapterName = registerAdapter(clazz, handler);

        registrable.setRegisterNames(
                new Object[]{
                        registerAdapterName
                }
        );
    }

    private String registerAdapter(Class clazzAdapter, IoCDictionary<Adapter> handler) {
        String adapterAndAdapteeName = clazzAdapter.getSimpleName() + "." + uObject.getClass().getSimpleName();

        handler.add(
                adapterAndAdapteeName,
                (args) -> {
                    try {
                        Constructor constructor = clazzAdapter.getConstructor(uObject.getClass());
                        return (Adapter) constructor.newInstance(uObject);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                }
        );

        return adapterAndAdapteeName;
    }
}
