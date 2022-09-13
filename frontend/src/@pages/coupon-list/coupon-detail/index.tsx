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
import { useChangeCouponStatus } from '@/@hooks/business/coupon';
import { couponTypeTextMapper } from '@/constants/coupon';
import theme from '@/styles/theme';
import { COUPON_STATUS } from '@/types/client/coupon';

import NotFoundPage from '../../404';
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
  const { me } = useFetchMe();
  // @TODO reservation null type check
  const { cancelCoupon, finishCoupon } = useChangeCouponStatus({
    id: Number(couponId),
    reservationId: coupon?.reservationId ?? null,
  });

  if (!coupon) {
    return <NotFoundPage />;
  }

  const {
    senderId,
    senderNickname,
    senderImageUrl,
    receiverId,
    receiverNickname,
    receiverImageUrl,
    couponType,
    couponStatus,
    couponHistories,
    description,
  } = coupon;

  const isSent = me?.id === senderId;

  const { buttons } = isSent ? sentCouponMapper[couponStatus] : receivedCouponMapper[couponStatus];

  const onClickRequestButton = () => {
    navigate(`/coupon-list/${couponId}/request`);
  };

  const onClickCancelButton = async () => {
    if (!window.confirm('쿠폰 사용 요청을 취소하시겠어요?')) {
      return;
    }

    try {
      await cancelCoupon();
    } catch (error) {
      console.error(error);
    }
  };

  const onClickAcceptButton = () => {
    navigate(`/coupon-list/${couponId}/accept`);
  };

  const onClickDeclineButton = () => {
    navigate(`/coupon-list/${couponId}/decline`);
  };

  const onClickFinishButton = async () => {
    if (!window.confirm('쿠폰을 사용하셨나요?')) {
      return;
    }

    try {
      await finishCoupon();
    } catch (error) {
      console.error(error);
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
          <Styled.ProfileImage src={isSent ? receiverImageUrl : senderImageUrl} alt='' />
          <Styled.SummaryMessage>
            <strong>{isSent ? `${receiverNickname}님에게 ` : `${senderNickname}님이 `}보낸</strong>
            &nbsp;
            {couponTypeTextMapper[couponType]} 쿠폰
          </Styled.SummaryMessage>
        </Styled.Top>
        <Styled.Main>
          <Styled.CouponInner>
            {/* couponId, reservation, message 는 필요 없다. */}
            <BigCouponItem
              {...{
                ...coupon,
                memberId: isSent ? receiverId : senderId,
                nickname: isSent ? receiverNickname : senderNickname,
                memberType: isSent ? 'SENT' : 'RECEIVED',
              }}
            />
          </Styled.CouponInner>
          <Styled.SubSection>
            <Styled.SubSectionTitle>쿠폰 메시지</Styled.SubSectionTitle>
            <Styled.DescriptionContainer>{description}</Styled.DescriptionContainer>
          </Styled.SubSection>

          <Styled.SubSection>
            <Styled.SubSectionTitle>소통 히스토리</Styled.SubSectionTitle>
            <CouponHistoryList historyList={couponHistories} />
          </Styled.SubSection>
          <Styled.FinishButtonInner>
            {/* {(couponStatus === 'READY' || couponStatus === 'REQUESTED') && (
              <button onClick={onClickFinishButton}>혹시 쿠폰을 사용하셨나요?</button>
            )} */}
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
    </PageTemplate>
  );
};

export default CouponDetailPage;
