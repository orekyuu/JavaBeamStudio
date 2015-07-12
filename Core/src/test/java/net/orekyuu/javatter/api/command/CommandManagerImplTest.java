package net.orekyuu.javatter.api.command;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.core.command.CommandManagerImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandManagerImplTest {

    CommandManager manager;
    List<String> testArgs;
    List<String> hogeArgs;
    List<String> executedCommand;

    @Before
    public void setUp() throws Exception {
        testArgs = Lists.mutable.empty();
        hogeArgs = Lists.mutable.empty();
        executedCommand = Lists.mutable.empty();
        manager = new CommandManagerImpl();
        manager.registerCommand(new Command() {
            @Override
            public String command() {
                return "test";
            }

            @Override
            public String help() {
                return "This is a Test Command";
            }

            @Override
            public void exec(List<String> args) {
                testArgs.addAll(args);
                executedCommand.add(command());
            }
        });
        manager.registerCommand(new Command() {
            @Override
            public String command() {
                return "hoge";
            }

            @Override
            public String help() {
                return "This is a Hoge Command";
            }

            @Override
            public void exec(List<String> args) {
                hogeArgs.addAll(args);
                executedCommand.add(command());
            }
        });
    }

    @Test
    public void testSimpleCommand() throws Exception {
        MutableList<Runnable> command = manager.findCommand("/hoge");
        assertEquals(command.size(), 1);

        command.each(Runnable::run);

        assertTrue(hogeArgs.isEmpty());
        assertEquals(executedCommand.get(0), "hoge");
    }

    @Test
    public void testNormalText() throws Exception {
        MutableList<Runnable> command = manager.findCommand("Test message.");
        assertTrue(command.isEmpty());
    }

    @Test
    public void testCommandArgs() throws Exception {
        MutableList<Runnable> command = manager.findCommand("/hoge huga");
        assertEquals(command.size(), 1);
        command.each(Runnable::run);

        assertEquals(executedCommand.get(0), "hoge");
        assertEquals(hogeArgs.size(), 1);
        assertEquals(hogeArgs.get(0), "huga");
    }

    @Test
    public void testTextAndCommand() throws Exception {
        MutableList<Runnable> command = manager.findCommand("hoge /hoge");
        assertEquals(command.size(), 1);
        command.each(Runnable::run);

        assertEquals(executedCommand.get(0), "hoge");
        assertEquals(hogeArgs.size(), 0);
    }

    @Test
    public void testAnyCommand() throws Exception {
        MutableList<Runnable> command = manager.findCommand("/hoge /test");
        assertEquals(command.size(), 2);
        command.each(Runnable::run);

        assertEquals(executedCommand.get(0), "hoge");
        assertEquals(executedCommand.get(1), "test");
        assertEquals(hogeArgs.size(), 0);
        assertEquals(testArgs.size(), 0);
    }

    @Test
    public void testTextAndAnyCommand() throws Exception {
        {
            MutableList<Runnable> command = manager.findCommand("/hoge aaa /test");
            assertEquals(command.size(), 2);
            command.each(Runnable::run);

            assertEquals(executedCommand.get(0), "hoge");
            assertEquals(executedCommand.get(1), "test");
            assertEquals(hogeArgs.size(), 1);
            assertEquals(hogeArgs.get(0), "aaa");
            assertEquals(testArgs.size(), 0);
            executedCommand.clear();
            hogeArgs.clear();
            testArgs.clear();
        }
        {
            MutableList<Runnable> command = manager.findCommand("aaa /hoge bbb /test ccc ddd");
            assertEquals(command.size(), 2);
            command.each(Runnable::run);

            assertEquals(executedCommand.get(0), "hoge");
            assertEquals(executedCommand.get(1), "test");
            assertEquals(testArgs.size(), 2);
            assertEquals(testArgs.get(0), "ccc");
            assertEquals(testArgs.get(1), "ddd");
            assertEquals(hogeArgs.size(), 1);
            assertEquals(hogeArgs.get(0), "bbb");
        }
    }
}
