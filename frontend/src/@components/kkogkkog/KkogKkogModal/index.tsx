import { css } from '@emotion/react';

import Button from '@/@components/@shared/Button';
import Modal from '@/@components/@shared/Modal';
import { THUMBNAIL } from '@/types/client/kkogkkog';
import { KkogKKogResponse } from '@/types/remote/response';

import KkogKkogItem from '../KkogKkogItem';
import * as Styled from './style';

interface KkogKkogItemProps {
  clickedKkogKkog: KkogKKogResponse;
  onCloseModal: () => void;
}

const KkogKkogModal = (props: KkogKkogItemProps) => {
  const { clickedKkogKkog, onCloseModal } = props;

  return (
    <Modal.WithHeader title='쿠폰을 사용하시겠어요?' position='bottom' onCloseModal={onCloseModal}>
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
        <Button css={Styled.ExtendedButton}>사용 완료</Button>
        <Button css={Styled.ExtendedButton}>사용 요청</Button>
      </Styled.ButtonContainer>
    </Modal.WithHeader>
  );
};

export default KkogKkogModal;
