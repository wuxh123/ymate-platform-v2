/*
 * Copyright 2007-2018 the original author or authors.
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
package net.ymate.platform.serv.nio.server;

import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.serv.AbstractSessionManager;
import net.ymate.platform.serv.IServ;
import net.ymate.platform.serv.IServer;
import net.ymate.platform.serv.IServerCfg;
import net.ymate.platform.serv.nio.INioCodec;
import net.ymate.platform.serv.nio.INioSession;
import net.ymate.platform.serv.nio.INioSessionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * TCP客户端会话管理器
 *
 * @author 刘镇 (suninformation@163.com) on 2018/11/12 3:40 PM
 * @version 1.0
 */
public class NioSessionManager<SESSION_WRAPPER extends NioSessionWrapper, MESSAGE_TYPE> extends AbstractSessionManager<SESSION_WRAPPER> {

    private static final Log _LOG = LogFactory.getLog(NioSessionManager.class);

    private INioSessionListener<SESSION_WRAPPER, MESSAGE_TYPE> __listener;

    public NioSessionManager(IServerCfg serverCfg, INioCodec codec, INioSessionListener<SESSION_WRAPPER, MESSAGE_TYPE> listener) {
        super(serverCfg, codec);
        __listener = listener;
    }

    /**
     * 注册客户端会话
     *
     * @param session 会话对象
     */
    private SESSION_WRAPPER __doRegisterSession(INioSession session) {
        SESSION_WRAPPER _wrapper = doBuildSessionWrapper(session);
        if (doRegister(_wrapper)) {
            putSessionWrapper(_wrapper.getId(), _wrapper);
            return _wrapper;
        }
        return null;
    }

    private SESSION_WRAPPER __doRemoveSession(String sessionId) {
        return removeSessionWrapper(sessionId);
    }

    /**
     * 执行会话注册逻辑
     *
     * @param session 会话包装器对象
     * @return 返回值为false表示不向管理器注册当前会话
     */
    protected boolean doRegister(SESSION_WRAPPER session) {
        return true;
    }

    @SuppressWarnings("unchecked")
    protected SESSION_WRAPPER doBuildSessionWrapper(INioSession session) {
        return (SESSION_WRAPPER) new NioSessionWrapper(session);
    }

    @Override
    protected IServer doBuildServer(IServ owner, IServerCfg serverCfg, INioCodec codec) {
        return owner.buildServer(serverCfg, codec, new NioServerListener() {
            @Override
            public void onSessionRegistered(INioSession session) throws IOException {
                SESSION_WRAPPER _wrapper = __doRegisterSession(session);
                if (_wrapper != null) {
                    if (_LOG.isDebugEnabled()) {
                        _LOG.debug(_wrapper + " - Registered. Session count: " + getSessionCount());
                    }
                    __listener.onSessionRegistered(_wrapper);
                }
            }

            @Override
            public void onSessionAccepted(INioSession session) throws IOException {
                super.onSessionAccepted(session);
                SESSION_WRAPPER _wrapper = getSessionWrapper(session.id());
                if (_wrapper != null) {
                    if (_LOG.isDebugEnabled()) {
                        _LOG.debug(_wrapper + " - Accepted.");
                    }
                    __listener.onSessionAccepted(_wrapper);
                }
            }

            @Override
            public void onBeforeSessionClosed(INioSession session) throws IOException {
                SESSION_WRAPPER _wrapper = getSessionWrapper(session.id());
                if (_wrapper != null) {
                    if (_LOG.isDebugEnabled()) {
                        _LOG.debug(_wrapper + " - Before closed.");
                    }
                    __listener.onBeforeSessionClosed(_wrapper);
                }
            }

            @Override
            public void onAfterSessionClosed(INioSession session) throws IOException {
                SESSION_WRAPPER _wrapper = __doRemoveSession(session.id());
                if (_wrapper != null) {
                    if (_LOG.isDebugEnabled()) {
                        _LOG.debug(_wrapper + " - After closed. Session count: " + getSessionCount());
                    }
                    __listener.onAfterSessionClosed(_wrapper);
                }
            }

            @Override
            @SuppressWarnings("unchecked")
            public void onMessageReceived(Object message, INioSession session) throws IOException {
                SESSION_WRAPPER _wrapper = getSessionWrapper(session.id());
                if (_wrapper != null) {
                    if (_LOG.isDebugEnabled()) {
                        _LOG.debug(_wrapper + " - Received: " + message);
                    }
                    speedTouch();
                    _wrapper.heartbeatTouch();
                    __listener.onMessageReceived((MESSAGE_TYPE) message, _wrapper);
                }
            }

            @Override
            public void onExceptionCaught(Throwable e, INioSession session) throws IOException {
                SESSION_WRAPPER _wrapper = getSessionWrapper(session.id());
                if (_wrapper != null) {
                    if (_LOG.isDebugEnabled()) {
                        _LOG.debug(_wrapper + " - Exception: ", RuntimeUtils.unwrapThrow(e));
                    }
                    __listener.onExceptionCaught(e, _wrapper);
                }
            }
        });
    }
}
