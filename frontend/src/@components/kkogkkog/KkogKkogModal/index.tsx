import { css } from '@emotion/react';

import Button from '@/@components/@shared/Button';
import Modal from '@/@components/@shared/Modal';
import { KkogKkog } from '@/types/domain';
import { THUMBNAIL } from '@/utils/constants/kkogkkog';

import KkogKkogItem from '../KkogKkogItem';
import * as Styled from './style';

interface KkogKkogItemProps {
  clickedKkogKkog: KkogKkog;
  handleCloseModal: () => void;
}

const KkogKkogModal = (props: KkogKkogItemProps) => {
  const { clickedKkogKkog, handleCloseModal } = props;

  return (
    <Modal position='bottom' onCloseModal={handleCloseModal}>
      <Styled.ModalTop>
        <header>쿠폰을 사용하시겠어요?</header>
        <button onClick={handleCloseModal}>X</button>
      </Styled.ModalTop>
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
        <Styled.ButtonInner
          css={css`
            padding-right: 10px;
          `}
        >
          <Button>사용 완료</Button>
        </Styled.ButtonInner>
        <Styled.ButtonInner
          css={css`
            padding-left: 10px;
          `}
        >
          <Button>사용 요청</Button>
        </Styled.ButtonInner>
      </Styled.ButtonContainer>
    </Modal>
  );
};

export default KkogKkogModal;
