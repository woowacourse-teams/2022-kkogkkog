import { css } from '@emotion/react';

import Button from '@/@components/@shared/Button';
import Modal from '@/@components/@shared/Modal';
import { THUMBNAIL } from '@/types/client/kkogkkog';
import { KkogKKogResponse } from '@/types/remote/response';

import KkogKkogItem from '../KkogKkogItem';
import * as Styled from './style';

interface KkogKkogItemProps {
  clickedKkogKkog: KkogKKogResponse;
  handleCloseModal: () => void;
}

const KkogKkogModal = (props: KkogKkogItemProps) => {
  const { clickedKkogKkog, handleCloseModal } = props;

  const buttons = [
    { text: '사용 완료', onClick: () => {} },
    { text: '사용 요청', onClick: () => {} },
  ];

  return (
    <Modal.WithHeader
      position='bottom'
      onCloseModal={handleCloseModal}
      title='쿠폰을 사용하시겠어요?'
      buttons={buttons}
    >
      <KkogKkogItem
        key={clickedKkogKkog.id}
        thumbnail={THUMBNAIL[clickedKkogKkog.couponType]}
        css={css`
          margin-bottom: 16px;
        `}
        {...clickedKkogKkog}
      />
      <Styled.Message>{clickedKkogKkog.message}</Styled.Message>
    </Modal.WithHeader>
  );
};

export default KkogKkogModal;
