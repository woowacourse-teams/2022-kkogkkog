import { AxiosError } from 'axios';
import React, { Component, ErrorInfo, PropsWithChildren } from 'react';
import { useQueryClient } from 'react-query';
import type { NavigateFunction } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

import { PATH } from '@/Router';

import { ErrorFallbackProps } from './ErrorFallback';
import { ToastContext, ToastContextType } from './ToastProvider';

interface ErrorBoundaryProps {
  fallback: React.ComponentType<ErrorFallbackProps>;
  onReset?: () => void;
}

type ErrorBoundaryState =
  | {
      error: null;
      errorCase: null;
    }
  | {
      error: Error;
      errorCase: null;
    }
  | {
      error: AxiosError;
      errorCase: 'unauthorized' | 'get';
    };

const initialState: ErrorBoundaryState = {
  error: null,
  errorCase: null,
};

class ErrorBoundary extends Component<
  PropsWithChildren<ErrorBoundaryProps> & {
    navigate: NavigateFunction;
  },
  ErrorBoundaryState
> {
  state: ErrorBoundaryState = {
    error: null,
    errorCase: null,
  };

  resetErrorBoundary = () => {
    this.props.onReset?.();
    this.setState(initialState);
  };

  static getDerivedStateFromError(error: Error): ErrorBoundaryState {
    if (!(error instanceof AxiosError)) {
      return { error, errorCase: null };
    }

    /** 401이라면 unauthorized에 get이어도 이 에러이다. */
    if (error.response?.status === 401) {
      return {
        error,
        errorCase: 'unauthorized',
      };
    }

    if (error.response?.config.method === 'get') {
      return {
        error,
        errorCase: 'get',
      };
    }

    return { error, errorCase: null };
  }

  static contextType = ToastContext;

  componentDidCatch(error: Error, errorInfo: ErrorInfo) {
    const { displayMessage } = this.context as ToastContextType;

    const { error: errorState, errorCase } = this.state;

    const { navigate } = this.props;

    if (errorCase === 'unauthorized') {
      localStorage.removeItem('user-token');

      displayMessage('다시 로그인해주세요', true);
      navigate(PATH.LOGIN);

      return;
    }

    if (errorCase === 'get') {
      displayMessage((errorState.response?.data as any).message, true);

      return;
    }

    if (errorCase === null) {
      displayMessage('알 수 없는 에러가 발생했습니다.', true);
    }
  }

  render() {
    const { fallback: FallbackComponent, children } = this.props;

    const { error, errorCase } = this.state;

    if (errorCase === 'get') {
      return <FallbackComponent error={error} resetErrorBoundary={this.resetErrorBoundary} />;
    }

    return children;
  }
}

const ErrorBoundaryWithHooks = ({ ...props }: PropsWithChildren<ErrorBoundaryProps>) => {
  /** useQueryErrorResetBoundary 의도한 대로 잘 동작하지 않는다. */
  // const { reset } = useQueryErrorResetBoundary();
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const resetError = () => {
    queryClient.clear();
  };

  return <ErrorBoundary navigate={navigate} onReset={resetError} {...props} />;
};

export default ErrorBoundaryWithHooks;
