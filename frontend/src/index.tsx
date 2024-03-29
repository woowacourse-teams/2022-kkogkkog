import { Global, ThemeProvider } from '@emotion/react';
import ReactDOM from 'react-dom/client';
import { QueryClient, QueryClientProvider } from 'react-query';
import { ReactQueryDevtools } from 'react-query/devtools';

import LoadingProvider from '@/@components/@shared/LoadingProvider';
import ToastProvider from '@/@components/@shared/ToastProvider';
import App from '@/App';
import globalStyle from '@/styles/globalStyle';
import theme from '@/styles/theme';

if (PRODUCT_ENV === 'local') {
  const { worker } = require('./mocks/browser');

  worker.start();
}

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
      retry: false,
      useErrorBoundary: true,
    },
    mutations: {
      useErrorBoundary: true,
    },
  },
});

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

root.render(
  <>
    <link
      rel='stylesheet'
      as='style'
      crossOrigin=''
      href='https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.5/dist/web/static/pretendard-dynamic-subset.css'
    />
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools initialIsOpen={false} />
      <ThemeProvider theme={theme}>
        <ToastProvider>
          <LoadingProvider>
            <Global styles={globalStyle} />
            <App />
          </LoadingProvider>
        </ToastProvider>
      </ThemeProvider>
    </QueryClientProvider>
  </>
);
