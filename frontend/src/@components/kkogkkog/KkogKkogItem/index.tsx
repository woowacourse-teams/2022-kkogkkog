import Placeholder from '@/@components/@shared/Placeholder';
import useMe from '@/@hooks/user/useMe';
import { KKOGKKOG_TYPE_MAPPER } from '@/types/client/kkogkkog';
import { KkogKKogResponse } from '@/types/remote/response';

import * as Styled from './style';

/** 폼에서 미리 생성될 쿠폰 UI를 보여준다면 이 때는 id, couponStatus가 존재하지 않는다... */
type KkogKkogItemProps = KkogKKogResponse & {
  onClick?: () => void;
  className?: string;
  thumbnail: string;
};

type KkogKkogItemPreviewProps = Omit<KkogKKogResponse, 'id' | 'sender' | 'couponStatus'> & {
  className?: string;
  thumbnail: string;
};

const KkogKkogItem = (props: KkogKkogItemProps) => {
  const { className, sender, receiver, backgroundColor, modifier, couponType, thumbnail, onClick } =
    props;

  const { me } = useMe();

  return (
    <Styled.Root onClick={onClick} className={className} hasCursor={!!onClick}>
      <Styled.TextContainer>
        {sender.id === me?.id ? (
          <div>To. {receiver.nickname}</div>
        ) : (
          <div>From. {sender.nickname}</div>
        )}
        <div>
          #{modifier} &nbsp;
          <Styled.TypeText>{KKOGKKOG_TYPE_MAPPER[couponType]}</Styled.TypeText>
          &nbsp;꼭꼭
        </div>
      </Styled.TextContainer>
      <Styled.ImageContainer backgroundColor={backgroundColor}>
        <img src={thumbnail} alt='쿠폰' />
      </Styled.ImageContainer>
    </Styled.Root>
  );
};

KkogKkogItem.Preview = function Preview(props: KkogKkogItemPreviewProps) {
  const { className, receiver, backgroundColor, modifier, couponType, thumbnail } = props;

  return (
    <Styled.Root className={className}>
      <Styled.TextContainer>
        <div>To. {receiver.nickname}</div>
        <div>
          #{modifier} &nbsp;
          <Styled.TypeText>{KKOGKKOG_TYPE_MAPPER[couponType]}</Styled.TypeText>
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
