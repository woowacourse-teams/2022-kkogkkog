import { Link } from 'react-router-dom';

import Position from '@/@components/@shared/Position';
import { DYNAMIC_PATH } from '@/Router';
import { CouponResponse } from '@/types/remote/response';

import * as Styled from './style';

interface ReservationItemProps {
  reservatedCoupon: CouponResponse;
}

const ReservationItem = (props: ReservationItemProps) => {
  const { reservatedCoupon } = props;

  const { couponId } = reservatedCoupon;

  return (
    <Position>
      <Styled.Root>
        <Styled.Bar />
        <Styled.ImageContainer>
          <img src={reservatedCoupon.imageUrl} alt='profile' />
        </Styled.ImageContainer>
        <Styled.TextContainer>{reservatedCoupon.nickname}님과의 약속</Styled.TextContainer>

        <Position position='absolute' right='10px'>
          <Link to={DYNAMIC_PATH.COUPON_DETAIL(couponId)} css={Styled.LinkButton}>
            쿠폰보기
          </Link>
        </Position>
      </Styled.Root>
    </Position>
  );
};

export default ReservationItem;
