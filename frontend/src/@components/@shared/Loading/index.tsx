import React from 'react';
import ReactDOM from 'react-dom';

import { Dimmed } from '@/@components/@shared/Dimmed';

import * as Styled from './style';

function Loading({ children }: React.PropsWithChildren) {
  return ReactDOM.createPortal(
    <Dimmed>
      <Styled.Root>{children}</Styled.Root>
    </Dimmed>,
    document.querySelector('#root') as Element
  );
}

export default Loading;
