package net.orekyuu.javatter.api.service;

/**
 * 実行時の環境を取得するサービスです。
 * @since 1.0.0
 */
@Service
public interface EnvironmentService {

    /**
     * Javaビーム工房APIを実装している本体のバージョンを返します。
     * @since 1.0.0
     * @return 実装のバージョン
     */
    String getJavaBeamStudioVersion();

    /**
     * Javaビーム工房APIのバージョンを返します。
     * @since 1.0.0
     * @return APIのバージョン
     */
    String getJavaBeamStudioApiVersion();
}
