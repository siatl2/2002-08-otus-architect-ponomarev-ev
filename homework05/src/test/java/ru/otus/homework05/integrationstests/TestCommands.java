package ru.otus.homework05.integrationstests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework05.domain.AnyParameter;
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

import static org.junit.jupiter.api.Assertions.*;

public class TestCommands {
    public static final String MESSAGE_CONSTUCTOR_NO_ARGS = "Вызван конструктор без параметров";
    public static final String MESSAGE_CONSTRUCTOR_TWO_ARGS = "Вызван конструктор с двумя параметрами";

    private static ScopesDictionary scopesDictionary;

    @BeforeAll
    protected static void setUp(){
        scopesDictionary = ScopesDictionaryImpl.getInstance();
        Initializer initializerScopeDictionary = new InitializerScopeDictionary();
        initializerScopeDictionary.initialize(scopesDictionary);
    }

    @Test
    @DisplayName("Тест регистрации и затем получения экземпляра класса А без параметров")
    public void testRegisterAndGetObjectWithoutArgsWhenUsingIoC(){

        TestLogger logger = TestLoggerFactory.getTestLogger(A.class);

        Command aaa = (Command) IoC.resolve("IoC.Register",
                List.of(new AnyParameter("aaa"),
                        new AnyParameter((args) -> new A())
                ));
        aaa.execute();

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

        Command aaa = (Command) IoC.resolve("IoC.Register",
                List.of(new AnyParameter("aaa"),
                        new AnyParameter((args) -> new A(((List) args).get(0),
                                ((List) args).get(1)
                        )
                )));
        aaa.execute();

        A a = IoC.resolve("aaa", List.of(new AnyParameter("aaa"),
                                             new AnyParameter("bbb")));

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

        ((Command)(IoC.resolve("Scopes.New",
                List.of(new AnyParameter("ScopeOne"))
        ))).execute();

        ((Command)(IoC.resolve("IoC.Register",
                List.of(new AnyParameter("aaa"),
                        new AnyParameter((args) -> new A())
                )
        ))).execute();

        ((Command)(IoC.resolve("Scopes.New",
                List.of(new AnyParameter("ScopeTwo"))
        ))).execute();

        ((Command)(IoC.resolve("IoC.Register",
                List.of(new AnyParameter("aaa"),
                        new AnyParameter((args) -> new A())
                )
        ))).execute();

        ((Command) (IoC.resolve("Scopes.Current",
                List.of(new AnyParameter("ScopeOne"))))
        ).execute();

        A aOne = IoC.resolve("aaa", null);

        ((Command) (IoC.resolve("Scopes.Current",
                List.of(new AnyParameter("ScopeTwo"))))
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
