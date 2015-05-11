/*
 * Copyright 2007-2107 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.persistence.jdbc.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 15/5/7 下午6:15
 * @version 1.0
 */
public class Params {

    /**
     * SQL参数集合
     */
    private List<Object> __params;

    public static Params create() {
        return new Params();
    }

    private Params() {
        this.__params = new ArrayList<Object>();
    }

    public List<Object> getParams() {
        return this.__params;
    }

    public Params add(Object param) {
        this.__params.add(param);
        return this;
    }

    public Params add(Params params) {
        this.__params.addAll(params.getParams());
        return this;
    }

    public Params addAll(Collection<Object> params) {
        this.__params.addAll(params);
        return this;
    }

    public Object[] toArray() {
        return __params.toArray(new Object[__params.size()]);
    }
}