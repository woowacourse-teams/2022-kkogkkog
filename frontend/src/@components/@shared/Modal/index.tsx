import React from 'react';
import ReactDOM from 'react-dom';

import Dimmed from '@/@components/@shared/Dimmed';

import * as Styled from './style';

interface ModalProps {
  title: string;
  position: 'top' | 'middle' | 'bottom';
  onCloseModal: () => void;
}

function Modal(props: React.PropsWithChildren<ModalProps>) {
  const { title, position, onCloseModal, children } = props;

  return ReactDOM.createPortal(
    <Dimmed position={position} onCloseModal={onCloseModal}>
      <Styled.Root position={position}>
        <Styled.ModalTop>
          <header>{title}</header>
          <button onClick={onCloseModal}>X</button>
        </Styled.ModalTop>
        {children}
      </Styled.Root>
    </Dimmed>,
    document.querySelector('#root') as Element
  );
}

export default Modal;
