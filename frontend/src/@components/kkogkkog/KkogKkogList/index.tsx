import { css } from '@emotion/react';
import { useState } from 'react';

import Button from '@/@components/@shared/Button';
import Modal from '@/@components/@shared/Modal';
import KkogKkogItem from '@/@components/kkogkkog/KkogKkogItem';
import { THUMBNAIL } from '@/types/client/kkogkkog';
import { KkogKKogResponse } from '@/types/remote/response';

import * as Styled from './style';

interface KkogKkogListProps {
  kkogkkogList: KkogKKogResponse[] | undefined;
  modalTitle: string;
  modalButtons: {
    text: string;
    onClick: (args: { id: number; message?: string }) => void;
  }[];
}

const KkogKkogList = (props: KkogKkogListProps) => {
  const { kkogkkogList, modalTitle, modalButtons } = props;
  const [clickedKkogKkog, setClickedKkogKkog] = useState<KkogKKogResponse | null>(null);

  const onClickKkogKkog = (kkogkkog: KkogKKogResponse & { thumbnail: string }) => {
    setClickedKkogKkog(kkogkkog);
  };

  const onCloseModal = () => {
    setClickedKkogKkog(null);
  };

  if (kkogkkogList?.length === 0) {
    return (
      <Styled.Root>
        <Styled.TextContainer fontSize='40px'>😱</Styled.TextContainer>
        <Styled.TextContainer>해당 꼭꼭이 존재하지 않아요 ㅠㅠ</Styled.TextContainer>
      </Styled.Root>
    );
  }

  return (
    <Styled.Root>
      {kkogkkogList?.map(kkogkkog => (
        <KkogKkogItem
          key={kkogkkog.id}
          thumbnail={THUMBNAIL[kkogkkog.couponType]}
          onClick={() =>
            onClickKkogKkog({ ...kkogkkog, thumbnail: THUMBNAIL[kkogkkog.couponType] })
          }
          {...kkogkkog}
        />
      ))}
      {clickedKkogKkog && (
        <Modal.WithHeader title={modalTitle} position='bottom' onCloseModal={onCloseModal}>
          <KkogKkogItem
            key={clickedKkogKkog.id}
            thumbnail={THUMBNAIL[clickedKkogKkog.couponType]}
            css={css`
              margin-bottom: 16px;
            `}
            {...clickedKkogKkog}
          />
          <Styled.Message>{clickedKkogKkog.message}</Styled.Message>
          <Styled.ButtonContainer>
            {modalButtons.map(({ text, onClick }) => (
              <Button
                key={text}
                onClick={() => {
                  onClick({ id: clickedKkogKkog.id });
                  onCloseModal();
                }}
                css={Styled.ExtendedButton}
              >
                {text}
              </Button>
            ))}
          </Styled.ButtonContainer>
        </Modal.WithHeader>
      )}
    </Styled.Root>
  );
};

export default KkogKkogList;
