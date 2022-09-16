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
    }
  | {
      error: Error;
    }
  | {
      error: AxiosError;
      errorCase: 'unauthorized' | 'get';
    };

const initialState: ErrorBoundaryState = {
  error: null,
};

class ErrorBoundary extends Component<
  PropsWithChildren<ErrorBoundaryProps> & {
    navigate: NavigateFunction;
  },
  ErrorBoundaryState
> {
  state: ErrorBoundaryState = {
    error: null,
  };

  resetErrorBoundary = () => {
    this.props.onReset?.();
    this.setState(initialState);
  };

  static getDerivedStateFromError(error: Error): ErrorBoundaryState {
    if (!(error instanceof AxiosError)) {
      return { error };
    }

    /** 우선순위를 고려하여 배치한다. */
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

    return { error };
  }

  static contextType = ToastContext;

  componentDidCatch(error: Error, errorInfo: ErrorInfo) {
    const { displayMessage } = this.context as ToastContextType;

    if (!('errorCase' in this.state)) {
      displayMessage('알 수 없는 에러가 발생했습니다.', true);

      return;
    }

    const { navigate } = this.props;

    const { error: errorState, errorCase } = this.state;

    if (errorCase === 'unauthorized') {
      localStorage.removeItem('user-token');
      displayMessage('다시 로그인해주세요', true);
      navigate(PATH.LOGIN);
    } else {
      displayMessage((errorState.response?.data as any).message, true);
    }
  }

  render() {
    const { fallback: FallbackComponent, children } = this.props;

    if (!('errorCase' in this.state)) {
      return children;
    }

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
