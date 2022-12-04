package ru.otus.homework06.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework06.adapter.*;
import ru.otus.homework06.repository.IoCDictionary;
import ru.otus.homework06.repository.impl.ScopesDictionaryImpl;
import ru.otus.homework06.service.Command;

@RequiredArgsConstructor
public class GetAdapter {
    private static final String ADAPTER_POSTFIX = "Adapter";
    private final Class adapterInterface;
    private final Object adaptee;

    public Adapter get() {
        String keyName = adapterInterface.getSimpleName() +
                ADAPTER_POSTFIX + "." + adaptee.getClass().getSimpleName();

        IoCDictionary ioCDictionary = ScopesDictionaryImpl.getInstance().getCurrentScope();

        Object objectAdapter = ioCDictionary.get(keyName, null);
        Adapter returnAdapter;

        if (objectAdapter == null) {
            returnAdapter = createAdapterFromInterface();
        } else {
            returnAdapter = (Adapter) objectAdapter;
        }

        return returnAdapter;
    }

    private Adapter createAdapterFromInterface() {
        Adapter generateable =
                ((GetAdapter) (IoC.resolve(
                        "Adapter",
                        new Object[]{
                                Generateable.class,
                                adaptee
                        }
                ))).get();

        IoC.<Command>resolve(
                "GenerateAdapterCommand",
                new Object[]{
                        generateable,
                        adapterInterface,
                        adaptee.getClass()
                }
        ).execute();

        Adapter compilable =
                ((GetAdapter) (IoC.resolve(
                        "Adapter",
                        new Object[]{
                                Compilable.class,
                                adaptee
                        }
                ))).get();

        IoC.<Command>resolve(
                "CompileAdapterCommand",
                new Object[]{
                        compilable,
                        ((Generateable) generateable).getSourceFiles()
                }
        ).execute();

        Adapter loadable =
                ((GetAdapter) (IoC.resolve(
                        "Adapter",
                        new Object[]{
                                Loadable.class,
                                adaptee
                        }
                ))).get();

        IoC.<Command>resolve(
                "LoadAdapterCommand",
                new Object[]{
                        loadable,
                        ((Compilable) compilable).getCompileFiles()
                }
        ).execute();

        Adapter registrable =
                ((GetAdapter) (IoC.resolve(
                        "Adapter",
                        new Object[]{
                                Registrable.class,
                                adaptee
                        }
                ))).get();

        IoC.<Command>resolve(
                "RegisterAdapterPropertiesCommand",
                new Object[]{
                        registrable,
                        ((Loadable) loadable).getClasses()
                }
        ).execute();

        IoC.<Command>resolve(
                "RegisterAdapterCommand",
                new Object[]{
                        registrable,
                        ((Loadable) loadable).getClasses()
                }
        ).execute();
        System.out.println("CREATE AND REGITER COMPLETE");

        return ((GetAdapter) (IoC.resolve(
                "Adapter",
                new Object[]{
                        adapterInterface,
                        adaptee
                }
        ))).get();
    }

}
