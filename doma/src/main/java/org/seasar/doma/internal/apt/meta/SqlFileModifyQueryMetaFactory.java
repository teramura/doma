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
package org.seasar.doma.internal.apt.meta;

import static org.seasar.doma.internal.util.AssertionUtil.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Update;
import org.seasar.doma.internal.apt.AptException;
import org.seasar.doma.internal.apt.TypeUtil;
import org.seasar.doma.message.DomaMessageCode;

/**
 * @author taedium
 * 
 */
public class SqlFileModifyQueryMetaFactory extends
        AbstractSqlFileQueryMetaFactory<SqlFileModifyQueryMeta> {

    public SqlFileModifyQueryMetaFactory(ProcessingEnvironment env,
            DomainMetaFactory domainMetaFactory) {
        super(env, domainMetaFactory);
    }

    @Override
    public QueryMeta createQueryMeta(ExecutableElement method, DaoMeta daoMeta) {
        assertNotNull(method, daoMeta);
        SqlFileModifyQueryMeta queryMeta = createSqlFileModifyQueryMeta(method,
                daoMeta);
        if (queryMeta == null) {
            return null;
        }
        doTypeParameters(queryMeta, method, daoMeta);
        doReturnType(queryMeta, method, daoMeta);
        doParameters(queryMeta, method, daoMeta);
        doThrowTypes(queryMeta, method, daoMeta);
        doSqlFile(queryMeta, method, daoMeta);
        return queryMeta;
    }

    protected SqlFileModifyQueryMeta createSqlFileModifyQueryMeta(
            ExecutableElement method, DaoMeta daoMeta) {
        SqlFileModifyQueryMeta queryMeta = new SqlFileModifyQueryMeta();
        Insert insert = method.getAnnotation(Insert.class);
        if (insert != null && insert.sqlFile()) {
            queryMeta.setQueryTimeout(insert.queryTimeout());
            queryMeta.setQueryKind(QueryKind.SQLFILE_INSERT);
        }
        Update update = method.getAnnotation(Update.class);
        if (update != null && update.sqlFile()) {
            queryMeta.setQueryTimeout(update.queryTimeout());
            queryMeta.setQueryKind(QueryKind.SQLFILE_UPDATE);
        }
        Delete delete = method.getAnnotation(Delete.class);
        if (delete != null && delete.sqlFile()) {
            queryMeta.setQueryTimeout(delete.queryTimeout());
            queryMeta.setQueryKind(QueryKind.SQLFILE_DELETE);
        }
        if (queryMeta.getQueryKind() == null) {
            return null;
        }
        queryMeta.setName(method.getSimpleName().toString());
        queryMeta.setExecutableElement(method);
        return queryMeta;
    }

    @Override
    protected void doReturnType(SqlFileModifyQueryMeta queryMeta,
            ExecutableElement method, DaoMeta daoMeta) {
        TypeMirror returnType = TypeUtil.resolveTypeParameter(daoMeta
                .getTypeParameterMap(), method.getReturnType());
        if (!isPrimitiveInt(returnType)) {
            throw new AptException(DomaMessageCode.DOMA4001, env, method);
        }
        QueryResultMeta resultMeta = new QueryResultMeta();
        resultMeta.setTypeName(TypeUtil.getTypeName(returnType, env));
        queryMeta.setResultMeta(resultMeta);
    }

    @Override
    protected void doParameters(SqlFileModifyQueryMeta queryMeta,
            ExecutableElement method, DaoMeta daoMeta) {
        for (VariableElement parameter : method.getParameters()) {
            QueryParameterMeta queryParameterMeta = createQueryParameterMeta(parameter);
            if (queryParameterMeta.isCollection()) {
                TypeMirror elementType = queryParameterMeta
                        .getCollectionElementType();
                if (!TypeUtil.isDomain(elementType, env)) {
                    throw new AptException(DomaMessageCode.DOMA4028, env,
                            method);
                }
            } else if (!queryParameterMeta.isEntity()) {
                if (!queryParameterMeta.isBasic()) {
                    throw new AptException(DomaMessageCode.DOMA4008, env,
                            method, queryParameterMeta.getParameterElement());
                }
            }
            queryMeta.addParameterMetas(queryParameterMeta);
            queryMeta.addExpressionParameterType(queryParameterMeta.getName(),
                    queryParameterMeta.getType());
        }
    }

}
