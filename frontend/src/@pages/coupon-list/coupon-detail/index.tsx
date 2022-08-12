import { css } from '@emotion/react';
import { Fragment } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import CouponHistoryList from '@/@components/coupon/CouponHistoryList';
import BigCouponItem from '@/@components/coupon/CouponItem/big';
import { useFetchCoupon } from '@/@hooks/@queries/coupon';
import { useFetchMe } from '@/@hooks/@queries/user';
import useChangeCouponStatus from '@/@hooks/coupon/useChangeCouponStatus';
import { couponTypeTextMapper } from '@/constants/coupon';
import theme from '@/styles/theme';
import { COUPON_STATUS } from '@/types/client/coupon';

import NotFoundPage from '../../404';
import * as Styled from './style';

type buttonType = '취소' | '완료' | '요청' | '승인' | '거절';

const receivedCouponMapper: Record<
  COUPON_STATUS,
  { confirmMessage?: string; buttons: buttonType[] }
> = {
  REQUESTED: {
    confirmMessage: '쿠폰 사용 요청을 취소하시겠어요?',
    buttons: ['취소'],
  },
  ACCEPTED: {
    confirmMessage: '쿠폰을 사용하셨나요?',
    buttons: ['완료'],
  },
  FINISHED: {
    buttons: [],
  },
  READY: {
    confirmMessage: '쿠폰을 사용하시겠어요?',
    buttons: ['요청'],
  },
};

const sentCouponMapper: Record<COUPON_STATUS, { confirmMessage?: string; buttons: buttonType[] }> =
  {
    REQUESTED: {
      buttons: ['승인', '거절'],
    },
    ACCEPTED: {
      confirmMessage: '쿠폰 사용하셨나요?',
      buttons: ['완료'],
    },
    FINISHED: {
      buttons: [],
    },
    READY: {
      buttons: [],
    },
  };

const CouponDetailPage = () => {
  const { couponId } = useParams();
  const navigate = useNavigate();

  const { coupon } = useFetchCoupon(Number(couponId));
  const { me } = useFetchMe();
  const { cancelCoupon, finishCoupon } = useChangeCouponStatus(Number(couponId));

  if (!coupon) {
    return <NotFoundPage />;
  }

  const { sender, receiver, couponType, couponStatus, couponHistories } = coupon;

  const isSent = me?.id === sender.id;

  const { confirmMessage, buttons } = isSent
    ? sentCouponMapper[couponStatus]
    : receivedCouponMapper[couponStatus];

  const onClickRequestButton = () => {
    navigate(`/coupon-list/${couponId}/request`);
  };

  const onClickCancelButton = () => {
    if (window.confirm(confirmMessage)) {
      cancelCoupon();
    }
  };

  const onClickAcceptButton = () => {
    navigate(`/coupon-list/${couponId}/accept`);
  };

  const onClickDeclineButton = () => {
    navigate(`/coupon-list/${couponId}/decline`);
  };

  const onClickFinishButton = () => {
    if (window.confirm(confirmMessage)) {
      finishCoupon();
    }
  };

  return (
    <PageTemplate title='쿠폰' hasHeader={false}>
      <Styled.Root>
        <Styled.Top>
          <Position position='absolute' top='20px' left='20px'>
            <Icon
              iconName='arrow'
              size='20'
              color={theme.colors.primary_400}
              onClick={() => navigate(-1)}
            />
          </Position>
          <Styled.ProfileImage src={isSent ? receiver.imageUrl : sender.imageUrl} alt='' />
          <Styled.SummaryMessage>
            {isSent ? `${receiver.nickname}님에게 ` : `${sender.nickname}님이 `}보낸&nbsp;
            {couponTypeTextMapper[couponType]} 쿠폰
          </Styled.SummaryMessage>
        </Styled.Top>
        <Styled.Main>
          <Styled.CouponInner>
            <BigCouponItem {...coupon} />
          </Styled.CouponInner>
          <Styled.HistorySection>
            <Styled.HistoryTitle>소통 히스토리</Styled.HistoryTitle>
            <CouponHistoryList historyList={couponHistories} />
          </Styled.HistorySection>
          <Styled.FinishButtonInner>
            {(couponStatus === 'READY' || couponStatus === 'REQUESTED') && (
              <button onClick={onClickFinishButton}>혹시 쿠폰을 사용하셨나요?</button>
            )}
          </Styled.FinishButtonInner>
          <Position position='fixed' bottom='0' right='0' css={Styled.ExtendedPosition}>
            {buttons.map(buttonType => (
              <Fragment key={buttonType}>
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
                {buttonType === '거절' && (
                  <Button onClick={onClickDeclineButton} css={Styled.ExtendedButton}>
                    사용 거절
                  </Button>
                )}
              </Fragment>
            ))}
          </Position>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate>
  );
};

export default CouponDetailPage;
