import { css } from '@emotion/react';
import { ChangeEventHandler, useState } from 'react';

import Button from '@/@components/@shared/Button';
import Modal from '@/@components/@shared/Modal';
import useKkogKkogStatusMutation from '@/@hooks/kkogkkog/useKkogKkogStatusMutation';
import useMe from '@/@hooks/user/useMe';
import { ANIMATION_DURATION } from '@/constants/animation';
import { KkogKKogResponse } from '@/types/remote/response';
import { getToday } from '@/utils';

import BigKkogKkogItem from '../KkogKkogItem/big';
import * as Styled from './style';

interface KkogKkogItemProps {
  kkogkkog: KkogKKogResponse;
  closeModal: () => void;
}

const receivedKkogkkogModalMapper = {
  REQUESTED: {
    title: '쿠폰 사용 요청을 취소하시겠어요?',
    buttons: ['취소'],
  },
  ACCEPTED: {
    title: '쿠폰을 사용하셨나요?',
    buttons: ['완료'],
  },
  FINISHED: {
    title: '이미 사용한 쿠폰입니다.',
    buttons: ['취소'],
  },
  READY: {
    title: '쿠폰을 사용하시겠어요?',
    buttons: ['요청', '완료'],
  },
};

const sentKkogkkogModalMapper = {
  REQUESTED: {
    title: '쿠폰 사용 요청을 승인하시겠어요?',
    buttons: ['승인'],
  },
  ACCEPTED: {
    title: '쿠폰 사용하셨나요??',
    buttons: ['완료'],
  },
  FINISHED: {
    title: '이미 사용한 쿠폰입니다.',
    buttons: [],
  },
  READY: {
    title: '보낸 쿠폰입니다.',
    buttons: [],
  },
};

// 꼭꼭의 상태에 따라, 로그인 한 유저에게 어떤 꼭꼭인지에 따라 (내가 보낸건지, 받은건지) -> 모달은 이 정보에만 따라야함
// 꼭꼭의 상태를 변경하는 모달이니.. 이름을 제대로 줘봐야하지 않을까?
// 버튼은 상태 변경 액션 별로 한개씩 있다.

const KkogKkogModal = (props: KkogKkogItemProps) => {
  const { kkogkkog, closeModal } = props;
  const { id, sender, couponStatus } = kkogkkog;

  const { data: me } = useMe();

  const [animation, setAnimation] = useState(false);

  const [meetingDate, setMeetingDate] = useState('');

  const { cancelKkogKkog, requestKkogKKog, finishKkogKkog, acceptKkogKkog } =
    useKkogKkogStatusMutation();

  const isSent = me?.id === sender.id;

  const { title, buttons } = isSent
    ? sentKkogkkogModalMapper[couponStatus]
    : receivedKkogkkogModalMapper[couponStatus];

  const onChangeMeetingDate: ChangeEventHandler<HTMLInputElement> = e => {
    const {
      target: { value },
    } = e;

    setMeetingDate(value);
  };

  const onCloseModal = () => {
    setAnimation(true);
    setTimeout(() => {
      closeModal();
    }, ANIMATION_DURATION.modal);
  };

  const onClickCancelButton = () => {
    cancelKkogKkog(
      { id },
      {
        onSuccess() {
          onCloseModal();
        },
      }
    );
  };

  const onClickRequestButton = () => {
    if (!meetingDate) {
      alert('날짜를 입력하고 요청해주세요.');

      return;
    }
    requestKkogKKog(
      { id, meetingDate },
      {
        onSuccess() {
          onCloseModal();
        },
      }
    );
  };

  const onClickFinishButton = () => {
    finishKkogKkog(
      { id },
      {
        onSuccess() {
          onCloseModal();
        },
      }
    );
  };

  const onClickAcceptButton = () => {
    acceptKkogKkog(
      { id },
      {
        onSuccess() {
          onCloseModal();
        },
      }
    );
  };

  return (
    <Modal.WithHeader
      title={title}
      position='bottom'
      animation={animation}
      closeModal={onCloseModal}
    >
      <BigKkogKkogItem
        key={id}
        css={css`
          margin-bottom: 16px;
        `}
        {...kkogkkog}
      />

      {!isSent && couponStatus === 'READY' && (
        <Styled.DateInput
          type='date'
          value={meetingDate}
          min={getToday()}
          onChange={onChangeMeetingDate}
          required
        />
      )}

      <Styled.ButtonContainer>
        {buttons.map(buttonType => (
          <>
            {buttonType === '취소' && (
              <Button onClick={onClickCancelButton} css={Styled.ExtendedButton}>
                사용 취소
              </Button>
            )}
            {buttonType === '완료' && (
              <Button onClick={onClickFinishButton} css={Styled.ExtendedButton}>
                사용 완료
              </Button>
            )}
            {buttonType === '요청' && (
              <Button onClick={onClickRequestButton} css={Styled.ExtendedButton}>
                사용 요청
              </Button>
            )}
            {buttonType === '승인' && (
              <Button onClick={onClickAcceptButton} css={Styled.ExtendedButton}>
                사용 승인
              </Button>
            )}
          </>
        ))}
      </Styled.ButtonContainer>
    </Modal.WithHeader>
  );
};

export default KkogKkogModal;
