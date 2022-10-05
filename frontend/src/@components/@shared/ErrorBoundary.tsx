import React, { Component, ErrorInfo, PropsWithChildren } from 'react';
import { useQueryClient } from 'react-query';
import { Navigate } from 'react-router-dom';

import { PATH } from '@/Router';
import { CustomAxiosError } from '@/types/utils';

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
      error: CustomAxiosError;
      errorCase: 'unauthorized' | 'get';
    };

const initialState: ErrorBoundaryState = {
  error: null,
  errorCase: null,
};

class ErrorBoundary extends Component<PropsWithChildren<ErrorBoundaryProps>, ErrorBoundaryState> {
  state: ErrorBoundaryState = {
    error: null,
    errorCase: null,
  };

  resetErrorBoundary = () => {
    this.props.onReset?.();
    this.setState(initialState);
  };

  static getDerivedStateFromError(error: Error): ErrorBoundaryState {
    if (!(error instanceof CustomAxiosError)) {
      return { error, errorCase: null };
    }

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

    if (errorCase === 'unauthorized') {
      localStorage.removeItem('user-token');

      displayMessage('다시 로그인해주세요', true);

      return;
    }

    if (errorCase === 'get') {
      displayMessage(errorState.response?.data.message || '알 수 없는 에러가 발생했습니다.', true);

      return;
    }

    if (errorCase === null) {
      displayMessage('알 수 없는 에러가 발생했습니다.', true);
    }
  }

  render() {
    const { fallback: FallbackComponent, children } = this.props;

    const { error, errorCase } = this.state;

    if (errorCase === 'unauthorized') {
      return <Navigate to={PATH.LOGIN} />;
    }

    if (errorCase === 'get') {
      return <FallbackComponent error={error} resetErrorBoundary={this.resetErrorBoundary} />;
    }

    return children;
  }
}

const ErrorBoundaryWithHooks = ({ ...props }: PropsWithChildren<ErrorBoundaryProps>) => {
  const queryClient = useQueryClient();

  const resetError = () => {
    queryClient.clear();
  };

  return <ErrorBoundary onReset={resetError} {...props} />;
};

export default ErrorBoundaryWithHooks;
