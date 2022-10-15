package ru.otus.homework03.itegrationtests;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework03.repository.ExceptionHandler;
import ru.otus.homework03.repository.QueueOfCommand;
import ru.otus.homework03.repository.impl.ExceptionHandlerImpl;
import ru.otus.homework03.repository.impl.QueueOfCommandImpl;
import ru.otus.homework03.service.Initializer;
import ru.otus.homework03.service.Processor;
import ru.otus.homework03.service.impl.*;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCommands {

    private static final String LOG_WHEN_SOME_COMMAND_CALLS = "Вызов SomeCommand()";
    private static final String EXCEPTION_WAS_NOTHING = "Повторите запуск, исключение НЕ возникло при выполнении команды";
    private static final String PART_WRITE_TO_LOG = "Exception";

    private static QueueOfCommand queue;
    private static ExceptionHandler handler;

    @BeforeAll
    public static void setUp(){
        BasicConfigurator.configure();

        queue = QueueOfCommandImpl.getInstance();
        handler = ExceptionHandlerImpl.getInstance();
    }

    @Test
    @DisplayName("Тест 1: реализовать Команду, которая записывает информацию о выброшенном исключении в лог")
    public void testExecuteCommandWhenExceptionThenWriteToLog(){
        Initializer<QueueOfCommand> queueOfCommandInitializer = new InitializeQueueOfCommands();
        queueOfCommandInitializer.initialize(queue);

        Initializer<ExceptionHandler> exceptionHandlerInitializer = new InitializeExceptionHandlerWriteToLog();
        exceptionHandlerInitializer.initialize(handler);

        TestLogger logger = TestLoggerFactory.getTestLogger(WriteToLogCommand.class);

        Processor processor = new ProcessCommand(queue, handler);
        processor.process();

        List<LoggingEvent> logMessages = logger.getLoggingEvents().asList();

        if (logMessages.size() > 0){
            assertTrue(logMessages.get(0).getMessage().contains(PART_WRITE_TO_LOG));
        } else {
            System.out.println(EXCEPTION_WAS_NOTHING);
            assertTrue(Boolean.FALSE);
        }
    }

    @Test
    @DisplayName("Тест 2: реализовать Команду, которая повторяет Команду, выбросившую исключение")
    public void testЕxecuteCommandWhenExceptionThenRepeatCommand(){
        Initializer<QueueOfCommand> queueOfCommandInitializer = new InitializeQueueOfCommands();
        queueOfCommandInitializer.initialize(queue);

        Initializer<ExceptionHandler> exceptionHandlerInitializer = new InitializeExceptionHandlerRepeatCommand();
        exceptionHandlerInitializer.initialize(handler);

        TestLogger logger = TestLoggerFactory.getTestLogger(SomeCommand.class);

        Processor processor = new ProcessCommand(queue, handler);
        processor.process();

        List<LoggingEvent> logMessages = logger.getLoggingEvents().asList();

        if (logMessages.size() > 0){
            assertAll(
                    () -> assertEquals(LOG_WHEN_SOME_COMMAND_CALLS, logMessages.get(0).getMessage()),
                    () -> assertEquals(LOG_WHEN_SOME_COMMAND_CALLS, logMessages.get(1).getMessage())
            );
        } else {
            System.out.println(EXCEPTION_WAS_NOTHING);
            assertTrue(Boolean.FALSE);
        }
    }

    @Test
    @DisplayName("Тест 3: с помощью Команд из тестов 1 и 2 реализовать следующую обработку исключений:" +
                    "при первом выбросе исключения повторить команду, " +
                    "при повторном выбросе исключения записать информацию в лог")
    public void testЕxecuteCommandWhenExceptionThenRepeatAndWriteLogCommand(){
        Initializer<QueueOfCommand> queueOfCommandInitializer = new InitializeQueueOfCommands();
        queueOfCommandInitializer.initialize(queue);

        Initializer<ExceptionHandler> exceptionHandlerInitializer = new
                            InitializeExceptionHandlerRepeatCommandAndWriteLog();
        exceptionHandlerInitializer.initialize(handler);

        TestLogger loggerWriteToCommand = TestLoggerFactory.getTestLogger(WriteToLogCommand.class);
        TestLogger loggerSomeCommand = TestLoggerFactory.getTestLogger(SomeCommand.class);

        Processor processor = new ProcessCommand(queue, handler);
        processor.process();

        List<LoggingEvent> logMessagesWriteToCommand = loggerWriteToCommand.getLoggingEvents().asList();
        List<LoggingEvent> logMessagesSomeCommand = loggerSomeCommand.getLoggingEvents().asList();

        if ((logMessagesWriteToCommand.size() > 0) &&
                (logMessagesSomeCommand.size() > 0)) {
            assertAll(
                    () -> assertTrue(logMessagesWriteToCommand.get(0).getMessage().contains(PART_WRITE_TO_LOG)),
                    () -> assertEquals(LOG_WHEN_SOME_COMMAND_CALLS, logMessagesSomeCommand.get(0).getMessage()),
                    () -> assertEquals(LOG_WHEN_SOME_COMMAND_CALLS, logMessagesSomeCommand.get(1).getMessage())
            );
        } else {
            System.out.println(EXCEPTION_WAS_NOTHING);
            assertTrue(Boolean.FALSE);
        }
    }

    @Test
    @DisplayName("Тест 4: реализовать стратегию обработки исключения - повторить два раза, потом записать в лог." +
                    "Указание: создать новую команду, точно такую же как в тесте 2." +
                    "Тип этой команды будет показывать, что Команду не удалось выполнить два раза")
    public void testЕxecuteCommandWhenExceptionThenTwoRepeatAndWriteLogCommand(){
        Initializer<QueueOfCommand> queueOfCommandInitializer = new InitializeQueueOfCommands();
        queueOfCommandInitializer.initialize(queue);

        Initializer<ExceptionHandler> exceptionHandlerInitializer = new
                InitializeExceptionHandlerRepeatCommandTwoTimesAndWriteLog();
        exceptionHandlerInitializer.initialize(handler);

        TestLogger loggerWriteToCommand = TestLoggerFactory.getTestLogger(WriteToLogCommand.class);
        TestLogger loggerSomeCommand = TestLoggerFactory.getTestLogger(SomeCommand.class);

        Processor processor = new ProcessCommand(queue, handler);
        processor.process();

        List<LoggingEvent> logMessagesWriteToCommand = loggerWriteToCommand.getLoggingEvents().asList();
        List<LoggingEvent> logMessagesSomeCommand = loggerSomeCommand.getLoggingEvents().asList();

        if ((logMessagesWriteToCommand.size() > 0) &&
                (logMessagesSomeCommand.size() > 0)) {
            assertAll(
                    () -> assertTrue(logMessagesWriteToCommand.get(0).getMessage().contains(PART_WRITE_TO_LOG)),
                    () -> assertEquals(LOG_WHEN_SOME_COMMAND_CALLS, logMessagesSomeCommand.get(0).getMessage()),
                    () -> assertEquals(LOG_WHEN_SOME_COMMAND_CALLS, logMessagesSomeCommand.get(1).getMessage()),
                    () -> assertEquals(LOG_WHEN_SOME_COMMAND_CALLS, logMessagesSomeCommand.get(2).getMessage())
            );
        } else {
            System.out.println(EXCEPTION_WAS_NOTHING);
            assertTrue(Boolean.FALSE);
        }
    }
}
