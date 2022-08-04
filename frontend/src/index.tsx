import { Global, ThemeProvider } from '@emotion/react';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { QueryClient, QueryClientProvider } from 'react-query';

import ToastProvider from '@/@components/@shared/ToastProvider';
import App from '@/App';
import globalStyle from '@/styles/globalStyle';
import theme from '@/styles/theme';

if (!process.env.NODE_ENV) {
  const { worker } = require('./mocks/browser');

  worker.start();
}

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
      retry: false,
      useErrorBoundary: false,
      staleTime: 6000,
    },
  },
});

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

root.render(
  <React.StrictMode>
    <Global styles={globalStyle} />
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <ToastProvider>
          <App />
        </ToastProvider>
      </ThemeProvider>
    </QueryClientProvider>
  </React.StrictMode>
);
