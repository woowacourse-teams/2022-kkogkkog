import { AxiosError } from 'axios';
import React, { Component, PropsWithChildren } from 'react';

import { NavigationProps } from '../helper/withNavigation';
import { ErrorFallbackProps } from './ErrorFallback';
import { ToastContext, ToastContextType } from './ToastProvider';

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
  PropsWithChildren<ErrorBoundaryProps> & NavigationProps,
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

  render() {
    const { fallback: FallbackComponent, children } = this.props;

    const { error } = this.state;

    const { displayMessage } = this.context as ToastContextType;

    if (error) {
      /** get 메소드일때만 fallback을 띄운다. */
      if (error.response?.config.method === 'get') {
        return <FallbackComponent error={error} resetErrorBoundary={this.resetErrorBoundary} />;
      }

      /** toast 에러 처리 */
      displayMessage((error.response?.data as any).message, true);
    }

    return children;
  }
}

export default ErrorBoundary;
