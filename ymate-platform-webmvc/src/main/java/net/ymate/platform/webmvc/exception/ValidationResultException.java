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
package net.ymate.platform.webmvc.exception;

import net.ymate.platform.webmvc.view.IView;

/**
 * 用于在验证器中设置控制器响应
 *
 * @author 刘镇 (suninformation@163.com) on 2018/8/12 下午3:56
 * @version 1.0
 * @since 2.0.6
 */
public class ValidationResultException extends RuntimeException {

    private int httpStatus;

    private IView resultView;

    public ValidationResultException(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ValidationResultException(IView resultView) {
        this.resultView = resultView;
    }

    public ValidationResultException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ValidationResultException(String message, Throwable cause, int httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public ValidationResultException(Throwable cause, int httpStatus) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    public ValidationResultException(int httpStatus, IView resultView) {
        this.httpStatus = httpStatus;
        this.resultView = resultView;
    }

    public ValidationResultException(String message, int httpStatus, IView resultView) {
        super(message);
        this.httpStatus = httpStatus;
        this.resultView = resultView;
    }

    public ValidationResultException(String message, Throwable cause, int httpStatus, IView resultView) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.resultView = resultView;
    }

    public ValidationResultException(Throwable cause, int httpStatus, IView resultView) {
        super(cause);
        this.httpStatus = httpStatus;
        this.resultView = resultView;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public IView getResultView() {
        return resultView;
    }
}
