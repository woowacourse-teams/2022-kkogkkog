import { Fragment } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import CouponHistoryList from '@/@components/coupon/CouponHistoryList';
import BigCouponItem from '@/@components/coupon/CouponItem/big';
import { useFetchCoupon } from '@/@hooks/@queries/coupon';
import { useChangeCouponStatus } from '@/@hooks/business/coupon';
import useCouponPartner from '@/@hooks/ui/coupon/useCouponPartner';
import NotFoundPage from '@/@pages/404';
import { couponTypeTextMapper } from '@/constants/coupon';
import { DYNAMIC_PATH } from '@/Router';
import { COUPON_STATUS } from '@/types/coupon/client';

import * as Styled from './style';

type buttonType = '취소' | '완료' | '요청' | '승인' | '거절';

const receivedCouponMapper: Record<COUPON_STATUS, { buttons: buttonType[] }> = {
  REQUESTED: {
    buttons: ['취소'],
  },
  ACCEPTED: {
    buttons: ['완료'],
  },
  FINISHED: {
    buttons: [],
  },
  READY: {
    buttons: ['요청'],
  },
};

const sentCouponMapper: Record<COUPON_STATUS, { buttons: buttonType[] }> = {
  REQUESTED: {
    buttons: ['승인', '거절'],
  },
  ACCEPTED: {
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

  const { cancelCoupon, finishCoupon } = useChangeCouponStatus({
    couponId: Number(couponId),
  });

  const { isSent, member } = useCouponPartner(coupon);

  // @TODO: coupon 만 타입 가드하면 member도 가드 되는 방법
  if (!coupon || !member) {
    return <NotFoundPage />;
  }

  const { couponType, couponStatus, couponHistories, couponMessage } = coupon;

  const { buttons } = isSent ? sentCouponMapper[couponStatus] : receivedCouponMapper[couponStatus];

  const onClickRequestButton = () => {
    navigate(DYNAMIC_PATH.COUPON_REQUEST(String(couponId)));
  };

  const onClickCancelButton = async () => {
    if (!window.confirm('쿠폰 사용 요청을 취소하시겠어요?')) {
      return;
    }

    await cancelCoupon();
  };

  const onClickAcceptButton = () => {
    navigate(DYNAMIC_PATH.COUPON_ACCEPT(String(couponId)), { replace: true });
  };

  const onClickDeclineButton = () => {
    navigate(DYNAMIC_PATH.COUPON_DECLINE(String(couponId)), { replace: true });
  };

  const onClickFinishButton = async () => {
    if (!window.confirm('쿠폰을 사용하셨나요?')) {
      return;
    }

    await finishCoupon();
  };

  return (
    <PageTemplate.ExtendedStyleHeader title='쿠폰 자세히 보기'>
      <Styled.Root>
        <Styled.Top>
          <Styled.ProfileImage src={member.imageUrl} alt='프로필' width={51} height={51} />
          <Styled.SummaryMessage>
            <strong>
              {member.nickname} {isSent ? '님에게' : '님이'} 보낸
            </strong>
            &nbsp;
            {couponTypeTextMapper[couponType]} 쿠폰
          </Styled.SummaryMessage>
        </Styled.Top>
        <Styled.Main>
          <Styled.CouponInner>
            <BigCouponItem {...coupon} />
          </Styled.CouponInner>
          <Styled.SubSection>
            <Styled.SubSectionTitle>쿠폰 메시지</Styled.SubSectionTitle>
            <Styled.DescriptionContainer>{couponMessage}</Styled.DescriptionContainer>
          </Styled.SubSection>

          <Styled.SubSection>
            <Styled.SubSectionTitle>소통 히스토리</Styled.SubSectionTitle>
            <CouponHistoryList historyList={couponHistories} />
          </Styled.SubSection>
          <Styled.FinishButtonInner>
            {(couponStatus === 'READY' || couponStatus === 'REQUESTED') && (
              <button onClick={onClickFinishButton}>혹시 쿠폰을 사용하셨나요?</button>
            )}
          </Styled.FinishButtonInner>
          <Position position='fixed' bottom='0' css={Styled.ExtendedPosition}>
            {buttons.map(buttonType => (
              <Fragment key={buttonType}>
                {buttonType === '취소' && (
                  <Button onClick={onClickCancelButton} css={Styled.ExtendedButton}>
                    요청 취소하기
                  </Button>
                )}
                {buttonType === '완료' && (
                  <Button onClick={onClickFinishButton} css={Styled.ExtendedButton}>
                    사용 완료
                  </Button>
                )}
                {buttonType === '요청' && (
                  <Button onClick={onClickRequestButton} css={Styled.ExtendedButton}>
                    약속 요청하기
                  </Button>
                )}
                {buttonType === '승인' && (
                  <Button onClick={onClickAcceptButton} css={Styled.ExtendedButton}>
                    약속 확정하기
                  </Button>
                )}
                {buttonType === '거절' && (
                  <Button onClick={onClickDeclineButton} css={Styled.ExtendedButton}>
                    약속 거절하기
                  </Button>
                )}
              </Fragment>
            ))}
          </Position>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate.ExtendedStyleHeader>
  );
};

export default CouponDetailPage;
