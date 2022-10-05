import { Link } from 'react-router-dom';

import Position from '@/@components/@shared/Position';
import useCouponPartner from '@/@hooks/ui/coupon/useCouponPartner';
import { DYNAMIC_PATH } from '@/Router';
import { Coupon } from '@/types/coupon/client';

import * as Styled from './style';

interface ReservationItemProps {
  reservatedCoupon: Coupon;
}

const ReservationItem = (props: ReservationItemProps) => {
  const { reservatedCoupon } = props;

  const { id } = reservatedCoupon;

  const { member } = useCouponPartner(reservatedCoupon);

  return (
    <Position>
      <Styled.Root>
        <Styled.Bar />
        <Styled.ImageContainer>
          <img src={member.imageUrl} alt='프로필' width={40} height={40} />
        </Styled.ImageContainer>
        <Styled.TextContainer>{member.nickname}님과의 약속</Styled.TextContainer>

        <Position position='absolute' right='10px'>
          <Link to={DYNAMIC_PATH.COUPON_DETAIL(id)} css={Styled.LinkButton}>
            쿠폰보기
          </Link>
        </Position>
      </Styled.Root>
    </Position>
  );
};

export default ReservationItem;
