import { css } from '@emotion/react';
import React, { useState } from 'react';

import Button from '@/@components/@shared/Button';
import Modal from '@/@components/@shared/Modal';
import useInput from '@/@hooks/@common/useInput';
import { useFetchMe } from '@/@hooks/@queries/user';
import useChangeCouponStatus from '@/@hooks/coupon/useChangeCouponStatus';
import { ANIMATION_DURATION } from '@/constants/animation';
import { COUPON_STATUS } from '@/types/client/coupon';
import { CouponResponse } from '@/types/remote/response';
import { getToday } from '@/utils';

import BigCouponItem from '../CouponItem/big';
import * as Styled from './style';

interface CouponItemProps {
  coupon: CouponResponse;
  closeModal: () => void;
}

type buttonType = '취소' | '완료' | '요청' | '승인';

const receivedCouponModalMapper: Record<COUPON_STATUS, { title: string; buttons: buttonType[] }> = {
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

const sentCouponModalMapper: Record<COUPON_STATUS, { title: string; buttons: buttonType[] }> = {
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

const CouponModal = (props: CouponItemProps) => {
  const { coupon, closeModal } = props;
  const { id, sender, couponStatus } = coupon;

  const { me } = useFetchMe();

  const [animation, setAnimation] = useState(false);

  const [meetingDate, onChangeMeetingDate] = useInput('');

  const { cancelCoupon, requestCoupon, finishCoupon, acceptCoupon } = useChangeCouponStatus(id);

  const isSent = me?.id === sender.id;

  const { title, buttons } = isSent
    ? sentCouponModalMapper[couponStatus]
    : receivedCouponModalMapper[couponStatus];

  const onCloseModal = () => {
    setAnimation(true);
    setTimeout(() => {
      closeModal();
    }, ANIMATION_DURATION.modal);
  };

  const onClickCancelButton = () => {
    cancelCoupon({
      onSuccessCallback() {
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
        onSuccessCallback() {
          onCloseModal();
        },
      }
    );
  };

  const onClickFinishButton = () => {
    finishCoupon({
      onSuccessCallback() {
        onCloseModal();
      },
    });
  };

  const onClickAcceptButton = () => {
    acceptCoupon({
      onSuccessCallback() {
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
