import Placeholder from '@/@components/@shared/Placeholder';
import { KkogKkog } from '@/types/domain';

import * as Styled from './style';

type KkogKkogItemProps = KkogKkog & {
  onClick?: () => void;
  className?: string;
  thumbnail: string;
};

const KkogKkogItem = (props: KkogKkogItemProps) => {
  const {
    className,
    senderName,
    receiverName,
    backgroundColor,
    modifier,
    couponType,
    thumbnail,
    onClick,
  } = props;

  return (
    <Styled.Root onClick={onClick} className={className} hasCursor={!!onClick}>
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

export default KkogKkogItem;
