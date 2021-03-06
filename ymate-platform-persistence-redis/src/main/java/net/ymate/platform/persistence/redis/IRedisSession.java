/*
 * Copyright 2007-2017 the original author or authors.
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
package net.ymate.platform.persistence.redis;

import net.ymate.platform.persistence.ISessionBase;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/30 下午11:53
 * @version 1.0
 */
public interface IRedisSession extends ISessionBase {

    /**
     * @return 命令对象持有者实例
     */
    IRedisCommandsHolder getCommandHolder();
}
