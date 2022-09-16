import { css } from '@emotion/react';
import { useMemo } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import CustomSuspense from '@/@components/@shared/CustomSuspense';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import SmallCouponItem from '@/@components/coupon/CouponItem/small';
import HorizontalCouponList from '@/@components/coupon/CouponList/horizontal';
import ReservationSection from '@/@components/reservation/ReservationSection';
import { useFetchCouponList } from '@/@hooks/@queries/coupon';
import { DYNAMIC_PATH, PATH } from '@/Router';
import { CouponResponse } from '@/types/remote/response';

import * as Styled from './style';

const MainPage = () => {
  const navigate = useNavigate();
  const { couponList, isLoading, parseOpenCouponList, generateReservationRecord } =
    useFetchCouponList();

  const reservationRecord = useMemo(() => generateReservationRecord(), [couponList]);
  const receivedOpenCouponList = useMemo(() => parseOpenCouponList('received'), [couponList]);
  const sentOpenCouponList = useMemo(() => parseOpenCouponList('sent'), [couponList]);

  const onClickCouponItem = (coupon: CouponResponse) => {
    navigate(DYNAMIC_PATH.COUPON_DETAIL(coupon.couponId));
  };

  return (
    <PageTemplate.LandingPage title='꼭꼭'>
      <Styled.Root>
        <Styled.CreateCouponContainer>
          <div>
            <Styled.ExtraBold>꼭꼭</Styled.ExtraBold>으로
            <br />
            당신의 마음을 전달해보세요!
          </div>

          <Styled.AdditionalExplanation>
            시간을 보내고 싶어하는 사람들이 있을지 모릅니다.
          </Styled.AdditionalExplanation>
          <Link
            to={PATH.COUPON_CREATE}
            css={css`
              margin-top: 30px;
            `}
          >
            <Position>
              <Button css={Styled.ExtendedButton}>
                쿠폰 보내러가기
                <Position
                  position='absolute'
                  css={css`
                    right: 5px;
                    bottom: 50%;

                    transform: translateY(50%);
                  `}
                >
                  <Icon iconName='plus' size='32' />
                </Position>
              </Button>
            </Position>
          </Link>
        </Styled.CreateCouponContainer>

        <Styled.FullListContainer>
          <Styled.FullListTitle>
            <span>예정된 약속</span>
          </Styled.FullListTitle>

          <CustomSuspense fallback={<ReservationSection.Skeleton />} isLoading={isLoading}>
            <ReservationSection reservationRecord={reservationRecord} />
          </CustomSuspense>
        </Styled.FullListContainer>

        <Styled.ListContainer>
          <div>
            <Styled.ListTitle>
              <span>받은 쿠폰</span>
              <Link to={PATH.RECEIVED_COUPON_LIST} css={Styled.ExtendedLink}>
                더보기
              </Link>
            </Styled.ListTitle>
            <CustomSuspense
              fallback={
                <HorizontalCouponList.Skeleton CouponItemSkeleton={SmallCouponItem.Skeleton} />
              }
              isLoading={isLoading}
            >
              <HorizontalCouponList
                couponList={receivedOpenCouponList}
                CouponItem={SmallCouponItem}
                onClickCouponItem={onClickCouponItem}
              />
            </CustomSuspense>
          </div>

          <div>
            <Styled.ListTitle>
              <span>보낸 쿠폰</span>
              <Link to={PATH.SENT_COUPON_LIST} css={Styled.ExtendedLink}>
                더보기
              </Link>
            </Styled.ListTitle>
            <CustomSuspense
              fallback={
                <HorizontalCouponList.Skeleton CouponItemSkeleton={SmallCouponItem.Skeleton} />
              }
              isLoading={isLoading}
            >
              <HorizontalCouponList
                couponList={sentOpenCouponList}
                CouponItem={SmallCouponItem}
                onClickCouponItem={onClickCouponItem}
              />
            </CustomSuspense>
          </div>
        </Styled.ListContainer>
      </Styled.Root>
    </PageTemplate.LandingPage>
  );
};

export default MainPage;