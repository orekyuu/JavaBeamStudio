package net.orekyuu.javatter.api.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DIなどの設定が行われた後に呼び出されるメソッドを表すアノテーション
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnInit {
}
