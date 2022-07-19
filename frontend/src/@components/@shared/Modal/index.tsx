import React, { PropsWithChildren } from 'react';
import ReactDOM from 'react-dom';

import Dimmed from '@/@components/@shared/Dimmed';

import Button from '../Button';
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

interface ModalWithHeader extends ModalProps {
  title: string;
  buttons: {
    text: string;
    onClick: () => void;
  }[];
}

Modal.WithHeader = function WithHeader(props: PropsWithChildren<ModalWithHeader>) {
  const { position, onCloseModal, title, buttons, children } = props;

  return (
    <Modal position={position} onCloseModal={onCloseModal}>
      <Styled.ModalTop>
        <header>{title}</header>
        <button onClick={onCloseModal}>X</button>
      </Styled.ModalTop>
      {children}
      <Styled.ButtonContainer>
        {buttons.map(({ text, onClick }) => (
          <Styled.ButtonInner key={text} onClick={onClick}>
            <Button>{text}</Button>
          </Styled.ButtonInner>
        ))}
      </Styled.ButtonContainer>
    </Modal>
  );
};

export default Modal;
