package ru.otus.homework05.integrationstests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework05.repository.IoCDictionary;
import ru.otus.homework05.repository.ScopesDictionary;
import ru.otus.homework05.repository.impl.ScopesDictionaryImpl;
import ru.otus.homework05.service.Command;
import ru.otus.homework05.service.Initializer;
import ru.otus.homework05.service.impl.InitializerScopeDictionary;
import ru.otus.homework05.service.impl.IoC;
import ru.otus.homework05.testclass.A;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class TestCommands {
    public static final String MESSAGE_CONSTUCTOR_NO_ARGS = "Вызван конструктор без параметров";
    public static final String MESSAGE_CONSTRUCTOR_TWO_ARGS = "Вызван конструктор с двумя параметрами";

    @BeforeAll
    protected static void setUp(){
        ScopesDictionary<IoCDictionary> scopesDictionary = ScopesDictionaryImpl.getInstance();
        Initializer<ScopesDictionary<IoCDictionary>> initializerScopeDictionary = new InitializerScopeDictionary();
        initializerScopeDictionary.initialize(scopesDictionary);
    }

    @Test
    @DisplayName("Тест регистрации и затем получения экземпляра класса А без параметров")
    public void testRegisterAndGetObjectWithoutArgsWhenUsingIoC(){

        TestLogger logger = TestLoggerFactory.getTestLogger(A.class);

        IoC.<Command>resolve("IoC.Register",
                new Object[]{"aaa",
                        (Function<Object[], Object>) ((args) -> new A())}
        ).execute();


        A a = IoC.resolve("aaa", null);

        List<LoggingEvent> logMessages = logger.getLoggingEvents().asList();

        if (logMessages.size() > 0){
            assertEquals(MESSAGE_CONSTUCTOR_NO_ARGS, logMessages.get(0).getMessage());
        } else {
            assertTrue(Boolean.FALSE);
        }

        TestLoggerFactory.clearAll();
    }

    @Test
    @DisplayName("Тест регистрации и затем получения экземпляра класса А с двумя параметрами")
    public void testRegisterAndGetObjectWithTwoArgsWhenUsingIoC(){

        TestLogger logger = TestLoggerFactory.getTestLogger(A.class);

        IoC.<Command>resolve("IoC.Register",
                new Object[]{"aaa",
                        (Function<Object[], Object>) ((args) -> new A(args[0], args[1]))}
        ).execute();


        A a = IoC.resolve("aaa", new Object[]{"aaa", "bbb"});

        List<LoggingEvent> logMessages = logger.getLoggingEvents().asList();

        if (logMessages.size() > 0){
            assertEquals(MESSAGE_CONSTRUCTOR_TWO_ARGS, logMessages.get(0).getMessage());
        } else {
            assertTrue(Boolean.FALSE);
        }

        TestLoggerFactory.clearAll();
    }

    @Test
    @DisplayName("Тест регистрации и затем получения экземпляра класса А в разных скоупах")
    public void testRegisterAndGetObjectInDiffScopesWhenUsingIoC(){

        TestLogger logger = TestLoggerFactory.getTestLogger(A.class);

        IoC.<Command>resolve("Scopes.New",
                                  new Object[]{"ScopeOne"}
        ).execute();

        IoC.<Command>resolve("IoC.Register",
                new Object[]{"aaa",
                        (Function<Object[], Object>) ((args) -> new A())}
        ).execute();

        IoC.<Command>resolve("Scopes.New",
                new Object[]{"ScopeTwo"}
        ).execute();

        IoC.<Command>resolve("IoC.Register",
                new Object[]{"aaa",
                        (Function<Object[], Object>) ((args) -> new A())}
        ).execute();

        IoC.<Command>resolve("Scopes.Current",
                new Object[]{"ScopeOne"}
        ).execute();

        A aOne = IoC.resolve("aaa", null);

        IoC.<Command>resolve("Scopes.Current",
                new Object[]{"ScopeTwo"}
        ).execute();

        A aTwo = IoC.resolve("aaa", null);

        List<LoggingEvent> logMessages = logger.getLoggingEvents().asList();

        if (logMessages.size() == 2){
            assertAll(
                    () -> assertEquals(MESSAGE_CONSTUCTOR_NO_ARGS, logMessages.get(0).getMessage()),
                    () -> assertEquals(MESSAGE_CONSTUCTOR_NO_ARGS, logMessages.get(1).getMessage())
            );
        } else {
            assertTrue(Boolean.FALSE);
        }
        TestLoggerFactory.clearAll();
    }
}
