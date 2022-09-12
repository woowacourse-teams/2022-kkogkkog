import { AxiosError } from 'axios';
import React, { Component, ErrorInfo, PropsWithChildren } from 'react';

import { PATH } from '@/Router';

import { ErrorFallbackProps } from './ErrorFallback';
import { ToastContext, ToastContextType } from './ToastProvider';
import { useQueryClient } from 'react-query';
import { NavigateFunction, useNavigate } from 'react-router-dom';

interface ErrorBoundaryProps {
  fallback: React.ComponentType<ErrorFallbackProps>;
  onReset?: () => void;
}

interface ErrorBoundaryState {
  error: AxiosError | null;
}

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

  static getDerivedStateFromError(error: AxiosError): ErrorBoundaryState {
    return { error };
  }

  static contextType = ToastContext;

  componentDidCatch(error: AxiosError, errorInfo: ErrorInfo) {
    const { navigate } = this.props;

    const { displayMessage } = this.context as ToastContextType;

    if (error.response?.status === 401) {
      localStorage.removeItem('user-token');
      displayMessage('다시 로그인해주세요', true);
      navigate(PATH.LOGIN);
    } else {
      displayMessage((error.response?.data as any).message, true);
    }
  }

  render() {
    const { fallback: FallbackComponent, children } = this.props;

    const { error } = this.state;

    if (error) {
      /** get 메소드일때만 fallback을 띄운다. */
      if (error.response?.config.method === 'get') {
        return <FallbackComponent error={error} resetErrorBoundary={this.resetErrorBoundary} />;
      }
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
