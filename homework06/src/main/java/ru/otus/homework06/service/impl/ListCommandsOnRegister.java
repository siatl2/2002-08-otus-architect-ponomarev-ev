package ru.otus.homework06.service.impl;

import ru.otus.homework06.adapter.Compilable;
import ru.otus.homework06.adapter.Generateable;
import ru.otus.homework06.adapter.Loadable;
import ru.otus.homework06.adapter.Registrable;
import ru.otus.homework06.service.Command;

import java.util.function.Function;

public class ListCommandsOnRegister implements Command {
    @Override
    public void execute() {
        IoC.<Command>resolve(
                "IoC.Register",
                new Object[]{
                        "Adapter",
                        (Function<Object[], Object>) (
                                (args) -> new GetAdapter(
                                        (Class) args[0],
                                        args[1]
                                )
                        )

                }
        ).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                new Object[]{
                        "GenerateAdapterCommand",
                        (Function<Object[], Object>) (
                                (args) -> new GenerateAdapterCommand(
                                        (Generateable) args[0],
                                        (Class) args[1],
                                        (Class) args[2]
                                )
                        )
                }
        ).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                new Object[]{
                        "CompileAdapterCommand",
                        (Function<Object[], Object>) (
                                (args) -> new CompileAdapterCommand(
                                        (Compilable) args[0],
                                        (Object[]) args[1]
                                )
                        )
                }
        ).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                new Object[]{
                        "LoadAdapterCommand",
                        (Function<Object[], Object>) (
                                (args) -> new LoadAdapterCommand(
                                        (Loadable) args[0],
                                        (Object[]) args[1]
                                )
                        )
                }
        ).execute();


        IoC.<Command>resolve(
                "IoC.Register",
                new Object[]{
                        "RegisterAdapterCommand",
                        (Function<Object[], Object>) (
                                (args) -> new RegisterAdapterCommand(
                                        (Registrable) args[0],
                                        (Object[]) args[1]
                                )
                        )
                }
        ).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                new Object[]{
                        "RegisterAdapterPropertiesCommand",
                        (Function<Object[], Object>) (
                                (args) -> new RegisterAdapterPropertiesCommand(
                                        (Registrable) args[0],
                                        (Object[]) args[1]
                                )
                        )
                }
        ).execute();

    }
}
