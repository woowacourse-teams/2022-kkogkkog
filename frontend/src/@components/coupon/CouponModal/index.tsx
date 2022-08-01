import { css } from '@emotion/react';
import React, { ChangeEventHandler, useState } from 'react';

import Button from '@/@components/@shared/Button';
import Modal from '@/@components/@shared/Modal';
import { useMe } from '@/@hooks/@queries/user';
import useChangeCouponStatus from '@/@hooks/coupon/useChangeCouponStatus';
import { ANIMATION_DURATION } from '@/constants/animation';
import { CouponResponse } from '@/types/remote/response';
import { getToday } from '@/utils';

import BigCouponItem from '../CouponItem/big';
import * as Styled from './style';

interface CouponItemProps {
  coupon: CouponResponse;
  closeModal: () => void;
}

const receivedCouponModalMapper = {
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

const sentCouponModalMapper = {
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

const CouponModal = (props: CouponItemProps) => {
  const { coupon, closeModal } = props;
  const { id, sender, couponStatus } = coupon;

  const { data: me } = useMe();

  const [animation, setAnimation] = useState(false);

  const [meetingDate, setMeetingDate] = useState('');

  const { cancelCoupon, requestCoupon, finishCoupon, acceptCoupon } = useChangeCouponStatus(id);

  const isSent = me?.id === sender.id;

  const { title, buttons } = isSent
    ? sentCouponModalMapper[couponStatus]
    : receivedCouponModalMapper[couponStatus];

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
    cancelCoupon({
      onSuccess() {
        onCloseModal();
      },
    });
  };

  const onClickRequestButton = () => {
    if (!meetingDate) {
      alert('날짜를 입력하고 요청해주세요.');

      return;
    }
    requestCoupon(
      { meetingDate },
      {
        onSuccess() {
          onCloseModal();
        },
      }
    );
  };

  const onClickFinishButton = () => {
    finishCoupon({
      onSuccess() {
        onCloseModal();
      },
    });
  };

  const onClickAcceptButton = () => {
    acceptCoupon({
      onSuccess() {
        onCloseModal();
      },
    });
  };

  return (
    <Modal.WithHeader
      title={title}
      position='bottom'
      animation={animation}
      closeModal={onCloseModal}
    >
      <BigCouponItem
        key={id}
        css={css`
          margin-bottom: 16px;
        `}
        {...coupon}
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
          <React.Fragment key={buttonType}>
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
          </React.Fragment>
        ))}
      </Styled.ButtonContainer>
    </Modal.WithHeader>
  );
};

export default CouponModal;
