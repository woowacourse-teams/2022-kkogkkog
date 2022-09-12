import { AxiosError } from 'axios';
import React, { Component, PropsWithChildren } from 'react';

import { NavigationProps } from '../helper/withNavigation';
import { ErrorFallbackProps } from './ErrorFallback';

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

  render() {
    const { fallback: FallbackComponent, children } = this.props;

    const { error } = this.state;

    if (error) {
      return <FallbackComponent error={error} resetErrorBoundary={this.resetErrorBoundary} />;
    }

    return children;
  }
}

export default ErrorBoundary;
