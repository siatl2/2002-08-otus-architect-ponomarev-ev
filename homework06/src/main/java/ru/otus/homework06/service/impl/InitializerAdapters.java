package ru.otus.homework06.service.impl;

import org.apache.commons.lang3.tuple.Pair;
import ru.otus.homework06.adapter.*;
import ru.otus.homework06.repository.IoCDictionary;
import ru.otus.homework06.repository.UObject;
import ru.otus.homework06.repository.impl.UObjectImpl;
import ru.otus.homework06.service.Initializer;

import java.lang.reflect.Constructor;

public class InitializerAdapters implements Initializer<IoCDictionary<Adapter>> {
    private static final String ADAPTER_POSTFIX = "Adapter";
    private static final String ADAPTER_PACKAGE = "ru.otus.homework06.adapter.impl";
    private static final UObject uObj = UObjectImpl.getInstanse();

    private static final Pair<Class, UObject>[] ADAPTERS_FOR_INITIALIZE = new Pair[]{
            Pair.of(Generateable.class, uObj),
            Pair.of(Compilable.class, uObj),
            Pair.of(Loadable.class, uObj),
            Pair.of(Registrable.class, uObj)
    };

    @Override
    public void initialize(IoCDictionary<Adapter> handler) {
        for (Pair<Class, UObject> adapterInterfaceAndObject : ADAPTERS_FOR_INITIALIZE) {

            Object adaptee = adapterInterfaceAndObject.getRight();
            String adapterName = adapterInterfaceAndObject.getLeft().getSimpleName() +
                    ADAPTER_POSTFIX;
            String adapteeName = adapterInterfaceAndObject.getRight().getClass().getSimpleName();

            String adapterAndAdapteeName = adapterName + "." + adapteeName;

            handler.add(
                    adapterAndAdapteeName,
                    (args) -> {
                        try {
                            Class clazzAdapter = Class.forName(ADAPTER_PACKAGE + "." + adapterName);
                            Constructor constructor = clazzAdapter.getConstructor(UObject.class);
                            return (Adapter) constructor.newInstance(adaptee);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    }
            );
        }
    }
}
