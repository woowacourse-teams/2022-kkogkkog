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
    <Modal position='bottom' title='쿠폰을 사용하시겠어요?' onCloseModal={handleCloseModal}>
      <KkogKkogItem
        key={clickedKkogKkog.id}
        {...clickedKkogKkog}
        thumbnail={THUMBNAIL[clickedKkogKkog.couponType]}
        hasCursor={false}
        css={css`
          margin-bottom: 30px;
        `}
      />
      <Styled.Message>{clickedKkogKkog.message}</Styled.Message>
      <Styled.ButtonContainer>
        <Styled.ButtonInner>
          <Button>사용 완료</Button>
        </Styled.ButtonInner>
        <Styled.ButtonInner>
          <Button>사용 요청</Button>
        </Styled.ButtonInner>
      </Styled.ButtonContainer>
    </Modal>
  );
};

export default KkogKkogModal;
