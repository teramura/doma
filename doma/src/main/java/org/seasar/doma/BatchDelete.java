/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.doma;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Statement;
import java.util.List;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.JdbcException;
import org.seasar.doma.jdbc.OptimisticLockException;
import org.seasar.doma.jdbc.SqlFileNotFoundException;

/**
 * バッチ削除処理を示します。
 * <p>
 * このアノテーションが注釈されるメソッドは、 {@link Dao} が注釈されたインタフェースのメンバでなければいけません。
 * 
 * 注釈されるメソッドは、次の制約を満たす必要があります。
 * <ul>
 * <li>パラメータを1つだけ受け取る。
 * <li>パラメータは {@link Entity} が注釈された型を要素とする {@link List} である。
 * <li>戻り値の型は {@code int[]} である。
 * </ul>
 * 
 * <h5>例:</h5>
 * 
 * <pre>
 * &#064;Entity
 * public class Employee {
 *     ...
 * }
 * 
 * &#064;Dao(config = AppConfig.class)
 * public interface EmployeeDao {
 * 
 *     &#064;BatchDelete
 *     int[] delete(List&lt;Employee&gt; employee);
 * }
 * </pre>
 * 
 * 注釈されるメソッドは、次の例外をスローすることがあります。
 * <ul>
 * <li> {@link DomaNullPointerException} パラメータに {@code null} を渡した場合
 * <li> {@link OptimisticLockException} {@code sqlFile} 要素の値が {@code false} で
 * バッチのそれぞれの処理において更新件数が1件でなかった場合
 * <li> {@link SqlFileNotFoundException} {@code sqlFile} 要素の値が {@code true}
 * で、SQLファイルが見つからなかった場合
 * <li> {@link JdbcException} 上記以外でJDBCに関する例外が発生した場合
 * </ul>
 * 
 * @author taedium
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@DaoMethod
public @interface BatchDelete {

    /**
     * SQLファイルにマッピングするかどうかを示します。
     */
    boolean sqlFile() default false;

    /**
     * クエリタイムアウト（秒）です。
     * <p>
     * 指定しない場合、{@link Config#queryTimeout()}が使用されます。
     * 
     * @see Statement#setQueryTimeout(int)
     */
    int queryTimeout() default -1;

    /**
     * DELETE文のWHERE句からバージョン番号を除去するかどうかを示します。
     */
    boolean ignoreVersion() default false;

    /**
     * 更新結果が1件でない場合にスローされる {@link OptimisticLockException}を抑制するかどうかを示します。
     */
    boolean suppressOptimisticLockException() default false;

}
