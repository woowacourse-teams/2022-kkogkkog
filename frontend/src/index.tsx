import { Global, ThemeProvider } from '@emotion/react';
import React, { Suspense } from 'react';
import ReactDOM from 'react-dom/client';
import { QueryClient, QueryClientProvider } from 'react-query';

import Loading from '@/@components/@shared/Loading';
import App from '@/App';
import globalStyle from '@/styles/globalStyle';
import theme from '@/styles/theme';

if (process.env.NODE_ENV === 'development') {
  const { worker } = require('./mocks/browser');

  worker.start();
}

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
    },
  },
});

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

root.render(
  <React.StrictMode>
    <Global styles={globalStyle} />
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <Suspense
          fallback={
            <Loading>
              <img src='/assets/images/logo.png' alt='logo' width='80' />
            </Loading>
          }
        >
          <App />
        </Suspense>
      </ThemeProvider>
    </QueryClientProvider>
  </React.StrictMode>
);
