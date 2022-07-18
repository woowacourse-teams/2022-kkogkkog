import Placeholder from '@/@components/@shared/Placeholder';
import { KKOGKKOG_TYPE_KOR } from '@/types/client/kkogkkog';
import { KkogKKogResponse } from '@/types/remote/response';

import * as Styled from './style';

type KkogKkogItemProps = KkogKKogResponse & {
  onClick?: () => void;
  className?: string;
  thumbnail: string;
};

const KkogKkogItem = (props: KkogKkogItemProps) => {
  const { className, sender, receiver, backgroundColor, modifier, couponType, thumbnail, onClick } =
    props;

  return (
    <Styled.Root onClick={onClick} className={className} hasCursor={!!onClick}>
      <Styled.TextContainer>
        <div>From. {sender.nickname}</div>
        <div>To. {receiver.nickname}</div>
        <div>
          #{modifier} &nbsp;
          <Styled.TypeText>{KKOGKKOG_TYPE_KOR[couponType]}</Styled.TypeText>
          &nbsp;꼭꼭
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
