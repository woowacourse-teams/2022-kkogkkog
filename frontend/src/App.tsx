import { useEffect } from 'react';
import { BrowserRouter } from 'react-router-dom';

import ScrollToTop from '@/@components/@shared/ScrollToTop';
import Router from '@/Router';

import ErrorBoundaryWithHooks from './@components/@shared/ErrorBoundary';
import ErrorFallback from './@components/@shared/ErrorFallback';

function setScreenSize() {
  const vh = window.innerHeight * 0.01;

  document.documentElement.style.setProperty('--vh', `${vh}px`);
}

const App = () => {
  useEffect(() => {
    setScreenSize();
  }, []);

  useEffect(() => {
    const onResize = () => {
      setScreenSize();
    };

    window.addEventListener('resize', onResize);

    return () => {
      window.removeEventListener('resize', onResize);
    };
  }, []);

  return (
    /** ErrorBoundary에서 useNavigation을 사용하기 위해 BrowserRouter안에 넣는다. */
    <BrowserRouter>
      <ErrorBoundaryWithHooks fallback={ErrorFallback}>
        <ScrollToTop />
        <Router />
      </ErrorBoundaryWithHooks>
    </BrowserRouter>
  );
};

export default App;
