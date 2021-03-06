/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.doma.internal.apt.type;

/**
 * @author taedium
 * 
 */
public class SimpleDataTypeVisitor<R, P, TH extends Throwable> implements
        DataTypeVisitor<R, P, TH> {

    protected R defaultValue;

    public SimpleDataTypeVisitor() {
    }

    public SimpleDataTypeVisitor(R defaultValue) {
        this.defaultValue = defaultValue;
    }

    protected R defaultAction(DataType dataType, P p) throws TH {
        return defaultValue;
    }

    @Override
    public R visitAnyType(AnyType dataType, P p) throws TH {
        return defaultAction(dataType, p);
    }

    @Override
    public R visitBasicType(BasicType dataType, P p) throws TH {
        return defaultAction(dataType, p);
    }

    @Override
    public R visitDomainType(DomainType dataType, P p) throws TH {
        return defaultAction(dataType, p);
    }

    @Override
    public R visitEntityType(EntityType dataType, P p) throws TH {
        return defaultAction(dataType, p);
    }

    @Override
    public R visitIterationCallbackType(IterationCallbackType dataType, P p)
            throws TH {
        return defaultAction(dataType, p);
    }

    @Override
    public R visitIterableType(IterableType dataType, P p) throws TH {
        return defaultAction(dataType, p);
    }

    @Override
    public R visitReferenceType(ReferenceType dataType, P p) throws TH {
        return defaultAction(dataType, p);
    }

    @Override
    public R visitSelectOptionsType(SelectOptionsType dataType, P p) throws TH {
        return defaultAction(dataType, p);
    }

    @Override
    public R visitWrapperType(WrapperType dataType, P p) throws TH {
        return defaultAction(dataType, p);
    }

    @Override
    public R visitEnumWrapperType(EnumWrapperType dataType, P p) throws TH {
        return visitWrapperType(dataType, p);
    }

    @Override
    public R visitMapType(MapType dataType, P p) throws TH {
        return defaultAction(dataType, p);
    }
}
