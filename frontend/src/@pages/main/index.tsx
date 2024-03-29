import { css } from '@emotion/react';
import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import CustomSuspense from '@/@components/@shared/CustomSuspense';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import SmallCouponItem from '@/@components/coupon/CouponItem/small';
import HorizontalCouponList from '@/@components/coupon/CouponList/horizontal';
import ReservationSection from '@/@components/reservation/ReservationSection';
import { useFetchCouponList, useFetchReservationList } from '@/@hooks/@queries/coupon';
import { DYNAMIC_PATH, PATH } from '@/Router';
import { filterOptionsSessionStorage, unregisteredCouponCodeStorage } from '@/storage/session';
import theme from '@/styles/theme';
import { Coupon } from '@/types/coupon/client';

import * as Styled from './style';

const MainPage = () => {
  const navigate = useNavigate();

  const { reservationList, isLoading: isAcceptedCouponListLoading } = useFetchReservationList();

  const { openCouponList: receivedOpenCouponList, isLoading: isReceivedCouponListLoading } =
    useFetchCouponList({
      couponListType: 'received',
    });
  const { openCouponList: sentOpenCouponList, isLoading: isSentCouponListLoading } =
    useFetchCouponList({
      couponListType: 'sent',
    });

  const onClickCouponItem = (coupon: Coupon) => {
    navigate(DYNAMIC_PATH.COUPON_DETAIL(coupon.id));
  };

  const onClickViewMoreCoupon = () => {
    filterOptionsSessionStorage.set('전체');
  };

  useEffect(() => {
    const couponCode = unregisteredCouponCodeStorage.get();

    if (couponCode) {
      if (window.confirm('쿠폰을 받으러 갈까요?')) {
        navigate(DYNAMIC_PATH.UNREGISTERED_COUPON_REGISTER(couponCode));
      }

      unregisteredCouponCodeStorage.remove();
    }
  }, [navigate]);

  return (
    <PageTemplate.ExtendedStyleHeader title='꼭꼭'>
      <Styled.Root>
        <Styled.CreateCouponContainer>
          <h3>
            <Styled.ExtraBold>꼭꼭</Styled.ExtraBold>으로
            <br />
            당신의 마음을 전달해보세요!
          </h3>

          <Styled.AdditionalExplanation>
            시간을 보내고 싶어하는 사람들이 있을지 모릅니다.
          </Styled.AdditionalExplanation>
          <Link
            to={PATH.COUPON_CREATE_SELECT}
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
                  <Icon iconName='plus' size='32' color={theme.colors.white_100} />
                </Position>
              </Button>
            </Position>
          </Link>
        </Styled.CreateCouponContainer>

        <Styled.FullListContainer>
          <Styled.FullListTitle>
            <h3>예정된 약속</h3>
          </Styled.FullListTitle>

          <CustomSuspense
            fallback={<ReservationSection.Skeleton />}
            isLoading={isAcceptedCouponListLoading}
          >
            <ReservationSection reservationList={reservationList} />
          </CustomSuspense>
        </Styled.FullListContainer>

        <Styled.ListContainer>
          <section>
            <Styled.ListTitle>
              <h2>받은 쿠폰</h2>
              <Link
                to={PATH.RECEIVED_COUPON_LIST}
                css={Styled.ExtendedLink}
                onClick={onClickViewMoreCoupon}
              >
                더보기
              </Link>
            </Styled.ListTitle>
            <CustomSuspense
              fallback={
                <HorizontalCouponList.Skeleton CouponItemSkeleton={SmallCouponItem.Skeleton} />
              }
              isLoading={isReceivedCouponListLoading}
            >
              <HorizontalCouponList
                couponList={receivedOpenCouponList}
                CouponItem={SmallCouponItem}
                onClickCouponItem={onClickCouponItem}
              />
            </CustomSuspense>
          </section>

          <section>
            <Styled.ListTitle>
              <h2>보낸 쿠폰</h2>
              <Link
                to={PATH.SENT_COUPON_LIST}
                css={Styled.ExtendedLink}
                onClick={onClickViewMoreCoupon}
              >
                더보기
              </Link>
            </Styled.ListTitle>
            <CustomSuspense
              fallback={
                <HorizontalCouponList.Skeleton CouponItemSkeleton={SmallCouponItem.Skeleton} />
              }
              isLoading={isSentCouponListLoading}
            >
              <HorizontalCouponList
                couponList={sentOpenCouponList}
                CouponItem={SmallCouponItem}
                onClickCouponItem={onClickCouponItem}
              />
            </CustomSuspense>
          </section>

          <Styled.UnRegisteredCouponSection>
            <Styled.ListTitle>
              <h2>링크로 보낸 쿠폰</h2>
            </Styled.ListTitle>
            <Styled.UnRegisteredCouponSectionInner>
              <Link to={PATH.UNREGISTERED_COUPON_LIST}>
                <Button css={Styled.ExtendedUnRegisteredCouponMoreButton}>조회하기</Button>
              </Link>
            </Styled.UnRegisteredCouponSectionInner>
          </Styled.UnRegisteredCouponSection>
        </Styled.ListContainer>
      </Styled.Root>
    </PageTemplate.ExtendedStyleHeader>
  );
};

export default MainPage;
