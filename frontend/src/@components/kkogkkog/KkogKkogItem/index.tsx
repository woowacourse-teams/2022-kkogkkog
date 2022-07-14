import { css } from '@emotion/react';

import Button from '@/@components/@shared/Button';
import Modal from '@/@components/@shared/Modal';
import Placeholder from '@/@components/@shared/Placeholder';
import { KkogKkog } from '@/types/domain';
import { THUMBNAIL } from '@/utils/constants/kkogkkog';

import * as Styled from './style';

type KkogKkogItemProps = KkogKkog & { onClickKkogKkog?: () => void; className?: string };

const KkogKkogItem = (props: KkogKkogItemProps) => {
  const {
    senderName,
    receiverName,
    backgroundColor,
    modifier,
    couponType,
    thumbnail,
    onClickKkogKkog,
    className,
  } = props;

  return (
    <Styled.Root onClick={onClickKkogKkog} className={className}>
      <Styled.TextContainer>
        <div>From. {senderName}</div>
        <div>To. {receiverName}</div>
        <div>
          #{modifier} &nbsp;
          <Styled.TypeText>
            {couponType}
            &nbsp;꼭꼭
          </Styled.TypeText>
        </div>
      </Styled.TextContainer>
      <Styled.ImageContainer backgroundColor={backgroundColor}>
        <img src={thumbnail} alt='쿠폰' />
      </Styled.ImageContainer>
    </Styled.Root>
  );
};

KkogKkogItem.LinkButton = function LinkButton() {
  return (
    <Styled.Root>
      <Styled.LinkButtonContainer>
        <div>+</div>
        <Styled.LinkButtonText>꼭꼭을 생성해보세요 !</Styled.LinkButtonText>
      </Styled.LinkButtonContainer>
    </Styled.Root>
  );
};
KkogKkogItem.Skeleton = function Skeleton() {
  return <Placeholder aspectRatio='3/1' />;
};

interface KkogKkogItemModalProps {
  clickedKkogKkog: KkogKkog;
  handleCloseModal: () => void;
}

KkogKkogItem.Modal = function KkogKkogItemModal(props: KkogKkogItemModalProps) {
  const { clickedKkogKkog, handleCloseModal } = props;

  return (
    <Modal position='bottom' title='쿠폰을 사용하시겠어요?' onCloseModal={handleCloseModal}>
      <KkogKkogItem
        key={clickedKkogKkog.id}
        {...clickedKkogKkog}
        thumbnail={THUMBNAIL[clickedKkogKkog.couponType]}
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

export default KkogKkogItem;
