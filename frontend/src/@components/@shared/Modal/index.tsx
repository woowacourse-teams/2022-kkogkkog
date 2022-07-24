import { MouseEvent, PropsWithChildren } from 'react';
import ReactDOM from 'react-dom';

import Dimmed from '@/@components/@shared/Dimmed';

import * as Styled from './style';

interface ModalProps {
  position: 'top' | 'middle' | 'bottom';
  animation?: boolean;
  closeModal: () => void;
}

function Modal(props: PropsWithChildren<ModalProps>) {
  const { position, animation, closeModal, children } = props;

  const onClickDimmed = (e: MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      closeModal();
    }
  };

  return ReactDOM.createPortal(
    <Dimmed position={position} onClick={onClickDimmed}>
      <Styled.Root position={position} animation={animation}>
        {children}
      </Styled.Root>
    </Dimmed>,
    document.querySelector('#root') as Element
  );
}

interface ModalWithHeader extends ModalProps {
  title: string;
}

Modal.WithHeader = function WithHeader(props: PropsWithChildren<ModalWithHeader>) {
  const { position, animation, title, closeModal, children } = props;

  return (
    <Modal position={position} closeModal={closeModal} animation={animation}>
      <Styled.ModalTop>
        <header>{title}</header>
        <button onClick={closeModal}>X</button>
      </Styled.ModalTop>
      {children}
    </Modal>
  );
};

export default Modal;
