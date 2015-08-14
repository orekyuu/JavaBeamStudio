package net.orekyuu.javatter.api.service;

import java.lang.annotation.*;

/**
 * Lookupを使用して実装を検索できる事を表すインターフェイスです。
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
}
