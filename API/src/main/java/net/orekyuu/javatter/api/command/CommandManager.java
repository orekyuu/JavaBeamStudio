package net.orekyuu.javatter.api.command;

import com.gs.collections.api.list.MutableList;
import net.orekyuu.javatter.api.service.Service;

/**
 * コマンド機能を提供します
 * @since 1.0.0
 */
@Service
public interface CommandManager {

    /**
     * コマンドを登録します。
     * @param command 登録するコマンド
     * @since 1.0.0
     */
    void registerCommand(Command command);

    /**
     * 文字列から実行できるコマンドを検索します。
     * @param text テキスト
     * @return コマンドを実行するRunnableのリスト
     * @since 1.0.0
     */
    MutableList<Runnable> findCommand(String text);

    /**
     * コマンドを実行します。
     * @param text テキスト
     * @return 加工後のテキスト
     * @since 1.0.0
     */
    String execCommand(String text);
}
