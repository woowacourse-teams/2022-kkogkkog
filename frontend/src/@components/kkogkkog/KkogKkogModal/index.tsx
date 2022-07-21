import { css } from '@emotion/react';

import Button from '@/@components/@shared/Button';
import Modal from '@/@components/@shared/Modal';
import { KkogKKogResponse } from '@/types/remote/response';

import KkogKkogItem from '../KkogKkogItem';
import * as Styled from './style';

interface KkogKkogItemProps {
  kkogkkog: KkogKKogResponse & { thumbnail: string };
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

  return (
    <Modal.WithHeader title={modalTitle} position='bottom' onCloseModal={onCloseModal}>
      <KkogKkogItem.Preview
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
              onCloseModal();
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
