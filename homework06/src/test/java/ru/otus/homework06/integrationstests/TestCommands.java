package ru.otus.homework06.integrationstests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework06.adapter.Adapter;
import ru.otus.homework06.repository.IoCDictionary;
import ru.otus.homework06.repository.ScopesDictionary;
import ru.otus.homework06.repository.UObject;
import ru.otus.homework06.repository.impl.ScopesDictionaryImpl;
import ru.otus.homework06.repository.impl.UObjectImpl;
import ru.otus.homework06.service.Command;
import ru.otus.homework06.service.Initializer;
import ru.otus.homework06.service.Movable;
import ru.otus.homework06.service.impl.GetAdapter;
import ru.otus.homework06.service.impl.InitializerScopeDictionary;
import ru.otus.homework06.service.impl.IoC;
import ru.otus.homework06.service.impl.ListCommandsOnRegister;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class TestCommands {

    @BeforeAll
    protected static void setUp() {
        ScopesDictionary<IoCDictionary> scopesDictionary = ScopesDictionaryImpl.getInstance();
        Initializer<ScopesDictionary<IoCDictionary>> initializerScopeDictionary = new InitializerScopeDictionary();
        initializerScopeDictionary.initialize(scopesDictionary);
        registerAllCommands();
    }

    private static void registerAllCommands() {
        IoC.<Command>resolve(
                "IoC.Register",
                new Object[]{
                        "ListCommandsOnRegister",
                        (Function<Object[], Object>) (
                                (args) -> new ListCommandsOnRegister()
                        )

                }
        ).execute();

        IoC.<Command>resolve(
                "ListCommandsOnRegister",
                null
        ).execute();
    }

    @Test
    @DisplayName("Тест создания адаптера")
    public void TestCreateAdapter() {
        UObject uObject = UObjectImpl.getInstanse();

        Adapter moveable =
                ((GetAdapter) (IoC.resolve(
                        "Adapter",
                        new Object[]{
                                Movable.class,
                                uObject
                        }
                ))).get();

        assertAll(
                () -> assertNotNull(moveable),
                () -> assertEquals(moveable.getClass().getSimpleName(), Movable.class.getSimpleName() + "Adapter")
        );
    }
}
