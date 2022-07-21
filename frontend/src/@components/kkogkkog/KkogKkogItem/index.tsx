import { useMutation, useQueryClient } from 'react-query';

import Placeholder from '@/@components/@shared/Placeholder';
import { useModal } from '@/@hooks/@common/useModal';
import { useKkogKkogList } from '@/@hooks/kkogkkog/useKkogKkogList';
import useMe from '@/@hooks/user/useMe';
import { changeKkogkkogStatus } from '@/apis/kkogkkog';
import { KKOGKKOG_TYPE_MAPPER } from '@/types/client/kkogkkog';
import { KkogKKogResponse } from '@/types/remote/response';

import KkogKkogModal from '../KkogKkogModal';
import * as Styled from './style';

type KkogKkogItemProps = KkogKKogResponse & {
  className?: string;
  thumbnail: string;
};

type KkogKkogItemPreviewProps = Omit<KkogKKogResponse, 'id' | 'sender' | 'couponStatus'> & {
  className?: string;
  thumbnail: string;
};

/* 클릭 시 모달을 띄우는 쿠폰 컴포넌트 */
const KkogKkogItem = (props: KkogKkogItemProps) => {
  const { className, ...kkogkkog } = props;

  const { sender, receiver, backgroundColor, modifier, couponType, couponStatus, thumbnail } =
    kkogkkog;

  const { me } = useMe();

  const { isShowModal, openModal, closeModal } = useModal();

  const { refetch } = useKkogKkogList();

  const queryClient = useQueryClient();

  const changeStatusMutate = useMutation(changeKkogkkogStatus, {
    onSuccess() {
      queryClient.invalidateQueries('kkogkkogList');
      refetch();
    },
  });

  const modalType: Record<
    string,
    {
      modalTitle: string;
      modalButtons: { text: string; onClick: (args: { id: number; message?: string }) => void }[];
    }
  > = {
    REQUESTED: {
      modalTitle: '쿠폰 사용 요청을 취소하시겠어요?',
      modalButtons: [
        {
          text: '요청 취소',
          onClick({ id, message }) {
            changeStatusMutate.mutate({ id, body: { couponEvent: 'CANCEL', message } });
          },
        },
      ],
    },
    READY: {
      modalTitle: '쿠폰을 사용하시겠어요?',
      modalButtons: [
        {
          text: '사용 요청',
          onClick({ id, message }) {
            changeStatusMutate.mutate({ id, body: { couponEvent: 'REQUEST', message } });
          },
        },
        {
          text: '사용 완료',
          onClick() {
            console.log('사용 완료합니다.');
          },
        },
      ],
    },
  };

  const modalInfo = modalType[couponStatus];

  return (
    <>
      <Styled.Root
        onClick={() => {
          openModal();
        }}
        className={className}
      >
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
      {isShowModal && (
        <KkogKkogModal kkogkkog={kkogkkog} onCloseModal={closeModal} {...modalInfo} />
      )}
    </>
  );
};

/* UI에서 보이지 않는 id, couponStatus를 제외한 props만 받는 프로토타입 컴포넌트 */
KkogKkogItem.Preview = function Preview(props: KkogKkogItemPreviewProps) {
  const { className, receiver, backgroundColor, modifier, couponType, thumbnail } = props;

  return (
    <Styled.Root className={className} hasCursor={false}>
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
