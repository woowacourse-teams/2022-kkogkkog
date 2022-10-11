import React from 'react';
import ReactDOM from 'react-dom';

import Dimmed from '@/@components/@shared/Dimmed';
import logoImage from '@/assets/images/logo.png';

import * as Styled from './style';

function Loading() {
  return ReactDOM.createPortal(
    <Dimmed>
      <Styled.Root>
        <img src={logoImage} alt='로고' width={36} height={36} />
      </Styled.Root>
    </Dimmed>,
    document.querySelector('#root') as Element
  );
}

export default Loading;
