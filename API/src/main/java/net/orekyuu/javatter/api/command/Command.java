package net.orekyuu.javatter.api.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * コマンドを表すインターフェイス
 * @since 1.0.0
 */
public interface Command {

    /**
     * コマンド実行用の文字列を返します。
     * @return コマンド実行用の文字列
     * @since 1.0.0
     */
    String command();

    /**
     * コマンドの説明を返します。
     * @return コマンドの説明
     * @since 1.0.0
     */
    String help();

    /**
     * コマンドを実行します。
     * @param args コマンドの引数
     * @since 1.0.0
     */
    void exec(List<String> args);

    /**
     * execを呼び出すRunnableを生成します。
     * @param args 引数
     * @return execを呼び出すRunnable
     * @since 1.0.0
     */
    default Runnable runner(String... args) {
        return runner(Arrays.asList(args));
    }

    /**
     * execを呼び出すRunnableを生成します。
     * @param args 引数
     * @return execを呼び出すRunnable
     * @since 1.0.0
     */
    default Runnable runner(List<String> args) {
        return () -> exec(new ArrayList<>(args));
    }
}
