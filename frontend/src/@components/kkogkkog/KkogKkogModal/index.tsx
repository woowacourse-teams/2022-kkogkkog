import { css } from '@emotion/react';
import { useState } from 'react';

import Button from '@/@components/@shared/Button';
import Modal from '@/@components/@shared/Modal';
import { KkogKKogResponse } from '@/types/remote/response';

import { ANIMATION_DURATION } from '../../../constants/animation';
import KkogKkogItem from '../KkogKkogItem';
import * as Styled from './style';

interface KkogKkogItemProps {
  kkogkkog: KkogKKogResponse;
  modalTitle: string;
  modalButtons?: {
    text: string;
    onClick: (args: { id: number; message?: string }) => void;
  }[];
  onCloseModal: () => void;
}

const KkogKkogModal = (props: KkogKkogItemProps) => {
  const { kkogkkog, modalTitle, modalButtons, onCloseModal } = props;
  const { id, message } = kkogkkog;

  const [animation, setAnimation] = useState(false);

  return (
    <Modal.WithHeader
      title={modalTitle}
      position='bottom'
      animation={animation}
      onCloseModal={() => {
        setAnimation(true);
        setTimeout(() => {
          onCloseModal();
        }, ANIMATION_DURATION.modal);
      }}
    >
      <KkogKkogItem
        key={id}
        css={css`
          margin-bottom: 16px;
        `}
        {...kkogkkog}
      />
      <Styled.Message>{message}</Styled.Message>
      <Styled.ButtonContainer>
        {modalButtons?.map(({ text, onClick }) => (
          <Button
            key={text}
            onClick={() => {
              onClick({ id });
              setAnimation(true);
              setTimeout(() => {
                onCloseModal();
              }, ANIMATION_DURATION.modal);
            }}
            css={Styled.ExtendedButton}
          >
            {text}
          </Button>
        ))}
      </Styled.ButtonContainer>
    </Modal.WithHeader>
  );
};

export default KkogKkogModal;
