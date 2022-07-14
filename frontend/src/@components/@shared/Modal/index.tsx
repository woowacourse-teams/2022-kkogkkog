import React from 'react';
import ReactDOM from 'react-dom';

import Dimmed from '@/@components/@shared/Dimmed';

import * as Styled from './style';

interface ModalProps {
  position: 'top' | 'middle' | 'bottom';
  onCloseModal: () => void;
}

function Modal(props: React.PropsWithChildren<ModalProps>) {
  const { position, onCloseModal, children } = props;

  return ReactDOM.createPortal(
    <Dimmed
      position={position}
      onClick={e => {
        if (e.target === e.currentTarget) {
          onCloseModal();
        }
      }}
    >
      <Styled.Root position={position}>{children}</Styled.Root>
    </Dimmed>,
    document.querySelector('#root') as Element
  );
}

export default Modal;
