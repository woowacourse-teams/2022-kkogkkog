import { ThemeProvider } from '@emotion/react';
import type { RenderOptions } from '@testing-library/react';
import { render } from '@testing-library/react';
import { PropsWithChildren, ReactElement } from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';

import LoadingProvider from '@/@components/@shared/LoadingProvider';
import ToastProvider from '@/@components/@shared/ToastProvider';
import theme from '@/styles/theme';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
      retry: false,
      useErrorBoundary: false,
    },
  },
});

const AllTheProviders = ({ children }: PropsWithChildren) => {
  const Root = document.createElement('div');

  Root.setAttribute('id', 'root');
  document.body.appendChild(Root);

  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <ToastProvider>
          <LoadingProvider>
            <BrowserRouter>{children}</BrowserRouter>
          </LoadingProvider>
        </ToastProvider>
      </ThemeProvider>
    </QueryClientProvider>
  );
};

const customRender = (ui: ReactElement, options?: Omit<RenderOptions, 'wrapper'>) =>
  render(ui, { wrapper: AllTheProviders, ...options });

export { customRender as render };
