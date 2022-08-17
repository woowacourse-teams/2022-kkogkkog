import React from 'react';
import ReactDOM from 'react-dom';

import Dimmed from '@/@components/@shared/Dimmed';

import * as Styled from './style';

function Loading() {
  return ReactDOM.createPortal(
    <Dimmed backgroundColor='white'>
      <Styled.Root>
        <img src='/assets/images/logo.png' alt='로고' width='36' />
      </Styled.Root>
    </Dimmed>,
    document.querySelector('#root') as Element
  );
}

export default Loading;
