import { useEffect } from 'react';
import { BrowserRouter } from 'react-router-dom';

import ScrollToTop from '@/@components/@shared/ScrollToTop';
import Router from '@/Router';

function setScreenSize() {
  const vh = window.innerHeight * 0.01;

  document.documentElement.style.setProperty('--vh', `${vh}px`);
}

const App = () => {
  useEffect(() => {
    setScreenSize();
  }, []);

  return (
    <BrowserRouter>
      <ScrollToTop />
      <Router />
    </BrowserRouter>
  );
};

export default App;
