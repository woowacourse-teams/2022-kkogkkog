import React from 'react';
import ReactDOM from 'react-dom';

import Dimmed from '@/@components/@shared/Dimmed';

import * as Styled from './style';

type LoadingProps = React.PropsWithChildren;

function Loading(props: LoadingProps) {
  const { children } = props;

  return ReactDOM.createPortal(
    <Dimmed>
      <Styled.Root>{children}</Styled.Root>
    </Dimmed>,
    document.querySelector('#root') as Element
  );
}

export default Loading;
